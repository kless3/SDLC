package by.bsuir.glycemicindex.model;

import java.util.Objects;

/** Last valid product entered by the user. */
public record ProductInput(String productName) {
    public ProductInput {
        Objects.requireNonNull(productName, "productName");
    }
}

