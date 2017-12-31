package me.lotabout.processor.typecheck;

import java.util.List;
import java.util.Map;
import java.lang.Integer;

public class MapWrapperValueB {
    Map<String, String> mapOk;
    Map<String, String> valueTypeMismatch;
    Map<String, List<String>> nestedOk;
    Map<String, List<Integer>> nestedMismatch;
}
