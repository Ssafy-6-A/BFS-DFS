package common;

import java.util.Scanner;

/*
 * 풀이 시작 시간: 9:00
 * 풀이 종료 시간: 9:50
 * 문제 풀이 시간: 50분
 * 
 * 문제: 방향 없는 그래프가 주어졌을 때, 연결 요소의 개수를 구하는 프로그램 구현
 * 
 * 문제 입력: 정점의 개수: N(1<=N<=1000), 간선의 개수: M(0<=M<=Nx(N-1)/2)
 * 둘째 줄부터는 M개의 줄에 간선의 양 끝점 U와 V가 주어짐 (1<=U,V<=n) U!=V
 * 
 * 문제 출력: 연결 요소의 개수 출력
 * 
 * 연결 요소: 하나의 정점에서 도달할 수 있는 모든 정점의 집합을 하나의 연결 요소로 간주
 * 
 * 내 풀이
 * 1. 그래프 구현
 * 2. 방문확인 후 모든 방문 가능한 정점 방문 처리
 * 3. 반복
 * 4. 모든 점이 연결 되었을 때, 그래픞를 몇번 돌았는지 확인
 * 5. 그래프 돈 횟수 == 연결 요소의 개수
 * 
 * 그래프를 탐색한 횟수가 연결 요소의 개수인 이유: 그래프 탐색 1번은 한 연결 요소에 연결되어 있는 정점 탐색을 의미하기 때문에 만약 연결 요소에 포함되지 않은 정점만 남게 된다면 그래프 탐색 1회가 끝나는 것, 이후 그래프 탐색을 다시 시작하면 연결요소의 개수가 늘어남.
 * 
 * */

public class BOJ_11724 {

	//graph
	static int[][] graph;
	//visited 배열
	static boolean[] visited;
	//정점의 개수
	static int n;
	//간선의 개수
	static int m;
	//연결요소의 개수
	static int cnt;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc  = new Scanner(System.in);
		
		n = sc.nextInt();
		m = sc.nextInt();
		
		graph = new int[1001][1001];
		visited = new boolean[1001];
		cnt = 0;
		
		for(int i=0;i<m;i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			
			graph[x][y] = graph[y][x] = 1;
		}
		
		for(int i=1;i<=n;i++) {
			if(visited[i]==false) {
				dfs(i);
				cnt++;
			}
		}
		System.out.println(cnt);
	}
	
	public static void dfs(int v) {
		if(visited[v]==true) {
			return;
		}
		
		visited[v]=true;
		for(int i=1;i<=n;i++) {
			if(graph[v][i]==1 && visited[i]==false) {
				dfs(i);
			}
		}
	}

}
