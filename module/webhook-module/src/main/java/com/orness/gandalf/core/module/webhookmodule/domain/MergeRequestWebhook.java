package com.orness.gandalf.core.module.webhookmodule.domain;

public class MergeRequestWebhook {

    private String object_kind;
    private WebhookUser user;
    private String project_id;
    private WebhookProject project;
    private WebhookRepository repository;
    private WebhookObjectAttributes object_attributes;
    private WebhookMergeRequest merge_request;
    private String work_in_progress;
    private WebhookUser[] assignee;
}
