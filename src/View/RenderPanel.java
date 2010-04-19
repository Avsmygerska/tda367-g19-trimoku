package View;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.opengl.*;
import javax.swing.JPanel;

import com.sun.opengl.util.FPSAnimator;

public class RenderPanel{

	private Render render;
	private JPanel panel;
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	
	RenderPanel(int dimX, int dimY) {		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(dimX,dimY));
		
		render = new Render();
		
		glCanvas = new GLCanvas(new GLCapabilities());
		glCanvas.setSize(dimX, dimY);
		glCanvas.setIgnoreRepaint(true);
		glCanvas.addGLEventListener(render);
		
		panel.add(glCanvas);
		
		animator = new FPSAnimator(glCanvas, 60);
        animator.setRunAsFastAsPossible(false);               
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public Render getRender() {
		return render;
	}
	
	public void start() {
		animator.start();
	}
	
	public void stop() {
		animator.stop();
	}
}
