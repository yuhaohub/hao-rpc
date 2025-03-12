package com.yuhao.haorpc.protocol;

import com.yuhao.haorpc.serializer.Serializer;
import com.yuhao.haorpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;


/**
 * 编码器（转Buffer）
 */
public class ProtocolMessageEncoder {

    public static Buffer encode(ProtocolMessage protocolMessage) throws IOException {
        if(protocolMessage == null || protocolMessage.getHeader() == null){
            return Buffer.buffer();

        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());
        //获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if(serializerEnum == null){
            throw  new RuntimeException("序列协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        //写入数据
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}
