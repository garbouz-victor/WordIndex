package com.nexign.taskproject.synchronous.wordindex;

import com.nexign.taskproject.data.structure.TrieTernaryTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author viga0114
 */
public class WordIndexTest {
    
    public WordIndexTest() {
    }

    @Test
    public void testGetIndexes4Word() {
        WordIndex indexer = new WordIndex();
        indexer.loadFile("src/main/resources/utf8text.txt");
        assertEquals(20, indexer.getIndexes4Word("just").toArray()[0]);
        assertNull(indexer.getIndexes4Word("null"));
    }
    
    @Test
    public void testGetIndexes4WordWithTernaryTree() {
        WordIndex indexer = new WordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/utf8text.txt");
        assertEquals(20, indexer.getIndexes4Word("just").toArray()[0]);
        assertNull(indexer.getIndexes4Word("null"));
    }
    
    @Test
    public void testGetIndexes4FordBigDcitionary() {
        WordIndex indexer = new WordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        assertNotNull(indexer.getIndexes4Word("winds"));
    }
    
    @Test
    public void testGetIndexes4FordBigDcitionaryNeg() {
        WordIndex indexer = new WordIndex(new TrieTernaryTree());
        indexer.loadFile("src/main/resources/outputUtf8_1.txt");
        assertNull(indexer.getIndexes4Word("abrakadabra"));
    }
    
}
