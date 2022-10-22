//package sol;
//
//import src.IDijkstra;
//import src.IGraph;
//
//import java.util.List;
//import java.util.HashSet;
//import java.util.LinkedList;
//import java.util.function.Function;
//import java.util.Comparator;
//import java.util.PriorityQueue;
//import java.util.HashMap;
//
//
///**
// * a class that implements Dijkstra's search method
// * @param <V> a datatype that represents a graph's vertices
// * @param <E> a datatype that represents a graph's edges.
// */
//public class Dijkstra<V, E> implements IDijkstra<V, E> {
//
//    /**
//     * gets the shortest path based on a weight according to Dijkstra's algo
//     * @param graph       the graph including the vertices
//     * @param source      the source vertex
//     * @param destination the destination vertex
//     * @param edgeWeight a function that represents the weight that we are
//     *                   traversing Dijkstra's based on
//     * @return a list of edges that represents the shortest path to get
//     * somewhere based on the weight.
//     */
//    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
//                                   Function<E, Double> edgeWeight) {
//
//        HashMap<V, Double> costMap = new HashMap<V, Double>();
//        // this comparator ranks the pq going from least to greatest
//        Comparator<V> leastToGreatest = (v1, v2) -> {
//            return Double.compare(costMap.get(v1), costMap.get(v2)) ;
//        };
//
//        // pq that's in order from least to greatest based on the cost value in the kvpair
//        PriorityQueue<V> toCheck = new PriorityQueue<V>(leastToGreatest);
//        HashMap<V, V> cameFrom = new HashMap<V, V>();
//
//        // establishes all cities in the graph with a value of infinity
//        for (V vertice : graph.getVertices()) {
//            costMap.put(vertice, Double.POSITIVE_INFINITY);
//            toCheck.add(vertice);
//        }
//
//        // this updates the order based on the new value for the source
//        toCheck.remove(source);
//        costMap.replace(source, 0.0);
//        toCheck.add(source);
//
//        // we don't have to check a graph that only has 1 node.
//        while (toCheck.size() > 1) {
//
//            // removes the first of the pq and establishes the currentvertex
//            V currentVertex = toCheck.poll();
//
//            // we use a hashset because we don't want repeats.
//            // Gets the neighboring vertices of the current vertex.
//            HashSet<V> vecinos = new HashSet<>();
//            for (E queremos : graph.getOutgoingEdges(currentVertex)) {
//                vecinos.add(graph.getEdgeTarget(queremos));
//            }
//
//            for (V neighbor : vecinos) {
//                // makes sure the neighbor has not been checked already
//                if (toCheck.contains(neighbor)) {
//
//                    Double immediateCost = cost(graph, currentVertex, neighbor, edgeWeight);
//                    // if the current vertex's cost + the cheapest edge to neighboring is less than the neighbor's cost.
//                    if (costMap.get(currentVertex) + immediateCost < costMap.get(neighbor)) {
//                        // updating the neighbor's cost.
//                        Double neighborCost = costMap.get(currentVertex) + immediateCost;
//                        cameFrom.put(neighbor, currentVertex);
//                        toCheck.remove(neighbor);
//                        costMap.replace(neighbor, neighborCost);
//                        toCheck.add(neighbor);
//
//                    }
//                }
//            }
//        }
//
//        // FOR BACKTRACKING
//        try {
//            V immediateDestination;
//            immediateDestination = destination;
//            LinkedList<E> returnMe = new LinkedList<E>();
//            while (!immediateDestination.equals(source)) {
//                returnMe.addFirst(getCheapestEdge(graph, immediateDestination,
//                        cameFrom.get(immediateDestination), edgeWeight));
//                immediateDestination = cameFrom.get(immediateDestination);
//            }
//
//            return returnMe;
//        } catch (NullPointerException e) {
//            return new LinkedList<>();
//        }
//
//    }
//
//    /**
//     * gets the chepeast edge between two vertices
//     * @param graph the graph that all of our calculations are based off of
//     * @param neighbor the neighboring vertex
//     * @param source the current vertex we're looking at
//     * @param edgeWeight the function that extracts the weight we're considering
//     *                   the iteration of Dijkstra's
//     * @return the edge that is cheapest between two vertices.
//     */
//    private E getCheapestEdge(IGraph<V, E> graph, V neighbor, V source, Function<E, Double> edgeWeight) {
//        Double cheapestCost = Double.POSITIVE_INFINITY;
//        // can't instantiate a new E, so we make a list of E and the cheapest
//        // will always be first due to the conditional structure.
//        LinkedList<E> cheapestFirst = new LinkedList<E>();
//        for (E edges: graph.getOutgoingEdges(source)) {
//            if (graph.getEdgeTarget(edges).equals(neighbor)) {
//                Double cuesta = edgeWeight.apply(edges);
//                if (cuesta< cheapestCost ) {
//                    cheapestFirst.addFirst(edges);
//                    cheapestCost = cuesta;
//                }
//            }
//        }
//        return cheapestFirst.getFirst();
//    }
//
//    /**
//     * returns the cheapest value a certain connection between 2 vertices can
//     * possibly be.
//     * @param graph the graph that we're doing our computations on
//     * @param checkingV the current vertex that we're onsidering
//     * @param vecino the immediate neighbor of the current vertex
//     * @param edgeWeight the function that extracts the weight that we're
//     *                   executing dijkstra's on.
//     * @return the minimum value for the weight between two vertices.
//     */
//    private Double cost(IGraph<V, E> graph, V checkingV, V vecino, Function<E, Double> edgeWeight) {
//        Double returnMe = Double.POSITIVE_INFINITY;
//        for (E edges : graph.getOutgoingEdges(checkingV)) {
//            if (graph.getEdgeTarget(edges).equals(vecino)) {
//                Double cuesta = edgeWeight.apply(edges);
//                if (cuesta< returnMe ) {
//                    returnMe = cuesta;
//                }
//            }
//        }
//        return returnMe;
//    }
//}//package sol;
////

