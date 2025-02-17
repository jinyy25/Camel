package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.springframework.stereotype.Component;

@Component
public class FileSampleRoute3 extends RouteBuilder {
    public FileSampleRoute3() {
    }

    public void configure() throws Exception {
        String content = "I'd like to get some food";
        from("timer:text?repeatCount=1")
                .setBody().constant("content") // 원하는 내용을 설정
                .to("file:data?fileName=Content"); // 파일 경로와 파일 이름 설정
    }
}

