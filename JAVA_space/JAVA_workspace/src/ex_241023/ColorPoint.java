package ex_241023;

class Point { 
	private int x, y; // 한 점을 구성하는 x, y 좌표

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void showPoint() { // 점의 좌표 출력
		System.out.println("(" + x + "," + y + ")");
	}
}

// Point를 상속받은 ColorPoint 선언
class ColorPoint extends Point {
	private String color; // 점의 색

	public void setColor(String color) {
		this.color = color;
	}

	public void showColorPoint() { // 컬러 점의 좌표 출력
		System.out.print(color);
		showPoint(); // Point의 showPoint() 호출
	}
}