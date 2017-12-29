package me.lotabout.pojo;

import java.math.BigDecimal;
import java.util.List;

public class ApplicantPojo {
    private int id;
    private String name;
    private int age;
    private BigDecimal salary;
    private List<EducationPojo> educationList;

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

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public List<EducationPojo> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<EducationPojo> educationList) {
        this.educationList = educationList;
    }
}
