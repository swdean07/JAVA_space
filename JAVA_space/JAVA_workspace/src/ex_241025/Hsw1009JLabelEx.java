package ex_241025;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Hsw1009JLabelEx extends JFrame {
    public Hsw1009JLabelEx() {
        setTitle("단어장 프로그램");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("");
        label.setBounds(50, 50, 300, 30);
        add(label);

        JButton appleButton = new JButton("Apple");  
        appleButton.setBounds(50, 150, 80, 30);
        add(appleButton);

        JButton bananaButton = new JButton("Banana");
        bananaButton.setBounds(150, 150, 80, 30);
        add(bananaButton);

        JButton houseButton = new JButton("House");
        houseButton.setBounds(250, 150, 80, 30);
        add(houseButton);

        JButton carButton = new JButton("Car");
        carButton.setBounds(350, 150, 80, 30);
        add(carButton);

        // 라벨 상태 복구 버튼
        JButton resetButton = new JButton("초기화");
        resetButton.setBounds(450, 150, 80, 30);
        add(resetButton);

        appleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Apple은 사과입니다.");
            }
        });

        bananaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Banana는 바나나입니다.");
            }
        });

        houseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("House는 집입니다.");
            }
        });

        carButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Car는 차입니다.");
            }
        });

        // 초기화 버튼 액션
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("초기화 상태입니다.");
            }
        });
    }

    public static void main(String[] args) {
        Hsw1009JLabelEx frame = new Hsw1009JLabelEx();
        frame.setVisible(true);
    }
}


