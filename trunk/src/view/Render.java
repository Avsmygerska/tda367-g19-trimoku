package view;

import java.awt.Color;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import model.*;


public class Render implements GLEventListener {
	
	private Board board;
	private GLU glu;

	// Board       Width     Depth    Thickness 
	private float w,d, t = 0.2f;

	private float defaultRotation = 250.0f;
	private float rotation = 250.0f;

	private float[] lightAmbient = {0.4f, 0.4f, 0.4f, 1f};
	private float[] lightDiffuse = {0.8f, 0.8f, 0.8f, 1f};
	private float[] lightPosition = {0f, 10.0f, 0f, 1.0f};
		
	private float edge = 0.5f;  // Distance between board edge and first row of pieces.
	private float pSize = 0.4f; // Radius of a piece.

	private int rows, cols; // Number of positions in X, Y and Z
	private float spacingRow = 1.25f, spacingCol = 1.25f;
	
	private boolean[][] pinVisible;
	
	public Render() {
		glu = new GLU();
	}
	
	public Render(Board board) {
		glu = new GLU();
		setBoard(board);		
	}
	
	public void setBoard(Board board) {
		this.board = board;
		rows = board.getRows();
		cols = board.getColumns();
		
		pinVisible = new boolean[rows][cols];
		showPins(true);
		
		w = edge + (spacingCol * (cols-1))/2;
		d = edge + (spacingRow * (rows-1))/2;
		
		rotation = defaultRotation;
	}

	// Ta det försiktigt här.
	// (columns,layers,row)
	public void display(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();                   

		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
		
		if(board == null)
			return;

		// Set camera
		glu.gluLookAt(
				7f, 7f, 7f,   // Eyes
				0f, 1.5f, 0f, // Look at
				0f, 1f, 0f);  // Up        

		// Draw plate
		gl.glRotatef(rotation, 0f, 1f, 0f);    	
		drawPlate(gl);

		// Top left and bottom right marker
		gl.glTranslatef(w,t,d);
		gl.glColor3f(0f, 1f, 0f);
		glu.gluSphere(glu.gluNewQuadric(), 0.1, 10, 10);
		gl.glTranslatef(-w,-t,-d);

		// Draw Pieces and Pins        
		gl.glTranslatef(w-edge, t+pSize, d-edge);

		for(int row = 0; row < rows; row++) {        	
			for(int col = 0; col < cols; col++) {
				if(pinVisible[row][col]) {
					ArrayList<Player> pieces = board.getPin(row, col);
					drawPin(gl, pieces.size(),new Color(150,150,0));
					int z = 0;
					for(Player p : pieces) {
						drawPiece(gl,p);
						gl.glTranslatef(0f,2*pSize,0f);
						z++;
					}
					gl.glTranslatef(0f, -2*pSize*z, 0f);
				}
				gl.glTranslatef(-spacingCol,0f, 0f);
			}        	
			gl.glTranslatef(spacingCol*cols,0f,-spacingRow);
		}

		gl.glFlush();
	}
	
	public void turn(float deg) { rotation += deg; }

