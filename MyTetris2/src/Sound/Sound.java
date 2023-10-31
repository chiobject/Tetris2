package Sound;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;  // 사운드 재생을 관리하는 Clip 객체
    public float volume = 0;  // 사운드의 볼륨 설정
    public String MusicPath;  // 사운드 파일 경로

    public Sound(String MusicPath, float volume) {
        try {
            this.volume = volume;
            this.MusicPath = MusicPath;
            File file = new File(MusicPath); // 테트리스 노래 지정
            clip = AudioSystem.getClip(); // Clip 객체 생성
            clip.open(AudioSystem.getAudioInputStream(file)); // 오디오 파일 열기

            // 볼륨을 조절할 믹서 생성
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 볼륨 조정 (음수 값: 줄이기, 양수 값: 늘리기)
            volumeControl.setValue(volume);
        } catch (Exception e) {
            System.err.println("err : " + e);
        }
        // 클립 상태 모니터링을 위한 리스너 추가
        clip.addLineListener(new LineListener() {
            @Override
            public void update(LineEvent event) {
                // 소리 재생이 끝나면 클립을 닫음 (단, 루프 중이 아닐 때만)
                if (event.getType() == LineEvent.Type.STOP && (!clip.isRunning() && !clip.isActive())) {
                    close();
                }
            }
        });
    }

    // 테트리스 노래 실행
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY); // 노래 무한 재생
    }

    public void play() {
        clip.start();  // 사운드 재생 시작
    }

    public void stop() {
        if (clip != null) {
            clip.stop();  // 사운드 재생 중지
        }
    }

    public void close() {
        if (clip != null && !clip.isRunning()) {
            clip.close();  // 클립 닫기
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (clip != null) {
            // 볼륨을 조절할 믹서 생성
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            // 볼륨 조정 (음수 값: 줄이기, 양수 값: 늘리기)
            volumeControl.setValue(volume);
        }
    }

    public void setPath(String path) {
        MusicPath = path;
    }

    public void setFramePosition(int position) {
        clip.setFramePosition(position);  // 재생 위치를 설정
    }
}
