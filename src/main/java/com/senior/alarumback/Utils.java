/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 *
 * @author Eduardo
 */
public class Utils {

    public static void configuraInstancia() throws FileNotFoundException, IOException {
        try {
            //Pegar arquivo de configuração
            InputStream path = Utils.class
                    .getClassLoader().getResourceAsStream("acess/alarum_auth.json");
            //Configurar as opções de credenciais
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(path))
                    .setDatabaseUrl("https://alarum-e97a3.firebaseio.com")
                    .build();
            //Iniciar a conexão.
            FirebaseApp.initializeApp(options);
        } catch (Exception ex) {
            Utils.mostraException(ex); //Mostrar o erro ao usuário.
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex); //StackTrace
            ex.printStackTrace();
            System.exit(0);// Sair do sistema, pois já falhou na conexão inicial
        }

    }

    private static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }

    public static String md5(String mensagem) {
        return stringHexa(gerarHash(mensagem, "MD5"));
    }
    
    public static void mostraException(Exception ex) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro na aplicação");
        alert.setHeaderText("Favor mostra o erro ao administrador");
        alert.setContentText(ex.getMessage());

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    private static byte[] gerarHash(String frase, String algoritmo) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
