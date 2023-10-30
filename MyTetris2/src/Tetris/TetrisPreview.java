package Tetris;
import java.awt.Color;
import java.awt.Font;
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
	public int enemy_score;
	
	public TetrisPreview(TetrisData data) {
		this.data = data;
		repaint();
	}
	
	public void setCurrentBlock(Piece current) {
		this.current = current;
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		data.score = data.getLine() * 175 * Constant.level;
		g.setFont(new Font(null, Font.BOLD, 18)); // 점수 표시하기
		g.drawString("score : " + data.score, 0, 510);

		int line = data.getLine();
		g.setFont(new Font(null, Font.BOLD, 18)); // 지운 줄 표시하기
		g.drawString("line : " + line, 0, 490);

		g.setFont(new Font(null, Font.BOLD, 18)); // 레벨 표시하기
		g.drawString("level : " + Constant.level, 0, 470);

		if (data.ismulti == true) {
			g.setFont(new Font(null, Font.BOLD, 18)); // 상대 점수 표시하기
			g.drawString("enemy score : " + enemy_score, 0, 400);
		}

		// 프리뷰 십자 그리기
		for(int i = 0; i < 5; i++) {
			for(int k = 0; k < 5; k++) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k,
							Constant.margin/2 + Constant.w * i , 
							Constant.w, Constant.w, true);
				System.out.print(data.getAt(i, k) + " ");
			}
			System.out.println("");
		}

		//System.out.println(current);
		// 프리뷰 블록 그리기
		if(current != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(current.getType()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (2+current.c[i]), 
						Constant.margin/2 + Constant.w * (2+current.r[i]), 
						Constant.w, Constant.w, true);
			}
		}
		
		
		// hold 십자 그리기
		for(int i = 0; i < 5; i++) {
			for(int k = 0; k < 5; k++) {
					g.setColor(Constant.getColor(0));
					g.draw3DRect(Constant.margin/2 + Constant.w * k ,
							Constant.margin/2 + Constant.w * i + 200, 
							Constant.w, Constant.w, true);
			}
		}
		
		//System.out.println(current);
		// hold 블록 그리기
		if(hold != null){
			for(int i = 0; i < 4; i++) {
				g.setColor(Constant.getColor(hold.getType()));
				g.fill3DRect(Constant.margin/2 + Constant.w * (2+hold.c[i]), 
						Constant.margin/2 + Constant.w * (2+hold.r[i]) + 200, 
						Constant.w, Constant.w, true);
			}
		}
	}
}
