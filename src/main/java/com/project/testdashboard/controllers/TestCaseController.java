package com.project.testdashboard.controllers;


import com.project.testdashboard.entities.TestCase;
import com.project.testdashboard.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/testcases")
public class TestCaseController {
    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("/")
    public String getAllTestCase(@RequestParam(defaultValue = "without") Optional<String> sort, Model model) {
        List<TestCase> entities;

        switch (sort.get()) {
            case "id":
                entities = testCaseService.getAllEntitiesSortedById();
                break;
            case "name":
                entities = testCaseService.getAllEntitiesSortedByName();
                break;

            default:
                entities = testCaseService.getAllEntities();
        }

        model.addAttribute("testCases", entities);
        return "testCases-index";
    }




    @GetMapping("/{testCaseId}")
    public String getEntity(@PathVariable long testCaseId, Model model){

        TestCase testCase = testCaseService.getBugById(testCaseId);
        model.addAttribute("testcase", testCase);
        return "testcase-index";
    }

    @GetMapping("/create")
    public String showBugForm() {
        return "testcase-create";
    }

    @PostMapping("/create")
    public String saveTestCase(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stepsToPlay") String stepsToPlay,
            @RequestParam("expectedResult") String expectedResult,
            @RequestParam("realResult") String realResult,
            Model model
    ) {
        if (name.isEmpty() || description.isEmpty() || stepsToPlay.isEmpty() || expectedResult.isEmpty() || realResult.isEmpty()) {
            model.addAttribute("errorMessage", "Ошибка: Все поля должны быть заполнены");
            return "testCase-create";
        }

       TestCase testCase = new TestCase();
        testCase.setName(name);
        testCase.setDescription(description);
        testCase.setStepsToPlay(stepsToPlay);
        testCase.setExpectedResult(expectedResult);
        testCase.setRealResult(realResult);


        testCaseService.saveTestCase(testCase);
        return "redirect:/testcases/";
    }

    @GetMapping("/{testCaseId}/update")
    public String updateBugPage(@PathVariable long testCaseId, Model model){
        TestCase testCase = testCaseService.getBugById(testCaseId);
        if (testCase == null) {
            return "Bug not found!";
        }
        model.addAttribute("testcase", testCase);
        return "testcase-update";
    }

    @PostMapping("/{testCaseId}/update")
    public String updateTestCaseStatus(
            @RequestParam("name") Optional<String> name,
            @RequestParam("description") Optional<String> description,
            @RequestParam("stepsToPlay") Optional<String> stepsToPlay,
            @RequestParam("expectedResult") Optional<String> expectedResult,
            @RequestParam("realResult") Optional<String> realResult,
            @PathVariable("testCaseId") Long testCaseId,
            Model model) {
        TestCase testCase = testCaseService.getBugById(testCaseId);

        if (testCase == null) {
            model.addAttribute("errorMessage", "Bug not found!");
            return "testcase-update";
        }

        if (name.get() != "") testCase.setName(name.get());
        if (description.get() != "") testCase.setDescription(description.get());
        if (stepsToPlay.get() != "") testCase.setStepsToPlay(String.valueOf(stepsToPlay.get()));
        if (expectedResult.get()!= "") testCase.setExpectedResult(String.valueOf(expectedResult.get()));
        if (realResult.get()!= "") testCase.setRealResult(String.valueOf(realResult.get()));

        testCaseService.saveTestCase(testCase);


        return "redirect:/testcases/";
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteBug(@RequestParam long testCaseId) {
        TestCase testCase = testCaseService.getBugById(testCaseId);
        if (testCase != null) {
            testCaseService.deleteTestCase(testCase);
        }

        return "redirect:/testcases/";
    }

}

