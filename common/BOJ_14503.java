package ssafy.algo;

import java.util.Scanner;

// ############실패 코드, 예제는 모두 정답이나 제출 시 오답############

public class BOJ_14503 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n, m, r, c, d;
        // n, m: 방의 크기
        n = sc.nextInt();
        m = sc.nextInt();
        // (r, c): 현재 방의 좌표
        r = sc.nextInt();
        c = sc.nextInt();
        // d: 로봇이 바라보는 방향(0: 북, 1: 동, 2: 남, 3: 서)
        d = sc.nextInt();

        // 방 정보 입력
        int[][] arr = new int[n][m];
        for(int i=0; i<n; i++) {
            for(int j=0; j<m; j++) {
                arr[i][j] = sc.nextInt();
            }
        }

        // 4방 탐색 - 북: {-1, 0}, 동:{0, 1}, 남:{1, 0}, 서:{0, -1}
        int[][] deltas = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };

        /*
        0: 청소되지 않은 빈칸, 1: 벽
        1. 현재 칸이 청소 안됨 - 현재 칸 청소
        2. 현재 칸의 주변 4칸이 모두 청소되어 있으면
            a) 바라보는 방향 유지하고 한 칸 후진 가능하면 한칸 후진하고 1번으로 복귀
            b) 바라보는 방향이 뒤쪽 칸이 벽인 경우 후진 불가능 - 작동 멈춤
        3. 현재 칸의 주변 4칸 중 청소 안 된 칸 있으면
            a) 반시계 방향으로 회전(북->서->남->동)
            b) 바라보는 방향 기준 앞 쪽 칸이 청소 안되있으면 한 칸 전진
            c) 1번 복귀
        !주의: 청소한 타일도 지나갈 수 있음!
         */

        // 시작 지점 설정
        int currX = r, currY = c;
        int cleanTile = 0;
        while(true) {
            // 1. 현재 칸이 청소 안되어 있으면 현재 칸 청소
            if (arr[currX][currY] == 0) {
                cleanTile++;
                // 청소를 한 칸은 다시 청소할 필요가 없으므로 별도의 값으로 청소했음을 표시
                arr[currX][currY] = -1;
            }

            // 4방이 모두 청소되어 있는지 확인
            boolean isClean = true;
            int dx, dy;
            for(int i=0; i<4; i++) {
                dx = currX + deltas[i][0];
                dy = currY + deltas[i][1];
                // 사방 탐색을 범위 안에서 할 때 (배열 바깥에 모두 1로 둘러싸여 있지만 안정성을 위해 코드를 남겨둠)
                // 청소하지 않은 빈 칸을 만나면 4방이 모두 청소됨은 거짓, break로 반복문 탈출
                if ((dx>=0 && dx<n && dy>=0 && dy<n) && arr[dx][dy] == 0) {
                    isClean = false;
                    break;
                }
            }
            // 2. 현재 칸의 주변 4칸이 모두 청소되어 있으면
            if (isClean) {
                // a) 바라보는 방향 유지하고 한 칸 후진 가능하면 한칸 후진하고 1번으로 복귀
                // 후진이므로 원래 방향의 반대(-)방향으로 이동
                dx = currX - deltas[d][0];
                dy = currY - deltas[d][1];
                // 후진했을 때 벽이 아님 - 후진 가능
                if (arr[dx][dy] != 1) {
                    currX = dx;
                    currY = dy;
                    continue;
                }
                // b) 바라보는 방향이 뒤쪽 칸이 벽인 경우 후진 불가능 - 작동 멈춤
                else {
                    break;
                }
            }
            // 3. 현재 칸의 주변 4칸 중 청소 안 된 칸 있으면
            else {
                // a) 반시계 방향으로 회전(북(0)->서(3)->남(2)->동(1))
                // 0->3->2->1 순서로 가는 것은 4~1까지 4의 나머지로 처리
                for(int j=4; j>0; j++) {
                    // 확인만 하기 위해 임시로 보기만 함
                    int tempDx = currX + deltas[j%4][0];
                    int tempDy = currY + deltas[j%4][1];
                    // b) 바라보는 방향 기준 앞 쪽 칸이 청소 안되있으면 한 칸 전진
                    if (arr[tempDx][tempDy] == 0) {
                        currX = tempDx;
                        currY = tempDy;
                        d = j%4;
                        break;
                    }
                    // else에 들어오면서 무조건 청소 안된 칸이 하나 이상이므로 별도의 break문 필요 - 앞에서 모든 경우의 수 확인
                }
                // c) 1번 복귀
            }
        }
        System.out.println(cleanTile);

    }

}