	// Draws the bottom plate.
	private void drawPlate(GL gl) {
		gl.glBegin(GL.GL_QUADS);

		gl.glColor3f(0.5f, 0.5f, 0.5f);
		gl.glVertex3f( w, t, -d);	// Top Right Of The Quad (Top)
		gl.glVertex3f(-w, t, -d);	// Top Left Of The Quad (Top)
		gl.glVertex3f(-w, t,  d);	// Bottom Left Of The Quad (Top)
		gl.glVertex3f( w, t,  d);	// Bottom Right Of The Quad (Top)

		gl.glVertex3f( w,  t, d);	// Top Right Of The Quad (Front)
		gl.glVertex3f(-w,  t, d);	// Top Left Of The Quad (Front)
		gl.glVertex3f(-w, -t, d);	// Bottom Left Of The Quad (Front)
		gl.glVertex3f( w, -t, d);	// Bottom Right Of The Quad (Front)

		gl.glVertex3f( w, -t, -d);	// Bottom Left Of The Quad (Back)
		gl.glVertex3f(-w, -t, -d);	// Bottom Right Of The Quad (Back)
		gl.glVertex3f(-w,  t, -d);	// Top Right Of The Quad (Back)
		gl.glVertex3f( w,  t, -d);	// Top Left Of The Quad (Back)

		gl.glVertex3f(-w,  t,  d);	// Top Right Of The Quad (Left)
		gl.glVertex3f(-w,  t, -d);	// Top Left Of The Quad (Left)
		gl.glVertex3f(-w, -t, -d);	// Bottom Left Of The Quad (Left)
		gl.glVertex3f(-w, -t,  d);	// Bottom Right Of The Quad (Left)
		
		gl.glVertex3f(w,  t, -d);	// Top Right Of The Quad (Right)
		gl.glVertex3f(w,  t,  d);	// Top Left Of The Quad (Right)
		gl.glVertex3f(w, -t,  d);	// Bottom Left Of The Quad (Right)
		gl.glVertex3f(w, -t, -d);	// Bottom Right Of The Quad (Right)
		gl.glEnd();
	}
	
	// Draws a single pin.
	private void drawPin(GL gl, int length, Color c) {
		gl.glTranslatef(0f, -pSize, 0f);
		float angle = 90;
		gl.glColor3i(c.getRed(), c.getGreen(), c.getBlue());
		gl.glRotatef(-angle,1f,0f,0f);
		glu.gluCylinder(glu.gluNewQuadric(), 0.1, 0.1, 2*pSize*length+0.3, 10, 10);
		gl.glRotatef(angle,1f,0f,0f);
		gl.glTranslatef(0f, +pSize, 0f);
	}

	// Draws a piece for the selected player.
	private void drawPiece(GL gl, Player p) {
		
		gl.glColor3f(p.getRed(), p.getGreen(), p.getBlue());		
		glu.gluSphere(glu.gluNewQuadric(), pSize, 10, 10);
	}

	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {}
	
	public void init(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);                              // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);                    // Black Background
		gl.glClearDepth(1.0f);                                      // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);							    // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations

		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, this.lightAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, this.lightDiffuse, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, this.lightPosition, 0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		final GL gl = gLDrawable.getGL();

		if (height <= 0) // avoid a divide by zero error!
			height = 1;
		final float h = (float) width / (float) height;        
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(50.0f, h, 1.0, 100.0);
		gl.glMatrixMode(GL.GL_MODELVIEW);        
		gl.glLoadIdentity();
	}
	 
	// Switches the visibility state for the selected pin.
	public void switchPin(int row, int col) {
		if( row < rows && col < cols)
			pinVisible[row][col] = !pinVisible[row][col];
	}
	
	public void setRow(int row, boolean val) {
		if(row < rows)
			for(int col = 0; col < cols; col++)
				pinVisible[row][col] = val;
	}
	
	public void setCol(int col, boolean val) {
		if(col < cols)
			for(int row = 0; row < rows; row++)
				pinVisible[row][col] = val;
	}

	// Shows or hides all pins.
	public void showPins(boolean val) {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				pinVisible[row][col] = val;				
			}
		}
	}	
	
	public boolean visiblePin(int row, int col) {
		if (row < rows && col < cols)
			return pinVisible[row][col];
		return false;
	}
	
	public boolean visibleRow(int row) {
		if(row >= rows)
			return false;
		
		// Are there any visible elements on the row?
		for(int col = 0; col < cols; col++)
			if(pinVisible[row][col])
				return true;
		
		return false;
	}
	
	public boolean visibleCol(int col) {
		if(col >= cols)
			return false;
		
		// Are there any visible elements in the column?
		for(int row = 0; row < rows; row++)
			if(pinVisible[row][col])
				return true;
		
		return false;
	}	
}
