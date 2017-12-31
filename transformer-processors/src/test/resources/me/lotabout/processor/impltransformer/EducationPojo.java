package me.lotabout.pojo;

import java.time.ZonedDateTime;
import java.util.List;

import me.lotabout.annotation.Transformer;
import me.lotabout.bo.EducationVo;

@Transformer(from={EducationVo.class})
public class EducationPojo {
    ZonedDateTime startTime;
    ZonedDateTime endTime;
    String school;
    List<String> courses;

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
