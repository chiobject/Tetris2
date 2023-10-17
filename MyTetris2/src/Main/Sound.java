package Main;

import java.io.File;
import javax.sound.sampled.*;

public class Sound {
    private Clip clip;

    // 테트리스 노래 실행
    public void loop() {
            clip.loop(Clip.LOOP_CONTINUOUSLY);  // 노래 무한 재생
    }
    
    public void play(String MusicPath, float volume) {
		try {
			File file = new File(MusicPath); // 테트리스 노래 지정
			clip = AudioSystem.getClip(); // Clip 객체 생성
			clip.open(AudioSystem.getAudioInputStream(file)); // 오디오 파일 열기
			
			 // 볼륨을 조절할 믹서 생성
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

         // 볼륨 조정 (음수 값: 줄이기, 양수 값: 늘리기)
            volumeControl.setValue(volume);
		}
    	catch (Exception e) {
    		System.err.println("err : " + e);
    	}
    }
    
    
    
    // 테트리스 소리 초기화
    public void stop() {
        if (clip != null) {  // clip이 null이 아닌 경우에만 실행
            clip.stop();  // 노래 재생 중지
            clip.close();  // clip 리소스 해제
        }
    }
}
