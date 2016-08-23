import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.Point2D;

public class Camera extends Object3d{
	
	public static enum VisionMode{EYE, CAMERA}
	
	public double ppr = 2000 / Math.PI; //pixels per radius
	public VisionMode mode = VisionMode.CAMERA;
	public BufferedImage outputImg;
	double ratioScaleX; // for camera mode
	double ratioScaleY; // for camera mode

	public Camera() {}

	public Camera(Coordinate3d position, Direction3d direction) {
		super(position,direction);
	}

	public Camera(Coordinate3d position, Direction3d direction, double ppr) {
		super(position,direction);
		this.ppr = ppr;
	}

	public Camera(Coordinate3d position, Direction3d direction, double ppr, VisionMode mode) {
		super(position,direction);
		this.ppr = ppr;
		this.mode = mode;
	}

	public Camera(double ppr) {
		this.ppr = ppr;
	}

	public Camera(double ppr, VisionMode mode) {
		this.ppr = ppr;
		this.mode = mode;
	}
	
	public Camera(VisionMode mode) {
		this.mode = mode;
	}
	
	public void getImage(BufferedImage outputImg){
		this.outputImg = outputImg;
		Graphics2D g2d = (Graphics2D) outputImg.getGraphics();
		g2d.setBackground(parentCSYS.background);
		g2d.setColor(Color.WHITE);
		g2d.clearRect(0, 0, outputImg.getWidth(), outputImg.getHeight());
		if(mode == Camera.VisionMode.CAMERA){
			ratioScaleX = outputImg.getWidth() / 2 / Math.tan(outputImg.getWidth() / 2 / ppr);
			ratioScaleY = outputImg.getHeight() / 2 / Math.tan(outputImg.getHeight() / 2 / ppr);
		}
		for (Solid s : parentCSYS.solids){
			plotSolid(g2d, s, new Coordinate3d());
		}
	}

	public Point2D.Double getOnScenePosition(Coordinate3d p) {
		double theta = Math.atan2(p.y, p.x) - direction.theta;
		double r = Math.sqrt(p.x * p.x + p.y * p.y);
		p.x = r * Math.cos(theta);
		p.y = r * Math.sin(theta);
		theta = Math.atan2(p.z, p.x) - direction.phi;
		r = Math.sqrt(p.x * p.x + p.z * p.z);
		p.x = r * Math.cos(theta);
		p.z = r * Math.sin(theta);
		
		switch(mode){
			case EYE:
				theta = Math.atan2(p.z, p.y);
				r = Math.atan2(Math.sqrt(p.y * p.y + p.z * p.z), p.x) * ppr;
				return new Point2D.Double((r * Math.cos(theta)) + outputImg.getWidth() / 2, -(r * Math.sin(theta)) + outputImg.getHeight() / 2);
			
			case CAMERA:
				if (p.x >= 0)
					return new Point2D.Double((p.y / p.x * ratioScaleX) + outputImg.getWidth() / 2, -(p.z / p.x * ratioScaleY) + outputImg.getHeight() / 2);
		}
		return new Point2D.Double(-1.0, -1.0);
	}

	public void plotPoint3d(Graphics2D g2d, Point3d p, Coordinate3d shift) {
		Point2D.Double onScreenCoordinate = getOnScenePosition(Coordinate3d.addCoordinates(p.position, shift));
		int x = (int)onScreenCoordinate.x, y = (int)onScreenCoordinate.y;
		g2d.drawLine(x, y, x, y);
	}
	
	public void plotLine3d(Graphics2D g2d, Line3d l, Coordinate3d shift){
		Coordinate3d relativePosition = Coordinate3d.addCoordinates(l.position, shift);
		Coordinate3d[] ends = l.getEnds();
		Point2D.Double[] onScreenCoordinate = {getOnScenePosition(Coordinate3d.addCoordinates(relativePosition, ends[0])), getOnScenePosition(Coordinate3d.addCoordinates(relativePosition, ends[1]))};
		int x1 = (int)onScreenCoordinate[0].x, y1 = (int)onScreenCoordinate[0].y;
		int x2 = (int)onScreenCoordinate[1].x, y2 = (int)onScreenCoordinate[1].y;
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	public void plotPlane3d(Graphics2D g2d, Plane3d p, Coordinate3d shift){
		
	}
	
	public void plotSolid(Graphics2D g2d, Solid s, Coordinate3d shift) {
		Coordinate3d relativePosition = Coordinate3d.subCoordinates(s.position, position);
		for (Point3d p : s.getPoints())
			plotPoint3d(g2d, new Point3d(Coordinate3d.subCoordinates(relativePosition, p.position)), shift);
	}
	
	public void plotCompound3d(Graphics2D g2d, Compound3d c, Coordinate3d shift){
		//for(Point3d p:c.points)
			
	}
}
