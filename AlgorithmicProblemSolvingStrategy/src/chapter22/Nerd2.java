package chapter22;

import java.util.Map.Entry;

import javax.swing.text.html.parser.Entity;

import java.util.TreeMap;

public class Nerd2 {
	TreeMap<Integer, Integer> coords = new TreeMap<>();
	
	/**
	 * 새로운점 (x,y)가 기존의 다른 점들에 지배당하는지 확인한다
	 * @param x
	 * @param y
	 * @return
	 */
	boolean isDominated(int x, int y){
		// x보다 오른쪽에 있는 점 중 가장 왼쪽 점을 찾는다.
		Entry<Integer, Integer> it = coords.ceilingEntry(x);
		
		//그런 점이 없으면 x,y는 지배 당하지 않는다.
		if (it == coords.lastEntry() || it == null) {
			return false;
		}
		
		// y가 it점의 y값보다 작으면 지배당하는 것이다. 
		return y < it.getValue();
	}
	
	/**
	 * 새로운점 (x,y)에 지배당한 점들을 삭제하는 함수 
	 * @param x
	 * @param y
	 */
	void removeDominated(int x, int y){
		// x,y의 바로 왼쪽에 있는 점을 찾는다.
		Entry<Integer, Integer> it = coords.floorEntry(x);
		
		//그런 점이 없으면 종료
		if (it == coords.lastEntry() || it == null) {
			return ;
		}
		
		
		while (true) {
			//찾는 점이 y보다 크다면 종료 (해당 점이 새로운 점의 y보다 크면 그 왼쪽 점들도 그럴것
			if(it.getValue() > y) break;
			//it가 맨 coords의 맨 앞 값이면 it만 지우고 종료
			if(it == coords.firstEntry() || it == null){
				coords.remove(it.getKey());
				break;
			}else{
				//왼쪽으로 it를 옮기고, it를 지운다.
				Entry<Integer, Integer> jt = coords.floorEntry(it.getKey());
				coords.remove(it.getKey());
				it = jt;
			}
		}
	}
	
	/**
	 * 새점 (x,y)가 추가되었을 때 coords를 갱신하고, 다른 점에 지배당하지 않는 점들의 개수를 반환한다.
	 * @param x
	 * @param y
	 * @return
	 */
	public int registered(int x, int y){
		//x,y가 이미 지배당하는 경우에는 그냥 x,y를 버린다.
		if(isDominated(x, y))
			return coords.size();
		
		//기존에 있던 점 중 x,y에 지배당하는 점들을 지운다.
		removeDominated(x, y);
		
		//treemap에 등록한다.
		coords.put(x, y);
		System.out.println(coords.toString());
		return coords.size();
	}
	
	public static void main(String[] args) {
		Nerd2 nerd2 = new Nerd2();
		nerd2.registered(72, 50);
		nerd2.registered(57, 67);
		nerd2.registered(74, 55); //(72, 50) 은 제거됨 
		nerd2.registered(64, 60);
	}
}
