package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;

public class SimpleExtractRoute2 extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // 첫 번째 타이머, "board" 테이블에 데이터를 삽입
        from("timer:test?repeatCount=1")
                .to("jdbc:dataSource?useHeadersAsParameters=true") // 데이터 삽입 쿼리 실행
                .setBody(constant("INSERT INTO board VALUES (101,'안녕','Open',SYSDATE,'나야나','1')"))
                .to("jdbc:dataSource"); // 데이터 삽입 쿼리 실행

        // 두 번째 타이머, "board" 테이블에서 데이터를 삭제
        from("timer:deleteTest?repeatCount=1")
                .setBody(constant("DELETE FROM board WHERE boardNo=101"))
                .to("jdbc:dataSource"); // 데이터 삭제 쿼리 실행
    }
}