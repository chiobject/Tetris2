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

import Main.*;
import Sound.Sound;

public class TetrisCanvas extends JPanel implements Runnable, KeyListener, ComponentListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Thread worker;
	public TetrisData data;
	protected boolean stop, makeNew;
	protected Piece current, hold;
	protected Piece newBlock = null;
	//그래픽스 함수를 사용하기 위한 클래스
	private Graphics bufferGraphics = null;
	//bufferGraphics로 그림을 그릴 때 실제로 그려지는 가상 버퍼
	private Image offscreen; 
	//화면의 크기를 저장하는 변수
	private Dimension dim;
	protected TetrisPreview preview;
	private MyTetris myTetris;
	private String bgmPath = "sound/TetrisBGM.wav";
	private Sound bgmsound;
	private String effectPath = "sound/blockbottom.wav";
	private Sound effectsound;
	
	public TetrisCanvas(MyTetris t) {
		this.myTetris = t;
		data = new TetrisData();
		addKeyListener(this);		
		addComponentListener(this);
	}
	
	public void setTetrisPreview(TetrisPreview preview) {
		this.preview = preview;
	}
	
	//버퍼 초기 함수
	public void initBufferd() {
		dim = getSize();
        System.out.println(dim.getSize());
        //화면의 크기를 가져온다.
        setBackground(Color.white);
        //배경 색깔을 흰색으로 변경한다. 
        offscreen = createImage(dim.width,dim.height);
        //화면 크기와 똑같은 가상 버퍼(이미지)를 생성한다.
        bufferGraphics = offscreen.getGraphics(); 
        //가상버퍼(이미지)로 부터 그래픽스 객체를 얻어옴
	}
	
	public void start() {    // 게임 시작
		data.clear();
		worker = new Thread(this);
		worker.start();
		makeNew = true;
		stop = false;
		preview.hold=null;
		requestFocus();
		repaint();
		bgmsound = new Sound(bgmPath,-20); 
		bgmsound.play();
		bgmsound.loop();
	}
	
	public void stop() {
		stop = true;
		current = null;
		newBlock = null;
		bgmsound.stop();
		bgmsound.close();
	}
	
	public void paint(Graphics g) {
		try {
			// 화면을 지운다. 지우지 않으면 이전그림이 그대로 남아 잔상이 생김
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

			// 현재 내려오고 있는 테트리스 조각 그리
			if (current != null) {
				for (int i = 0; i < 4; i++) {
					bufferGraphics.setColor(Constant.getColor(current.getType()));
					bufferGraphics.fill3DRect(Constant.margin / 2 + Constant.w * (current.getX() + current.c[i]),
							Constant.margin / 2 + Constant.w * (current.getY() + current.r[i]), Constant.w, Constant.w,
							true);
				}
			}

			// 가상버퍼(이미지)를 원본 버퍼에 복사
			g.drawImage(offscreen, 0, 0, this);

			myTetris.originalrefresh();
		} catch (Exception e) {

		}
	}

	public Dimension getPreferredSize(){ // 테트리스 판의 크기 지정
		int tw = Constant.w * TetrisData.COL + Constant.margin;
		int th = Constant.w * TetrisData.ROW + Constant.margin;
		
		return new Dimension(tw, th);
	}
	
	private Piece createBlock() {
		Piece piece;
		int random = (int)(Math.random() * Integer.MAX_VALUE) % 7;
		switch(random){
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
			if(random % 2 == 0)
				piece = new Tee(data);
			else piece = new El(data);
		}
		return piece;
	}
	public void run(){
		while(!stop) {
			try {
				if(makeNew){ // 새로운 테트리스 조각 만들기 
					if(newBlock != null && preview.ishold == false) {
						effectsound = new Sound(effectPath, 0);
						effectsound.play();
					}
					if (newBlock == null) {
						newBlock = createBlock();
					} 
					current = newBlock;
					newBlock = createBlock();
					preview.setCurrentBlock(newBlock);
					makeNew = false;
				} else { // 현재 만들어진 테트리스 조각 아래로 이동
					System.out.println(data.mode);
					if(current.moveDown()){
						makeNew = true;
						if(current.copy()){
							stop();
							JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + data.score);
						}
						preview.ishold=false;
						current = null;
						continue;
					}
				}
				repaint();
				preview.repaint();
				data.removeLines();
				Thread.sleep(Constant.interval / (Constant.level+1));
			} catch(ArrayIndexOutOfBoundsException e) {
				stop();
				JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + data.score);
			}
			catch(Exception e){ }
			
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	// 키보드를 이용해서 테트리스 조각 제어
	@Override
	public void keyPressed(KeyEvent e) {
		if(current == null) return;
		switch(e.getKeyCode()) {
		case KeyEvent.VK_LEFT: // 왼쪽 화살표
			current.moveLeft();
			repaint();
			break;
			
		case KeyEvent.VK_RIGHT:  // 오른쪽 화살표
			current.moveRight();
			repaint();
			break;
			
		case KeyEvent.VK_UP:  // 윗쪽 화살표
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
						JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + data.score);
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
						JOptionPane.showMessageDialog(this, "게임끝\n점수 : " + data.score);
					}
				}
				repaint();
			} else if (data.mode == 1) {
				current.rotate();
				repaint();
			}
			break;
			
		case KeyEvent.VK_SPACE:
			while(!current.moveDown()) {System.out.println("아"); }
			makeNew = true;
			if(current.copy()){
				stop();
				JOptionPane.showMessageDialog(this,"게임끝\n점수 : " + data.score);
			}
			preview.ishold=false;
			current = null;
			worker.interrupt();
			repaint();
			break;
			
		case 67 :
			if (preview.hold == null) {
				preview.hold = current;	//현재 piece을 hold
				makeNew = true;	// piece 갱신
				preview.ishold = true;	// hold 기능 비활성화(TetrisData에서 활성화)
				preview.repaint();
				worker.interrupt();
			}
			
			else if (preview.hold != null && preview.ishold != true) {
				Piece temp1;
				temp1 = current;	//현재 piece을 hold
				current = preview.hold;	// piece 갱신
				preview.hold = temp1;
				preview.ishold = true;	// hold 기능 비활성화
				preview.repaint();
				worker.interrupt();
			}
			if(data.mode == 0) {
			preview.hold.center.x = 5;
			preview.hold.center.y = 0; 
			}
			else if(data.mode == 1) {
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
	public void keyReleased(KeyEvent e) { }
	
	@Override
	public void componentResized(ComponentEvent e) {
		System.out.println("resize");
		initBufferd();
	}
	
	@Override
	public void componentMoved(ComponentEvent e) { }
	
	@Override
	public void componentShown(ComponentEvent e) { }
	
	@Override
	public void componentHidden(ComponentEvent e) { }
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}

}