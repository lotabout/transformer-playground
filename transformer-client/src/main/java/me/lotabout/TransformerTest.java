package me.lotabout;

import me.lotabout.bo.ApplicantBo;
import me.lotabout.pojo.ApplicantPojo;
import me.lotabout.bo.ApplicantBoTransformer;

public class TransformerTest {
    static void test() {
        ApplicantBo bo = null;
        ApplicantPojo pojo = ApplicantBoTransformer.toApplicantPojo(bo);
        System.out.println(pojo.getName());
    }
}
