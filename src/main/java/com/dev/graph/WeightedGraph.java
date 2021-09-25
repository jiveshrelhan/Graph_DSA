package com.dev.graph;

import java.util.LinkedList;

public class WeightedGraph {

	class Edge {
		int src, dest, weight;

		Edge(int s, int d, int w) {
			this.src = s;
			this.dest = d;
			this.weight = w;
		}

	}

	LinkedList<Edge>[] WG;

	WeightedGraph(int size) {
		this.WG = new LinkedList[size];

		for (int i = 0; i < size; i++) {
			WG[i] = new LinkedList<>();
		}
	}

	public void addEdge(int src, int desc, int weight) {
		Edge edge = new Edge(src, desc, weight);
		Edge edge1 = new Edge(desc, src, weight);
		WG[src].addLast(edge);
		WG[desc].addLast(edge1);
	}

}
