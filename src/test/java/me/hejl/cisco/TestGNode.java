package me.hejl.cisco;

public class TestGNode implements GNode {

    private final String name;

    private final GNode[] children;

    public TestGNode(String name) {
        this(name, new GNode[]{});
    }

    public TestGNode(String name, GNode[] children) {
        this.name = name;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public GNode[] getChildren() {
        // should be more defensive in production code; this is test only
        return children;
    }

    @Override
    public String toString() {
        return name;
    }
}
