package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.repositories.BugRepository;
import com.project.testdashboard.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bugs")
public class BugController {
    private final BugService bugService;
    private final BugRepository bugRepository;

    @Autowired
    public BugController(BugService bugService, BugRepository bugRepository) {
        this.bugService = bugService;
        this.bugRepository = bugRepository;
    }

    @GetMapping("/form")
    public String showBugForm() {
        return "bug-form";
    }

    @PostMapping("/submit")
    public String submitBug(Bug bug, Model model) {
        // Сохранение бага в базе данных
        bugRepository.save(bug);

        // Передача данных в модель для отображения на странице
        model.addAttribute("bug", bug);

        return "bug-details";
    }

    @GetMapping("/summary")
    public String getBugSummary(Model model) {
        int newBugs = bugService.countBugsByStatus("New");
        int closedBugs = bugService.countBugsByStatus("Closed");
        model.addAttribute("newBugs", newBugs);
        model.addAttribute("closedBugs", closedBugs);
        return "bug-summary";
    }
}