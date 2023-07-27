package choiseohyun.common;

import java.util.*;

/** DFS와 BFS
 * 방문할 수 있는 정점이 여러개인 경우 작은것부터 방문, 정점번호는 1부터 N까지임
 * input : 정점N개와 간선M개, 탐색 시작번호V
 * output : DFS 탐색결과 \n BFS 탐색결과
 */

class Node {
	int x,y,len; //x좌표, y좌표, 거리 변수
	boolean broken; //벽 부순적있는 경로인지 체크
	public Node(int x, int y, boolean broken, int len) {
		this.x = x;
		this.y = y;
		this.broken = broken;
		this.len = len;
	}
	@Override
	public String toString() {
		return "x: "+x+", y: "+y;
	}
}

public class Main {
	static int[] dx = {1,-1,0,0};
	static int[] dy = {0,0,1,-1};
	static int[][] map;
	static int[][][] check;
	static int result = -1;
	
	
	public static void BFS(Node node) {
		Queue<Node> q = new LinkedList<Node>();
		q.offer(node);
		
		while(!q.isEmpty()) {
			Node cnode = q.poll();
			
			if(cnode.x==map.length-1 && cnode.y==map[0].length-1) {
				result = cnode.len;
				return;
			}
			
			for(int i=0; i<4; i++) {
				int nx = cnode.x+dx[i];
				int ny = cnode.y+dy[i];
				
				if(nx>=0 && ny>=0 && nx<map.length && ny<map[0].length && check[nx][ny][cnode.broken?1:0]==0) {
					// 다음경로가 길일땐 broken 여부와 상관없이 나아간다. (이미 방문했는지도 확인 필요)
					if(map[nx][ny]==0) {
						check[nx][ny][cnode.broken?1:0] = 1;
						q.offer(new Node(nx,ny,cnode.broken,cnode.len+1));
					}
					// 다음 경로가 벽일땐 broken==false일때만 길을 뚫고 나아간다.
					else if(map[nx][ny]==1 && !cnode.broken) {
						check[nx][ny][cnode.broken?1:0] = 1;
						q.offer(new Node(nx,ny,!cnode.broken,cnode.len+1));
					}
				}
			}
		}
		
	}
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		map = new int[n][m];
		check = new int[n][m][2]; //벽 부순적 있으면 1, 없으면 0
		//0은 이동가능, 1은 이동불가
		for(int i=0; i<n; i++) {
			String str = sc.next();
			for(int j=0; j<m; j++) {
				map[i][j] = str.charAt(j)-48;
			}
		}
		
		check[0][0][0] = 1;
		check[0][0][1] = 1;
		
		BFS(new Node(0,0,false,1));
		System.out.println(result);
	}

}


// 벽을 부수지 않았을때 최단경로 , 1,1번벽을 부쉈을때 최단경로, 1,2번벽을 부쉈을때 최단경로,,.. 들 중에서 가장 작은수?
// 왜 벽을 부순것별로 체크배열을 만들지 않는가? => 의미가 없으니까!! 벽 부순이상 갈수있는 경로가 겹침! 빨리 도착한 노드만 찾으면됨!

// 0 1 0
// 1 0 1
// 1 0 0
// 위 예시에서

// 이차원 체크배열이 두개 있다고 생각하ㅁ면
// 루트노드가 1번벽을 부순채로 탐색.. broken=true인 체크배열의 1번벽 위치에 1이 삽입
// 이상태에서 1번벽을 부수지 않은 루트노드가 3번벽을 부순다. broken=true인 체크배열의 3번벽 위치에 1이 삽입
// 그리고 이제 두 노드는 벽을 부수지 못하는 노드임, 따라서 값이 0인거만 찾아서 가면됨
// 4번 노드에 먼저 도착하게 되고, broken=true인 4번 체크배열이 1이됨
// 오.. ㄱㅊ은데?
// 즉, 이미 벽을 부순 노드라면 어차피 더이상 벽을 탐색하는 의미가 없으므로 체크배열에서 방문했는지만 확인하면 됨
// 근데 1번벽을 부순 경로가 3번벽을 부순 경로가 지나갈 길을 미리 지나가면 어케되노
// 아하 어차피 뭔 벽을 부수든 빨리 도착한애가 장땡

//오오.. ㅋㅋ

// 여러개 만들어야 한다고 생각한 이유: 벽뿌순 1번경로가 뒤늦게 벽뿌순 2번경로의 진로를 방해하면 어케?
// => 상관없지 동일노드에 들어왔다는건 결국 같은경로로 진행한다는 뜻인데 1번경로가 먼저 거길 지나갔기 때문에 최단경로에 해당함.
// 따라서 벽을 부순 체크배열과 벽을 안부순 체크배열만 있으면됨~!

// 벽을 부쉈다 = 경로가 길에 한정됨다
// 벽을 부수지 않았다 = 경로가 길+벽이 있다
// 따라서 체크가 분리되는것,,


