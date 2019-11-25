package com.ditrit.gandalf.gandalfjava.services.buildservice.constant;

public class BuildConstant {

    public static final String SCRIPT_BUILD_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_CLONE = "git clone";
    public static final String SCRIPT_BUILD = "mvn clean install -DskipTests";
    public static final String SCRIPT_TAR = "tar -czf ";
    public static final String SCRIPT_UNTAR = "tar -xzf ";
    public static final String SCRIPT_DEPLOY_DIRECTORY = System.getProperty("user.dir") + "/builds";
}
