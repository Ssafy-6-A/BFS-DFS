package choiseohyun.common;
import java.util.*;
//가독성을 위해 현재 방문한 위치를 노드 클래스로 관리 (x행, y열, d방향)
class Node {
	Node(int x, int y, int d){
		this.x = x;
		this.y = y;
		this.d = d;
	}
	int x;
	int y;
	int d;
}

public class BOJ_14503 {
	
	static int[][] arr;
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static int[][] go = {{-1,0},{0,1},{1,0},{0,-1}}; //북,동,남,서쪽으로 이동하려면 더하면 됨
	static int result = 0;
	
	//작동이 멈출때까지 청소할수있는 칸의 갯수를 카운트한다
	public static void solution(Node node) {
		result ++;
		arr[node.x][node.y] = -1; //방문노드는 -1로 표시한다.(벽/빈칸/방문노드 동작이 다 다르므로)
		
		//주변 4칸 탐색하여 분기문 처리
		boolean flag = false;
		for(int i=0; i<4; i++) {
			int nx = node.x+dx[i];
			int ny = node.y+dy[i];
			if(arr[nx][ny] == 0) flag = true; 
		}
		
		//주변4칸중 청소할칸이 없을경우 - 방향은 유지한채로 (1)후진가능시 후진 (2)후진불가(벽)시 리턴
		if(!flag) {
			int nx = node.x+((-1)*go[node.d][0]); //후진의경우 해당 방향벡터에 -1을 곱하면 됨(ex.북쪽 {-1,0} <-> 남쪽 {1,0})
			int ny = node.y+((-1)*go[node.d][1]);
			if(arr[nx][ny] == 1) return; //벽일때 멈춤
			else { //후진 가능시 재귀로 계속해서 검색한다.
				result--; //후진하면 이미 방문한 노드일것이므로 result++하면 안됨, 따라서 도로 빼줌
				solution(new Node(nx,ny,node.d));
			}
		} else{
		//주변 4칸중 청소할칸이 있을경우 - 반시계로 90도 회전, 앞쪽칸이 청소되지 않았으면 전진, 그리고 1번 실행
			int nd = node.d;
			for(int i=0; i<4; i++) {				
				nd -= 1; //다음 방향 노드를 사방탐색
				if(nd == -1) nd = 3;
				
				//청소되지 않은 노드라면 재귀로 계속해서 검색
				int nx = node.x+(go[nd][0]);
				int ny = node.y+(go[nd][1]);
				if(arr[nx][ny] == 0) { 
					solution(new Node(nx,ny,nd));
					break;
				}
			}
		}
		
		return;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		arr = new int[n][m];
		
		int r = sc.nextInt();
		int c = sc.nextInt();
		int d = sc.nextInt(); // 0:북, 1:동, 2:남, 4:서
		
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				arr[i][j] = sc.nextInt();
			}
		}
		
		solution(new Node(r,c,d));
		System.out.println(result);
		
		
		
	}

}