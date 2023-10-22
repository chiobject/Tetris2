package Main;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TetrisPreview extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisData data;
	protected Piece current = null;
	protected Piece hold = null;
	protected boolean ishold = false;
	
	public TetrisPreview(TetrisData data) {
		this.data = data;
		repaint();
	}
	
	public void setCurrentBlock(Piece current) {
		this.current = current;
		//System.out.println(current);
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		// 프리뷰 십자 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i , 
							Constant.w, Constant.w, true);
				}
			}
		}
		//System.out.println(current);
		// 프리뷰 블록 그리기
		if(current != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(current.getType()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (1+current.c[i]), 
						Constant.margin/2 + Constant.w * (2+current.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
		
		
		// hold 십자 그리기
		for(int i = 0; i < 4; i++) {
			for(int k = 0; k < 4; k++) {
				if(data.getAt(i, k) == 0) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k ,
							Constant.margin/2 + Constant.w * i + 200, 
							Constant.w, Constant.w, true);
				}
			}
		}
		
		//System.out.println(current);
		// hold 블록 그리기
		if(hold != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(hold.getType()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (1+hold.c[i]), 
						Constant.margin/2 + Constant.w * (2+hold.r[i]) + 200, 
						Constant.w, Constant.w, true);
			}
		}
		
	}
}
