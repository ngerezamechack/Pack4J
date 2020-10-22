/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.Noutput;
import beans.Packager;
import java.io.File;
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
public class Dependencies {

    public static boolean jdeps(Packager p, Noutput out) throws Exception {

        String jdepsArgs = " --ignore-missing-deps --no-recursive  --list-deps " + p.getAppDir() + "*.jar";

        String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator;
        CommandLine cl = new CommandLine(javaBin + "jdeps")
                .addArguments(jdepsArgs);

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

}
