import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.vecmath.Vector3f;

public class ThunderManager {
	private final Timer timer = new Timer();
	private Scene scene;
	
	
	private boolean isPreparing = false;
	private Vector3f thunderPosition;
	
	private long delay = 2000;
	private float hitRadius = 0.3f;
	private int score = 0;
 
    public ThunderManager(Scene scene) {
    	this.scene = scene;
    	start();
    }
 
    public void start() {
        timer.schedule(new TimerTask() {
            public void run() {
                playSound();
            }
            private void playSound() {
                delay = (long) Math.max(delay*0.9f, 300);
                changeThunderPosition();
                start();
                
            }
        }, delay);
    }
    
    private void changeThunderPosition() {
    	if(isPreparing) {
    		playSound();
    		
    		TransformGroup group = scene.getFlashTransform();
        	
        	Transform3D p_cylindra1 = new Transform3D();
    		p_cylindra1.set(thunderPosition);
    		group.setTransform(p_cylindra1);
    		  		
    		if(checkIfHitted(thunderPosition,scene.getPlayerPosition())) {
    			System.out.println("HIT");
    			score-=10; 			
    		}
    		else {
    			score++;   			
    		}
    		scene.setScore(score);
    		
    		timer.schedule(new TimerTask() {
                public void run() {
                	Transform3D p_cylindra1 = new Transform3D();
                	thunderPosition = new Vector3f(90,0,0);
            		p_cylindra1.set(thunderPosition);         		
            		group.setTransform(p_cylindra1);
            		scene.getPjerunTransform().setTransform(p_cylindra1);
                }
            }, delay/2);
    		
    		
    	}
    	else {   		
    		TransformGroup group = scene.getPjerunTransform();
        	
        	Transform3D p_cylindra1 = new Transform3D();
        	thunderPosition = new Vector3f(getRandom(-1.0f,1.0f),0.0f,getRandom(-1.0f,1.0f));
    		p_cylindra1.set(thunderPosition);
    		
    		group.setTransform(p_cylindra1); 
    	}
    	isPreparing = !isPreparing;
    	
    }
    
    
    private void playSound() {
    	try {
	          URL url = this.getClass().getClassLoader().getResource("thunder.wav");
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	          Clip clip = AudioSystem.getClip();
	          clip.open(audioIn);
	          clip.start();
	       } catch (UnsupportedAudioFileException e) {
	          e.printStackTrace();
	       } catch (IOException e) {
	          e.printStackTrace();
	       } catch (LineUnavailableException e) {
	          e.printStackTrace();
	       }
    }
    
    private boolean checkIfHitted(Vector3f object1, Vector3f object2) {
    	float differenceX = Math.abs(object1.x-object2.x);
    	float differenceZ = Math.abs(object1.z-object2.z);
    	return (differenceX < hitRadius && differenceZ < hitRadius);
    }
    
    private float getRandom(float min, float max) {
    	Random r = new Random();
    	double random = min + r.nextDouble() * (max - min);
    	return (float) random;
    }
 
}
