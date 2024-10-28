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

public class Hsw1009Graphics extends JFrame {
    public Hsw1009Graphics() {
        setTitle("마우스로 도형 그리기 구현");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel buttonPanel = new JPanel();
        
        MyPanel drawingPanel = new MyPanel();
        setContentPane(drawingPanel);
        setSize(300, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Hsw1009Graphics();
    }

    class MyPanel extends JPanel {
        private Vector<Point> vStart = new Vector<Point>();
        private Vector<Point> vEnd = new Vector<Point>();
        private boolean isRectangleMode = false;
        private boolean isDrawingEnabled = true;

        public MyPanel() {
            setLayout(null);


            JButton lineButton = new JButton("직선");
            lineButton.setBounds(10, 10, 80, 30);
            lineButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isRectangleMode = false;
                    isDrawingEnabled = true;
                }
            });

            JButton rectangleButton = new JButton("사각형");
            rectangleButton.setBounds(100, 10, 80, 30);
            rectangleButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    isRectangleMode = true;
                    isDrawingEnabled = true;
                }
            });

            JButton resetButton = new JButton("초기화");
            resetButton.setBounds(190, 10, 80, 30);
            resetButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    vStart.clear(); 
                    vEnd.clear(); 
                    isDrawingEnabled = false;
                    repaint();
                }
            });

            add(lineButton);
            add(rectangleButton);
            add(resetButton);

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

