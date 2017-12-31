package me.lotabout.processor.typecheck;

import me.lotabout.annotation.Transformer;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

@Transformer(to = {MapWrapperValueB.class})
public class MapWrapperValueA {
    Map<String, String> mapOk;
    Map<String, Integer> valueTypeMismatch;
    Map<String, List<String>> nestedOk;
    Map<String, List<String>> nestedMismatch;
}
