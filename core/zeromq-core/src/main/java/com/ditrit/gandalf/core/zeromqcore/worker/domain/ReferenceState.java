package com.ditrit.gandalf.core.zeromqcore.worker.domain;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;

public class ReferenceState {

    private Constant.State state;

    public Constant.State getState() {
        return state;
    }

    public ReferenceState(Constant.State state) {
        this.state = state;
    }
}
