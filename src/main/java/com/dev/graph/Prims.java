package com.dev.graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @Algo Create MST by adding one edge at a time.; Addition of that edge should
 *       not create cycle in current MST ;You have to use the minimum weighted
 *       edge possible from all the vertex in current MST ;Repeat till all the
 *       vertices are explored
 * @author JiveshR1
 *
 */
public class Prims {

	static class Edge {
		int src;
		int dest;
		int w;

		Edge(int src, int dest, int w) {
			this.src = src;
			this.dest = dest;
			this.w = w;
		}
	}

	private static List<Edge> extractEdges(ArrayList<ArrayList<Integer>> graph, int V) {
		List<Edge> res = new ArrayList<>();

		for (int i = 0; i < V; i++) {
			ArrayList<Integer> children = graph.get(i);
			for (int j = 0; j < children.size(); j++) {
				int child = children.get(j);
				//if (child != Integer.MAX_VALUE)
					res.add(new Edge(i, j, child));
			}
		}
		return res;
	}

	private static void addEdges(List<Edge> edges, int src, int V, PriorityQueue<Edge> pq) {

		for (int i = (src * V); i < edges.size() && i < (src * V) + V; i++) {
			Edge edge = edges.get(i);
			pq.add(edge);
		}
	}

	private static void addEdgeToGraph(ArrayList<ArrayList<Integer>> graph, int src, int dest) {
		graph.get(src).add(dest);
		graph.get(dest).add(src);
	}

	private static int MST(ArrayList<ArrayList<Integer>> graph, int V) {

		int minCost = 0;

		int edges_count = 0;

		List<Edge> edges = extractEdges(graph, V);

		boolean[] visitedinMST = new boolean[V];

		PriorityQueue<Edge> pq = new PriorityQueue<>(new Comparator<Edge>() {

			public int compare(Edge o1, Edge o2) {
				if (o1.w < o2.w)
					return -1;
				else if (o1.w > o2.w)
					return 1;
				else
					return 0;
			}
		});

		addEdges(edges, 0, V, pq);

		visitedinMST[0] = true;

		while (!pq.isEmpty() && edges_count != V - 1) {
			Edge minEdge = pq.poll();
			if (!visitedinMST[minEdge.dest]) {
				addEdgeToGraph(graph, minEdge.src, minEdge.dest);
				visitedinMST[minEdge.dest]=true;
				minCost += minEdge.w;
				edges_count++;
				addEdges(edges, minEdge.dest, V, pq);
			}

		}
		return minCost;
	}

	public static void main(String[] args) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		ArrayList<Integer> zero = new ArrayList<>();
		zero.add(Integer.MAX_VALUE);
		zero.add(5);
		zero.add(Integer.MAX_VALUE);
		zero.add(8);

		ArrayList<Integer> one = new ArrayList<>();
		one.add(5);
		one.add(Integer.MAX_VALUE);
		one.add(Integer.MAX_VALUE);
		one.add(7);

		ArrayList<Integer> two = new ArrayList<>();
		two.add(Integer.MAX_VALUE);
		two.add(Integer.MAX_VALUE);
		two.add(Integer.MAX_VALUE);
		two.add(6);
		
		ArrayList<Integer> three = new ArrayList<>();
		three.add(8);
		three.add(7);
		three.add(6);
		three.add(Integer.MAX_VALUE);

		graph.add(zero);
		graph.add(one);
		graph.add(two);
		graph.add(three);

		System.out.println(MST(graph, 4));

	}
}
