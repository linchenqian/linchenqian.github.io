

public class Cube extends Compound3d{
	public Cube(){
		for (int x = -10; x <= 10; x++)
			for (int y = -10; y <= 10; y++)
				for (int z = -10; z <= 10; z++)
					if (Math.abs(x) == 10 && Math.abs(y) == 10
							|| (Math.abs(y) == 10 && Math.abs(z) == 10)
							|| (Math.abs(x) == 10 && Math.abs(z) == 10))
						points.add(new Point3d(x, y, z));
	}
}
