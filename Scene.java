import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.universe.SimpleUniverse;



public class Scene extends JFrame {
	
	private TransformGroup transformacja_c;
	private TransformGroup transformacja_pjeruna;
	private TransformGroup transformacja_flash;
	
	private Text3D text3d;
	
	private float x;
	private float z;

	public Scene(){
		 	super("Grafika 3D");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setResizable(false);


	        GraphicsConfiguration config =
	           SimpleUniverse.getPreferredConfiguration();

	        Canvas3D canvas3D = new Canvas3D(config);
	        canvas3D.setPreferredSize(new Dimension(1200,800));

	        add(canvas3D);
	        
	        pack();
	        add(new Text());
	        setVisible(true);
	        addKeyListener(new KeyControl(this));

	        BranchGroup scena = utworzScene();
		    scena.compile();
		    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

	        Transform3D przesuniecie_obserwatora = new Transform3D();
	        Transform3D rot_obs = new Transform3D();
	        rot_obs.rotY((float)(-Math.PI/7));
	        przesuniecie_obserwatora.set(new Vector3f(-1.3f,2f,3.1f));
	        przesuniecie_obserwatora.mul(rot_obs);
	        rot_obs.rotX((float)(-Math.PI/6));
	        przesuniecie_obserwatora.mul(rot_obs);

	        simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);

	        simpleU.addBranchGraph(scena);
	        
	        //start thunder manager
	        new ThunderManager(this);
	        
	}
	
	public void paint(Graphics g) {
		g.drawString("abc", 25, 25);
         
    }
	

	private BranchGroup utworzScene() {

		int i;

		BranchGroup wezel_scena = new BranchGroup();

		Appearance wyglad_ziemia = new Appearance();
		Appearance wyglad_daszek = new Appearance();

		Material wmaterial_daszek = new Material(new Color3f(0.0f, 0.1f, 0.0f), new Color3f(0.3f, 0.0f, 0.3f),
				new Color3f(0.6f, 0.1f, 0.1f), new Color3f(1.0f, 0.5f, 0.5f), 80.0f);
		wyglad_daszek.setMaterial(wmaterial_daszek);



		Point3f[] coords = new Point3f[4];
		for (i = 0; i < 4; i++)
			coords[i] = new Point3f();

		Point2f[] tex_coords = new Point2f[4];
		for (i = 0; i < 4; i++)
			tex_coords[i] = new Point2f();

		coords[0].y = 0.0f;
		coords[1].y = 0.0f;
		coords[2].y = 0.0f;
		coords[3].y = 0.0f;

		coords[0].x = 1.0f;
		coords[1].x = 1.0f;
		coords[2].x = -1f;
		coords[3].x = -1f;

		coords[0].z = 1f;
		coords[1].z = -1f;
		coords[2].z = -1f;
		coords[3].z = 1f;


		// ziemia

		QuadArray qa_ziemia = new QuadArray(4, GeometryArray.COORDINATES | GeometryArray.TEXTURE_COORDINATE_2);
		qa_ziemia.setCoordinates(0, coords);

		Shape3D ziemia = new Shape3D(qa_ziemia);
		wyglad_ziemia.setColoringAttributes(new ColoringAttributes(0.7f,0.7f,0.9f,ColoringAttributes.NICEST));
		ziemia.setAppearance(wyglad_ziemia);

		wezel_scena.addChild(ziemia);
		
		//tworzenie gracza
		
		Appearance  wygladCylindra = new Appearance();
		wygladCylindra.setColoringAttributes(new ColoringAttributes(0.5f,0.5f,0.9f,ColoringAttributes.NICEST));
		
		Cylinder cylinder = new Cylinder(0.1f, 0.2f, wygladCylindra);
		
		Transform3D p_cylindra   = new Transform3D();
		p_cylindra.set(new Vector3f(0.0f,0.1f,0.0f));
		  
		transformacja_c = new TransformGroup(p_cylindra);
		transformacja_c.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformacja_c.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformacja_c.addChild(cylinder);
		
		wezel_scena.addChild(transformacja_c);
		
		//tworzenie pieruna
		Appearance  wygladPieruna = new Appearance();
		wygladCylindra.setColoringAttributes(new ColoringAttributes(0.5f,0.5f,0.9f,ColoringAttributes.NICEST));
		
		Cylinder pjerun = new Cylinder(0.005f, 8f, wygladPieruna);
		
		Transform3D transform_pjeruna   = new Transform3D();
		transform_pjeruna.set(new Vector3f(90.0f,0.0f,0.0f));
		  
		transformacja_pjeruna = new TransformGroup(transform_pjeruna);
		transformacja_pjeruna.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformacja_pjeruna.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformacja_pjeruna.addChild(pjerun);
		
		wezel_scena.addChild(transformacja_pjeruna);
		
		//tworzenie blysku		
		Appearance  wygladFlash = new Appearance();
		wygladFlash.setColoringAttributes(new ColoringAttributes(1f,1f,0f,ColoringAttributes.NICEST));
		
		Cylinder flash = new Cylinder(0.1f, 8f, wygladFlash);
		
		Transform3D transform_flash   = new Transform3D();
		transform_flash.set(new Vector3f(90.0f,0.0f,0.0f));
		  
		transformacja_flash = new TransformGroup(transform_flash);
		transformacja_flash.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		transformacja_flash.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		transformacja_flash.addChild(flash);
		
		wezel_scena.addChild(transformacja_flash);
		
		
		Appearance  wygladTekst = new Appearance();
	      wygladTekst.setColoringAttributes(new ColoringAttributes(1f,1f,1f,ColoringAttributes.NICEST));
	
	      Font3D font3d = new Font3D(new Font("Arial",Font.BOLD,1),
	              new FontExtrusion(new java.awt.geom.Line2D.Float(0, 0, 0.1f, 1)));
	      text3d = new Text3D(font3d, new String("0"), new Point3f(-1f,0.0f,-1.2f));
	      text3d.setCapability(Text3D.ALLOW_STRING_WRITE);
	      Shape3D textShape = new Shape3D(text3d,wygladTekst);
	      wezel_scena.addChild(textShape);
	      

		return wezel_scena;
		
	}
	
	public Vector3f getPlayerPosition() {
		return new Vector3f(x,0.0f,z);
	}
	
	public TransformGroup getPjerunTransform() {
		return transformacja_pjeruna;
	}
	
	public TransformGroup getFlashTransform() {
		return transformacja_flash;
	}
	
	public void setScore(int score) {
		text3d.setString(String.valueOf(score));
	}
	
	public void changeCylinderPosition(float deltaX, float deltaZ) {
		x+=deltaX;
		z+=deltaZ;
		
		float border = 0.91f;
		x = Math.min(x, border);
		x = Math.max(x, -border);
		z = Math.min(z, border);
		z = Math.max(z, -border);
		
		updateCylinderPosition();
	}
	
	private void updateCylinderPosition() {
		Transform3D p_cylindra1 = new Transform3D();
		p_cylindra1.set(new Vector3f(x,0.1f,z));
		transformacja_c.setTransform(p_cylindra1);      
	}
}
