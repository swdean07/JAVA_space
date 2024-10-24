package ex_241024;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hsw1009Main {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. 입력, 2. 출력, 3. 검색, 4. 삭제, 5. 랜덤, 6. 종료");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    inputContact();
                    break;
                case 2:
                    outputContacts();
                    break;
                case 3:
                    searchContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    randomContact();
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("올바른 선택을 해주세요.");
            }
        }
    }

    private static void inputContact() {
        System.out.print("이름: ");
        String name = scanner.nextLine();
        System.out.print("연락처: ");
        String contactNumber = scanner.nextLine();
        String registrationDate = java.time.LocalDateTime.now().toString(); // 현재 시간
        
        Contact contact = new Contact(name, contactNumber, registrationDate);
        contacts.add(contact);
        
        System.out.println("연락처가 등록되었습니다.");
    }

    private static void outputContacts() {
        if (contacts.isEmpty()) {
            System.out.println("등록된 연락처가 없습니다.");
            return;
        }
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    private static void searchContact() {
        System.out.print("검색할 이름: ");
        String name = scanner.nextLine();
        boolean found = false;

        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println(contact);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("해당 이름의 연락처를 찾을 수 없습니다.");
        }
    }

    private static void deleteContact() {
        System.out.print("삭제할 이름: ");
        String name = scanner.nextLine();
        boolean deleted = false;

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getName().equalsIgnoreCase(name)) {
                contacts.remove(i);
                System.out.println(name + "의 연락처가 삭제되었습니다.");
                deleted = true;
                break;
            }
        }
        if (!deleted) {
            System.out.println("해당 이름의 연락처를 찾을 수 없습니다.");
        }
    }

    public static ArrayList<Contact> getContacts() {
		return contacts;
	}

	public static void setContacts(ArrayList<Contact> contacts) {
		Hsw1009Main.contacts = contacts;
	}

	public static Scanner getScanner() {
		return scanner;
	}

	public static void setScanner(Scanner scanner) {
		Hsw1009Main.scanner = scanner;
	}

	private static void randomContact() {
        if (contacts.isEmpty()) {
            System.out.println("등록된 연락처가 없습니다.");
            return;
        }
        Random rand = new Random();
        int index = rand.nextInt(contacts.size());
        System.out.println("랜덤으로 선택된 연락처: " + contacts.get(index));
    }
}

class Contact {
    private String name;
    private String contactNumber;
    private String registrationDate;

    public Contact(String name, String contactNumber, String registrationDate) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "이름: " + name + ", 연락처: " + contactNumber + ", 등록일: " + registrationDate;
    }
}