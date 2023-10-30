package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

public class SoundSelect {

    public static void main(String[] args) {
        JFrame frame = new JFrame("파일 찾기");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 212); // 창 크기 조정

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER); // 중앙에 배치

        JButton openButton = new JButton("WAV 파일 찾기");
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                // WAV 파일 필터 추가
                FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV 파일 (*.wav)", "wav");
                fileChooser.setFileFilter(filter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (selectedFile.getName().toLowerCase().endsWith(".wav")) {
                        // 선택한 파일이 WAV 파일인 경우
                        String wavFilePath = selectedFile.getAbsolutePath();
                        System.out.println("선택한 WAV 파일 경로: " + wavFilePath);
                    } else {
                        // 선택한 파일이 WAV 파일이 아닌 경우
                        JOptionPane.showMessageDialog(null, "선택한 파일은 WAV 파일이 아닙니다. 다시 선택하세요.",
                                "오류", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        panel.add(openButton);

        frame.setVisible(true);
    }
}
