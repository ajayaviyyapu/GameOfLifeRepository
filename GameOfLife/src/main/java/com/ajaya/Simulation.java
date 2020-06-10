package com.ajaya;

public class Simulation {
	
	int width;
	int height;
	int[][] board;
	
	
	public Simulation(int width, int height) {
		
		this.width = width;
		this.height = height;
		this.board = new int[width][height];
	}
	
	
	//Simulation from one board to another board
	public void step() {
		
		// Editing the current board inline while checking it. 
		// This will create problems as we are changing the original state of the board. 
		// So when the next line is processed it is calculated according to the changed board not the original board
		// So creating a new board array and applying the changes on it based on the original board
		// Finally assign the new board to the original board
		
		int[][] newBoard = new int[width][height];
		
		for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					
						int aliveNeighbours = countAliveNeighbours(x,y);
						
						if(getCellState(x,y) == 1) { // Live Cell Conditions
							if(aliveNeighbours < 2) //Under Population Condition
								newBoard[x][y] = 0;
							else if(aliveNeighbours == 2 || aliveNeighbours == 3) //Keeping Alive Condition
								newBoard[x][y] = 1;
							else if(aliveNeighbours > 3) //Over Population Condition
								newBoard[x][y] = 0;
						}
						else {
							if(aliveNeighbours == 3) //Dead Cell Condition - More than three alive neighbours - Activate the Dead Cell
								newBoard[x][y] = 1;
						}
					
					}
						
				}
		
		this.board = newBoard;
	}
	
	public int countAliveNeighbours(int x, int y) {
		int count=0;
		
		count += getCellState(x-1,y-1);
		count += getCellState(x,y-1);
		count += getCellState(x+1,y-1);
		
		count += getCellState(x-1,y);
		count += getCellState(x+1,y);
		
		count += getCellState(x-1,y+1);
		count += getCellState(x,y+1);
		count += getCellState(x+1,y+1);
		
		return count;
	}
	
	//Preventing the countAliveNeighbours function from going away from the board boundaries
	public int getCellState(int x, int y) {
	
		if(x < 0 || x >= width)
			return 0;
		
		if(y < 0 || y >= height)
			return 0;
			
		return this.board[x][y];
	}
	
	public void printBoard() {
		
		System.out.println("---");
		
		//iterate line by line thats why selecting y-axis first
		for (int y = 0; y < height; y++) {
			
			String line = "|";
			
			for (int x = 0; x < width; x++) {
				
				if(this.board[x][y] == 0)
				{
				   line += ".";
				}
				else {
				   line += "*";
				}
			}
			
			line += "|";
			System.out.println(line);
			
		}
		
		System.out.println("---\n");
	}
	
	public void setAlive(int x, int y) {
		this.board[x][y] = 1;
	}

	public void setDead(int x, int y) {
		this.board[x][y] = 0;
	}
	
	public static void main(String[] args) {
		
		Simulation s = new Simulation(8, 5);
		
		s.setAlive(2, 2);
		s.setAlive(3, 2);
		s.setAlive(4, 2);
		
		s.printBoard();
		
		s.step();
		
		s.printBoard();
		
		s.step();
		
		s.printBoard();
	}
	
}
