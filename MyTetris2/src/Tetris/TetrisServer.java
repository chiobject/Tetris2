package Tetris;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class TetrisServer extends Thread {
    private Vector<ServerHandler> handlers;
    private int port;

    // 생성자: 포트를 인수로 받아와서 저장
    public TetrisServer(int port) {
        this.port = port;
    }

    // 서버 스레드 실행
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port); // 지정된 포트로 서버 소켓 생성
            handlers = new Vector<ServerHandler>(); // 클라이언트 핸들러를 저장하는 벡터 생성
            System.out.println("Server is ready."); // 서버가 준비되었다는 메시지 출력
            while (true) {
                Socket client = server.accept(); // 클라이언트의 연결을 대기하고, 연결되면 클라이언트 소켓을 생성
                System.out.println("Connected from " + client.getInetAddress() + ":" + client.getPort()); // 클라이언트의 IP 주소와 포트 출력
                ServerHandler c = new ServerHandler(this, client); // 클라이언트 핸들러 생성
                c.start(); // 클라이언트 핸들러를 실행
            }
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // 클라이언트 핸들러 등록
    public void register(ServerHandler c) {
        handlers.addElement(c); // 클라이언트 핸들러를 벡터에 등록
    }

    // 클라이언트 핸들러 제거
    public void unregister(Object o) {
        handlers.removeElement(o); // 클라이언트 핸들러를 벡터에서 제거
    }

    // 모든 클라이언트에게 메시지 브로드캐스트
    public void broadcast(String message) {
        synchronized (handlers) {
            int n = handlers.size();
            for (int i = 0; i < n; i++) {
                ServerHandler c = handlers.elementAt(i);
                try {
                    c.println(message); // 모든 클라이언트에게 메시지를 전송
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
