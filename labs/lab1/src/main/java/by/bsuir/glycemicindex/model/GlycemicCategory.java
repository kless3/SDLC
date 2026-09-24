package by.bsuir.glycemicindex.model;

/** Glycemic-index categories on the glucose scale. */
public enum GlycemicCategory {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private final String displayName;

    GlycemicCategory(String displayName) {
        this.displayName = displayName;
    }

    public static GlycemicCategory fromIndex(int index) {
        if (index <= 55) {
            return LOW;
        }
        if (index <= 69) {
            return MEDIUM;
        }
        return HIGH;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

