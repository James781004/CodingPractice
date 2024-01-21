package Hackerrank.Ch09_Graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BfsShortestReachInAGraph {
    static void BFS(ArrayList<Integer>[] adj, int[] visited, int s) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int i = 0; i < adj[node].size(); ++i) {
                int neighbor = adj[node].get(i);

                if (visited[neighbor] == -1) {
                    visited[neighbor] = visited[node] + 6;
                    queue.add(neighbor);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine());

        for (int i = 0; i < q; ++i) {
            String[] nm = br.readLine().split(" ");
            int n = Integer.parseInt(nm[0]);
            int m = Integer.parseInt(nm[1]);

            ArrayList<Integer>[] adj = new ArrayList[n + 1];
            for (int j = 1; j <= n; ++j) {
                adj[j] = new ArrayList<>();
            }

            for (int j = 1; j <= m; ++j) {
                String[] uv = br.readLine().split(" ");
                int u = Integer.parseInt(uv[0]);
                int v = Integer.parseInt(uv[1]);
                adj[u].add(v);
                adj[v].add(u);
            }

            int s = Integer.parseInt(br.readLine());

            int[] visited = new int[n + 1];
            Arrays.fill(visited, -1);

            BFS(adj, visited, s);

            for (int j = 1; j <= n; ++j) {
                if (visited[j] != 0) {
                    System.out.print(visited[j] + " ");
                }
            }

            System.out.println();
        }
    }
}

