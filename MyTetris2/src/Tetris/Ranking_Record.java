package Tetris;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Main.MyTetris;

import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class Ranking_Record extends JFrame {
	static final String FILE_PATH = "C:\\Users\\admin\\git\\Tetris2\\MyTetris2\\src\\Ranking.txt";
	private JPanel contentPane;
	private JTextField textField;
	private TetrisData data;

	/**
	 * Create the frame.
	 */
	public Ranking_Record(TetrisData data) {
		this.data = data;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 298, 189);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setToolTipText("Score save");
		contentPane.add(panel, "name_624620029655900");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 69, 56, 30, 106, 44, 56, 62, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 40, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("게임 끝");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 20));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("점수 : " + data.score);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField = new JTextField();
		textField.setToolTipText("Name");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 3;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		// 기록 저장하는 버튼
		JButton btnNewButton = new JButton("기록 저장");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				if (name.isEmpty()) {
					name = "Nickname"; // 만약 이름이 입력되지 않은 경우 기본값으로 "Nickname" 설정
				}
				saveRanking(name, data.score); // 이름과 점수를 저장하는 메소드 호출
				JOptionPane.showMessageDialog(Ranking_Record.this, "랭킹이 저장되었습니다.");
				dispose(); // 다이얼로그 창 닫기
			}
		});

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.VERTICAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 4;
		panel.add(btnNewButton, gbc_btnNewButton);

	}

	// 기존의 saveRanking 함수
	protected void saveRanking(String name, int score) {
		List<String> rankingDataList = loadRanking(); // 랭킹 데이터 로드
		addRanking(rankingDataList, name, score); // 새로운 랭킹 데이터 추가
		sortRanking(rankingDataList); // 내림차순으로 정렬
		saveSortedRanking(rankingDataList); // 정렬된 랭킹 데이터 저장
		removeExcessRanking(rankingDataList); // 초과 데이터 제거
	}

	// 랭킹 데이터 로드
	protected List<String> loadRanking() {
		List<String> rankingDataList = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			while ((line = reader.readLine()) != null) {
				rankingDataList.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rankingDataList;
	}

	// 새로운 랭킹 데이터 추가
	protected void addRanking(List<String> rankingDataList, String name, int score) {
		String rankingData = name + "," + score;
		rankingDataList.add(rankingData);
	}

	// 내림차순으로 정렬
	protected void sortRanking(List<String> rankingDataList) {
		rankingDataList.sort((s1, s2) -> {
			int score1 = Integer.parseInt(s1.split(",")[1]);
			int score2 = Integer.parseInt(s2.split(",")[1]);
			return Integer.compare(score2, score1);
		});
	}

	// 정렬된 랭킹 데이터 저장
	protected void saveSortedRanking(List<String> rankingDataList) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
			for (String data : rankingDataList) {
				writer.write(data);
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 랭킹 인원 초과 데이터 제거
	protected void removeExcessRanking(List<String> rankingDataList) {
		int maxEntries = 10; // 최대 저장 개수

		if (rankingDataList.size() > maxEntries) {
			rankingDataList.subList(maxEntries, rankingDataList.size()).clear();
			saveSortedRanking(rankingDataList); // 수정된 랭킹 데이터 저장
		}
	}

	protected String outputRanking() {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
			String line;
			String indentation = "    "; // 들여쓰기 공백 설정 (4칸 들여쓰기)
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(","); // 쉼표로 문자열 분할
				if (parts.length == 2) {
					String username = parts[0].trim(); // 유저명
					String number = parts[1].trim(); // 숫자
					sb.append(indentation).append(username).append(": ").append(number).append("\n");
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
