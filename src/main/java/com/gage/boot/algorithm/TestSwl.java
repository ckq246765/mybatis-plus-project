package com.gage.boot.algorithm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestSwl {

    public static void main(String[] args) {
        String string = new String("hhhh 1=1 hhhhh");
        log.info("{}", string.replaceAll("1=1", "1=1 and 2=2"));

    }

}
