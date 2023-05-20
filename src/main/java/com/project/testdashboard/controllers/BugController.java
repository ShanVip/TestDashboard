package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/bugs")
public class BugController {
    private final BugService bugService;

    @Autowired
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/form")
    public String showBugForm() {
        return "bug-form"; // Имя шаблона формы (HTML страницы) для создания нового бага
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBug(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("stepsToPlay") String stepsToPlay,
                                          @RequestParam("expectedResult") String expectedResult,
                                          @RequestParam("realResult") String realResult,
                                          @RequestParam("status") String status) {
        Bug bug = new Bug();
        bug.setName(name);
        bug.setDescription(description);
        bug.setStepsToPlay(stepsToPlay);
        bug.setExpectedResult(expectedResult);
        bug.setRealResult(realResult);
        bug.setStatus(status);

        bugService.saveBug(bug);

        return ResponseEntity.ok("Bug registered successfully!");

    }
}
