package me.hejl.cisco;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GNodeOperationsTest extends TestCase {

    private static final GNode J = new TestGNode("J");

    private static final GNode D = new TestGNode("D", new GNode[] {J});

    private static final GNode I = new TestGNode("I");

    private static final GNode H = new TestGNode("H");

    private static final GNode G = new TestGNode("G");

    private static final GNode C = new TestGNode("C", new GNode[] {G, H, I});

    private static final GNode F = new TestGNode("F");

    private static final GNode E = new TestGNode("E");

    private static final GNode B = new TestGNode("B", new GNode[] {E, F});

    private static final GNode A = new TestGNode("A", new GNode[] {B, C, D});

    private static final GNode Z = new TestGNode("Z");

    public GNodeOperationsTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(GNodeOperationsTest.class);
    }

    public void testWalkGraph() {
        GNodeOperations operations = new GNodeOperations();
        ArrayList result = operations.walkGraph(A);
        // assignment does not assume any particular order of the nodes in the result
        Set<GNode> expected = new HashSet<>();
        Collections.addAll(expected, A, B, C, D, E, F, G, H, I, J);
        for (Object item : result) {
            GNode node = (GNode) item;
            assertTrue("walkGraph result contains node outside of the tree", expected.remove(node));
        }
        assertTrue("walkGraph does not walk through all nodes", expected.isEmpty());
    }

    public void testWalkNode() {
        GNodeOperations operations = new GNodeOperations();
        ArrayList result = operations.walkGraph(Z);
        assertEquals(1, result.size());
        assertEquals(Z, result.iterator().next());
    }

    public void testPaths() {
        GNodeOperations operations = new GNodeOperations();
        ArrayList result = operations.paths(A);

        // assignment does not assume any particular order of the paths in the result
        ArrayList<List<GNode>> expected = new ArrayList<>();
        Collections.addAll(expected, Arrays.asList(A, B, E), Arrays.asList(A, B, F), Arrays.asList(A, C, G),
                Arrays.asList(A, C, H), Arrays.asList(A, C, I), Arrays.asList(A, D, J));

        for (Object item : result) {
            ArrayList path = (ArrayList) item;
            boolean found = false;
            for (Iterator<List<GNode>> testIt = expected.iterator(); testIt.hasNext(); ) {
                if (isSamePath(path, testIt.next())) {
                    testIt.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                fail("paths returned nonexisting path " + path);
            }
        }

        assertTrue("paths failed to find " + expected, expected.isEmpty());
    }

    private boolean isSamePath(List path, List expected) {
        if (path.size() != expected.size()) {
            return false;
        }

        for (int i = 0; i < expected.size(); i++) {
            if (path.get(i) != expected.get(i)) {
                return false;
            }
        }
        return true;
    }
}
