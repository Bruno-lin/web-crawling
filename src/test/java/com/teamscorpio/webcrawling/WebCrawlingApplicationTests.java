package com.teamscorpio.webcrawling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    void testCallDigiKeyTable() {
        List<String> content = getContent();
        System.out.println("content: " + content);
        callWeb.getRealItemList(content);
    }

    public static List<String> getContent() {
        String fileName = "/home/brunozzz/Downloads/dj.txt";
        //读取文件
        List<String> lineLists = null;
        try {
            System.out.println(fileName);
            lineLists = Files
                    .lines(Paths.get(fileName), Charset.defaultCharset())
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineLists;
    }


}
