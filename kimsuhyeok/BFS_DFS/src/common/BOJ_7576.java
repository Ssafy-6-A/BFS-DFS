package common;

import java.awt.Point;
import java.util.Queue;
import java.util.Scanner;
import java.util.LinkedList;

/**
 * 안풀렸어요 ㅠㅠ 크흑
 */

class point{
	int x;
	int y;
	public point(int x, int y){
		this.x=x;
		this.y=y;
	}
}

public class BOJ_7576 {
	
	static int[][] board;
	static boolean[][] visited;
	static int n;
	static int m;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int cnt = 0;
	static Queue<point> queue;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		queue = new LinkedList<point>();
		
		Scanner sc = new Scanner(System.in);
		m = sc.nextInt();
		n = sc.nextInt();
		
		board = new int[n][m];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				board[i][j] = sc.nextInt();
				
				if(board[i][j]==1) {
					queue.offer(new point(i,j));
				}
			}
		}
		System.out.println(bfs());
	}
	
	public static int bfs() {
		while(!queue.isEmpty()) {
			point node = queue.poll();
			
			int x = node.x;
			int y = node.y;
			
			for(int d=0;d<4;d++) {
				int nx = x+dx[d];
				int ny = y+dy[d];
				
				if(nx<0 || ny<0 || nx>=n || ny>=m) continue;
				if(board[nx][ny]==0) {
					queue.offer(new point(nx,ny));
					board[nx][ny] = board[x][y]+1;
				}
			}
			
		}
		int day = Integer.MIN_VALUE;
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				if(board[i][j]==0) {
					return -1;
				}
				day = Math.max(day, board[i][j]);
			}
		}
		if(day==1) {
			return 0;
		}
		else {
			return day-1;
		}
	}

}
