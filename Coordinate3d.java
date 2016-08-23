
public class Coordinate3d {
	public double x, y, z;

	public Coordinate3d() {
		x = 0.0;
		y = 0.0;
		z = 0.0;
	}

	public Coordinate3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Coordinate3d(int x, int y, int z) {
		this.x = (double) x;
		this.y = (double) y;
		this.z = (double) z;
	}

	public Coordinate3d(Coordinate3d c) {
		x = c.x;
		y = c.y;
		z = c.z;
	}

	public Coordinate3d add(Coordinate3d c) {
		x += c.x;
		y += c.y;
		z += c.z;
		return this;
	}

	public double absVal() {
		return Math.exp(Math.log(x * x + y * y + z * z) / 3); // fastest way to calculate cubic root of (x * x + y * y + z * z)
	}

	public static Coordinate3d addCoordinates(Coordinate3d c1, Coordinate3d c2) {
		return new Coordinate3d(c1.x + c2.x, c1.y + c2.y, c1.z + c2.z);
	}

	public static Coordinate3d subCoordinates(Coordinate3d c1, Coordinate3d c2) {
		return new Coordinate3d(c1.x - c2.x, c1.y - c2.y, c1.z - c2.z);
	}
	
	public static Coordinate3d rotatedCoordinate(Coordinate3d c, Direction3d d){
		double theta;
		double r;
		double tempX, tempY, tempZ;
		theta = Math.atan2(c.y, c.x) + d.theta;
		r = Math.sqrt(c.x * c.x + c.y * c.y);
		tempX = r * Math.cos(theta);
		tempY = r * Math.sin(theta);
		theta = Math.atan2(c.z, tempX) + d.phi;
		r = Math.sqrt(tempX * tempX + c.z * c.z);
		tempX = r * Math.cos(theta);
		tempZ = r * Math.sin(theta);
		return new Coordinate3d(tempX, tempY, tempZ);
	}
}
