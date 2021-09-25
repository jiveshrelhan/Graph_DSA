package com.dev.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class IfPathExists {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int testcases = scan.nextInt();

		while (testcases > 0) {

			int N = scan.nextInt();
			int[][] graph = new int[N][N];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					graph[i][j] = scan.nextInt();
				}
			}

			lookForOne(graph, N);
			testcases--;
		}
		scan.close();
	}

	private static void lookForOne(int[][] graph, int N) {
	
      
		boolean[][] visited = new boolean[N][N];
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (graph[i][j] == 1) {
					res = DFS(graph, i, j, N, visited);
					break;
				}
			}
		}

		res = (res > 0) ? 1 : 0;
		System.out.println(res);
	}

	private static int DFS(int[][] graph, int i, int j, int N, boolean[][] visited) {

		// A small optimization can be there. If we stop once we get the result 1.
		// We can achieve this if use res in DFS func parameter & return immediately if
		// res ==1 return 1;

		if (i < 0 || i >= N || j < 0 || j >= N)
			return 0;

		int node = graph[i][j];

		if (node == 2)
			return 1;

		if (node == 0)
			return 0;

		if ((node == 3 || node == 1) && !visited[i][j]) {
			visited[i][j] = true;
			return DFS(graph, i - 1, j, N, visited) + DFS(graph, i + 1, j, N, visited) + DFS(graph, i, j + 1, N, visited)
					+ DFS(graph, i, j - 1, N, visited);
		}

		return 0;

	}
}