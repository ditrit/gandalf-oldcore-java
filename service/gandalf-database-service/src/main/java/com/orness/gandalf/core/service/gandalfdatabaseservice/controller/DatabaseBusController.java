package com.orness.gandalf.core.service.gandalfdatabaseservice.controller;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import com.orness.gandalf.core.module.messagemodule.gandalf.repository.GandalfMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DatabaseBusController {

    private GandalfMessageRepository gandalfMessageRepository;

    @Autowired
    public DatabaseBusController(GandalfMessageRepository gandalfMessageRepository) {
        this.gandalfMessageRepository = gandalfMessageRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/database/index")
    public String index(Model model) {
        List<GandalfMessage> gandalfMessageList = gandalfMessageRepository.findAll();
        model.addAttribute("gandalfMessageList", gandalfMessageList);
        return "index";
    }
}
