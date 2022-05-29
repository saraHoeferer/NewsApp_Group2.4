package at.ac.fhcampuswien.enumerations;

public enum Country {
    UNITED_ARAB_EMIRATES, ARGENTINA, AUSTRIA, AUSTRALIA, BELGIUM, BULGARIA, BRASIL, CANADA, SWITZERLAND, CHINA, COLOMBIA, CUBA, CZECH_REPUBLIC, GERMANY, EGYPT, FRANCE, GREAT_BRITAIN, GREECE, HONGKONG, HUNGARY, INDONESIA, IRELAND, ISRAEL, INDIA, ITALY, JAPAN, SOUTH_KOREA, LITHUANIA, LATVIA, MAROKKO, MEXIKO, MALAYSIA, NIGERIA, NETHERLANDS, NORWAY, NEW_ZEALAND, PHILIPPINES, POLAND, PORTUGAL, ROMANIA, SERBIA, RUSSIA, SAUDI_ARABIA, SWEDEN, SINGAPUR, SLOVENIA, SLOVAKIA, THAILAND, TURKEY, TAIWAN, UKRAINE, USA, VENEZUELA, SOUTH_AFRICA;

    public String getCountry () {
        return switch (this) {
            case UNITED_ARAB_EMIRATES -> "ae";
            case ARGENTINA -> "ar";
            case AUSTRIA -> "at";
            case AUSTRALIA -> "au";
            case BELGIUM -> "be";
            case BULGARIA -> "bg";
            case BRASIL -> "br";
            case CANADA -> "ca";
            case SWITZERLAND -> "ch";
            case CHINA -> "cn";
            case COLOMBIA -> "co";
            case CUBA -> "cu";
            case CZECH_REPUBLIC -> "cz";
            case GERMANY -> "de";
            case EGYPT -> "eg";
            case FRANCE -> "fr";
            case GREAT_BRITAIN -> "gb";
            case GREECE -> "gr";
            case HONGKONG -> "hk";
            case HUNGARY -> "hu";
            case INDONESIA -> "id";
            case IRELAND -> "ie";
            case ISRAEL -> "il";
            case INDIA -> "in";
            case ITALY -> "it";
            case JAPAN -> "jp";
            case SOUTH_KOREA -> "kr";
            case LITHUANIA -> "lt";
            case LATVIA -> "lv";
            case MAROKKO -> "ma";
            case MEXIKO -> "mx";
            case MALAYSIA -> "my";
            case NIGERIA -> "ng";
            case NETHERLANDS -> "nl";
            case NORWAY -> "no";
            case NEW_ZEALAND -> "nz";
            case PHILIPPINES -> "ph";
            case POLAND -> "pl";
            case PORTUGAL -> "pt";
            case ROMANIA -> "ro";
            case SERBIA -> "rs";
            case RUSSIA -> "ru";
            case SAUDI_ARABIA -> "sa";
            case SWEDEN -> "se";
            case SINGAPUR -> "sg";
            case SLOVENIA -> "si";
            case SLOVAKIA -> "sk";
            case THAILAND -> "th";
            case TURKEY -> "tr";
            case TAIWAN -> "tw";
            case UKRAINE -> "ua";
            case USA -> "us";
            case VENEZUELA -> "ve";
            case SOUTH_AFRICA -> "za";
        };
    }
}
