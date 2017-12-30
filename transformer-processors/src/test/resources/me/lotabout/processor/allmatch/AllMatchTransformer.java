package me.lotabout.bo;

import java.lang.*;
import java.lang.Integer;
import java.lang.String;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import me.lotabout.bo.*;
import me.lotabout.pojo.*;

public class AllMatchBoTransformer {
    public static AllMatchPojo toAllMatchPojo(AllMatchBo from) {
        AllMatchPojo to = new AllMatchPojo();
        to.setFieldNestedMap(from.getFieldNestedMap().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> v1.getValue().entrySet().stream().collect(Collectors.toMap(k2 -> k2.getKey(), v2 -> v2.getValue())))));
        to.setFieldShortClass(from.getFieldShortClass());
        to.setFieldMap(from.getFieldMap().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> v1.getValue())));
        to.setFieldDouble(from.getFieldDouble());
        to.setFieldByte(from.getFieldByte());
        to.setFieldCharacter(from.getFieldCharacter());
        to.setFieldBool(from.isFieldBool());
        to.setFieldLongClass(from.getFieldLongClass());
        to.setFieldByteClass(from.getFieldByteClass());
        to.setFieldListClass(from.getFieldListClass().stream().map(l1 -> AllMatchBoTransformer.toAllMatchPojo(l1)).collect(Collectors.toList()));
        to.setFieldMapClass(from.getFieldMapClass().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> AllMatchBoTransformer.toAllMatchPojo(v1.getValue()))));
        to.setFieldShort(from.getFieldShort());
        to.setFieldChar(from.getFieldChar());
        to.setFieldFloatClass(from.getFieldFloatClass());
        to.setFieldLong(from.getFieldLong());
        to.setFieldInteger(from.getFieldInteger());
        to.setFieldInt(from.getFieldInt());
        to.setFieldNestedList(from.getFieldNestedList().stream().map(l1 -> l1.stream().map(l2 -> l2).collect(Collectors.toList())).collect(Collectors.toList()));
        to.setFieldBoolean(from.getFieldBoolean());
        to.setFieldDoubleClass(from.getFieldDoubleClass());
        to.setTime(from.getTime());
        to.setFieldFloat(from.getFieldFloat());
        to.setFieldList(from.getFieldList().stream().map(l1 -> l1).collect(Collectors.toList()));

        // Fields only in AllMatchBo:

        // Fields only in AllMatchPojo:
        return to;
    }
}
