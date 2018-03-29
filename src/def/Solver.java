/**
 * 
 */
package def;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author Bolton
 *
 */
public class Solver {
	
	private Grid grid;
	private String solution = "012345678";
	private Grid solutionGrid;
	public PriorityQueue<Node> prio;
	private Grid prevState;
	public int numNodes;
	
	public Solver(Grid grid){
		this.grid = grid;
		prio = new PriorityQueue<Node>();
		solutionGrid = new Grid(solution);
		prevState = grid;
		numNodes = 0;
	}

	public Node solve(int h){
		if(solvable()%2 == 0){
			Node focus;
			if(h == 1)
				prio.add(new Node(this.grid, getH1(this.grid)));
			else if (h == 2)
				prio.add(new Node(this.grid, getH2(this.grid)));
			while(prio.peek() != null){
				focus = prio.poll();
				numNodes++;
				if(checkSolution(focus))
					return focus;
				else {
//					System.out.println(focus.grid.toString() + focus.value);
					addChildren(focus, h);
					prevState = new Grid(focus.grid);
				}
			}
		} else
			return null;
		return null;
	}

	private void addChildren(Node parent, int h) {
		Grid child;
		int[] order = solutionGrid.generateRandom();
		for(int i = 0; i < 9; i++){
			child = makeChild(parent.grid, order[i]);
			if(child != null && child != prevState){
				if (h == 1)
					prio.add(new Node(child, getH1(child)+parent.steps, parent));
				else if (h == 2)
					prio.add(new Node(child, getH2(child)+parent.steps, parent));
			}
		}
	}

	public Grid makeChild(Grid parent, int i) {
		if(parent.canSwap(parent.getTile(i))){
			Grid childGrid = new Grid(parent);
			childGrid.swap(i);
			return childGrid;
		} else 
			return null;
	}

	private boolean checkSolution(Node node){
		return node.grid.checkSolution(solution);
	}
	
	public int getH1(){
		return getH1(this.grid);
	}
	
	public int getH1(Grid inspect){
		int[] layout = inspect.toLayout();
		int misplaced = 0;
		for(int i = 0; i < 9; i++){
			if(i != layout[i])
				misplaced++;
		}
		return misplaced;
	}
	
	public int getH2(){
		return getH2(this.grid);
	}
	
	public int getH2(Grid inspect){
		int h2 = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				h2 += getDist(inspect.puzzle[i][j]);
			}
		}
		return h2;
	}
	
	private int getDist(Tile tile) {
		int dist = 0;
		if(tile.face == 0)
			return 0;
		try {
			Tile compare = this.solutionGrid.getTile(tile.face);
			dist += Math.abs(compare.X - tile.X);
			dist += Math.abs(compare.Y - tile.Y);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dist;
	}

	public int solvable(){
		int[] array = this.grid.toLayout();
		int inverted = 0;
		for(int i = 0; i < 9 - 1; i++){
			for(int j = i+1; j < 9; j++){
				if(array[i] > array[j] && array[j] != 0){
					inverted++;
				}
			}
		}
				
		return inverted;
	}
	
}
