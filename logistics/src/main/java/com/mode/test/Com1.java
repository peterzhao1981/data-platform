package com.mode.test;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by zhaoweiwei on 16/12/29.
 */
@Component
public class Com1 {

    private Com1() {

    }

    @Bean
    public A createA() {
        return new A(2);
    }


}
