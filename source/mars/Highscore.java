package mars;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Highscore implements Serializable {
	private static final long serialVersionUID = 1L;

	public class Record implements Serializable{
		private static final long serialVersionUID = 1L;
		
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
		deserialize();
		
		Collections.sort(list, new Comparator<Record>(){
			public int compare(Record r1, Record r2){
				return r1.getDistance() < r2.getDistance() ? 1 : -1;
			}
		});
	}

	public void serialize(){
		String name = "save.dat";
			try {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name));
				out.writeObject(list);
				out.close();
			} catch (IOException e){
				System.err.println("Error saving toplist...");
				e.printStackTrace();
			}
	}
	
	@SuppressWarnings("unchecked")
	public void deserialize(){
		String name = "save.dat";
		String filePath = System.getProperty("user.dir") + "\\" + name;
		if(new File(filePath).exists()){
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(name));
				Object o = ois.readObject();
				if(o instanceof java.util.ArrayList<?>)
					list = (ArrayList<Record>) o;
				ois.close();
			} catch(IOException ex){
				System.err.println("Error loading toplist...");
				ex.printStackTrace();
			} catch(ClassNotFoundException ex){
				System.err.println("Error loading toplist...");
				ex.printStackTrace();
			}
		}
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
}
