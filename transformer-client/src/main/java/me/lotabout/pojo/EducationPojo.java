package me.lotabout.pojo;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class EducationPojo {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    String school;
    List<String> courses;
}
