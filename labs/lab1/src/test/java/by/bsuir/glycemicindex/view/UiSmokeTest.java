package by.bsuir.glycemicindex.view;

import by.bsuir.glycemicindex.model.GlycemicCategory;
import by.bsuir.glycemicindex.model.GlycemicState;
import by.bsuir.glycemicindex.model.ProductInfo;
import by.bsuir.glycemicindex.model.ProductInput;

import javax.swing.SwingUtilities;
import java.awt.GraphicsEnvironment;
import java.util.concurrent.atomic.AtomicReference;

/** Verifies construction of both Swing windows and input restoration. */
public final class UiSmokeTest {
    private UiSmokeTest() {
    }

    public static void main(String[] args) throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("UI smoke test skipped: headless environment.");
            return;
        }

        AtomicReference<Throwable> failure = new AtomicReference<>();
        SwingUtilities.invokeAndWait(() -> {
            MainView view = null;
            InputDialog dialog = null;
            try {
                ProductInput input = new ProductInput("Яблоко");
                ProductInfo result = new ProductInfo("Яблоко", 36, GlycemicCategory.LOW);

                view = new MainView();
                view.render(new GlycemicState(input, result));

                dialog = new InputDialog(view, input);
                check(dialog.getProductName().equals(input.productName()),
                        "Product name was not restored");
            } catch (Throwable throwable) {
                failure.set(throwable);
            } finally {
                if (dialog != null) {
                    dialog.dispose();
                }
                if (view != null) {
                    view.dispose();
                }
            }
        });

        if (failure.get() != null) {
            throw new AssertionError("UI smoke test failed", failure.get());
        }
        System.out.println("Swing UI smoke test passed.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}

