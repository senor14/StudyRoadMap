package controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.IImgService;
import util.CmmUtil;
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
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Developer: 김창영
 * Comment: 이미지 가져오는 컨트롤러
 */

@Slf4j
@Controller
public class ImgController {

    @Resource(name = "ImgService")
    IImgService imgService;

    // 로드맵 이미지가 저장되는 기본폴더 설정
    final private String ROADMAP_UPLOAD_SAVE_PATH = "C:\\studyRoadMapImg"; // C:\\studyRoadMapImg 폴더에 저장 /studyRoadMapImg
    // 마인드맵 이미지가 저장되는 기본폴더 설정
    final private String MINDMAP_UPLOAD_SAVE_PATH = "C:\\studyMindMapImg"; // C:\\studyMindMapImg 폴더에 저장 /studyMindMapImg

    // 로드맵 이미지 캡쳐 저장
    @RequestMapping(value = "roadMapFileUpload")
    @ResponseBody
    public int roadMapFileUpload(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("FileUpload start");

        // 이미지 파일 저장하는 사용자 UUID
        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        Map<String, Object> dMap = new HashMap<>();

        dMap.put("userUuid" , userUuid);
        dMap.put("orgFileName" , "roadMapCapture.png");

        List<Map<String, String>> rList = imgService.deletePastImg(dMap);

        File file = new File(rList.get(0).get("saveFilePath") + "/" + rList.get(0).get("saveFileName"));
        if (file.exists()) {
            if (file.delete()) {
                log.info("파일삭제 성공");
            } else {
                log.info("파일삭제 실패");
            }
        } else {
            log.info("파일이 존재하지 않습니다.");
        }

        String binaryData = request.getParameter("imgSrc");

        String originalFileName = "roadMapCapture.png";

        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length())
                .toLowerCase();

        // 웹서버에 저장되는 파일이름 (영어, 숫자로 파일명 변경)
        String saveFileName = userUuid + "." + ext;

        // 웹서버에 업로드한 파일 저장하는 물리적 경로
        String saveFilePath = FileUtil.mkdirForDate(ROADMAP_UPLOAD_SAVE_PATH);
        String fullFileInfo = saveFilePath + "/" + saveFileName;

        // 정상적으로 값이 생성되었는지 로그에 찍어서 확인
        log.info("ext : " + ext);
        log.info("originalFileName : " + originalFileName);
        log.info("saveFileName : " + saveFileName);
        log.info("saveFilePath : " + saveFilePath);
        log.info("fullFileInfo : " + fullFileInfo);

        byte[] inputFile = null;
        try {
            if (binaryData == null || binaryData.trim().equals("")) {
                throw new Exception();
            }
            binaryData = binaryData.replaceAll("data:image/png;base64,", "");
            inputFile = Base64.decodeBase64(binaryData);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("에러 발생");
        }

        Map<String, Object> iMap = new HashMap<>();

        iMap.put("saveFileName", saveFileName);
        iMap.put("saveFilePath", saveFilePath);
        iMap.put("orgFileName", originalFileName);
        iMap.put("ext", ext);
        iMap.put("userUuid", userUuid);
        iMap.put("createDate", DateUtil.getDateTime("yyyy-MM-dd-hh:mm:ss"));

        // 이미지 정보 저장
        log.info("imgService start!!");
        int res = imgService.InsertImage(iMap);
        log.info("imgService end!!");

        // 이미지 리사이징
        InputStream inputStream = new ByteArrayInputStream(inputFile);

        int width = 500; // 리사이즈할 가로길이
        int height = 500; // 리사이즈한 세로길이

        BufferedImage resizedImage = ImageResizeUtil.resize(inputStream, width, height);
        ImageIO.write(resizedImage, ext, new File(fullFileInfo));

