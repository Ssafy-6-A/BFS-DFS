package kimyongjun.common;
/**
 * 풀이 시작 : 21:33
 * 풀이 완료 : 22:22
 * 풀이 시간 :
 *
 * 문제 해석
 * 로봇 청소기와 방의 상태가 주어졌을 때 청소하는 영역의 개수를 구하는 프로그램
 * 방은 N * M 크기의 직사각형, 1 * 1의 정사각형으로 나뉘어 있음
 * 청소기는 바라보는 방향이 있음 (동서남북 중 하나)
 * 좌표의 범위는 최서북단(0, 0) ~ 최동남단(N - 1, M - 1)까지
 * 처음 빈칸은 모두 청소되지 않은 상태
 *
 * 구해야 하는 것
 * 작동을 멈출 때까지 청소하는 영역의 개수
 *
 * 제한 사항
 * 방의 가장자리는 모두 벽으로 되어 있음
 * 행과 열의 범위 3 <= N, M <= 50
 *
 * 입력
 * 첫째 줄 : 방의 세로, 가로 길이 N, M
 * 둘째 줄 : 처음 로봇청소기가 있는 칸의 좌표 (r, c)와 바라보는 방향 d (방향 0 북, 1 동, 2 남, 3 서)
 * 셋째 줄부터 N개의 줄 : 각 장소의 상태를 나타냄
 * 0이라면 청소되지 않은 빈 칸, 1이라면 벽
 * 로봇청소기가 있는 칸은 항상 빈 칸
 *
 * 로봇청소기의 작동 방식
 * 1. 현재 칸이 작동되지 않은 경우, 현재 칸 청소
 * 2. 현재 칸의 주변 4칸 (상하좌우) 중 청소되지 않은 빈 칸이 있는 경우
 *  2-1. 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 후진 후 1번으로 이동
 *  2-2. 2-1이 가능하지 않다면 종료
 * 3. 현재 칸 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우
 *  3-1. 반시계 방향으로 90도 회전
 *  3-2. 바라보는 칸 앞쪽 칸이 청소되지 않았다면 전진 후 1번으로 이동
 *
 * 구현해야 하는 기능
 * 1. 입력에 따라 방의 상태를 저장하는 배열 생성
 * 2. 현재 칸 청소
 * 3. 주위 4방향 탐색 (델타 배열)
 * 4. 벽인지 판별
 * 5. 후진
 * 6. 반시계 방향으로 90도 회전
 * 7. 전진
 * 8. 로봇청소기의 상태를 저장하는 클래스
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_14503 {
    static int N, M;
    static int[][] room;
    static Robot cleaner;

    // * 3. 주위 4방향 탐색 (델타 배열)
    static int[] dx = {-1, 0, 1, 0}; // 북동남서
    static int[] dy = {0, 1, 0, -1}; // 북동남서

    static class Robot { // * 8. 로봇청소기의 상태를 저장하는 클래스
        int x, y, dir;

        public Robot(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        int dir = Integer.parseInt(st.nextToken());
        cleaner = new Robot(x, y, dir);

        // * 1. 입력에 따라 방의 상태를 저장하는 배열 생성
        room = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int nowValue = Integer.parseInt(st.nextToken());
                room[i][j] = nowValue;

            }
        }

        System.out.println(cleanRoom());
    }

    private static int cleanRoom() {
        int cnt = 0;
        while (true) {
            if (room[cleaner.x][cleaner.y] == 0) {
                cnt++;
                cleanNowArea();
            }

            // * 6. 반시계 방향으로 90도 회전
            boolean hasDirtyArea = false;
            for (int i = 3; i >= 0; i--) {
                if (isDirtyArea(i)) {
                    moveForward(i);
                    hasDirtyArea = true;
                    break;
                }
            }

            if (!hasDirtyArea) {
                if (!isBehindWall()) {
                    moveBackward();
                } else break;
            }
        }

        return cnt;
    }

    // * 2. 현재 칸 청소
    private static void cleanNowArea() {
        room[cleaner.x][cleaner.y] = -1;
    }

    // * 5. 후진
    private static void moveBackward() {
        int backX = cleaner.x - dx[cleaner.dir];
        int backY = cleaner.y - dy[cleaner.dir];

        cleaner.x = backX;
        cleaner.y = backY;
    }

    // * 4. 벽인지 판별
    private static boolean isBehindWall() {
        int behindX = cleaner.x - dx[cleaner.dir];
        int behindY = cleaner.y - dy[cleaner.dir];

        return room[behindX][behindY] == 1;
    }

    // * 7. 전진
    private static void moveForward(int dir) {
        int nextDir = (cleaner.dir + dir) % 4;
        int forwardX = cleaner.x + dx[nextDir];
        int forwardY = cleaner.y + dy[nextDir];

        cleaner.x = forwardX;
        cleaner.y = forwardY;
        cleaner.dir = nextDir;
    }

    // 현재 위치가 더러운 곳인지 판별
    private static boolean isDirtyArea(int dir) {
        int nextDir = (cleaner.dir + dir) % 4;
        int forwardX = cleaner.x + dx[nextDir];
        int forwardY = cleaner.y + dy[nextDir];

        return room[forwardX][forwardY] == 0;
    }
}
