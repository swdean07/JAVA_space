package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MemberManagementFrame extends JFrame {
    private JTextField memberIdField, nameField, birthField, phoneField, emailField, joinDateField, addressField;
    private JTextField overdueCountField;
    private JRadioButton yesRadioButton, noRadioButton;
    private ButtonGroup loanEligibilityGroup;

    private ArrayList<String> memberIds; // 임시 회원 ID 목록

    public MemberManagementFrame() {
        setTitle("회원 정보 관리");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        memberIds = new ArrayList<>();
        
        // UI 구성
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));

        memberIdField = new JTextField();
        nameField = new JTextField();
        birthField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        joinDateField = new JTextField();
        addressField = new JTextField();
        overdueCountField = new JTextField();

        yesRadioButton = new JRadioButton("Yes");
        noRadioButton = new JRadioButton("No");
        loanEligibilityGroup = new ButtonGroup();
        loanEligibilityGroup.add(yesRadioButton);
        loanEligibilityGroup.add(noRadioButton);

        JPanel loanEligibilityPanel = new JPanel();
        loanEligibilityPanel.add(yesRadioButton);
        loanEligibilityPanel.add(noRadioButton);

        panel.add(new JLabel("회원 ID:"));
        panel.add(memberIdField);
        panel.add(new JLabel("이름:"));
        panel.add(nameField);
        panel.add(new JLabel("생년월일:"));
        panel.add(birthField);
        panel.add(new JLabel("연락처:"));
        panel.add(phoneField);
        panel.add(new JLabel("이메일:"));
        panel.add(emailField);
        panel.add(new JLabel("가입일:"));
        panel.add(joinDateField);
        panel.add(new JLabel("주소:"));
        panel.add(addressField);
        panel.add(new JLabel("대여 가능 여부:"));
        panel.add(loanEligibilityPanel);
        panel.add(new JLabel("연체 횟수:"));
        panel.add(overdueCountField);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("저장");
        JButton addButton = new JButton("추가");
        JButton editButton = new JButton("수정");
        JButton deleteButton = new JButton("삭제");
        buttonPanel.add(saveButton);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // 각 버튼의 이벤트 처리
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("저장");
            }
        });
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick("추가");
            }
        });

     // 수정 버튼 이벤트 처리
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String memberId = JOptionPane.showInputDialog(null, "회원 ID를 입력하세요:");

                // 회원 ID가 비어 있으면 수정하지 않음
                if (memberId == null || memberId.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "회원 ID를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 회원 ID가 목록에 존재하는지 확인
                if (!memberIds.contains(memberId)) {
                    JOptionPane.showMessageDialog(null, "해당 회원 ID는 존재하지 않습니다.", "회원 ID 오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 회원 ID가 존재하면 해당 정보를 필드에 자동으로 채워줌
                    memberIdField.setText(memberId);  // 회원 ID는 수정 불가
                    nameField.setText("회원 이름");  // 실제 이름 데이터를 삽입해야 함
                    birthField.setText("회원 생년월일");  // 실제 생년월일 데이터를 삽입해야 함
                    phoneField.setText("회원 연락처");  // 실제 연락처 데이터를 삽입해야 함
                    emailField.setText("회원 이메일");  // 실제 이메일 데이터를 삽입해야 함
                    joinDateField.setText("회원 가입일");  // 실제 가입일 데이터를 삽입해야 함
                    addressField.setText("회원 주소");  // 실제 주소 데이터를 삽입해야 함
                    overdueCountField.setText("연체 횟수");  // 실제 연체 횟수 데이터를 삽입해야 함

                    // 수정 확인 메시지
                    int confirm = JOptionPane.showConfirmDialog(
                            null, "저장된 필드 내용을 수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        // 수정된 필드 내용이 중복되지 않는지 확인
                        if (isDuplicate()) {
                            JOptionPane.showMessageDialog(null, "회원 ID가 중복되었습니다. 다른 ID를 입력해주세요.", "중복 오류", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // 필드 내용 수정 후 콘솔에 출력
                            printFields();

                            // 회원 ID가 중복되지 않으면 수정 완료
                            JOptionPane.showMessageDialog(null, "회원 정보가 수정되었습니다.");
                            // 수정된 정보를 목록에 업데이트
                            memberIds.add(memberIdField.getText());  // 수정된 회원 ID 추가 (중복 검증 후)
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "회원 정보가 수정되지 않았습니다.");
                    }
                }
            }
        });


     // 삭제 버튼 이벤트 처리
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                        null, "필드 내용을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // 필드 내용 초기화
                    memberIdField.setText("");
                    nameField.setText("");
                    birthField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    joinDateField.setText("");
                    addressField.setText("");
                    overdueCountField.setText("");
                    yesRadioButton.setSelected(false);
                    noRadioButton.setSelected(false);

                    JOptionPane.showMessageDialog(null, "필드 내용이 삭제되었습니다.");
                } else {
                    JOptionPane.showMessageDialog(null, "필드 내용 삭제가 취소되었습니다.");
                }
            }
        });
    }

    private boolean isFieldsEmpty() {
        return memberIdField.getText().isEmpty() || nameField.getText().isEmpty() || birthField.getText().isEmpty() ||
               phoneField.getText().isEmpty() || emailField.getText().isEmpty() || joinDateField.getText().isEmpty() ||
               addressField.getText().isEmpty() || overdueCountField.getText().isEmpty() ||
               (!yesRadioButton.isSelected() && !noRadioButton.isSelected());
    }

    private boolean isDuplicate() {
        return memberIds.contains(memberIdField.getText());
    }

    private void handleButtonClick(String action) {
        if (isFieldsEmpty()) {
            JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
            return;
        } else if (isDuplicate()) {
            JOptionPane.showMessageDialog(null, "회원 ID가 중복되었습니다. 다른 ID를 입력해주세요.", "중복 오류", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, action + "하시겠습니까?", action + " 확인", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            printFields();
            memberIds.add(memberIdField.getText()); // ID 저장
            JOptionPane.showMessageDialog(null, action + "되었습니다.");
        } else {
            JOptionPane.showMessageDialog(null, action + "되지 않았습니다.");
        }
    }

    private void printFields() {
        System.out.println("회원 ID: " + memberIdField.getText());
        System.out.println("이름: " + nameField.getText());
        System.out.println("생년월일: " + birthField.getText());
        System.out.println("연락처: " + phoneField.getText());
        System.out.println("이메일: " + emailField.getText());
        System.out.println("가입일: " + joinDateField.getText());
        System.out.println("주소: " + addressField.getText());
        System.out.println("대여 가능 여부: " + (yesRadioButton.isSelected() ? "Yes" : "No"));
        System.out.println("연체 횟수: " + overdueCountField.getText());
        System.out.println("--------------------------------------------------");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MemberManagementFrame().setVisible(true);
        });
    }
}





