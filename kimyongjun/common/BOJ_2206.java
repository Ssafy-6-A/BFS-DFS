package kimyongjun.common;
/**
 * 풀이 시작 10:11
 * 풀이 완료 11:11, (10:20 ~ 10:43 다른 작업) 걸린 시간 37분
 *
 * 문제 해석
 * N * M의 맵의 (1, 1)에서 (N, M)으로 가야함
 * 벽은 최대 한 번 부수고 갈 수 있음
 * 이동 가능한 위치 : 상하좌우의 맵에서 0으로 표시된 곳
 *
 * 구해야 하는 것
 * 가장 빠르게 도착했을 때 경로의 길이
 *
 * 문제 입력
 * 첫째 줄 : 행의 수 N, 열의 수 M
 * 다음 N개의 줄에 걸쳐 M개의 숫자로 맵이 주어짐
 *
 * 제한 사항
 * 이동은 상하좌우 인접 구역만 가능
 * 이동은 맵의 값이 0인 지점만 가능
 * 1인 지점은 벽을 부순 적이 없는 경우에만 단 한 번 이동 가능
 *
 * 생각나는 풀이
 * 인접 탐색 => BFS
 * BFS는 처음 목적지에 도달하는 시간이 곧 최솟값
 * 탐색 과정에서 벽을 부순 적 있는지 체크해야 함
 * Queue에 들어가는 Node 클래스
 * 멤버 변수 : x좌표, y좌표, 벽을 부쉈는지 여부, 지나온 경로의 길이
 *
 * 구현해야 하는 기능
 * 1. 인접 4방 탐색 용 델타 배열
 * 1-1. 4방 탐색 시 맵의 범위를 벗어나는지 체크하는 메서드
 * 2. Node 클래스
 * 3. 방문 처리용 배열, 3차원 visited[벽 부쉈는지, 안부쉈는지][행좌표][열좌표]
 * 4. bfs 메서드
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_2206 {
    static int N, M;
    static char[][] map;

    // 3. 방문 처리용 배열, 3차원
    // visited[벽 부순 횟수][행 좌표][열 좌표]
    static boolean[][][] visited;

    // 1. 인접 4방 탐색 용 델타 배열
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    static class Node { // 2. Node 클래스
        // 멤버 변수 : x좌표, y좌표, 벽을 부쉈는지 여부, 지나온 경로의 길이
        int x, y, time, wall;

        public Node(int x, int y, int time, int wall) {
            this.x = x;
            this.y = y;
            this.time = time;
            this.wall = wall;
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new char[N][];
        visited = new boolean[2][N][M];
        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
            for (int j = 0; j < M; j++) {
                if (map[i][j] == '1') { // 벽이라면 벽을 부쉈을 때 이동하지 못하도록 미리 방문 처리
                    visited[1][i][j] = true;
                }
            }
        }

        System.out.println(bfs());
    }

    // 4. bfs 메서드
    private static int bfs() {
        Queue<Node> q = new LinkedList<>();
        q.offer(new Node(0, 0, 1, 0));
        visited[0][0][0] = true; // 시작 인덱스 방문 처리

        while (!q.isEmpty()) {
            Node now = q.poll();
            // 현재 좌표가 목적지라면 return now.time
            if (now.x == N - 1 && now.y == M - 1) return now.time;
            for (int i = 0; i < 4; i++) { // 4방탐색
                int nextX = now.x + dx[i];
                int nextY = now.y + dy[i];

                // 맵의 밖이거나 이미 방문했다면 continue
                if (!isInRange(nextX, nextY) || visited[now.wall][nextX][nextY]) continue;
                if (map[nextX][nextY] == '0') { // 가려는 곳이 0일 때는 Queue에 삽입
                    q.offer(new Node(nextX, nextY, now.time + 1, now.wall));
                    visited[now.wall][nextX][nextY] = true;
                } else if (now.wall == 0 && !visited[0][nextX][nextY]) { // 벽을 한 번도 부수지 않았고 가려는 곳이 1인 경우 Queue에 삽입
                    q.offer(new Node(nextX, nextY, now.time + 1, 1));
                    visited[0][nextX][nextY] = true;
                }
            }
        }

        return -1; // 탐색 끝까지 목적지에 도달하지 못했으면 return -1
    }

    // 1-1. 4방 탐색 시 맵의 범위를 벗어나는지 체크하는 메서드
    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
