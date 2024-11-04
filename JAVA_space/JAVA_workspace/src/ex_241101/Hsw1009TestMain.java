package ex_241101;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Hsw1009TestMain extends JFrame {
    // 프로그램 제목과 메시지 상수 정의
    private static final String TITLE = "회원 관리 프로그램 V 1.0.0";
    private static final String INPUT_ERROR_MSG = "이미지 첨부와 내용을 작성하세요.";
    private static final String EMAIL_EXISTS_MSG = "이미 존재하는 이메일입니다.";
    private static final String INSERT_SUCCESS_MSG = "회원 정보가 성공적으로 저장되었습니다.";
    private static final String INSERT_FAIL_MSG = "회원 정보 저장에 실패했습니다.";
    private static final String NO_MEMBERS_FOUND_MSG = "조회된 회원이 없습니다.";
    private static final String FETCH_SUCCESS_MSG = "회원 목록이 성공적으로 불러왔습니다.";
    private static final String PROFILE_CANCEL_MSG = "프로필 이미지 선택이 취소되었습니다.";
    private static final String INTEREST_PROMPT_MSG = "요즘 관심 분야는 무엇인가요?";
    private static final String SEARCH_CONFIRM_MSG = "검색을 진행하시겠습니까?";
    private static final String DELETE_CONFIRM_MSG = "정말로 이 회원 정보를 삭제하시겠습니까?";
    private static final String DELETE_SUCCESS_MSG = "회원 정보가 성공적으로 삭제되었습니다.";
    private static final String DELETE_FAIL_MSG = "회원 정보 삭제에 실패했습니다.";

    // UI 컴포넌트 정의
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private Hsw1009DAO Hsw1009DAO; // DAO 객체
    private JLabel profileLabel;
    private JLabel interestLabel;
    private JTextArea detailArea;
    private File profileImageFile;

    public Hsw1009TestMain() {
        setTitle(TITLE);
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Hsw1009DAO = new Hsw1009DAO(); // DAO 초기화

        // 입력 패널 설정
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Name: "));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("E-mail: "));
        emailField = new JTextField(10);
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField(10);
        inputPanel.add(passwordField);

        // 버튼 추가
        JButton addButton = new JButton("Add");
        JButton fetchButton = new JButton("Search");
        JButton uploadButton = new JButton("Upload");
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");

        inputPanel.add(addButton);
        inputPanel.add(fetchButton);
        inputPanel.add(uploadButton);
        inputPanel.add(saveButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH); // 상단에 입력 패널 추가

        // 프로필 패널 설정
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());

        // 프로필 이미지 패널 설정
        JPanel imagePanel = new JPanel(new GridBagLayout());
        profileLabel = new JLabel("Profile Image", SwingConstants.CENTER);
        profileLabel.setPreferredSize(new Dimension(150, 150));
        profileLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        imagePanel.add(profileLabel, gbc);
        
        profilePanel.add(imagePanel, BorderLayout.WEST);

        // 관심 분야 패널 설정
        JPanel interestPanel = new JPanel();
        interestPanel.setLayout(new BorderLayout());
        interestPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        interestLabel = new JLabel("관심 분야: 없음", SwingConstants.CENTER);
        interestLabel.setPreferredSize(new Dimension(150, 30));
        interestPanel.add(interestLabel, BorderLayout.NORTH);

        detailArea = new JTextArea(5, 20);
        detailArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        detailArea.setBackground(Color.LIGHT_GRAY);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(detailArea);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        interestPanel.add(scrollPane, BorderLayout.CENTER);

        profilePanel.add(interestPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.WEST); // 프로필 패널 추가

        // 테이블 설정
        String[] columnNames = {"이름", "이메일", "패스워드"};
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        add(tableScrollPane, BorderLayout.CENTER); // 테이블 추가

        // 버튼에 액션 리스너 추가
        addButton.addActionListener(e -> addMember());
        fetchButton.addActionListener(e -> selectMembers());
        uploadButton.addActionListener(e -> chooseProfileImage());
        saveButton.addActionListener(e -> save());
        deleteButton.addActionListener(e -> deleteMember());

        setVisible(true); // 프레임 보이기
    }

    private void chooseProfileImage() {
        // 프로필 이미지 선택기 설정
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "png", "gif"));
        
        int result = fileChooser.showOpenDialog(this); // 이미지 선택 대화상자 표시

        if (result == JFileChooser.APPROVE_OPTION) {
            profileImageFile = fileChooser.getSelectedFile();
            ImageIcon profileIcon = new ImageIcon(new ImageIcon(profileImageFile.getAbsolutePath())
                    .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            profileLabel.setIcon(profileIcon); // 선택한 이미지 설정
            System.out.println("프로필 이미지가 선택되었습니다: " + profileImageFile.getAbsolutePath());

            // 관심 분야 입력 받기
            String interest = JOptionPane.showInputDialog(this, INTEREST_PROMPT_MSG);
            if (interest != null && !interest.trim().isEmpty()) {
                interestLabel.setText("관심 분야: " + interest);
                System.out.println("내용이 입력되었습니다: " + interest);
            } else {
                interestLabel.setText("관심 분야: 없음");
                System.out.println("내용이 입력되지 않았습니다.");
            }
        } else {
            System.out.println(PROFILE_CANCEL_MSG); // 이미지 선택 취소 시 메시지
        }
    }

    private void addMember() {
        // 회원 정보를 입력받고 저장하는 메서드
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        // 입력값 검증
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || profileImageFile == null) {
            JOptionPane.showMessageDialog(this, INPUT_ERROR_MSG, "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Hsw1009DAO.isEmailExists(email)) {
            JOptionPane.showMessageDialog(this, EMAIL_EXISTS_MSG, "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String Password = Password(password); // 비밀번호 해싱
            String interest = interestLabel.getText().replace("관심 분야: ", "");
            String details = detailArea.getText();
            
            // 디버깅 정보 출력
            System.out.println("Member Info: " + name + ", " + email + ", " + Password + ", " + interest + ", " + profileImageFile.getAbsolutePath());

            Hsw1009DTO member = new Hsw1009DTO(name, email, Password, interest + " - " + details, profileImageFile.getAbsolutePath());
            boolean isInserted = Hsw1009DAO.insertMember(member);
            if (isInserted) {
                JOptionPane.showMessageDialog(this, INSERT_SUCCESS_MSG, "저장 성공", JOptionPane.INFORMATION_MESSAGE);
                tableModel.addRow(new String[]{name, email, "*****"}); // 테이블에 추가
                clearFields(); // 입력 필드 초기화
            } else {
                JOptionPane.showMessageDialog(this, INSERT_FAIL_MSG, "저장 실패", JOptionPane.ERROR_MESSAGE);
                System.out.println("회원 정보 저장 실패: 이메일 = " + email);
            }
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(this, "비밀번호 해싱 실패", "오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void selectMembers() {
        // 회원 정보를 조회하는 메서드
        List<Hsw1009DTO> members = Hsw1009DAO.selectAllMembers();
        tableModel.setRowCount(0); // 테이블 초기화

        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, NO_MEMBERS_FOUND_MSG, "조회 결과", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Hsw1009DTO member : members) {
                tableModel.addRow(new String[]{member.getName(), member.getEmail(), "*****"}); // 비밀번호는 숨김
            }
            JOptionPane.showMessageDialog(this, FETCH_SUCCESS_MSG, "조회 성공", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void save() {
        // 회원 정보를 파일로 저장하는 메서드
        List<Hsw1009DTO> members = Hsw1009DAO.selectAllMembers();
        String filePath = "C:\\Users\\admin\\Documents\\member501.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // CSV 파일 헤더 작성
            writer.write("Name,Email,Password,Interest,ProfileImage");
            writer.newLine(); // 새 줄로 이동

            for (Hsw1009DTO member : members) {
                // 각 회원 정보를 CSV 형식으로 작성
                writer.write(member.getName() + "," + member.getEmail() + "," + member.getPassword() + ","
                        + member.getInterest() + "," + member.getProfileImagePath());
                writer.newLine(); // 새 줄로 이동
            }
            JOptionPane.showMessageDialog(this, "회원 정보가 성공적으로 파일에 저장되었습니다.", "파일 저장", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 저장 중 오류가 발생했습니다.", "파일 저장 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    
    private void loadMembersFromFile() {
        String filePath = "C:\\Users\\admin\\Documents\\member501.csv";
        List<Hsw1009DTO> members = new ArrayList<>(); // 리스트 이름 수정

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // 첫 번째 줄(헤더)은 건너뜀

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // 데이터 길이가 맞지 않는 경우 무시
                if (data.length == 5) {
                    String name = data[0].trim();
                    String email = data[1].trim();
                    String password = data[2].trim();
                    String interest = data[3].trim();
                    String profileImage = data[4].trim();

                    // 회원 DTO 객체 생성 및 추가
                    members.add(new Hsw1009DTO(name, email, password, interest, profileImage));
                } else {
                    System.err.println("데이터 형식 오류: " + line); // 잘못된 형식의 행을 로그에 남김
                }
            }

            // 테이블 업데이트
            updateMemberTable(members); // 테이블 업데이트 메서드 호출
            JOptionPane.showMessageDialog(this, "회원 정보를 성공적으로 불러왔습니다.", "파일 불러오기", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "파일을 찾을 수 없습니다: " + filePath, "파일 오류", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "파일 읽기 중 오류가 발생했습니다.", "파일 읽기 오류", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateMemberTable(List<Hsw1009DTO> members) {
        tableModel.setRowCount(0); // 테이블 초기화
        for (Hsw1009DTO member : members) {
            tableModel.addRow(new String[]{member.getName(), member.getEmail(), "*****"}); // 비밀번호는 숨김
        }
    }


    private void deleteMember() {
        // 선택된 회원 정보를 삭제하는 메서드
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 회원을 선택하세요.", "삭제 오류", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, DELETE_CONFIRM_MSG, "삭제 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String email = (String) memberTable.getValueAt(selectedRow, 1);
            boolean isDeleted = Hsw1009DAO.deleteMember(email);
            if (isDeleted) {
                JOptionPane.showMessageDialog(this, DELETE_SUCCESS_MSG, "삭제 성공", JOptionPane.INFORMATION_MESSAGE);
                tableModel.removeRow(selectedRow); // 테이블에서 삭제
            } else {
                JOptionPane.showMessageDialog(this, DELETE_FAIL_MSG, "삭제 실패", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String Password(String password) throws NoSuchAlgorithmException {
        // 비밀번호 해싱 메서드
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private void clearFields() {
        // 입력 필드 초기화 메서드
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        profileLabel.setIcon(null);
        interestLabel.setText("관심 분야: 없음");
        detailArea.setText("");
        profileImageFile = null; // 이미지 파일 초기화
    }

    public static void main(String[] args) {
        new Hsw1009TestMain(); // 메인 메서드 실행
    }
}







