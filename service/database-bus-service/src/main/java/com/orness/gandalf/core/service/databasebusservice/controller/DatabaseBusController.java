package com.orness.gandalf.core.service.databasebusservice.controller;

import com.orness.gandalf.core.module.messagemodule.gandalf.domain.MessageGandalf;
import com.orness.gandalf.core.module.messagemodule.gandalf.repository.MessageGandalfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class DatabaseBusController {

    private MessageGandalfRepository messageGandalfRepository;

    @Autowired
    public DatabaseBusController(MessageGandalfRepository messageGandalfRepository) {
        this.messageGandalfRepository = messageGandalfRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/database/index")
    public String index(Model model) {
        List<MessageGandalf> messageGandalfList = messageGandalfRepository.findAll();
        model.addAttribute("messageGandalfList", messageGandalfList);
        return "index";
    }
}
