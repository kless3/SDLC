package by.bsuir.glycemicindex.model;

import java.util.concurrent.atomic.AtomicInteger;

/** Self-contained model checks without external test libraries. */
public final class GlycemicIndexModelTest {
    private GlycemicIndexModelTest() {
    }

    public static void main(String[] args) throws Exception {
        findsProductAndNormalizesInput();
        recognizesAliasesAndCategories();
        notifiesViewAboutChanges();
        rejectsInvalidInput();
        preservesLastValidStateAfterError();
        System.out.println("GlycemicIndexModel checks passed.");
    }

    private static void findsProductAndNormalizesInput() throws Exception {
        GlycemicIndexModel model = new GlycemicIndexModel();
        model.findProduct("  ЯБЛОКО  ");

        check(model.getState().input().productName().equals("Яблоко"),
                "Canonical product name must be saved");
        check(model.getState().result().glycemicIndex() == 36,
                "Apple GI must be returned");
        check(model.getState().result().category() == GlycemicCategory.LOW,
                "Apple must have a low category");
    }

    private static void recognizesAliasesAndCategories() throws Exception {
        GlycemicIndexModel model = new GlycemicIndexModel();

        model.findProduct("спагетти");
        check(model.getState().input().productName().equals("Макароны"),
                "Alias must resolve to its canonical name");

        model.findProduct("мед");
        check(model.getState().result().category() == GlycemicCategory.MEDIUM,
                "Honey must have a medium category");

        model.findProduct("глюкоза");
        check(model.getState().result().category() == GlycemicCategory.HIGH,
                "Glucose must have a high category");
    }

    private static void notifiesViewAboutChanges() throws Exception {
        GlycemicIndexModel model = new GlycemicIndexModel();
        AtomicInteger events = new AtomicInteger();
        model.addPropertyChangeListener(event -> events.incrementAndGet());

        model.findProduct("банан");
        check(events.get() == 1, "Active model must publish one state event");
    }

    private static void rejectsInvalidInput() {
        expectValidation(() -> new GlycemicIndexModel().findProduct(null));
        expectValidation(() -> new GlycemicIndexModel().findProduct(" "));
        expectValidation(() -> new GlycemicIndexModel().findProduct("я"));
        expectValidation(() -> new GlycemicIndexModel().findProduct("а".repeat(61)));
        expectValidation(() -> new GlycemicIndexModel().findProduct("рис123"));
        expectValidation(() -> new GlycemicIndexModel().findProduct("неизвестный продукт"));
    }

    private static void preservesLastValidStateAfterError() throws Exception {
        GlycemicIndexModel model = new GlycemicIndexModel();
        model.findProduct("банан");
        GlycemicState validState = model.getState();

        expectValidation(() -> model.findProduct("неизвестный продукт"));
        check(model.getState().equals(validState),
                "Invalid input must not replace the last valid state");
    }

    private static void expectValidation(CheckedAction action) {
        try {
            action.run();
            throw new AssertionError("ValidationException was expected");
        } catch (ValidationException expected) {
            check(!expected.getMessage().isBlank(), "Validation error must have a message");
        } catch (Exception exception) {
            throw new AssertionError("Unexpected exception", exception);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    @FunctionalInterface
    private interface CheckedAction {
        void run() throws Exception;
    }
}

