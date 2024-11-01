package ex_241101;

public class Hsw1009DTO {
	// 모델 정의, 데이터를 
	// 비지니스 모델 (실제 디비)-> 프레젠테이션 모델(출력 형식)
	String deptno;  // dept 테이블의 칼럼을 저장하기 위한 인스턴스 변수를 정의.
	String dname;
	String loc;
	private String name;
	private String email;
	private String password;
	
	public Hsw1009DTO(String name2, String email2, String hashedPassword, String string, String string2) {
	
	}
	
	public Hsw1009DTO(String name, String dname, String loc) {
		super();
		this.deptno = name;
		this.dname = dname;
		this.loc = loc;
	}
	public Hsw1009DTO(String name2, String email2, String hashedPassword, String absolutePath) {
		// TODO Auto-generated constructor stub
	}

	public String getDeptno() {
		return deptno;
	}
	public void setDeptno(String deptno) {
		this.deptno = deptno;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}

	public void setDeptno(int int1) {
		return;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getProfileImagePath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHobby() {
		// TODO Auto-generated method stub
		return null;
	}
}