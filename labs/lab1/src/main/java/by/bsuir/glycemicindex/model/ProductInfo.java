package by.bsuir.glycemicindex.model;

import java.util.Objects;

/** Result returned from the built-in product directory. */
public record ProductInfo(
        String displayName,
        int glycemicIndex,
        GlycemicCategory category
) {
    public ProductInfo {
        Objects.requireNonNull(displayName, "displayName");
        Objects.requireNonNull(category, "category");
        if (glycemicIndex < 0 || glycemicIndex > 100) {
            throw new IllegalArgumentException("Glycemic index must be between 0 and 100");
        }
    }
}

