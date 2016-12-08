package me.hejl.cisco;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class providing various operations on {@link GNode}.
 *
 * @author Petr Hejl
 */
public final class GNodeOperations {

    /**
     * Walks through the graph represented by the passed root node
     * returning the list of all nodes in the graph. <i>Graph
     * represented by the node has to be acyclic.</i>.
     *
     * @param node the root node of the graph
     * @return list of all nodes in the graph; never <code>null</code>
     */
    public ArrayList walkGraph(GNode node) {
        if (node == null) {
            throw new NullPointerException("node must not be null");
        }
        ArrayList<GNode> result = new ArrayList<>();
        walkGraph(node, result);
        return result;
    }

    /**
     * Computes paths from the root of the graph represented by the passed
     * node to its leafs.
     *
     * @param node the root node of the graph
     * @return list of paths where each path is represented as a list of nodes;
     *             never <code>null</code>
     */
    public ArrayList paths(GNode node) {
        if (node == null) {
            throw new NullPointerException("node must not be null");
        }

        ArrayList<ArrayList<GNode>> result = new ArrayList<>();
        LinkedList<GNode> prefix = new LinkedList<>();
        prefix.add(node);

        paths(node, prefix, result);
        return result;
    }

    private void walkGraph(GNode node, ArrayList<GNode> result) {
        result.add(node);
        for (GNode child : node.getChildren()) {
            walkGraph(child, result);
        }
    }

    private void paths(GNode node, LinkedList<GNode> prefix, ArrayList<ArrayList<GNode>> result) {
        GNode[] children = node.getChildren();
        if (children.length > 0) {
            for (GNode child : children) {
                prefix.add(child);
                paths(child, prefix, result);
                prefix.removeLast();
            }
        } else {
            ArrayList<GNode> path = new ArrayList<>(prefix);
            result.add(path);
        }
    }
}
