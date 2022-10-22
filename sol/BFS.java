package sol;

import src.IBFS;
import src.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


/**
 * BFS impements the Breadth-first-search algorithm
 * @param <V> a datatype that stores the vertex of a graph
 * @param <E> a dataypte that stores the edge of a graph.
 */

public class BFS<V, E> implements IBFS<V, E> {

    /**
     * Gets the shortest possible distance between two vertices
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return a list of edges that represents the shortest possible path
     * between two vertices.
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {

        // maps the starting cities to an edge
        HashMap<V,E> cameFrom = new HashMap<V, E>();
        LinkedList<E> toCheck = new LinkedList<E>();
        HashSet<E> visited = new HashSet<E>();

        LinkedList<E> returnMe = new LinkedList<>();
        // if the start and end are equal, an empty list is returned
        if (start.equals(end)) {
            return returnMe;
        }

        // begin by adding the starting city's edges to the toCheck list
         for (E edge: graph.getOutgoingEdges(start)) {
             // all the edges are added to the toCheck list
            toCheck.addLast(edge);
        }

        while (!toCheck.isEmpty()) {
            E checkingEdge = toCheck.poll();

            visited.add(checkingEdge);

            // updating cameFrom map
            // if the hashmap does not already contain the city
            if (!cameFrom.containsKey(graph.getEdgeTarget(checkingEdge))) {
                cameFrom.put(graph.getEdgeTarget(checkingEdge), checkingEdge);
            }

            // add the edge target's neighbors to toCheck
            for (E edge: graph.getOutgoingEdges(graph.getEdgeTarget(checkingEdge))) {
                if (!visited.contains(edge)) {
                    toCheck.addLast(edge);
                }
            }
            // if the destination of the current edge = end, we can backtracking
            if (graph.getEdgeTarget(checkingEdge).equals(end)) {
                try {
                    V immediateDestination = end;
                    while (!immediateDestination.equals(start)) {
                        E addingEdge = cameFrom.get(immediateDestination);
                        returnMe.addFirst(addingEdge);
                        immediateDestination = graph.getEdgeSource(addingEdge);
                    }
                    return returnMe;
                } catch (NullPointerException e) {
                    return new LinkedList<E>();
                }
            }
        }
        return returnMe;
    }
}

