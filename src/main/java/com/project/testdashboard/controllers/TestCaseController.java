package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.TestCase;
import com.project.testdashboard.services.BugService;
import com.project.testdashboard.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/testcase")
public class TestCaseController {
    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveTestCase(@RequestParam("name") String name,
                                          @RequestParam("description") String description,
                                          @RequestParam("stepsToPlay") String stepsToPlay,
                                          @RequestParam("expectedResult") String expectedResult,
                                          @RequestParam("realResult") String realResult) {
        if (name.isEmpty() || description.isEmpty() || stepsToPlay.isEmpty() || expectedResult.isEmpty() || realResult.isEmpty()) {
            return ResponseEntity.badRequest().body("Ошибка: Все поля должны быть заполнены");
        }


        TestCase testCase = new TestCase();
        testCase.setName(name);
        testCase.setDescription(description);
        testCase.setStepsToPlay(stepsToPlay);
        testCase.setExpectedResult(expectedResult);
        testCase.setRealResult(realResult);

        testCaseService.saveTestCase(testCase);

        return ResponseEntity.ok("Bug registered successfully!");
    }
}
