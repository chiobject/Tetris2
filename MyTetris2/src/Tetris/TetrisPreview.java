package Tetris;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class TetrisPreview extends JPanel {
    private TetrisData data;
    protected Piece current = null;
    protected Piece hold = null;
    protected boolean ishold = false;
    public int enemy_score;

    // TetrisPreview 클래스는 Tetris 게임의 미리보기 화면을 담당하는 패널입니다.

    public TetrisPreview(TetrisData data) {
        this.data = data;
        repaint();
        // TetrisData 객체를 초기화하고 패널을 다시 그립니다.
    }

    public void setCurrentBlock(Piece current) {
        this.current = current;
        repaint();
        // 현재 블록을 설정하고 패널을 다시 그립니다.
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 스코어, 지운 줄, 레벨 표시
        data.score = data.getLine() * 175 * Constant.level;
        g.setFont(new Font(null, Font.BOLD, 18));
        g.drawString("score : " + data.score, 0, 510);
        int line = data.getLine();
        g.setFont(new Font(null, Font.BOLD, 18));
        g.drawString("line : " + line, 0, 490);
        g.setFont(new Font(null, Font.BOLD, 18));
        g.drawString("level : " + Constant.level, 0, 470);

        // 멀티플레이 모드인 경우 상대방 점수 표시
        if (data.ismulti == true) {
            g.setFont(new Font(null, Font.BOLD, 18));
            g.drawString("enemy score : " + enemy_score, 0, 400);
        }

        // 미리보기 십자 그리기
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                g.setColor(Constant.getColor(0));
                g.draw3DRect(Constant.margin / 2 + Constant.w * k, Constant.margin / 2 + Constant.w * i, Constant.w,
                        Constant.w, true);
            }
        }

        // 미리보기 블록 그리기
        if (current != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(current.getType()));
                g.fill3DRect(Constant.margin / 2 + Constant.w * (2 + current.c[i]),
                        Constant.margin / 2 + Constant.w * (2 + current.r[i]), Constant.w, Constant.w, true);
            }
        }

        // 홀드 십자 그리기
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                g.setColor(Constant.getColor(0));
                g.draw3DRect(Constant.margin / 2 + Constant.w * k, Constant.margin / 2 + Constant.w * i + 200, Constant.w,
                        Constant.w, true);
            }
        }

        // 홀드 블록 그리기
        if (hold != null) {
            for (int i = 0; i < 4; i++) {
                g.setColor(Constant.getColor(hold.getType()));
                g.fill3DRect(Constant.margin / 2 + Constant.w * (2 + hold.c[i]),
                        Constant.margin / 2 + Constant.w * (2 + hold.r[i]) + 200, Constant.w, Constant.w, true);
            }
        }
    }
}
