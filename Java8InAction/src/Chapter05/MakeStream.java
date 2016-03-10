package Chapter05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class MakeStream {
	public static void main(String[] args) {
		
		//1. ������ ��Ʈ�� �����
		Stream<String> stream = Stream.of("a","b","c");
		
		//2. �迭�� ��Ʈ�� �����
		int[] numbers = {1,2,3,4};
		int sum = Arrays.stream(numbers).sum();
		
		//3. ���Ϸ� ��Ʈ�� �����
		long uniqueWords = 0;
		try(Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir")+"\\src\\Chapter05\\data.txt"), Charset.defaultCharset())){
			uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" ")))
						       .distinct()
						       .count();
		}catch(IOException e){
			
		}
		System.out.println("�ܾ� ���� : "+uniqueWords);
		
		//4. �Լ��� ���ѽ�Ʈ�� �����
		//4-1. iterator
		Stream.iterate(0, n -> n+2)
			  .limit(10)
			  .forEach(System.out::print);
		
		System.out.println("");
		//4-2. generate
		Stream.generate(Math::random)
			  .limit(5)
			  .forEach(System.out::print);
		
	}
}
