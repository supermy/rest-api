package com.supermy.web;

/**
 * Created by moyong on 16/8/11.
 */


import com.supermy.security.AvatarRepository;
import com.supermy.security.domain.Avatar;
import com.supermy.utils.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class FileUploadController {

    @Autowired
    private AvatarRepository avatarRepository;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(FileUploadController.class);

    public static final String ROOT = "upload-dir";

    @RequestMapping(value="/singleUpload")
    public String singleUpload(){
        return "singleUpload";
    }

    /**
     * 单个文件上传，测试通过
     * @param file
     * @param desc
     * @return
     */
    @RequestMapping(value="/singleSave", method=RequestMethod.POST )
    public @ResponseBody
    Map singleSave(@RequestParam("upload") MultipartFile file, @RequestParam("action") String desc ){
        System.out.println("File Description:"+desc);
        Map result = new HashMap();

        if (!file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(file.getOriginalFilename());

                Path target = Paths.get(ROOT, filename);
                Files.copy(file.getInputStream(), target);

                log.debug(target.getFileName().toString());
                log.debug(target.getParent().getFileName().toString());
                log.debug(target.toString());
                log.debug(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());

                log.debug("=============ContentType:"+file.getContentType());
//                FilenameUtils.getExtension()
                log.debug(Files.probeContentType(target));


                Avatar img=new Avatar();
                img.setFilename(file.getOriginalFilename());
                img.setFilesize(file.getSize());
                img.setWebpath(ROOT+"/"+filename);
                img.setSyspath(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
                img.setCreateDate(new Date());
                img.setCreateBy("user");
                avatarRepository.save(img);


                result.put("data","");
                result.put("upload",img);
                Map m = new HashMap();

                List<Avatar> files = avatarRepository.findAll();
                Map mm=new HashMap();
                for (Avatar f:files
                     ) {
                    mm.put(f.getId(),f);
                }
                m.put("files", mm);

                result.put("files",m);
//                result.put("files",avatarRepository.findAll());

                result.put("desc","文件上传成功"+file.getOriginalFilename());

                return result;

            } catch (IOException  e) {
                result.put("desc","文件上传失败"+file.getOriginalFilename());
                return result;
            }
        } else {
            result.put("desc", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
            return result;
        }

    }

    @RequestMapping(value="/multipleUpload")
    public String multiUpload(){
        return "multipleUpload";
    }

    /**
     * 返回数据格式需要完善。
     *
     * @param files
     * @return
     */
    @RequestMapping(value="/multipleSave", method=RequestMethod.POST )
    public @ResponseBody
    Map multipleSave(@RequestParam("upload") MultipartFile[] files){
        String msg = "";
        Map result = new HashMap();
        List list = new ArrayList();
        if (files != null && files.length > 0) {

            for (int i = 0; i < files.length; i++) {
                try {
                    String filename = UUID.randomUUID().toString()+"."+FileUtils.getExtensionName(files[i].getOriginalFilename());

                    Path target = Paths.get(ROOT, filename);
                    Files.copy(files[i].getInputStream(), target);


                    Avatar img = new Avatar();
                    img.setFilename(files[i].getOriginalFilename());
                    img.setFilesize(files[i].getSize());
                    img.setWebpath(ROOT + "/" + filename);
                    img.setSyspath(target.toRealPath(LinkOption.NOFOLLOW_LINKS).toString());
                    img.setCreateDate(new Date());
                    img.setCreateBy("user");
                    avatarRepository.save(img);

                    list.add(img);

                }

                catch(Exception e){

                    result.put("desc", "文件上传失败" + "You failed to upload " + files[i].getOriginalFilename() + ": " + e.getMessage() + "<br/>");
                }
            }

            result.put("data", list);

            return result;


        } else {
            result.put("desc", "Unable to upload. File is empty.");

            return result;
        }
    }
}

