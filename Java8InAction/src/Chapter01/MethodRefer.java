package Chapter01;

import java.io.File;
import java.io.FileFilter;

public class MethodRefer {
	public static void main(String[] args) {
		
		//1. ����
		File[] hiddenFiles = new File("c:/.").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.isHidden();
			}
		});
		
		//2. �ڹ�8

		hiddenFiles = new File("c:/.").listFiles(File::isHidden); //�޼��� ���۷��� :: -> FileŬ������ isHidden�޼��带 parameter�� ����
		
		for(File hf : hiddenFiles){
			System.out.println(hf.toString());
		}
	}
}

/*
 �͸��� �̳�Ŭ���� ( Anonymous inner class)
  : �߻�Ŭ������ �������̽��� ��ӹ޾� �� �ѹ��� ����ϴ� ��� ���
  : new �������̽���(){
  	 �޼���������̵�(){}
  }
*/
