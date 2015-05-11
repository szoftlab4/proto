package mars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Highscore {
	
	public class Record{
		private String name;
		private int distance;
		
		public Record(String name, int distance){
			this.name = name;
			this.distance = distance;
		}
		public String getName(){
			return name;
		}
		public int getDistance(){
			return distance;
		}
	}
	
	static ArrayList<Record> list;
	
	public Highscore(){
		list = new ArrayList<Record>();
		
		Collections.sort(list, new Comparator<Record>(){
			public int compare(Record r1, Record r2){
				return r1.getDistance() < r2.getDistance() ? 1 : -1;
			}
		});
	}
	
	public void add(String name, int distance){
		list.add(new Record(name,distance));
		
		Collections.sort(list, new Comparator<Record>(){
			public int compare(Record r1, Record r2){
				return r1.getDistance() < r2.getDistance() ? 1 : -1;
			}
		});
	}

	public void clear(){
		list.clear();
	}
	
	public void kiir(){
		for(int i = 0; i < list.size(); i++){
			Record a = list.get(i);
			System.out.println(a.getName() + " " + a.getDistance());
		}
	}
}
