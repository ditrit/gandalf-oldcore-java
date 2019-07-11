package com.orness.gandalf.core.module.constantmodule.bash;

public class BashConstant {

    public static final String SCRIPT_BUILD_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_CLONE = "git clone ";
    public static final String SCRIPT_BUILD = "mvn clean install -DskipTests";
    public static final String SCRIPT_TAR = "tar -czf";
    public static final String SCRIPT_DEPLOY_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_COMMAND_FILE = "manage_svc.sh";
    public static final String SCRIPT_REGISTER_FILE = "register.sh";
    public static final String SCRIPT_RESSOURCES_FILE = "script";
    public static final String SCRIPT_RESSOURCES_DIRECTORY = System.getProperty("user.dir") + "/script";
    //public static final String SCRIPT_DEPLOY_PREFIX_TARGET = "/";
}
