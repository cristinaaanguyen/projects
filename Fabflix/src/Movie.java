import java.util.ArrayList;

public class Movie {
	
	private String id;
	
	private String title;

	private int year;
	
	private String director;

	private ArrayList<String> genres;
	
public Movie(){
		
	}
	
	public Movie(String title, String id, int year, String director, String genre) {
		genres = new ArrayList<String>();
		this.title = title;
		this.id = id;
		this.year  = year;
		this.director = director;
		this.genres.add(genre);
	}
	
	public Movie(String title, int year, String director, String genre) {
		genres = new ArrayList<String>();
		this.title = title;
		this.year  = year;
		this.director = director;
		if (genre != null)
			this.genres.add(genre);	
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}


	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}
	
	public ArrayList<String> getGenres(){
		return genres;
	}
	
	public void addGenre(String genre) {
		genres.add(genre);
	}
	
	public void removeGenre(String genre) {
		if(genres.contains(genre))
			genres.remove(genre);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Movie Details - ");
		sb.append("Id:" + getId());
		sb.append(", ");
		sb.append("Title:" + getTitle());
		sb.append(", ");
		sb.append("Year:" + getYear());
		sb.append(", ");
		sb.append("Director:" + getDirector());
		sb.append(", ");
		sb.append("Genres:" + getGenres());
		sb.append(".");
		
		return sb.toString();
	}
	
}
