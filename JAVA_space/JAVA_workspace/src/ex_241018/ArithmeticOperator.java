package ex_241018;

import java.util.Scanner;

public class ArithmeticOperator {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("정수를입력하세요:");
		int time = scanner.nextInt(); // 정수입력
		int second = time % 60; // 60으로나눈나머지는초
		int minute = (time / 60) % 60; // 60으로나눈몫을다시60으로나눈나머지는분
		int hour = (time / 60) / 60; // 60으로나눈몫을다시60으로나눈몫은시간
		System.out.print(time + "초는");
		System.out.print(hour + "시간, ");
		System.out.print(minute + "분, ");
		System.out.println(second + "초입니다.");
		scanner.close();
		
	}

}