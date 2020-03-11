/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Noutput;
import beans.Packager;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 *
 * @author NGEREZA
 */
public class Packaging {

    public static boolean jpackage(Packager p, Noutput out) {
        
        
        String jpackage = "jpackage --type " + p.getType() + " --runtime-image " + p.getRuntime() + " --input " + p.getAppDir() + " "
                + " --main-jar " + p.getMainJar() + " --name " + p.getAppName() + " --icon " + p.getIcon()
                + " --win-menu-group " + p.getAppName() + " --main-class " + p.getMainClass() + " "
                + " --verbose --win-shortcut --win-menu --win-dir-chooser --dest "+p.getDest()
                + " --copyright " + p.getAppVendor() + " --vendor " + p.getAppVendor() + " --app-version " + p.getAppVersion();
        out.setText(jpackage);

        
        ProcessBuilder pb;
        Process pc;
        BufferedReader in;
        
        StringBuilder sb;
        String l = "";
        
        try {

            pb = new ProcessBuilder("cmd.exe","/c",jpackage);
            pc = pb.start();

            sb = new StringBuilder();
            in = new BufferedReader(new InputStreamReader(pc.getInputStream()));

            while ((l = in.readLine()) != null) {
                out.setText(l);
            }

            return pc.exitValue() == 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
