/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author NGEREZA
 */
public class Packager {
    
    
    private String appDir;
    private String appName;
    private String mainJar;
    private String mainClass;
    private String appVersion;
    private String appVendor;
    
    private String type;
    private String dependencies;
    private String runtime;
    private String icon;
    private String dest;
    
    
    public Packager(){
        
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    
    
    
    public void setAppDir(String appDir) {
        this.appDir = appDir;
    }

    
    public String getAppDir() {
        return appDir;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setMainJar(String mainJar) {
        this.mainJar = mainJar;
    }

    public String getMainJar() {
        return mainJar;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    public String getMainClass() {
        return mainClass;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVendor(String appVendor) {
        this.appVendor = appVendor;
    }

    public String getAppVendor() {
        return appVendor;
    }

    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

    public String getDependencies() {
        return dependencies;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDest() {
        return dest;
    }
   
    
}
