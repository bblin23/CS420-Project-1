package def;

public class Node implements Comparable<Node>{
	public Grid grid;
	public int value;
	public Node parent;
	public int steps;
	
	public Node(Grid grid, int value){
		this.grid = grid;
		this.value = value;
		this.parent = null;
		this.steps = 0;
	}
	
	public Node(Grid grid, int value, Node parent){
		this.grid = grid;
		this.value = value;
		this.parent = parent;
		this.steps = parent.steps+1;
	}
	
	public String toString(){
		return (this.grid.toString() + this.value);
	}
	
	public String pathToString(){
		StringBuilder toReturn = new StringBuilder("");
		toReturn.insert(0, this.grid.toString());
		return parent.pathToString(toReturn).toString();
	}
	
	public StringBuilder pathToString(StringBuilder prev){
		if(parent == null){
			prev.insert(0, "\n");
			prev.insert(0, this.grid.toString());
			return prev;
		} else {
			prev.insert(0, "\n");
			prev.insert(0, this.grid.toString());
			return parent.pathToString(prev);
		}
			
	}
	
	@Override
	public int compareTo(Node o) {
		return Integer.compare(this.value, o.value);
	}
}