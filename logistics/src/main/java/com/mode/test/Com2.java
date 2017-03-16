package com.mode.test;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by zhaoweiwei on 16/12/29.
 */
@Component
public class Com2 {

    @Bean
    private B getB(A a) {
        B b = new B();
        b.setB(a.getA());
        return b;
    }
}
