package com.nexign.taskproject.asynchronous.wordindex;

import com.nexign.taskproject.data.structure.TrieTernaryTree;
import com.nexign.taskproject.synchronous.wordindex.WordIndex;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author viga0114
 */
public class AsyncWordIndexTest {
    
    public AsyncWordIndexTest() {
    }

    @Test
    public void testLoadFile() {
        AsyncWordIndex indexer = new AsyncWordIndex();
        indexer.loadFile("src/main/resources/utf8text.txt");
        assertEquals(20, indexer.getIndexes4Word("just").toArray()[0]);
        assertNull(indexer.getIndexes4Word("null"));
    }
    
    @Test
    public void testGetIndexes4WordWithTernaryTree() {
        AsyncWordIndex indexer = new AsyncWordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/utf8text.txt");
        assertEquals(20, indexer.getIndexes4Word("just").toArray()[0]);
        assertNull(indexer.getIndexes4Word("null"));
    }
    
    @Test
    public void testGetIndexes4FordBigDcitionary() {
        AsyncWordIndex indexer = new AsyncWordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        assertNotNull(indexer.getIndexes4Word("winds"));
    }
    
    @Test
    public void testGetIndexes4FordBigDcitionaryNeg() {
        AsyncWordIndex indexer = new AsyncWordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        assertNull(indexer.getIndexes4Word("abrakadabra"));
    }
    
}
