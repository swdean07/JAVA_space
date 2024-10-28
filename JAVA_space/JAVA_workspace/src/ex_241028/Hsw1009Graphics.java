package ex_241028;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

public class Hsw1009Graphics extends JFrame {
    public Hsw1009Graphics() {
        setTitle("마우스로 도형 그리기 구현");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 버튼 패널 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // 가운데 정렬, 간격 10 설정

        // 그리기 패널 설정
        MyPanel drawingPanel = new MyPanel();

        // 버튼 생성
        JButton lineButton = new JButton("직선");
        JButton rectangleButton = new JButton("사각형");
        JButton resetButton = new JButton("초기화");

        // 버튼 이벤트 리스너
        lineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setRectangleMode(false);
                drawingPanel.enableDrawing(true);
            }
        });

        rectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.setRectangleMode(true);
                drawingPanel.enableDrawing(true);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawingPanel.resetDrawing();
            }
        });

        // 버튼 패널에 버튼 추가
        buttonPanel.add(lineButton);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(resetButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // 위, 아래 간격 추가

        // 프레임에 버튼 패널과 그리기 패널 추가
        add(buttonPanel, "North");
        add(drawingPanel, "Center");

        setSize(400, 400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Hsw1009Graphics();
    }

    class MyPanel extends JPanel {
        private Vector<Point> vStart = new Vector<Point>();
        private Vector<Point> vEnd = new Vector<Point>();
        private boolean isRectangleMode = true;
        private boolean isDrawingEnabled = true;

        public MyPanel() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    if (isDrawingEnabled) { // 그리기 가능 상태일 때만 동작
                        Point startP = e.getPoint();
                        vStart.add(startP);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    if (isDrawingEnabled) { // 그리기 가능 상태일 때만 동작
                        Point endP = e.getPoint();
                        vEnd.add(endP);
                        repaint();
                    }
                }
            });
        }

        public void setRectangleMode(boolean isRectangle) {
            this.isRectangleMode = isRectangle;
        }

        public void enableDrawing(boolean enable) {
            this.isDrawingEnabled = enable;
        }

        public void resetDrawing() {
            vStart.clear();
            vEnd.clear();
            isDrawingEnabled = false;
            repaint();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);

            for (int i = 0; i < vStart.size(); i++) {
                Point s = vStart.elementAt(i);
                Point e = vEnd.elementAt(i);

                if (isRectangleMode) {
                    int x = Math.min(s.x, e.x);
                    int y = Math.min(s.y, e.y);
                    int width = Math.abs(s.x - e.x);
                    int height = Math.abs(s.y - e.y);
                    g.drawRect(x, y, width, height);
                } else {
                    g.drawLine(s.x, s.y, e.x, e.y);
                }
            }
        }
    }
}

