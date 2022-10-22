package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IDijkstra;
import src.Transport;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;

    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
            dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

// =============================================================================

    // TESTING KOREA GRAPH
    TravelController koreaControl;
    TravelGraph koreaGraph;

    /**
     * Setting up the controller for the Korea Graph using the imported CSV file
     */
    @Before
    public void setupKoreaControl() {
        this.koreaControl = new TravelController();
        this.koreaControl.load("sol/KRCity.csv", "sol/KRTransport.csv");
        this.koreaGraph = this.koreaControl.getGraph();
    }

    @Test
    public void testKoreaGraph() {
        this.setupKoreaControl();
        IDijkstra<City, Transport> koreaDijkstra = new Dijkstra<>();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getMinutes();
        List<Transport> path =
                koreaDijkstra.getShortestPath(this.koreaGraph, this.koreaGraph.getCity("Seoul"), this.koreaGraph.getCity("Jejudo"), edgeWeightCalculation);
        //assertEquals(6, koreaGraph.getShortestPath(path), DELTA);
        assertEquals(2, path.size());
    }

// -----------------------------------------------------------------------------
    // TESTING GET SHORTEST DURAITON:
    // just testing if our testing methods work...
    @Test
    public void k1() {
        System.out.println(
                this.koreaControl.fastestRoute("Busan", "Jejudo"));
        Assert.assertEquals(this.koreaControl.totalDuration(), 611., 1);
        System.out.println(this.koreaControl.totalDuration());

    }

    // makes sure that cost and duration are not conflated in a case where they are
    // independent of each other
    @Test
    public void k10() {
        Assert.assertEquals(this.koreaControl.fastestRoute("Ulsan", "Busan").size(), 1);
        Assert.assertEquals(this.koreaControl.totalDuration(), 1, 0);
    }

    // getting a duration that has many edges to traverse but overall low time
    @Test
    public void k11() {
        Assert.assertEquals(this.koreaControl.fastestRoute("Busan", "Daejeon").size(), 4);
        Assert.assertEquals(this.koreaControl.totalDuration(), 62, 0);
    }

// -----------------------------------------------------------------------------

    // TESTING GET CHEAPEST

    // if there are two routes of the same cost, the route that reaches the dest
    // first is chosen
    @Test
    public void k2() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Seoul", "Incheon").size(), 1 );
        Assert.assertEquals(this.koreaControl.totalCost(), 100., 0.1);
    }

    // tests that the cost to get from 1 place to itself is 0
    @Test
    public void k3() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Seoul", "Seoul").size(), 0);
        Assert.assertEquals(this.koreaControl.totalCost(), 0, 0.1);
    }

    // tests that the cost to get from 1 place to a non-existen place is 0
    @Test
    public void k4() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Seoul", "Tokyo").size(), 0);
        Assert.assertEquals(this.koreaControl.totalCost(), 0, 0.1);
    }

    // ensures that the cheaper of two possible routes is taken.
    @Test
    public void k5() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Seoul", "Jejudo").size(), 2);
        Assert.assertEquals(this.koreaControl.totalCost(), 110., 0.1);
    }

    // ensures that the cost between two cities that don't connect is 0
    @Test
    public void k6() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Jejudo", "Busan").size(), 0);
        Assert.assertEquals(this.koreaControl.totalCost(), 0, 0.);
    }

    // ensures that a cycle does not occur between two cities
    @Test
    public void k7() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Busan", "Ulsan").size(), 1);
        Assert.assertEquals(this.koreaControl.totalCost(), 5, 0.);
    }

    // testing a case where there's only 1 possible path but there are two
    // different modes of transportation: takes the cheaper one
    @Test
    public void k8() {
        LinkedList<Transport> path = new LinkedList<Transport>(this.koreaControl.cheapestRoute("Ulsan", "Incheon"));
        Assert.assertEquals(path.size(), 1);
        Assert.assertEquals(this.koreaControl.totalCost(), 8, 0);
    }

    // testing where ther's only 1 possible path and transport and its super
    // expensive
    @Test
    public void k9() {
        Assert.assertEquals(this.koreaControl.cheapestRoute("Busan", "Jejudo").size(), 3);
        Assert.assertEquals(this.koreaControl.totalCost(), 23, 0);
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
    // TESTING DURATION

    // given an only 1 way option, makes sure that the fastest between two modes
    // of transport is taken
    @Test
    public void t1() {
        Assert.assertEquals(this.tControl.fastestRoute("Houston", "Dallas").size(), 1);
        Assert.assertEquals(this.tControl.totalDuration(), 10, 0);

    }

    // given that there are two options 1
    @Test
    public void t2() {
        Assert.assertEquals(this.tControl.fastestRoute("Austin", "Dallas").size(), 2);
        Assert.assertEquals(this.tControl.totalDuration(), 11, 0);
    }

    // given that there are two options, 1 that has many edges, and 1 that doesnt
    // return the shorter even if its longer
    @Test
    public void t3() {
        Assert.assertEquals(this.tControl.fastestRoute("Austin", "Dallas").size(), 2);
        Assert.assertEquals(this.tControl.totalDuration(), 11, 0);
    }

    // the duration to get from a lone city to itself
    @Test
    public void t4() {
        Assert.assertEquals(this.tControl.fastestRoute("SA", "SA").size(), 0);
        Assert.assertEquals(this.tControl.totalDuration(), 0, 0);
    }

    // the duration to get from a lone city to somewhere else, even tho
    // its impossible
    @Test
    public void t5() {
        Assert.assertEquals(this.tControl.fastestRoute("SA", "Austin").size(), 0);
        Assert.assertEquals(this.tControl.totalDuration(), 0, 0);
    }

    // when there's only 1 option that has 2 edges, make sure u take the shortest
    // each time
    @Test
    public void t7() {
        Assert.assertEquals(this.tControl.fastestRoute("Dallas", "Houston").size(), 2);
        Assert.assertEquals(this.tControl.totalDuration(), 51, 0);
    }

    // trying to go from a city to a lone city
    @Test
    public void t8() {
        Assert.assertEquals(this.tControl.fastestRoute("Dallas", "SA").size(), 0);
        Assert.assertEquals(this.tControl.totalDuration(), 0, 0);
    }

// -----------------------------------------------------------------------------
    // TESTING CHEAPEST

    // make sure we don't conflate price with duration
    @Test
    public void t9() {
        Assert.assertEquals(this.tControl.cheapestRoute("Dallas", "SA").size(), 0);
        Assert.assertEquals(this.tControl.totalCost(), 0, 0);
    }
    
    @Test
    public void t10() {
        Assert.assertEquals(this.tControl.cheapestRoute("Dallas", "Austin").size(), 1);
        Assert.assertEquals(this.tControl.totalCost(), 20, 0);
    }



}
