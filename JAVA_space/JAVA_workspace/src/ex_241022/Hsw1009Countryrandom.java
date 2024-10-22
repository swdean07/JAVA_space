package ex_241022;

import java.util.Random;

public class Hsw1009Countryrandom {

	    public static void main(String[] args) {

	        Sports[] sportsArray = new Sports[3];
	        sportsArray[0] = new Sports("축구", new String[]{"한국", "브라질", "스페인"}, new String[]{"축구공", "유니폼", "축구화"});
	        sportsArray[1] = new Sports("수영", new String[]{"한국", "미국", "호주"}, new String[]{"수영복", "수경(물안경)", "수모(수영 모자)"});
	        sportsArray[2] = new Sports("야구", new String[]{"한국", "미국", "일본"}, new String[]{"야구공", "배트", "글러브"});


	        Random random = new Random();
	        int randomIndex = random.nextInt(sportsArray.length);


	        System.out.println("추천된 스포츠:");
	        sportsArray[randomIndex].introduce();
	    }
	}