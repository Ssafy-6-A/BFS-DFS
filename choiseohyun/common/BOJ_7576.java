package choiseohyun.common;

import java.util.*;

//x,y좌표와 길이를 Node에서 관리한다.
class Node{
	int x,y,len;
	Node(int x, int y, int len){
		this.x = x;
		this.y = y;
		this.len = len;
	}
}


public class BOJ_7576 {
	
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static int[][] arr;
	static Queue<Node> q = new LinkedList<Node>();
	static int tomato=0; //익은토마토+안익은 토마토 갯수 카운트
	static int result=0; //모든 토마토가 익는데 걸리는 일수 카운트 
	
	//토마토가 익는 일수를 계산한다. 최단경로를 구하는것이므로 BFS를 사용
	public static void solution() {
		while(!q.isEmpty()) {
			Node node = q.poll();
			tomato--;
			for(int i=0; i<4; i++) {
				int nx = node.x+dx[i];
				int ny = node.y+dy[i];
				if(nx>=0 && ny>=0 && nx<arr.length && ny<arr[0].length && arr[nx][ny]==0) {
					arr[nx][ny] = 1;
					q.offer(new Node(nx,ny,node.len+1)); //len일차에 익은 토마토에 영향 받은것이므로 len+1일차로 표시
					result = node.len+1;
				}
			}
		}
		return;
	}
	
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int m = sc.nextInt();
		int n = sc.nextInt();
		arr = new int[n][m];
		tomato = n*m;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				arr[i][j]=sc.nextInt();
				if(arr[i][j]==1) {
					q.offer(new Node(i,j,0));
				} else if(arr[i][j] == -1) tomato--; //익은 토마토 + 안익은 토마토의 갯수 셋팅
			}
		}
		
		solution();
		
		if(tomato==0) System.out.println(result); //모든 토마토가 익었으면 일수 출력 
		else System.out.println(-1); //안익은 토마토가 있다면 -1을 출력
		
	}
}
