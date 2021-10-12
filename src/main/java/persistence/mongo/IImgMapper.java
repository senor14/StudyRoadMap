package persistence.mongo;

import java.util.List;
import java.util.Map;

public interface IImgMapper {

    int InsertImage(Map<String, Object> iMap, String img_colNm);

    List<Map<String, String>> getImgList(Map<String, Object> iMap, String img_colNm);

    List<Map<String, String>> deletePastImg(Map<String, Object> dMap, String img_colNm);
}
