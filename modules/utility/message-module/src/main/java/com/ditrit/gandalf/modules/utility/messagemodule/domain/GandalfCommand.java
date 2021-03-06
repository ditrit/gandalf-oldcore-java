package com.ditrit.gandalf.modules.utility.messagemodule.domain;

import com.google.gson.Gson;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GandalfCommand {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String idReceiver;
    @Column
    private String idSender;
    @Column
    private String typeCommand;
    @Column
    private String command;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getTypeCommand() {
        return typeCommand;
    }

    public void setTypeCommand(String typeCommand) {
        this.typeCommand = typeCommand;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public GandalfCommand() { //JPA
    }

    public GandalfCommand(Long id, String idReceiver, String idSender, String typeCommand, String command) {
        this.id = id;
        this.idReceiver = idReceiver;
        this.idSender = idSender;
        this.typeCommand = typeCommand;
        this.command = command;
    }

    public String toJson() {
        Gson mapper = new Gson();
        return mapper.toJson(this);
    }

    @Override
    public String toString() {
        return "GandalfCommand{" +
                "id=" + id +
                ", idReceiver='" + idReceiver + '\'' +
                ", idSender='" + idSender + '\'' +
                ", typeCommand='" + typeCommand + '\'' +
                ", command='" + command + '\'' +
                '}';
    }
}
