
import java.util.*;
public class Song implements Comparable<Song>{
	private String title;
	private int likes;
	private int id = 0;
	/* The String ids is used to store the id of each Song, in order to be certain of each one's uniqueness
	 * The Random class is also used for the id of a Song*/
	Random r = new Random();
	static String ids = "";
	
	// Default Constructor
	public Song() {
		int a = r.nextInt(9999);
		while (!uniqueID(Integer.toString(a)) || a<=0) {
			a = r.nextInt(9999);
		}
		ids += Integer.toString(a) + " ";
		//this.id = a;
		setID(id);
		setTitle("Unidentified");
		setLikes(0);
	}
	
	// Constructor with arguments
	public Song(int id, String title, int likes) {
		if (ids.contains(Integer.toString(id)))
			return;
		ids += Integer.toString(id) + " ";
		this.id = id;
		setTitle(title);
		setLikes(likes);
	}
	
	// Getters
	public int getID() {
		return id;
	}
	public int getLikes() {
		return likes;
	}
	public String getTitle() {
		return title;
	}
	
	
	// Setters
	public void setID(int idi) {
		if (idi>=1 && idi<=9999 && uniqueID(Integer.toString(idi))) { 
			this.id = idi;
		}
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public void setTitle(String title) {
		if (title.length()<=80)
			this.title = title;
	}

	
	// Returns false if an id is identical with another, true otherwise
	public static boolean uniqueID(String a) {
		StringTokenizer st = new StringTokenizer(ids);
		while (st.hasMoreTokens()) {
			if (a.equalsIgnoreCase(st.nextToken()))
				return false;
		}
		return true;
	}
	
	
	/* If this.likes > o.likes return 1
	 * If this.likes < o.likes return -1
	 * If this.likes == o.likes 
	 *  	check the alphabetical order of their titles
	 *      and return their difference
	 *      	positive if this.title > o.title
	 *      	negative if this.title < o.title
	 *      	0 	 	 if this.title == o.title
	 * If their likes and names are the same return 0
	 * So if this has bigger priority than o return something positive
	 * If it's the opposite case, return something negative
	 * If the have the same priority, return 0*/
	@Override
	public int compareTo(Song o) {
		if (o.getLikes()==this.getLikes())
			return -this.getTitle().compareToIgnoreCase(o.getTitle());
		return this.getLikes()>o.getLikes() ? 1 : -1;
	}
}
