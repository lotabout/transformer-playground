package me.lotabout.bo;

import java.util.stream.Collectors;
import me.lotabout.pojo.AllMatchPojo;

public final class AllMatchBoTransformer {
  public static AllMatchPojo toAllMatchPojo(AllMatchBo from) {
    AllMatchPojo to = new AllMatchPojo();;
    to.setFieldBool(from.isFieldBool());
    to.setFieldByte(from.getFieldByte());
    to.setFieldChar(from.getFieldChar());
    to.setFieldShort(from.getFieldShort());
    to.setFieldInt(from.getFieldInt());
    to.setFieldLong(from.getFieldLong());
    to.setFieldFloat(from.getFieldFloat());
    to.setFieldDouble(from.getFieldDouble());
    to.setFieldBoolean(from.isFieldBoolean());
    to.setFieldByteClass(from.getFieldByteClass());
    to.setFieldCharacter(from.getFieldCharacter());
    to.setFieldShortClass(from.getFieldShortClass());
    to.setFieldInteger(from.getFieldInteger());
    to.setFieldLongClass(from.getFieldLongClass());
    to.setFieldFloatClass(from.getFieldFloatClass());
    to.setFieldDoubleClass(from.getFieldDoubleClass());
    to.setTime(from.getTime());
    to.setFieldList(from.getFieldList().stream().map(l1 -> l1).collect(Collectors.toList()));
    to.setFieldNestedList(from.getFieldNestedList().stream().map(l1 -> l1.stream().map(l2 -> l2).collect(Collectors.toList())).collect(Collectors.toList()));
    to.setFieldListClass(from.getFieldListClass().stream().map(l1 -> AllMatchBoTransformer.toAllMatchPojo(l1)).collect(Collectors.toList()));
    to.setFieldMap(from.getFieldMap().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> v1.getValue())));
    to.setFieldNestedMap(from.getFieldNestedMap().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> v1.getValue().entrySet().stream().collect(Collectors.toMap(k2 -> k2.getKey(), v2 -> v2.getValue())))));
    to.setFieldMapClass(from.getFieldMapClass().entrySet().stream().collect(Collectors.toMap(k1 -> k1.getKey(), v1 -> AllMatchBoTransformer.toAllMatchPojo(v1.getValue()))));
    // Fields only in AllMatchBo
    // Fields only in AllMatchPojo
    return to;
  }
}
