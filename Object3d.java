
public class Object3d {
	public Coordinate3d position = new Coordinate3d();
	public Direction3d direction = new Direction3d();
	public CoordinateSystem3d parentCSYS = new CoordinateSystem3d();
	
	public Object3d(){}
	
	public Object3d(Coordinate3d position, Direction3d direction){
		this.position = position;
		this.direction = direction;
	}
	
	public Coordinate3d move(Coordinate3d vector) {
		return position.add(vector);
	}

	public Direction3d rotate(double dTheta, double dPhi) {
		direction.theta += dTheta;
		direction.theta %= 2 * Math.PI;
		direction.phi += dPhi;
		direction.phi %= 2 * Math.PI;
		return direction;
	}
}
