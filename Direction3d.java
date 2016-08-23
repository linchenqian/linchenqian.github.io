public class Direction3d {
	public double theta, phi;

	public Direction3d() {
		theta = 0.0;
		phi = 0.0;
	}

	public Direction3d(double theta, double phi) {
		this.theta = theta % (Math.PI * 2);
		this.phi = phi % (Math.PI * 2);
	}
}
