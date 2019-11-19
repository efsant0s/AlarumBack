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
import com.senior.alarumback.model.Mensagem;
import com.senior.alarumback.model.Usuario;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private static Grupo grupoAtual = new Grupo();
    private static boolean grupoConfigurado = false;

    public static boolean isGrupoConfigurado() {
        return grupoConfigurado;
    }

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
    void grupoListAction(ActionEvent event) {
        try {
            atualizaGerenciaSelecionada();
        } catch (Exception ex) {
            Utils.mostraException(ex);
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        setListaGrupos(gerenciaSelecionada.getDs_gerencia());
    }

    @FXML
    void btnSalvarAction(ActionEvent event) {

        try {
            atualizaNomeUsuario();
            mandaInformacaoAtualizacao();
            if (!isGrupoConfigurado()) {
                configuraNotificacao(gerenciaSelecionada.getDs_gerencia());
            }

            new Alert(Alert.AlertType.INFORMATION, "Informações salvas com sucesso!").showAndWait();
            fechaJanela();

        } catch (Exception ex) {
            Utils.mostraException(ex);
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public static void mandaInformacaoAtualizacao() throws InterruptedException, IOException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference dbrBanco = myRootRef.child("banco");
        DatabaseReference dbrGrupo = dbrBanco.child(gerenciaSelecionada.getDs_gerencia());

        if (grupoAtual != null && grupoAtual.getUsuarios() != null && grupoAtual.getUsuarios().size() >= 1 && grupoAtual.getUsuarios().containsKey(getNomeUsuario())) {
            DatabaseReference dbrUsuarios = dbrGrupo.child("usuarios");
            dbrUsuarios.child(getNomeUsuario()).setValueAsync(new Usuario(getNomeUsuario()));
        } else {
            grupoAtual = colocaValorGrupo(grupoAtual);
            dbrGrupo.setValueAsync(grupoAtual);
        }
    }

    public static void mandaInformacaoConfirmacao() throws InterruptedException, IOException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference dbrBanco = myRootRef.child("banco");
        DatabaseReference dbrGrupo = dbrBanco.child(gerenciaSelecionada.getDs_gerencia());

        if (grupoAtual != null && grupoAtual.getUsuarios() != null && grupoAtual.getUsuarios().size() >= 1 && grupoAtual.getUsuarios().containsKey(getNomeUsuario())) {
            DatabaseReference dbrUsuarios = dbrGrupo.child("usuarios");
            Usuario usuario = new Usuario(getNomeUsuario());
            usuario.setDt_confirmacao(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
            dbrUsuarios.child(getNomeUsuario()).setValueAsync(usuario);
        } else {
            grupoAtual = colocaValorGrupo(grupoAtual);
            dbrGrupo.setValueAsync(grupoAtual);
        }
    }

    private static void configuraNotificacao(String gerencia) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference userRef = myRootRef.child("banco").child(gerencia).child("mensagem");
        grupoConfigurado = true;
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Mensagem mensagem = dataSnapshot.getValue(Mensagem.class);
                    if (mensagem != null && !passou5Mins(mensagem.getDt_atualizacao())) {
                        mostraMensagem(mensagem);

                        mandaInformacaoAtualizacao();

                    }

                } catch (Exception e) {
            Utils.mostraException(e);
                    System.out.println(dataSnapshot.getKey());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(("err"));
            }

            private boolean passou5Mins(String dt_atualizacao) throws ParseException {
                Date dataEnvioMensagem = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(dt_atualizacao);
                return (((new java.util.Date()).getTime() - (dataEnvioMensagem.getTime())) / (1000 * 60)) > 1;
            }
        });
    }

    public void fechaJanela() {
        Stage stage = (Stage) txtNome.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public static void mostraMensagem(final Mensagem mensagem) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(mensagem.getDs_titulo());
                alert.setHeaderText(mensagem.getDs_mensagem());
                alert.setContentText("Choose your option.");

                ButtonType buttonTypeOne = new ButtonType("Confirmo que recebi a notificação e estou saindo");

                alert.getButtonTypes().setAll(buttonTypeOne);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeOne) {
                    try {
                        mandaInformacaoConfirmacao();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }

    private static Gerencia getGerenciaSelecionada() {
        return gerenciaSelecionada;
    }

    private static void setListaGrupos(String dev) throws InterruptedException, IOException {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference grupoRef = database.getReference().child("banco").child(dev);
        grupoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Grupo getGrupo = dataSnapshot.getValue(Grupo.class);
                    if (getGrupo != null) {
                        grupoAtual.setGerencia(getGrupo.getGerencia());
                        grupoAtual.setUsuarios(getGrupo.getUsuarios());
                    } else {
                        grupoAtual = new Grupo();
                        grupoAtual.setGerencia(gerenciaSelecionada);
                        grupoAtual.setUsuarios(new HashMap<String, Usuario>());
                    }

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

    public static List<Gerencia> getListaGerencias() throws InterruptedException, IOException {
        final AtomicBoolean done = new AtomicBoolean(false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        final DatabaseReference userRef = myRootRef.child("gerencias");
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
                    userRef.removeEventListener(this);

                } catch (Exception e) {
                    //Log the exception and the key 
                    System.out.println(dataSnapshot.getKey());
                    e.printStackTrace();
                    done.set(true);
                    userRef.removeEventListener(this);

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

            stage.setTitle("Alarum");
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
            Utils.mostraException(ex);
            ex.printStackTrace();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizaVisibilidade();

    }

    public boolean isLoggedIn() { 
        return logged;
    }

    private static Grupo colocaValorGrupo(Grupo grupo) {

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

    private void removeUsuarioBanco() {
        if (grupoAtual != null && grupoAtual.getUsuarios() != null) {
            grupoAtual.getUsuarios().remove(getNomeUsuario());

        }

    }

}
