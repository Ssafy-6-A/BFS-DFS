package common;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * bfs를 선택한 이유: 해당 문제는 미로찾기와 같이 최단거리를 요구하는 문제기에 bfs를 선택
 * 
 * 나의 bfs와 dfs 선택 기준: bfs=> 최단거리 찾기, dfs=>경로를 알아야하고 그 경로에 특정 조건이 존재할때
 * @author SSAFY
 *
 */

//queue에 넣을 때 point로 넣어주기 위한 클래스
class Point{
	int x;
	int y;
	int crash;
	public Point(int x, int y, int crash) {
		this.x = x;
		this.y = y;
		this.crash = crash;
	}
}

public class BOJ_2206 {
	//deltas
	//dx는 행, dy는 열 관련
	static final int[] dx = {0,0,-1,1};
	static final int[] dy = {1,-1,0,0};
	
	//int 2차원 배열 graph
	static int[][] graph;
	
	//행 길이인 n
	static int n;
	
	//열 길이인 m
	static int m;
	
	//방문 배열 3차원인 이유는 마지막은 벽을 부순 적이 있으면 0 벽을 부시지 않았으면 1로 설정하려고
	static int[][][] visited;
	static int cnt;
	static Queue<Point> queue;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		//n과 m 입력받기
		n = sc.nextInt();
		m = sc.nextInt();
		
		//graph와 visited 배열 초기화
		visited = new int[n][m][2];
		graph = new int[n][m];
		
		//왜인지 모르겠는데 nextInt()로 바로 받으면 오류가 남
		//따라서 string으로 한줄을 받고
		for(int i=0;i<n;i++) {
			String tmp = sc.next();
			for(int j=0;j<m;j++) {
				//이 부분에서 한줄을 각자 int로 변환하여 graph에 대입함
				graph[i][j] = Integer.valueOf(tmp.charAt(j)-'0');
			}
		}
		
		//queue초기화
		queue = new LinkedList<Point>();
		
		//queue에 시작지점 넣어주기
		queue.offer(new Point(0,0,0));
		
		//시작지점 방문처리
		visited[0][0][0]=1;
		
		//시작지점 개수 증가
		cnt = 1;
		
		//bfs실행
		System.out.println(bfs());
	}
	
	public static int bfs() {
		//queue가 빌 때까지
		while(!queue.isEmpty()) {
			
			//queue의 값 꺼내서 Point에 넣어주기
			Point node = queue.poll();
			
			//cx에 node의 x, cy에 node의 y, crash에 node의 crash값 넣어주기
			int cx = node.x;
			int cy = node.y;
			int crash = node.crash;
			
			//종료 조건
			//맵의 끝에 다다르면 종료
			if(cx == n-1 && cy==m-1) {
				//visited 배열의 (5,3)의 값을 return
				return visited[cx][cy][crash];
			}
			
			// 4방향으로 돌면서
			for(int d=0;d<4;d++) {
				//n은 next의 뜻을 넣어 nx = next node의 x, ny = next node의 y를 뜻함
				int nx = cx+dx[d];
				int ny = cy+dy[d];
				
				//경계선 체크
				if(nx>=0 && ny>=0 && nx<n && ny<m) {
					//graph[nx][ny]==0이면 벽이 아님
					//visited[nx][ny][crash]==0이면 방문하지 않음
					//따라서 벽이 아니고 방문하지 않았으면
					if(graph[nx][ny]==0 && visited[nx][ny][crash]==0) {
						//해당 위치를 방문처리하는데, 그 전의 visited값에 1을 추가한 즉 그 vistied배열까지의 거리를 넣어줌
						visited[nx][ny][crash] = visited[cx][cy][crash]+1;
						//queue에 다음 노드 추가
						queue.offer(new Point(nx,ny,crash));
					}
					
					//만약 벽이고 방문하지 않았으면
					if(graph[nx][ny]==1 && visited[nx][ny][crash]==0) {
						//아직 벽을 부순적이 없으면
						if(crash==0) {
							//벽을 부수고 부섰다고 체크
							visited[nx][ny][1] = visited[cx][cy][crash]+1;
							//다음 노드를 queue에 추가
							queue.offer(new Point(nx,ny,1));
						}
						//만약 벽을 부쉈으면
						else if(crash==1) {
							//continue
							continue;
						}
					}
				}
			}
		}
		//다 돌았는데 (5,3)에 도착하지 못하면 -1을 return
		return -1;
	}


}
