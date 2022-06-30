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
public class TaxiRideDeserialization implements DeserializationSchema<TaxiRide> {
    @Override
    public TaxiRide deserialize(byte[] bytes) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        String message = byteBufferToString(buffer);
        if (StringUtils.isBlank(message)) {
            return null;
        }
        TaxiRide taxiRide = JsonUtils.fromJson(message, TaxiRide.class);
        return taxiRide;
    }

    @Override
    public boolean isEndOfStream(TaxiRide taxiRide) {
        return false;
    }

    @Override
    public TypeInformation<TaxiRide> getProducedType() {
        return TypeInformation.of(TaxiRide.class);
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
