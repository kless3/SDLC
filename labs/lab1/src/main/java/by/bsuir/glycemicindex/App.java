package by.bsuir.glycemicindex;

import by.bsuir.glycemicindex.controller.GlycemicIndexController;
import by.bsuir.glycemicindex.model.GlycemicIndexModel;
import by.bsuir.glycemicindex.view.MainView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/** Application entry point. */
public final class App {
    private App() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            useSystemLookAndFeel();

            GlycemicIndexModel model = new GlycemicIndexModel();
            MainView view = new MainView();
            GlycemicIndexController controller = new GlycemicIndexController(model, view);
            controller.start();
        });
    }

    private static void useSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {
            // Default Swing look and feel remains a safe fallback.
        }
    }
}

