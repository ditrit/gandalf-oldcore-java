package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;

public class ReferenceState {

    private Constant.State state;

    public Constant.State getState() {
        return state;
    }

    public ReferenceState(Constant.State state) {
        this.state = state;
    }
}
