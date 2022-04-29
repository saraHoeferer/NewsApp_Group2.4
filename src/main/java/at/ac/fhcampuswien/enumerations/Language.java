package at.ac.fhcampuswien.enumerations;

public enum Language {
    ARABIC, GERMAN, ENGLISH, SPANISH, FRENCH, HEBREW, ITALIAN, DUTCH, NORWEGIAN, PORTUGUESE, RUSSIAN, SWEDISH, UD, CHINESE, NONE;

    public String getLanguage () {
        return switch (this) {
            case ARABIC -> "ar";
            case GERMAN -> "de";
            case ENGLISH -> "en";
            case SPANISH -> "es";
            case FRENCH -> "fr";
            case HEBREW -> "he";
            case ITALIAN -> "it";
            case DUTCH -> "nl";
            case NORWEGIAN -> "no";
            case PORTUGUESE -> "pt";
            case RUSSIAN -> "ru";
            case SWEDISH -> "sv";
            case UD -> "ud";
            case CHINESE -> "zh";
            case NONE -> "";
        };
    }
}
