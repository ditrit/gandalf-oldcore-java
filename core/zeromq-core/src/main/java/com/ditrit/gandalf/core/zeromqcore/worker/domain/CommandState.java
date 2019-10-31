package com.ditrit.gandalf.core.zeromqcore.worker.domain;

public class CommandState {

    public String stateValue;
    public boolean state;
    public String payload;

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public CommandState() {
    }

    public CommandState(String stateValue, boolean state, String payload) {
        this.stateValue = stateValue;
        this.state = state;
        this.payload = payload;
    }
}
