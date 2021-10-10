package persistence.mongo;

import java.util.Map;

public interface IImgMapper {

    int InsertImage(Map<String, Object> iMap, String img_colNm);

}
