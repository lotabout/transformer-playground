package me.lotabout.bo;

import lombok.Data;
import me.lotabout.annotation.Transformer;
import me.lotabout.pojo.EducationPojo;

import java.time.ZonedDateTime;
import java.util.List;

@Transformer(to = EducationPojo.class)
@Data
public class EducationVo {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    String school;
    List<String> courses;
}
