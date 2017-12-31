package me.lotabout.bo;

import java.lang.String;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.lotabout.pojo.ApplicantPojo;
import me.lotabout.pojo.EducationPojoTransformer;

public final class ApplicantBoTransformer {
    public static ApplicantPojo toApplicantPojo(ApplicantBo from) {
        ApplicantPojo to = new ApplicantPojo();;
        to.setId(from.getId());
        to.setName(from.getName());
        to.setAge(from.getAge());
        to.setSalary(from.getSalary());
        to.setEducationList(from.getEducationList().stream().map(l1 -> EducationPojoTransformer.fromEducationVo(l1)).collect(Collectors.toList()));
        // Fields only in ApplicantBo
        // List<List<String>>: additionalInfo
        // Map<String, EducationVo>: educationVoMap
        // Fields only in ApplicantPojo
        return to;
    }
}

