package com.supermy.web;

/**
 * Created by moyong on 16/8/11.
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FileUploadController.class);

    public static final String ROOT = "upload-dir";

    @RequestMapping(value="/singleUpload")
    public String singleUpload(){
        return "singleUpload";
    }


    @RequestMapping(value="/singleSave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleSave(@RequestParam("upload") MultipartFile file, @RequestParam("action") String desc ){
        System.out.println("File Description:"+desc);
        Map result = new HashMap();
        if (!file.isEmpty()) {
            try {
                Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));

                result.put("desc","You successfully uploaded " + file.getOriginalFilename() + "!");
                result.put("web_path",ROOT+"/"+file.getOriginalFilename());
                return result;

            } catch (IOException  e) {
                result.put("desc","Failued to upload " + file.getOriginalFilename());
                result.put("web_path","");
                return result;
            }
        } else {
            result.put("desc", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
            result.put("web_path","");
            return result;
        }

    }
    @RequestMapping(value="/multipleUpload")
    public String multiUpload(){
        return "multipleUpload";
    }
    @RequestMapping(value="/multipleSave", method=RequestMethod.POST )
    public @ResponseBody String multipleSave(@RequestParam("upload") MultipartFile[] files){
        String msg = "";
        if (files != null && files.length >0) {
            for(int i =0 ;i< files.length; i++){
                try {
                    Files.copy(files[i].getInputStream(), Paths.get(ROOT, files[i].getOriginalFilename()));
                    msg += "You successfully uploaded " + files[i].getOriginalFilename() + "!<br/>";
                } catch (Exception e) {
                    return "You failed to upload " + files[i].getOriginalFilename() + ": " + e.getMessage() +"<br/>";
                }
            }
            return msg;
        } else {
            return "Unable to upload. File is empty.";
        }
    }
}

