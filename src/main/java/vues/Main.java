/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import beans.Boite;
import controlers.MainPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
/**
 *
 * @author NGEREZA
 */
public class Main extends Application{

    @Override
    public void start(Stage st) throws Exception {
    
        
        MainPane mp = new MainPane();
        Scene scene = new Scene(mp);
        scene.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        
        st.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(Boite.showConfirmation("Close?", "Attention")){
                    System.exit(0);
                }else{
                    event.consume();
                }
            }
        });
        
        st.setTitle("Pack4J");
        st.getIcons().add(new Image(getClass().getResourceAsStream("/images/package.png")));
        
        st.setResizable(false);
        st.setScene(scene);
        st.show();
        
    }
    
    
    
    
    public static void main(String[] arg) {
        launch(arg);
    }

    
   
}
