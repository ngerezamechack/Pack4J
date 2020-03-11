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

    public Noutput(TextArea out) {
        this.out = out;
    }

    @Override
    public void write(int b) throws IOException {
        out.appendText(new StringBuilder().append((char) b).toString());
    }

    public void setText(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                out.appendText(str);
                out.appendText("\n");
                out.end();
            }
        });
    }

}
