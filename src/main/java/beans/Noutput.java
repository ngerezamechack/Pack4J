/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author NGEREZA
 */
public class Noutput extends OutputStream {

    private TextArea out;
    private String md = "";

    public Noutput(TextArea out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        Platform.runLater(()->{out.appendText(new StringBuilder().append((char) b).toString());});
    }

    public void setText(String str) {
        Platform.runLater(() -> {
            out.appendText(str);
            out.appendText("\n");
            out.end();
        });
    }

    public String getModules(){
        return traitement().replace(" ", "");
    }
    
    
    private String traitement(){
        out.getText().lines().forEach((line)->{
            line = removePackage(line);
            if(!line.contains("jdk8") && !md.contains(line)){
                if(!md.isBlank()) md +=",";
                md += line;
            }
        });
        
        return md += ",jdk.localedata";
    }
    
    
    //remove package
    private static String removePackage(String m) {
        if (m.contains("/")) {
            return m.substring(0, m.indexOf("/"));
        } else {
            return m;
        }
    }
}
