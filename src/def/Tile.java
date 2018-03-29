package def;

public class Tile {
	int face;
	int X, Y;
	
	public Tile(int X, int Y, int face){
		this.X = X;
		this.Y = Y;
		this.face = face;
	}
	
	public Tile(Tile copy){
		this.X = copy.X;
		this.Y = copy.Y;
		this.face = copy.face;
	}
}
