package Tetris;

import java.awt.Point;

public abstract class Piece {
    final int DOWN = 0; // 아래로 이동 방향을 나타내는 상수
    final int UP = 0;   // 위로 이동 방향을 나타내는 상수 (아마 오타로 보입니다)
    final int LEFT = 1;  // 왼쪽으로 이동 방향을 나타내는 상수
    final int RIGHT = 2; // 오른쪽으로 이동 방향을 나타내는 상수
    public int r[];       // 블록 모양을 나타내는 Y축 좌표 배열
    public int c[];       // 블록 모양을 나타내는 X축 좌표 배열
    protected TetrisData data;  // 테트리스 내부 데이터
    public Point center;        // 조각의 중심 좌표
    protected int type, roteType;

    // Piece 클래스의 생성자
    public Piece(TetrisData data, int type, int roteType) {
        r = new int[4];
        c = new int[4];
        this.data = data;
        this.type = type;
        this.roteType = roteType;
        if (data.mode == 0)
            center = new Point(5, 0); // 초기 중심 좌표 설정 (노말 테트리스)
        else if (data.mode == 1)
            center = new Point(5, 20); // 초기 중심 좌표 설정 (리버스 테트리스)
    }

    // 아래부터 Piece 클래스의 주요 메서드에 대한 설명입니다:

    // 블록 조각의 타입을 반환
    public int getType() {
        return type;
    };

    // 블록 조각의 회전 타입을 반환
    public int roteType() {
        return roteType;
    };

    // 블록 조각의 중심 좌표의 X 값 반환
    public int getX() {
        return center.x;
    }

    // 블록 조각의 중심 좌표의 Y 값 반환
    public int getY() {
        return center.y;
    }

    // 블록 조각을 복사하고 게임 종료 상황을 검사
    public boolean copy() {
        boolean value = false;
        int x = getX();
        int y = getY();
        if (data.mode == 0) {
            if (getMinY() + y < 0) { // 게임 종료 상황
                value = true;
            }
        } else if (data.mode == 1) {
            if (getMaxY() + y >= 20) { // 게임 종료 상황
                value = true;
            }
        }
        for (int i = 0; i < 4; i++) {
            data.setAt(y + r[i], x + c[i], getType());
        }
        return value;
    }

