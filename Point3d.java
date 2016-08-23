public class Point3d extends Object3d{

	public Point3d() {}
	
	public Point3d(double x, double y, double z) {
		position = new Coordinate3d(x, y, z);
	}

	public Point3d(int x, int y, int z) {
		position = new Coordinate3d(x, y, z);
	}
	
	public Point3d(Coordinate3d c) {
		position = c;
	}

	public Point3d(Point3d p) {
		position = p.position;
	}
}
