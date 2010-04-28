package View;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import Model.*;


public class Render implements GLEventListener {
	private float rquad = 0.0f;
	
	Board brd;

	// Board       Width     Depth    Thickness 
	private float w = 3.0f, d = 3.0f, t = 0.2f;

	private float edge = 0.5f;
	private float pSize = 0.4f;

	private int pX = 5, pY = 5, pZ = 5;

	private boolean[][] pinVisible = new boolean[pX][pY];  

	private float modX, modY;

	private GLU glu = new GLU();

	private float[] lightAmbient = {0.4f, 0.4f, 0.4f, 1f};
	private float[] lightDiffuse = {0.8f, 0.8f, 0.8f, 1f};
	private float[] lightPosition = {10.0f, 10.0f, 10.0f, 1.0f};
	
	float[] col = {
			1f,0f,0f,  // Red
		    0f,1f,0f,  // Green
		    0f,0f,1f}; // Blue
	
	int[] pl = {0,3};

	// Ta det försiktigt här, vårt Z-led är GL:s Y-led.
	public void display(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();                   

		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);

		// Set camera
		glu.gluLookAt(
				7f, 7f, 7f,  // Eyes
				0f, 0f, 0f,  // Look at
				0f, 1f, 0f); // Up        

		// Draw plate
		gl.glRotatef(rquad, 0f, 1f, 0f);    	
		drawPlate(gl);

		// Top left and bottom right marker
		gl.glTranslatef(w,t,d);
		gl.glColor3f(0f, 1f, 0f);
		glu.gluSphere(glu.gluNewQuadric(), 0.1, 10, 10);
		gl.glTranslatef(-2*w,0f,-2*d);
		gl.glColor3f(0f, 0f, 1f);
		glu.gluSphere(glu.gluNewQuadric(), 0.1, 10, 10);
		gl.glTranslatef(w,-t,d);    	

		// Draw Pieces and Pins        
		gl.glTranslatef(w-edge, t+pSize, d-edge);

		for(int x = 0; x < pX; x++) {        	
			for(int y = 0; y < pY; y++) {
				if(pinVisible[x][y]) {
					drawPin(gl);
					int z = 0;
					for(Player p : brd.getPin(x, y)) {
						drawPiece(gl,p);
						gl.glTranslatef(0f,2*pSize,0f);
						z++;
					}
					gl.glTranslatef(0f, -2*pSize*z, 0f);
				}
				gl.glTranslatef(0f, 0f, -modY);
			}        	
			gl.glTranslatef(-modX, 0f, modY*pY);
		}

		gl.glFlush();
	}
	
	public void turn(float deg) {
		rquad += deg;
	}

	// Draws the bottom plate.
	public void drawPlate(GL gl) {
		gl.glBegin(gl.GL_QUADS);

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
	public void drawPin(GL gl) {
		gl.glTranslatef(0f, -pSize, 0f);
		float angle = 90;    	
		gl.glColor3f(0.3f, 0.3f, 0.0f);
		gl.glRotatef(-angle,1f,0f,0f);
		glu.gluCylinder(glu.gluNewQuadric(), 0.1, 0.1, 2*pSize*pZ+0.3, 10, 10);
		gl.glRotatef(angle,1f,0f,0f);
		gl.glTranslatef(0f, +pSize, 0f);
	}

	// Draws a peice for the selected player.
	public void drawPiece(GL gl, Player p) {
		
		gl.glColor3f(p.getRed(), p.getGreen(), p.getBlue());		
		glu.gluSphere(glu.gluNewQuadric(), pSize, 10, 10);
	}

	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
	}
	
	// Switches the visibility state for the selected pin.
	public void switchPin(int x, int y) {
		if( x < pX && y < pY) {
			pinVisible[x][y] = !pinVisible[x][y];			
		}		
	}

	// Shows or hides all pins.
	public void showPins(boolean val) {
		for (int x = 0; x < pX; x++) {
			for (int y = 0; y < pY; y++) {
				pinVisible[x][y] = val;				
			}
		}
	}
	
	public void setBoard(Board b) {
		brd = b;
	}
	
	public void init(GLAutoDrawable gLDrawable) {
		GL gl = gLDrawable.getGL();
		gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
		gl.glClearDepth(1.0f);                      // Depth Buffer Setup
		gl.glEnable(GL.GL_DEPTH_TEST);							// Enables Depth Testing
		gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations

		modX = (2*w - 2*edge)/(pX-1);
		modY = (2*d - 2*edge)/(pY-1);

		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, this.lightAmbient, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, this.lightDiffuse, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, this.lightPosition, 0);
		gl.glEnable(GL.GL_LIGHT1);
		gl.glEnable(GL.GL_LIGHTING);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		
		showPins(true);
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
}
