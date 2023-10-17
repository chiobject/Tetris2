package Main;


public class O extends Piece {
	public O(TetrisData data) {
		super(data);
		c[0] = 0;	r[0] = 0;
		c[1] = 1;	r[1] = 0;
		c[2] = 1;	r[2] = -1;
		c[3] = 0;	r[3] = -1;
	}
		 
	public int getType() {
		return 7;
	}
	public int roteType() {
		return 4;
	}
}
