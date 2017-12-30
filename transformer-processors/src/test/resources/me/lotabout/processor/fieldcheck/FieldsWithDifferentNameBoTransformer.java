package me.lotabout.bo;

import java.lang.*;
import me.lotabout.bo.*;
import me.lotabout.pojo.*;

public class FieldsWithDifferentNameBoTransformer {
    public static FieldsWithDifferentNamePojo toFieldsWithDifferentNamePojo(FieldsWithDifferentNameBo from) {
        FieldsWithDifferentNamePojo to = new FieldsWithDifferentNamePojo();

        // Fields only in FieldsWithDifferentNameBo:
        // String: fieldOfBo

        // Fields only in FieldsWithDifferentNamePojo:
        // String: fieldOfPojo
        return to;
    }
}
