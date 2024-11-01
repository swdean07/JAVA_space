package ex_241101;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Hsw1009TestMain extends JFrame {
    // Constants for UI messages
    private static final String TITLE = "회원 관리 프로그램 V 1.0.0";
    private static final String INPUT_ERROR_MSG = "모든 필드와 이미지를 입력하세요.";
    private static final String EMAIL_EXISTS_MSG = "이미 존재하는 이메일입니다.";
    private static final String INSERT_SUCCESS_MSG = "회원 정보가 성공적으로 저장되었습니다.";
    private static final String INSERT_FAIL_MSG = "회원 정보 저장에 실패했습니다.";
    private static final String NO_MEMBERS_FOUND_MSG = "조회된 회원이 없습니다.";
    private static final String FETCH_SUCCESS_MSG = "회원 목록이 성공적으로 불러왔습니다.";
    private static final String PROFILE_CANCEL_MSG = "프로필 이미지 선택이 취소되었습니다.";
    private static final String HOBBY_PROMPT_MSG = "본인이 좋아하는 관심사는 무엇인가요?";

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private Hsw1009DAO memberDAO;
    private JLabel profileLabel;
    private JLabel hobbyLabel; // 취미를 표시할 레이블
    private JTextArea detailArea; // 상세 내용 입력을 위한 텍스트 영역
    private File profileImageFile;

    public Hsw1009TestMain() {
        setTitle(TITLE);
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        memberDAO = new Hsw1009DAO();

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

        JButton addButton = new JButton("Add");
        JButton fetchButton = new JButton("Search");
        JButton uploadButton = new JButton("Upload");

        inputPanel.add(addButton);
        inputPanel.add(fetchButton);
        inputPanel.add(uploadButton);

        add(inputPanel, BorderLayout.NORTH);

        // 프로필 이미지와 취미를 표시할 JPanel 설정
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());

        // 프로필 이미지를 위한 패널 생성
        JPanel imagePanel = new JPanel(new GridBagLayout());
        profileLabel = new JLabel("Profile Image", SwingConstants.CENTER);
        profileLabel.setPreferredSize(new Dimension(150, 150));
        profileLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 연한 회색 테두리

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        imagePanel.add(profileLabel, gbc);
        
        profilePanel.add(imagePanel, BorderLayout.WEST);

        // 취미를 표시할 레이블과 테두리를 설정할 JPanel
        JPanel hobbyPanel = new JPanel();
        hobbyPanel.setLayout(new BorderLayout());
        hobbyPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 연한 회색 테두리
        hobbyLabel = new JLabel("취미: 없음", SwingConstants.CENTER);
        hobbyLabel.setPreferredSize(new Dimension(150, 30));
        hobbyPanel.add(hobbyLabel, BorderLayout.NORTH);

        // 상세 내용 입력을 위한 텍스트 영역 추가
        detailArea = new JTextArea(5, 20);
        detailArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // 연한 회색 테두리
        detailArea.setBackground(Color.LIGHT_GRAY); // 회색 배경색 설정
        detailArea.setLineWrap(true); // 줄 바꿈 허용
        detailArea.setWrapStyleWord(true); // 단어 단위로 줄 바꿈

        // JScrollPane을 추가하여 스크롤 가능하게 설정
        JScrollPane scrollPane = new JScrollPane(detailArea);
        scrollPane.setPreferredSize(new Dimension(200, 100)); // 원하는 고정 크기로 설정
        hobbyPanel.add(scrollPane, BorderLayout.CENTER);

        profilePanel.add(hobbyPanel, BorderLayout.CENTER);
        add(profilePanel, BorderLayout.WEST);

        String[] columnNames = {"이름", "이메일", "패스워드"};
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(memberTable);
        add(tableScrollPane, BorderLayout.CENTER);

        addButton.addActionListener(e -> addMember());
        fetchButton.addActionListener(e -> fetchMembers());
        uploadButton.addActionListener(e -> chooseProfileImage());

        setVisible(true);
    }

    // 이미지 선택과 취미 입력을 위한 메서드
    private void chooseProfileImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            profileImageFile = fileChooser.getSelectedFile();
            ImageIcon profileIcon = new ImageIcon(new ImageIcon(profileImageFile.getAbsolutePath())
                    .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            profileLabel.setIcon(profileIcon);
            System.out.println("프로필 이미지가 선택되었습니다: " + profileImageFile.getAbsolutePath());

            // 좋아하는 관심사 입력 받기
            String hobby = JOptionPane.showInputDialog(this, HOBBY_PROMPT_MSG);
            if (hobby != null && !hobby.trim().isEmpty()) {
                hobbyLabel.setText("취미: " + hobby);
                System.out.println("취미가 입력되었습니다: " + hobby);
            } else {
                hobbyLabel.setText("취미: 없음");
                System.out.println("취미가 입력되지 않았습니다.");
            }
        } else {
            System.out.println(PROFILE_CANCEL_MSG);
        }
    }

    // 회원 추가 메서드
    private void addMember() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || profileImageFile == null) {
            JOptionPane.showMessageDialog(this, INPUT_ERROR_MSG, "입력 오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("회원 추가 실패: 모든 필드가 채워져야 합니다.");
            return;
        }

        if (memberDAO.isEmailExists(email)) {
            JOptionPane.showMessageDialog(this, EMAIL_EXISTS_MSG, "입력 오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("회원 추가 실패: 이미 존재하는 이메일입니다.");
            return;
        }

        try {
            String hashedPassword = hashPassword(password);
            String hobby = hobbyLabel.getText().replace("취미: ", ""); // 취미 텍스트에서 "취미: "를 제거
            String details = detailArea.getText(); // 상세 내용을 가져옴
            Hsw1009DTO member = new Hsw1009DTO(name, email, hashedPassword, hobby + " - " + details, profileImageFile.getAbsolutePath());
            if (memberDAO.insertMember(member)) {
                JOptionPane.showMessageDialog(this, INSERT_SUCCESS_MSG, "저장 성공", JOptionPane.INFORMATION_MESSAGE);
                tableModel.addRow(new String[]{name, email, "*****"});
                System.out.println("회원 추가 성공: " + name + " (" + email + ")");
                clearFields();
                // 회원가입이 완료되었음을 알림
                JOptionPane.showMessageDialog(this, "회원가입이 정상적으로 완료되었습니다.", "회원가입 완료", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, INSERT_FAIL_MSG, "저장 실패", JOptionPane.ERROR_MESSAGE);
                System.out.println("회원 추가 실패: 데이터베이스 오류");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류 발생: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            System.out.println("회원 추가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("비밀번호 해싱 오류", e);
        }
    }

    // 입력 필드 초기화 메서드
    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        profileLabel.setIcon(null);
        hobbyLabel.setText("취미: 없음");
        detailArea.setText(""); // 상세 내용 초기화
    }

    // 회원 조회 메서드
    private void fetchMembers() {
        List<Hsw1009DTO> members = memberDAO.fetchAllMembers();
        tableModel.setRowCount(0); // 기존 데이터 초기화
        if (members.isEmpty()) {
            JOptionPane.showMessageDialog(this, NO_MEMBERS_FOUND_MSG, "조회 실패", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(NO_MEMBERS_FOUND_MSG);
        } else {
            for (Hsw1009DTO member : members) {
                tableModel.addRow(new String[]{member.getName(), member.getEmail(), "*****"});
            }
            memberTable.getSelectionModel().addListSelectionListener(e -> showProfileDialog());
            JOptionPane.showMessageDialog(this, FETCH_SUCCESS_MSG, "조회 성공", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("회원 목록 조회 성공: " + members.size() + "명의 회원이 조회되었습니다.");
        }
    }

    private void showProfileDialog() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            String email = (String) tableModel.getValueAt(selectedRow, 1);

            Hsw1009DTO member = memberDAO.getMemberByEmail(email);
            if (member != null) {
                String profileImagePath = member.getProfileImagePath();
                String hobby = member.getHobby();

                JFrame profileFrame = new JFrame("프로필");
                profileFrame.setSize(300, 400);
                profileFrame.setLayout(new BorderLayout());

                JPanel infoPanel = new JPanel(new GridLayout(3, 1));
                infoPanel.add(new JLabel("이름: " + name));
                infoPanel.add(new JLabel("이메일: " + email));
                infoPanel.add(new JLabel("취미: " + hobby));
                profileFrame.add(infoPanel, BorderLayout.NORTH);

                JLabel imageLabel = new JLabel();
                if (profileImagePath != null && !profileImagePath.isEmpty()) {
                    ImageIcon profileIcon = new ImageIcon(new ImageIcon(profileImagePath)
                            .getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
                    imageLabel.setIcon(profileIcon);
                } else {
                    imageLabel.setText("No Image");
                }
                profileFrame.add(imageLabel, BorderLayout.CENTER);

                profileFrame.setVisible(true);
                System.out.println("프로필 대화상자가 표시되었습니다: " + name);
            }
        } else {
            System.out.println("선택된 회원이 없습니다.");
        }
    }

    public static void main(String[] args) {
        new Hsw1009TestMain();
    }
}












