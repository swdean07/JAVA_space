package ex_241023;

import java.util.ArrayList;

import ex_241023.Person;

public class Hsw1009ArrayList {

	public static void main(String[] args) {
		ArrayList<Person> a = new ArrayList<Person>();

		Scanner scanner = new Scanner();
		System.out.println("연락처 프로그램 만들기.ArrayList 버전");
		System.out.println("1.입력, 2.출력, 3.검색, 4.삭제, 5.랜덤, 6.종료 ");
		
		Person s= scanner.next();
		a.add(s);
	}
}

