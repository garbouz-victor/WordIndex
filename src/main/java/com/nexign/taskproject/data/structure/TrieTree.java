package com.nexign.taskproject.data.structure;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author viga0114
 */
public class TrieTree implements WordIndexFindDs{
    private static final int R = Character.MAX_VALUE;
    private Node root;
    
    private class Node {
        private List<Pair<Integer, Integer>> position = new LinkedList<>();
        private Node[] next = new Node[R];
    }
    
    @Override
    public List<Pair<Integer, Integer>> get(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return null;
        }
        return x.position;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }
    
    @Override
    public void put(String key, Pair<Integer, Integer> value) {
        root = put(root, key, value, 0);
    }
    
    private Node put(Node x, String key, Pair<Integer, Integer> value, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.position.add(value);
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c], key, value, d + 1);
        return x;
    }
}
