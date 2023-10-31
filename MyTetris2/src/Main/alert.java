package Main;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class alert {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		alert();
	}

	/**
	 * Create the application.
	 */
	/**
	 * Initialize the contents of the frame.
	 */
	
	public static void alert() {
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
	            frame.dispose();
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

}
