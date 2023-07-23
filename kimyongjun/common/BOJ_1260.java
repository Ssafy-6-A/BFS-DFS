package kimyongjun.common;
/**
 * 풀이 시작 : 11:25
 * 풀이 완료 : 11:44
 * 풀이 시간 : 19분
 *
 * 문제 해석
 * 그래프를 DFS 탐색한 결과와 BFS 탐색한 결과를 출력하는 프로그램 작성
 *
 * 구해야 하는 것
 * DFS 탐색, BFS 탐색의 결과
 *
 * 문제 입력
 * 첫째 줄 : 정점의 개수 N, 간선의 개수 M, 탐색 시작 번호 V
 * 둘째 줄부터 M개의 줄 : 간선이 연결하는 두 정점의 번호, 양방향
 *
 * 제한 사항
 * 방문할 수 있는 정점이 여러 개인 경우 정점 번호가 작은 것부터 방문
 * 정점의 번호는 1부터 N까지
 * 간선은 양방향
 * 첫째 줄에 DFS 수행 결과 출력
 * 둘째 줄에 BFS 수행 결과 출력
 *
 * 생각나는 풀이
 * 입력에 따라 그래프를 구현 후 DFS 탐색, BFS 탐색
 * 인접 노드가 여러 개인 경우 오름차순으로 방문해야 함 -> 간선 순서 정렬 필요
 * DFS BFS 탐색 후 결과 출력
 *
 * 필요한 기능
 * 1. 입력에 따라 그래프 구현
 * 2. 간선의 정렬
 * 3. DFS
 * 3-1. 결과 저장용 배열
 * 4. BFS
 * 4-1. 결과 저장용 배열
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1260 {
    static int N;
    static ArrayList<Integer>[] graph; // graph 구현 할 ArrayList배열
    static boolean[] visitedDfs; // DFS 방문 여부 체크 배열
    static boolean[] visitedBfs; // BFS 방문 여부 체크 배열
    static StringBuilder sb = new StringBuilder(); // 방문 순서 담는 StringBuilder

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int V = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        visitedDfs = new boolean[N + 1];
        visitedBfs = new boolean[N + 1];

        for (int i = 1; i <= N; i++) graph[i] = new ArrayList<>();

        // 1. 입력에 따라 그래프 구현
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            // 양방향 간선이므로 두 방향 모두 저장
            graph[start].add(end);
            graph[end].add(start);
        }

        // 2. 간선의 정렬 (인접 배열로 구현한다면 정렬 필요 x)
        for (int i = 1; i <= N; i++) Collections.sort(graph[i]);
        dfs(V);
        sb.append('\n'); // dfs 출력과 bfs 출력에 줄바꿈이 있어야 하므로
        bfs(V);

        System.out.println(sb);
    }

    // 3. DFS
    private static void dfs(int prev) {
        sb.append(prev).append(' ');
        visitedDfs[prev] = true;

        for (int now : graph[prev]) {
            if (visitedDfs[now]) continue;
            dfs(now);
        }
    }

    // 4. BFS
    private static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visitedBfs[start] = true;


        while (!q.isEmpty()) {
            int prev = q.poll();
            sb.append(prev).append(' ');
            for (int now : graph[prev]) {
                if (visitedBfs[now]) continue;
                visitedBfs[now] = true;
                q.offer(now);
            }
        }
    }
}
