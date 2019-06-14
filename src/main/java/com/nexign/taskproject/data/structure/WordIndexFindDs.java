package com.nexign.taskproject.data.structure;

import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author viga0114
 */
public interface WordIndexFindDs {
    void put(String key, Pair<Integer, Integer> value);
    List<Pair<Integer, Integer>> get(String key);
}
