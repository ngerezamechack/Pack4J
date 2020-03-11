/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vues;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author NGEREZA
 */
public class Progress extends Stage{
    
    private ProgressIndicator pg = new ProgressIndicator();
    private Scene sc;
    
    public Progress(){
        
        pg.setPrefSize(100, 100);
        sc = new Scene(pg);
        
        this.initStyle(StageStyle.TRANSPARENT);
        this.setScene(sc);
        this.setAlwaysOnTop(true);
        
    }
    
    
    public ProgressIndicator getProgress(){
        return pg;
    }
    
}
