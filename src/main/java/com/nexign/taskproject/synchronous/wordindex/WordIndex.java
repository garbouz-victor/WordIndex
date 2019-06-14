package com.nexign.taskproject.synchronous.wordindex;

import com.nexign.taskproject.data.structure.TrieTree;
import com.nexign.taskproject.data.structure.WordIndexFindDs;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.util.Pair;

/**
 *
 * @author viga0114
 */
public class WordIndex {
    protected final String DEFAULT_ENCODING = "UTF-8";
    protected final String DEFAULT_DELIMETER = ";";
    protected final String delimeter;
    protected final WordIndexFindDs tree;
    protected final ArrayList<Integer> rowPosition = new ArrayList<>();
    {
        rowPosition.add(0); // we haven't 0 line
        rowPosition.add(1); // index of first line is 1
    }
    
    public WordIndex() {
        tree = new TrieTree();
        this.delimeter = DEFAULT_DELIMETER;
    }
    
    public WordIndex(WordIndexFindDs tree) {
        this.tree = tree;
        this.delimeter = DEFAULT_DELIMETER;
    }
    
    public WordIndex(WordIndexFindDs tree, String delimeter) {
        this.tree = tree;
        this.delimeter = delimeter;
    }
    
    public void loadFile(String fileName) {
        try (BufferedReader br
                = new BufferedReader(
                        new InputStreamReader(new FileInputStream(fileName),
                                DEFAULT_ENCODING))) {
            final AtomicInteger row = new AtomicInteger(0);
            br.lines().forEach((line) -> put(line, row.addAndGet(1)));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected void put(String str, int row) {
        int position = 1;
        String[] keys = str.split(DEFAULT_DELIMETER);
        for (int i = 0; i < keys.length; i++) {
            tree.put(keys[i], new Pair<>(row, position));
            position += keys[i].length() + 1;
        }
        rowPosition.add(position);
    }
    
    public Set<Integer> getIndexes4Word(String searchWord) {
        Set<Integer> setOfIndexes = new LinkedHashSet<>();
        List<Pair<Integer, Integer>> positions = tree.get(searchWord);
        if (positions != null) {
            positions.stream()
                    .forEach(index -> setOfIndexes.add(rowPosition.get(index.getKey()) + index.getValue()));
            return setOfIndexes;
        }
        return null;
    }
}
