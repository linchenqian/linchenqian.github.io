import java.util.ArrayList;


public class Plane3d extends Object3d{
	public ArrayList<Coordinate3d> vertexes;
	
	public Plane3d(){}
	
	public Plane3d(ArrayList<Coordinate3d> vertexes){
		this.vertexes = vertexes;
	}
	
	public Plane3d(ArrayList<Coordinate3d> vertexes, Coordinate3d position, Direction3d direction){
		super(position, direction);
		this.vertexes = vertexes;
	}
	
	public void addVertex(Coordinate3d vertex){
		vertexes.add(vertex);
	}
	
	public void addVertex(int index, Coordinate3d vertex){
		vertexes.add(index, vertex);
	}
	
	public ArrayList<Coordinate3d> getVertexes() {
		ArrayList<Coordinate3d> resultVertexes = new ArrayList<Coordinate3d>();
		double theta;
		double r;
		double tempX, tempY, tempZ;
		for (Coordinate3d v : vertexes) {
			theta = Math.atan2(v.y, v.x) + direction.theta;
			r = Math.sqrt(v.x * v.x + v.y * v.y);
			tempX = r * Math.cos(theta);
			tempY = r * Math.sin(theta);
			theta = Math.atan2(v.z, tempX) + direction.phi;
			r = Math.sqrt(tempX * tempX + v.z * v.z);
			tempX = r * Math.cos(theta);
			tempZ = r * Math.sin(theta);
			resultVertexes.add(new Coordinate3d(tempX, tempY, tempZ));
		}
		return resultVertexes;
	}
}
