package library.enums;

public enum BookCategory {
    SCHOOL_BOOK("SCHOOL_BOOK"),
    FANTASY("FANTASY"),
    POETRY("POETRY"),
    SCIENCE_FICTION("SCIENCE_FICTION"),
    HORROR("HORROR"),
    DRAMA("DRAMA"),
    ROMANCE("ROMANCE"),
    MANAGEMENT_AND_ECONOMY("MANAGEMENT_AND_ECONOMY"),
    POPULAR_SCIENCE("POPULAR_SCIENCE"),
    RELIGION("RELIGION"),
    NOVEL("NOVEL"),
    FAIRYTALE("FAIRYTALE"),
    SHORT_STORY("SHORT_STORY"),
    REPORTAGE("REPORTAGE"),
    DETECTIVE_STORY("DETECTIVE_STORY"),
    THRILLER("THRILLER"),
    BIOGRAPHY("BIOGRAPHY"),
    HISTORY("HISTORY"),
    TOURISM("TOURISM"),
    GUIDE("GUIDE"),
    CRIME_STORY("CRIME_STORY"),
    ADVENTURE("ADVENTURE");

    private final String displayValue;

    private BookCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
