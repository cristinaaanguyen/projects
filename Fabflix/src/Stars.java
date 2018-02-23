import java.util.ArrayList;


public class Stars {
			
		private String id;
		
		private String name;

		private int birthYear;

		private ArrayList<String> Movies;
		
	public Stars(){
			
		}
		
		public Stars(String name, String id, int birthYear, String movie) {
			Movies = new ArrayList<String>();
			this.name = name;
			this.id = id;
			this.birthYear = birthYear;
			this.Movies.add(movie);
		}
		
		public Stars(String name, int birthYear, String movie) {
			Movies = new ArrayList<String>();
			this.name = name;
			this.birthYear = birthYear;
			this.Movies.add(movie);
		}
		
		public Stars(String name, int birthYear) {
			this.name = name;
			this.birthYear = birthYear;
		}
		
		public Stars(String name, String movie) {
			Movies = new ArrayList<String>();
			this.name = name;
			if (movie != null)
				this.Movies.add(movie);
		}
		
		public Stars(String id, String name, String movie) {
			Movies = new ArrayList<String>();
			this.name = name;
			this.id = id;
			if (movie != null)
				this.Movies.add(movie);
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getBirthYear() {
			return birthYear;
		}

		public void setBirthYear(int birthYear) {
			this.birthYear = birthYear;
		}

		
		public ArrayList<String> getMovies(){
			return Movies;
		}
		
		public void addGenre(String movie) {
			Movies.add(movie);
		}
		
		public void removeGenre(String movie) {
			if(Movies.contains(movie))
				Movies.remove(movie);
		}
		
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Star Details - ");
			sb.append("Id:"+ getId());
			sb.append(", ");
			sb.append("Name:" + getName());
			sb.append(", ");
			sb.append("BirthYear:" + getBirthYear());
			sb.append(", ");
			sb.append("Movies:" + getMovies());
			sb.append(".");
			
			return sb.toString();
		}
		
	

}
