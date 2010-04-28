package View;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.*;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import com.sun.opengl.util.FPSAnimator;

public class RenderPanel extends JPanel{

	private static final long serialVersionUID = 27030460017242914L;
	private Render render;
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	private CMA cma;
	
	RenderPanel(int dimX, int dimY) {
		setPreferredSize(new Dimension(dimX,dimY));		
		render = new Render();
		cma = new CMA(render);
		
		glCanvas = new GLCanvas(new GLCapabilities());
		glCanvas.setSize(dimX, dimY);
		glCanvas.setIgnoreRepaint(true);
		glCanvas.addGLEventListener(render);
		glCanvas.addMouseListener(cma);
		glCanvas.addMouseMotionListener(cma);
		
		add(glCanvas);	
		
		animator = new FPSAnimator(glCanvas, 60);
        animator.setRunAsFastAsPossible(false);           
	}	
	
	public Render getRender() { return render; }	
	public void start() { animator.start(); }	
	public void stop() { animator.stop(); }
}

class CMA implements MouseListener, MouseMotionListener {
	
	private Render r;
	private int x;
	
	public CMA(Render r) {
		this.r = r;
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// Pin selection?
	}

	@Override
	public void mouseEntered(MouseEvent m) {
		// Do nothing		
	}

	@Override
	public void mouseExited(MouseEvent m) {
		// Reset recording?		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		x = m.getXOnScreen();
	}

	@Override
	public void mouseReleased(MouseEvent m) {}

	@Override
	public void mouseDragged(MouseEvent m) {
		// TODO Auto-generated method stub
		r.turn(360*((m.getXOnScreen()-x)/(1f*m.getComponent().getWidth())));
		x = m.getXOnScreen();
	}

	@Override
	public void mouseMoved(MouseEvent m) {
		// Do nothing		
	}
	
}
