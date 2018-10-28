import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class KeyControl extends JFrame implements KeyListener {
	
	private Scene scene;
	
	public KeyControl(Scene scene) {
		this.scene = scene;
	}
	
	 @Override
	    public void keyTyped(KeyEvent e) {

	    }

	    @Override
	    public void keyPressed(KeyEvent e) {

	        if (e.getKeyCode() == KeyEvent.VK_W) {
	            scene.changeCylinderPosition(0.0f,-0.04f);
	        }
	        if (e.getKeyCode() == KeyEvent.VK_A) {
	            scene.changeCylinderPosition(-0.04f,0.0f);
	        }
	        if (e.getKeyCode() == KeyEvent.VK_S) {
	            scene.changeCylinderPosition(0.0f,0.04f);
	        }
	        if (e.getKeyCode() == KeyEvent.VK_D) {
	            scene.changeCylinderPosition(0.04f,0.0f);
	        }
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
	    }

}
