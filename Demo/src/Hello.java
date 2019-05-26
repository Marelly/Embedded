import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Hello {

	public static void main(String[] args) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		arr.add(12);
		arr.add(13);
		arr.add(14);
		arr.add(2, 15);
		System.out.println(arr);
		arr.remove(1);
		System.out.println(arr);
		
		for (Integer i: arr) {
			System.out.println(i);
		}
		
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Sunday");
		map.put(2, "Monday");
		map.put(3, "Tuesday");
		System.out.println(map);
		System.out.println("1: " + map.get(1));
		// map.clear();
		
		for (String s: map.values()) {
			System.out.println(s);
		}
		for (Integer i: map.keySet()) {
			System.out.println(i);
		}
		
	}
}
