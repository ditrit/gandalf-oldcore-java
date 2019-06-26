package com.orness.gandalf.core.module.constantmodule.bash;

public class BashConstant {

    public static final String SCRIPT_BUILD_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_CLONE = "git clone ";
    public static final String SCRIPT_BUILD = "mvn clean install";
    public static final String SCRIPT_DEPLOY_CONF_FILE = "conf.ini";
    public static final String SCRIPT_DEPLOY_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_DEPLOY_FILE = "cgms.sh";
    public static final String SCRIPT_DEPLOY = SCRIPT_DEPLOY_DIRECTORY + "./" + SCRIPT_DEPLOY_FILE;
    //public static final String SCRIPT_DEPLOY_PREFIX_TARGET = "/";
}
