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

import java.util.Calendar;
import java.util.Date;


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
                                          @RequestParam("priority") String priority) {
        if (name.isEmpty() || description.isEmpty() || stepsToPlay.isEmpty() || expectedResult.isEmpty() || realResult.isEmpty() || priority.isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: Все поля должны быть заполнены");
        }

        if (!isValidPriority(priority)) {
            return ResponseEntity.badRequest().body("Invalid priority value. Allowed values: easy, medium, high");
        }

        Bug bug = new Bug();
        bug.setName(name);
        bug.setDescription(description);
        bug.setStepsToPlay(stepsToPlay);
        bug.setExpectedResult(expectedResult);
        bug.setRealResult(realResult);
        bug.setPriority(priority);

        bug.setStatus("Open");


        Date currentTime = Calendar.getInstance().getTime();
        bug.setCreatedAt(currentTime);

        bugService.saveBug(bug);

        return ResponseEntity.ok("Bug registered successfully!");
    }

    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("easy") ||
                priority.equalsIgnoreCase("medium") ||
                priority.equalsIgnoreCase("high");
    }

    @GetMapping("/status-summary")
    public ResponseEntity<String> getStatusSummary() {
        int totalBugs = bugService.getTotalBugs();
        int openBugs = bugService.getOpenBugsCount();
        int closedBugs = bugService.getClosedBugsCount();

        int openPercentage = (int) Math.round((double) openBugs / totalBugs * 100);
        int closedPercentage = (int) Math.round((double) closedBugs / totalBugs * 100);

        String summary = "Open Bugs: " + openBugs + " (" + openPercentage + "%)\n" +
                "Closed Bugs: " + closedBugs + " (" + closedPercentage + "%)";

        return ResponseEntity.ok(summary);
    }


}
