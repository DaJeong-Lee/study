package Chapter01;

import java.io.File;
import java.io.FileFilter;

public class MethodRefer {
	public static void main(String[] args) {
		
		//1. 기존
		File[] hiddenFiles = new File("c:/.").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		
		//2. 자바8

		hiddenFiles = new File("c:/.").listFiles(File::isHidden); //메서드 레퍼런스 :: -> File클래스의 isHidden메서드를 parameter로 던짐
		
		for(File hf : hiddenFiles){
			System.out.println(hf.toString());
		}
	}
}

/*
 익명의 이너클래스 ( Anonymous inner class)
  : 추상클래스나 인터페이스를 상속받아 딱 한번만 사용하는 경우 사용
  : new 인터페이스명(){
  	 메서드오버라이딩(){}
  }
*/