        return res;
    }

    // 로드맵 이미지 가져오기
    @RequestMapping(value = "/getRoadMapImage", method = RequestMethod.GET)
    public void getRoadMapImage(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        log.info("getRoadMapImage start! ");

        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        log.info("userUuid : " + userUuid);

        // 가장 최근에 등록한 프로필 사진 정보가져오기

        Map<String, Object> iMap = new HashMap<>();

        iMap.put("userUuid", userUuid);
        iMap.put("orgFileName", "roadMapCapture.png");

        List<Map<String, String>> rList = imgService.getImgList(iMap);
        log.info("getImgList end! ");

        if (rList == null) {
            rList = new LinkedList<>();
        }

        String realFile = CmmUtil.nvl(rList.get(0).get("saveFilePath") + "/"); // 파일이 저장된 경로 : /usr/local/images/userimg/0000/00/00/
        String fileNm = CmmUtil.nvl(rList.get(0).get("saveFileName")); // 파일명 : 000000.jpg 000000.png
        String ext = CmmUtil.nvl(rList.get(0).get("ext")); // 파일 확장자
        log.info("realFile : " + realFile);
        log.info("fileNm : " + fileNm);
        log.info("ext : " + ext);

        BufferedOutputStream out = null;
        InputStream in = null;

        try {

            if (!ext.equals("")) {
                response.setContentType("image/" + ext);
                response.setHeader("Content-Disposition", "inline;filename=" + fileNm);
                File file = new File(realFile + fileNm);
                log.info("file : " + file);

                in = new FileInputStream(file);
                out = new BufferedOutputStream(response.getOutputStream());
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } else {
                log.info("basicFile start");

                String basicFile = "/img/basicimg.png";

                File file1 = new File(basicFile);
                log.info("basicFile : " + basicFile);
                log.info("file1 : " + file1);

                in = new FileInputStream(file1);
                out = new BufferedOutputStream(response.getOutputStream());
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

            }
        } catch (Exception e) {
            log.info(String.valueOf(e.getStackTrace()));
        } finally {
            if (out != null) {
                out.flush();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        log.info("getRoadMapImage end");
    }

    // 마인드맵 이미지 캡쳐 저장
    @RequestMapping(value = "mindMapFileUpload")
    @ResponseBody
    public int mindMapFileUpload(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("mindMapFileUpload start");

        // 이미지 파일 저장하는 사용자 UUID
        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        Map<String, Object> dMap = new HashMap<>();

        dMap.put("userUuid" , userUuid);
        dMap.put("orgFileName" , "mindMapCapture.png");

        List<Map<String, String>> rList = imgService.deletePastImg(dMap);

        File file = new File(rList.get(0).get("saveFilePath") + "/" + rList.get(0).get("saveFileName"));
        if (file.exists()) {
            if (file.delete()) {
                log.info("파일삭제 성공");
            } else {
                log.info("파일삭제 실패");
            }
        } else {
            log.info("파일이 존재하지 않습니다.");
        }

        String binaryData = request.getParameter("imgSrc");

        String originalFileName = "mindMapCapture.png";

        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName.length())
                .toLowerCase();

        // 웹서버에 저장되는 파일이름 (영어, 숫자로 파일명 변경)
        String saveFileName = userUuid + "." + ext;

        // 웹서버에 업로드한 파일 저장하는 물리적 경로
        String saveFilePath = FileUtil.mkdirForDate(MINDMAP_UPLOAD_SAVE_PATH);
        String fullFileInfo = saveFilePath + "/" + saveFileName;

        // 정상적으로 값이 생성되었는지 로그에 찍어서 확인
        log.info("ext : " + ext);
        log.info("originalFileName : " + originalFileName);
        log.info("saveFileName : " + saveFileName);
        log.info("saveFilePath : " + saveFilePath);
        log.info("fullFileInfo : " + fullFileInfo);

        byte[] inputFile = null;
        try {
            if (binaryData == null || binaryData.trim().equals("")) {
                throw new Exception();
            }
            binaryData = binaryData.replaceAll("data:image/png;base64,", "");
            inputFile = Base64.decodeBase64(binaryData);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("에러 발생");
        }

        Map<String, Object> iMap = new HashMap<>();

        iMap.put("saveFileName", saveFileName);
        iMap.put("saveFilePath", saveFilePath);
        iMap.put("orgFileName", originalFileName);
        iMap.put("ext", ext);
        iMap.put("userUuid", userUuid);
        iMap.put("createDate", DateUtil.getDateTime("yyyy-MM-dd-hh:mm:ss"));

        // 이미지 정보 저장
        log.info("imgService start!!");
        int res = imgService.InsertImage(iMap);
        log.info("imgService end!!");

        // 이미지 리사이징
        InputStream inputStream = new ByteArrayInputStream(inputFile);

        int width = 500; // 리사이즈할 가로길이
        int height = 500; // 리사이즈한 세로길이

        BufferedImage resizedImage = ImageResizeUtil.resize(inputStream, width, height);
        ImageIO.write(resizedImage, ext, new File(fullFileInfo));

        log.info("mindMapFileUpload end");
        return res;
    }

    // 마인드맵 이미지 가져오기
    @RequestMapping(value = "/getMindMapImage", method = RequestMethod.GET)
    public void getMindMapImage(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

        log.info("getMindMapImage start! ");

        String userUuid = (String) session.getAttribute("SS_USER_UUID");

        log.info("userUuid : " + userUuid);

        // 가장 최근에 등록한 프로필 사진 정보가져오기

        Map<String, Object> iMap = new HashMap<>();

        iMap.put("userUuid", userUuid);
        iMap.put("orgFileName", "mindMapCapture.png");

        List<Map<String, String>> rList = imgService.getImgList(iMap);
        log.info("getImgList end! ");

        if (rList == null) {
            rList = new LinkedList<>();
        }

        String realFile = CmmUtil.nvl(rList.get(0).get("saveFilePath") + "/"); // 파일이 저장된 경로 : /usr/local/images/userimg/0000/00/00/
        String fileNm = CmmUtil.nvl(rList.get(0).get("saveFileName")); // 파일명 : 000000.jpg 000000.png
        String ext = CmmUtil.nvl(rList.get(0).get("ext")); // 파일 확장자
        log.info("realFile : " + realFile);
        log.info("fileNm : " + fileNm);
        log.info("ext : " + ext);

        BufferedOutputStream out = null;
        InputStream in = null;

        try {

            if (!ext.equals("")) {
                response.setContentType("image/" + ext);
                response.setHeader("Content-Disposition", "inline;filename=" + fileNm);
                File file = new File(realFile + fileNm);
                log.info("file : " + file);

                in = new FileInputStream(file);
                out = new BufferedOutputStream(response.getOutputStream());
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } else {
                log.info("basicFile start");

                String basicFile = "/img/basicimg.png";

                File file1 = new File(basicFile);
                log.info("basicFile : " + basicFile);
                log.info("file1 : " + file1);

                in = new FileInputStream(file1);
                out = new BufferedOutputStream(response.getOutputStream());
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

            }
        } catch (Exception e) {
            log.info(String.valueOf(e.getStackTrace()));
        } finally {
            if (out != null) {
                out.flush();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        }
        log.info("getMindMapImage end");
    }

    @RequestMapping(value="getMindImg")
    public String getMindImg(){

        return "/Main/getImg";
    }

}