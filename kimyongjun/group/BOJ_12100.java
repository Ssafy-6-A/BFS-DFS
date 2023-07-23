package kimyongjun.group;
/*
풀이 시작 20:03
풀이 완료 21:53
문제 분석
보드를 움직여 이동시키는 게임
1. 보드는 상하좌우로 움직일 수 있다
2. 1의 결과로 같은 값을 갖는 두 블록이 충돌하면 두 블록은 하나로 합쳐지며 크기가 2배가 된다.
2-1. 한 번의 이동에서 이미 합쳐진 블록은 또 다른 블록과 합쳐질 수 없다.
2-2. 똑같은 수가 세 개 있는 경우 이동하려고 하는 쪽의 칸이 먼저 합쳐진다

구해야 하는 값
최대 5번의 이동으로 만들 수 있는 가장 큰 블록의 값

입력
첫 줄 : 보드의 크기 N
둘째 줄 ~ N + 1번째 줄(N개 줄) : 게임판의 i행의 초기 상태
0은 빈칸, 이외의 값은 모두 블록, 블록의 값은 2 <= k = (2의 제곱꼴) <= 1024
블록은 적어도 하나 주어짐

만들어야 하는 기능
1. 방향 체크
2. 보드 움직임 4방향
2-1. 모든 블록을 1번 방향의 가능한 끝으로 밀기
2-2. 순서는 방향에 가까운 블록부터 이동
3. 블록 병합
3-1. 목적 방향에 존재하는 앞 블록이 값이 같은지 체크
3-2. 이번 움직임에서 해당 블록이 병합되었는지 체크
4. 시뮬레이션이 끝난 후 블록의 최대 크기 구하기

수행횟수
최대 5회 움직임, 4방향 = 4^5 = 1024회 시뮬레이션
움직임 당 보드판 탐색 = N^2 (N <= 20) = 최대 400
시뮬레이션이 끝나고 보드판 완전탐색 = N^2 = 최대 400
예상 수행횟수 = 1024 * 400 + 400 = 41만 < 1억이므로 시간 제한 내
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_12100 {
    static int N, max = 0;
    static int[][] board;
    static boolean[][] changed;
    static int[] direction = new int[5]; // 1. 방향 체크, dir 상하좌우 순

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        board = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        backTracking(0);
        System.out.println(max);
    }

    /**
     * 모든 움직이는 경우 시뮬레이션
     * 5번의 움직임 {상, 상, 상, 상, 상} ~ {우, 우, 우, 우, 우}까지 모든 경우
     */
    private static void backTracking(int depth) {
        if (depth == 5) { // 5번의 움직이는 방향이 모두 정해졌다면
            // board 초기 상태 복사
            int[][] nowBoard = new int[N][];
            for (int i = 0; i < N; i++) {
                nowBoard[i] = board[i].clone();
            }

            // 움직임 5회
            for (int i = 0; i < 5; i++) {
                changed = new boolean[N][N]; // 병합되었는지 체크하는 배열
                moveBoard(direction[i], nowBoard);
            }

            max = Math.max(max, findMax(nowBoard));
            return;
        }

        for (int i = 0; i < 4; i++) {
            direction[depth] = i;
            backTracking(depth + 1);
        }
    }

    private static void moveBoard(int dir, int[][] nowBoard) {
        //2. 보드 움직임 4방향
        if (dir == 0) {
            moveTop(nowBoard);
        } else if (dir == 1) {
            moveBottom(nowBoard);
        } else if (dir == 2) {
            moveLeft(nowBoard);
        } else {
            moveRight(nowBoard);
        }
    }

    //2-1. 모든 블록을 1번 방향의 가능한 끝으로 밀기
    //2-2. 순서는 방향에 가까운 블록부터 이동
    private static void moveTop(int[][] nowBoard) {
        for (int i = 1; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (nowBoard[i][j] == 0) continue; // 블록이 있을 때만 동작
                int nowValue = nowBoard[i][j]; // 현재 블록의 값
                nowBoard[i][j] = 0; // 현재 위치 블록 값 0으로 변경
                int nextX = i - 1; // 바로 앞 인덱스부터
                while ((nextX > 0 && nowBoard[nextX][j] == 0)) { // 앞에 블록이 있거나 보드 끝에 도달하기 전까지 반복
                    nextX--;
                }


                if (nowBoard[nextX][j] == 0) { // 앞에 아무 블록도 없이 끝까지 탐색했다면
                    nowBoard[nextX][j] = nowValue; // 그 위치로 블록 이동
                } else {
                    //3. 블록 병합
                    //3-1. 목적 방향에 존재하는 앞 블록이 값이 같은지 체크
                    //3-2. 이번 움직임에서 해당 블록이 병합되었는지 체크
                    if (nowBoard[nextX][j] == nowValue && !changed[nextX][j]) {
                        nowBoard[nextX][j] = nowBoard[nextX][j] << 1; // 비트 왼쪽으로 1칸 이동 => 기존 값의 2배
                        changed[nextX][j] = true; // 이번 움직임에서 합쳐졌다는 것을 의미
                    } else { // 앞에 블록이 있지만 병합하지 못하는 경우 그 블록 바로 뒤로 블록 이동
                        nowBoard[nextX + 1][j] = nowValue;
                    }
                }
            }
        }
    }

    private static void moveBottom(int[][] nowBoard) {
        for (int i = N - 2; i >= 0; i--) {
            for (int j = 0; j < N; j++) {
                if (nowBoard[i][j] == 0) continue;
                int nowValue = nowBoard[i][j];
                nowBoard[i][j] = 0;
                int nextX = i + 1;
                while ((nextX < N - 1 && nowBoard[nextX][j] == 0)) {
                    nextX++;
                }

                if (nowBoard[nextX][j] == 0) {
                    nowBoard[nextX][j] = nowValue;
                } else {
                    if (nowBoard[nextX][j] == nowValue && !changed[nextX][j]) {
                        nowBoard[nextX][j] = nowBoard[nextX][j] << 1;
                        changed[nextX][j] = true;
                    } else {
                        nowBoard[nextX - 1][j] = nowValue;
                    }
                }
            }
        }
    }
    private static void moveLeft(int[][] nowBoard) {
        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                if (nowBoard[i][j] == 0) continue;
                int nowValue = nowBoard[i][j];
                nowBoard[i][j] = 0;
                int nextY = j - 1;
                while ((nextY > 0 && nowBoard[i][nextY] == 0)) {
                    nextY--;
                }

                if (nowBoard[i][nextY] == 0) {
                    nowBoard[i][nextY] = nowValue;
                } else {
                    if (nowBoard[i][nextY] == nowValue && !changed[i][nextY]) {
                        nowBoard[i][nextY] = nowBoard[i][nextY] << 1;
                        changed[i][nextY] = true;
                    } else {
                        nowBoard[i][nextY + 1] = nowValue;
                    }
                }
            }
        }
    }

    private static void moveRight(int[][] nowBoard) {
        for (int i = 0; i < N; i++) {
            for (int j = N - 2; j >= 0; j--) {
                if (nowBoard[i][j] == 0) continue;
                int nowValue = nowBoard[i][j];
                nowBoard[i][j] = 0;
                int nextY = j + 1;
                while ((nextY < N - 1 && nowBoard[i][nextY] == 0)) {
                    nextY++;
                }

                if (nowBoard[i][nextY] == 0) {
                    nowBoard[i][nextY] = nowValue;
                } else {
                    if (nowBoard[i][nextY] == nowValue && !changed[i][nextY]) {
                        nowBoard[i][nextY] = nowBoard[i][nextY] << 1;
                        changed[i][nextY] = true;
                    } else {
                        nowBoard[i][nextY - 1] = nowValue;
                    }
                }
            }
        }
    }

    //4. 시뮬레이션이 끝난 후 블록의 최대 크기 구하기
    private static int findMax(int[][] nowBoard) {
        int nMax = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nMax = Math.max(nMax, nowBoard[i][j]);
            }
        }

        return nMax;
    }
}