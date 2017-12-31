package me.lotabout.processor.typecheck;

import me.lotabout.annotation.Transformer;
import java.util.List;
import java.util.Map;
import java.lang.Integer;

@Transformer(to = {MapWrapperKeyB.class})
public class MapWrapperKeyA {
    Map<String, Integer> map;
    Map<List<String>, Integer> mapWithStrangeKey;
}
