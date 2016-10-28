package com.supermy.db;

import com.supermy.web.FileUploadController;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by moyong on 16/8/11.
 */
public class CmdConfig implements CommandLineRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(CmdConfig.class);

    @Override
    public void run(String... strings) throws Exception {
        try {
            System.out.println("cmd test:"+new Date());

        } catch (Exception e) {

            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
