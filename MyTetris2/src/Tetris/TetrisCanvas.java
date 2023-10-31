package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Main.MyTetris;
import Sound.Sound;

// TetrisCanvas 클래스는 테트리스 게임 화면을 그리고 관리하는 패널입니다.
public class TetrisCanvas extends JPanel implements Runnable, KeyListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    public Thread worker;
    public TetrisData data;
    public boolean stop;
	protected boolean makeNew;
    protected Piece current, hold;
    protected Piece newBlock = null;
    // 그래픽스 함수를 사용하기 위한 클래스
    private Graphics bufferGraphics = null;
    // bufferGraphics로 그림을 그릴 때 실제로 그려지는 가상 버퍼
    private Image offscreen;
    // 화면의 크기를 저장하는 변수
    private Dimension dim;
    protected TetrisPreview preview;
    private MyTetris myTetris;
    public String bgmPath = "sound/TetrisBGM.wav";
    public float volume = -20.0f;
    public Sound bgmsound;
    private String effectPath = "sound/blockbottom.wav";
    private Sound effectsound;

    // 생성자
    public TetrisCanvas(MyTetris t) {
        this.myTetris = t;
        data = new TetrisData();
        addKeyListener(this);
        addComponentListener(this);
    }

    // TetrisPreview 패널과 연결
    public void setTetrisPreview(TetrisPreview preview) {
        this.preview = preview;
    }

    // 버퍼 초기화 함수
    public void initBuffered() {
        dim = getSize();
        setBackground(Color.white);
        offscreen = createImage(dim.width, dim.height);
        bufferGraphics = offscreen.getGraphics();
    }

    // 게임 시작
    public void start() {
        data.clear();
        worker = new Thread(this);
        worker.start();
        makeNew = true;
        stop = false;
        preview.hold = null;
        requestFocus();
        repaint();
        bgmsound = new Sound(bgmPath, volume);
        bgmsound.play();
        bgmsound.loop();
    }

    // 게임 중지
    public void stop() {
        stop = true;
        current = null;
        newBlock = null;
        bgmsound.stop();
        bgmsound.close();
    }

    // 화면 그리기
    public void paint(Graphics g) {
        try {
            bufferGraphics.clearRect(0, 0, dim.width, dim.height);

            // 쌓인 조각들 그리기
            for (int i = 0; i < TetrisData.ROW; i++) {
                for (int k = 0; k < TetrisData.COL; k++) {
                    if (data.getAt(i, k) == 0) {
                        bufferGraphics.setColor(Constant.getColor(data.getAt(i, k)));
                        bufferGraphics.draw3DRect(Constant.margin / 2 + Constant.w * k,
                                Constant.margin / 2 + Constant.w * i, Constant.w, Constant.w, true);
                    } else {
                        bufferGraphics.setColor(Constant.getColor(data.getAt(i, k)));
                        bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * k,
                                Constant.margin / 2 + Constant.w * i, Constant.w, Constant.w, true);
                    }
                }
            }

            // 현재 내려오고 있는 테트리스 조각 그리기
            if (current != null) {
                for (int i = 0; i < 4; i++) {
                    bufferGraphics.setColor(Constant.getColor(current.getType()));
                    bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * (current.getX() + current.c[i]),
                            Constant.margin / 2 + Constant.w * (current.getY() + current.r[i]), Constant.w, Constant.w,
                            true);
                }
            }

            // 가상 버퍼(이미지)를 원본 버퍼에 복사
            g.drawImage(offscreen, 0, 0, this);

            myTetris.originalrefresh();
        } catch (Exception e) {

        }
    }

    public Dimension getPreferredSize() {
        int tw = Constant.w * TetrisData.COL + Constant.margin;
        int th = Constant.w * TetrisData.ROW + Constant.margin;
        return new Dimension(tw, th);
    }

    private Piece createBlock() {
        Piece piece;
        int random = (int) (Math.random() * Integer.MAX_VALUE) % 7;
        switch (random) {
            case 0:
                piece = new Bar(data);
                break;
            case 1:
                piece = new Tee(data);
                break;
            case 2:
                piece = new El(data);
                break;
            case 3:
                piece = new J(data);
                break;
            case 4:
                piece = new S(data);
                break;
            case 5:
                piece = new Z(data);
                break;
            case 6:
                piece = new O(data);
                break;
            default:
                if (random % 2 == 0)
                    piece = new Tee(data);
                else piece = new El(data);
        }
        return piece;
    }

    public void run() {
        while (!stop) {
            try {
                if (makeNew) {
                    if (newBlock != null && preview.ishold == false) {
                        effectsound = new Sound(effectPath, -10);
                        effectsound.play();
                    }

                    if (newBlock == null) {
                        newBlock = createBlock();
                    }
                    current = newBlock;
                    newBlock = createBlock();
                    preview.setCurrentBlock(newBlock);
                    makeNew = false;
                } else {
                    if (current.moveDown()) {
                        makeNew = true;
                        if (current.copy()) {
                            stop();
							Ranking_Record RankingSave = new Ranking_Record(data);
							RankingSave.setLocationRelativeTo(null);
							RankingSave.setVisible(true);
                        }
                        preview.ishold = false;
                        current = null;
                        continue;
                    }
                }
                repaint();
                preview.repaint();
                data.removeLines();
                Thread.sleep(Constant.interval / (Constant.level + 1));
            } catch (ArrayIndexOutOfBoundsException e) {
                stop();
				Ranking_Record RankingSave = new Ranking_Record(data);
				RankingSave.setLocationRelativeTo(null);
				RankingSave.setVisible(true);
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // 키보드를 이용해서 테트리스 조각 제어
    @Override
    public void keyPressed(KeyEvent e) {
        if (current == null) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: // 왼쪽 화살표
                current.moveLeft();
                repaint();
                break;

            case KeyEvent.VK_RIGHT: // 오른쪽 화살표
                current.moveRight();
                repaint();
                break;

            case KeyEvent.VK_UP: // 윗쪽 화살표
                if (data.mode == 0) {
                    current.rotate();
                    repaint();
                } else if (data.mode == 1) {
                    boolean temp = current.moveDown();
                    if (temp) {
                        makeNew = true;
                        preview.ishold = false;
                        if (current.copy()) {
                            stop();
							Ranking_Record RankingSave = new Ranking_Record(data);
							RankingSave.setLocationRelativeTo(null);
							RankingSave.setVisible(true);
                        }
                    }
                    repaint();
                }
                break;

            case KeyEvent.VK_DOWN: // 아랫쪽 화살표
                if (data.mode == 0) {
                    boolean temp = current.moveDown();
                    if (temp) {
                        makeNew = true;
                        preview.ishold = false;
                        if (current.copy()) {
                            stop();
							Ranking_Record RankingSave = new Ranking_Record(data);
							RankingSave.setLocationRelativeTo(null);
							RankingSave.setVisible(true);
                        }
                    }
                    repaint();
                } else if (data.mode == 1) {
                    current.rotate();
                    repaint();
                }
                break;

            case KeyEvent.VK_SPACE:
                while (!current.moveDown()) {
                }
                makeNew = true;
                if (current.copy()) {
                    stop();
					Ranking_Record RankingSave = new Ranking_Record(data);
					RankingSave.setLocationRelativeTo(null);
					RankingSave.setVisible(true);
                }
                preview.ishold = false;
                current = null;
                worker.interrupt();
                repaint();
                break;

            case 67:
                if (preview.hold == null) {
                    preview.hold = current;
                    makeNew = true;
                    preview.ishold = true;
                    preview.repaint();
                    worker.interrupt();
                } else if (preview.hold != null && preview.ishold != true) {
                    Piece temp1;
                    temp1 = current;
                    current = preview.hold;
                    preview.hold = temp1;
                    preview.ishold = true;
                    preview.repaint();
                    worker.interrupt();
                }
                if (data.mode == 0) {
                    preview.hold.center.x = 5;
                    preview.hold.center.y = 0;
                } else if (data.mode == 1) {
                    preview.hold.center.x = 5;
                    preview.hold.center.y = 20;
                }
                break;
        }
    }

    public TetrisData getData() {
        return data;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("resize");
        initBuffered();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
