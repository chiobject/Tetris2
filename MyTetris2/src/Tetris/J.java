package Tetris;


public class J extends Piece {
	public J(TetrisData data) {
		super(data,4,4);
		c[0] = 0;	r[0] = 0;
		c[1] = 1;	r[1] = 0;
		c[2] = -1;	r[2] = 0;
		c[3] = -1;	r[3] = -1;
	}
}
