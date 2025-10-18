package com.luo.gateway.demo1.socket.agreement;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class RequestParser {


    private final FullHttpRequest request;

    public RequestParser(FullHttpRequest request) {
        this.request = request;
    }

    /**
     * 简单请求路径
     *
     * @return String
     */
    public String getUri() {
        String uri = request.uri();
        int index = uri.indexOf("?");
        if (index > 0) {
            uri = uri.substring(0, index);
        }
        if (uri.equals("favicon.ico")) return null;
        uri = uri;
        return uri;
    }


    /**
     * 解析封装请求
     */
    public Map<String, Object> parse() {
        //获取请求类型
        HttpMethod method = request.getMethod();
        Map<String, Object> paramsMap = new HashMap<>();
        if (method == HttpMethod.GET) {
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
            queryStringDecoder.parameters().forEach((key, value) -> paramsMap.put(key, value.get(0)));
        } else if (method == HttpMethod.POST) {
            //获取content-type
            String contentType = getContentType();
            switch (contentType) {

                case "multipart/form-data":
                    Map<String, Object> parameterMap = new HashMap<>();
                    HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(request);
                    decoder.offer(request);
                    decoder.getBodyHttpDatas().forEach(data -> {
                        Attribute attr = (Attribute) data;
                        try {
                            parameterMap.put(data.getName(), attr.getValue());
                        } catch (IOException ignore) {
                        }
                    });
                    return parameterMap;
                case "application/json":
                    ByteBuf byteBuf = request.content().copy();
                    if (byteBuf.isReadable()) {
                        String json = byteBuf.toString(StandardCharsets.UTF_8);
                        return JSON.parseObject(json);
                    }
                    break;
                case "none":
                    return new HashMap<>();
                default:
                    throw new RuntimeException("未实现类型");
            }
        }
        throw new RuntimeException("未实现类型");
    }

    private String getContentType() {
        Optional<Map.Entry<String, String>> header = request.headers().entries().stream()
                .filter(val -> val.getKey().equals("Content-Type")).findAny();
        Map.Entry<String, String> entry = header.orElse(null);
        if (entry == null) return "none";

        String contentType = entry.getValue();
        int idx = contentType.indexOf(";");
        if (idx > 0) {
            return contentType.substring(0, idx);
        }
        return contentType;
    }
}
