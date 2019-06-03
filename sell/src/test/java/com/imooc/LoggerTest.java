package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    //LoggerTest.class为需要查看的类的日志，所以在那个方法中就需要本类的class
    //还可以直接用注解来代替，引入
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        String name = "imooc";
        int password = 123456;
        logger.debug("debug...");
        logger.info("name:{}, password:{}", name , password);
        logger.error("error...");
    }
}
