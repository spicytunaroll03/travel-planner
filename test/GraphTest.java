package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sol.TravelGraph;

import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import src.City;
import src.Transport;
import sol.TravelController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;

    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();

        System.out.println(graph);
        //System.out.println(graph.getVertices());
        //System.out.println(graph.getEdgeSource(this.edgeAB));
        //System.out.println(graph.getEdgeTarget(this.edgeAB));
        System.out.println(graph.getOutgoingEdges(this.a));
        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

// =============================================================================
    // SELF LOADED EASY GRAPH
    TravelGraph newEngland;

    City BOS, PVD, NYC;
    Transport BtoPT, BtoPB, PtoBT, PtoBB, NtoP, NtoB, BtoN;

    /**
     * Setting up a graph for New England cities
     */
    @Before
    public void createNewEnglandGraph() {

        this.newEngland = new TravelGraph();

        // creating the cities
        this.BOS = new City("Boston");
        this.PVD = new City("Providence");
        this.NYC = new City("New York City");
        // adding the cities to the graph
        this.newEngland.addVertex(this.BOS);
        this.newEngland.addVertex(this.PVD);
        this.newEngland.addVertex(this.NYC);
        // creating the different edges
        this.BtoPT = new Transport(this.BOS, this.PVD, TransportType.TRAIN,
                50., 180.);
        this.BtoPB = new Transport(this.BOS, this.PVD, TransportType.BUS,
                30., 300);
        this.PtoBT = new Transport(this.PVD, this.BOS, TransportType.TRAIN,
                50., 180.);
        this.PtoBB = new Transport(this.PVD, this.BOS, TransportType.BUS,
                30., 300);
        this.NtoB = new Transport(this.NYC, this.BOS, TransportType.PLANE, 200.,
        50.);
        this.NtoP = new Transport(this.NYC, this.PVD, TransportType.TRAIN, 90.,
                200.);
        this.BtoN = new Transport(this.BOS, this.NYC, TransportType.PLANE, 200.,
                50.);
        // adding the edges to the graph
        this.newEngland.addEdge(this.BOS, this.BtoPT);
        this.newEngland.addEdge(this.BOS, this.BtoPB);
        this.newEngland.addEdge(this.BOS, this.BtoN);
        this.newEngland.addEdge(this.PVD, this.PtoBB);
        this.newEngland.addEdge(this.PVD, this.PtoBT);
        this.newEngland.addEdge(this.NYC, this.NtoB);
        this.newEngland.addEdge(this.NYC, this.NtoP);
    }

// -----------------------------------------------------------------------------
    // TESTS THAT CITIES ARE ADDED CORRECTLY INTO THE GRAPH

    // tests that the cities are added correctlly to the graph.
    @Test
    public void addCitiesTest() {
        // checks that Boston was correctly added to the set of cities
        Assert.assertEquals(this.newEngland.getCity("Boston"), this.BOS);
        // checks that New York City was correctly added to the set of cities
        Assert.assertEquals(this.newEngland.getCity("New York City"), this.NYC);
        // checks that Providence was correctly added to the set of cities
        Assert.assertEquals(this.newEngland.getCity("Providence"), this.PVD);
    }

    // tests boston's outgoing edges are correctly added
    @Test
    public void bostonOutgoing() {
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.BOS).contains(this.BtoPT));
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.BOS).contains(this.BtoPB));
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.BOS).contains(this.BtoN));
    }

    // tests that nyc's edges are correctly added
    @Test
    public void nycOutgoing() {
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.NYC).contains(this.NtoP));
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.NYC).contains(this.NtoB));
    }

    // tests that pvd's edges are correctly added to pvd
    @Test
    public void pvdOutgoing() {
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.PVD).contains(this.PtoBT));
        Assert.assertTrue(this.newEngland.getOutgoingEdges(this.PVD).contains(this.PtoBB));
    }


