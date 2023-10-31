package Tetris;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    private JLabel label1 = new JLabel("Host:"); // "Host" 레이블 생성
    private JTextField text1 = new JTextField(10); // "Host" 입력 필드 생성
    private JLabel label2 = new JLabel("Port:"); // "Port" 레이블 생성
    private JTextField text2 = new JTextField(10); // "Port" 입력 필드 생성
    private JButton okButton = new JButton("Ok"); // "Ok" 버튼 생성
    private JButton cancelButton = new JButton("Cancel"); // "Cancel" 버튼 생성
    private int port = 0; // 입력된 포트 번호를 저장할 변수
    private String host; // 입력된 호스트 주소를 저장할 변수

    // 사용자의 선택을 나타내는 열거형
    public enum Choice {
        NONE, OK, CANCEL
    }
    
    // 사용자의 선택 (기본값은 NONE)
    public Choice userChoice = Choice.NONE;

    public ClientDialog() {
        setModal(true);	// 모달 다이얼로그로 설정
        setTitle("서버 접속");	// 다이얼로그 제목 설정
        setLayout(new GridLayout(3, 1));  // 3x1 그리드 레이아웃 설정

        // 호스트 입력 패널 생성 및 설정
        JPanel hostPanel = new JPanel();
        add(hostPanel);
        hostPanel.setLayout(new FlowLayout());
        hostPanel.add(label1); // "Host" 레이블 추가
        hostPanel.add(text1); // "Host" 입력 필드 추가

        // 포트 입력 패널 생성 및 설정
        JPanel portPanel = new JPanel();
        add(portPanel);
        portPanel.setLayout(new FlowLayout());
        portPanel.add(label2); // "Port" 레이블 추가
        portPanel.add(text2); // "Port" 입력 필드 추가

        // 버튼 패널 생성 및 설정
        JPanel buttonPanel = new JPanel();
        add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(okButton); // "Ok" 버튼 추가
        buttonPanel.add(cancelButton); // "Cancel" 버튼 추가

        pack(); // 다이얼로그 크기 조정

        text1.setText("127.0.0.1"); // "Host" 입력 필드의 기본 값 설정
        text2.setText("5000"); // "Port" 입력 필드의 기본 값 설정

        // "Ok" 버튼에 대한 이벤트 리스너 설정
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = Choice.OK; // 사용자가 "Ok"를 클릭한 경우
                host = text1.getText(); // 입력된 호스트 주소 저장
                port = Integer.parseInt(text2.getText()); // 입력된 포트 번호 저장
                dispose(); // 다이얼로그 닫기
            }
        });

        // "Cancel" 버튼에 대한 이벤트 리스너 설정
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userChoice = Choice.CANCEL; // 사용자가 "Cancel"을 클릭한 경우
                dispose(); // 다이얼로그 닫기
            }
        });
    }

    // 입력된 포트 번호를 반환
    public int getPortNumber() {
        return this.port;
    }

    // 입력된 호스트 주소를 반환
    public String getHost() {
        return this.host;
    }
}
