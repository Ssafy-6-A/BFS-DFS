package common;

import java.util.*;

/**
 * 반시계 방향 90도 회전이면 왼쪽으로 도는거지
 * 
 * 이 문제는 청소 안한칸이 0
 * 벽이 1이므로 청소한 칸을 따로 설정해줘야함
 * 
 */

public class BOJ_14503 {

	static int n;
	static int m;
	static int r,c,d;
	static int[][] board;
	static int cnt = 1;
	
	//deltas를 북동남서로 설정
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,1,0,-1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();
		r = sc.nextInt();
		c = sc.nextInt();
		d = sc.nextInt();
		
		board = new int[n][m];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				board[i][j] = sc.nextInt();
			}
		}
		
		dfs(r, c, d);
        System.out.println(cnt);
		
	}
	
	public static void dfs(int x, int y, int direction) {
		//청소했으므로 다시 올 필요 없다는 의미에서 -1로 설정
		board[x][y]=-1;
		
		for(int i=0;i<4;i++) {
			//방향을 왼쪽으로
			direction = (direction+3)%4;
			
			int nx = x+dx[direction];
			int ny = y+dy[direction];
			
			//경계선 체크
			if(nx<0 || ny<0 || nx>=n || ny>=m)continue;
			//위에서 청소한칸은 -1로 바꿔줬기 때문에 방문하지않고(==청소하지 않고) 벽도 아니라면 해당 칸은 0이어야함
			//즉, 청소해야할 곳이라면
			if(board[nx][ny]==0) {
				//청소한 구역의 수를 하나 증가시켜주고
				cnt++;
				dfs(nx,ny,direction);
				
				//return하는 이유 : return 안하면 복귀중에 다른 곳 청소 가능
				//즉 일반적인 dfs는 밑에 node가 없으면 상위의 node로 되돌아 와서 해당 위치부터 다시 계산하는데, 이 문제의 경우 돌아오는 것이 후진이라는 개념으로 존재하기 때문에 하면 안됨.
				return;
			}
			
		}
		
		//반복문 빠져나왔을 때 == 주변에 청소할 곳이 없을 때
		//후진을 해줘야하는 경우
		int back_dir = (direction+2)%4;	//반대 방향으로 가려면 +2를 하거나 -2를 해줘야하는데 4로 나눈 나머지로 일정하게 유지
		//후진 후 x좌표, y좌표
		int b_x = x+dx[back_dir];	
		int b_y = y+dy[back_dir];
		if(b_x>=0 && b_y>=0 && b_x<n && b_y<m && board[b_x][b_y]!=1) {
			dfs(b_x,b_y,direction);
		}
		
	}
	
}
