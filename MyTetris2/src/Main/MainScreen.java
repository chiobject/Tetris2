package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainScreen extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuItem startItem;
	
	public MainScreen() {
		JFrame frame = new JFrame("모드 선택");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		JButton button1 = new JButton("노말");
		JButton button2 = new JButton("반전");
		MyTetris mytetris = new MyTetris();

		// 버튼 1에 액션 추가
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 버튼 1이 클릭되었을 때 실행할 코드 작성
				mytetris.NormalTetris();
				frame.dispose();
			}
		});

		// 버튼 2에 액션 추가
		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mytetris.ReverseTetris();
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
	
	
	public static void main(String[] args) {
		new MainScreen();
	}
}
