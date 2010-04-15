package View;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.opengl.*;
import javax.swing.JPanel;

import com.sun.opengl.util.FPSAnimator;

public class RenderPanel{

	//private GraphicsDevice usedDevice;
	private JPanel panel;
	private GLCanvas glCanvas;
	private FPSAnimator animator;
	
	RenderPanel(int dimX, int dimY) {		
		//usedDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(dimX,dimY));
		
		glCanvas = new GLCanvas(new GLCapabilities());
		glCanvas.setSize(dimX, dimY);
		glCanvas.setIgnoreRepaint(true);
		glCanvas.addGLEventListener(new Render());
		
		panel.add(glCanvas);
		
		animator = new FPSAnimator( glCanvas, 60 );
        animator.setRunAsFastAsPossible(false);               
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void start() {
		animator.start();
	}
	
	public void stop() {
		animator.stop();
	}
}
