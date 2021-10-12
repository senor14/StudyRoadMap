package service;

import java.util.List;
import java.util.Map;

public interface IImgService {

    int InsertImage(Map<String, Object> iMap);

    List<Map<String, String>> getImgList(Map<String, Object> iMap);

    List<Map<String, String>> deletePastImg(Map<String, Object> dMap);
}
