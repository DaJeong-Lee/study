package Chapter03;

public class LamdaExample {
	public static void main(String[] args) {
		//람다표현식
		Runnable r1 = () -> System.out.println("Hello1");
		
		//익명클래스 (기존 자바 방식)
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello2");
			}
		};
		
		process(r1);
		process(r2);
		
		//직접 람다표현식으로 전달
		process(() -> System.out.println("Hello3"));
		
	}
	
	public static void process(Runnable r){
		r.run();
	}
}
