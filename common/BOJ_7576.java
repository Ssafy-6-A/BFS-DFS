package ssafy.algo;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * ############실패 코드, 예제는 모두 정답이나 제출 시 오답############
 * 1. 토마토 4방탐색 -> BFS에서 두 개 이상의 연결 요소가 나오면 -1 출력
 * 2. BFS 탐색 - 4방으로 연결되어 있는 것으로 정의
 * 인접한 토마토들을 확인하면서 진행하므로 BFS로 풀기!
 */

// 토마토의 위치를 (x,y) 형태로 저장
class Pair {
    int x, y;

    Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class BOJ_7576 {

    static int m, n;
    static int[][] arr;
    static int answer = 0;
    // 탐색한 토마토들을 저장
    static Queue<Pair> queue = new LinkedList<Pair>();

    // BFS 탐색
    public static void BFS(int x, int y) {
        // 처음 입력받은 익은 토마토의 위치를 큐에 저장
        queue.add(new Pair(x, y));
        // 탐색할 방향(상, 하, 좌, 우)을 지정
        int[][] delta = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        // 탐색할 점의 상하좌우의 위치

        do {
            // queue에 있는 토마토의 위치에서 탐색
            Pair currentNode = queue.poll();
            x = currentNode.x; // 토마토 위치 지정
            y = currentNode.y;
            // 4방탐색
            for (int d=0; d<4; d++) {
                int dx = x + delta[d][0];
                int dy = y + delta[d][1];
                // 탐색하고자 하는 토마토가 범위 내에 있고 and 안 익었는지 확인
                if ((dx>=0 && dx<n && dy>=0 && dy<m) && arr[dx][dy] == 0) {
                    // 하나라도 안익은 토마토가 있으면 익은 것으로 처리
                    // 이전까지 통과한
                    arr[dx][dy] = arr[x][y] + 1;
                    // 방근 익은 토마토에서부터 다시 탐색해야하므로 queue에 삽입
                    queue.add(new Pair(dx, dy));
                }
            }
        } while (!queue.isEmpty()); // 탐색을 완료할 때 까지 실행

        // 배열 내에서 가장 큰 요소 값을 구함
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {
                answer = Math.max(answer, arr[i][j]);
            }
        }
        // 초기 토마토 시작점을 시작일인 0이 아닌 1로 시작했으므로 1을 감소시킴
        answer--;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        m = sc.nextInt();
        n = sc.nextInt();
        arr = new int[n][m];

        Pair pair = new Pair(0, 0);
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                arr[i][j] = sc.nextInt();
                if (arr[i][j] == 1) {
                    pair.x = i;
                    pair.y = j;
                    // 익은 토마토가 있는 장소들을 queue에 추가, 초기 익은 토마토는 여러 개 있을 수 있기 때문.
                    queue.add(new Pair(i, j));
                }
            }
        }

        // 2. DFS 탐색
        // 시작점: 익은 토마토가 있는 위치
        BFS(pair.x, pair.y);
        // 만약 탐색 후 아직 익지 않은 토마토가 있다면 = 연결 요소가 2개 이상 -> 더이상 익을 수 없으므로 -1 출력
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                if (arr[i][j] == 0)
                    answer = -1;
            }
        }
        // System.out.println(Arrays.deepToString(arr));
        System.out.println(answer);
    }

}

/*
 * start (x, y)
 * search (x-1, y), (x+1, y), (x, y-1), (x, y+1)
 *     if (in boundary) and (unchecked tomato available)
 *         unchecked tomato -> checked tomato
 *         search from that tomato again
 * */