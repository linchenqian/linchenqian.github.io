import java.applet.Applet;
import java.awt.Label;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

public class MainWindow extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
	
	Thread thread;
	int fps; // frames per second
	int delay; // 1000ms/fps
	int cursorX, cursorY;
	CoordinateSystem3d cSYS;
	Solid solid;
	Camera currentCamera;
	double angle;
	double tanAngle2; //Width of the view
	Label lblFPS;
	Label lblAngle;
	Checkbox chkEyeMode;
	
	public void init() {
		this.setSize(1000, 1000);
		fps = 60;
		delay = 1000 / fps;
		tanAngle2 = 2.0;
		angle = Math.atan(tanAngle2 / 2) * 2;
		currentCamera = new Camera(this.getWidth() / angle, Camera.VisionMode.CAMERA);
		cSYS = new CoordinateSystem3d();
		solid = new Solid();
		cSYS.addCamera(currentCamera);
		cSYS.addSolid(solid);
		lblFPS = new Label();
		lblFPS.setFocusable(false);
		lblFPS.setBackground(Color.BLACK);
		lblFPS.setForeground(Color.WHITE);
		lblAngle = new Label("Angle " + angle * 180 / Math.PI);
		lblAngle.setFocusable(false);
		lblAngle.setBackground(Color.BLACK);
		lblAngle.setForeground(Color.WHITE);
		chkEyeMode = new Checkbox("Eye Mode");
		chkEyeMode.setFocusable(false);
		chkEyeMode.setBackground(Color.BLACK);
		chkEyeMode.setForeground(Color.WHITE);
		chkEyeMode.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED)
					currentCamera.mode = Camera.VisionMode.EYE;
				else
					currentCamera.mode = Camera.VisionMode.CAMERA;
			}});
		
		this.add(lblFPS);
		this.add(lblAngle);
		this.add(chkEyeMode);
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				currentCamera.rotate(-(arg0.getX() - cursorX) * Math.PI / 1800, (arg0.getY() - cursorY) * Math.PI / 1800);
				cursorX = arg0.getX();
				cursorY = arg0.getY();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				cursorX = arg0.getX();
				cursorY = arg0.getY();
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// TODO Auto-generated method stub
				switch(currentCamera.mode){
					case CAMERA:
						tanAngle2 += tanAngle2 * e.getWheelRotation() / 100.0;
						angle = Math.atan(tanAngle2 / 2) * 2;
					case EYE:
						angle += angle * e.getWheelRotation() / 100.0;
				}
				currentCamera.ppr = currentCamera.outputImg.getWidth() / angle;
				lblAngle.setText("Angle " + angle * 180 / Math.PI);
			}
			
		});
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// JOptionPane.showMessageDialog(null, arg0.getKeyCode(), "ÌבÊ¾",
				// JOptionPane.OK_OPTION);
				if (arg0.getKeyCode() == KeyEvent.VK_A)
					currentCamera.position.x += 1;
				if (arg0.getKeyCode() == KeyEvent.VK_Z)
					currentCamera.position.x -= 1;
				if (arg0.getKeyCode() == KeyEvent.VK_UP)
					currentCamera.position.z += 1;
				if (arg0.getKeyCode() == KeyEvent.VK_DOWN)
					currentCamera.position.z -= 1;
				if (arg0.getKeyCode() == KeyEvent.VK_LEFT)
					currentCamera.position.y -= 1;
				if (arg0.getKeyCode() == KeyEvent.VK_RIGHT)
					currentCamera.position.y += 1;
				if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD8)
					currentCamera.direction.phi += Math.PI / 90;
				if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD2)
					currentCamera.direction.phi -= Math.PI / 90;
				if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD4)
					currentCamera.direction.theta -= Math.PI / 90;
				if (arg0.getKeyCode() == KeyEvent.VK_NUMPAD6)
					currentCamera.direction.theta += Math.PI / 90;

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		
		thread = new Thread(this);
		thread.start();
	}

	public void start() {
		
	}

	public void stop() {

	}

	public void run() {
		BufferedImage map = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		long timeMarker = System.currentTimeMillis();
		long netDelay;
		while (true) {
			try {
				currentCamera.getImage(map);
				solid.rotate(0.00834, 0.00543);
				refresh(map);
				//this.getGraphics().drawString((1000 / (System.currentTimeMillis() - timeMarker)) + " fps", 10, 10);
				//this.getGraphics().drawString("Angle " + angle * 180 / Math.PI, 10, 30);
				netDelay = delay - (System.currentTimeMillis() - timeMarker);
				if(netDelay < 0)
					netDelay = 0;
				Thread.sleep(netDelay);
				lblFPS.setText((1000 / (System.currentTimeMillis() - timeMarker)) + " fps");
				lblFPS.setBounds(10, 10, 45, 15);
				lblAngle.setBounds(10, 30, 65, 15);
				chkEyeMode.setLocation(10, 50);
				timeMarker = System.currentTimeMillis();
			} catch (Exception e) {
			}
		}
	}

	public void refresh(Image scene) {
		this.getGraphics().drawImage(scene, 0, 0, null);
	}
}
