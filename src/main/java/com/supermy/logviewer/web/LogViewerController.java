package com.supermy.logviewer.web;

/**
 * Created by moyong on 16/10/28.
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.supermy.logviewer.domain.FileNode;
import com.supermy.logviewer.service.LogViewerService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/logviewer")
public class LogViewerController {

    protected final Logger log = LogManager.getLogger(getClass());
    @Autowired
    private LogViewerService logViewerService;

    /**
     * Build list of all files in the log directory
     *
     * @return JSON response with objects expected by a tree grid.
     */
    @RequestMapping(value = "/files")
    public
    @ResponseBody
    Object getFiles(HttpServletResponse response) {
        try {
            List<FileNode> files = logViewerService.getFiles();
            return files;
        } catch (Exception e) {
            log.error("Error loading files ", e);
            // This just returns a Map that will result in JSON {"success":"false", "message":"Unexpected Error"}
//            Map<String, Object> errorMap = WebUtil.getModelMapMessage(false, ControllerConstants.DEFAULT_ERROR_MSG, null);
            Map<String, Object> errorMap = new HashMap();
            errorMap.put("error", e.getMessage());
            return errorMap;
        }
    }

    /**
     * Open a specified file in a browser window/tab.  Some of the success of the
     * open might depend on client-side settings.
     *
     * @param request
     * @param response
     * @param fileName The name of the file to open, including path info if not in
     *                 the root log directory.
     */
    @RequestMapping(value = "/open")
    public void openFile(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("fileName") String fileName) {
        try {
            File file = logViewerService.getFile(fileName);
            byte[] content = new byte[(int) file.length()];
            response.setContentType("text/plain");
            response.addHeader("content-disposition", "inline;filename=" + fileName);
            response.setContentLength(content.length);
            FileInputStream in = new FileInputStream(file);
            FileCopyUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            log.error("Something happened", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Download the selected file to the client.
     *
     * @param request
     * @param response
     * @param fileName The name of the file to open, including path info if not in
     *                 the root log directory.
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("fileName") String fileName) {
        try {
            File file = logViewerService.getFile(fileName);
            byte[] content = new byte[(int) file.length()];
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Cache-Control", "cache, must-revalidate");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentLength(content.length);
            FileInputStream in = new FileInputStream(file);
            FileCopyUtils.copy(in, response.getOutputStream());

        } catch (Exception e) {
            log.error("Something happened", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
