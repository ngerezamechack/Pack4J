/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Noutput;
import beans.Packager;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 *
 * @author NGEREZA
 */
public class Runtime {
    
    public static boolean jlink(Packager p,Noutput out) throws Exception{
        
        delete(p.getRuntime());
        String jlink = " --verbose --add-modules "+out.getModules()+" "
                + "--include-locales=fr --no-header-files --no-man-pages --strip-debug --compress=2 --output "+p.getRuntime();
        
        
        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator;
        CommandLine cl = new CommandLine(javaBin + "jlink")
                .addArguments(jlink);

        out.setText(cl.getExecutable()+jlink);
        
        DefaultExecuteResultHandler rh = new DefaultExecuteResultHandler();
        ExecuteWatchdog wd = new ExecuteWatchdog(ExecuteWatchdog.INFINITE_TIMEOUT);
        Executor exec = new DefaultExecutor();

        PumpStreamHandler psh = new PumpStreamHandler(out);

        exec.setStreamHandler(psh);
        exec.setWatchdog(wd);

        exec.execute(cl, rh);
        rh.waitFor();

        return exec.isFailure(1);
    }
    
    private static File f;
    private static void delete(String p) throws Exception{
        f = new File(p);
        if(f.exists())
        Files.walk(f.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }
}
