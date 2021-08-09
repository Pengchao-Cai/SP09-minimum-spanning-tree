// Starter code for SP9

package pxc190029;

import idsa.Graph;
import idsa.Graph.Vertex;
import idsa.Graph.Edge;
import idsa.Graph.GraphAlgorithm;
import idsa.Graph.Factory;
import idsa.Graph.Timer;

import pxc190029.BinaryHeap.Index;
import pxc190029.BinaryHeap.IndexedHeap;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.File;

public class MST extends GraphAlgorithm<MST.MSTVertex> {
    String algorithm;
    public long wmst;
    List<Edge> mst;
    
    MST(Graph g) {
	super(g, new MSTVertex((Vertex) null));
		this.algorithm = null;
		this.wmst = 0;
		mst = new ArrayList<>();
    }

    public static class MSTVertex implements Index, Comparable<MSTVertex>, Factory {
    	int index;
    	int distance;
    	Vertex parent;
    	boolean seen;
    	Vertex self;
		MSTVertex(Vertex u) {
			this.parent = null;
			this.seen = false;
			this.self = u;
			this.distance = Integer.MAX_VALUE;
		}

		MSTVertex(MSTVertex u) {  // for prim2
		}

		public MSTVertex make(Vertex u) { return new MSTVertex(u); }

		public void putIndex(int index) {this.index = index;}

		public int getIndex() { return index; }

		public int compareTo(MSTVertex other) {
			if(this.distance < other.distance){
				return -1;
			}else if(this.distance > other.distance){
				return 1;
			}
			return 0;
		}
    }

    public long kruskal() {
	algorithm = "Kruskal";
	Edge[] edgeArray = g.getEdgeArray();
        mst = new LinkedList<>();
        wmst = 0;
        return wmst;
    }
	
    public long boruvka() {
	algorithm = "Boruvka";
        
	wmst = 0;
	
	return wmst;
    }

    public long prim2(Vertex s) {
	algorithm = "indexed heaps";
        mst = new LinkedList<>();
	wmst = 0;
	IndexedHeap<MSTVertex> q = new IndexedHeap<>(g.size());
	get(s).distance = 0;
	get(s).seen = true;
	for(Vertex v : g.getVertexArray()){
		q.add(get(v));
	}
	while(!q.isEmpty()){
		MSTVertex u = q.remove();
		u.seen = true;
		wmst = wmst + u.distance;
		for(Edge e : g.outEdges(u.self)){
			Vertex v = e.otherEnd(u.self);
			if(!get(v).seen && e.getWeight() < get(v).distance){
				get(v).distance = e.getWeight();
				get(v).parent = u.self;
				q.decreaseKey(get(v));
			}
		}
	}
	return wmst;
    }


    public long prim1(Vertex s) {
	algorithm = "PriorityQueue<Edge>";
        mst = new LinkedList<>();
	wmst = 0;
	PriorityQueue<Edge> q = new PriorityQueue<>();
	return wmst;
    }

    public static MST mst(Graph g, Vertex s, int choice) {
	MST m = new MST(g);
	switch(choice) {
	case 0:
	    m.boruvka();
	    break;
	case 1:
	    m.prim1(s);
	    break;
	case 2:
	    m.prim2(s);
	    break;
	case 3:
	    m.kruskal();
	    break;
	default:
	    
	    break;
	}
	return m;
    }

    public static void main(String[] args) throws FileNotFoundException {
	Scanner in;
	int choice = 2;  // prim2

	if (args.length == 0 || args[0].equals("-")) {
		in = new Scanner(System.in);
	} else {
		File inputFile = new File(args[0]);
		in = new Scanner(inputFile);
	}

	if (args.length > 1) { choice = Integer.parseInt(args[1]); }

	Graph g = Graph.readGraph(in);
	Vertex s = g.getVertex(1);
	Timer timer = new Timer();
	MST m = mst(g, s, choice);
	System.out.println(m.algorithm + "\n" + m.wmst);
	System.out.println(timer.end());



    }

}
