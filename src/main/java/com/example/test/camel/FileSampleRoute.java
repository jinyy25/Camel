package com.example.test.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ProcessorDefinition;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileSampleRoute extends RouteBuilder {

    public void configure() throws Exception {
        String sql = "SELECT * FROM board WHERE boardContent = 13ORDER BY boardNo";
        List<String> sggs = Arrays.asList("1");
        Iterator var4 = sggs.iterator();

        while (var4.hasNext()) {
            String sgg = (String) var4.next();

            // 타이머 엔드포인트 설정
            from("timer://test111?repeatCount=1")  // 타이머를 한 번만 실행
                    .setBody().constant(sql)            // SQL 상수 설정
                    .to("file:data3?fileName=ABC-" + sgg);  // 파일로 전송
        }

    }
}

