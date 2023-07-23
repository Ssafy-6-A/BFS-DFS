package kimyongjun.common;
/**
 * 풀이 시작 21:52
 * 풀이 완료 22:12
 *
 * 문제 해석
 * 토마토는 익은 토마토, 익지 않은 토마토 2종류
 * 익은 토마토의 인접한 곳에 있는 익지 않은 토마토는 하루 뒤 익게 됨
 * (인접함 = 토마토의 상하좌우)
 * 토마토가 최소 며칠 후 모두 익게 되는지?
 *
 * 구해야 하는 것
 * 토마토가 다 익는데 걸리는 최소 일수
 *
 * 문제 입력
 * N * M 크기의 입력 (2 <= N, M <= 1000)
 * 1 = 익은 토마토, 0 = 익지 않은 토마토, -1 = 빈 칸
 * 토마토가 하나 이상 있는 경우만 입력으로 주어짐
 *
 * 출력 조건
 * 토마토가 모두 익는 최소 날짜 1줄 출력
 * 토마토가 모두 익지 못한다면 -1 출력, 처음부터 모두 익었다면 0 출력
 *
 * 생각나는 풀이
 * 인접한 칸끼리 연결되어 있는 그래프 문제, 인접 탐색 => BFS
 * 델타 사방 탐색
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_7576 {
    static int N, M;
    static int[][] visited; // 해당 칸에 방문했다면 현재 날짜로 변경, 익지 않은 토마토의 위치는 -1
    static Queue<Tomato> q = new LinkedList<>(); // 익은 토마토를 저장하는 Queue

    static class Tomato { // 현재 행, 열 인덱스와 현재 날짜를 가진 클래스
        int x, y, day;

        public Tomato(int x, int y, int day) {
            this.x = x;
            this.y = y;
            this.day = day;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        visited = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int nowValue = Integer.parseInt(st.nextToken());
                if (nowValue == 0) { // 현재칸이 익지 않은 토마토라면 방문하지 않았다는 의미로 배열 값 -1 저장
                    visited[i][j] = -1;
                }else if (nowValue == 1) { // 현재칸이 익은 토마토라면 익은 토마토 Queue에 저장
                    q.offer(new Tomato(i, j, 0));
                }
            }
        }

        bfs();
        System.out.println(getAns());
    }

    private static void bfs() {
        // 인접 사방 토마토 탐색용 델타 배열
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        while (!q.isEmpty()) {
            Tomato now = q.poll();

            for (int i = 0; i < 4; i++) {
                int nextX = now.x + dx[i];
                int nextY = now.y + dy[i];

                // 배열 범위를 벗어났거나 이미 방문했다면 continue
                if (!isInRange(nextX, nextY) || visited[nextX][nextY] != -1) continue;
                // 그렇지 않다면 방문 배열의 현재 인덱스의 값을 방문 날짜로 변경 후 익은 토마토 Queue에 삽입
                visited[nextX][nextY] = now.day + 1;
                q.offer(new Tomato(nextX, nextY, now.day + 1));
            }
        }
    }

    // 토마토가 전부 익은 날짜를 반환하는 메서드
    private static int getAns() {
        int ans = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (visited[i][j] == -1) { // 탐색 후에도 익지 않은 토마토가 있다면 return -1
                    return -1;
                }
                ans = Math.max(ans, visited[i][j]);
            }
        }
        return ans;
    }

    // 배열 범위 내인지 체크하는 메서드
    private static boolean isInRange(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
