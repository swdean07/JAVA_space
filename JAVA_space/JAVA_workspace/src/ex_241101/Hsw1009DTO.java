package ex_241101;

public class Hsw1009DTO {
    private String name;
    private String email;
    private String password;
    private String interest;
    private String profileImagePath;
    private int id; // id 필드 추가

    // 기본 생성자
    public Hsw1009DTO(String name, String email, String password, String interest, String profileImagePath) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.interest = interest;
        this.profileImagePath = profileImagePath;
    }

    // id 포함 생성자
    public Hsw1009DTO(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.interest = "없음"; // 기본값
        this.profileImagePath = ""; // 기본값
    }

    // Getter 메서드
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getInterest() {
        return interest;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public int getId() {
        return id; // id 반환
    }

    // Setter 메서드
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    // 프로필 이미지 경로 반환
    public String getProfileImage() {
        return profileImagePath;
    }
}



