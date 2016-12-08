package me.hejl.cisco;


/**
 * Node defining graph.
 *
 * @author Petr Hejl
 */
public interface GNode {

    /**
     * Returns the name of the node.
     *
     * @return name of the node
     */
    String getName();

    /**
     * Return node's children.
     *
     * @return node's children; never <code>null</code>
     */
    GNode[] getChildren();

}
