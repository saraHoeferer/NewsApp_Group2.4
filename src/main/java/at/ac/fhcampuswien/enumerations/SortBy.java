package at.ac.fhcampuswien.enumerations;

public enum SortBy {
    RELEVANCY, POPULARITY, PUBLISHED_AT, NONE;

    public String getSort () {
        return switch (this) {
            case RELEVANCY -> "relevancy";
            case POPULARITY -> "popularity";
            case PUBLISHED_AT -> "publishedAt";
            case NONE -> "";
        };
    }

}
