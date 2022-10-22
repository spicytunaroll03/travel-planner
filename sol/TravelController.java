package sol;

import src.City;
import src.ITravelController;
import src.Transport;
import src.TravelCSVParser;
import src.TransportType;

import java.util.function.Function;
import java.io.IOException;
import java.util.Map;

import java.util.List;


public class TravelController implements ITravelController<City, Transport> {

    private TravelGraph graph;
    // THIS FIELD USED ONLY IN TESTING
    private List<Transport> returnedPath;

    public TravelController() {}

    /**
     * creates the graph based on a given pair of csv files
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return a success message that tells us that the graph has been loaded
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parseMe = new TravelCSVParser();
        Function<Map<String, String>, Void> parseVertex =
                mapping -> {
                    this.graph.addVertex(new City(mapping.get("name")));
                    return null;
                };
        Function<Map<String, String>, Void> parseTransport =
                mapping -> {
                    this.graph.addEdge(this.graph.getCity(mapping.get("origin")),
                            new Transport(
                            this.graph.getCity(mapping.get("origin")),
                            this.graph.getCity(mapping.get("destination")),
                            TransportType.fromString(mapping.get("type")),
                            Double.parseDouble(mapping.get("price")),
                            Double.parseDouble(mapping.get("duration"))
                            )
                    );
                    return null;
                };
        try {
            parseMe.parseLocations(citiesFile, parseVertex);
            parseMe.parseTransportation(transportFile, parseTransport);

            return "Successfully loaded cities and transportation files.";
        } catch (IOException e) {
            return "error parsing file:  " + citiesFile;
        }
    }

    /**
     * a method to get the fastest route between two cities.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the list representing the path that must be taken for the shortest
     * path between two cities.
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> getValue =
                edge -> {
                    return edge.getMinutes();
                };
        Dijkstra<City, Transport> dijkstraInstance = new Dijkstra<City, Transport>();

        return this.returnedPath = dijkstraInstance.getShortestPath(this.graph, this.graph.getCity(source),
                this.graph.getCity(destination), getValue);
    }
    /**
     * returns the total duration for a given path between two cities
     * @return a double, representing the total duration to get ebetween
     * two cities.
     */
    // ONLY USED FOR TESTING
    public Double totalDuration() {
        Function<Transport, Double> getDuration =
                edge -> {
                    return edge.getMinutes();
                };
        Double returnMe = 0.;
        for (Transport edge : this.returnedPath) {
            returnMe += getDuration.apply(edge);
        }
        return returnMe;
    }

    /**
     * returns the cheapest route between two given cities.
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the set of edges that represents the cheapest path between two
     * cities
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> getValue =
                edge -> {
                    return edge.getPrice();
                };
        Dijkstra<City, Transport> dijkstraInstance = new Dijkstra<City, Transport>();

        return this.returnedPath = dijkstraInstance.getShortestPath(this.graph, this.graph.getCity(source),
                this.graph.getCity(destination), getValue);

    }
    /**
     * totalCost returns the total cost for a single trip
     * @return the double that represents the cost of the trip;
     */
    // ONLY USED FOR TESTING
    public Double totalCost() {
        Function<Transport, Double> getCost =
                edge -> {
                    return edge.getPrice();
                };
        Double returnMe = 0.;
        for (Transport edge : this.returnedPath) {
            returnMe += getCost.apply(edge);
        }
        return returnMe;
    }

    /**
     * calculates the most direct path between two cities; aims for no stop-over
     * and no transfers
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the most direct path between two gven cities, represented by a
     * set of transport edges.
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        BFS<City, Transport> bfsInstance = new BFS<City, Transport>();

        return this.returnedPath = bfsInstance.getPath(this.graph, this.graph.getCity(source),
                this.graph.getCity(destination));
    }

    /**
     * converts travel controller to string
     * @return the string representation of a travel controller.
     */
    @Override
    public String toString() {
        return this.graph.toString();
    }

    public TravelGraph getGraph(){
        return this.graph;
    }
}
