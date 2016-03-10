package Chapter04;

import java.util.Arrays;
import java.util.List;
import static Chapter04.Dish.menu;
import static java.util.stream.Collectors.toList;

public class StreamStart {
	public static void main(String[] args) {
		List<String> threeHighCalDishNames = menu.stream()
				                           .filter(d -> d.getCalories() > 300)
										   .map(Dish::getName)
										   .limit(3)
										   .collect(toList());
										
       System.out.println(threeHighCalDishNames);
	}
}
