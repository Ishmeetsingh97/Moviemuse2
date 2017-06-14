package me.warmachine.Moviemuse.entity;

public class Result extends Results<Movie> {

    public enum SortCriteria {
        POPULARITY("popularity.desc"), RATING("vote_average.desc"), FAVORITES("favorites");
        public final String str;
        SortCriteria(String str) {
            this.str = str;
        }
        public int getId() {
            return this.str.hashCode();
        }
        public String toString() {
            return this.str;
        }
    }

}
