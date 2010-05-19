package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.*;
import javax.swing.JPanel;

import com.sun.opengl.util.FPSAnimator;

import model.*;

public class RenderPanel extends JPanel{

	private static final long serialVersionUID = 27030460017242914L;
	private Render render;
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	private OurMouseListener mouseAdapter;
	
	
	RenderPanel(int dimX, int dimY) {
		render       = new Render();
		mouseAdapter = new OurMouseListener(render);
		
		glCanvas = new GLCanvas(new GLCapabilities());
		glCanvas.setSize(dimX, dimY);
		glCanvas.setIgnoreRepaint(true);
		glCanvas.addGLEventListener(render);
		glCanvas.addMouseListener(mouseAdapter);
		glCanvas.addMouseMotionListener(mouseAdapter);
		
		add(glCanvas);	
		
		animator = new FPSAnimator(glCanvas, 60);
        animator.setRunAsFastAsPossible(false);		
	}
	
	public Render getRender() { return render; }	
	public void start() { animator.start(); }	
	public void stop()  { animator.stop(); }
}

class OurMouseListener implements MouseListener, MouseMotionListener {
	
	private Render r;
	private int x;
	
	public OurMouseListener(Render r) { this.r = r; }	
	
	// Get the horizontal position when the mouse is first pressed.
	public void mousePressed(MouseEvent m) { x = m.getXOnScreen(); }
	
	// Use the distance the mouse has been moved to calculated how much the view 
	// should be rotated. One full screen equals one full turn.
	public void mouseDragged(MouseEvent m) {
		r.turn(360*((m.getXOnScreen()-x)/(1f*m.getComponent().getWidth())));
		x = m.getXOnScreen();
	}

	// None of these are used.
	@Override public void mouseClicked(MouseEvent m)  {}
	@Override public void mouseReleased(MouseEvent m) {}
	@Override public void mouseEntered(MouseEvent m)  {}
	@Override public void mouseExited(MouseEvent m)   {}
	@Override public void mouseMoved(MouseEvent m)    {}
}
