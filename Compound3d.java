import java.util.ArrayList;

public class Compound3d extends Object3d{
	public Compound3d parentCompound;
	public ArrayList<Point3d> points;
	public ArrayList<Line3d> lines;
	public ArrayList<Plane3d> planes;
	public ArrayList<Solid> solids;
	public ArrayList<Compound3d> compounds;
	
	public void addPoint(Point3d point){
		points.add(point);
		point.parentCSYS = parentCSYS;
	}
	
	public void addLine(Line3d line){
		lines.add(line);
		line.parentCSYS = parentCSYS;
	}
	
	public void addPlane(Plane3d plane){
		planes.add(plane);
		plane.parentCSYS = parentCSYS;
	}
	
	public void addSolid(Solid solid){
		solids.add(solid);
		solid.parentCSYS = parentCSYS;
	}
	
	public void addCompound(Compound3d compound){
		compounds.add(compound);
		compound.parentCompound = this;
		compound.parentCSYS = parentCSYS;
	}
	
	public void setParentCSYS(CoordinateSystem3d cSYS){
		for(Point3d p:points)
			p.parentCSYS = cSYS;
		for(Line3d l:lines)
			l.parentCSYS = cSYS;
		for(Plane3d p:planes)
			p.parentCSYS = cSYS;
		for(Solid s:solids)
			s.parentCSYS = cSYS;
		for(Compound3d c:compounds)
			c.setParentCSYS(cSYS);
	}
	
	public ArrayList<Point3d> getPoints(){
		ArrayList<Point3d> resultPoints = new ArrayList<Point3d>();
		for(Point3d p : points)
			resultPoints.add(new Point3d(new Coordinate3d(Coordinate3d.rotatedCoordinate(p.position, direction))));
		return resultPoints;
	}
	
	public ArrayList<Line3d> getLines(){
		ArrayList<Line3d> resultLines = new ArrayList<Line3d>();
		Line3d tempLine;
		for(Line3d l:lines){
			tempLine = new Line3d(l.getEnds()[0], l.getEnds()[1]);
			tempLine.rotate(direction.theta, direction.phi);
			tempLine.position = Coordinate3d.rotatedCoordinate(l.position, direction);
			resultLines.add(tempLine);
		}
		return resultLines;
	}
	
	public ArrayList<Plane3d> getPlanes(){
		ArrayList<Plane3d> resultPlanes = new ArrayList<Plane3d>();
		//ArrayList<Coordinate3d> tempVertexes;
		Plane3d tempPlane;
		for(Plane3d p:planes){
			//tempVertexes = new ArrayList<Coordinate3d>();
			//for(Coordinate3d v:p.getVertexes())
			//	tempVertexes.add(Coordinate3d.rotatedCoordinate(v, direction));
			//tempPlane = new Plane3d(tempVertexes);
			
			tempPlane = new Plane3d(p.getVertexes());
			tempPlane.rotate(direction.theta, direction.phi);
			
			tempPlane.position = Coordinate3d.rotatedCoordinate(p.position, direction);
			resultPlanes.add(tempPlane);
		}
		return resultPlanes;
	}
	
	public ArrayList<Solid> getSolids(){
		ArrayList<Solid> resultSolids = new ArrayList<Solid>();
		Solid tempSolid;
		for(Solid s:solids){
			tempSolid = new Solid(s.getPlanes());
			tempSolid.rotate(direction.theta, direction.phi);
			tempSolid.position = Coordinate3d.rotatedCoordinate(s.position, direction);
			resultSolids.add(tempSolid);
		}
		return resultSolids;
	}
	
	public ArrayList<Compound3d> getCompounds(){
		ArrayList<Compound3d> resultCompounds = new ArrayList<Compound3d>();
		Compound3d tempCompound = new Compound3d();
		for(Compound3d c:compounds){
			for(Point3d p:c.getPoints())
				tempCompound.addPoint(p);
			for(Line3d l:c.getLines())
				tempCompound.addLine(l);
			for(Plane3d p:c.getPlanes())
				tempCompound.addPlane(p);
			for(Solid s:c.getSolids())
				tempCompound.addSolid(s);
			for(Compound3d subC:c.getCompounds())
				tempCompound.addCompound(subC);
			tempCompound.rotate(direction.theta, direction.phi);
			tempCompound.position = Coordinate3d.rotatedCoordinate(c.position, direction);
			resultCompounds.add(tempCompound);
		}
		return resultCompounds;
		
	}
}
