package com.supermy.base.db;

import com.supermy.base.web.FileUploadController;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/**
 * Created by moyong on 16/8/11.
 */
public class UploadConfig implements CommandLineRunner {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UploadConfig.class);

    @Override
    public void run(String... strings) throws Exception {
        try {
            //FileSystemUtils.deleteRecursively(new File(FileUploadController.ROOT));
            if (Files.notExists(Paths.get(FileUploadController.ROOT), LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectory(Paths.get(FileUploadController.ROOT));
            }
        } catch (IOException e) {

            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
