package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class BookManagementFrame extends JFrame {
    private JTextField bookIdField, titleField, authorField, publisherField, yearField, categoryField, stockField;
    private JTextArea descriptionArea;
    private Map<String, Book> bookMap; // 도서 정보를 저장할 Map (도서 ID를 key로)

    public BookManagementFrame() {
        setTitle("도서 정보 관리");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 도서 정보 저장을 위한 Map 초기화
        bookMap = new HashMap<>();

        // UI 구성
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        // 필드 초기화
        bookIdField = new JTextField(15);
        titleField = new JTextField(15);
        authorField = new JTextField(15);
        publisherField = new JTextField(15);
        yearField = new JTextField(15);
        categoryField = new JTextField(15);
        stockField = new JTextField(15);
        descriptionArea = new JTextArea(3, 15);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        // 라벨 및 입력 필드 추가
        panel.add(new JLabel("도서 ID:"));
        panel.add(bookIdField);
        panel.add(new JLabel("제목:"));
        panel.add(titleField);
        panel.add(new JLabel("저자:"));
        panel.add(authorField);
        panel.add(new JLabel("출판사:"));
        panel.add(publisherField);
        panel.add(new JLabel("출판 연도:"));
        panel.add(yearField);
        panel.add(new JLabel("카테고리:"));
        panel.add(categoryField);
        panel.add(new JLabel("재고 수량:"));
        panel.add(stockField);
        panel.add(new JLabel("설명:"));
        panel.add(new JScrollPane(descriptionArea));

        // 버튼 패널
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
        
        
        // 저장 버튼 이벤트 처리
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String bookId = bookIdField.getText();
                    if (isBookIdDuplicate(bookId)) {
                        JOptionPane.showMessageDialog(null, "도서 정보가 중복되어 저장되지 않았습니다.", "중복 오류", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(null, "도서 정보를 저장하시겠습니까?", "도서 저장 확인", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            Book newBook = new Book(bookId, titleField.getText(), authorField.getText(),
                                    publisherField.getText(), yearField.getText(), categoryField.getText(),
                                    stockField.getText(), descriptionArea.getText());
                            bookMap.put(bookId, newBook);

                            JOptionPane.showMessageDialog(null, "도서 정보가 저장되었습니다.");
                            clearFields();
                            printFields();
                        }
                    }
                }
            }
        });

        // 추가 버튼 이벤트 처리
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    String bookId = bookIdField.getText();
                    if (isBookIdDuplicate(bookId)) {
                        JOptionPane.showMessageDialog(null, "도서 정보가 중복되어 추가되지 않았습니다.", "중복 오류", JOptionPane.WARNING_MESSAGE);
                    } else {
                        int confirm = JOptionPane.showConfirmDialog(null, "도서 정보를 추가하시겠습니까?", "도서 추가 확인", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            // 도서 정보 저장
                            Book newBook = new Book(bookId, titleField.getText(), authorField.getText(),
                                    publisherField.getText(), yearField.getText(), categoryField.getText(),
                                    stockField.getText(), descriptionArea.getText());
                            bookMap.put(bookId, newBook);

                            JOptionPane.showMessageDialog(null, "도서 정보가 추가되었습니다.");
                            clearFields();
                            printFields();
                        }
                    }
                }
            }
        });

        // 수정 버튼 이벤트 처리
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText();
                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "도서 ID를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                } else if (!bookMap.containsKey(bookId)) {
                    JOptionPane.showMessageDialog(null, "해당 도서 정보가 존재하지 않아 수정할 수 없습니다.", "오류", JOptionPane.WARNING_MESSAGE);
                } else {
                    Book book = bookMap.get(bookId);
                    // 기존 도서 정보를 불러와서 입력 필드에 표시
                    titleField.setText(book.getTitle());
                    authorField.setText(book.getAuthor());
                    publisherField.setText(book.getPublisher());
                    yearField.setText(book.getYear());
                    categoryField.setText(book.getCategory());
                    stockField.setText(book.getStock());
                    descriptionArea.setText(book.getDescription());

                    int confirm = JOptionPane.showConfirmDialog(null, "도서 정보를 수정하시겠습니까?", "도서 수정 확인", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // 수정된 정보로 도서 정보 업데이트
                        book.setTitle(titleField.getText());
                        book.setAuthor(authorField.getText());
                        book.setPublisher(publisherField.getText());
                        book.setYear(yearField.getText());
                        book.setCategory(categoryField.getText());
                        book.setStock(stockField.getText());
                        book.setDescription(descriptionArea.getText());

                        JOptionPane.showMessageDialog(null, "도서 정보가 수정되었습니다.");
                        printFields();
                    }
                }
            }
        });

        // 삭제 버튼 이벤트 처리
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText();
                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "도서 ID를 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
                } else if (!bookMap.containsKey(bookId)) {
                    JOptionPane.showMessageDialog(null, "해당 도서 정보가 존재하지 않아 삭제할 수 없습니다.", "오류", JOptionPane.WARNING_MESSAGE);
                } else {
                    int confirm = JOptionPane.showConfirmDialog(null, "도서 정보를 삭제하시겠습니까?", "도서 삭제 확인", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        bookMap.remove(bookId);
                        JOptionPane.showMessageDialog(null, "도서 정보가 삭제되었습니다.");
                        clearFields();
                        printFields();
                    }
                }
            }
        });
    }

    // 필드 유효성 검사
    private boolean validateFields() {
        if (bookIdField.getText().isEmpty() || titleField.getText().isEmpty() || authorField.getText().isEmpty() ||
            publisherField.getText().isEmpty() || yearField.getText().isEmpty() || categoryField.getText().isEmpty() ||
            stockField.getText().isEmpty() || descriptionArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "모든 내용을 입력해주세요.", "입력 오류", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // 도서 ID 중복 여부 체크
    private boolean isBookIdDuplicate(String bookId) {
        return bookMap.containsKey(bookId);
    }

    // 입력 필드 초기화
    private void clearFields() {
        bookIdField.setText("");
        titleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        yearField.setText("");
        categoryField.setText("");
        stockField.setText("");
        descriptionArea.setText("");
    }

    // 필드 내용 콘솔에 출력 함수
    private void printFields() {
        System.out.println("도서 ID: " + bookIdField.getText());
        System.out.println("제목: " + titleField.getText());
        System.out.println("저자: " + authorField.getText());
        System.out.println("출판사: " + publisherField.getText());
        System.out.println("출판 연도: " + yearField.getText());
        System.out.println("카테고리: " + categoryField.getText());
        System.out.println("재고 수량: " + stockField.getText());
        System.out.println("설명: " + descriptionArea.getText());
        System.out.println("---------------");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookManagementFrame().setVisible(true));
    }

    // Book 클래스 정의
    public static class Book {
        private String bookId, title, author, publisher, year, category, stock, description;

        public Book(String bookId, String title, String author, String publisher, String year, String category, String stock, String description) {
            this.bookId = bookId;
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.year = year;
            this.category = category;
            this.stock = stock;
            this.description = description;
        }

        public String getBookId() {
            return bookId;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getPublisher() {
            return publisher;
        }

        public String getYear() {
            return year;
        }

        public String getCategory() {
            return category;
        }

        public String getStock() {
            return stock;
        }

        public String getDescription() {
            return description;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}





