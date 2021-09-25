package com.dev.graph;

import java.util.ArrayList;
import java.util.Scanner;

class DriverClass {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();

		while (t-- > 0) {
			ArrayList<ArrayList<Integer>> list = new ArrayList<>();
			int nov = sc.nextInt();
			int edg = sc.nextInt();
			for (int i = 0; i < nov + 1; i++)
				list.add(i, new ArrayList<Integer>());
			for (int i = 1; i <= edg; i++) {
				int u = sc.nextInt();
				int v = sc.nextInt();
				list.get(u).add(v);
			}
			if (new DetectCycle1().isCyclic(list, nov) == true)
				System.out.println("1");
			else
				System.out.println("0");
		}
	}
}

/*
 * This is a function problem.You only need to complete the function given below
 */
/* Complete the function below */
/*
 * ArrayList<ArrayList<Integer>> list: to represent graph containing 'v'
 * vertices and edges between them V: represent number of vertices
 */
class DetectCycle1 {
	static boolean isCyclic(ArrayList<ArrayList<Integer>> list, int V) {
		boolean[] global_visited = new boolean[V];

		boolean[] local_visited = new boolean[V];
		
		for (int i = 0; i < V; i++) {
			if (!global_visited[i]) {
				
				if (DFS(list, i, global_visited, local_visited)) {
					return true;
				}
			}
		}

		return false;
	}

	private static boolean DFS(ArrayList<ArrayList<Integer>> list, int src, boolean[] global_visited,
			boolean[] local_visited) {

		global_visited[src] = true;
		local_visited[src] = true;

		ArrayList<Integer> dependents = list.get(src);

		for (int i = 0; i < dependents.size(); i++) {
			int dependent = dependents.get(i);
			if (local_visited[dependent])
				return true;
			else if (!global_visited[dependent] && DFS(list, dependent, global_visited, local_visited)) {
				return true;
			}

		}
		local_visited[src] = false;
		return false;
	}
}