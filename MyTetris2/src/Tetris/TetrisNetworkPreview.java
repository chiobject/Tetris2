package Tetris;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class TetrisNetworkPreview extends JPanel {

    // serialVersionUID는 객체 직렬화와 관련된 버전 식별자로 사용됩니다.
    private static final long serialVersionUID = 1L;
    
    private TetrisData data; // Tetris 게임 데이터를 저장하는 객체
    public Piece hold = null; // Tetris 블록을 임시로 보관하는 변수
    public Piece current = null; // 현재 Tetris 블록을 저장하는 변수

    public TetrisNetworkPreview(TetrisData data) {
        this.data = data; // TetrisData 객체를 받아서 저장
        repaint(); // JPanel을 다시 그리도록 요청
    }

    public void setCurrentBlock(Piece current) {
        this.current = current; // 현재 Tetris 블록을 설정
        repaint(); // JPanel을 다시 그리도록 요청
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // JPanel의 paint 메서드를 재정의

        // 프리뷰 십자 그리기
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                g.setColor(Constant.getColor(0)); // 색상 설정
                g.draw3DRect(Constant.margin/2 + Constant.w * k,
                        Constant.margin/2 + Constant.w * i, 
                        Constant.w, Constant.w, true); // 3D 테두리 그리기
            }
        }

        // 프리뷰 블록 그리기
        if (current != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(current.getType())); // 현재 Tetris 블록의 타입에 따른 색상 설정
                g.fill3DRect(Constant.margin/2 + Constant.w * (2+current.c[i]), 
                        Constant.margin/2 + Constant.w * (2+current.r[i]), 
                        Constant.w, Constant.w, true); // Tetris 블록 그리기
            }
        }

        // 5x5 그리드를 그리기 위해 다시 반복
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                g.setColor(Constant.getColor(0)); // 색상 설정
                g.draw3DRect(Constant.margin/2 + Constant.w * k,
                        Constant.margin/2 + Constant.w * i + 200, 
                        Constant.w, Constant.w, true); // 3D 테두리 그리기
            }
        }

        // hold 블록 그리기
        if (hold != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(hold.getType())); // hold 중인 Tetris 블록의 타입에 따른 색상 설정
                g.fill3DRect(Constant.margin/2 + Constant.w * (2+hold.c[i]), 
                        Constant.margin/2 + Constant.w * (2+hold.r[i]) + 200, 
                        Constant.w, Constant.w, true); // Tetris 블록 그리기
            }
        }
    }
}
