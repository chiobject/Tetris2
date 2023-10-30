package Main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import Sound.Sound;

public class SoundControl {
    private JFrame frame;
    private JSlider volumeSlider;
    private JComboBox<String> soundComboBox;
    private Sound[] sounds;
    private Sound selectedSound;

    public SoundControl() {
        frame = new JFrame("소리 설정");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);

        soundComboBox = new JComboBox<>();
        String[] sounds = new String[3];
        sounds[0] = "sound/TetrisBGM.wav";
        sounds[1] = "sound/TetrisBGM.wav";
        sounds[2] = "소리3.wav";
        selectedSound = new Sound(sounds[0], -20.0f);
        
        for (int i = 0; i < sounds.length; i++) {
        	soundComboBox.addItem(sounds[i]);
        }

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                float volume = volumeSlider.getValue() / 100.0f;
                if (selectedSound != null) {
                    selectedSound.setVolume(volume);
                }
            }
        });

        JButton playButton = new JButton("재생");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedSound != null) {
                    selectedSound.play();
                    System.out.println(selectedSound.MusicPath);
                }
            }
        });
        
        soundComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSound.stop();
                selectedSound.close();
                selectedSound = new Sound(sounds[soundComboBox.getSelectedIndex()],-20.0f);
            }
        });

        JButton setButton = new JButton("원하는 파일 설정");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV 파일", "wav"));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        sounds[soundComboBox.getSelectedIndex()] = selectedFile.getPath();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JPanel controlPanel = new JPanel();
        controlPanel.add(volumeSlider);
        controlPanel.add(soundComboBox);
        controlPanel.add(playButton);
        controlPanel.add(setButton);

        frame.add(controlPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SoundControl();
            }
        });
    }
}

