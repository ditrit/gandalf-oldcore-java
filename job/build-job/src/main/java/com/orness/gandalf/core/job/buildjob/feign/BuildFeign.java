package com.orness.gandalf.core.job.buildjob.feign;

import com.google.gson.JsonObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "job.endPointName", url = "job.endPointConnection")
public interface BuildFeign {

    @RequestMapping(method = RequestMethod.POST, value = "/build/{projectName}")
    JsonObject build(@PathVariable("projectName") String projectName, @RequestBody String projectUrl);
}
