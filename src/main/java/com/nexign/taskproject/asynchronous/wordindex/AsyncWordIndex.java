package com.nexign.taskproject.asynchronous.wordindex;

import com.nexign.taskproject.data.structure.WordIndexFindDs;
import com.nexign.taskproject.synchronous.wordindex.WordIndex;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.util.Pair;

/**
 *
 * @author viga0114
 */
public class AsyncWordIndex extends WordIndex{
    private BlockingQueue<Pair<String, Pair<Integer, Integer>>> words 
            = new LinkedBlockingQueue<>();
    private int threadNum = 3;
    private final String POISON = "STOP";
    public AsyncWordIndex() {
        super();
    }
    
    public AsyncWordIndex(WordIndexFindDs tree) {
        super(tree);
    }
    
    public AsyncWordIndex(
            WordIndexFindDs tree, 
            String delimeter, int threadNum) {
        super(tree, delimeter);
        this.threadNum = threadNum;
    }
    
    
    
    @Override
    public void loadFile(String fileName) {
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {                
                try (BufferedReader br
                        = new BufferedReader(
                                new InputStreamReader(new FileInputStream(fileName),
                                        DEFAULT_ENCODING))) {
                    final AtomicInteger row = new AtomicInteger(0);
                    br.lines().forEach((line) -> put(line, row.addAndGet(1)));
                    for (int i = 0; i < threadNum; i++) {
                        words.put(new Pair<>(POISON, null));
                    }
                } catch (IOException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        producer.start();
        ExecutorService ex = Executors.newCachedThreadPool();
        for (int i = 0; i < threadNum; i++) {
            ex.submit(new Runnable() {
                @Override
                public void run() {                  
                    while (producer.isAlive()
                            || !words.isEmpty()) {
                        try {
                            Pair<String, Pair<Integer, Integer>> word
                                    = words.take();
                            if (word.getKey().equals(POISON)) {
                                break;
                            }
                            synchronized (tree) {
                                tree.put(word.getKey(), word.getValue());
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });            
        }
        ex.shutdown();
        try {
            ex.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void put(String str, int row) {
        int position = 1;
        String[] keys = str.split(DEFAULT_DELIMETER);
        for (int i = 0; i < keys.length; i++) {
            try {
                words.put(new Pair(keys[i], new Pair<>(row, position)));
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            position += keys[i].length() + 1;
        }
        rowPosition.add(position);
    }
}
