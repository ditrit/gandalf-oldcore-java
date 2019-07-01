package com.orness.gandalf.core.connector.connectorversioncontrolservice.domain;

public class CustomMergeRequest {

    private String projectName;
    private String projectUrl;
    private String mergeSource;
    private String mergeTarget;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
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

    public CustomMergeRequest(String projectName, String projectUrl, String mergeSource, String mergeTarget) {
        this.projectName = projectName;
        this.projectUrl = projectUrl;
        this.mergeSource = mergeSource;
        this.mergeTarget = mergeTarget;
    }

    @Override
    public String toString() {
        return "CustomMergeRequest{" +
                "projectName='" + projectName + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                ", mergeSource='" + mergeSource + '\'' +
                ", mergeTarget='" + mergeTarget + '\'' +
                '}';
    }
}
