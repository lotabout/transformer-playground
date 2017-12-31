package me.lotabout.bo;

import lombok.Data;
import me.lotabout.annotation.Transformer;
import me.lotabout.pojo.ApplicantPojo;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
@Transformer(to = {ApplicantPojo.class})
public class ApplicantBo {
    private int id;
    private String name;
    private int age;
    private Integer salary;
    private List<EducationVo> educationList;
    private List<List<String>> additionalInfo;
    private Map<String, EducationVo> educationVoMap;
    private String[] array;
    private String[][] stringMatrix;
    private int[] intArray;
    private int[][] matrix;
}
