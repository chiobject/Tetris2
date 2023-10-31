package Tetris;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlWindow extends JFrame {
	TetrisData tetrisdata;

	public ControlWindow(TetrisData tetrisdata) {
		this.tetrisdata = tetrisdata;
		setTitle("조작키 안내"); // 창 제목을 "조작키 안내"로 설정
		setSize(400, 300); // 창 크기를 400x300 픽셀로 설정
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 작업 정의

		// 조작 안내를 표시할 JTextArea 생성
		JTextArea controlsText = new JTextArea();
		controlsText.setEditable(false);
		controlsText.setLineWrap(true); // 자동 줄 바꿈 활성화
		controlsText.setWrapStyleWord(true);

		Font textFont = new Font("굴림", Font.PLAIN, 16); // 텍스트에 사용할 글꼴 정의

		if (tetrisdata.mode == 0) {
			controlsText.setFont(textFont);
			controlsText.append("<노말 테트리스>\n\n"); // 게임 모드 표시: 노말 테트리스
			controlsText.append(" ← : 블록을 왼쪽으로 이동\n"); // 블록을 왼쪽으로 이동하는 방법 설명
			controlsText.append(" → : 블록을 오른쪽으로 이동\n"); // 블록을 오른쪽으로 이동하는 방법 설명
			controlsText.append(" ↓ : 블록을 아래로 이동\n"); // 블록을 아래로 이동하는 방법 설명
			controlsText.append(" ↑ : 블록을 회전\n"); // 블록을 회전하는 방법 설명
			controlsText.append(" space : 블록을 바로 아래로 내림\n"); // 블록을 빠르게 아래로 내리는 방법 설명
			controlsText.append(" c 키 : 블록 홀드"); // 블록을 홀드하는 방법 설명

		} else if (tetrisdata.mode == 1) {
			controlsText.setFont(textFont);
			controlsText.append("<리버스 테트리스>\n\n"); // 게임 모드 표시: 리버스 테트리스
			controlsText.append(" ← : 블록을 왼쪽으로 이동\n"); // 블록을 왼쪽으로 이동하는 방법 설명
			controlsText.append(" → : 블록을 오른쪽으로 이동\n"); // 블록을 오른쪽으로 이동하는 방법 설명
			controlsText.append(" ↓ : 블록을 회전\n"); // 블록을 회전하는 방법 설명
			controlsText.append(" ↑ : 블록을 위로 이동\n"); // 블록을 위로 이동하는 방법 설명
			controlsText.append(" space : 블록을 바로 위로 올림\n"); // 블록을 빠르게 위로 올리는 방법 설명
			controlsText.append(" c 키 : 블록 홀드"); // 블록을 홀드하는 방법 설명
		}

		// 사용자가 안내를 스크롤하도록 허용하는 JScrollPane 생성
		JScrollPane scrollPane = new JScrollPane(controlsText);
		add(scrollPane, BorderLayout.CENTER);

		// "닫기" 버튼을 생성하고 클릭하면 창을 닫는 기능을 수행
		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // 창 닫기
			}
		});
		add(closeButton, BorderLayout.SOUTH); // 버튼을 창 아래쪽에 추가

		setLocationRelativeTo(null); // 창을 화면 가운데에 배치
	}
}
