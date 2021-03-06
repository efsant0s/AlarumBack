package com.senior.alarumback;

import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.imageio.ImageIO;

public class MainApp extends Application {

    private boolean firstTime;
    private TrayIcon trayIcon;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Utils.configuraInstancia();
            createTrayIcon(stage);
            firstTime = true;
            Platform.setImplicitExit(false);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/Scene.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/styles/Styles.css");

            stage.setTitle("Alarum");
            stage.setScene(scene);
            stage.show();
            LoginBase.lista();
            ControleListaGrupos.lista();
        } catch (Exception ex) {
            Utils.mostraException(ex);
            ex.printStackTrace();
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void createTrayIcon(final Stage stage) {
        if (SystemTray.isSupported()) {
            // get the SystemTray instance
            SystemTray tray = SystemTray.getSystemTray();
            // load an image
            java.awt.Image image = null;
            try {

                URL url = System.class.getResource("/icons/alarum.png");
                image = ImageIO.read(url);
                stage.getIcons().add(new Image("/icons/alarum.png"));
            } catch (Exception ex) {
                Utils.mostraException(ex);
            }
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    hide(stage);
                }
            });
            // create a action listener to listen for default action executed on the tray icon
            final ActionListener closeListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener showListener = new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stage.show();
                        }
                    });
                }
            };
            // create a popup menu
            PopupMenu popup = new PopupMenu();

            MenuItem showItem = new MenuItem("Mostrar");
            showItem.addActionListener(showListener);
            popup.add(showItem);

            MenuItem closeItem = new MenuItem("Fechar");
            closeItem.addActionListener(closeListener);
            popup.add(closeItem);
            /// ... add other items
            // construct a TrayIcon
            trayIcon = new TrayIcon(image, "Alarum", popup);
            trayIcon.setImage(image);
            // set the TrayIcon properties
            trayIcon.addActionListener(showListener);
            // ...
            // add the tray image
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                Utils.mostraException(e);
                System.err.println(e);
            }
            // ...
        }
    }

    public void showProgramIsMinimizedMsg() {
        if (firstTime) {
            trayIcon.displayMessage("Ainda rodando.",
                    "A aplicação vai continuar de fundo.",
                    TrayIcon.MessageType.INFO);
            firstTime = false;
        }
    }

    private void hide(final Stage stage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                    stage.hide();
                    showProgramIsMinimizedMsg();
                } else {
                    System.exit(0);
                }
            }
        });
    }
}