////import src.IDijkstra;
////import src.IGraph;
////
////import java.util.List;
////import java.util.HashSet;
////import java.util.LinkedList;
////import java.util.function.Function;
////import java.util.Comparator;
////import java.util.PriorityQueue;
////import java.util.HashMap;
////
////
/////**
//// * a class that implements Dijkstra's search method
//// * @param <V> a datatype that represents a graph's vertices
//// * @param <E> a datatype that represents a graph's edges.
//// */
////public class Dijkstra<V, E> implements IDijkstra<V, E> {
////
////    /**
////     * gets the shortest path based on a weight according to Dijkstra's algo
////     * @param graph       the graph including the vertices
////     * @param source      the source vertex
////     * @param destination the destination vertex
////     * @param edgeWeight a function that represents the weight that we are
////     *                   traversing Dijkstra's based on
////     * @return a list of edges that represents the shortest path to get
////     * somewhere based on the weight.
////     */
////    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
////                                   Function<E, Double> edgeWeight) {
////
////        HashMap<V, Double> costMap = new HashMap<V, Double>();
////        // this comparator ranks the pq going from least to greatest
////        Comparator<V> leastToGreatest = (v1, v2) -> {
////            return Double.compare(costMap.get(v1), costMap.get(v2)) ;
////        };
////
////        // pq that's in order from least to greatest based on the cost value in the kvpair
////        PriorityQueue<V> toCheck = new PriorityQueue<V>(leastToGreatest);
////        HashMap<V, V> cameFrom = new HashMap<V, V>();
////
////        // establishes all cities in the graph with a value of infinity
////        for (V vertice : graph.getVertices()) {
////            costMap.put(vertice, Double.POSITIVE_INFINITY);
////            toCheck.add(vertice);
////        }
////
////        // this updates the order based on the new value for the source
////        toCheck.remove(source);
////        costMap.replace(source, 0.0);
////        toCheck.add(source);
////
////        // we don't have to check a graph that only has 1 node.
////        while (toCheck.size() > 1) {
////
////            // removes the first of the pq and establishes the currentvertex
////            V currentVertex = toCheck.poll();
////
////            // we use a hashset because we don't want repeats.
////            // Gets the neighboring vertices of the current vertex.
////            HashSet<V> vecinos = new HashSet<>();
////            for (E queremos : graph.getOutgoingEdges(currentVertex)) {
////                vecinos.add(graph.getEdgeTarget(queremos));
////            }
////
////            for (V neighbor : vecinos) {
////                // makes sure the neighbor has not been checked already
////                if (toCheck.contains(neighbor)) {
////
////                    Double immediateCost = cost(graph, currentVertex, neighbor, edgeWeight);
////                    // if the current vertex's cost + the cheapest edge to neighboring is less than the neighbor's cost.
////                    if (costMap.get(currentVertex) + immediateCost < costMap.get(neighbor)) {
////                        // updating the neighbor's cost.
////                        Double neighborCost = costMap.get(currentVertex) + immediateCost;
////                        cameFrom.put(neighbor, currentVertex);
////                        toCheck.remove(neighbor);
////                        costMap.replace(neighbor, neighborCost);
////                        toCheck.add(neighbor);
////
////                    }
////                }
////            }
////        }
////
////        // FOR BACKTRACKING
////        try {
////            V immediateDestination;
////            immediateDestination = destination;
////            LinkedList<E> returnMe = new LinkedList<E>();
////            while (!immediateDestination.equals(source)) {
////                returnMe.addFirst(getCheapestEdge(graph, immediateDestination,
////                        cameFrom.get(immediateDestination), edgeWeight));
////                immediateDestination = cameFrom.get(immediateDestination);
////            }
////
////            return returnMe;
////        } catch (NullPointerException e) {
////            return new LinkedList<>();
////        }
////
////    }
////
////    /**
////     * gets the chepeast edge between two vertices
////     * @param graph the graph that all of our calculations are based off of
////     * @param neighbor the neighboring vertex
////     * @param source the current vertex we're looking at
////     * @param edgeWeight the function that extracts the weight we're considering
////     *                   the iteration of Dijkstra's
////     * @return the edge that is cheapest between two vertices.
////     */
////    private E getCheapestEdge(IGraph<V, E> graph, V neighbor, V source, Function<E, Double> edgeWeight) {
////        Double cheapestCost = Double.POSITIVE_INFINITY;
////        // can't instantiate a new E, so we make a list of E and the cheapest
////        // will always be first due to the conditional structure.
////        LinkedList<E> cheapestFirst = new LinkedList<E>();
////        for (E edges: graph.getOutgoingEdges(source)) {
////            if (graph.getEdgeTarget(edges).equals(neighbor)) {
////                Double cuesta = edgeWeight.apply(edges);
////                if (cuesta< cheapestCost ) {
////                    cheapestFirst.addFirst(edges);
////                    cheapestCost = cuesta;
////                }
////            }
////        }
////        return cheapestFirst.getFirst();
////    }
////
////    /**
////     * returns the cheapest value a certain connection between 2 vertices can
////     * possibly be.
////     * @param graph the graph that we're doing our computations on
////     * @param checkingV the current vertex that we're onsidering
////     * @param vecino the immediate neighbor of the current vertex
////     * @param edgeWeight the function that extracts the weight that we're
////     *                   executing dijkstra's on.
////     * @return the minimum value for the weight between two vertices.
////     */
////    private Double cost(IGraph<V, E> graph, V checkingV, V vecino, Function<E, Double> edgeWeight) {
////        Double returnMe = Double.POSITIVE_INFINITY;
////        for (E edges : graph.getOutgoingEdges(checkingV)) {
////            if (graph.getEdgeTarget(edges).equals(vecino)) {
////                Double cuesta = edgeWeight.apply(edges);
////                if (cuesta< returnMe ) {
////                    returnMe = cuesta;
////                }
////            }
////        }
////        return returnMe;
////    }
////}

