package Tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;

public class TetrisNetworkCanvas extends JPanel implements Runnable, ComponentListener {
	private static final long serialVersionUID = 1L;
	protected Thread worker;
	protected TetrisData data;
	protected boolean stop, makeNew;
	protected Piece current;
	public TetrisNetworkPreview preview;
	private Graphics bufferGraphics = null;
	private Image offscreen;
	private Dimension dim;

	// TetrisNetworkCanvas 클래스는 네트워크 대전 화면을 구현하는 패널입니다.

	public TetrisNetworkCanvas() {
		data = new TetrisData();
		addComponentListener(this);
		// TetrisData 객체를 초기화하고 ComponentListener를 패널에 추가합니다.
	}

	public void setTetrisNetworkPreview(TetrisNetworkPreview preview) {
		this.preview = preview;
		// TetrisNetworkPreview를 설정하는 메서드입니다.
	}

	public void initBufferd() {
		dim = getSize();
		setBackground(Color.white);
		// 화면 크기를 가져오고 배경 색상을 흰색으로 설정합니다.
		offscreen = createImage(dim.width, dim.height);
		bufferGraphics = offscreen.getGraphics();
		// 화면 크기와 같은 가상 버퍼 이미지를 생성하고 해당 이미지의 그래픽스 객체를 얻어옵니다.
	}

	public void start() {
		data.clear();
		worker = new Thread(this);
		worker.start();
		repaint();
		// 게임을 시작하는 메서드로, 데이터를 초기화하고 게임 스레드를 시작합니다.
	}

	public void stop() {
		stop = true;
		current = null;
		// 게임을 중지하는 메서드로, 스레드를 중지하고 현재 조각을 null로 설정합니다.
	}

	public void paint(Graphics g) {
		try {
			bufferGraphics.clearRect(0, 0, dim.width, dim.height);
			// 화면을 지우고 새로 그립니다.

			// 그리드 그리기
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

			// 현재 조각 그리기
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
		} catch (Exception e) {
		}
		// 화면을 그리는 메서드로, 게임 상태와 현재 조각을 화면에 그립니다.
	}

	public Dimension getPreferredSize() {
		int tw = Constant.w * TetrisData.COL + Constant.margin;
		int th = Constant.w * TetrisData.ROW + Constant.margin;

		return new Dimension(tw, th);
		// 패널의 기본 크기를 지정하는 메서드입니다.
	}

	public void run() {
		while (!stop) {
			try {
				repaint();
				preview.repaint();
			} catch (Exception e) {
			}
		}
		// 게임을 업데이트하고 화면을 다시 그리는 스레드입니다.
	}

	public TetrisData getData() {
		return data;
		// 현재 게임 데이터를 반환하는 메서드입니다.
	}

	public void setCurrent(Piece current) {
		this.current = current;
		// 현재 게임 조각을 설정하는 메서드입니다.
	}

	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("resize");
		initBufferd();
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
	// ComponentListener 인터페이스의 메서드를 구현한 것으로, 화면 크기가 변경되거나 숨겨질 때 호출됩니다.
}
