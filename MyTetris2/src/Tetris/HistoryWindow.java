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

public class HistoryWindow extends JFrame {

	public HistoryWindow() {
		setTitle("테트리스 역사"); // 창 제목 설정
		setSize(450, 300); // 창 크기 설정
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창을 닫을 때 기본 동작 설정

		JTextPane historyText = new JTextPane();
		historyText.setEditable(false); // 편집 비활성화

		StyledDocument doc = historyText.getStyledDocument(); // StyledDocument 가져오기
		addStylesToDocument(doc);

		// "나만의 테트리스" 텍스트 설정
		Style titleStyle = doc.getStyle("TitleStyle");
		try {
			doc.insertString(doc.getLength(), "테트리스 역사\n\n", titleStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		// 나머지 텍스트 설정
		Style textStyle = doc.getStyle("TextStyle");
		try {
			doc.insertString(doc.getLength(),
					"테트리스의 역사는 많은 법적 분쟁으로 점철되어 있다. 1985년 6월 알렉세이 파지노프는 그가 근무하던 소련 과학기술원에서 일렉트로니카 60으로 가동되는 테트리스를 처음 개발해 내었으며, Dmitry Pavlovsky와 Vadin Gerasimov는 이걸 IBM PC로 이식하였다. Gerasimov는 파지노프가 지은 테트리스란 이름이 테트라미노와 테트리스라고 발표하였다. 여기서 시작된 테트리스는 대중으로 퍼져나갔고, 모스크바 전역에 퍼지게 되었다.\r\n"
					+ "\r\n"
					+ "최초의 IBM PC 버전은 1986년에 개발되었는데, 헝가리 부다페스트에서 테트리스가 여러 가지 플랫폼으로 퍼져나가면서 안드로메다란 영국 소프트웨어 회사에게 발견되었다. 그들은 파지노프와 PC버전에 대한 권리를 협의하기 전에 스펙트럼 홀로바이트란 회사에 권리를 팔았으며, 파지노프로부터 권리를 사는데 실패한 안드로메다는 헝가리 프로그래머로부터 사용권을 구입하게 된다.\r\n"
					+ "\r\n"
					+ "그동안, 스펙트럼 홀로바이트가 만든 IBM PC버전의 테트리스는 1986년 미국에 유통되었다. 테트리스의 인기는 폭발적이었으며, 많은 사람들이 테트리스에 푹 빠져들게 되었으며, 컴퓨터 게이밍 월드라는 게임잡지는 테트리스를 가리켜 \"믿지 못할 만큼 간단하고 방심할 수 없을 정도로 중독적이다\"란 기사를 내었다.\r\n"
					+ "\r\n"
					+ "테트리스의 저작권문제가 불분명하였지만, 1987년 안드로메다측에선 IBM PC 판과 다른 여러 홈 컴퓨터 시장에서의 테트리스의 저작권을 구입하게 되었다. 아미가와 아타리 ST 두가지 판의 테트리스가 스펙트럼 홀로바이트와 미러 소프트에서 출시되었다. "
					+ "\n스펙트럼 홀로바이트의 테마가 러시아풍이었는 데 비하여, 미러 소프트에서 개발한 테트리스의 테마는 존재하지 않았다. 테트리스는 게임이 간단함에 따라 값이 싸게 팔렸다. "
					+ "\n스펙트럼이 애플 II판으로 출시한 세 디스켓에는 세가지 다른 판의 테트리스가 들어있었는데, 애플 II+용과 애플 IIe, 그리고 애플 DOS 3.3과 프로도스판이 그것으로, 5.25인치 디스켓에 들어갔다. "
					+ "\n그리고 나머지 한장은 애플 IIgs용으로 3.5인치 디스켓에 들어갔으며, 아무런 복제 방지장치도 없었다"
					+ "\r\n"
					+ "1988년, 소련 정부는 Eletronorgtechnica 혹은 Elorg를 통해서 테트리스의 권리를 주장하고 나섰으며, 파지노프는 10년동안 일한 컴퓨터 센터를 통하여 소련 정부에 자신의 테트리스 권리를 제공하였다. "
					+ "\n그러나 Elorg측에선 여전히 안드로메다 측으로부터 저작권료를 받지 못했으며, 그리고 심지어 안드로메다측도 서브 라이선스 권리와 라이선스를 가지지 못하였다."
					+ "\r\n\n"
					+ "또한 테트리스 컴퍼니라는 회사는 2003년 3월 한국에 상륙하여 테트리스의 저작권을 주장하여 국내 테트리스 게임이 대거 서비스가 중단되는 테트리스 대란이 일어났다. "
					+ "\n 넷마블과 한게임 테트리스등 대부분의 회사들은 소송을 감수하고 서비스를 계속할 실익이 없다고 판단하고 서비스를 중단하거나, 돈을 주고 서비스를 계속하는 길을 선택하였다.\r\n"
					+ "\r\n"
					+ "테트리스 컴퍼니 측은 테트로미노를 활용한 다른 게임에 대해 소송이나 DMCA Takedown을 걸고 있다. 2012년에는 미노(Mino)에 대해 미국 법원에서 침해 판결을 받아냈다."
					+ "\n 판결문은 가로 10줄 세로 20줄, 테트로미노의 색, 다음에 떨어트릴 조각을 표시하는 위치등이 유사하다는 이유를 들어 테트리스 컴퍼니측 테트리스와 외관이 유사하다고 했는데 여기서 제시된 \"트레이드 드레스\" 개념은 한국법에는 명문으로 인정되는 규정이 아니며, 아직 한국에는 테트리스 게임을 따와서 배포할때 저작권을 침해하는 지에 대한 판례는 없다. "
					+ "\n 오히려 게임 내에서 사용되는 아이디어는 저작권이 아니라 특허로 보호 받고 게임을 표현하는 부분만 저작권으로 보호 받는 게 보통이지만 미국 법원에서는 트레이드 드레스라는 개념을 통해 테트리스를 보호하였고, 이에 따라 레이싱 게임들이나 FPS 게임들처럼 컨셉이 비슷한 수많은 게임들 역시 소송이 붙을 위험이 있거나 표절 논란이 붙을 수 있게 되었다."
					+ "\r\n"
					+ "테트리스 컴퍼니 측은 라이선스 계약을 할때 테트리스 컴퍼니의 동의 없이는 게임 툴을 변경할 수 없게 하고 있다. "
					+ "\n앞서 언급한 것도 있고 계약할 때 계약서상에 명시해놓는 듯하다."
					+ "\r\n"
					+ "또한 국가별, 플랫폼 별로 출시할 수 있는 타이틀도 엄격히 제한되어 있다. "
					+ "\n대부분은 각 분류별 독점 계약만 가능. 테트리스가 한게임에서만 서비스되고 넷마블 테트리스와 크레이지 아케이드 테트리스가 서비스를 재개할 수 없는 것은 이 때문. 같은 이유로 2013년 현재 세가제 테트리스는 아케이드로만, 닌텐도 제 테트리스는 닌텐도제 콘솔로만 나오고 있다. 유일한 예외는 '테트리스 온라인'에서 만드는 테트리스 게임들뿐. 이 회사에만 서브 라이선싱이 허용되어 있을 정도로 라이선싱 조건이 꽤 까다롭다. 하지만 테트리스 온라인 회사에서 제작한 테트리스는 대부분 서비스 종료한 상태다.\r\n"
					+ "\r\n"
					+ "심지어 테트리스를 기반으로 제작된 팬 게임 중 Tetra Online이 스팀에 무료로 올라오자 DMCA 테이크 다운을 걸어버렸다. "
					+ "\n결국 Tetra Online 제작자인 학생[16]은 스팀에서 Tetra Online을 내리게 되었으며, 다른 팬 게임 제작자들도 혹시나 있을 수 있는 저작권 분쟁에 대해 경계하고 있다. "
					+ "\n또한 저작권을 가지고 있는 테트리스 컴퍼니가 테트리스 플레이어들과의 소통 없이 게임을 판매하고 팬 게임들을 견제하고 있기 때문에 이에 대해 불만의 목소리도 많은 편이다."
						,textStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(historyText);
		add(scrollPane, BorderLayout.CENTER);

		JButton closeButton = new JButton("닫기");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // 창 닫기
			}
		});
		add(closeButton, BorderLayout.SOUTH);
		historyText.setSelectionStart(0);
		historyText.setSelectionEnd(0);
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
				HistoryWindow historywindow = new HistoryWindow();
				historywindow.setVisible(true);
			}
		});
	}
}
