package me.lotabout.pojo;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ApplicantPojo {
    private int id;
    private String name;
    private List<EducationPojo> educationList;
}
