import java.util.ArrayList;

public class Solid extends Object3d{
	public ArrayList<Point3d> points;
	public ArrayList<Plane3d> planes;

	public Solid() {
		points = new ArrayList<Point3d>();
		for (int x = -10; x <= 10; x++)
			for (int y = -10; y <= 10; y++)
				for (int z = -10; z <= 10; z++)
					if (Math.abs(x) == 10 && Math.abs(y) == 10
							|| (Math.abs(y) == 10 && Math.abs(z) == 10)
							|| (Math.abs(x) == 10 && Math.abs(z) == 10))
						points.add(new Point3d(x, y, z));
		position = new Coordinate3d(30.0, 0.0, 0.0);
		direction = new Direction3d();
	}
	
	public Solid(ArrayList<Plane3d> planes) {
		
	}

	public ArrayList<Point3d> getPoints() {
		ArrayList<Point3d> resultPoints = new ArrayList<Point3d>();
		Coordinate3d c;
		double theta;
		double r;
		double tempX, tempY, tempZ;
		for (Point3d p : points) {
			c = p.position;
			theta = Math.atan2(c.y, c.x) + direction.theta;
			r = Math.sqrt(c.x * c.x + c.y * c.y);
			tempX = r * Math.cos(theta);
			tempY = r * Math.sin(theta);
			theta = Math.atan2(c.z, tempX) + direction.phi;
			r = Math.sqrt(tempX * tempX + c.z * c.z);
			tempX = r * Math.cos(theta);
			tempZ = r * Math.sin(theta);
			resultPoints.add(new Point3d(tempX, tempY, tempZ));
		}
		return resultPoints;
	}
	
	public ArrayList<Plane3d> getPlanes(){
		ArrayList<Plane3d> resultPlanes = new ArrayList<Plane3d>();
		Plane3d tempPlane;
		for(Plane3d p:planes){
			tempPlane = new Plane3d(p.getVertexes());
			tempPlane.rotate(direction.theta, direction.phi);
			
			tempPlane.position = Coordinate3d.rotatedCoordinate(p.position, direction);
			resultPlanes.add(tempPlane);
		}
		return resultPlanes;
	}
}
