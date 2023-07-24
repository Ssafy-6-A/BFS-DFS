package ssafy.algo;

/*
문제
그래프를 DFS로 탐색한 결과와 BFS로 탐색한 결과를 출력하는 프로그램을 작성하시오.
단, 방문할 수 있는 정점이 여러 개인 경우에는 정점 번호가 작은 것을 먼저 방문하고,
더 이상 방문할 수 있는 점이 없는 경우 종료한다. 정점 번호는 1번부터 N번까지이다.

입력
첫째 줄에 정점의 개수 N(1 ≤ N ≤ 1,000), 간선의 개수 M(1 ≤ M ≤ 10,000), 탐색을 시작할 정점의 번호 V가 주어진다.
다음 M개의 줄에는 간선이 연결하는 두 정점의 번호가 주어진다.
어떤 두 정점 사이에 여러 개의 간선이 있을 수 있다. 입력으로 주어지는 간선은 양방향이다.

출력
첫째 줄에 DFS를 수행한 결과를, 그 다음 줄에는 BFS를 수행한 결과를 출력한다.
V부터 방문된 점을 순서대로 출력하면 된다.
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BOJ_1260 {

    // int[][] arr, boolean[] visited의 크기로 사용하기 위해 static으로 선언
    static int n, m, v;
    // int[][] arr, boolean[] visited는 공통으로 사용하므로 static으로 선언
    // int[][] arr: 간선 연결 정보를 저장
    static int[][] arr;
    // boolean[] visited: 정점 방문 여부를 저장
    static boolean[] visited;
    // Queue<Integer> queue: BFS로 탐색한 정점들을 저장하기 위해 사용
    static Queue<Integer> queue = new LinkedList<>();

    static void DFS(int cNode) {
        System.out.print(cNode+" ");
        // 정점 정보가 1부터 n까지 나오므로 1~n까지 탐색
        for (int i = 1; i <= n; i++) {
            // 가려고 하는 정점을 방문을 안했는지 and 가려고 하는 정점과 현재 정점 간 간선의 여부를 확인
            if (!visited[i] && (arr[cNode][i] == 1 || arr[i][cNode] == 1)) {
                // 해당 정점을 방문했으므로 방문 확인 처리
                visited[i] = true;
                // 해당 정점에서 다시 탐색을 시작
                DFS(i);
            }
        }
    }

    static void BFS(int cNode) {
        do {
            // queue의 FIFO 성질을 이용하여 먼저 들어온 노드를 내보내면서 해당 정점이랑 연결된 정점 탐색
            cNode = queue.poll();
            System.out.print(cNode+" ");
            // 정점 정보가 1부터 n까지 나오므로 1~n까지 탐색
            for (int i = 1; i <= n; i++) {
                // 가려고 하는 정점을 방문을 안했는지 and 가려고 하는 정점과 현재 정점 간 간선의 여부를 확인
                if (!visited[i] && (arr[cNode][i] == 1 || arr[i][cNode] == 1)) {
                    // 해당 정점을 방문했으므로 queue에 추가
                    queue.add(i);
                    // 해당 정점을 방문했으므로 방문 확인 처리
                    visited[i] = true;
                }
            }
            // queue 안에 비어있으면 모든 노드를 탐색한 것이므로 queue가 빌 때 까지 탐색한다.
        } while(!queue.isEmpty());
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        // 정보 입력
        n = sc.nextInt();
        m = sc.nextInt();
        v = sc.nextInt();

        // 주어진 정보에서 정점은 1~n의 범위를 가지므로 배열의 사이즈를 1씩 증가시킨다.
        arr = new int[n+1][n+1];
        visited = new boolean[n+1];
        // 시작점은 시작하자마자 방문하므로 방문 처리
        visited[v] = true;

        // 간선의 정보를 저장, 숫자 크기와 상관없이 정보가 들어오므로
        for (int i = 0; i < m; i++) {
            int firstNode = sc.nextInt();
            int secondNode = sc.nextInt();
            arr[firstNode][secondNode] = 1;
            arr[secondNode][firstNode] = 1;
        }

        // DFS 구현
        DFS(v);

        System.out.println();

        // BFS 구현
        // visited가 모두 참인 상태이므로 다시 false로 초기화
        visited = new boolean[n+1];
        // 시작점은 시작하자마자 방문하므로 방문 처리
        visited[v] = true;
        // // 시작점은 시작하자마자 방문하므로 queue에 시작점 추가
        queue.add(v);
        BFS(v);

    }

}
