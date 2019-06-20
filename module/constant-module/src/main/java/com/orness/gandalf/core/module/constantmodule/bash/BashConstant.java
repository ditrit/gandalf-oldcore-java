package com.orness.gandalf.core.module.constantmodule.bash;

public class BashConstant {

    public static final String SCRIPT_BUILD_DIRECTORY = "/";
    public static final String SCRIPT_CLONE = SCRIPT_BUILD_DIRECTORY + "git clone ";
    public static final String SCRIPT_BUILD = "/mvn clean install";
    public static final String SCRIPT_DEPLOY_DIRECTORY = "/";
    public static final String SCRIPT_DEPLOY_FILE = "cgms.sh";
    public static final String SCRIPT_DEPLOY = SCRIPT_DEPLOY_DIRECTORY + "./" + SCRIPT_DEPLOY_FILE;
    public static final String SCRIPT_DEPLOY_PREFIX_TARGET = "/";
}
