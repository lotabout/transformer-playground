package me.lotabout.bo;

import java.lang.String;
import me.lotabout.pojo.FieldsWithDifferentNamePojo;

public final class FieldsWithDifferentNameBoTransformer {
  public static FieldsWithDifferentNamePojo toFieldsWithDifferentNamePojo(FieldsWithDifferentNameBo from) {
    FieldsWithDifferentNamePojo to = new FieldsWithDifferentNamePojo();;
    // Fields only in FieldsWithDifferentNameBo
    // String: fieldOfBo
    // Fields only in FieldsWithDifferentNamePojo
    // String: fieldOfPojo
    return to;
  }
}
