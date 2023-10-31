package Tetris;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerDialog extends JDialog {
	// 포트 번호 입력을 위한 "Port:" 라벨
	private JLabel label = new JLabel("Port:");

	// 포트 번호를 입력하는 텍스트 필드
	private JTextField text = new JTextField(10);

	// 확인 버튼
	private JButton okButton = new JButton("Ok");

	// 취소 버튼
	private JButton cancelButton = new JButton("Cancel");

	// 사용자가 입력한 포트 번호를 저장하는 변수
	private int port = 0;

	// 사용자의 대화 상자 선택을 나타내는 열거형
	public enum Choice {
		NONE, OK, CANCEL
	}

	// 사용자의 선택을 기본적으로 NONE으로 초기화
	public Choice userChoice = Choice.NONE;

	public ServerDialog() {
		// 모달 대화 상자로 설정
		setModal(true);

		// 대화 상자 제목 설정
		setTitle("서버 생성");

		// FlowLayout을 사용하여 컴포넌트 배치
		setLayout(new FlowLayout());

		// "Port:" 라벨을 추가
		add(label);

		// 텍스트 필드를 추가하여 포트 번호 입력을 받음
		add(text);

		// "Ok" 버튼을 추가하여 확인 작업을 수행
		add(okButton);

		// "Cancel" 버튼을 추가하여 취소 작업을 수행
		add(cancelButton);

		// 컴포넌트 크기를 자동으로 조절하여 최적의 크기로 설정
		pack();

		// 텍스트 필드의 초기 값을 "5000"으로 설정
		text.setText("5000");

		// "Ok" 버튼 클릭 시 이벤트 처리
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 사용자가 "Ok" 버튼을 눌렀을 때 선택을 OK로 설정
				userChoice = Choice.OK;

				// 입력된 포트 번호를 가져와서 변수에 저장
				port = Integer.parseInt(text.getText());

				// 대화 상자 닫기
				dispose();
			}
		});

		// "Cancel" 버튼 클릭 시 이벤트 처리
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 사용자가 "Cancel" 버튼을 눌렀을 때 선택을 CANCEL로 설정
				userChoice = Choice.CANCEL;

				// 대화 상자 닫기
				dispose();
			}
		});
	}

	// 사용자가 입력한 포트 번호를 반환
	public int getPortNumber() {
		return this.port;
	}
}
