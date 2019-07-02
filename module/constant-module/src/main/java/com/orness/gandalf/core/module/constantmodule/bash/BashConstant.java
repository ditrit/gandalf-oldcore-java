package com.orness.gandalf.core.module.constantmodule.bash;

public class BashConstant {

    public static final String SCRIPT_BUILD_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_CLONE = "git clone ";
    public static final String SCRIPT_BUILD = "mvn clean install";
    public static final String SCRIPT_DEPLOY_CONF_FILE = "conf.ini";
    public static final String SCRIPT_DEPLOY_DIRECTORY = System.getProperty("user.dir") + "/builds";
    public static final String SCRIPT_COMMAND_FILE = "manage_svc.sh";
    public static final String SCRIPT_REGISTER_FILE = "feign.sh";
    public static final String SCRIPT_RESSOURCES_FILE = "classpath:script";
    //public static final String SCRIPT_DEPLOY_PREFIX_TARGET = "/";
}
