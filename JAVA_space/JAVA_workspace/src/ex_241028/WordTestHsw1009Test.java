package ex_241028;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WordTestHsw1009Test extends JFrame {
	private JTextField wordField;
	private JTextField meaningField;
	private JPanel wordPanel;

	private Map<String, JLabel> wordLabelMap;
	private Map<String, String> wordMeaningLabelMap;
	private Random random;

	public WordTestHsw1009Test() {
		setTitle("단어 프로그램 V 1.0.0");
		setSize(800, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		wordLabelMap = new HashMap<>();
		wordMeaningLabelMap = new HashMap<>();
		random = new Random();

		setLayout(new BorderLayout());

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());

		inputPanel.add(new JLabel("단어: "));
		wordField = new JTextField(10);
		inputPanel.add(wordField);

		inputPanel.add(new JLabel("뜻: "));
		meaningField = new JTextField(10);
		inputPanel.add(meaningField);

		JButton addButton = new JButton("단어추가");
		inputPanel.add(addButton);

		JButton randomButton = new JButton("랜덤 단어 뽑기");
		inputPanel.add(randomButton);

		JButton saveButton = new JButton("Save");
		inputPanel.add(saveButton);

		JButton loadButton = new JButton("Load");
		inputPanel.add(loadButton);

		wordPanel = new JPanel();
		wordPanel.setLayout(new FlowLayout());

		add(inputPanel, BorderLayout.NORTH);
		add(wordPanel, BorderLayout.CENTER);

		addButton.addActionListener(e -> addWord());
		randomButton.addActionListener(e -> showRandomWord());
		saveButton.addActionListener(e -> saveWordsToFile());
		loadButton.addActionListener(e -> loadWordsFromFile());

		setVisible(true);
	}

	private void saveWordsToFile() {
		File file = new File("c:\\Temp\\wordTestCsv.csv");
		try (FileWriter fw = new FileWriter(file)) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : wordMeaningLabelMap.entrySet()) {
				sb.append(entry.getKey()).append(",").append(entry.getValue()).append(",");
			}

			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1);
			}
			fw.write(sb.toString());
			JOptionPane.showMessageDialog(null, "단어 목록이 CSV 파일에 저장되었습니다.", "저장 성공", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "파일 저장 중 오류가 발생했습니다.", "저장 오류", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void showRandomWord() {
		// 맵에, 단어가 추가 안된 상황,
		if (wordLabelMap.isEmpty()) {
			JOptionPane.showMessageDialog(null, "단어가 입력 안됨", "먼저, 단어를 추가해주세요", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// 랜덤하게 하나만 출력, 나머지 단어는 모두 가리기.
		// wordLabelMap, key : String, value: 각 단어가 표시된 라벨
		for (JLabel jLabel : wordLabelMap.values()) {
			jLabel.setVisible(false);
		}

		// 랜덤하게 단어 하나 선택.
		// 맵 -> 리스트 형식으로 변환.
		// wordLabelMap.keySet(), 영어 단어 , car, -> car 를 출력하는 라벨을 생성.
		List<String> words = new ArrayList<String>(wordLabelMap.keySet());
		// words 에는 맵에 등록된 영어 단어의 영어만 전부다 리스트로 변환.
		String randomWord = words.get(random.nextInt(words.size()));

		// 선택된 단어만 보이게 하는 설정.
		wordLabelMap.get(randomWord).setVisible(true);
	}

	private void addWord() {
		// 입력된 단어 가져오기.
		// 전역으로 설정된, 단어 입력란 참조형 변수를 이용해서 가지고 올 예정.
		// 1
		String word = wordField.getText();
		System.out.println("addWord() , word 확인 : " + word);

		// 입력된 뜻 가져오기.
		// 2
		String meaning = meaningField.getText();

		// 기본 유효성 체크, 단어와 뜻에 입력이 된 경우만 로직을 진행.
		// 3
		if (!word.isEmpty() && !meaning.isEmpty()) {
			// 임시로 출력할 JLabel를 만들어서, 여기에 단어를 붙이는 과정.
			// 임시 라벨 생성.
			// 1
			JLabel wordLabel = new JLabel(word);
			// 마우스 커서를 손 모양으로 변경하는 기능. 옵션,
			//
			wordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

			// 이벤트 리스너 추가하기. , 마우스 클릭시 뜻을 보여주는 기능.
			// addMouseListener , 함수형 인터페이스가 아님. 간단히 추상 메서드가 2개이상.
			// 인터페이스 그냥 구현하면, 사용 안하는 추상메서드 모두를 일단 재정의
			// 조금 더 간편히 하기 위해서, 어댑터 클래스 이용하면, 사용할 추상 메서드만,
			// 이용 가능함.
			// 익명 클래스로 구현.
			// new 부모 클래스의(인터페이스, 클래스) {}
			// 3
			wordLabel.addMouseListener(new MouseAdapter() {
				// 상태 변수, 설정, 뜻을 보여주는 상태.
				private boolean showMeaning = false;

				@Override
				public void mouseClicked(MouseEvent e) {
					if (!showMeaning) {
						wordLabel.setText("뜻 : " + meaning);
						showMeaning = true;
					} else {
						wordLabel.setText(word);
						showMeaning = false;
					}
				}
			});
			// Map 메모리에 저장하는 로직.
			// 4
			wordLabelMap.put(word, wordLabel);
			wordMeaningLabelMap.put(word, meaning);

			// 패널에 라벨 추가.
			// wordPanel : 단어를 출력하는 패널 공간, -> JLabel 붙이고 있음.
			wordPanel.add(wordLabel);
			// 레이아웃 업데이트
			wordPanel.revalidate();
			// 패널 그림을 다시 그리기.
			wordPanel.repaint();

		} // if
			// 유효성 통과 안될 때, 경고창 보여주기.
			// 영어 , 단어 뜻을 입력을 안될 경우
			// 4
		else {
			// 자바 버전에서, 간단한 다이얼 로그 창, 경고창 alert 같은 ui 사용
			JOptionPane.showMessageDialog(null, "단어와 뜻을 모두 입력해주세요.", "입력오류: 모두 입력해주세요", JOptionPane.ERROR_MESSAGE);
		}

	} // addWord()

	private void addWordLabel(String word, String meaning) {
		JLabel wordLabel = new JLabel(word);
		wordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

		wordLabel.addMouseListener(new MouseAdapter() {
			private boolean showMeaning = false;

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!showMeaning) {
					wordLabel.setText("뜻 : " + meaning);
					showMeaning = true;
				} else {
					wordLabel.setText(word);
					showMeaning = false;
				}
			}
		});

		wordLabelMap.put(word, wordLabel);
		wordPanel.add(wordLabel);
		wordPanel.revalidate();
		wordPanel.repaint();
	}

	private void loadWordsFromFile() {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			JOptionPane.showMessageDialog(null, "파일이 존재하지 않습니다.", "로드 오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		wordPanel.removeAll();
		wordLabelMap.clear();
		wordMeaningLabelMap.clear();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				for (int i = 0; i < parts.length; i += 2) {
					if (i + 1 < parts.length) {
						String word = parts[i].trim();
						String meaning = parts[i + 1].trim();
						if (!wordMeaningLabelMap.containsKey(word)) {
							addWordLabel(word, meaning);
							wordMeaningLabelMap.put(word, meaning);
						}
					}
				}
			}
			JOptionPane.showMessageDialog(null, "단어 목록이 로드되었습니다.", "로드 성공", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "파일 로드 중 오류가 발생했습니다.", "로드 오류", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		wordPanel.revalidate();
		wordPanel.repaint();
	}

	private static final String FILE_PATH = "c:\\Temp\\wordTestCsv.csv";

	public static void main(String[] args) {
		new WordTestHsw1009Test();
	}
}

