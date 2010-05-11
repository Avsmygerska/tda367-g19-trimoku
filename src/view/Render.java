package view;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import model.Board;
import model.Player;



public class Render implements GLEventListener {

	private Board board;
	private GLU glu;

	// Textures
	private int boardTop, boardSide, boardFront, pinTexture, pieceTexture;
	private int[] textures;

	// Board       Width     Depth    Thickness 
	private float w,d,t = 0.2f;

	private float defaultRotation = 0;//200f;
	private float rotation;

	private float[] lightAmbient = {0.4f, 0.4f, 0.4f, 1f};
	private float[] lightDiffuse = {0.8f, 0.8f, 0.8f, 1f};
	private float[] lightPosition = {0f, 10.0f, 0f, 1.0f};

	private float edge = 0.5f;  // Distance between board edge and first row of pieces.
	private float pSize = 0.4f; // Radius of a piece.

	private int rows, cols; // Number of positions in X, Y and Z
	private float spacingRow = 1.25f, spacingCol = 1.25f;

	private ArrayList<Point> order;

	private boolean[][] pinVisible;

	public Render() { glu = new GLU(); }

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

		order = new ArrayList<Point>();
		order();

		rotation = defaultRotation;
	}

	// Ta det försiktigt här.
	// (columns,layers,row)
	public void display(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		if(board == null)
			return;

		// Set camera
		glu.gluLookAt(
				0f, 7.5f, 9f, // Eyes
				0f, 1.5f, 0f, // Look at
				0f, 1f, 0f);  // Up        

		// Draw plate
		gl.glRotatef(rotation, 0f, 1f, 0f);
		drawBoard(gl);

		// Top left marker
		gl.glTranslatef(w,t,d);
		gl.glBindTexture(GL.GL_TEXTURE_2D,0);
		gl.glColor3f(0f,1f,0f);
		glu.gluSphere(glu.gluNewQuadric(), 0.1, 10, 10);
		gl.glTranslatef(-w,-t,-d);

		// Draw Pieces and Pins        
		gl.glTranslatef(w-edge, t+pSize, d-edge);

		for(Point o : order) {
			int row = o.x;
			int col = o.y;
			gl.glTranslatef(-spacingCol*col,0f,-spacingRow*row);
			if(pinVisible[row][col]) {
				ArrayList<Player> pieces = board.getPin(row, col);
				drawPin(gl, pieces.size());
				int z = 0;
				for(Player p : pieces) {
					drawPiece(gl,p);
					gl.glTranslatef(0f,2*pSize,0f);
					z++;
				}
				gl.glTranslatef(0f, -2*pSize*z, 0f);
			}
			gl.glTranslatef(spacingCol*col,0f,spacingRow*row);
		}
		gl.glFlush();
	}

	public void turn(float deg) { 
		rotation = (rotation + deg) % 360;
		reorder();
	}

	public void order () {
		order = new ArrayList<Point>();
		for(int row = 0; row < board.getRows();row++)
			for(int col = 0; col < board.getColumns(); col++)
				order.add(new Point(row,col));
	}
	
	public void reorder() {
		ArrayList<Point> tmp = new ArrayList<Point>();
		HashMap<Point,Double> dist = new HashMap<Point,Double>();
		
		Point2D cpos = new Point2D.Double(2.5*board.getRows()*spacingRow,2.5*board.getColumns()*spacingCol+9);	
		
		for(Point p : order) {
			double px = p.x - cpos.getX();
			double py = p.y - cpos.getY();
			double distance = Math.sqrt(Math.pow(px,2)+Math.pow(py,2));
			//System.out.println(p.x + "," + p.y + " : " + distance);
			dist.put(p, distance);
		}
		
		while(!order.isEmpty()) {
			Point cand = order.get(0);
			for(Point p : order)
				if(dist.get(p) <= dist.get(cand))
					cand = p;
			
			order.remove(cand);
			tmp.add(cand);				
		}
		
		order = tmp;
	}
	

	// Draws the bottom plate.
	private void drawBoard(GL gl) {
		gl.glColor3f(1f,1f,1f);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardTop]);		
		gl.glBegin(GL.GL_QUADS);

		// TOP
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( w, t,  d);	// Top Left
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w, t,  d);	// Top Right
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, t, -d);	// Bottom Right
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( w, t, -d);	// Bottom Left

		gl.glEnd();
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardFront]);		
		gl.glBegin(GL.GL_QUADS);

		// BACK
		gl.glNormal3f(0.0f, 0.0f, -1.0f);		
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-w,  t, d);	// Top Right
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f( w,  t, d);	// Top Left
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f( w, -t, d);	// Bottom Left
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-w, -t, d);	// Bottom Right


		// FRONT        
		gl.glNormal3f(0.0f, 0.0f, 1.0f);		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w,  t, -d);	// Top Right
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( w,  t, -d);	// Top Left
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( w, -t, -d);	// Bottom Left
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, -t, -d);	// Bottom Right

		gl.glEnd();
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardSide]);		
		gl.glBegin(GL.GL_QUADS);

		// RIGHT
		gl.glNormal3f(1.0f, 0.0f, 0.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w,  t,  d);	// Top Right
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-w,  t, -d);	// Top Left
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-w, -t, -d);	// Bottom Left
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, -t,  d);	// Bottom Right

		// LEFT
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(w,  t,  d);	// Top Right
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(w,  t, -d);	// Top Left
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(w, -t, -d);	// Bottom Left
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(w, -t,  d);	// Bottom Right

		gl.glEnd();
	}

	// Draws a single pin.
	private void drawPin(GL gl, int length) {		
		gl.glTranslatef(0f, -pSize, 0f);		
		gl.glRotatef(-90,1f,0f,0f);

		gl.glColor3f(1f,1f,1f);
		gl.glBindTexture(GL.GL_TEXTURE_2D,textures[pinTexture]);

		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricTexture(q, true);				
		glu.gluCylinder(q, 0.1, 0.1, 2*pSize*length+0.3, 10, 10);
		gl.glRotatef(90,1f,0f,0f);
		gl.glTranslatef(0f, +pSize, 0f);
	}

	// Draws a piece for the selected player.
	private void drawPiece(GL gl, Player p) {
		gl.glColor4f(p.getRed(), p.getGreen(), p.getBlue(),0.5f);		
		gl.glBindTexture(GL.GL_TEXTURE_2D,textures[pieceTexture]);			

		gl.glEnable(GL.GL_BLEND);

		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricTexture(q, true);
		glu.gluSphere(q, pSize, 10, 10);

		gl.glDisable(GL.GL_BLEND);
	}

	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged, boolean deviceChanged) {}

	public void init(GLAutoDrawable glDrawable) {
		GL gl = glDrawable.getGL();			
		gl.glShadeModel(GL.GL_SMOOTH);                              // Enable Smooth Shading
		gl.glEnable(GL.GL_TEXTURE_2D);                              // Enable Textures
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);                    // Black Background
		gl.glClearDepth(1.0f);                                      // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);							    // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations

		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT,  lightAmbient,  0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE,  lightDiffuse,  0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPosition, 0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

		boardTop     = 0;
		boardFront   = 1;
		boardSide    = 2;
		pinTexture   = 0;
		pieceTexture = 3;

		String[] txts = {
				"resources/images/board.jpg",
				"resources/images/frontboard.jpg",
				"resources/images/sideboard.jpg",
		"resources/images/frosted_glass.jpg"};

		textures = new int[txts.length];
		loadTexture(glDrawable,txts);				
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

	public boolean loadTexture (GLAutoDrawable glDrawable, String files[]) {
		GL gl = glDrawable.getGL();
		gl.glGenTextures(files.length, textures, 0);

		for(int i = 0; i < files.length; i++) {
			Texture texture = new Texture();
			if(!texture.loadTexture(files[i])) { return false; }

			gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);	        
			gl.glTexEnvf ( GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_MODULATE );

			gl.glTexImage2D(GL.GL_TEXTURE_2D,
					0,
					3,
					texture.getWidth(),
					texture.getHeight(),
					0,
					GL.GL_RGB,
					GL.GL_UNSIGNED_BYTE,
					texture.getPixels());
		}

		return true;
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
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				pinVisible[row][col] = val;
	}	

	public boolean visiblePin(int row, int col) {
		if (row < rows && col < cols)
			return pinVisible[row][col];
		return false;
	}

	public boolean visibleRow(int row) {
		if(row >= rows)
			return false;

		for(int col = 0; col < cols; col++)
			if(pinVisible[row][col])
				return true;

		return false;
	}

	public boolean visibleCol(int col) {
		if(col >= cols)
			return false;
		
		for(int row = 0; row < rows; row++)
			if(pinVisible[row][col])
				return true;

		return false;
	}	
}
