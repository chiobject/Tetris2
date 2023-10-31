package Tetris;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

public class TetrisClient extends Thread {
    private String host; // 호스트(서버 주소)를 저장할 변수
    private int port; // 포트(통신용 역할)를 저장할 변수
    private BufferedReader input; // 입력 스트림
    private PrintWriter output; // 출력 스트림
    private TetrisNetworkCanvas netCanvas; // 네트워크 관련 캔버스
    private TetrisCanvas tetrisCanvas; // 테트리스 게임 화면
    private String key, current, preview, hold, score; // 데이터 관련 변수들

    /**
     * TetrisClient 클래스의 생성자입니다.
     *
     * @param tetrisCanvas TetrisClient와 관련된 TetrisCanvas 인스턴스
     * @param netCanvas TetrisNetworkCanvas 인스턴스 (네트워크 통신용)
     * @param host 서버의 호스트명 또는 IP 주소
     * @param port 서버가 수신 대기하는 포트 번호
     */
    public TetrisClient(TetrisCanvas tetrisCanvas, TetrisNetworkCanvas netCanvas, String host, int port) {
        this.tetrisCanvas = tetrisCanvas;
        this.netCanvas = netCanvas;
        this.host = host;
        this.port = port;

        UUID uuid = UUID.randomUUID();
        key = uuid.toString() + ";";
        System.out.println("내 키: " + key);
    }

    /**
     * 서버로 게임 데이터를 전송합니다.
     */
    public void send() {
        String data = tetrisCanvas.getData().saveNetworkData();
        if (tetrisCanvas.current != null) {
            current = tetrisCanvas.current.saveNetworkPiece();
        }
        if (tetrisCanvas.preview.current != null) {
            preview = tetrisCanvas.preview.current.saveNetworkPiece();
        }
        if (tetrisCanvas.preview.hold != null) {
            hold = tetrisCanvas.preview.hold.saveNetworkPiece();
        }
        score = String.valueOf(tetrisCanvas.data.score);
        output.println(key + data + ";" + current + ";" + preview + ";" + hold + ";" + score);
    }

    /**
     * 클라이언트 스레드의 주 실행 메서드입니다.
     */
    public void run() {
        System.out.println("클라이언트 시작!");
        Socket socket;
        try {
            socket = new Socket(host, port);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            input = new BufferedReader(new InputStreamReader(inputStream));
            output = new PrintWriter(new OutputStreamWriter(outputStream), true);

            while (true) {
                String line = input.readLine();
                if (line.length() != 0) {
                    String[] parsedData = line.split(";");
                    String checkKey = parsedData[0] + ";";
                    if (!checkKey.equals(key) && parsedData.length > 1) {
                        netCanvas.getData().loadNetworkData(parsedData[1]);

                        Piece currentPiece = new Bar(new TetrisData());
                        currentPiece.loadNetworkPiece(parsedData[2]);
                        netCanvas.setCurrent(currentPiece);

                        try {
                            Piece previewPiece = new Bar(new TetrisData());
                            previewPiece.loadNetworkPiece(parsedData[3]);
                            netCanvas.preview.current = previewPiece;

                            Piece holdPiece = new Bar(new TetrisData());
                            holdPiece.loadNetworkPiece(parsedData[4]);
                            netCanvas.preview.hold = holdPiece;

                            System.out.println("상대방 점수: " + parsedData[5]);
                            tetrisCanvas.preview.enemy_score = Integer.parseInt(parsedData[5]);
                        } catch (Exception e) {
                            System.out.println("클라이언트 문제: " + e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
