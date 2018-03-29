package def;

import java.util.Arrays;
import java.util.Random;

public class Grid {

	public Tile[][] puzzle;
	private int blankX, blankY;
	private int[] layout;
	
	public Grid(){
		puzzle = new Tile[3][3];
	}
	
	public Grid(int[] layout){
		puzzle = new Tile[3][3];
		fillPuzzle(layout);
		this.layout = layout;
	}
	
	public Grid(String input){
		try {
			puzzle = new Tile[3][3];
			this.layout = interpret(input);
			fillPuzzle(layout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Grid(Grid copy){
		puzzle = copyPuzzle(copy.puzzle);
		blankX = copy.blankX;
		blankY = copy.blankY;
		layout = copyLayout(copy.layout);
	}
	
	private Tile[][] copyPuzzle(Tile[][] puzzle2) {
		Tile[][] toReturn = new Tile[3][3];
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				toReturn[i][j] = new Tile(puzzle2[i][j]);
			}
		}
		return toReturn;
	}

	private int[] copyLayout(int[] layout2) {
		return Arrays.copyOf(layout2, 9);
	}

	public String toString(){
		StringBuilder output = new StringBuilder("");
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				output.append(Integer.toString(puzzle[i][j].face));
				output.append(" ");
			}
			output.append("\n");
		}
		return output.toString();
	}
	
	public Tile getTile(int X, int Y){
		return puzzle[X][Y];
	}
	
	public Tile getTile(int face){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(this.puzzle[i][j].face == face)
					return this.puzzle[i][j];
			}
		}
		return null;
	}
	
	public int[] interpret(String input) throws Exception{
		int[] layout = new int[9];
		input = input.replaceAll("\\s+", "");
		if(input.matches("\\d{9}")){
			int numero = Integer.parseInt(input);
			for(int i = 1; i < 10; i++){
				layout[9-i] = numero%((int)Math.pow(10,i))/((int)Math.pow(10, i-1));
			}
		} else {
			throw new Exception("Invalid input, enter only 9 numbers separated by"
					+ " whitespace.");
		}
		return layout;
	}
	
	public boolean checkSolution(int[] solution){
		int k = 0;
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				if(puzzle[i][j].face != solution[k])
					return false;
				k++;
			}
		}
		return true;
	}
	
	public boolean checkSolution(String solution){
		try {
			return checkSolution(interpret(solution));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void fillPuzzle(int[] layout) {
		int i = 0;
		for(int j = 0; j < 3; j++){
			for(int k = 0; k < 3; k++){
				if(layout[i] == 0){
					this.blankX = j;
					this.blankY = k;
				}
				puzzle[j][k] = new Tile(j,k,layout[i]);
				i++;
			}
		}
		this.layout = layout;
	}
	
	public void randomFill(){
		int[] layout2 = generateRandom();
		fillPuzzle(layout2);
	}
	
	public int[] toLayout(){
		return this.layout;
	}
	
	public int[] generateRandom() {
		int[] toReturn = new int[9];
		Arrays.fill(toReturn, -1);
		Random rand = new Random();
		int entry = 0;
		boolean boo = true;
		for(int i = 0; i < toReturn.length; i++){
			while(boo){
				entry = Math.abs(rand.nextInt()%9);
				if(unique(entry, toReturn))
					boo = false;
			}
			toReturn[i] = entry;
			boo = true;
		}
		return toReturn;
	}

	private boolean unique(int entry, int[] toReturn) {
		for(int i = 0; i < toReturn.length; i++){
			if(entry == toReturn[i])
				return false;
		}
		return true;
	}

	public void swap(int face){
		swap(getTile(face));
	}
	
	public void swap(Tile swap){
		int placeholderX, placeholderY;
		if(canSwap(swap)){
			puzzle[blankX][blankY].face = swap.face;
			swap.face = 0;
			blankX = swap.X;
			blankY = swap.Y;
			updateLayout();
		} else {
//			System.out.print("Can't swap.\n");
		}
	}

	private void updateLayout() {
		int i = 0;
		for(int j = 0; j < 3; j++){
			for(int k = 0; k < 3; k++){
				this.layout[i] = this.puzzle[j][k].face;
				i++;
			}
		}
	}

	public boolean canSwap(Tile swap) {
		if(blankX == swap.X && blankY == swap.Y)
			return false;
		if(blankX == swap.X){
			if(Math.abs(blankY - swap.Y) > 1)
				return false;
			else
				return true;
		} else if (blankY == swap.Y){
			if(Math.abs(blankX - swap.X) > 1)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	
}
