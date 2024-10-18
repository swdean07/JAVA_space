package ex_241018;

import java.util.Scanner;

public class ScannerEx1 {

	public static void main(String[] args) {
		//System.in, 키보드로 입력된 내용이 바이트 코드로 변환해주는 역할.
		//Scanner 이용해서, 우리가 사용하는 문자열, 정수 형태로 이용.
		//자바에 기본으로 탑재된 기능. 다른 패키지에 있으면, 반드시 import 후 사용 가능.
		//IDE, 자동으로 임포트, 패키지명이 겹치면, 선택만 해주기.
		System.out.println("Scanner를 이용한, 콘솔에 입력한 문자열, 숫자 입력 받기.");
		
		// Scanner 객체, 인스턴스 만들기.
		// 정의,
		// 클래스타입 참조형 변수 = new 생성자();
		Scanner scanner = new Scanner(System.in);
		System.out.println("오늘 먹고 싶은 점심 메뉴입력해주세요 : ");
		String lunchMenu = scanner.next();
		System.out.println("먹고 싶은 메뉴 : " + lunchMenu);
		
		System.out.println("주말에 먹고 싶은 메뉴 입력하기 : ");
		String weekendMenu = scanner.next();
		System.out.println("주말에 먹고 싶은 메뉴 : " + weekendMenu);
		
		
	}

}
