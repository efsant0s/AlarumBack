package com.senior.alarumback;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.senior.alarumback.model.Gerencia;
import com.senior.alarumback.model.UsuarioLogin;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private JFXButton salvarButton;

    @FXML
    private JFXButton loginButton;

    private boolean logged = false;
    @FXML
    private JFXTextField txtNome;

    @FXML
    private JFXComboBox<?> listGroup;

    @FXML
    private Text labelNome;

    @FXML
    private Text LabelGrupo;

    @FXML
    private JFXButton sairButton;

    @FXML
    private Text labelAviso;

    void atualizaVisibilidade() {
        salvarButton.setVisible(isLoggedIn());
        listGroup.setVisible(isLoggedIn());
        LabelGrupo.setVisible(isLoggedIn());
        labelNome.setVisible(isLoggedIn());
        txtNome.setVisible(isLoggedIn());
        labelAviso.setVisible(!isLoggedIn());
        sairButton.setVisible(!isLoggedIn());
    }

    @FXML
    void btnSairAction(ActionEvent event) {
    }

    @FXML
    void btnSalvarAction(ActionEvent event) {
        Map listaUsuarios;
        try {
            listaUsuarios = getListaUsuarios();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRootRef = database.getReference();
            
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static List<Gerencia> getListaGerencias() throws InterruptedException, IOException {
        final AtomicBoolean done = new AtomicBoolean(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference userRef = myRootRef.child("gerencias");
        final List<Gerencia> listaGerencias = new ArrayList<>();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                    while (dataSnapshots.hasNext()) {
                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                        Gerencia ger = dataSnapshotChild.getValue(Gerencia.class);
                        listaGerencias.add(ger);
                    }
                    done.set(true);
                } catch (Exception e) {
                    //Log the exception and the key 
                    System.out.println(dataSnapshot.getKey());
                    e.printStackTrace();
                    done.set(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(("err"));
            }
        });
        while (!done.get());
        return listaGerencias;
    }

    @FXML
    void btnLoginAction(ActionEvent event) {

        try {
            Stage stagePrincipal = (Stage) loginButton.getScene().getWindow();
            Stage stage = new Stage();
            LoginBase root = new LoginBase() {
            };
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            stage.setTitle("JavaFX and Maven");
            stage.setScene(scene);
            stagePrincipal.hide();
            stage.showAndWait();
            stagePrincipal.show();
            if (!logged) {
                logged = root.isLogou();
                txtNome.setText(root.getLogin());
                txtNome.setEditable(false);
                listGroup.getItems().clear();
                listGroup.getItems().addAll(((Collection)getListaGerencias()));
            }
            if (logged) {
            }

            atualizaVisibilidade();
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizaVisibilidade();

    }

    public boolean isLoggedIn() {
        //TODO 
        return logged;
    }

    private Map getListaUsuarios() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
