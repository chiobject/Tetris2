package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainScreen extends JFrame {
	/**
	 * 직렬화 버전 UID
	 */
	private static final long serialVersionUID = 1L;

	// 시작 아이템을 나타내는 JMenuItem 변수
	JMenuItem startItem;

	public MainScreen() {
		// JFrame을 생성하여 '모드 선택'이라는 제목을 가진 창을 만듭니다.
		JFrame frame = new JFrame("모드 선택");
		frame.setTitle("나만의 테트리스 1.0");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창을 닫을 때 프로그램 종료

		// 모드 선택을 위한 JPanel을 생성
		JPanel panel = new JPanel();

		// '노말' 버튼 생성
		JButton button1 = new JButton("노말");

		// '반전' 버튼 생성
		JButton button2 = new JButton("반전");

		// MyTetris 클래스의 인스턴스 생성
		MyTetris mytetris = new MyTetris();

		// '노말' 버튼에 대한 액션(동작)을 추가
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// '노말' 버튼이 클릭되었을 때 실행할 코드 작성
				// MyTetris 클래스의 NormalTetris 메서드를 호출하고 창을 닫습니다.
				mytetris.NormalTetris();
				frame.dispose();
			}
		});

		// '반전' 버튼에 대한 액션을 추가
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// '반전' 버튼이 클릭되었을 때 실행할 코드 작성
				// MyTetris 클래스의 ReverseTetris 메서드를 호출하고 창을 닫습니다.
				mytetris.ReverseTetris();
				frame.dispose();
			}
		});

		// '노말'과 '반전' 버튼을 JPanel에 추가
		panel.add(button1);
		panel.add(button2);

		// JPanel을 JFrame에 추가하고 창의 크기와 위치를 설정한 후, 화면에 표시
		frame.add(panel);
		frame.setSize(300, 100);
		frame.setLocationRelativeTo(null); // 화면 중앙에 위치
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// MainScreen 클래스의 인스턴스를 생성하여 프로그램 실행
		new MainScreen();
	}
}
