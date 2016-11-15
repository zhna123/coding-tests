package com.nz.java.problems;

/**
 * GNode interface to help define a graph
 */
public interface GNode {

    /**
     * Get the name of the GNode.
     *
     * @return the name of the GNode
     */
    public String getName();

    /**
     * Get all children of the GNode. When GNode has no children, this method returns a size 0 array.
     *
     * @return an array of non-null children GNode.
     */
    public GNode[] getChildren();
}
