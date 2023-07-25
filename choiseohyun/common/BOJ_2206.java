package choiseohyun.common;

import java.util.*;

// 못풀었음;; 체크배열이 3차원인게 이해안됨 ㅜ
class Node {
	int x,y,len; //x좌표, y좌표, 거리 변수
	boolean broken; //벽 부순적있는 경로인지 체크
	public Node(int x, int y, boolean broken, int len) {
		this.x = x;
		this.y = y;
		this.broken = broken;
		this.len = len;
	}
}

public class BOJ_2206 {
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,1,-1};
	static int[][] map;
	static int result = Integer.MAX_VALUE;
	static boolean chk = false;
	
	public static void DFS(Node node) {
		if(node.x==map.length-1 && node.y==map[0].length-1) {
			result = Math.min(result, node.len);
			chk = true;
		}
		
		for(int i=0; i<4; i++) {
			int nx = node.x+dx[i];
			int ny = node.y+dy[i];
			if(nx>=0 && nx<map.length && ny>=0 && ny<map[0].length) {
				if(map[nx][ny] == 0) {
					map[nx][ny] = -1;
					DFS(new Node(nx,ny,node.broken,node.len+1));
					map[nx][ny] = 0; //백트래킹?
				}else if(map[nx][ny]==1 && !node.broken) {
					map[nx][ny] = -1;
					DFS(new Node(nx,ny,true,node.len+1));
					map[nx][ny] = 1;
				}
			}
		}
		
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		map = new int[n][m];
		
		//0은 이동가능, 1은 이동불가
		for(int i=0; i<n; i++) {
			String str = sc.next();
			for(int j=0; j<m; j++) {
				map[i][j] = str.charAt(j)-48;
			}
		}
		
		DFS(new Node(0,0,false,1));
		
		if(chk) System.out.println(result);
		else System.out.println(-1);
	}

}