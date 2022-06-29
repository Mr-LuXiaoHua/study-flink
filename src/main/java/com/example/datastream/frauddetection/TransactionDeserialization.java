package com.example.datastream.frauddetection;

import com.example.common.JsonUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:21
 */
public class TransactionDeserialization implements DeserializationSchema<Transaction> {
    @Override
    public Transaction deserialize(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        String message = byteBufferToString(buffer);
        if (StringUtils.isBlank(message)) {
            return null;
        }
        Transaction transaction = JsonUtils.fromJson(message, Transaction.class);
        return transaction;
    }

    @Override
    public boolean isEndOfStream(Transaction transaction) {
        return false;
    }

    @Override
    public TypeInformation<Transaction> getProducedType() {
        return TypeInformation.of(Transaction.class);
    }



    /**
     * ByteBuffer 转换 String
     * @param buffer
     * @return
     */
    private String byteBufferToString(ByteBuffer buffer) {
        String ret = "";
        try{
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            CharBuffer charBuffer = decoder.decode(buffer.asReadOnlyBuffer());;
            ret = charBuffer.toString();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
