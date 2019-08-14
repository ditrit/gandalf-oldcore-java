package com.orness.gandalf.core.module.artifactmodule.constant;

public abstract class ArtifactConstant {

    public static final String COMMAND_LIST_REPOSITORIES = "COMMAND_LIST_REPOSITORIES";
    public static final String COMMAND_LIST_ARTIFACTS = "COMMAND_LIST_ARTIFACT";
    public static final String COMMAND_DOWNLOAD_ARTIFACT = "COMMAND_DOWNLOAD_ARTIFACT";
    public static final String COMMAND_UPLOAD_ARTIFACT = "COMMAND_UPLOAD_ARTIFACT";
    public static final String COMMAND_DELETE_ARTIFACT = "COMMAND_DELETE_ARTIFACT";

    public static final String URL_CONTROLLER = "/artifact";
    public static final String URL_CONTROLLER_LIST_REPOSITORIES = "/list/repositories";
    public static final String URL_CONTROLLER_LIST_ARTIFACTS = "/list/artifacts";
    public static final String URL_CONTROLLER_DOWNLOAD_ARTIFACT = "/upload/{id}";
    public static final String URL_CONTROLLER_UPLOAD_ARTIFACT = "/download";
    public static final String URL_CONTROLLER_DELETE_ARTIFACT = "/delete/{id}";

}
