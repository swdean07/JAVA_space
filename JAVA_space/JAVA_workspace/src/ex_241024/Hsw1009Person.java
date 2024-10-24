package ex_241024;

class Hsw1009Person {
    private String name;
    private String contact;
    private String registrationDate;

    public Hsw1009Person(String name, String contact, String registrationDate) {
        this.name = name;
        this.contact = contact;
        this.registrationDate = registrationDate;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
		this.name = name;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
        return "이름: " + name + ", 연락처: " + contact + ", 등록 날짜: " + registrationDate;
    }
}
