package com.example.stud.util;

import javafx.scene.Parent;
import javafx.scene.Scene;

public final class SceneUtil {
    private static final String APP_CSS = "/com/example/stud/app.css";

    private SceneUtil() {
    }

    public static Scene createScene(Parent root) {
        Scene scene = new Scene(root);
        String css = SceneUtil.class.getResource(APP_CSS).toExternalForm();
        scene.getStylesheets().add(css);
        return scene;
    }
}
