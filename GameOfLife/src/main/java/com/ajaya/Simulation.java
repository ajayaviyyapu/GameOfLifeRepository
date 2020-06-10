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
		
	}
	
}
