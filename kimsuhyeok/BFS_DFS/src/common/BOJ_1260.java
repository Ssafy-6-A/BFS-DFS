package common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
 * 풀이 시작 시간: 6:00
 * 풀이 종료 시간 6:37
 * 풀이 시간 : 37분
 * 
 * 문제: 문제에서 말한 간선을 통해 그래프를 구현 후, DFS와 BFS를 통해 탐색한 결과를 출력하는 문제
 * 
 * 목표: DFS와 BFS 구현
 * 
 * 문제 입력 : 정점의 개수: N(1<=N<=1000), 간선의 개수: M(1<=M<=10000), 탐색을 시작할 정점의 번호: V, 이후 간선을 연결하는 두 정점의 번호가 M개 주어짐
 * 입력으로 주어지는 간선은 양방향이다.
 * ??? 궁금한 점: 문제에서 어떤 두 정점 사이에 여러 개의 간선이 있을 수 있다. 라고 명시하였는데 이는 어떤 것을 고려하라고 의도한 것인가?
 * 
 * 문제 출력: 첫줄에는 DFS를 수행한 결과, 다음 줄에는 BFS를 수행한 결과 출력
 * V부터 방문된 정점을 순서대로 출력하면 됨.
 *
 * 내가 할 구현의 순서
 * 1. 그래프(2차원 배열) 구현
 * 2. visited 배열(1차원 배열) 구현
 * 3. DFS 구현
 * 4. 위에서 사용한 visited배열을 다시 0으로 초기화
 * 5. BFS 구현
 * 6. 출력은 DFS와 BFS 함수에서 각자 진행
 * 
 * 
 * */

public class BOJ_1260 {

	static int[][] graph;	//그래프 선언
	static boolean[] visited;	//visited 배열 선언
	static int n;	//정점 개수
	static int m;	//간선 개수
	static int v;	//시작 정점
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		//정점의 개수 입력받기
		n = sc.nextInt();
		
		//간선의 개수 입력받기
		m = sc.nextInt();
		
		//시작 정점 입력받기
		v = sc.nextInt();
		
		//graph 크기 설정
		//n이 1000까지이므로 각각 1000+1개씩
		graph = new int[1001][1001];
		
		//visited 크기 설정
		//방문 확인 배열 또한 1001로 크기 설정
		visited = new boolean[1001];
		
		//그래프 입력받기
		//m개 받으므로 m번 for문 돌기
		for(int i=0;i<m;i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			//양방향이므로 모두 설정
			graph[x][y] = graph[y][x] = 1;
		}
		
		// dfs 호출
		// dfs의 파라미터는 현재 정점
		dfs(v);
		
		//dfs 함수를 돌면서 값이 부여됐을 visited를 bfs에서 사용하기 위해 0으로 초기화
		visited = new boolean[1001];
		//dfs이후 한줄 띄어쓰기
		System.out.println();
		
		//bfs 호출
		bfs();
	}

	
	public static void dfs(int i) {
		
		//방문 정점에 visited값 왔다고 바꿔주기
		visited[i]=true;
		//방문 지점 출력
		System.out.print(i + " ");
		
		// 정점 개수만큼 돌면서 확인해야하므로
		for(int j=1;j<=n;j++) {
			//조건: graph가 연결되어 있고 아직 온적이 없어야함
			if(graph[i][j]==1 && visited[j]==false) {
				dfs(j);
			}
		}
	}
	
	public static void bfs() {
		//파이썬에서도 BFS는 QUEUE를 이용하여 문제 해결 
		//자바 queue는 linked=list로 구현되어 있음
		
		Queue<Integer> queue = new LinkedList<Integer>();					
		
		//시작 지점을 queue에 추가
		queue.offer(v);
		
		//visited에 방문지점 체크
		visited[v] = true;
		
		//출력
		System.out.print(v + " ");
		
		//queue가 비어있지 않은 동안 while문을 돌리면서
		while(!queue.isEmpty()) {
			//queue에서 pop
			int temp = queue.poll();
			
			for(int i=1;i<=n;i++) {
				if(graph[temp][i]==1 && visited[i]==false) {
					queue.offer(i);
					visited[i]=true;
					System.out.print(i + " ");
				}
			}
		}
		
	}
	
	
}
