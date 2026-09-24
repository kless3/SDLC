package by.bsuir.glycemicindex.controller;

import by.bsuir.glycemicindex.model.GlycemicIndexModel;
import by.bsuir.glycemicindex.model.ProductInput;
import by.bsuir.glycemicindex.model.ValidationException;
import by.bsuir.glycemicindex.view.InputDialog;
import by.bsuir.glycemicindex.view.MainView;

/** Connects user actions to the active model. */
public final class GlycemicIndexController {
    private final GlycemicIndexModel model;
    private final MainView view;

    public GlycemicIndexController(GlycemicIndexModel model, MainView view) {
        this.model = model;
        this.view = view;
    }

    public void start() {
        model.addPropertyChangeListener(view);
        view.onInputRequested(event -> openInputDialog());
        view.render(model.getState());
        view.open();
    }

    private void openInputDialog() {
        ProductInput previousInput = model.getLastInput().orElse(null);
        InputDialog dialog = new InputDialog(view, previousInput);

        dialog.onSubmit(() -> {
            try {
                model.findProduct(dialog.getProductName());
                dialog.close();
            } catch (ValidationException exception) {
                view.showError(dialog, exception.getMessage());
            }
        });

        dialog.open();
    }
}

