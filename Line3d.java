public class Line3d extends Object3d{
	public Coordinate3d[] ends = new Coordinate3d[2];

	public Line3d() {
		ends[0] = new Coordinate3d();
		ends[1] = new Coordinate3d();
	}

	public Line3d(Coordinate3d p1, Coordinate3d p2) {
		ends[0] = p1;
		ends[1] = p2;
	}

	public Line3d(Line3d line) {
		ends[0] = line.ends[0];
		ends[1] = line.ends[1];
	}

	public double length() {
		return Coordinate3d.subCoordinates(ends[0], ends[1]).absVal();
	}
	
	public Coordinate3d[] getEnds() {
		Coordinate3d[] resultEnds = new Coordinate3d[2];
		double theta;
		double r;
		double tempX, tempY, tempZ;
		for (int i = 0; i < ends.length; i++) {
			theta = Math.atan2(ends[i].y, ends[i].x) + direction.theta;
			r = Math.sqrt(ends[i].x * ends[i].x + ends[i].y * ends[i].y);
			tempX = r * Math.cos(theta);
			tempY = r * Math.sin(theta);
			theta = Math.atan2(ends[i].z, tempX) + direction.phi;
			r = Math.sqrt(tempX * tempX + ends[i].z * ends[i].z);
			tempX = r * Math.cos(theta);
			tempZ = r * Math.sin(theta);
			resultEnds[i] = new Coordinate3d(tempX, tempY, tempZ);
		}
		return resultEnds;
	}
}
