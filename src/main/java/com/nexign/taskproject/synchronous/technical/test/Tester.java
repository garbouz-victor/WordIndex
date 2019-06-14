package com.nexign.taskproject.synchronous.technical.test;

import com.nexign.taskproject.asynchronous.wordindex.AsyncWordIndex;
import com.nexign.taskproject.data.structure.TrieTernaryTree;
import com.nexign.taskproject.synchronous.wordindex.WordIndex;

/**
 *
 * @author viga0114
 */
public class Tester {
    public static void main(String... args) {
        System.out.println("Test is starting");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            WordIndex indexer = new WordIndex();
            indexer.loadFile("src/main/resources/test.txt");
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("Loading 100 times took: " + (finishTime - startTime) + " ms for TrieTree.");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            WordIndex indexer = new WordIndex(new TrieTernaryTree());            
            indexer.loadFile("src/main/resources/test.txt");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Loading 100 times took: " + (finishTime - startTime) + " ms for TrieTernaryTree.");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            AsyncWordIndex indexer = new AsyncWordIndex(new TrieTernaryTree());            
            indexer.loadFile("src/main/resources/test.txt");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Loading 100 times took: " + (finishTime - startTime) + " ms for AsyncWordIndex.");       
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            WordIndex indexer = new WordIndex(new TrieTernaryTree());            
            indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Loading 1000 times large file took: " + (finishTime - startTime) + " ms for TrieTernaryTree.");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            AsyncWordIndex indexer = new AsyncWordIndex(new TrieTernaryTree());            
            indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Loading 1000 times large file took: " + (finishTime - startTime) + " ms for AsyncWordIndex.");
        WordIndex indexer = new WordIndex();
        indexer.loadFile("src/main/resources/test.txt");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            indexer.getIndexes4Word("neighbours");
            indexer.getIndexes4Word("abrakadabra");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Find operation took: " + (finishTime - startTime) + " ms.");
        indexer = new WordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/test.txt");
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            indexer.getIndexes4Word("neighbours");
            indexer.getIndexes4Word("abrakadabra");
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Find operation for TrieTernaryTree took: " + (finishTime - startTime) + " ms.");
    }
}
