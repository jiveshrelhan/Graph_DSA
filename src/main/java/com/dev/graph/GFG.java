package com.dev.graph;

import java.util.Scanner;

class GFG {
	public static void main(String[] args) {
		 Scanner s = new Scanner(System.in);
		    int j=s.nextInt();
		    for(int i=0; i<j; i++){
		        int m=s.nextInt();
		        int n=s.nextInt();
		        
		        char arr [][] = new char[m][n];
		        
		        for(int z=0; z<m; z++){
		            String f = s.next();
		            arr[z]=f.toCharArray();
		        }
		        System.out.println(noOfComponents(arr, m, n));
		    }
	}

	private static int noOfComponents(char[][] graph, int N, int M) {
		int res = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (graph[i][j] == 'X') {
					DFS(graph, i, j, N, M);
					res++;
				}
			}
		}
		return res;
	}

	private static void DFS(char[][] graph, int i, int j, int N, int M) {

		if (i < 0 || i >= N || j < 0 || j >= M) {
			return;
		}

		if (graph[i][j] == 'O') {
			return;
		}

		graph[i][j] = 'O';

		DFS(graph, i, j - 1, N, M);
		DFS(graph, i, j + 1, N, M);
		DFS(graph, i - 1, j, N, M);
		DFS(graph, i + 1, j, N, M);

	}

}