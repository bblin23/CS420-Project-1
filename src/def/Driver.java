/**
 * 
 */
package def;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Bolton Lin
 *
 */
public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("8-Puzzle Solver");
		Scanner in = new Scanner(System.in);
		boolean retry = true;
		int input;
		String input2;
		while(retry){
			System.out.println("Input?\n\t[1]String Input\n\t[2]File Input\n\t[3]Random Input");
			input = Integer.parseInt(in.nextLine());
			switch(input){
			case 1:
				input = getH(in);
				System.out.println("Enter puzzle (e.g. 410382675): ");
				input2 = in.nextLine();
				stringInput(input2, input);
				break;
			case 2:
				input = getH(in);
				System.out.println("Enter filename: ");
				input2 = in.nextLine();
				fileInput(input2, input);
				break;
			case 3:
				input = getH(in);
				randomInput(input);
				break;
			default:
				System.out.println("Invalid input.");	
			}
			System.out.println("Retry (Y/N)? ");
			input2 = in.nextLine();
			if(input2.equalsIgnoreCase("N"))
				retry = false;
			else if (input2.equalsIgnoreCase("Y"))
				retry = true;
			else{
				System.out.println("Invalid input. Exiting.");
				System.exit(0);
			}
		}
	}
	
	private static int getH(Scanner in) {
		System.out.println("Enter heuristic:\n\t[1] Hamming\n\t[2] Manhattan Distance");
		int h = Integer.parseInt(in.nextLine());
		return h;
	}

	private static void randomInput(int h) {
		Grid grid = new Grid();
		grid.randomFill();
		Solver solver = new Solver(grid);
		Node answer;
		System.out.println("Solving...\n" +grid.toString());
		final long startTime = System.currentTimeMillis();
		answer = solver.solve(h);
		final long endTime = System.currentTimeMillis();
		if(answer == null)
			System.out.println("Unsolvable, Inverted Nodes: " + solver.solvable());
		else {
			System.out.println(answer.pathToString());
			System.out.println("Nodes Expanded: " + solver.numNodes);
			System.out.println("Steps: " + answer.steps);
			System.out.println("Execution time: " + (double)(endTime - startTime)/1000 + "s\n");
		}
	}

	private static void stringInput(String input, int h) throws Exception {
		Grid grid = new Grid();
		grid.fillPuzzle(grid.interpret(input));
		Solver solver = new Solver(grid);
		Node answer;
		System.out.println("Solving...\n" +grid.toString());
		final long startTime = System.currentTimeMillis();
		answer = solver.solve(h);
		final long endTime = System.currentTimeMillis();
		if(answer == null)
			System.out.println("Unsolvable, Inverted Nodes: " + solver.solvable());
		else {
			System.out.println(answer.pathToString());
			System.out.println("Nodes Expanded: " + solver.numNodes);
			System.out.println("Steps: " + answer.steps);
			System.out.println("Execution time: " + (double)(endTime - startTime)/1000);
		}
	}

	public static void fileInput(String filename, int h) throws Exception{
		Grid grid = new Grid();
		Solver solver;
		Node node;
		int min, max, avg;
		min = max = avg = -1;
		int sum, iterations;
		sum = iterations = 0;
		int numNodes;
		long totalTime = 0;
		List<String> list = readInput(filename);
		for(String in : list){
			grid.fillPuzzle(grid.interpret(in));
			solver = new Solver(grid);
			System.out.println("Solving...\n" +grid.toString());
//			System.out.println(in);
			final long startTime = System.currentTimeMillis();
			node = solver.solve(h);
			final long endTime = System.currentTimeMillis();
			System.out.println(node.pathToString());
			numNodes = solver.numNodes;
			if(min == -1)
				min = numNodes;
			else if (max == -1){
				if (numNodes > min)
					max = solver.numNodes;
				else if (numNodes < min){
					max = min;
					min = numNodes;
				}
			} else {
				if(numNodes > max)
					max = numNodes;
				else if (numNodes < min)
					min = numNodes;
			}
			System.out.println("Nodes Expanded: " + numNodes);
			System.out.println("Steps: " + node.steps);
			System.out.println("Execution time: " + (double)(endTime - startTime)/1000+"\n");
			totalTime += (endTime - startTime);
			sum += numNodes;
			iterations++;
		}
		
		System.out.println("Min nodes: " + min);
		System.out.println("Max nodes: " + max);
		System.out.println("Avg nodes: " + sum/iterations);
		System.out.println("Total Time: " + (double)totalTime/1000);
		System.out.println("Average Time: " + (double)totalTime/1000/iterations);
		System.out.println("Iterations: " + iterations);


	}
	
	public static List<String> readInput(String filename){
		List<String> records = new ArrayList<String>();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while((line = reader.readLine()) != null){
				if(line.matches("\\d{9}"))
					records.add(line);
			}
			reader.close();
			return records;
		}catch (Exception e){
		    System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		}
	}

}
