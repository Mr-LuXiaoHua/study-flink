package com.example.datastream.rideandfare;

import com.example.common.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:21
 */
public class TaxiFareDeserialization implements DeserializationSchema<TaxiFare> {
    @Override
    public TaxiFare deserialize(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        String message = byteBufferToString(buffer);
        if (StringUtils.isBlank(message)) {
            return null;
        }
        TaxiFare taxiFare = JsonUtils.fromJson(message, TaxiFare.class);
        return taxiFare;
    }

    @Override
    public boolean isEndOfStream(TaxiFare taxiFare) {
        return false;
    }

    @Override
    public TypeInformation<TaxiFare> getProducedType() {
        return TypeInformation.of(TaxiFare.class);
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
