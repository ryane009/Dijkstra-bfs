package test;

import org.junit.Assert;
import org.junit.Test;
import sol.BFS;
import sol.Transport;
import sol.TravelController;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;

    private String citiesFilepath = "data/testcities.csv";
    private String transportFilepath = "data/testtransport.csv";

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    /**
     * A sample test that tests BFS on a simple graph. Checks that running BFS gives us the path we expect.
     */
    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * Testing BFS for finding the correct most direct route,
     * as well as the edge cases:
     * if we make the destination the same as the origin
     * if we make the destination an unreachable location
     */
    @Test
    public void testBFS(){
        TravelController controller = new TravelController();
        controller.load(this.citiesFilepath, this.transportFilepath);

        List<Transport> transportList = controller.mostDirectRoute("Boston", "New York");
        double cost = 0;
        for(Transport transport : transportList){
            cost += transport.getPrice();
        }
        Assert.assertEquals(80, cost, DELTA);

        List<Transport> transportListTwo = controller.mostDirectRoute("Boston", "Boston");
        Assert.assertEquals(new LinkedList<Transport>(), transportListTwo);

        List<Transport> transportListThree = controller.mostDirectRoute("Los Angeles", "Boston");
        Assert.assertEquals(new LinkedList<Transport>(), transportListThree);

    }

    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
