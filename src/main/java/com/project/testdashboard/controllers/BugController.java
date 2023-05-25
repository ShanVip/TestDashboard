package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.services.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping("/bugs")
public class BugController {
    private final BugService bugService;


    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("blocker") ||
                priority.equalsIgnoreCase("trivial") ||
                priority.equalsIgnoreCase("low")  ||
                priority.equalsIgnoreCase("high");
    }

    @Autowired
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/")
    public String getAllEntities(@RequestParam(defaultValue = "without") Optional<String> sort, Model model) {
        List<Bug> entities;

        switch (sort.get()){
            case "id":
                entities = bugService.getAllEntitiesSortedById();
                break;
            case "name":
                entities = bugService.getAllEntitiesSortedByName();
                break;
            case "priority":
                entities = bugService.getAllEntitiesSortedByPriority();
                break;
            case "status":
                entities = bugService.getAllEntitiesSortedByStatus();
                break;

            default:
                entities = bugService.getAllEntities();
        }

        model.addAttribute("bugs", entities);
        return "bugs-index";
    }

    @GetMapping("/{bugId}")
    public String getEntity(@PathVariable long bugId, Model model){

        Bug bug = bugService.getBugById(bugId);
        model.addAttribute("bug", bug);
        return "bug-index";
    }

    @GetMapping("/create")
    public String showBugForm() {
        return "bug-create"; // Имя шаблона формы (HTML страницы) для создания нового бага
    }

    @PostMapping("/create")
    public String saveBug(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stepsToPlay") String stepsToPlay,
            @RequestParam("expectedResult") String expectedResult,
            @RequestParam("realResult") String realResult,
            @RequestParam("priority") String priority,
            Model model
    ) {
        if (name.isEmpty() || description.isEmpty() || stepsToPlay.isEmpty() || expectedResult.isEmpty() || realResult.isEmpty() || priority.isEmpty()) {
            model.addAttribute("errorMessage", "Ошибка: Все поля должны быть заполнены");
            return "bug-create";
        }

        if (!isValidPriority(priority)) {
            model.addAttribute("errorMessage","Invalid priority value. Allowed values: easy, medium, high");
            return "bug-create";
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
        return "redirect:/bugs/";
    }

    @GetMapping("/status-summary")
    public String getStatusSummary(Model model) {
        int totalBugs = bugService.getTotalBugs();
        int openBugs = bugService.getOpenBugsCount();
        int closedBugs = bugService.getClosedBugsCount();

        int openPercentage = (int) Math.round((double) openBugs / totalBugs * 100);
        int closedPercentage = (int) Math.round((double) closedBugs / totalBugs * 100);

        model.addAttribute("openedBugCount", openBugs);
        model.addAttribute("openedBugPercentage", openPercentage);
        model.addAttribute("closedBugCount", closedBugs);
        model.addAttribute("closedBugPercentage", closedPercentage);
        return "bug-status-total";
    }
    @GetMapping("/{bugId}/update")
    public String updateBugPage(@PathVariable long bugId, Model model){
        Bug bug = bugService.getBugById(bugId);
        if (bug == null) {
            return "Bug not found!";
        }
        model.addAttribute("bug", bug);
        return "bug-update";
    }

    @PostMapping("/{bugId}/update")
    public String updateBugStatus(
            @RequestParam("name") Optional<String> name,
            @RequestParam("description") Optional<String> description,
            @RequestParam("stepsToPlay") Optional<String> stepsToPlay,
            @RequestParam("expectedResult") Optional<String> expectedResult,
            @RequestParam("realResult") Optional<String> realResult,
            @RequestParam("priority") Optional<String> priority,
            @RequestParam("status") Optional<String> status,
            @PathVariable("bugId") Long bugId) {
        Bug bug = bugService.getBugById(bugId);

        if (bug == null) {
            return "Bug not found!";
        }

        if (name.get() != "") bug.setName(name.get());
        if (description.get() != "") bug.setDescription(description.get());
        if (stepsToPlay.get() != "") bug.setStepsToPlay(String.valueOf(stepsToPlay.get()));
        if (expectedResult.get()!= "") bug.setExpectedResult(String.valueOf(expectedResult.get()));
        if (realResult.get()!= "") bug.setRealResult(String.valueOf(realResult.get()));
        if (priority.get()!= "") bug.setPriority(String.valueOf(priority.get()));
        if (status.get()!= "") bug.setStatus(String.valueOf(status.get()));

        bugService.saveBug(bug);

        return "redirect:/bugs/";
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBug(@RequestParam long bugId) {
        Bug bug = bugService.getBugById(bugId);
        if (bug != null) {
            bugService.deleteBug(bug);
        }

        return "redirect:/bugs/";
    }

}
