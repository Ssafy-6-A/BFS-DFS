import java.util.*;

/** DFS와 BFS
 * 방문할 수 있는 정점이 여러개인 경우 작은것부터 방문, 정점번호는 1부터 N까지임
 * input : 정점N개와 간선M개, 탐색 시작번호V
 * output : DFS 탐색결과 \n BFS 탐색결과
 */

public class Main {
	static int[][] arr;
	static int n,m;
	static boolean[] check1;
	static boolean[] check2;

	public static void DFS(int v) {
		System.out.print(v+" ");
		for(int i=1; i<=n; i++) {
			if(arr[v][i]==1 && check1[i]==false) {
				check1[i] = true;
				DFS(i);
			}
		}
	}

	public static void BFS(int v) {
		Queue<Integer> q = new LinkedList<>();
		q.offer(v);
		check2[v]=true;
		while(!q.isEmpty()) {
			int curr = q.poll();
			System.out.print(curr+" ");
			for(int i=1; i<=n; i++) {
				if(arr[curr][i] == 1 && check2[i] == false) {
					q.offer(i);
					check2[i] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt(); //정점번호는 1~n
		m = sc.nextInt(); //간선갯수
		int v = sc.nextInt(); //탐색시작점
		
		arr = new int[n+1][n+1];
		check1 = new boolean[n+1];	
		check2 = new boolean[n+1];	
		for(int i=0; i<m; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			arr[x][y] = 1;
			arr[y][x] = 1; //무방향 그래프이므로
		}
		check1[v]=true;
		DFS(v);
		System.out.println();
		BFS(v);
		
	}
}
