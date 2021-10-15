package service.impl;

import mapper.IUserMapper;
import org.springframework.stereotype.Service;
import persistence.mongo.IImgMapper;
import service.IImgService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("ImgService")
public class ImgService implements IImgService {

    @Resource(name = "ImgMapper")
    IImgMapper imgMapper;

    public static String img_colNm = "ImageMap";

    @Override
    public int InsertImage(Map<String, Object> iMap) {

        return imgMapper.InsertImage(iMap, img_colNm);
    }

    @Override
    public List<Map<String, String>> getImgList(Map<String, Object> iMap) {

        return imgMapper.getImgList(iMap, img_colNm);
    }

    @Override
    public List<Map<String, String>> deletePastImg(Map<String, Object> dMap) {

        return imgMapper.deletePastImg(dMap, img_colNm);
    }

    @Override
    public int imgCheck(Map<String, Object> dMap) {

        return imgMapper.imgCheck(dMap, img_colNm);
    }
}