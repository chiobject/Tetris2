package Main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.JLabel;

import Sound.Sound;
import Tetris.TetrisClient;
import Tetris.TetrisCanvas;
import Tetris.TetrisNetworkCanvas;
import Tetris.TetrisNetworkPreview;
import Tetris.TetrisData;
import Tetris.TetrisPreview;
import Tetris.ServerDialog;
import Tetris.ClientDialog;
import Tetris.Ranking_Leaderboard;
import Tetris.TetrisServer;
import Tetris.ControlWindow;
import Tetris.HelpWindow;
import Tetris.HistoryWindow;

public class MyTetris extends JFrame {
	/**
	 * 직렬화 버전 UID
	 */
	private static final long serialVersionUID = 1L;

	// TetrisClient와 TetrisServer의 인스턴스를 생성
	private TetrisClient originalclient = null;
	private JFrame frame;
	private JSlider volumeSlider;
	private JComboBox<String> soundComboBox;
	private String[] sounds;
	private Sound selectedSound;
	private float volume = -20.0f;
	JMenuItem changeItem = new JMenuItem("모드 변경");

	// TetrisCanvas의 인스턴스 생성
	TetrisCanvas tetrisCanvas = new TetrisCanvas(this);
	JMenuItem startItem;

	public void NormalTetris() {
		setTitle("테트리스");
		setSize(320 * 4, 600);

		// 그리드 레이아웃 설정
		GridLayout layout = new GridLayout(1, 4);
		setLayout(layout);

		// TetrisNetworkCanvas 인스턴스 생성
		TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();

		// 메뉴를 생성하고 TetrisCanvas 및 TetrisNetworkCanvas 인스턴스를 전달
		createMenu(tetrisCanvas, netCanvas);

		// TetrisPreview 및 TetrisNetworkPreview 인스턴스 생성
		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		TetrisNetworkPreview netPreview = new TetrisNetworkPreview(netCanvas.getData());

		// TetrisCanvas 및 TetrisNetworkCanvas에 TetrisPreview 및 TetrisNetworkPreview 설정
		tetrisCanvas.setTetrisPreview(preview);
		netCanvas.setTetrisNetworkPreview(netPreview);

		// 컴포넌트를 프레임에 추가
		add(tetrisCanvas);
		add(preview);
		add(netCanvas);
		add(netPreview);

		// 게임 모드 설정
		tetrisCanvas.data.mode = 0;
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false); // 크기 조절 불가능으로 설정
		setVisible(true);
	}

	public void ReverseTetris() {
		setTitle("리버스테트리스");
		setSize(320 * 4, 600);

		// 그리드 레이아웃 설정
		GridLayout layout = new GridLayout(1, 4);
		setLayout(layout);

		// TetrisNetworkCanvas 인스턴스 생성
		TetrisNetworkCanvas netCanvas = new TetrisNetworkCanvas();

		// 메뉴를 생성하고 TetrisCanvas 및 TetrisNetworkCanvas 인스턴스를 전달
		createMenu(tetrisCanvas, netCanvas);

		// TetrisPreview 및 TetrisNetworkPreview 인스턴스 생성
		TetrisPreview preview = new TetrisPreview(tetrisCanvas.getData());
		TetrisNetworkPreview netPreview = new TetrisNetworkPreview(netCanvas.getData());

		// TetrisCanvas 및 TetrisNetworkCanvas에 TetrisPreview 및 TetrisNetworkPreview 설정
		tetrisCanvas.setTetrisPreview(preview);
		netCanvas.setTetrisNetworkPreview(netPreview);

		// 컴포넌트를 프레임에 추가
		add(tetrisCanvas);
		add(preview);
		add(netCanvas);
		add(netPreview);

		// 게임 모드 설정
		tetrisCanvas.data.mode = 1;
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false); // 크기 조절 불가능으로 설정
		setVisible(true);
	}

	// 게임 및 네트워크 메뉴를 생성
	public void createMenu(TetrisCanvas tetrisCanvas, TetrisNetworkCanvas netCanvas) {
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);

		// 게임 메뉴 생성
		JMenu gameMenu = new JMenu("게임");
		mb.add(gameMenu);

		// 게임 메뉴 아이템 생성
		JMenuItem startItem = new JMenuItem("시작");
		JMenuItem parseItem = new JMenuItem("중지 / 재시작");
		JMenuItem exitItem = new JMenuItem("종료");
		parseItem.setVisible(false);

		// 시작 아이템에 대한 액션 설정
		startItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.start();
				netCanvas.start();
				startItem.setVisible(false);
				parseItem.setVisible(true);
			}
		});

		// 중지 아이템에 대한 액션 설정
		parseItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tetrisCanvas.stop != true) {
					tetrisCanvas.stop();
				}
				else {
					tetrisCanvas.start();
				}
				startItem.setVisible(true);
				parseItem.setVisible(false);
			}
		});
		
		// 모드 변경에 대한 액션 설정
		changeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("모드 변경 확인");
			    frame.setSize(300, 120);
			    frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

			    JLabel messageLabel = new JLabel("모드를 변경하시겠습니까?");
			    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			    frame.getContentPane().add(messageLabel);

			    JPanel buttonPanel = new JPanel();
			    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // 중앙 정렬

			    JButton okButton = new JButton("OK");
			    okButton.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            try {
			            	tetrisCanvas.stop();
			            	tetrisCanvas.bgmsound.stop();
			            }
			            catch(Exception e1) {
			            	
			            }
			        	dispose();
			            frame.dispose();
			            new MainScreen();
			        }
			    });
			    buttonPanel.add(okButton);

			    JButton noButton = new JButton("NO");
			    noButton.addActionListener(new ActionListener() {
			        @Override
			        public void actionPerformed(ActionEvent e) {
			            // 아무 동작 없이 창 닫기
			            frame.dispose();
			        }
			    });
			    buttonPanel.add(noButton);

			    frame.getContentPane().add(buttonPanel);
			    frame.setLocationRelativeTo(null); // 창을 중앙에 위치
			    frame.setVisible(true);
			}
		});

		// 종료 아이템에 대한 액션 설정
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog(null, "게임을 종료하시겠습니까?", "종료 확인",
						JOptionPane.YES_NO_OPTION);
				if (dialogResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		// 게임 메뉴에 아이템 추가
		gameMenu.add(startItem);
		gameMenu.add(parseItem);
		gameMenu.add(changeItem);
		gameMenu.add(exitItem);

		// 네트워크 메뉴 생성
		JMenu networkMenu = new JMenu("네트워크");
		mb.add(networkMenu);

		// 서버 생성 아이템 생성
		JMenuItem serverItem = new JMenuItem("서버 생성...");
		networkMenu.add(serverItem);

		// 서버 접속 아이템 생성
		JMenuItem clientItem = new JMenuItem("서버 접속...");
		networkMenu.add(clientItem);

		// 서버 생성 아이템에 대한 액션 설정
		serverItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerDialog dialog = new ServerDialog();
				dialog.setLocationRelativeTo(null); 
				dialog.setVisible(true);
				if (dialog.userChoice == ServerDialog.Choice.OK) {
					tetrisCanvas.data.ismulti(true);
					TetrisServer tetrisserver = new TetrisServer(dialog.getPortNumber());
					tetrisserver.start();
					serverItem.setEnabled(false);
					changeItem.setEnabled(false);
				}
			}
		});

		// 서버 접속 아이템에 대한 액션 설정
		clientItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientDialog dialog = new ClientDialog();
				dialog.setLocationRelativeTo(null); 
				dialog.setVisible(true);
				if (dialog.userChoice == ClientDialog.Choice.OK) {
					tetrisCanvas.data.ismulti(true);
					originalclient = new TetrisClient(tetrisCanvas, netCanvas, dialog.getHost(),
							dialog.getPortNumber());
					originalclient.start();
					clientItem.setEnabled(false);
					changeItem.setEnabled(false);
				}

			}
		});

		// 소리 메뉴 생성
		JMenu SoundMenu = new JMenu("소리");
		mb.add(SoundMenu);

		// 소리 설정 아이템 생성
		JMenuItem soundItem = new JMenuItem("배경음악 설정");
		SoundMenu.add(soundItem);

		// 소리 설정 아이템에 대한 액션 설정
		soundItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SoundControl();
			}
		});

		// 랭크 리더보드 메뉴 생성
		JMenu RankMenu = new JMenu("랭킹");
		mb.add(RankMenu);

		// 소리 설정 아이템 생성
		JMenuItem RankItem = new JMenuItem("랭킹 보기");
		RankMenu.add(RankItem);

		// 소리 설정 아이템에 대한 액션 설정
		RankItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ranking_Leaderboard leaderboard = new Ranking_Leaderboard();
				leaderboard.setLocationRelativeTo(null);
				leaderboard.setVisible(true);
			}
		});

		// 도움말 메뉴 생성
		JMenu HelpMenu = new JMenu("도움말");
		mb.add(HelpMenu);

		// 게임 정보 아이템 생성
		JMenuItem infoItem = new JMenuItem("테트리스 정보");
		HelpMenu.add(infoItem);

		// 게임 정보 아이템에 대한 액션 설정
		infoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpWindow helpwindow = new HelpWindow();
				helpwindow.setVisible(true);
			}
		});

		// 조작키 아이템 생성
		JMenuItem controlItem = new JMenuItem("조작키");
		HelpMenu.add(controlItem);

		// 조작키 아이템에 대한 액션 설정
		controlItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ControlWindow controlwindow = new ControlWindow(tetrisCanvas.data);
				controlwindow.setVisible(true);
			}
		});
		
		// 테트리스 역사 아이템 생성
		JMenuItem historyItem = new JMenuItem("테트리스 역사");
		HelpMenu.add(historyItem);

		// 게임 정보 아이템에 대한 액션 설정
		historyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HistoryWindow historywindow = new HistoryWindow();
				historywindow.setVisible(true);
			}
		});
		
	}

	// 소리 설정 다이얼로그 생성 및 관리
	public void SoundControl() {
		frame = new JFrame("배경음악 설정");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 200);

		// 볼륨 슬라이더 생성
		volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		volumeSlider.setMajorTickSpacing(10);
		volumeSlider.setPaintTicks(true);

		// 소리 선택 콤보 상자 생성
		soundComboBox = new JComboBox<>();
		sounds = new String[3];
		sounds[0] = "sound/TetrisBGM1.wav";
		sounds[1] = "sound/TetrisBGM2.wav";
		sounds[2] = "sound/TetrisBGM3.wav";

		selectedSound = new Sound(sounds[0], volume);

		for (int i = 0; i < sounds.length; i++) {
			soundComboBox.addItem(sounds[i]);
		}

		// 볼륨 슬라이더의 변경 이벤트 처리
		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				volume = volumeSlider.getValue() / -2.5f;
				if (selectedSound != null) {
					selectedSound.setVolume(volume);
				}
			}
		});

		// 재생 버튼 생성
		JButton playButton = new JButton("재생");

		// 재생 버튼 클릭 이벤트 처리
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedSound != null) {
					selectedSound.setFramePosition(0);
					selectedSound.play();
				}
			}
		});

		// 소리 선택 콤보 상자 이벤트 처리
		soundComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedSound.stop();
				selectedSound.close();
				selectedSound = new Sound(sounds[soundComboBox.getSelectedIndex()], volume);
			}
		});

		// 노래 설정 버튼 생성
		JButton setButton = new JButton("배경음악 설정");

		// 노래 설정 버튼 클릭 이벤트 처리
		setButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tetrisCanvas.bgmPath = selectedSound.MusicPath;
				tetrisCanvas.volume = selectedSound.volume;
				JOptionPane.showMessageDialog(frame, "Bgm이 설정되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		// 노래 찾기 버튼 생성
		JButton findButton = new JButton("배경음악 불러오기");
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("WAV 파일", "wav"));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String filePath = selectedFile.getPath();
					sounds = Arrays.copyOf(sounds, sounds.length + 1);
					sounds[sounds.length - 1] = filePath;
					soundComboBox.addItem(selectedFile.getName()); // 파일 이름 추가
					soundComboBox.setSelectedItem(selectedFile.getName()); // 현재 선택된 아이템 설정
				}
			}
		});
		
		JLabel messageLabel = new JLabel("\n이미 게임중이라면 다음 게임부터 배경 음악이 적용됩니다.");
	    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// 컨트롤 패널 생성
		JPanel controlPanel = new JPanel();
		controlPanel.add(volumeSlider);
		controlPanel.add(soundComboBox);
		controlPanel.add(playButton);
		controlPanel.add(setButton);
		controlPanel.add(findButton);
		controlPanel.add(messageLabel);
		
		frame.add(controlPanel, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				// 창이 닫힐 때 실행하고자 하는 작업을 여기에 추가
				selectedSound.stop();
			}
		});
		
		
		frame.setLocationRelativeTo(null); // 창을 중앙에 위치
		frame.setVisible(true);
	}



	// 네트워크 갱신을 위한 메서드
	public void originalrefresh() {
		if (originalclient != null)
			originalclient.send();
	}

	public static void main(String[] args) {
		// MyTetris 클래스의 인스턴스 생성하여 프로그램 시작
		new MyTetris();
	}
}
