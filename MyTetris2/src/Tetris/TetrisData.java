package Tetris;

import Sound.Sound;

// TetrisData 클래스는 테트리스 게임 데이터를 관리합니다.
public class TetrisData {
    public static final int ROW = 20;
    public static final int COL = 10;
    private int data[][]; // ROW x COL 의 배열
    private int line; // 지운 줄 수
    public int score;
    public boolean ismulti = false;
    public int mode = 0; // 0 : 노말, 1 : 리버스
    public int curline = 0;
    // 생성자
    public TetrisData() {
        data = new int[ROW][COL];
        clear();
    }

    // 배열에서 (x, y) 위치의 값을 반환
    public int getAt(int x, int y) {
        if (x < 0 || x >= ROW || y < 0 || y >= COL)
            return 0;
        return data[x][y];
    }

    // 배열에서 (x, y) 위치에 값을 설정
    public void setAt(int x, int y, int v) {
        try {
            data[x][y] = v;
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
        }
    }

    // 현재까지 지운 줄 수 반환
    public int getLine() {
        return line;
    }

    // 줄을 제거하고 점수를 계산하는 메서드
    public synchronized void removeLines() {
        if (mode == 0) {
            NEXT: for (int i = ROW - 1; i >= 0; i--) {
                boolean done = true;
                for (int k = 0; k < COL; k++) {
                    if (data[i][k] == 0) {
                        done = false;
                        continue NEXT;
                    }
                }
                if (done) {
                    line++;
                    if (Constant.level < 9) {
                        Constant.level = ((line - Constant.level) / 10) + 1;
                    }
                    for (int x = i; x > 0; x--) {
                        for (int y = 0; y < COL; y++) {
                            data[x][y] = data[x - 1][y];
                        }
                    }
                    if (i != 0) {
                        for (int y = 0; y < COL; y++) {
                            data[0][y] = 0;
                        }
                    }
                    i++;
                }
            }
        } else if (mode == 1) {
            NEXT: for (int i = 0; i < ROW; i++) {
                boolean done = true;
                for (int k = 0; k < COL; k++) {
                    if (data[i][k] == 0) {
                        done = false;
                        continue NEXT;
                    }
                }
                if (done) {
                    line++;
                    if ((Constant.level + line) % 10 == 0) {
                        Constant.level += 1;
                    }
                    for (int x = i; x < ROW - 1; x++) {
                        for (int y = 0; y < COL; y++) {
                            data[x][y] = data[x + 1][y];
                        }
                    }
                    if (i != 0) {
                        for (int y = 0; y < COL; y++) {
                            data[ROW - 1][y] = 0;
                        }
                    }
                    i--;
                }
            }
        }
        if (curline < line) {
            Sound sound = new Sound("sound/Coin.wav",-10);
        	sound.play();
        	curline = line;
        }
    }

    // 데이터 배열을 초기화
    public void clear() {
        for (int i = 0; i < ROW; i++) {
            for (int k = 0; k < COL; k++) {
                data[i][k] = 0;
            }
        }
        Constant.level = 1;
        line = 0;
    }

    // 데이터 배열을 화면에 출력
    public void dump() {
        for (int i = 0; i < ROW; i++) {
            for (int k = 0; k < COL; k++) {
                System.out.print(data[i][k] + " ");
            }
        }
    }

    // 네트워크에서 데이터를 불러옴
    public void loadNetworkData(String input) {
        clear();
        String[] dataArray = input.split(",");
        int count = 0;
        for (int i = 0; i < TetrisData.ROW; i++) {
            for (int k = 0; k < TetrisData.COL; k++) {
                setAt(i, k, Integer.parseInt(dataArray[count]));
                count++;
            }
        }
    }

    // 네트워크로 데이터를 저장
    public String saveNetworkData() {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < TetrisData.ROW; i++) {
            for (int k = 0; k < TetrisData.COL; k++) {
                String data = String.valueOf(getAt(i, k));
                output.append(data + ",");
            }
        }
        String result = output.toString();
        return result;
    }

    // 현재 점수를 계산하고 반환
    public int getScore() {
        score = getLine() * 175 * Constant.level;
        return score;
    }

    // 멀티플레이 모드 설정
    public boolean ismulti(boolean a) {
        ismulti = a;
        return ismulti;
    }
}
