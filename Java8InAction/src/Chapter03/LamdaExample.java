package Chapter03;

public class LamdaExample {
	public static void main(String[] args) {
		//����ǥ����
		Runnable r1 = () -> System.out.println("Hello1");
		
		//�͸�Ŭ���� (���� �ڹ� ���)
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello2");
			}
		};
		
		process(r1);
		process(r2);
		
		//���� ����ǥ�������� ����
		process(() -> System.out.println("Hello3"));
		
	}
	
	public static void process(Runnable r){
		r.run();
	}
}
