package GlycemicIndexApplication {
    part def GlycemicIndexModel;
    part def MainView;
    part def InputDialog;
    part def GlycemicIndexController;

    part application {
        part model : GlycemicIndexModel;
        part view : MainView;
        part dialog : InputDialog;
        part controller : GlycemicIndexController;

        connect view to controller;
        connect controller to model;
        connect model to view;
        connect controller to dialog;
    }
}

