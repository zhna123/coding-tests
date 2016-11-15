package com.nz.java.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Graph class to define graph operation functions.
 */
public class Graph {
    private final int numberOfNodes;

    /**
     * Constructor
     *
     * @param numberOfNodes the total number of nodes of the graph
     */
    Graph(final int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
    }

    /**
     * Find all the GNode in the graph. No duplicates.
     *
     * @param node a GNode of the graph
     * @return an ArrayList containing every GNode in the graph. Return an empty list if give GNode is null.
     */
    public List<GNode> walkThrough(final GNode node) {
        if (node == null) {
            return Collections.emptyList();
        }
        // list to check visited nodes as well as keeping results
        final List<GNode> visited = new ArrayList<GNode>(numberOfNodes);

        // 3 Approaches:
        // 1) DFS traversal nodes (recursion approach)
        return DFS(node, visited);
        // 2) DFS2 used stack approach
        //  return DFS2(node, visited);
        // 3) BFS traversal nodes using a queue
        //  return BFS(node, visited);
    }


    /**
     * Find all possible paths through the graph starting at a given node.
     *
     * @param node starting GNode
     * @return an ArrayList of GNode ArrayLists of paths. Return an empty list if give GNode is null.
     */
    public ArrayList<ArrayList<GNode>> paths(final GNode node) {
        if (node == null) {
            final List<GNode> emptyList = Collections.emptyList();
            return new ArrayList<ArrayList<GNode>>(new ArrayList(emptyList));
        }
        //created visited list to store all the visited nodes
        final List<GNode> visited = new ArrayList<GNode>(numberOfNodes);
        // list to store final results
        final ArrayList<ArrayList<GNode>> pathsList = new ArrayList<ArrayList<GNode>>();

        // use DFS with stack to get paths
        final Stack<GNode> stack = new Stack<GNode>();
        // currentPath list is used to record stack elements status
        final ArrayList<GNode> currentPath = new ArrayList<GNode>();
        stack.push(node);
        visited.add(node);
        currentPath.add(node);
        while (!stack.isEmpty()) {
            final GNode n = stack.peek();
            final GNode child = getNextUnvisitedChild(n, visited);
            if (child != null) {
                visited.add(child);
                stack.push(child);
                currentPath.add(child);
            } else {
                // this step/flag is used to prevent including sub-path of the main path
                boolean isSubPath = false;
                for (ArrayList<GNode> path : pathsList) {
                    if (path.containsAll(currentPath)) {
                        isSubPath = true;
                        break;
                    }
                }
                if (!isSubPath) {
                    pathsList.add(new ArrayList<GNode>(currentPath));
                }
                currentPath.remove(stack.pop());
            }
        }
        return pathsList;
    }

    /********************** helper methods below **************************************************/

    /**
     * DFS - recursive
     */
    private List<GNode> DFS(final GNode node, final List<GNode> visited) {
        visited.add(node);
        final GNode[] childrenNodes = node.getChildren();
        for (int i = 0; i < childrenNodes.length; i++) {
            if (!visited.contains(childrenNodes[i])) {
                DFS(childrenNodes[i], visited);
            }
        }
        return visited;
    }

    /**
     * DFS - using stack
     */
    private List<GNode> DFS2(final GNode node, final List<GNode> visited) {
        final Stack<GNode> stack = new Stack<GNode>();
        stack.push(node);
        visited.add(node);
        while (!stack.isEmpty()) {
            GNode n = stack.peek();
            GNode child = getNextUnvisitedChild(n, visited);
            if (child != null) {
                visited.add(child);
                stack.push(child);
            } else {
                stack.pop();
            }
        }
        return visited;
    }

    /**
     * BFS - using queue
     */
    private List<GNode> BFS(final GNode node, final List<GNode> visited) {
        final Queue<GNode> queue = new LinkedList<GNode>();
        queue.add(node);
        visited.add(node);
        while (!queue.isEmpty()) {
            final GNode n = queue.peek();
            final GNode child = getNextUnvisitedChild(n, visited);
            if (child != null) {
                visited.add(child);
                queue.add(child);
            } else {
                // remove head of the queue
                queue.poll();
            }
        }
        return visited;
    }

    /**
     * Get next unvisited child node of the given GNode
     */
    private GNode getNextUnvisitedChild(final GNode node, final List<GNode> visited) {
        final GNode[] childrenNodes = node.getChildren();
        for (int i = 0; i < childrenNodes.length; i++) {
            if (!visited.contains(childrenNodes[i])) {
                return childrenNodes[i];
            }
        }
        return null;
    }
}
