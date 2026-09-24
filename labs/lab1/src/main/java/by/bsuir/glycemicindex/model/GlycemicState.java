package by.bsuir.glycemicindex.model;

/** Immutable snapshot of the active model state. */
public record GlycemicState(ProductInput input, ProductInfo result) {
    public static GlycemicState empty() {
        return new GlycemicState(null, null);
    }

    public boolean hasResult() {
        return input != null && result != null;
    }
}

