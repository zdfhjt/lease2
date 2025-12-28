package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.web.app.service.SmsService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsServiceImplTest {
    @Autowired
    private SmsService smsService;
    @Test
    void send() {
        smsService.sendCode("17750923229","3123");
    }

}