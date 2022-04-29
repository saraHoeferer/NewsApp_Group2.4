package at.ac.fhcampuswien.enumerations;

public enum Category {
    BUSINESS, ENTERTAINMENT, GENERAL, HEALTH, SCIENCE, SPORTS, TECHNOLOGY, NONE;

    public String getCategory () {
        return switch (this) {
            case BUSINESS -> "business";
            case ENTERTAINMENT -> "entertainment";
            case GENERAL -> "general";
            case HEALTH -> "health";
            case SCIENCE -> "science";
            case SPORTS -> "sports";
            case TECHNOLOGY -> "technology";
            case NONE -> "";
        };
    }
}
