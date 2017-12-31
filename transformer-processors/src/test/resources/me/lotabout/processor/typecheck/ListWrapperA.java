package me.lotabout.processor.typecheck;

import me.lotabout.annotation.Transformer;
import java.util.List;
import java.lang.Integer;

@Transformer(to = {ListWrapperB.class})
public class ListWrapperA {
    List<Integer> list;
    List<List<Integer>> nestedList;

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

    public List<List<Integer>> getNestedList() {
        return nestedList;
    }

    public void setNestedList(List<List<Integer>> nestedList) {
        this.nestedList = nestedList;
    }
}
