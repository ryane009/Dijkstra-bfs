package sol;

import src.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implementation for TravelGraph
 */
public class TravelGraph implements IGraph<City, Transport> {

    private HashMap<String, City> citySet;

    public TravelGraph(){
        this.citySet = new HashMap<>();
    }
    @Override
    public void addVertex(City vertex) {
        this.citySet.put(vertex.toString(), vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() {
        HashSet<City> vertices = new HashSet<>();
        for(Map.Entry<String, City> element : this.citySet.entrySet()){
            vertices.add(element.getValue());
        }
        return vertices;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    public City getCityByName(String name){
        return this.citySet.get(name);
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}