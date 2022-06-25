package com.teamscorpio.webcrawling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebCrawlingApplicationTests {

    @Autowired
    private callWeb callWeb;

    @Test
    void testCallDigiKey() {
        callWeb.callDigiKey("ZXMP6A17E6TA","560638");
    }

    @Test
    void testCallArrow() {
        callWeb.callArrow();
    }

    @Test
    void testCallMouser() {
        callWeb.callMouser();
    }

    @Test
    void testCallAvent() {
        callWeb.callAvent();
    }

}
