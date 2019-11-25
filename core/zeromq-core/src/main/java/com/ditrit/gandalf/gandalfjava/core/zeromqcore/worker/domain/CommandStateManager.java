package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStateManager {

    private Map<String, List<CommandState>> mapUUIDCommandStates;
    private Map<String, ReferenceState> mapUUIDState;

    public Map<String, List<CommandState>> getMapUUIDCommandStates() {
        return mapUUIDCommandStates;
    }

    public void setMapUUIDCommandStates(Map<String, List<CommandState>> mapUUIDCommandStates) {
        this.mapUUIDCommandStates = mapUUIDCommandStates;
    }

    public Map<String, ReferenceState> getMapUUIDState() {
        return mapUUIDState;
    }

    public void setMapUUIDState(Map<String, ReferenceState> mapUUIDState) {
        this.mapUUIDState = mapUUIDState;
    }

    public CommandStateManager() {
        this.mapUUIDCommandStates = new HashMap<String, List<CommandState>>();
        this.mapUUIDState = new HashMap<String, ReferenceState>();
    }

    public List<CommandState> getMapUUIDCommandStatesByUUID(String uuid) {
        return this.mapUUIDCommandStates.get(uuid);
    }

    public ReferenceState getMapUUIDStateByUUID(String uuid) {
        return this.mapUUIDState.get(uuid);
    }
}
