package me.lotabout.bo;

import me.lotabout.annotation.Transformer;
import me.lotabout.pojo.ApplicantPojo;

import java.math.BigDecimal;
import java.util.List;

@Transformer(to = {ApplicantBo.class, ApplicantPojo.class})
public class ApplicantBo {
    private int id;
    private String name;
    private int age;
    private Integer salary;
    private List<EducationVo> educationList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public List<EducationVo> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationVo> educationList) {
        this.educationList = educationList;
    }
}
