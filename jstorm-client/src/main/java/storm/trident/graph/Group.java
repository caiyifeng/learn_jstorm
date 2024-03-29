package storm.trident.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jgrapht.DirectedGraph;

import storm.trident.planner.Node;
import storm.trident.util.IndexedEdge;
import storm.trident.util.TridentUtils;


public class Group {
    public Set<Node> nodes = new HashSet<Node>();
    private DirectedGraph<Node, IndexedEdge> graph;
    private String id;
    
    public Group(DirectedGraph graph, List<Node> nodes) {
        init(graph);
        this.nodes.addAll(nodes);
        this.graph = graph;
    }
    
    public Group(DirectedGraph graph, Node n) {
        this(graph, Arrays.asList(n));
    }
    
    public Group(Group g1, Group g2) {
        init(g1.graph);
        nodes.addAll(g1.nodes);
        nodes.addAll(g2.nodes);
    }
    
    private void init(DirectedGraph graph) {
        this.graph = graph;
        this.id = UUID.randomUUID().toString();
    }
    
    public Set<Node> outgoingNodes() {
        Set<Node> ret = new HashSet<Node>();
        for(Node n: nodes) {
            ret.addAll(TridentUtils.getChildren(graph, n));
        }
        return ret;
    }
    
    public Set<Node> incomingNodes() {
        Set<Node> ret = new HashSet<Node>();
        for(Node n: nodes) {
            ret.addAll(TridentUtils.getParents(graph, n));
        }        
        return ret;        
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
    
    

    @Override
    public String toString() {
        return nodes.toString();
    }    
}
