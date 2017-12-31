package me.lotabout.pojo;

import java.util.stream.Collectors;
import me.lotabout.bo.EducationVo;

public final class EducationPojoTransformer {
    public static EducationPojo fromEducationVo(EducationVo from) {
        EducationPojo to = new EducationPojo();;
        to.setStartTime(from.getStartTime());
        to.setEndTime(from.getEndTime());
        to.setSchool(from.getSchool());
        to.setCourses(from.getCourses().stream().map(l1 -> l1).collect(Collectors.toList()));
        // Fields only in EducationVo
        // Fields only in EducationPojo
        return to;
    }
}
