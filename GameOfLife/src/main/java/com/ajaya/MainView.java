package com.ajaya;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

//This is going to get displayed in the Java Applet

public class MainView extends VBox{
	
	
	private Button stepButton;
	private Canvas canvas;
	
	private Affine affine;
	
	private Simulation simulation;
	
	private int drawMode = 1; //To check whether to draw live cells or dead cells
	
	public MainView() {
		
		this.stepButton = new Button("step");
		this.stepButton.setOnAction(actionEvent -> {
			simulation.step();
			draw();
		});
		
		this.canvas = new Canvas(400, 400);
		//On click of the grid activate the cell.
		this.canvas.setOnMousePressed(this::handleDraw);
		this.canvas.setOnMouseDragged(this::handleDraw);
		
		this.setOnKeyPressed(this::onKeyPressed);
		
		
		this.affine = new Affine(); // for scaling up the picture that is drawn
		this.affine.appendScale(400/10, 400/10); //Canvas Size divided by simulation size
		
		this.getChildren().addAll(this.stepButton, this.canvas); // To add all the elements to VBOX
		
		this.simulation = new Simulation(10, 10);
	} 
	
	private void onKeyPressed(KeyEvent event) {
		if(event.getCode() == KeyCode.D) {
			this.drawMode = 1;
			System.out.println("Draw Mode");
		}else if(event.getCode() == KeyCode.E) {
			this.drawMode = 0;	
			System.out.println("Erase  Mode");
		}
	}
	
	private void handleDraw(MouseEvent event) {
		
		// capture the co-ordinates where the mose is pressed
		
		double mouseX = event.getX();
		double mouseY = event.getY();
		
		try {
			 Point2D simCoord = this.affine.inverseTransform(mouseX,mouseY);
			 		 
			 int simX = (int) simCoord.getX();
			 int simY = (int) simCoord.getY();
			 System.out.println(simX + "--" + simY);
			 this.simulation.setCellState(simX, simY, drawMode);; 
			 draw();
			 
			
		} catch (NonInvertibleTransformException e) {
			// TODO Auto-generated catch block
			System.out.println("could not invert transform");
		}
		
	}
	
	public void draw() {
		GraphicsContext g = this.canvas.getGraphicsContext2D();
		g.setTransform(this.affine);
		g.setFill(Color.LIGHTGRAY);
		
		g.fillRect(0, 0, 400, 400);
		
		g.setFill(Color.BLACK);
		
		for (int x = 0; x < this.simulation.width; x++) {
			for (int y = 0; y < this.simulation.height; y++) {
				if(this.simulation.getCellState(x, y) == 1)
				g.fillRect(x, y, 1, 1);	
			} 
			
		}
		
		
		g.setStroke(Color.GRAY);
		g.setLineWidth(0.05d);
		// Adding grid lines to the canvas
		for (int x = 0; x <= this.simulation.width; x++) {
			g.strokeLine(x, 0, x, 10); // Adding Vertical Lines
		}

		for (int y = 0; y <= this.simulation.width; y++)	{
			g.strokeLine(0, y, 10, y); // Adding Horizontal Lines
		}
		 
		
		
	}

	
	

}
