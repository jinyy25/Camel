package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;
import org.apache.camel.model.TryDefinition;

import java.util.ArrayList;
import java.util.List;

public class OnCompletionTest extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // onCompletion을 사용하여 완료 후 작업을 처리
        onCompletion()
                .log("stop");

        // 리스트 생성
        List<String> list = new ArrayList<>();
        list.add("juice");
        list.add("milk");

        // 경로 정의
        from("timer:what?repeatCount=1")
                .setBody().constant(list)
                .split(body())
                .streaming()
                .stopOnException()
                .doTry()
                .log("${body}")
                .throwException(new Exception("test"))
                .endDoTry()
                .doCatch(Exception.class)
                .log("finished")
                .endDoCatch();
    }
}