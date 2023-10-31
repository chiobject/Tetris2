package Tetris;


import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpWindow extends JFrame {

	public HelpWindow() {
		setTitle("테트리스 설명"); // 창 제목 설정
		setSize(450, 300); // 창 크기 설정
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 기본 동작 설정

		JTextPane helpText = new JTextPane();
		helpText.setEditable(false); // 편집 비활성화

		StyledDocument doc = helpText.getStyledDocument(); // StyledDocument 가져오기
		addStylesToDocument(doc);

		// "나만의 테트리스" 텍스트 설정
		Style titleStyle = doc.getStyle("TitleStyle");
		try {
			doc.insertString(doc.getLength(), "테트리스 정보\n\n", titleStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// 나머지 텍스트 설정
		Style textStyle = doc.getStyle("TextStyle");
		try {
			doc.insertString(doc.getLength(),
					"<제작 목적>\n"
							+ "나만의 테트리스는 개발자가 직접 자바 테트리스 게임을 만들어 보고, 테트리스 게임을 확장함으로써 문제 해결 능력을 향상 시키기 위해 제작되었습니다.\n\n"
							+ "<테트리스 관련 내용>\n" + "테트리스는 퍼즐 게임으로 소련의 프로그래머 알렉세이 파지트노프가 처음 디자인하고 프로그래밍한 게임입니다. \n\n"
							+ "테트리스는 1985년에 처음 만들어졌으며, 그는 이 게임의 이름을 그리스 숫자 접두어인 Tetra와 파지트노프가 좋아하던 테니스를 합쳐서 만들었습니다.\n\n"
							+ "테트리스는 거의 대부분의 비디오 게임기와 컴퓨터 운영 체제에서 가동되며, 그래핑 계산기, 휴대 전화, 네트워크 음악 재생기와 "
							+ "심지어 오실로스코프 같은 기기의 이스터 에그에서도 발견됩니다. 또한 테트리스는 여러 빌딩의 겉면에서도 실행된 적이 있습니다.\n\n"
							+ "'테트로미노'는 위 게임에서 블록(piece)를 뜻하는데, 무작위로 나타나 바닥과 블록 위에 떨어집니다.\n\n"
							+ "이 오락의 목표는 이 '테트로미노'를 움직이고 90도씩 회전하여, 수평선을 빈틈 없이 채우는 것입니다.\n\n"
							+ "이러한 수평선이 만들어질 때 이 선은 없어지며 그 위의 블록이 아래로 떨어지는데 테트리스 게임이 진행될수록 '테트로미노'는 더 빨리 떨어지며 "
							+ "게임을 즐기는 사람이 블록을 꼭대기까지 가득 메워, '테트로미노'가 더 들어갈 공간이 없게 되면 게임이 끝나게 됩니다.",
					textStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(helpText);
		add(scrollPane, BorderLayout.CENTER);

		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // 창 닫기
			}
		});
		add(closeButton, BorderLayout.SOUTH);
		helpText.setSelectionStart(0);
		helpText.setSelectionEnd(0);
		setLocationRelativeTo(null); // 창을 화면 중앙에 표시
	}

	private void addStylesToDocument(StyledDocument doc) {
		Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		// 제목 스타일
		Style titleStyle = doc.addStyle("TitleStyle", def);
		StyleConstants.setFontSize(titleStyle, 30);
		StyleConstants.setFontFamily(titleStyle, "굴림");
		StyleConstants.setAlignment(titleStyle, StyleConstants.ALIGN_CENTER);

		// 본문 스타일
		Style textStyle = doc.addStyle("TextStyle", def);
		StyleConstants.setFontSize(textStyle, 16);
		StyleConstants.setFontFamily(textStyle, "굴림");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HelpWindow helpWindow = new HelpWindow();
				helpWindow.setVisible(true);
			}
		});
	}
}
