package com.lagou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileUploadController {
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    // 获取表单提交参数,完成文件上传
    public String fileupload(String username, MultipartFile[] filePic) throws IOException {
        System.out.println(username);
        // 文件转存
        // 获取后缀名
        for (MultipartFile multipartFile : filePic) {
            String originalFilename = multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File("D:/BaiduNetdiskDownload/" + originalFilename));
        }
        return "success";
    }
}
