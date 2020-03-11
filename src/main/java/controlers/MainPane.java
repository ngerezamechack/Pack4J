/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Boite;
import beans.Noutput;
import beans.Packager;
import java.io.File;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

/**
 *
 * @author NGEREZA
 */
public class MainPane extends BorderPane {

    @FXML
    private TextField ticon, tdir, tclass, tname, tversion, tvendor;
    @FXML
    private ComboBox<String> tjar, ttype;
    @FXML
    private TextArea tout;

    private Packager pac = new Packager();

    private DirectoryChooser dc;
    private FileChooser ic;
    private File file, icon;
    private Noutput out;

    public MainPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vues/main.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            out = new Noutput(tout);
            ttype.setItems(FXCollections.observableArrayList("", "msi", "exe"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    private void cicon(ActionEvent ev) {
        ic = new FileChooser();
        ic.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Icon", "*.ico", "*.icns"));

        if ((icon = ic.showOpenDialog(null)) != null) {
            if (icon.exists() || (file != null)) {
                ticon.setText(icon.getAbsolutePath());
            }
        }
    }

    @FXML
    private void cdir(ActionEvent ev) {
        dc = new DirectoryChooser();
        dc.setTitle("Choose Jars dir");

        if ((file = dc.showDialog(null)) != null) {

            tjar.getItems().clear();
            tjar.getItems().add("");

            //jars
            if (file.exists() && (file != null) && file.isDirectory()) {

                tdir.setText(file.getAbsolutePath());

                for (File f : file.listFiles()) {
                    if (f.getAbsolutePath().endsWith(".jar")) {
                        tjar.getItems().add(f.getName());
                    }
                }

            }
        }

    }

    @FXML
    private void packaging(ActionEvent ev) {

        if (Boite.verifier(tdir, tjar, tclass, tname, tversion, tvendor, ttype,ticon)) {
            if (Boite.showConfirmation("Execute?", "")) {

                //initialisation
                pac = new Packager();
                pac.setAppDir(tdir.getText() + File.separatorChar);

                pac.setMainJar(tjar.getSelectionModel().getSelectedItem());
                pac.setMainClass(tclass.getText());

                pac.setAppName(tname.getText());
                pac.setAppVersion(tversion.getText());
                pac.setAppVendor(tvendor.getText());

                pac.setType(ttype.getSelectionModel().getSelectedItem());
                pac.setIcon(ticon.getText());
                pac.setRuntime(file.getParentFile().getAbsolutePath() + File.separatorChar + "runtime");
                pac.setDest(file.getParentFile().getAbsolutePath());

                //execution
                Boite.executeTache(new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (Dependencies.jdeps(pac, out)) {
                            if (Runtime.jlink(pac, out)) {
                                if(Packaging.jpackage(pac, out)){
                                    vider();
                                }
                            }
                        }
                        return null;
                    }
                });

            }
        }

    }

    @FXML
    private void jartext(ActionEvent ev) {
        String jar;
        if (!(jar = tjar.getSelectionModel().getSelectedItem()).isBlank() || tjar.getSelectionModel() != null) {
            String main = "";
            main = getMainClass(tdir.getText() + File.separator + jar);
            tclass.setText(main);
        }
    }

    private String getMainClass(String path) {
        try {
            JarFile jar = new JarFile(path);
            return jar.getManifest().getMainAttributes().getValue(Attributes.Name.MAIN_CLASS);
        } catch (Exception ex) {
            Boite.showException(ex, "main");
            return "";
        }

    }
    
    
    
    private void vider(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Boite.vider(tdir, tjar, tclass, tname, tversion, tvendor, ttype,ticon);
                tjar.getItems().clear();
            }
        });
    }
}
