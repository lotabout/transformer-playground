package me.lotabout.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

import me.lotabout.annotation.Transformer;
import me.lotabout.bo.EducationVo;

@Transformer(from={EducationVo.class})
@Data
@NoArgsConstructor
public class EducationPojo {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    String school;
    List<String> courses;
}
