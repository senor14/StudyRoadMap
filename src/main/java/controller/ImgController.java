package controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import service.IImgService;
import util.DateUtil;
import util.FileUtil;
import util.ImageResizeUtil;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Developer: 김창영
 * Comment: 이미지 가져오는 컨트롤러
 * */
@Slf4j
@Controller
@RequestMapping("/")
public class ImgController {

    @Resource(name = "ImgService")
    IImgService imgService;

    // 프로필 이미지가 업로드되는 파일이 저장되는 기본폴더 설정(자바에서 경로는 /로 표현함)
    final private String USERFILE_UPLOAD_SAVE_PATH = "C:\\userimg"; // C:\\upload 폴더에 저장 /upload
    // 스터디 이미지가 업로드되는 기본폴더 설정
    final private String STUDYFILE_UPLOAD_SAVE_PATH = "C:\\studyimg"; // C:\\upload 폴더에 저장 /upload

    @RequestMapping(value = "img")
    public String img(){

        log.info("img start");

        log.info("img end");

        return "/Main/img";
    }
    // 프로필 이미지파일 업로드 (ajax로 구현)
    @RequestMapping(value = "FileUplod")
    @ResponseBody
    public int UserFileUpload(HttpServletRequest request, HttpServletResponse response, ModelMap model,
                                              @RequestParam(value = "fileUplod") MultipartFile mf, HttpSession session) throws Exception {

        log.info("FileUplod start");

        Map<String, String> rMap = new HashMap<String, String>();
        // 이미지 파일 저장하는 사용자 ID
        String user_uuid = (String) session.getAttribute("user_uuid");

        // 임의로 정의된 파일명을 원래대로 만들어주기 위한 목적
        String originalFileName = mf.getOriginalFilename();

        // 파일 확장자 가져오기
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length())
                .toLowerCase();

        int res = 0;

        // 이미지 파일만 실행되도록 함
        if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

            // 웹서버에 저장되는 파일이름 (영어, 숫자로 파일명 변경)
            String saveFileName = DateUtil.getDateTime("24hhmmss") + "_" + user_uuid + "." + ext;

            // 웹서버에 업로드한 파일 저장하는 물리적 경로
            String saveFilePath = FileUtil.mkdirForDate(USERFILE_UPLOAD_SAVE_PATH);
            String fullFileInfo = saveFilePath + "/" + saveFileName;

            rMap.put("path", fullFileInfo);

            // 정상적으로 값이 생성되었는지 로그에 찍어서 확인
            log.info("ext : " + ext);
            log.info("originalFileName : " + originalFileName);
            log.info("saveFileName : " + saveFileName);
            log.info("saveFilePath : " + saveFilePath);
            log.info("fullFileInfo : " + fullFileInfo);

            // 업로드 되는 파일을 서버에 저장
            File targetFile = new File(fullFileInfo);
            targetFile.setReadable(true, false);
            targetFile.setWritable(false, false);
            targetFile.setWritable(true, true);

            mf.transferTo(targetFile);

            Map<String, Object> iMap = new HashMap<>();

            iMap.put("save_file_name", saveFileName);
            iMap.put("save_file_path", saveFilePath);
            iMap.put("org_file_name", originalFileName);
            iMap.put("ext", ext);
            iMap.put("user_uuid", user_uuid);
            iMap.put("create_date", DateUtil.getDateTime("yyyy-MM-dd-hh:mm:ss"));

            log.info("imgService start!!");
            res = imgService.InsertImage(iMap);
            log.info("imgService end!!");

            // 이미지 리사이징
            File file = new File(mf.getOriginalFilename());
            mf.transferTo(file); // MultipartFile을 File 로 변환

            InputStream inputStream = new FileInputStream(file);
            Image img = new ImageIcon(file.toString()).getImage(); // 파일정보 추출
            log.info("사진의 가로길이 : " + img.getWidth(null));
            log.info("사진의 세로길이 : " + img.getHeight(null));

            int width = 700; // 리사이즈할 가로길이
            int height = 500; // 리사이즈한 세로길이

            BufferedImage resizedImage = ImageResizeUtil.resize(inputStream, width, height);
            ImageIO.write(resizedImage, ext, new File(fullFileInfo));

        }
        return res;
    }













}