package Main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Tetris.*;

public class MyTetris extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TetrisClient originalclient = null;
	JMenuItem startItem;
	
	public MyTetris() {
		JFrame frame = new JFrame("모드 선택");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		JButton button1 = new JButton("노말");
		JButton button2 = new JButton("반전");

		// 버튼 1에 액션 추가
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼 1이 클릭되었을 때 실행할 코드 작성
				NormalTetris();
				frame.dispose();
			}
		});

		// 버튼 2에 액션 추가
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ReverseTetris();
				frame.dispose();
			}
		});

		panel.add(button1);
		panel.add(button2);

		frame.add(panel);
		frame.setSize(300, 100);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	   
	}
	
	public void NormalTetris() {
		setTitle("테트리스");
		setSize(320*4, 600);
		
		GridLayout layout = new GridLayout(1,4);
		setLayout(layout);
		TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
		TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();
		OriginalcreateMenu(tetrisCanvas, netCanvas);
		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		TetrisNetworkPreview netPreview = new TetrisNetworkPreview(netCanvas.getData());
		tetrisCanvas.setTetrisPreview(preview);
		netCanvas.setTetrisNetworkPreview(netPreview);
		add(tetrisCanvas);
		add(preview);
		add(netCanvas);
		add(netPreview);
		
		tetrisCanvas.data.mode = 0;
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void ReverseTetris() {
		setTitle("리버스테트리스");
		setSize(320*4, 600);
		
		GridLayout layout = new GridLayout(1,4);
		setLayout(layout);
		TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
		TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();
		OriginalcreateMenu(tetrisCanvas, netCanvas);
		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		TetrisNetworkPreview netPreview = new TetrisNetworkPreview(netCanvas.getData());
		tetrisCanvas.setTetrisPreview(preview);
		netCanvas.setTetrisNetworkPreview(netPreview);
		add(tetrisCanvas);
		add(preview);
		add(netCanvas);
		add(netPreview);
		
		tetrisCanvas.data.mode = 1;
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void OriginalcreateMenu(TetrisCanvas tetrisCanvas, TetrisNetworkCanvas netCanvas) {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu gameMenu = new JMenu("게임");
		mb.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("시작");
		JMenuItem restartItem = new JMenuItem("재시작");
		JMenuItem exitItem = new JMenuItem("종료");
		
		startItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
				netCanvas.start();
				gameMenu.remove(startItem);
				gameMenu.insert(restartItem, 0);
				restartItem.setEnabled(true);
			}
		});
		
		restartItem.setEnabled(false);
		restartItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tetrisCanvas.stop();
			        tetrisCanvas.start();
			    } catch (Exception e1) {
			    }
			}
		});
		
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.stop();
			}
		});
		
		gameMenu.add(startItem);
		gameMenu.add(exitItem);
		
		JMenu networkMenu = new JMenu("네트워크");
		mb.add(networkMenu);
		
		JMenuItem serverItem = new JMenuItem("서버 생성...");
		networkMenu.add(serverItem);
		JMenuItem clientItem = new JMenuItem("서버 접속...");
		networkMenu.add(clientItem);
		
		serverItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerDialog dialog = new ServerDialog();
				dialog.setVisible(true);
				if(dialog.userChoice == ServerDialog.Choice.OK)
				{
					tetrisCanvas.data.ismulti(true);
					TetrisServer server = new TetrisServer(dialog.getPortNumber());
					server.start();	
					serverItem.setEnabled(false);
				}
			}
		});
		
		clientItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientDialog dialog = new ClientDialog();
				dialog.setVisible(true);
				if(dialog.userChoice == ClientDialog.Choice.OK)
				{
					tetrisCanvas.data.ismulti(true);
					originalclient = new TetrisClient(tetrisCanvas, netCanvas, dialog.getHost(), dialog.getPortNumber());
					originalclient.start();
					clientItem.setEnabled(false);
				}
				
			}
		});
	}
	
	public void originalrefresh() {
		if(originalclient != null)
			originalclient.send();
	}

	public static void main(String[] args) {
		new MyTetris();
	}
}
