/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback;

/**
 *
 * @author Eduardo
 */
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.senior.alarumback.model.UsuarioLogin;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginBase extends GridPane {

    protected final ColumnConstraints columnConstraints;
    protected final RowConstraints rowConstraints;
    protected final GridPane gridPane;
    protected final ColumnConstraints columnConstraints0;
    protected final RowConstraints rowConstraints0;
    protected final RowConstraints rowConstraints1;
    protected final RowConstraints rowConstraints2;
    protected final GridPane gridPane0;
    protected final ColumnConstraints columnConstraints1;
    protected final RowConstraints rowConstraints3;
    protected final RowConstraints rowConstraints4;
    protected final RowConstraints rowConstraints5;
    protected final Text text;
    protected final GridPane gridPane1;
    protected final ColumnConstraints columnConstraints2;
    protected final RowConstraints rowConstraints6;
    protected final RowConstraints rowConstraints7;
    protected final RowConstraints rowConstraints8;
    protected final JFXPasswordField jFXTextField;
    protected final Text text0;
    protected final JFXTextField jFXTextField0;
    protected final GridPane gridPane2;
    protected final ColumnConstraints columnConstraints3;
    protected final ColumnConstraints columnConstraints4;
    protected final RowConstraints rowConstraints9;
    protected final RowConstraints rowConstraints10;
    protected final RowConstraints rowConstraints11;
    protected final JFXButton jFXButton;
    protected final JFXButton jFXButton0;

    public static UsuarioLogin usuarioLogin = new UsuarioLogin();
    public static Map<String, UsuarioLogin> listaUsuario = new HashMap<String, UsuarioLogin>();
    private boolean logou = false;
    private String login;

    public String getLogin() {
        return login;
    }

    private void fechaJanela() {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.close();
    }

    public boolean isLogou() {
        return logou;
    }

    public LoginBase() {

        columnConstraints = new ColumnConstraints();
        rowConstraints = new RowConstraints();
        gridPane = new GridPane();
        columnConstraints0 = new ColumnConstraints();
        rowConstraints0 = new RowConstraints();
        rowConstraints1 = new RowConstraints();
        rowConstraints2 = new RowConstraints();
        gridPane0 = new GridPane();
        columnConstraints1 = new ColumnConstraints();
        rowConstraints3 = new RowConstraints();
        rowConstraints4 = new RowConstraints();
        rowConstraints5 = new RowConstraints();
        text = new Text();
        gridPane1 = new GridPane();
        columnConstraints2 = new ColumnConstraints();
        rowConstraints6 = new RowConstraints();
        rowConstraints7 = new RowConstraints();
        rowConstraints8 = new RowConstraints();
        jFXTextField0 = new JFXTextField();
        jFXTextField0.setId("02");
        jFXTextField = new JFXPasswordField();
        jFXTextField.setId("01");
        text0 = new Text();
        gridPane2 = new GridPane();
        columnConstraints3 = new ColumnConstraints();
        columnConstraints4 = new ColumnConstraints();
        rowConstraints9 = new RowConstraints();
        rowConstraints10 = new RowConstraints();
        rowConstraints11 = new RowConstraints();
        jFXButton = new JFXButton();
        jFXButton0 = new JFXButton();
        jFXButton.setText("Sair");
        jFXButton0.setText("Entrar");
        jFXButton0.setId("03");
        jFXButton.setId("04");
        jFXButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fechaJanela();
            }
        });
        jFXTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(
                        KeyCode.ENTER)) {
                    login();
                }
            }
        });
        jFXButton0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                login();
            }
        });
        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(414.0);
        setPrefWidth(408.0);

        columnConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints.setMinWidth(10.0);
        columnConstraints.setPrefWidth(100.0);

        rowConstraints.setMinHeight(10.0);
        rowConstraints.setPrefHeight(30.0);
        rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints0.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints0.setMinWidth(10.0);
        columnConstraints0.setPrefWidth(100.0);

        rowConstraints0.setMinHeight(10.0);
        rowConstraints0.setPrefHeight(30.0);
        rowConstraints0.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints1.setMinHeight(10.0);
        rowConstraints1.setPrefHeight(30.0);
        rowConstraints1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints2.setMinHeight(10.0);
        rowConstraints2.setPrefHeight(30.0);
        rowConstraints2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        columnConstraints1.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints1.setMinWidth(10.0);
        columnConstraints1.setPrefWidth(320.0);

        rowConstraints3.setMinHeight(10.0);
        rowConstraints3.setPrefHeight(30.0);
        rowConstraints3.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints4.setMinHeight(10.0);
        rowConstraints4.setPrefHeight(30.0);
        rowConstraints4.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints5.setMinHeight(10.0);
        rowConstraints5.setPrefHeight(30.0);
        rowConstraints5.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setHalignment(text, javafx.geometry.HPos.CENTER);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("Login");

        GridPane.setRowIndex(gridPane1, 1);

        columnConstraints2.setHgrow(javafx.scene.layout.Priority.ALWAYS);
        columnConstraints2.setMinWidth(10.0);
        columnConstraints2.setPrefWidth(320.0);

        rowConstraints6.setMinHeight(10.0);
        rowConstraints6.setPrefHeight(30.0);
        rowConstraints6.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints7.setMinHeight(10.0);
        rowConstraints7.setPrefHeight(30.0);
        rowConstraints7.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints8.setMinHeight(10.0);
        rowConstraints8.setPrefHeight(30.0);
        rowConstraints8.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setRowIndex(jFXTextField, 1);
        GridPane.setMargin(jFXTextField, new Insets(0.0, 40.0, 0.0, 40.0));

        GridPane.setHalignment(text0, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(text0, javafx.geometry.VPos.CENTER);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setText("Senha");

        GridPane.setMargin(jFXTextField0, new Insets(0.0, 40.0, 0.0, 40.0));

        GridPane.setRowIndex(gridPane2, 2);

        columnConstraints3.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints3.setMinWidth(10.0);
        columnConstraints3.setPrefWidth(100.0);

        columnConstraints4.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        columnConstraints4.setMinWidth(10.0);
        columnConstraints4.setPrefWidth(100.0);

        rowConstraints9.setMinHeight(10.0);
        rowConstraints9.setPrefHeight(30.0);
        rowConstraints9.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints10.setMinHeight(10.0);
        rowConstraints10.setPrefHeight(30.0);
        rowConstraints10.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        rowConstraints11.setMinHeight(10.0);
        rowConstraints11.setPrefHeight(30.0);
        rowConstraints11.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        GridPane.setHalignment(jFXButton, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(jFXButton, 1);

        GridPane.setColumnIndex(jFXButton0, 1);
        GridPane.setHalignment(jFXButton0, javafx.geometry.HPos.CENTER);
        GridPane.setRowIndex(jFXButton0, 1);

        getColumnConstraints().add(columnConstraints);
        getRowConstraints().add(rowConstraints);
        gridPane.getColumnConstraints().add(columnConstraints0);
        gridPane.getRowConstraints().add(rowConstraints0);
        gridPane.getRowConstraints().add(rowConstraints1);
        gridPane.getRowConstraints().add(rowConstraints2);
        gridPane0.getColumnConstraints().add(columnConstraints1);
        gridPane0.getRowConstraints().add(rowConstraints3);
        gridPane0.getRowConstraints().add(rowConstraints4);
        gridPane0.getRowConstraints().add(rowConstraints5);
        gridPane0.getChildren().add(text);
        gridPane.getChildren().add(gridPane0);
        gridPane1.getColumnConstraints().add(columnConstraints2);
        gridPane1.getRowConstraints().add(rowConstraints6);
        gridPane1.getRowConstraints().add(rowConstraints7);
        gridPane1.getRowConstraints().add(rowConstraints8);
        gridPane1.getChildren().add(jFXTextField);
        gridPane1.getChildren().add(text0);
        gridPane.getChildren().add(gridPane1);
        gridPane.getChildren().add(jFXTextField0);
        gridPane2.getColumnConstraints().add(columnConstraints3);
        gridPane2.getColumnConstraints().add(columnConstraints4);
        gridPane2.getRowConstraints().add(rowConstraints9);
        gridPane2.getRowConstraints().add(rowConstraints10);
        gridPane2.getRowConstraints().add(rowConstraints11);
        gridPane2.getChildren().add(jFXButton);
        gridPane2.getChildren().add(jFXButton0);
        gridPane.getChildren().add(gridPane2);
        getChildren().add(gridPane);

    }

    private void login() {
        try {
            if (login(jFXTextField0.getText(), Utils.md5(jFXTextField.getText()))) {
                logou = true;
                new Alert(Alert.AlertType.INFORMATION, "Usuário logado com sucesso").showAndWait();
                login = jFXTextField0.getText();
                fechaJanela();
            } else {
                new Alert(Alert.AlertType.ERROR, "Usuário ou senha inválida!").showAndWait();
            }
        } catch (InterruptedException ex) {
            Utils.mostraException(ex);
            ex.printStackTrace();
            Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Utils.mostraException(ex);
            ex.printStackTrace();
            Logger.getLogger(LoginBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void lista() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference userRef = myRootRef.child("usuarios");
        final Map listaUsuarios = new HashMap();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        UsuarioLogin fcmUser = dataSnapshotChild.getValue(UsuarioLogin.class);
                        listaUsuarios.put(fcmUser.getDs_login(), fcmUser);
                    }
                    listaUsuario = listaUsuarios;

                } catch (Exception e) {
                    //Log the exception and the key 
                    System.out.println(dataSnapshot.getKey());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(("err"));
            }
        });

    }

    private static boolean login(final String login, final String senha) throws InterruptedException, IOException {
        if (listaUsuario.containsKey(login)) {
            for (Map.Entry<String, UsuarioLogin> entrySet : listaUsuario.entrySet()) {
                if (entrySet.getKey().equals(login) && entrySet.getValue() != null && (entrySet.getValue().getDs_senha().equals(senha)
                        || entrySet.getValue().getDs_senha().equals(Utils.md5(senha))) || entrySet.getValue().getDs_senha().equals(Utils.md5(Utils.md5(senha)))) {
                    return true;
                };
            }
        }
        return false;
    }
}
