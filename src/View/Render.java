package View;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Render implements GLEventListener {
    private float rquad = 0.0f;
    
    // Board       Width     Depth    Thickness 
    private float w = 3.0f, d = 3.0f, t = 0.2f;
    
    private float edge = 0.1f;
    private float pSize = 0.2f;
    
    private int pX = 5, pY = 5, pZ = 5;
    
    private GLU glu = new GLU();
    
    public void display(GLAutoDrawable gLDrawable) {
        GL gl = gLDrawable.getGL();
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        // Set camera
        glu.gluLookAt(10f, 10f, 10f, // Eyes   
  		      0f, 0f, 0f,            // Look at
  		      0f, 1f, 0f);           // Up        
        
        gl.glRotatef(rquad, 0f, 1f, 0f);
        drawPlate(gl);
        gl.glTranslatef(w-edge, 0f, d-edge);
        
        for(int i = 0; i < 5; i++) {
        	gl.glTranslatef(0f,0.4f,0f);
        	drawPiece(gl);
        }
        gl.glFlush();
        rquad += 0.15f;
    }
    
    public void drawPlate(GL gl) {
    	gl.glBegin(gl.GL_QUADS);
    	
        gl.glColor3f(1.0f, 0.5f, 0.0f);		// Set The Color To Orange    	
        gl.glVertex3f( w, t, -d);	// Top Right Of The Quad (Top)
        gl.glVertex3f(-w, t, -d);	// Top Left Of The Quad (Top)
        gl.glVertex3f(-w, t,  d);	// Bottom Left Of The Quad (Top)
        gl.glVertex3f( w, t,  d);	// Bottom Right Of The Quad (Top)

        gl.glColor3f(1.0f, 1.0f, 0.0f);		// Change color;
        gl.glVertex3f( w, -t,  d);	// Top Right Of The Quad (Bottom)
        gl.glVertex3f(-w, -t,  d);	// Top Left Of The Quad (Bottom)
        gl.glVertex3f(-w, -t, -d);	// Bottom Left Of The Quad (Bottom)
        gl.glVertex3f( w, -t, -d);	// Bottom Right Of The Quad (Bottom)

        gl.glColor3f(1.0f, 0.0f, 1.0f);		// Change color;
        gl.glVertex3f( w,  t, d);	// Top Right Of The Quad (Front)
        gl.glVertex3f(-w,  t, d);	// Top Left Of The Quad (Front)
        gl.glVertex3f(-w, -t, d);	// Bottom Left Of The Quad (Front)
        gl.glVertex3f( w, -t, d);	// Bottom Right Of The Quad (Front)

        gl.glColor3f(0.0f, 1.0f, 1.0f);		// Change color;
        gl.glVertex3f( w, -t, -d);	// Bottom Left Of The Quad (Back)
        gl.glVertex3f(-w, -t, -d);	// Bottom Right Of The Quad (Back)
        gl.glVertex3f(-w,  t, -d);	// Top Right Of The Quad (Back)
        gl.glVertex3f( w,  t, -d);	// Top Left Of The Quad (Back)

        gl.glColor3f(0.5f, 0.0f, 0.5f);		// Change color;
        gl.glVertex3f(-w,  t,  d);	// Top Right Of The Quad (Left)
        gl.glVertex3f(-w,  t, -d);	// Top Left Of The Quad (Left)
        gl.glVertex3f(-w, -t, -d);	// Bottom Left Of The Quad (Left)
        gl.glVertex3f(-w, -t,  d);	// Bottom Right Of The Quad (Left)

        gl.glColor3f(0.5f, 1.0f, 0.5f);		// Change color;
        gl.glVertex3f(w,  t, -d);	// Top Right Of The Quad (Right)
        gl.glVertex3f(w,  t,  d);	// Top Left Of The Quad (Right)
        gl.glVertex3f(w, -t,  d);	// Bottom Left Of The Quad (Right)
        gl.glVertex3f(w, -t, -d);	// Bottom Right Of The Quad (Right)
    	gl.glEnd();
    }
    
    public void drawPin(GL gl) {
    	
    }
    
    public void drawPiece(GL gl) {
    	gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
    	glu.gluSphere(glu.gluNewQuadric(), pSize, 10, 10);
    }
    
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    public void init(GLAutoDrawable gLDrawable) {
        GL gl = gLDrawable.getGL();
        gl.glShadeModel(GL.GL_SMOOTH);              // Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
        gl.glEnable(GL.GL_DEPTH_TEST);							// Enables Depth Testing
        gl.glDepthFunc(GL.GL_LEQUAL);								// The Type Of Depth Testing To Do
        gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);	// Really Nice Perspective Calculations
    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        final GL gl = gLDrawable.getGL();

        if (height <= 0) // avoid a divide by zero error!
            height = 1;
        final float h = (float) width / (float) height;        
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
                
        glu.gluPerspective(45.0f, h, 1.0, 100.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);        
        gl.glLoadIdentity();
    }
}