package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

/**
 * a class that implements Dijkstra's search method
 * @param <V> a datatype that represents a graph's vertices
 * @param <E> a datatype that represents a graph's edges.
 */
public class Dijkstra<V, E> implements IDijkstra<V, E> {


    /**
     * gets the shortest path based on a weight according to Dijkstra's algo
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight a function that represents the weight that we are
     *                   traversing Dijkstra's based on
     * @return a list of edges that represents the shortest path to get
     * somewhere based on the weight.
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        HashMap<V, Double> costMap = new HashMap<V,Double>();
        Comparator leastToGreatest= (v1, v2) -> {
            return Double.compare(costMap.get(v1), costMap.get(v2)) ;
        };
        PriorityQueue<V> toCheck  = new PriorityQueue<V>(leastToGreatest);
        HashMap<V, E> cameFrom = new HashMap<V, E>();

        LinkedList<E> returnMe = new LinkedList<>();
        if (source.equals(destination)) {
            return returnMe;
        }

        // adding  default values to costMap and establishing cities in the toCheck PQ
        for (V city: graph.getVertices()) {
            if (city.equals(source)) {
                costMap.put(city, 0.0);
            }
            else {
                costMap.put(city, Double.POSITIVE_INFINITY);
            }
            toCheck.add(city);
        }
        // does computations for the edges leading to neighboring vertices
        while(toCheck.size() > 1) {
            V checkingVertex =  toCheck.poll();

            // getting a list of neighbors for the checkingVertex
            HashSet<V> neighborSet = new HashSet<V>();
            for (E outgoing: graph.getOutgoingEdges(checkingVertex)) {
                neighborSet.add(graph.getEdgeTarget(outgoing));
            }

            // operations regarding the edges leading to the neighbors.
            for (V neighbor: neighborSet) {
                Double immediateCost = costMap.get(checkingVertex) +
                        edgeWeight.apply(this.cheapestEdge(graph, checkingVertex, neighbor, edgeWeight));
                if (immediateCost < costMap.get(neighbor)) {
                    cameFrom.put(neighbor, this.cheapestEdge(graph, checkingVertex, neighbor, edgeWeight));
                    toCheck.remove(neighbor);
                    costMap.replace(neighbor, immediateCost);
                    toCheck.add(neighbor);
                }
            }
        }

        // FOR BACKTRACKING
        try {
            V immediateDestination = destination;
            while (!immediateDestination.equals(source)) {
                E fromEdge = cameFrom.get(immediateDestination);
                returnMe.addFirst(fromEdge);
                immediateDestination= graph.getEdgeSource(fromEdge);
            }
            return returnMe ;

        } catch (NullPointerException e) {
            return new LinkedList<E>();
        }
    }

    /**
     * gets the chepeast edge between two vertices
     * @param graph the graph that all of our calculations are based off of
     * @param neighbor the neighboring vertex
     * @param checkingV the current vertex we're looking at
     * @param edgeWeight the function that extracts the weight we're considering
     *                   the iteration of Dijkstra's
     * @return the edge that is cheapest between two vertices.
     */
    private E cheapestEdge(IGraph<V, E> graph, V checkingV, V neighbor, Function<E, Double> edgeWeight) {
        Double compareCost = Double.POSITIVE_INFINITY;
        LinkedList<E> edgesToCompare = new LinkedList<E>();
        for (E edge: graph.getOutgoingEdges(checkingV)) {
            if (graph.getEdgeTarget(edge).equals(neighbor)) { // if correct edge going to the neighbor
                Double cost = edgeWeight.apply(edge);
                if (cost < compareCost) { // if the cost is less than the compareCost, addFirst
                    edgesToCompare.addFirst(edge);
                    compareCost = cost; // update the compareCost to maintain the cheapest cost
                }
            }
        }
        return edgesToCompare.getFirst(); // returns the cheapestEdge
    }

}

