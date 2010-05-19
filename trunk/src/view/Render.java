package view;

import java.awt.Point;
import java.util.ArrayList;

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
	private int boardTop, boardSide, boardFront, pinTexture, pieceTexture, floorTexture;
	private int[] textures;

	// Board width, depth and thickness 
	private float w,d,t = 0.2f;

	// Camera rotation.
	private float defaultRotation = 200f;
	private float rotation;

	// Light settings.
	private float[] lightAmbient  = {0.4f, 0.4f, 0.4f, 1f};
	private float[] lightDiffuse  = {0.7f, 0.7f, 0.7f, 1f};
	private float[] lightSpecular = {0.7f, 0.7f, 0.7f, 1f};
	private float[] lightPosition = {0f, 10f, 0f, 1f};

	// Common materials
	private float[] green = {0,1,0,1};
	private float[] white = {1,1,1,1};
	private float[] black = {0,0,0,1};
	private float[] grey  = {0.5f,0.5f,0.5f,1f};

	// Fog settings.
	private float fogDensity = 0.03f;

	private float edge     = 0.5f; // Distance between board edge and first row of pieces.
	private float pSize    = 0.4f; // Radius of a piece.
	private float pOpacity = 0.7f; // Pieces transparency.

	private int rows, cols; // Number of rows and columns in the current board.
	private float spacingRow = 1.25f, spacingCol = 1.25f; // Spacing between pins.

	// Orderings used for displaying the pieces with correct blending.
	private ArrayList<Point> currentOrder;
	private ArrayList<Point> frOrder;
	private ArrayList<Point> flOrder;
	private ArrayList<Point> rrOrder;
	private ArrayList<Point> rlOrder;

	// What pins are visible?
	private boolean[][] pinVisible;	
	private boolean[][] markedPin;

	public Render() { glu = new GLU(); }

	public Render(Board board) {
		glu = new GLU();
		setBoard(board);		
	}

	public void setBoard(Board board) {		
		this.board = board;
		rows = board.getRows();
		cols = board.getColumns();

		rotation = defaultRotation;

		pinVisible = new boolean[rows][cols];
		showPins(true);
		markedPin  = new boolean[rows][cols];
		markPins(false);		

		w = edge + (spacingCol * (cols-1))/2;
		d = edge + (spacingRow * (rows-1))/2;

		buildOrderings();
		reorder();		
	}

	public void displayChanged(GLAutoDrawable glDrawable, boolean modeChanged, boolean deviceChanged) {}

	public void init(GLAutoDrawable glDrawable) {
		GL gl = glDrawable.getGL();			
		gl.glShadeModel(GL.GL_SMOOTH);                              // Enable Smooth Shading
		gl.glEnable(GL.GL_TEXTURE_2D);                              // Enable Textures
		gl.glClearColor(0f, 0f, 0f, 1f);                            // Black Background
		gl.glClearDepth(1.0f);                                      // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);							    // Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations
		
		// Cull back-faces.
		gl.glEnable(GL.GL_CULL_FACE);
		gl.glCullFace(GL.GL_BACK);		
		
		// Fog settings.
		gl.glFogi(GL.GL_FOG_MODE, GL.GL_EXP2);
		gl.glFogfv(GL.GL_FOG_COLOR, grey, 0);
		gl.glFogf(GL.GL_FOG_DENSITY, fogDensity);		
		gl.glHint(GL.GL_FOG_HINT,GL.GL_NICEST);
		gl.glEnable(GL.GL_FOG);

		// Set up the light source.
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT,  lightAmbient,  0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE,  lightDiffuse,  0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, lightSpecular, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, lightPosition, 0);		
		gl.glEnable(GL.GL_LIGHT1);
		
		// Enable Lighting and set up the blending function.
		gl.glEnable(GL.GL_LIGHTING);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		// 
		gl.glSampleCoverage(1, true);
		gl.glEnable(GL.GL_MULTISAMPLE);
		gl.glHint (GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		gl.glEnable (GL.GL_LINE_SMOOTH);

		// Load and assign textures.
		pinTexture   = 0;
		boardTop     = 0;
		boardFront   = 1;
		boardSide    = 2;		
		floorTexture = 3;				
		pieceTexture = 4;

		String[] txts = {
				"resources/images/board.jpg",      // 0
				"resources/images/frontboard.jpg", // 1
				"resources/images/sideboard.jpg",  // 2		
				"resources/images/floor.jpg",      // 3
				"resources/images/marble.jpg"};    // 4

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


	// Ta det försiktigt här (columns,layers,row)
	public void display(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		if(board == null)
			return;		

		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT,  white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_DIFFUSE,  white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black, 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK , GL.GL_SHININESS, 100f);

		// Set camera
		glu.gluLookAt(
				0f, 7f, 9f, // Eyes
				0f, 1f, 0f, // Look at
				0f, 1f, 0f);  // Up
		gl.glRotatef(rotation, 0f, 1f, 0f);		

		//Draw background
		drawBackGround(gl);

		// Draw plate
		drawBoard(gl);

		// Top left marker, green color, no texture 
		gl.glPushMatrix();
		{
			gl.glTranslatef(w,t,d);
			gl.glBindTexture(GL.GL_TEXTURE_2D,0);
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE,  green, 0);
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white, 0);
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black, 0);
			gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, 35f);
			glu.gluSphere(glu.gluNewQuadric(), 0.1, 10, 10);
		}
		gl.glPopMatrix();

		// Draw all visible pieces and pins.
		gl.glTranslatef(w-edge, t+pSize, d-edge);
		for(Point o : currentOrder) {
			int row = o.x;
			int col = o.y;
			gl.glTranslatef(-spacingCol*col,0f,-spacingRow*row);
			if(pinVisible[row][col]) {
				ArrayList<Player> pieces = board.getPin(row, col);
				drawPin(gl, pieces.size(),markedPin[row][col]);
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

	// Draw the floor.
	private void drawBackGround(GL gl) {		
		gl.glPushMatrix();
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE,  white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black, 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK , GL.GL_SHININESS, 100f);

		gl.glBindTexture(GL.GL_TEXTURE_2D,textures[floorTexture]);

		int dst = 15;
		int stp = 2*dst;		

		gl.glTranslatef(stp, 0, stp);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {				
				gl.glBegin(GL.GL_QUADS);
				
				gl.glNormal3f(0.0f, 1.0f, 0.0f);				
				gl.glTexCoord2f(0,0);      // Bottom Left
				gl.glVertex3f(dst,0,-dst);				
				gl.glTexCoord2f(1,0);      // Bottom Right
				gl.glVertex3f(-dst,0,-dst);				
				gl.glTexCoord2f(1,1);      // Top Right
				gl.glVertex3f(-dst,0,dst);				
				gl.glTexCoord2f(0,1);      // Top Left
				gl.glVertex3f(dst,0,dst);				
				gl.glEnd();

				gl.glTranslatef(-stp, 0, 0);
			}
			gl.glTranslatef(3*stp,0,-stp);
		}
		gl.glPopMatrix();
	}

	// Draws the board.
	private void drawBoard(GL gl) {
		gl.glPushMatrix();
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white, 0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black, 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK , GL.GL_SHININESS, 100f);
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardTop]);		
		gl.glBegin(GL.GL_QUADS);

		// TOP
		gl.glNormal3f(0.0f, 1.0f, 0.0f);
		
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( w, t, -d);	// Bottom Left				
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, t, -d);	// Bottom Right		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w, t,  d);	// Top Right		
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( w, t,  d);	// Top Left

		gl.glEnd();
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardFront]);		
		gl.glBegin(GL.GL_QUADS);
		
		// FAR END       
		gl.glNormal3f(0.0f, 0.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( w, -t, -d);	// Bottom Right
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, -t, -d);	// Bottom Left		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w,  t, -d);	// Top Right
		gl.glTexCoord2f(0.0f, 1.0f);		
		gl.glVertex3f( w,  t, -d);	// Top Left		
		
		// NEAR END
		gl.glNormal3f(0.0f, 0.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f( w,  t, d);	// Top Left
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w,  t, d);   // Top Right
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, -t, d);	// Bottom Right
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f( w, -t, d);	// Bottom Left

		gl.glEnd();
		gl.glBindTexture(GL.GL_TEXTURE_2D, textures[boardSide]);		
		gl.glBegin(GL.GL_QUADS);

		// LEFT SIDE
		gl.glNormal3f(-1f, 0f, 0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-w,  t,  d);	// Top Left
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-w,  t, -d);	// Top Right
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-w, -t, -d);	// Bottom Right
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-w, -t,  d);	// Bottom Left
		
		// RIGHT SIDE
		gl.glNormal3f(1f, 0f, 0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(w, -t,  d);	// Bottom Left
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(w, -t, -d);	// Bottom Right		
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(w,  t, -d);	// Top Right
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(w,  t,  d);	// Top Left

		gl.glEnd();
		gl.glPopMatrix();
	}

	// Draws a single pin.
	private void drawPin(GL gl, int length, boolean marked) {
		gl.glPushMatrix();

		// Turn the pin black if it has been marked.
		if(marked) {
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, black, 0);
		} else {
			gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
		}
		
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_SPECULAR, white,0);
		gl.glMaterialfv(GL.GL_FRONT_AND_BACK, GL.GL_EMISSION, black, 0);
		gl.glMaterialf(GL.GL_FRONT_AND_BACK, GL.GL_SHININESS, 35f);

		gl.glTranslatef(0f,-pSize, 0f);		
		gl.glRotatef(-90,1f,0f,0f);

		GLUquadric q = glu.gluNewQuadric();
		glu.gluQuadricTexture(q, true);

		// Draw the pin.
		gl.glBindTexture(GL.GL_TEXTURE_2D,textures[pinTexture]);
		glu.gluCylinder(q, 0.1, 0.1, 2*pSize*length+0.3, 10, 10);
		gl.glRotatef(90,1f,0f,0f);
		gl.glTranslatef(0f, 2*pSize*length+0.3f, 0f);
		gl.glRotatef(-90,1f,0f,0f);

		// Draw the "lid".
		glu.gluDisk(q, 0, 0.1, 10, 1);
		gl.glPopMatrix();
	}

	// Draws a piece for the selected player.
	private void drawPiece(GL gl, Player p) {
		gl.glPushMatrix();
		gl.glBindTexture(GL.GL_TEXTURE_2D,textures[pieceTexture]);

		float[] ambient  = {p.getRed(),p.getGreen(),p.getBlue(),1};
		float[] diffuse  = {p.getRed(),p.getGreen(),p.getBlue(),pOpacity};
		float[] specular = {0.9f,0.9f,0.9f,1};
		float[] emission = {p.getRed()*0.25f,p.getGreen()*0.25f,p.getBlue()*0.25f,1};
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT,  ambient,  0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE,  diffuse,  0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, emission, 0);
		gl.glMaterialf(GL.GL_FRONT,  GL.GL_SHININESS, 3f);
		
		gl.glEnable(GL.GL_BLEND);

		GLUquadric q = glu.gluNewQuadric();
		
		glu.gluQuadricTexture(q, true);
		glu.gluSphere(q, pSize, 20, 20);
		gl.glPopMatrix();
	}

	// Rotate the board by the specified angle.
	public void turn(float deg) { 
		rotation = (rotation + deg) % 360;

		if(rotation < 0)
			rotation = 360 - rotation;

		reorder();
	}

	private void buildOrderings () {
		ArrayList<Integer> incRows = new ArrayList<Integer>();
		ArrayList<Integer> decRows = new ArrayList<Integer>();		
		ArrayList<Integer> incCols = new ArrayList<Integer>();
		ArrayList<Integer> decCols = new ArrayList<Integer>();	

		for(int r = 0; r < rows; r++)
			incRows.add(r);

		for(int r = rows-1; r >= 0; r--)
			decRows.add(r);

		for(int c = 0; c < cols; c++)
			incCols.add(c);

		for(int r = cols-1; r >= 0; r--)
			decCols.add(r);

		// Front left order. Increasing columns, decreasing rows.
		flOrder = new ArrayList<Point>();
		for(Integer col : incCols)
			for(Integer row : decRows)
				flOrder.add(new Point(row,col));

		// Front right order. Decreasing rows, decreasing columns.
		frOrder = new ArrayList<Point>();
		for(Integer row : decRows)
			for(Integer col : decCols)
				frOrder.add(new Point(row,col));

		// Rear left order. Increasing rows. Increasing columns.
		rlOrder = new ArrayList<Point>();
		for(Integer row : incRows)
			for(Integer col : incCols)
				rlOrder.add(new Point(row,col));

		// Rear right order. Decreasing columns. Increasing rows.
		rrOrder = new ArrayList<Point>();
		for(Integer col : decCols)
			for(Integer row : incRows)
				rrOrder.add(new Point(row,col));
	}

	private void reorder() {
		if(rotation <= 90) {
			currentOrder = flOrder;
		} else if(rotation <= 180) {
			currentOrder = rlOrder;
		} else if (rotation <= 270) {
			currentOrder = rrOrder;
		} else {
			currentOrder = frOrder;
		}
	}

	// Loads the textures specified in the string array.  
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

	// Switches the visibility for the selected pin.
	public void switchPin(int row, int col) {
		if(row < rows && col < cols)
			pinVisible[row][col] = !pinVisible[row][col];
	}

	// Sets the visibility of all pins on the specified row.
	public void setRow(int row, boolean val) {
		if(row < rows)
			for(int col = 0; col < cols; col++)
				pinVisible[row][col] = val;
	}

	// Sets the visibility of all pins on the specified column.
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
	
	public void markPin(int row, int col, boolean val) {
		if(row < rows && col < cols)
			markedPin[row][col] = val;
	}
	
	public void markPins(boolean val) {
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				markedPin[row][col] = val;		
	}

	// Returns the visibility of the specified pin.
	public boolean visiblePin(int row, int col) {		
		if (row < rows && col < cols)
			return pinVisible[row][col];
		return false;
	}

	// Returns true if there are any visible pieces on the given row, false otherwise. 
	public boolean visibleRow(int row) {
		if(row < rows)
			for(int col = 0; col < cols; col++)
				if(pinVisible[row][col])
					return true;

		return false;
	}

	// Returns true if there are any visible pieces on the given column, false otherwise.
	public boolean visibleCol(int col) {
		if(col < cols)
			for(int row = 	0; row < rows; row++)
				if(pinVisible[row][col])
					return true;

		return false;
	}	
}
