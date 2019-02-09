import java.util.* ;

class Main {
	int n;	//# of nodes
	int m;	//# of edges
	int p;	//p=1 Prim , p=0 Kruskal
	int[] component;	//keep track of component
	int[] size; //keep track the size of each component
	
	Comparator<Node> costComparator = new Comparator<Node>() {
		public int compare(Node a, Node b) {
			if(a.key==b.key) return a.nodeNum-b.nodeNum; 
			return a.key-b.key;
		}
	};
	
	Comparator<Edge> edgeComparator = new Comparator<Edge>() {
		public int compare(Edge a,Edge b) {
			if(a.cost==b.cost) return a.id-b.id;
			return a.cost - b.cost;
		}
	};
	
	/**priority queue to store all the available edges/nodes*/
	PriorityQueue<Edge> kque = new PriorityQueue<Edge>(edgeComparator);	//Kruskal queue
	PriorityQueue<Node> que = new PriorityQueue<Node>(costComparator);	//Prim queue
	HashMap<Integer, Node> getNode = new HashMap<>();	//map nodeNum to a node in the queue
	HashMap<Integer,ArrayList<Node>> adj = new HashMap<>();	//map each node to its adjacent list Prim
	
	public class Node{
		int nodeNum;
		int key;
		int edgeNum;
		
		public Node(int node, int key){
			this.nodeNum = node;
			this.key = key;
		}
		public Node(int node, int key,int num){
			this.nodeNum = node;
			this.key = key;
			edgeNum=num;
		}
	}
	
	public class Edge{
		int node1;
		int node2;
		int cost;
		int id;
		
		public Edge(int node1,int node2) {
			this.node1 = node1;
			this.node2 = node2;
		}
		
		public Edge(int node1, int node2, int cost, int id) {
			this.node1 = node1;
			this.node2 = node2;
			this.cost = cost;
			this.id = id;
		}
	}
	
	void priminput(Scanner sc) {
		 
		/**Add all nodes to the queue*/
		for(int i=1;i<=n;i++) {
			Node node;
			if(i==1) node = new Node(1,0);
			else node = new Node(i,Integer.MAX_VALUE);
			
			getNode.put(i, node);
			que.add(node);
		}
		
		/**Scan all the edges*/
		for(int i=1;i<=m;i++) {

			int node1 = sc.nextInt();		
			int node2 = sc.nextInt();
			int cost = sc.nextInt();		
			
			if(!adj.containsKey(node1)) {
				adj.put(node1, new ArrayList<>());
			}
			if(!adj.containsKey(node2)) {
				adj.put(node2, new ArrayList<>());
			}
			
			adj.get(node1).add(new Node(node2,cost,i));
			adj.get(node2).add(new Node(node1,cost,i));
			
		}
	}
	

	
	void prim() {	
		Node newNode;
		int count=0;
		while(!que.isEmpty()) {
			newNode = que.poll();	//extractMin
			if(newNode.key!=0) {
				count++;
				if(count==n) break;
				System.out.println(newNode.edgeNum);
			}
			ArrayList<Node> adjNodes = adj.get(newNode.nodeNum);
		
			for(int i = 0;i < adjNodes.size();i++) {   //traverse list of adjacent nodes
				Node adjN = adjNodes.get(i);
				if(getNode.containsKey(adjN.nodeNum)) {
					if(adjN.key < getNode.get(adjN.nodeNum).key) {
						que.remove(getNode.get(adjN.nodeNum));		//Update key value
						que.add(adjN);
						getNode.put(adjN.nodeNum, adjN);
					}
				}
			}
		}
	}
	
	void kruskalinput(Scanner sc) {
		component = new int[n];
		size = new int[n];
			/**Add all nodes to PriorityQueue*/
			for(int i=1;i<=m;i++) {
				int node1 = sc.nextInt();
				int node2 = sc.nextInt();
				int cost = sc.nextInt();
				
				Edge edge = new Edge(node1, node2, cost, i);
				kque.add(edge);
			}

	}
	
	void kruskal() {
		/**Initialize component array*/
		for(int i=0;i<n;i++) {
			component[i] = i;
			size[i] = 1;
		}
		
		/**Insert Edges*/
		int count=0;
		while(!kque.isEmpty()) {
			Edge edge = kque.poll();
			int parent1 = find(component,edge.node1-1);
			int parent2 = find(component,edge.node2-1);
			if(parent1 == parent2) {
				continue;
			}else {
				System.out.println(edge.id);
				count++;
				if(count==n-1) break;
				component[parent2] = parent1;
			}
		}
	}
	
	int find(int[] component, int i) {
		if(component[i]!=i) return find(component, component[i]);
		return i;
	}
	public Main(String[] Args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();		
		m = sc.nextInt();		
		p = sc.nextInt();		
		if(p==1) {
			priminput(sc);
			prim();
		}
		else if(p==0) {
			kruskalinput(sc);
			kruskal();
		}
		
		
	}
	
	public static void main (String[] Args) {
		new Main(Args);
	}
	
}
