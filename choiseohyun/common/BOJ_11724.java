
import java.util.*;

/** DFS와 BFS
 * 방문할 수 있는 정점이 여러개인 경우 작은것부터 방문, 정점번호는 1부터 N까지임
 * input : 정점N개와 간선M개, 탐색 시작번호V
 * output : DFS 탐색결과 \n BFS 탐색결과
 */

public class Main {
	static int[][] arr;
	static int n,m;
	static boolean[] check;

	public static void DFS(int v) {
		// 기준노드에 대하여 깊이우선탐색한다
		if(check[v] == true) {
			return;
		}else{
			check[v] = true;
			for(int i=1; i<=n; i++) {
				if(arr[v][i]==1 && check[i]==false) DFS(i);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //정점번호는 1~n
		m = sc.nextInt(); //간선갯수
		
		arr = new int[n+1][n+1];
		check = new boolean[n+1];	
		for(int i=0; i<m; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			arr[x][y] = 1;
			arr[y][x] = 1; //무방향 그래프이므로
		}
		
		int result = 0;
//		// 행이 기준노드, 열이 연결노드라고 가정
//		for(int i=1; i<=n; i++) {
//			for(int j=1; j<=n; j++) {
//				//이렇게 하면 arr[i][j]가 0인, 즉 간선이 없는 정점에 대한 처리가 불가능함/
//				if(arr[i][j]==1 && check[i] == false) { 
//					DFS(i);
//					result++;
//				}
//			}
//		}
		
		for(int i=1; i<=n; i++) {
			if(check[i] == false) {
				DFS(i);
				result++;
			}
		}
		System.out.println(result);
		
	}
}
