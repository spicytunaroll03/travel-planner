package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.BFS;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

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

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

// =============================================================================

    // TESTING KOREA GRAPH
    private TravelController koreaControl;
    private TravelGraph koreaGraph;

    // SETUP
    @Before
    public void setUpKoreaControl() {
        this.koreaControl = new TravelController();
        this.koreaControl.load("sol/KRCity.csv", "sol/KRTransport.csv");
        this.koreaGraph = this.koreaControl.getGraph();
    }

    @Test
    public void testKoreaBFS() {
        this.setUpKoreaControl();
        BFS<City, Transport> bfs = new BFS<>();

        List<Transport> seoulToIncheonPath = this.koreaControl.mostDirectRoute("Seoul", "Incheon");
        Assert.assertEquals(seoulToIncheonPath.size(), 1);
        Assert.assertEquals(seoulToIncheonPath.get(0).getTarget(), this.koreaGraph.getCity("Incheon"));

        // Tests that BFS findPath() returns an empty list when the source and destination are the same city
        List<Transport> seoulToSeoulPath = this.koreaControl.mostDirectRoute("Seoul", "Seoul");
        Assert.assertTrue(seoulToSeoulPath.isEmpty());

        // Tests that BFS correctly finds the most direct path from Seoul to Ulsan
        List<Transport> seoulToUlsanPath = this.koreaControl.mostDirectRoute("Seoul", "Ulsan");
        Assert.assertEquals(seoulToUlsanPath.size(), 2);
        Assert.assertEquals(seoulToUlsanPath.get(0).getTarget(), this.koreaGraph.getCity("Busan"));
        Assert.assertEquals(seoulToUlsanPath.get(1).getTarget(), this.koreaGraph.getCity("Ulsan"));

        // Tests that BFS catches the NullPointerException when a user inputs a city that is not in the graph, and
        // returns an empty list
        List<Transport> jejudoToTokyo = this.koreaControl.mostDirectRoute("Seoul", "Tokyo");
        Assert.assertTrue(jejudoToTokyo.isEmpty());
    }

// -----------------------------------------------------------------------------
    // TESTING FUNCTIONANILTY

    // makes sure that ulsan to busan goes through the option that has a set
    // with 1 edge rather than a set with 3 edges.
    @Test
    public void testKBFS1 () {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Ulsan", "Busan").size(), 1 );
    }

    // makes sure that a city leads back to itself
    @Test
    public void testKBFS2() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Ulsan", "Ulsan").size(), 0 );
    }

    // tests the path between two cities when there is only 1 path to a city
    @Test
    public void testKBFS3() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Seoul", "Busan").size(), 1 );
    }

    // tests that a city that has no outgoing outputs an empty set of edges
    @Test
    public void testKBFS4() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Jejudo", "Busan").size(), 0 );
    }

    // tests for another path between two cities that have 1 short and 1 long path
    @Test
    public void testKBFS5() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Seoul", "Incheon").size(), 1 );
    }

    // tests for if there are two possible paths that are the same length
    @Test
    public void testKBFS6() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Seoul", "Jejudo").size(), 2 );
    }

    // tests for getting the path to a city that doesn't exist
    @Test
    public void testKBFS7() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Seoul", "Boston").size(), 0);
    }

    // tests for getting the path to a city when there are 3 options available
    @Test
    public void testKBFS8() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Daejeon", "Jejudo").size(), 2 );
    }

    // TESTING THAT THE ACTUAL PATH IS WHAT IS EXPECTED:

    // making sure that the path from seoul to ulsan actally goes to ulsan
    @Test
    public void testKBFS9() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Ulsan", "Busan").get(0).getTarget(),
                this.koreaControl.getGraph().getCity("Busan"));
    }

    // for a path with 2 of the same, making sure that we take the path
    // that goes through the first neighboring city.
    @Test
    public void testKBFS10() {
        Assert.assertEquals(this.koreaControl.mostDirectRoute("Seoul", "Jejudo").get(0).getTarget(),
                this.koreaControl.getGraph().getCity("Busan"));
    }

// =============================================================================
// SETTING UP TEXAS GRAPH

    private TravelController tControl;
    private TravelGraph tGraph;

    @Before
    public void setUpTexasControl() {
        this.tControl = new TravelController();
        this.tControl.load("sol/TexasCities.csv", "sol/TexasTransport.csv");
        this.tGraph = this.tControl.getGraph();
    }

// -----------------------------------------------------------------------------

    // testing that we get the path when there is only 1 path existing
    @Test
    public void t1() {
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Dallas").size(), 1);
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Dallas").get(0).getTarget(),
                this.tGraph.getCity("Dallas"));
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Dallas").get(0).getSource(),
                this.tGraph.getCity("Houston"));

    }

    // testing that the path returns, no matter how long it is
    @Test
    public void t2() {
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Austin").size(), 2);
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Austin").get(0).getTarget(),
                this.tGraph.getCity("Dallas"));
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "Austin").get(1).getTarget(),
                this.tGraph.getCity("Austin"));

    }

    // testing that an isolated city can go to itself
    @Test
    public void t3() {
        Assert.assertEquals(this.tControl.mostDirectRoute("SA", "SA").size(), 0);
    }

    // testing that an isolated city cannot go anywhere
    @Test
    public void t4() {
        Assert.assertEquals(this.tControl.mostDirectRoute("SA", "Houston").size(), 0);
    }

    // Testing that nothing can go to an isolated city
    @Test
    public void t5() {
        Assert.assertEquals(this.tControl.mostDirectRoute("Houston", "SA").size(), 0);
    }

    // Testing that we get the shortest path if there are two options
    @Test
    public void t6() {
        Assert.assertEquals(this.tControl.mostDirectRoute("Austin", "Dallas").size(), 1);
        Assert.assertEquals(this.tControl.mostDirectRoute("Austin", "Dallas").get(0).getTarget(),
                this.tGraph.getCity("Dallas"));
        Assert.assertEquals(this.tControl.mostDirectRoute("Austin", "Dallas").get(0).getSource(),
                this.tGraph.getCity("Austin"));

    }

}
