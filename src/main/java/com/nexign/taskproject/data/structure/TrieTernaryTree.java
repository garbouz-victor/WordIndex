package com.nexign.taskproject.data.structure;

import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

/**
 *
 * @author viga0114
 */
public class TrieTernaryTree implements WordIndexFindDs {
    
    private Node root;    
    
    private class Node {
        char c;
        Node left, mid, right;
        List<Pair<Integer, Integer>> position = new LinkedList<>();
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
        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d+1);
        } else {
            return x;
        }
    }
    
    @Override
    public void put(String key, Pair<Integer, Integer> value) {
        root = put(root, key, value, 0);
    }
    
    private Node put(Node x, String key, Pair<Integer, Integer> value, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, value, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, value, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, value, d + 1);
        } else {
            x.position.add(value);
        }
        return x;
    }
}
