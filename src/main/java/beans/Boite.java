/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.Period;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import vues.Progress;

/**
 *
 * @author NGEREZA
 */
public class Boite {

    private static Alert alert;
    private static TextArea text;

    private static Service ser;
    private static Progress pg = new Progress();

    //Affiche une exception
    public static void showException(Exception ex, String title) {

        alert = new Alert(AlertType.ERROR);
        text = new TextArea();
        text.setEditable(false);
        text.setWrapText(true);
        text.setText(ex.toString());

        alert.setTitle(title+" : "+ex.getMessage());
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(text);
        alert.showAndWait();

    }

    //afficher une erreur
    public static void showInformation(String info, String title, Alert.AlertType type) {

        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(info);

        alert.showAndWait();

    }

    //afficher une boite de confirmation
    public static boolean showConfirmation(String qst, String title) {

        alert = new Alert(Alert.AlertType.CONFIRMATION, qst, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(null);
        if (ButtonType.YES == alert.showAndWait().get()) {
            return true;
        }
        return false;
    }

    public static boolean verifier(Object... ob) {

        for (Object o : ob) {
            //textField
            if (o instanceof TextField) {
                TextField tf = (TextField) o;
                if (tf.getText().isBlank()) {
                    Boite.showInformation("Information manquante :\n" + tf.getPromptText(), "", AlertType.WARNING);
                    tf.requestFocus();
                    return false;
                }
            }

            //TextArea
            if (o instanceof TextArea) {
                TextArea ta = (TextArea) o;
                if (ta.getText().isBlank()) {
                    Boite.showInformation("Information manquante :\n" + ta.getPromptText(), "", AlertType.WARNING);
                    ta.requestFocus();
                    return false;
                }
            }

            //comboBox
            if (o instanceof ComboBox) {
                ComboBox<String> cb = (ComboBox<String>) o;
                if (cb.getSelectionModel().isEmpty()) {
                    Boite.showInformation("Information manquante :\n" + cb.getPromptText(), "", AlertType.WARNING);
                    cb.requestFocus();
                    return false;
                }
            }

            //datepicker
            if (o instanceof DatePicker) {
                DatePicker dp = (DatePicker) o;
                if (dp.getEditor().getText().isBlank()) {
                    Boite.showInformation("Information manquante :\n" + dp.getPromptText(), "", AlertType.WARNING);
                    dp.requestFocus();
                    return false;
                }
            }
        }

        return true;
    }

    public static void vider(Object... ob) {

        for (Object o : ob) {
            //TextField
            if (o instanceof TextField) {
                TextField tf = (TextField) o;
                if (!tf.getText().isBlank()) {
                    tf.clear();
                }
            }

            //TextArea
            if (o instanceof TextArea) {
                TextArea ta = (TextArea) o;
                if (!ta.getText().isBlank()) {
                    ta.clear();
                }
            }

            //comboBox
            if (o instanceof ComboBox) {
                ComboBox<String> cb = (ComboBox<String>) o;
                if (!cb.getSelectionModel().isEmpty()) {
                    cb.getSelectionModel().clearSelection();
                }
            }

            //datepicker
            if (o instanceof DatePicker) {
                DatePicker dp = (DatePicker) o;
                if (!dp.getEditor().getText().isBlank()) {
                    dp.setValue(null);
                }
            }
        }

    }

    //executer une tache
    public static void executeTache(Task tsk) {

        ser = new Service() {
            @Override
            protected Task createTask() {
                return tsk;
            }
        };
        pg.getProgress().progressProperty().bind(ser.progressProperty());
        ser.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                pg.show();
            }
        });
        ser.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                pg.close();
            }
        });
        ser.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                pg.close();
                Boite.showException(new Exception(ser.getException()), ser.getTitle());
            }
        });
        ser.start();

    }
    public static void execute(Runnable r){
        Platform.runLater(r);
    }
    

    //intervale des dates
    public static int jours(LocalDate date) {

        LocalDate ld = LocalDate.now();
        return Period.between(date, ld).getDays();
    }

    public static int annees(LocalDate date) {

        LocalDate ld = LocalDate.now();
        return Period.between(date, ld).getYears();
    }
}
