package me.lotabout.processor.typecheck;

import java.util.List;
import java.lang.String;

public class ListWrapperB {
    List<String> list;
    List<List<String>> nestedList;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public List<List<String>> getNestedList() {
        return nestedList;
    }

    public void setNestedList(List<List<String>> nestedList) {
        this.nestedList = nestedList;
    }
}
