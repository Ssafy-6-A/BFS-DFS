package ssafy.algo;

import java.util.Scanner;

public class BOJ_11724 {

    // cc: 연결 요소 (Connected Component)
    static int n, m, cc = 0;
    static int[][] arr;
    static boolean[] visited;

    // DFS 구조
    static void DFS(int currentNode) {
        visited[currentNode] = true;
        for(int i=1; i<=n; i++) {
            if (!visited[i] && (arr[currentNode][i] == 1 || arr[i][currentNode] == 1)) {
                visited[i] = true;
                DFS(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        arr = new int[n+1][n+1];
        visited = new boolean[n+1];

        for (int i=0; i<m; i++) {
            int firstNode = sc.nextInt();
            int secondNode = sc.nextInt();
            arr[firstNode][secondNode] = 1;
            arr[secondNode][firstNode] = 1;
        }

        // 첫 노드를 1부터 시작
        for (int i=1; i<=n; i++) {
            // 만약 이미 탐색한 연결 요소 안에 있었던 정점인 경우 지나감
            if (visited[i]) continue;
                // 연결 요소로 탐색하지 못했던 정점이면 해당 정점부터 다시 탐색 시작
            else  {
                cc++; // 새로운 연결 요소로 들어갔으므로 연결 요소 1씩 증가
                DFS(i);
            }
        }
        System.out.println(cc);
    }

}