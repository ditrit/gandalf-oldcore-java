package com.ditrit.gandalf.core.clustercore.constant;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;

public class ClusterConstant extends Constant {

    public static final String WORKER_SERVICE_CLASS_CAPTURE_URL_CMD = "http://arangodb.service.gandalf:8529/_db/gandalf/keep/cmd";
    public static final String WORKER_SERVICE_CLASS_CAPTURE_URL_EVENT = "http://arangodb.service.gandalf:8529/_db/gandalf/keep/event";
    
}
