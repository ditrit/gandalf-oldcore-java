package com.orness.gandalf.core.module.webhookmodule.domain;

import com.orness.gandalf.core.module.webhookmodule.parser.CustomMergeRequestParser;

public class CustomMergeRequest {

    private String projectName;
    private String projectHttpUrl;
    private String projectSshUrl;
    private String projectHttpUrlGit;
    private String projectSshUrlGit;
    private String mergeSource;
    private String mergeTarget;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectHttpUrl() {
        return projectHttpUrl;
    }

    public void setProjectHttpUrl(String projectHttpUrl) {
        this.projectHttpUrl = projectHttpUrl;
    }

    public String getProjectSshUrl() {
        return projectSshUrl;
    }

    public void setProjectSshUrl(String projectSshUrl) {
        this.projectSshUrl = projectSshUrl;
    }

    public String getProjectHttpUrlGit() {
        return projectHttpUrlGit;
    }

    public void setProjectHttpUrlGit(String projectHttpUrlGit) {
        this.projectHttpUrlGit = projectHttpUrlGit;
    }

    public String getProjectSshUrlGit() {
        return projectSshUrlGit;
    }

    public void setProjectSshUrlGit(String projectSshUrlGit) {
        this.projectSshUrlGit = projectSshUrlGit;
    }

    public String getMergeSource() {
        return mergeSource;
    }

    public void setMergeSource(String mergeSource) {
        this.mergeSource = mergeSource;
    }

    public String getMergeTarget() {
        return mergeTarget;
    }

    public void setMergeTarget(String mergeTarget) {
        this.mergeTarget = mergeTarget;
    }

    public CustomMergeRequest(String projectName, String projectHttpUrl, String projectSshUrl, String projectHttpUrlGit, String projectSshUrlGit, String mergeSource, String mergeTarget) {
        this.projectName = projectName;
        this.projectHttpUrl = projectHttpUrl;
        this.projectSshUrl = projectSshUrl;
        this.projectHttpUrlGit = projectHttpUrlGit;
        this.projectSshUrlGit = projectSshUrlGit;
        this.mergeSource = mergeSource;
        this.mergeTarget = mergeTarget;
    }

    public CustomMergeRequest() {
    }

    @Override
    public String toString() {
        return "CustomMergeRequest{" +
                "projectName='" + projectName + '\'' +
                ", projectHttpUrl='" + projectHttpUrl + '\'' +
                ", projectSshUrl='" + projectSshUrl + '\'' +
                ", projectHttpUrlGit='" + projectHttpUrlGit + '\'' +
                ", projectSshUrlGit='" + projectSshUrlGit + '\'' +
                ", mergeSource='" + mergeSource + '\'' +
                ", mergeTarget='" + mergeTarget + '\'' +
                '}';
    }

    public String toStringJackson() {
        return CustomMergeRequestParser.parseObjectToString(this);
    }
}
