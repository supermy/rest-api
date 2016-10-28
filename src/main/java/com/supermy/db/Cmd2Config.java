package com.supermy.db;

import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.Date;

/**
 * Created by moyong on 16/8/11.
 */
public class Cmd2Config implements CommandLineRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Cmd2Config.class);

    @Override
    public void run(String... strings) throws Exception {
        try {
            System.out.println("cmd2 test:"+new Date());

        } catch (Exception e) {

            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
