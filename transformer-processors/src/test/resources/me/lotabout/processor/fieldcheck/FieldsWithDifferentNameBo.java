package me.lotabout.bo;

import me.lotabout.annotation.Transformer;
import me.lotabout.pojo.FieldsWithDifferentNamePojo;

@Transformer(to = FieldsWithDifferentNamePojo.class)
public class FieldsWithDifferentNameBo {
    String fieldOfBo;
}
