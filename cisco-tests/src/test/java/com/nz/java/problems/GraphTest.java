package com.nz.java.problems;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * Test for {@link Graph}
 */
public class GraphTest {

    // ******************************* tests for walkThrough method ******************************

    /**
     * Test for {@link Graph#walkThrough(GNode)} when the graph is empty
     */
    @Test
    public void testWalkThrough_EmptyGraph() {

        final Graph g = new Graph(0);
        Assert.assertTrue(g.walkThrough(null).isEmpty());
    }

    /**
     * Test for {@link Graph#walkThrough(GNode)} when the given GNode is null
     */
    @Test
    public void testWalkThrough_NullNode() {

        final GNode node = null;
        final Graph g = new Graph(1);

        Assert.assertTrue(g.walkThrough(node).isEmpty());
    }

    /**
     * Test for {@link Graph#walkThrough(GNode)} when there is only 1 node
     *
     * Graph representation:
     * <p>
     * A
     */
    @Test
    public void testWalkThrough_OneNode() {

        final GNode node = createMockLeafNodes("A");

        final List<GNode> expectedList = new ArrayList<GNode>(1);
        expectedList.add(node);

        final Graph g = new Graph(1);

        final List<GNode> actualList = g.walkThrough(node);
        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#walkThrough(GNode)} when there are 2 nodes
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * B
     * - A
     */
    @Test
    public void testWalkThrough_TwoNodes() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeA = createMockNodeWithName("A");
        final GNode[] childrenNodesForA = new GNode[]{nodeB};
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        final List<GNode> expectedList = new ArrayList<GNode>(2);
        expectedList.add(nodeA);
        expectedList.add(nodeB);

        final Graph g = new Graph(2);

        final List<GNode> actualList = g.walkThrough(nodeA);
        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#walkThrough(GNode)} when there are 3 nodes (can traverse in one single path)
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * - C
     * B
     * - A
     * C
     * - A
     */
    @Test
    public void testWalkThrough_ThreeNodes() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeC = createMockNodeWithName("C");
        final GNode[] childrenNodesForA = new GNode[]{nodeB, nodeC};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode[] childrenNodesForC = new GNode[]{nodeA};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        final List<GNode> expectedList = new ArrayList<GNode>(3);
        expectedList.add(nodeA);
        expectedList.add(nodeB);
        expectedList.add(nodeC);

        final Graph g = new Graph(3);

        final List<GNode> actualList = g.walkThrough(nodeB);
        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#walkThrough(GNode)} when there are multiple nodes (cannot traverse in one single path)
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * - C
     * - J
     * B
     * - A
     * C
     * - A
     * - E
     * D
     * - E
     * - F
     * E
     * - D
     * - C
     * F
     * - D
     * J
     * - A
     */
    @Test
    public void testWalkThrough_ManyNodes() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeC = createMockNodeWithName("C");
        final GNode nodeJ = createMockNodeWithName("J");
        final GNode[] childrenNodesForA = new GNode[]{nodeB, nodeC, nodeJ};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode nodeE = createMockNodeWithName("E");
        final GNode[] childrenNodesForC = new GNode[]{nodeA, nodeE};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        // node D
        final GNode nodeF = createMockNodeWithName("F");
        final GNode[] childrenNodesForD = new GNode[]{nodeE, nodeF};
        final GNode nodeD = createMockNodeWithName("D");
        Mockito.when(nodeD.getChildren()).thenReturn(childrenNodesForD);

        // node E
        final GNode[] childrenNodesForE = new GNode[]{nodeD, nodeC};
        Mockito.when(nodeE.getChildren()).thenReturn(childrenNodesForE);

        // node F
        final GNode[] childrenNodesForF = new GNode[]{nodeD};
        Mockito.when(nodeF.getChildren()).thenReturn(childrenNodesForF);

        // node J
        final GNode[] childrenNodesForJ = new GNode[]{nodeA};
        Mockito.when(nodeJ.getChildren()).thenReturn(childrenNodesForJ);

        final List<GNode> expectedList = new ArrayList<GNode>(7);
        expectedList.add(nodeA);
        expectedList.add(nodeB);
        expectedList.add(nodeC);
        expectedList.add(nodeD);
        expectedList.add(nodeE);
        expectedList.add(nodeF);
        expectedList.add(nodeJ);

        final Graph g = new Graph(7);

        final List<GNode> actualList = g.walkThrough(nodeJ);
        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    // ***************************************** tests for paths method ************************************

    /**
     * Test for {@link Graph#paths(GNode)} when the graph is empty
     */
    @Test
    public void testPaths_EmptyGraph() {

        final Graph g = new Graph(0);
        Assert.assertTrue(g.paths(null).isEmpty());
    }

    /**
     * Test for {@link Graph#paths(GNode)}  when the given GNode is null
     */
    @Test
    public void testPaths_NullNode() {

        final GNode node = null;
        final Graph g = new Graph(1);

        Assert.assertTrue(g.walkThrough(node).isEmpty());
    }

    /**
     * Test for {@link Graph#paths(GNode)}  when there is only 1 node
     *
     * Graph representation:
     * <p>
     * A
     */
    @Test
    public void testPaths_OneNode() {

        final GNode node = createMockLeafNodes("A");

        final Graph g = new Graph(1);
        final ArrayList<ArrayList<GNode>> actualList = g.paths(node);

        final ArrayList<GNode> path1 = new ArrayList<GNode>(1);
        path1.add(node);
        final ArrayList<ArrayList<GNode>> expectedList = new ArrayList<ArrayList<GNode>>(1);
        expectedList.add(path1);

        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#paths(GNode)}  when there are 2 nodes
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * B
     * - A
     */
    @Test
    public void testPaths_TwoNodes() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeA = createMockNodeWithName("A");
        final GNode[] childrenNodesForA = new GNode[]{nodeB};
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        final Graph g = new Graph(2);
        // start A
        final ArrayList<ArrayList<GNode>> actualListA = g.paths(nodeA);

        final ArrayList<GNode> pathA = new ArrayList<GNode>(2);
        pathA.add(nodeA);
        pathA.add(nodeB);
        final ArrayList<ArrayList<GNode>> expectedListA = new ArrayList<ArrayList<GNode>>(1);
        expectedListA.add(pathA);

        Assert.assertEquals(expectedListA.size(), actualListA.size());
        Assert.assertTrue(expectedListA.containsAll(actualListA));
        Assert.assertTrue(actualListA.containsAll(expectedListA));

        // start B
        final ArrayList<ArrayList<GNode>> actualListB = g.paths(nodeB);

        final ArrayList<GNode> pathB = new ArrayList<GNode>(2);
        pathB.add(nodeB);
        pathB.add(nodeA);
        final ArrayList<ArrayList<GNode>> expectedListB = new ArrayList<ArrayList<GNode>>(1);
        expectedListB.add(pathB);

        Assert.assertEquals(expectedListB.size(), actualListB.size());
        Assert.assertTrue(expectedListB.containsAll(actualListB));
        Assert.assertTrue(actualListB.containsAll(expectedListB));
    }


    /**
     * Test for {@link Graph#paths(GNode)}
     *
     * Graph representation:
     * <p>
     *     * Graph representation:
     * <p>
     * A
     * - B
     * B
     * - A
     * - C
     * C
     * - B
     * - D
     * D
     * - C
     */
    @Test
    public void testPaths_StartA() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode[] childrenNodesForA = new GNode[]{nodeB};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode nodeC = createMockNodeWithName("C");
        final GNode[] childrenNodesForB = new GNode[]{nodeA, nodeC};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode nodeD = createMockNodeWithName("D");
        final GNode[] childrenNodesForC = new GNode[]{nodeB, nodeD};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        // node D
        final GNode[] childrenNodesForD = new GNode[]{nodeC};
        Mockito.when(nodeD.getChildren()).thenReturn(childrenNodesForD);

        final Graph g = new Graph(4);
        final ArrayList<ArrayList<GNode>> actualList = g.paths(nodeA);
        final ArrayList<GNode> path1 = new ArrayList<GNode>(4);
        path1.add(nodeA);
        path1.add(nodeB);
        path1.add(nodeC);
        path1.add(nodeD);
        final ArrayList<ArrayList<GNode>> expectedList = new ArrayList<ArrayList<GNode>>(1);
        expectedList.add(path1);

        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#paths(GNode)}
     *
     * Graph representation:
     * <p>
     *     * Graph representation:
     * <p>
     * A
     * - B
     * B
     * - A
     * - C
     * C
     * - B
     * - D
     * D
     * - C
     */
    @Test
    public void testPaths_StartB() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode[] childrenNodesForA = new GNode[]{nodeB};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode nodeC = createMockNodeWithName("C");
        final GNode[] childrenNodesForB = new GNode[]{nodeA, nodeC};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode nodeD = createMockNodeWithName("D");
        final GNode[] childrenNodesForC = new GNode[]{nodeB, nodeD};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        // node D
        final GNode[] childrenNodesForD = new GNode[]{nodeC};
        Mockito.when(nodeD.getChildren()).thenReturn(childrenNodesForD);

        final Graph g = new Graph(4);
        final ArrayList<ArrayList<GNode>> actualList = g.paths(nodeB);
        final ArrayList<GNode> path1 = new ArrayList<GNode>(2);
        path1.add(nodeB);
        path1.add(nodeA);
        final ArrayList<GNode> path2 = new ArrayList<GNode>(3);
        path2.add(nodeB);
        path2.add(nodeC);
        path2.add(nodeD);
        final ArrayList<ArrayList<GNode>> expectedList = new ArrayList<ArrayList<GNode>>(2);
        expectedList.add(path1);
        expectedList.add(path2);

        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#paths(GNode)} when start from A
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * - C
     * - J
     * B
     * - A
     * C
     * - A
     * - E
     * D
     * - E
     * - F
     * E
     * - D
     * - C
     * F
     * - D
     * J
     * - A
     */
    @Test
    public void testPaths_ManyNodes_StartA() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeC = createMockNodeWithName("C");
        final GNode nodeJ = createMockNodeWithName("J");
        final GNode[] childrenNodesForA = new GNode[]{nodeB, nodeC, nodeJ};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode nodeE = createMockNodeWithName("E");
        final GNode[] childrenNodesForC = new GNode[]{nodeA, nodeE};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        // node D
        final GNode nodeF = createMockNodeWithName("F");
        final GNode[] childrenNodesForD = new GNode[]{nodeE, nodeF};
        final GNode nodeD = createMockNodeWithName("D");
        Mockito.when(nodeD.getChildren()).thenReturn(childrenNodesForD);

        // node E
        final GNode[] childrenNodesForE = new GNode[]{nodeD, nodeC};
        Mockito.when(nodeE.getChildren()).thenReturn(childrenNodesForE);

        // node F
        final GNode[] childrenNodesForF = new GNode[]{nodeD};
        Mockito.when(nodeF.getChildren()).thenReturn(childrenNodesForF);

        // node J
        final GNode[] childrenNodesForJ = new GNode[]{nodeA};
        Mockito.when(nodeJ.getChildren()).thenReturn(childrenNodesForJ);

        final Graph g = new Graph(7);

        final ArrayList<ArrayList<GNode>> actualList = g.paths(nodeA);
        final ArrayList<GNode> path1 = new ArrayList<GNode>(2);
        path1.add(nodeA);
        path1.add(nodeJ);
        final ArrayList<GNode> path2 = new ArrayList<GNode>(2);
        path2.add(nodeA);
        path2.add(nodeB);
        final ArrayList<GNode> path3 = new ArrayList<GNode>(5);
        path3.add(nodeA);
        path3.add(nodeC);
        path3.add(nodeE);
        path3.add(nodeD);
        path3.add(nodeF);

        final ArrayList<ArrayList<GNode>> expectedList = new ArrayList<ArrayList<GNode>>(3);
        expectedList.add(path1);
        expectedList.add(path2);
        expectedList.add(path3);

        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    /**
     * Test for {@link Graph#paths(GNode)} when start from C
     *
     * Graph representation:
     * <p>
     * A
     * - B
     * - C
     * - J
     * B
     * - A
     * C
     * - A
     * - E
     * D
     * - E
     * - F
     * E
     * - D
     * - C
     * F
     * - D
     * J
     * - A
     */
    @Test
    public void testPaths_ManyNodes_StartC() {

        // node A
        final GNode nodeB = createMockNodeWithName("B");
        final GNode nodeC = createMockNodeWithName("C");
        final GNode nodeJ = createMockNodeWithName("J");
        final GNode[] childrenNodesForA = new GNode[]{nodeB, nodeC, nodeJ};
        final GNode nodeA = createMockNodeWithName("A");
        Mockito.when(nodeA.getChildren()).thenReturn(childrenNodesForA);

        // node B
        final GNode[] childrenNodesForB = new GNode[]{nodeA};
        Mockito.when(nodeB.getChildren()).thenReturn(childrenNodesForB);

        // node C
        final GNode nodeE = createMockNodeWithName("E");
        final GNode[] childrenNodesForC = new GNode[]{nodeA, nodeE};
        Mockito.when(nodeC.getChildren()).thenReturn(childrenNodesForC);

        // node D
        final GNode nodeF = createMockNodeWithName("F");
        final GNode[] childrenNodesForD = new GNode[]{nodeE, nodeF};
        final GNode nodeD = createMockNodeWithName("D");
        Mockito.when(nodeD.getChildren()).thenReturn(childrenNodesForD);

        // node E
        final GNode[] childrenNodesForE = new GNode[]{nodeD, nodeC};
        Mockito.when(nodeE.getChildren()).thenReturn(childrenNodesForE);

        // node F
        final GNode[] childrenNodesForF = new GNode[]{nodeD};
        Mockito.when(nodeF.getChildren()).thenReturn(childrenNodesForF);

        // node J
        final GNode[] childrenNodesForJ = new GNode[]{nodeA};
        Mockito.when(nodeJ.getChildren()).thenReturn(childrenNodesForJ);

        final Graph g = new Graph(7);

        final ArrayList<ArrayList<GNode>> actualList = g.paths(nodeC);
        final ArrayList<GNode> path1 = new ArrayList<GNode>(3);
        path1.add(nodeC);
        path1.add(nodeA);
        path1.add(nodeJ);
        final ArrayList<GNode> path2 = new ArrayList<GNode>(3);
        path2.add(nodeC);
        path2.add(nodeA);
        path2.add(nodeB);
        final ArrayList<GNode> path3 = new ArrayList<GNode>(4);
        path3.add(nodeC);
        path3.add(nodeE);
        path3.add(nodeD);
        path3.add(nodeF);

        final ArrayList<ArrayList<GNode>> expectedList = new ArrayList<ArrayList<GNode>>(3);
        expectedList.add(path1);
        expectedList.add(path2);
        expectedList.add(path3);

        Assert.assertEquals(expectedList.size(), actualList.size());
        Assert.assertTrue(expectedList.containsAll(actualList));
        Assert.assertTrue(actualList.containsAll(expectedList));
    }

    private GNode createMockLeafNodes(String name) {
        final GNode node = Mockito.mock(GNode.class);
        Mockito.when(node.getName()).thenReturn(name);
        Mockito.when(node.getChildren()).thenReturn(new GNode[]{});
        return node;
    }

    private GNode createMockNodeWithName(String name) {
        final GNode node = Mockito.mock(GNode.class);
        Mockito.when(node.getName()).thenReturn(name);
        return node;
    }
}
