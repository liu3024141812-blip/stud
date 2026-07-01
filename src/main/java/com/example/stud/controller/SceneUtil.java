package com.example.stud.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;

final class SceneUtil {
    private static final String APP_CSS = "/com/example/stud/app.css";

    private SceneUtil() {
    }

    static Scene createScene(Parent root) {
        Scene scene = new Scene(root);
        String css = SceneUtil.class.getResource(APP_CSS).toExternalForm();
        scene.getStylesheets().add(css);
        return scene;
    }
}
