package sol;

import src.IDijkstra;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;
import java.util.function.Function;

/**
 * Implementation for Dijkstra's algorithm
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public class Dijkstra<V extends IVertex<E>, E extends IEdge<V>> implements IDijkstra<V, E> {

    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        LinkedList<E> shortestPath = new LinkedList<>();
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!

        if(source.equals(destination)){
            return shortestPath;
        }

        //initializing Hashmaps that we'll need to track running weight totals and paths respectively
        HashMap<V, Double> vertexVals = new HashMap<>();
        HashMap<V, E> from = new HashMap();

        Comparator<V> shortest = (val1, val2) ->{
            return Double.compare(vertexVals.get(val1), vertexVals.get(val2)) * -1;
        };

        PriorityQueue<V> toCheck = new PriorityQueue<>(shortest);
        for(V vertex : graph.getVertices()){
            vertexVals.put(vertex, 9999999999.0);
            toCheck.add(vertex);
        }
        vertexVals.replace(source, 0.0);
        toCheck.remove(source);
        toCheck.add(source);

        while(!toCheck.isEmpty()){
            V curr = toCheck.remove();
            for(V neighbor : this.getNeighbors(curr)){
                if(vertexVals.get(curr) + this.getCost(curr, neighbor, edgeWeight) < vertexVals.get(neighbor)){
                    vertexVals.replace(neighbor, vertexVals.get(curr) + this.getCost(curr, neighbor, edgeWeight));
                    from.put(neighbor, this.getEdge(curr, neighbor, edgeWeight, this.getCost(curr, neighbor, edgeWeight)));
                    toCheck.remove(neighbor);
                    toCheck.add(neighbor);
                }
            }
        }

        V currVertex = destination;
        if(!from.containsKey(destination)){
            return shortestPath;
        }
        while(currVertex != source){
            shortestPath.addFirst(from.get(currVertex));
            currVertex = from.get(currVertex).getSource();
        }
        return shortestPath;
    }

    private E getEdge(V origin, V dest, Function<E, Double> edgeWeight, double weight){
        for(E edge : origin.getOutgoing()){
            if(edge.getTarget().equals(dest) && edgeWeight.apply(edge) == weight){
                return edge;
            }
        }
        return null;
    }


    /**
     * calculates the smallest weight between the edges of two
     * given vertices according to the given edgeWeight that is passed in
     * @param origin
     * @param dest
     * @param edgeWeight
     * @return smallest weighted value between given origin and desdtination
     */
    private double getCost(V origin, V dest, Function<E, Double> edgeWeight){
        double smallestValue = 99999999999.0;
        for(E edge : origin.getOutgoing()){
            if(edge.getTarget().equals(dest)){
                if(edgeWeight.apply(edge) < smallestValue){
                    smallestValue = edgeWeight.apply(edge);
                }
            }
        }
        return smallestValue;
    }

    /**
     * Returns a list of all the neighboring vertices that are connected to
     * the vertex we pass through the parameters
     * @param vertex the vertex whose neighboring vertices we want to find
     * @return HashSet containing all neighboring vertices
     */
    private Set<V> getNeighbors(V vertex){
        HashSet<V> neighbors = new HashSet<V>();
        for(E edge : vertex.getOutgoing()){
            neighbors.add(edge.getTarget());
        }
        return neighbors;
    }


    // TODO: feel free to add your own methods here!
}
