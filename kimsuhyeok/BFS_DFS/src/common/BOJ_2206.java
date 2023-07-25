package common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.awt.Point;

/**
 * (1,1)과 (n,m)은 항상 0
 * 최단거리 출력
 * 
 */

class Node{
	int x;
	int y;
	int crash;
	public Node(int x, int y, int crash) {
		this.x = x;
		this.y = y;
		this.crash = crash;
	}
}

public class BOJ_2206 {
	
	static int n;
	static int m;
	
//	static char[][] graph;
	static int[][] graph;
	static int[][][] visited;
	
	static Queue<Node> queue;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int cnt;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();
		
//		graph = new char[n][m];
		graph = new int[n][m];
		for(int i=0;i<n;i++) {
			for(int j=0;j<m;j++) {
				graph[i][j] = sc.next().charAt(j);
			}
		}
		
		queue = new LinkedList<Node>();
		queue.offer(new Node(0,0,0));
		cnt = 1;
		
		System.out.println(bfs());
	}
	
	public static int bfs() {
		while(!queue.isEmpty()) {
			Node node = queue.poll();
			
			int cx = node.x;
			int cy = node.y;
			int crash = node.crash;
			if(cx == n-1 && cy==m-1) {
				return visited[cx][cy][crash];
			}
			
			for(int d=0;d<4;d++) {
				int nx = cx+dx[d];
				int ny = cy+dy[d];
				
				//경계선 체크
				if(nx>=0 && ny>=0 && nx<n && ny<m) {
//					if(graph[nx][ny]=='0' && visited[nx][ny][crash]==0) 
					if(graph[nx][ny]==0 && visited[nx][ny][crash]==0)
					{
						visited[nx][ny][crash] = visited[nx][ny][crash]+1;
						queue.offer(new Node(nx,ny,node.crash));
					}
//					if(graph[nx][ny]=='1' && visited[nx][ny][crash]==0) 
					if(graph[nx][ny]==1 && visited[nx][ny][crash]==0) 
					{
						if(crash==0) {
							visited[nx][ny][1] = visited[nx][ny][crash]+1;
							queue.offer(new Node(nx,ny,1));
						}
						if(crash==1) {
							continue;
						}
					}
				}
			}
		}
		return -1;
	}

}
