package at.ac.fhcampuswien.enumerations;

public enum Endpoint {
    EVERYTHING, TOPHEADLINES;

    public String getEndpoint () {
        return switch (this) {
            case EVERYTHING -> "everything";
            case TOPHEADLINES -> "top-headlines";
            default -> "everything";
        };
    }
}
