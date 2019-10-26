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
import com.senior.alarumback.model.Grupo;
import com.senior.alarumback.model.Usuario;
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
import javafx.scene.control.Alert;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private JFXButton salvarButton;

    @FXML
    private JFXButton loginButton;

    private boolean logged = false;
    private static String nomeUsuario;

    public static String getNomeUsuario() {
        return nomeUsuario;
    }

    private static Gerencia gerenciaSelecionada;
    @FXML
    private JFXTextField txtNome;

    @FXML
    private JFXComboBox<Gerencia> listGroup;

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
        System.exit(0);
    }

    private void atualizaNomeUsuario() {
        nomeUsuario = txtNome.getText();
    }

    private void atualizaGerenciaSelecionada() throws Exception {
        if (listGroup.getSelectionModel().getSelectedItem() == null) {
            throw new IOException("É necessário selecionar uma gerência");
        }
        this.gerenciaSelecionada = listGroup.getSelectionModel().getSelectedItem();
    }

    @FXML
    void btnSalvarAction(ActionEvent event) {

        try {
            atualizaGerenciaSelecionada();
            atualizaNomeUsuario();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRootRef = database.getReference();
            DatabaseReference dbrBanco = myRootRef.child("banco");
            Grupo grupo = getListaGrupos(gerenciaSelecionada.getDs_gerencia());
            DatabaseReference dbrGrupo = dbrBanco.child(gerenciaSelecionada.getDs_gerencia());

            if (grupo != null && grupo.getUsuarios() != null && grupo.getUsuarios().size() >= 1 && grupo.getUsuarios().containsKey(getNomeUsuario())) {
                DatabaseReference dbrUsuarios = dbrGrupo.child("usuarios");
                dbrUsuarios.child(getNomeUsuario()).setValueAsync(new Usuario(getNomeUsuario()));
            } else {
                grupo = colocaValorGrupo(grupo);
                dbrGrupo.setValueAsync(grupo);
            }
            new Alert(Alert.AlertType.INFORMATION, "Informações salvas com sucesso!").showAndWait();
            fechaJanela();

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public void fechaJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    private static Gerencia getGerenciaSelecionada() {
        return gerenciaSelecionada;
    }

    private static Grupo getListaGrupos(String dev) throws InterruptedException, IOException {
        final AtomicBoolean done = new AtomicBoolean(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference bancoRef = myRootRef.child("banco");
        DatabaseReference userRef = bancoRef.child(dev);
        final Grupo grupo = new Grupo();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Grupo getGrupo = dataSnapshot.getValue(Grupo.class);
                    if (getGrupo != null) {
                        grupo.setGerencia(getGrupo.getGerencia());
                        grupo.setUsuarios(getGrupo.getUsuarios());
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
        return grupo;

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
            logged = false;
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
                listGroup.getItems().addAll(((Collection) getListaGerencias()));
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

    private Grupo colocaValorGrupo(Grupo grupo) {
        if (grupo == null) {
            grupo = new Grupo();
            grupo.setGerencia(getGerenciaSelecionada());
            HashMap<String, Usuario> novaListaUsuarios = new HashMap<>();
            novaListaUsuarios.put(getNomeUsuario(), new Usuario(getNomeUsuario()));
            grupo.setUsuarios(novaListaUsuarios);
        } else {
            Map<String, Usuario> usuariosAtuais = grupo.getUsuarios();
            usuariosAtuais.put(getNomeUsuario(), new Usuario(getNomeUsuario()));
            grupo.setUsuarios(usuariosAtuais);
        }
        return grupo;
    }

}
