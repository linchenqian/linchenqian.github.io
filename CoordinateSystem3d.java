import java.awt.Color;
import java.util.ArrayList;

public class CoordinateSystem3d {
	public ArrayList<Camera> cameras;
	public ArrayList<Point3d> points;
	public ArrayList<Line3d> lines;
	public ArrayList<Plane3d> planes;
	public ArrayList<Solid> solids;
	public ArrayList<Compound3d> compounds;
	public Color background = Color.BLACK;
	
	public CoordinateSystem3d(){
		cameras = new ArrayList<Camera>();
		points = new ArrayList<Point3d>();
		lines = new ArrayList<Line3d>();
		solids = new ArrayList<Solid>();
		compounds = new ArrayList<Compound3d>();
	}
	
	public void addCamera(Camera camera){
		cameras.add(camera);
		camera.parentCSYS = this;
	}
	
	public void addPoint(Point3d point){
		points.add(point);
		point.parentCSYS = this;
	}
	
	public void addLine(Line3d line){
		lines.add(line);
		line.parentCSYS = this;
	}
	
	public void addPlane(Plane3d plane){
		planes.add(plane);
		plane.parentCSYS = this;
	}
	
	public void addSolid(Solid solid){
		solids.add(solid);
		solid.parentCSYS = this;
	}
	
	public void addCompound(Compound3d compound){
		compounds.add(compound);
		compound.setParentCSYS(this);
	}
}
