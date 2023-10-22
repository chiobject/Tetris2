package Main;
import java.awt.Point;

public abstract class Piece {
	final int DOWN = 0;  // 방향 지정
	final int LEFT = 1;
	final int RIGHT = 2;
	protected int r[];   // Y축 좌표 배열
	protected int c[];   // X축 좌표 배열
	protected TetrisData data;  // 테트리스 내부 데이터
	protected Point center; // 조각의 중심 좌표
	protected int type, roteType;
	public Piece(TetrisData data, int type, int roteType) {
		r = new int[4];
		c = new int[4];
		this.data = data;
		this.type = type;
		this.roteType = roteType;
		center = new Point(5,0);
	}
	public int getType() {return type;};
	public int roteType() {return roteType;};
 
	public int getX() { return center.x; }
	public int getY() { return center.y; }
	public boolean copy(){  // 값 복사
		boolean value = false;
		int x = getX();
		int y = getY();
		if(getMinY() + y <= 0) { // 게임 종료 상황
			value = true;
		}
		for(int i=0; i < 4; i++) {
			data.setAt(y + r[i], x + c[i], getType());
		}
		return value;
	}
 
	public boolean isOverlap(int dir){ // 다른 조각과 겹치는지 파악
		int x = getX();
		int y = getY();
		switch(dir) {
		case 0 : // 아래
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i]+1, x+c[i]) != 0) {
					System.out.println("아래!");
					return true;
				}
			}
			break;
		case 1 : // 왼쪽
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i], x+c[i] - 1) != 0) {
					System.out.println("왼쪽!");
					return true;
				}
			}
			break;
		case 2 : // 오른쪽
			for(int i=0; i < r.length; i++) {
				if(data.getAt(y+r[i], x+c[i] + 1) != 0) {
					System.out.println("오른쪽!");
					return true;
				}
			}
			break;
		}
		return false;
	}
 
	public int getMinX() {
		int min = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] < min) {
				min = c[i];
			}
		}
		return min;
	}
 
	public int getMaxX() {
		int max = c[0];
		for(int i=1; i < c.length; i++) {
			if(c[i] > max) {
				max = c[i];
			}
		}
		return max;
	}
 
	public int getMinY() {
		int min = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] < min) {
				min = r[i];
			}
		}
		return min;
	}
 
	public int getMaxY() {
		int max = r[0];
		for(int i=1; i < r.length; i++) {
			if(r[i] > max) {
				max = r[i];
			}
		}
		return max;
	}

	public boolean moveDown() { // 아래로 이동
		if(center.y + getMaxY() + 1 < TetrisData.ROW) {
			if(isOverlap(DOWN) != true) {
				center.y++;
			} else {
				return true;
			}
		} 
		else {
				return true; 
			}
		return false;
	}

	public void moveLeft() {  // 왼쪽으로 이동
		if(center.x + getMinX() -1 >= 0)
			if(isOverlap(LEFT) != true) {center.x--;}
			else return;
	}

	public void moveRight() {  // 오른쪽으로 이동
		if(center.x + getMaxX() + 1 < TetrisData.COL)
			if(isOverlap(RIGHT) != true) {center.x++;}
			else return;
	}

	public void rotate() {  // 조각 회전
		int rc = roteType();
		if(rc <= 1) return;
		if(rc == 2) {
			rotate4();
			rotate4();
			rotate4();
			if(center.x + getMinX()  < 0 && canmove() == false || // 블록이 좌측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
					center.x + getMaxX() +1 > TetrisData.COL && canmove() == false || // 블록이 우측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
					(rotate_direct() || isOverlap(0))  // 블록 회전 시 다른 블록과 겹치는 경우
					|| center.y + getMaxY() + 1 > TetrisData.ROW) {  // 블록이 아래를 뚫는 경우
				reverse_rotate4();
				reverse_rotate4();
				reverse_rotate4();
			}
		} else {
			rotate4();
			if(center.x + getMinX()  < 0 && canmove() == false || // 블록이 좌측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
					center.x + getMaxX() +1 > TetrisData.COL && canmove() == false || // 블록이 우측 벽에서 회전했을 때 맵 밖으로 넘어가는 경우
					(rotate_direct() || isOverlap(0))  // 블록 회전 시 다른 블록과 겹치는 경우
					|| center.y + getMaxY() + 1 > TetrisData.ROW) {  // 블록이 아래를 뚫는 경우
				 reverse_rotate4(); 
				 System.out.println("어어? 안된다!");}
		}
	}

	public void rotate4() {   // 조각 회전
		for(int i = 0; i < 4; i++) {
			int temp = c[i];
			c[i] = -r[i];
			r[i] = temp;
		}
	}
	
	public void reverse_rotate4() {   // 조각 회전
		for(int i = 0; i < 4; i++) {
			int temp = r[i];
			r[i] = -c[i];
			c[i] = temp;
		}
	}
	
	public boolean rotate_direct() { // 블록 회전 시 다른 블록과 좌 우가 겹치는 경우(isoverlap 보다 반경이 1 더 커서 따로 빼냄)
		int x = getX();
		int y = getY();
		// 왼쪽 검사
		for(int i=0; i < r.length; i++) {
			if(data.getAt(y+r[i], x+c[i]) != 0) {
				System.out.println("왼쪽!");
				return true;
			}
		}
		
		//오른쪽 검사
		for(int i=0; i < r.length; i++) {
			if(data.getAt(y+r[i], x+c[i]) != 0) {
				System.out.println("오른쪽!");
				return true;
			}
		}
		return false;
	}
	
	
	public boolean canmove() {
		if (isOverlap(RIGHT) || isOverlap(LEFT) || isOverlap(DOWN)){
			return true;
		}
		return false;
	}

	
	public String saveNetworkPiece() {
		String piece =
		r[0] + "." + r[1] + "." + r[2] + "." + r[3] + "." +
		c[0] + "." + c[1] + "." + c[2] + "." + c[3] + "." +
		center.x + "." + center.y + "." + getType();
		return piece;
	}
	
	public void loadNetworkPiece(String str) {
		String[] fixedStr = str.split("\\.");
		for(int i = 0; i < 11; i++) {
			switch(i/4) {
			case 0:	// r 목록
				r[i] = Integer.parseInt(fixedStr[i]);	
				break;
			case 1:		// c 목록
				c[i%4] = Integer.parseInt(fixedStr[i]);	
				break;
			case 2:	// x,y좌표 및 회전 타입
				if(i%4 == 0) {
					center.x = Integer.parseInt(fixedStr[i]);
				} else if(i%4 == 1){
					center.y = Integer.parseInt(fixedStr[i]);
				} else {
					type = Integer.parseInt(fixedStr[i]);
				}
				break;
			}		
		}
	}
}
