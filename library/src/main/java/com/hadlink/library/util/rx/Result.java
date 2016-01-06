package com.hadlink.library.util.rx;

import java.util.Map;

/**
 * 仅用于RxJava返回的封装数据(结构体封装)
 */
public class Result {
    public Map<String, String> header;
    public byte[] data;

    public Result(Map<String, String> header, byte[] data) {
        this.header = header;
        this.data = data;
    }
}
