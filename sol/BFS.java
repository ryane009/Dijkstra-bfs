package sol;

import src.IBFS;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;

/**
 * Implementation for BFS, implements IBFS interface
 * @param <V> The type of the vertices
 * @param <E> The type of the edges
 */
public class BFS<V extends IVertex<E>, E extends IEdge<V>> implements IBFS<V, E> {

    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        LinkedList<E> path = new LinkedList<>();
        if(start.equals(end)){
            return path;
        }

        Queue<V> toVisit = new LinkedList<>();
        HashMap<V, E>  from = new HashMap();
        LinkedList<V> visited = new LinkedList<>();

        toVisit.add(start);
        visited.add(start);
        while(!toVisit.isEmpty()){
            V curr = toVisit.remove();
            if(end.equals(curr)){
                V currVertex = curr;
                while(currVertex != start){
                    path.addFirst(from.get(currVertex));
                    currVertex = from.get(currVertex).getSource();
                }
                return path;
            }
            for(V neighbor : this.getNeighbors(curr)){
                if(!visited.contains(neighbor)){
                    visited.add(neighbor);
                    toVisit.add(neighbor);
                    from.put(neighbor, this.getEdge(curr, neighbor));
                }
            }
        }
        return path;
    }

    private Set<V> getNeighbors(V vertex){
        HashSet<V> neighbors = new HashSet<V>();
        for(E edge : vertex.getOutgoing()){
            neighbors.add(edge.getTarget());
        }
        return neighbors;
    }

    private E getEdge(V origin, V dest){
        for(E edge : origin.getOutgoing()){
            if(edge.getTarget().equals(dest)){
                return edge;
            }
        }
        return null;
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}
