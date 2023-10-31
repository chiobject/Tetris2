package Tetris;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Ranking_Leaderboard extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public Ranking_Leaderboard() {
		setBounds(100, 100, 300, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.WEST);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 28, 51, 77, 79, 29, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 10, -6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel_User = new JLabel();
			JLabel lblNewLabel_Score = new JLabel("New label");
			{
				JLabel lblNewLabel_title = new JLabel("순위표");
				lblNewLabel_title.setFont(new Font("한컴 고딕", Font.BOLD, 30));
				GridBagConstraints gbc_lblNewLabel_title = new GridBagConstraints();
				gbc_lblNewLabel_title.gridwidth = 3;
				gbc_lblNewLabel_title.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_title.gridx = 1;
				gbc_lblNewLabel_title.gridy = 1;
				contentPanel.add(lblNewLabel_title, gbc_lblNewLabel_title);
			}
			{
				lblNewLabel_Score.setVerticalAlignment(SwingConstants.TOP);
				try {
					String filePath = Ranking_Record.FILE_PATH;
					String data = Ranking_Score(filePath);
					lblNewLabel_Score.setText("<html>" + data + "</html>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				GridBagConstraints gbc_lblNewLabel_Score = new GridBagConstraints();
				gbc_lblNewLabel_Score.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel_Score.gridheight = 1;
				gbc_lblNewLabel_Score.gridx = 3;
				gbc_lblNewLabel_Score.gridy = 2;
				gbc_lblNewLabel_Score.anchor = GridBagConstraints.NORTH;
				contentPanel.add(lblNewLabel_Score, gbc_lblNewLabel_Score);
			}
			{
				JLabel lblNewLabel_Ranking = new JLabel();
				lblNewLabel_Ranking.setVerticalAlignment(SwingConstants.TOP);
				lblNewLabel_Ranking.setText("<html>" + Ranking_Ranking());
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.gridheight = 1;
				gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 2;
				contentPanel.add(lblNewLabel_Ranking, gbc_lblNewLabel);
			}
			{
				lblNewLabel_User.setVerticalAlignment(SwingConstants.TOP);
				try {
					String filePath = Ranking_Record.FILE_PATH;
					String data = Ranking_User(filePath);
					lblNewLabel_User.setText("<html>" + data + "</html>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				GridBagConstraints gbc_lblNewLabel_User = new GridBagConstraints();
				gbc_lblNewLabel_User.gridheight = 1;
				gbc_lblNewLabel_User.insets = new Insets(0, 0, 0, 5);
				gbc_lblNewLabel_User.gridx = 2;
				gbc_lblNewLabel_User.gridy = 2;
				gbc_lblNewLabel_User.anchor = GridBagConstraints.NORTH;
				contentPanel.add(lblNewLabel_User, gbc_lblNewLabel_User);
			}
			{
				JPanel buttonPane = new JPanel();
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
				getContentPane().add(buttonPane, BorderLayout.SOUTH);
				{
					JButton okButton = new JButton("OK");
					okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);

					JButton resetButton = new JButton("Reset");
					resetButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int dialogResult = JOptionPane.showConfirmDialog(null, "정말로 초기화 하시겠습니까?", "초기화 확인",
									JOptionPane.YES_NO_OPTION);
							if (dialogResult == JOptionPane.YES_OPTION) {
								// 메모장 파일을 삭제하거나 초기화합니다.
								try {
									String filePath = Ranking_Record.FILE_PATH;
									FileWriter fileWriter = new FileWriter(filePath);
									PrintWriter printWriter = new PrintWriter(fileWriter);
									printWriter.close();
									String data = Ranking_Score(filePath);
									lblNewLabel_User.setText("<html>" + data + "</html>");
									lblNewLabel_Score.setText("<html>" + data + "</html>");
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}
						}
					});
					buttonPane.add(resetButton);

				}

			}

		}
	}

	// 점수 불러오기
	private static String Ranking_Score(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(","); // 쉼표로 문자열 분할
			if (parts.length == 2) {
				String number = parts[1].trim(); // 숫자
				sb.append(number).append("<br><br>");
			}
		}
		reader.close();
		return sb.toString();
	}

	// 유저 불러오기
	private static String Ranking_User(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(","); // 쉼표로 문자열 분할
			if (parts.length == 2) {
				String username = parts[0].trim(); // 유저명
				sb.append(username).append("<br><br>");
			}
		}
		reader.close();
		return sb.toString();
	}

	// 등수
	private static String Ranking_Ranking() {
		StringBuilder ranking = new StringBuilder();
		for (int i = 1; i < 11; i++) {
			ranking.append(i).append("등").append("<br><br>");
		}

		return ranking.toString();
	}
}
