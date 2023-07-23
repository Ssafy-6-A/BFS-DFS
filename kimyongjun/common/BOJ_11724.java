package kimyongjun.common;
/**
 * 풀이 시작 : 11:51
 * 풀이 완료 : 12:03
 * 풀이 시간 : 12분
 *
 * 문제 해석
 * 방향 없는(양방향) 그래프가 주어졌을 때 연결 요소의 개수를 출력하는 프로그램
 *
 * / 연결 요소란? /
 * 하나의 정점에서 도달할 수 있는 모든 정점의 집합을 하나의 연결 요소라고 함
 * 즉 연결 요소끼리는 서로 접점이 없음
 * 연결 요소의 조건
 * 1. 연결 요소에 속한 모든 정점을 연결하는 경로가 있어야 한다
 * 2. 다른 연결 요소에 속한 정점과 연결하는 경로가 있으면 안된다
 *
 * 구해야 하는 것
 * 연결 요소의 개수
 *
 * 문제 입력
 * 첫째 줄 : 정점의 개수 N, 간선의 개수 M
 * 둘째 줄부터 M개의 줄 : 간선의 양 끝점 u와 v
 *
 * 제한 요소
 * 1 <= N <= 1000, 0 <= M <= N * (N - 1) / 2
 * 정점 번호는 1 ~ N
 *
 * 생각나는 풀이
 * 입력에 맞게 그래프를 구현한다
 * 방문하지 않은 정점이라면 그래프를 탐색해 모든 방문 가능한 정점을 방문처리 한다
 * 그래프 탐색 횟수 = 연결 요소의 개수가 된다
 *
 * 구현해야 하는 기능
 * 1. 입력에 맞게 그래프 구현
 * 2. 그래프 탐색 (BFS or DFS)
 * 3. 탐색 횟수 카운트
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_11724 {
    static ArrayList<Integer>[] graph; // 그래프 구현용 ArrayList배열
    static boolean[] visited; // 방문 여부 판별 배열

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) graph[i] = new ArrayList<>(); // 배열 각 인덱스에 ArrayList 할당

        // 1. 입력에 맞게 그래프 구현
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());

            // 양방향 간선이므로 두 방향 모두 생성
            graph[start].add(end);
            graph[end].add(start);
        }

        int cnt = 0;
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                // 2. 그래프 탐색 (BFS or DFS)
                dfs(i);
                // 3. 탐색 횟수 카운트
                cnt++;
            }
        }

        System.out.println(cnt);
    }

    // dfs 메서드
    private static void dfs(int prev) {
        visited[prev] = true;

        for (int now : graph[prev]) {
            if (visited[now]) continue;
            dfs(now);
        }
    }
}
