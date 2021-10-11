package domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@Document(collection = "CareerRoadMap")
public class CareerRoadMap {

    @Id
    private String userUuid;
    private String careerTitle;
    private String created;
    private String publicYn;
}
