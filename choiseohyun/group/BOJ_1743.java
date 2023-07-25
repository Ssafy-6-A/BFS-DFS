package choiseohyun.group;
import java.util.*;

/** 음식물 피하기
 * input : N,M,K(음식물 쓰레기의 개수), 쓰레기가 있는 좌표(r,c)
 * output : 음식물 중 가장 큰 음식물의크기를 출력
 */

class Node{
	int x,y,len;
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
public class BOJ_1743 {
	static int[] dx = {0,0,1,-1};
	static int[] dy = {1,-1,0,0};
	static int[][] arr;
	static boolean[][] check;

	public static int BFS(Node node) {
		int result = 1;		
		Queue<Node> q = new LinkedList<>();
		q.offer(node);
		check[node.x][node.y] = true;
		
		while(!q.isEmpty()) {
			Node c_node = q.poll();
			for(int i=0; i<4; i++) {
				int nx = c_node.x+dx[i];
				int ny = c_node.y+dy[i];
				
				if(nx>=0 && nx<arr.length&&ny>=0&&ny<arr[0].length 
						&& arr[nx][ny]==1 && check[nx][ny]==false) {
					check[nx][ny] = true;
					q.offer(new Node(nx,ny));
					result++;
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		arr = new int[n][m];
		check = new boolean[n][m]; //방문노드 확인용 체크배열
		
		int k = sc.nextInt();
		for(int i=0; i<k; i++) {
			int r = sc.nextInt();
			int c = sc.nextInt();
			arr[r-1][c-1] = 1;
		}
		
		int result = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) 
				if(arr[i][j]==1 && check[i][j]==false) { //방문한적 없는 값이 1인 노드를 찾기
					result = Math.max(result, BFS(new Node(i,j)));
				}
		}
		
		System.out.println(result);
	}
}