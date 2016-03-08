package Chapter03;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
//정적import
import static java.util.Comparator.comparing;

public class MixWithLamda {
	
	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple(80,"green"), new Apple(155, "green"), new Apple(120, "red"), new Apple(120, "blue"));
		
		//1. 무게를 내림차순으로 정렬
		inventory.sort(comparing(Apple::getWeight));
		System.out.println("1. "+ inventory.toString());
		
		//2. 무게를 오름차순으로 정렬
		inventory.sort(comparing(Apple::getWeight).reversed());
		System.out.println("2. "+ inventory.toString());
		
		//3. 두번째 조건으로 정렬
		inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
		System.out.println("3. "+ inventory.toString());
	}
	
	//Apple클래스
	public static class Apple {
		private int weight = 0;
		private String color = "";

		public Apple(int weight, String color){
			this.weight = weight;
			this.color = color;
		}

		public Integer getWeight() {
			return weight;
		}

		public void setWeight(Integer weight) {
			this.weight = weight;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String toString() {
			return "Apple{" +
					"color='" + color + '\'' +
					", weight=" + weight +
					'}';
		}
	}
	
	
	
}


