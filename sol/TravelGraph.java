package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;

/**
 * implements all of the necessary methods to create a travel graph based on
 * cities and transportations between said cities.
 */
public class TravelGraph implements IGraph<City, Transport> {

    // the graph is a hashmap of string to cities, and cities contain all of the
    // connections between each other.
    private HashMap<String, City> ciudades;

    // a constructor for a travelgraph.
    public TravelGraph() {
        this.ciudades = new HashMap<String, City>();
    }

    /**
     * Adds a vertex to the travel graph.
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        this.ciudades.put(vertex.toString(), vertex);
    }

    /**
     * adds an edge to the travel graph according to its correct origin city
     * @param origin the origin of the edge.
     * @param edge the actual edge that contains all the informaiton.
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        if (this.ciudades.containsKey(origin.toString())) {
            origin.addOut(edge);
        } else {
            throw new IllegalArgumentException("City doesn't exist");
        }
    }

    /**
     * getvertices returns all of the cities within the travelgraph
     * @return a set of all possible cities.
     */
    @Override
    public Set<City> getVertices() {
        HashSet<City> allCities = new HashSet<City>();
        for (City cities: this.ciudades.values()) {
            allCities.add(cities);
        }
        return allCities;
    }

    /**
     * gets the origin city for a given edge
     * @param edge the edge
     * @return the city that the edge came fromm.
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * gets the city that the edge leads to.
     * @param edge the edge
     * @return the city that the edge leads to.
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * returns all possible edges leading out of a given city
     * @param fromVertex the vertex
     * @return a set of a all possible modes of transportation out of a city
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

// =============================================================================

    // self made:

    /**
     * gets the city based on its string name.
     * @param name the string name of the city
     * @return the city corresponding to the name.
     */
    public City getCity(String name) {
        return this.ciudades.get(name);
    }

    /**
     * turns the graph into a string.
     * @return a string representaiton of the graph.
     */
    @Override
    public String toString() {
        String printme = "";
        for (City ciudad: this.ciudades.values()) {
            printme += ciudad.toString() + "\n";
        }
        return printme;
    }

}