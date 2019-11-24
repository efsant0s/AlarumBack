package com.senior.alarumback;

import javafx.scene.text.Text;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
/**
 *
 * @author Eduardo
 */
public class MensagemApp extends AnchorPane {

    protected final Text text;

    public MensagemApp() {

        text = new Text();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);

        text.setLayoutX(271.0);
        text.setLayoutY(196.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Mensagem");

        getChildren().add(text);

    }
}
