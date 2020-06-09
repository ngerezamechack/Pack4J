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
public class Dependencies {

    public static boolean jdeps(Packager p, Noutput out) {

        
        String jdeps = "jdeps --ignore-missing-deps --no-recursive  --list-deps " + p.getAppDir() + "*.jar";
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", jdeps);
        out.setText(jdeps);

        StringBuilder sb = new StringBuilder();
        String l = "";
        String modules;
        try {

            Process pc = pb.start();

            BufferedReader in = new BufferedReader(new InputStreamReader(pc.getInputStream()));

            while ((l = in.readLine()) != null) {

                l = removePackage(l);

                if (!sb.toString().contains(l) && !l.contains("jdk8")) {
                    if (sb.toString().contains("java.base") || l.contains("java.base")) {
                        if (!sb.toString().isBlank()) {
                            sb.append(",");
                        }

                        sb.append(l);
                        out.setText(l);
                    } else {
                        sb.append(l);
                        out.setText(l);
                        return false;
                    }
                }
            }

            l = sb.toString();
            modules = l;
            modules = modules.replace(" ", "");
            
            if(!modules.isBlank()){
                modules+=",jdk.localedata";
            }else{
                modules+="jdk.localedata";
            }

            p.setDependencies(modules);
            return pc.exitValue() == 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    //remove package
    private static String removePackage(String m) {
        if (m.contains("/")) {
            return m.substring(0, m.lastIndexOf("/"));
        } else {
            return m;
        }
    }

}