// -----------------------------------------------------------------------------
    // TESTING THAT THE CONNECTIONS ARE CORRECT BETWEEN TWO CITIES

    // tests that for the BOS -> PVD w/ Train edge, source is BOS and destination is PVD
    @Test
    public void bosToPvd1() {
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeSource(this.BtoPT));
        Assert.assertEquals(this.PVD, this.newEngland.getEdgeTarget(this.BtoPT));
    }

    // tests that for the BOS -> PVD w/ Bus edge, source is BOS and destination is PVD
    @Test
    public void bosToPvd2() {
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeSource(this.BtoPB));
        Assert.assertEquals(this.PVD, this.newEngland.getEdgeTarget(this.BtoPB));
    }

    // tests that for the PVD -> BOS w/ Train edge, source is PVD and destination is BOS
    @Test
    public void pvdToBos1() {
        Assert.assertEquals(this.PVD, this.newEngland.getEdgeSource(this.PtoBT));
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeTarget(this.PtoBT));
    }

    // tests that for the PVD -> BOS w/ Bus edge, source is PVD and destination is BOS
    @Test
    public void pvdToBos2() {
        Assert.assertEquals(this.PVD, this.newEngland.getEdgeSource(this.PtoBB));
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeTarget(this.PtoBB));
    }

    // tests that for the NYC -> BOS edge, source is NYC and destination is BOS
    @Test
    public void nycToBos1() {
        Assert.assertEquals(this.NYC, this.newEngland.getEdgeSource(this.NtoB));
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeTarget(this.NtoB));
    }

    // tests that for the NYC -> PVD edge, source is NYC and destination is PVD
    @Test
    public void nycToPVD(){
        Assert.assertEquals(this.NYC, this.newEngland.getEdgeSource(this.NtoP));
        Assert.assertEquals(this.PVD, this.newEngland.getEdgeTarget(this.NtoP));
    }

    // tests that for the BOS -> NYC edge, source is BOS and destination is NYC
    @Test
    public void bosToNYC() {
        Assert.assertEquals(this.BOS, this.newEngland.getEdgeSource(this.BtoN));
        Assert.assertEquals(this.NYC, this.newEngland.getEdgeTarget(this.BtoN));
    }

// -----------------------------------------------------------------------------

    // TESTING THAT ADDEDGE CANNOT BE CALLED ON A NONEXISTENT CITY.
    @Test (expected = IllegalArgumentException.class)
    public void addEdgeToNoCity() {
        this.newEngland.addEdge(new City("LA"), this.BtoN);
    }

// =============================================================================
    // TESTING AN AUTOLOADED GRAPH

    TravelController koreaControl;

    //importing the csv file and loading it into a graph.
    @Before
    public void setUpKoreaControl() {
        this.koreaControl = new TravelController();
        this.koreaControl.load("sol/KRCity.csv", "sol/KRTransport.csv");
    }

    // makes sure that the right number of cities have been added into the graph.
    @Test
    public void testLoadSize()  {
        Assert.assertEquals(this.koreaControl.getGraph().getVertices().size(), 6);
    }

// =============================================================================
    // makes sure that all cities have the appropriate number of outgoing.

    // tests that incheon has all of its outgoing cities.
    @Test
    public void incheonOut() {
        // Incheon
        Assert.assertEquals(this.koreaControl.getGraph().getOutgoingEdges(
                        this.koreaControl.getGraph().getCity("Incheon")).size(),
                3);
    }

    // tests that busan has all of its outgoing cities
    @Test
    public void busanOut() {
        Assert.assertEquals(this.koreaControl.getGraph().getOutgoingEdges(
                        this.koreaControl.getGraph().getCity("Busan")).size(),
                3);
    }

    // tests that seoul has all of its outgoing cities.
    @Test
    public void seoulOut() {
        Assert.assertEquals(this.koreaControl.getGraph().getOutgoingEdges(
                        this.koreaControl.getGraph().getCity("Seoul")).size(),
                4);
    }

    // tests that jejudo has all of its outgoing cities.
    @Test
    public void jejudoOut() {
        Assert.assertEquals(this.koreaControl.getGraph().getOutgoingEdges(
                        this.koreaControl.getGraph().getCity("Jejudo")).size(),
                1);
    }

    // tests that ulsan has all of its outgoing cities.
    @Test
    public void ulsanOut() {
        Assert.assertEquals(this.koreaControl.getGraph().getOutgoingEdges(
                        this.koreaControl.getGraph().getCity("Ulsan")).size(),
                3);
    }

// =============================================================================
    //TESTING FOR IO AND PARSING ERRORS.

    // Tests that the io error is caught
    @Test
    public void loadError() {
        TravelController error = new TravelController();
        Assert.assertEquals("error parsing file:  invalid string path",
                error.load("invalid string path", "invalid"));
    }

    // tests that an invalid csv file will throw
    @Test (expected = NullPointerException.class)
    public void loadError2() {
        TravelController error = new TravelController();
        error.load("sol/messyCsv.csv", "sol/KRCity.csv");
    }




}
