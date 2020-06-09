/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Noutput;
import beans.Packager;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author NGEREZA
 */
public class Runtime {
    
    public static boolean jlink(Packager p,Noutput out){
        
        String jlink = "jlink --verbose --add-modules "+p.getDependencies()+" "
                + "--include-locales=fr --no-header-files --no-man-pages --strip-debug --compress=2 --output "+p.getRuntime();
        
        out.setText(jlink);
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", jlink);

        StringBuilder sb = new StringBuilder();
        String l = "";
        try {

            Process pc = pb.start();

             BufferedReader in = new BufferedReader(new InputStreamReader(pc.getInputStream()));

            while ((l = in.readLine()) != null) {
                out.setText(l);
                if(l.contains("Error"))return false;
            }

            return pc.exitValue() == 0;
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
