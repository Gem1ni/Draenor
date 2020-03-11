package com.gem1ni.draenor.collections.uf;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class UFTest {

    @Test
    public void testPCWQU() {
        UnionFind uf = new PathCompressWeightedQuickUnionUFImpl(10);
        uf.union(0, 1);
        uf.union(3, 9);
        uf.union(4, 6);
        uf.union(2, 5);
        uf.union(5, 1);

        Assertions.assertFalse(uf.connected(5, 9));
    }
}