    // 다른 조각과 겹치는지 파악
    public boolean isOverlap(int dir) {
        int x = getX();
        int y = getY();
        switch (dir) {
            case 0: // 아래 or 위
                for (int i = 0; i < r.length; i++) {
                    if (data.mode == 0) {
                        if (data.getAt(y + r[i] + 1, x + c[i]) != 0) {
                            return true;
                        }
                    } else if (data.mode == 1) {
                        if (data.getAt(y + r[i] - 1, x + c[i]) != 0) {
                            return true;
                        }
                    }
                }
                break;
            case 1: // 왼쪽
                for (int i = 0; i < r.length; i++) {
                    if (data.getAt(y + r[i], x + c[i] - 1) != 0) {
                        System.out.println("왼쪽!");
                        return true;
                    }
                }
                break;
            case 2: // 오른쪽
                for (int i = 0; i < r.length; i++) {
                    if (data.getAt(y + r[i], x + c[i] + 1) != 0) {
                        System.out.println("오른쪽!");
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    // X 축으로 가장 작은 값 반환
    public int getMinX() {
        int min = c[0];
        for (int i = 1; i < c.length; i++) {
            if (c[i] < min) {
                min = c[i];
            }
        }
        return min;
    }

    // X 축으로 가장 큰 값 반환
    public int getMaxX() {
        int max = c[0];
        for (int i = 1; i < c.length; i++) {
            if (c[i] > max) {
                max = c[i];
            }
        }
        return max;
    }

    // Y 축으로 가장 작은 값 반환
    public int getMinY() {
        int min = r[0];
        for (int i = 1; i < r.length; i++) {
            if (r[i] < min) {
                min = r[i];
            }
        }
        return min;
    }

    // Y 축으로 가장 큰 값 반환
    public int getMaxY() {
        int max = r[0];
        for (int i = 1; i < r.length; i++) {
            if (r[i] > max) {
                max = r[i];
            }
        }
        return max;
    }

    // 아래로 이동
    public boolean moveDown() {
        if (data.mode == 0) {
            if (center.y + getMaxY() + 1 < TetrisData.ROW) {
                if (isOverlap(DOWN) != true) {
                    center.y++;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        if (data.mode == 1) {
            if (center.y + getMinY() > 0) {
                if (isOverlap(UP) != true) {
                    center.y--;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    // 왼쪽으로 이동
    public void moveLeft() {
        if (center.x + getMinX() - 1 >= 0)
            if (isOverlap(LEFT) != true) {
                center.x--;
            } else
                return;
    }

    // 오른쪽으로 이동
    public void moveRight() {
        if (center.x + getMaxX() + 1 < TetrisData.COL)
            if (isOverlap(RIGHT) != true) {
                center.x++;
            } else
                return;
    }

    // 조각 회전
    public void rotate() {
        int rc = roteType();
        if (rc <= 1)
            return;
        if (rc == 2) {
            rotate4();
            rotate4();
            rotate4();
            System.out.println(center.x + getMaxX() >= TetrisData.COL);
            if (data.mode == 0) {
                if (center.x + getMinX() < 0 ||
                        center.x + getMaxX() >= TetrisData.COL ||
                        (rotate_direct() || isOverlap(0)) ||
                        center.y + getMaxY() + 1 > TetrisData.ROW) {
                    reverse_rotate4();
                    reverse_rotate4();
                    reverse_rotate4();
                }
            } else if (data.mode == 1) {
                if (center.x + getMinX() < 0 ||
                        center.x + getMaxX() + 1 > TetrisData.COL ||
                        (rotate_direct() || isOverlap(0)) ||
                        center.y + getMinY() + 1 < 0) {
                    reverse_rotate4();
                    reverse_rotate4();
                    reverse_rotate4();
                }
            }
        } else {
            rotate4();
            if (data.mode == 0) {
                if (center.x + getMinX() < 0 ||
                        center.x + getMaxX() + 1 > TetrisData.COL && canmove() == false ||
                        (rotate_direct() || isOverlap(0)) ||
                        center.y + getMaxY() + 1 > TetrisData.ROW) {
                    System.out.println();
                    reverse_rotate4();
                }
            } else if (data.mode == 1) {
                if (center.x + getMinX() < 0 ||
                        center.x + getMaxX() > TetrisData.COL && canmove() == false ||
                        (rotate_direct() || isOverlap(0)) ||
                        center.y + getMinY() + 1 < 0) {
                    reverse_rotate4();
                }
            }
        }
    }

    // 조각 회전 (4회 회전)
    public void rotate4() {
        for (int i = 0; i < 4; i++) {
            int temp = c[i];
            c[i] = -r[i];
            r[i] = temp;
        }
    }

    // 조각 회전을 반대로 되돌림 (4회 회전)
    public void reverse_rotate4() {
        for (int i = 0; i < 4; i++) {
            int temp = r[i];
            r[i] = -c[i];
            c[i] = temp;
        }
    }

    // 블록 회전 시 다른 블록과 좌우로 겹치는 경우 검사
    public boolean rotate_direct() {
        int x = getX();
        int y = getY();
        // 왼쪽 검사
        for (int i = 0; i < r.length; i++) {
            if (data.getAt(y + r[i], x + c[i]) != 0) {
                System.out.println("왼쪽!");
                return true;
            }
        }
        // 오른쪽 검사
        for (int i = 0; i < r.length; i++) {
            if (data.getAt(y + r[i], x + c[i]) != 0) {
                System.out.println("오른쪽!");
                return true;
            }
        }
        return false;
    }

    // 이동 가능 여부를 검사
    public boolean canmove() {
        if (isOverlap(RIGHT) || isOverlap(LEFT) || isOverlap(DOWN)) {
            return true;
        }
        return false;
    }

    // 현재 블록 조각의 상태를 문자열로 저장
    public String saveNetworkPiece() {
        String piece = r[0] + "." + r[1] + "." + r[2] + "." + r[3] + "." + c[0] + "." + c[1] + "." + c[2] + "." + c[3]
                + "." + center.x + "." + center.y + "." + getType();
        return piece;
    }

    // 저장된 문자열로부터 블록 조각의 상태를 불러옴
    public void loadNetworkPiece(String str) {
        String[] fixedStr = str.split("\\.");
        for (int i = 0; i < 11; i++) {
            switch (i / 4) {
                case 0: // r 목록
                    r[i] = Integer.parseInt(fixedStr[i]);
                    break;
                case 1: // c 목록
                    c[i % 4] = Integer.parseInt(fixedStr[i]);
                    break;
                case 2: // x,y좌표 및 회전 타입
                    if (i % 4 == 0) {
                        center.x = Integer.parseInt(fixedStr[i]);
                    } else if (i % 4 == 1) {
                        center.y = Integer.parseInt(fixedStr[i]);
                    } else {
                        type = Integer.parseInt(fixedStr[i]);
                    }
                    break;
            }
        }
    }
}
