package me.lotabout.pojo;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ApplicantPojo {
    private int id;
    private String name;
    private int age;
    private Integer salary;
    private List<EducationPojo> educationList;
    private String[] array;
    private String[][] stringMatrix;
    private int[] intArray;
    private int[][] matrix;
}
