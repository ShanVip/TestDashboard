package com.project.testdashboard.controllers;

import com.project.testdashboard.entities.TestCase;
import com.project.testdashboard.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testcases")
public class TestCaseController {
    private final TestCaseService testCaseService;

    @Autowired
    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createTestCase(@RequestBody TestCase testCase) {
        if (testCase.getName() == null || testCase.getDescription() == null ||
                testCase.getStepsToPlay() == null || testCase.getExpectedResult() == null ||
                testCase.getRealResult() == null) {
            return ResponseEntity.badRequest().body("Ошибка: Все поля должны быть заполнены");
        }

        testCaseService.saveTestCase(testCase);

        return ResponseEntity.ok("Тест-кейс успешно создан!");
    }

    @GetMapping("/{testCaseId}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable long testCaseId) {
        TestCase testCase = testCaseService.getTestCaseById(testCaseId);
        if (testCase == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(testCase);
    }

    @PutMapping("/{testCaseId}")
    public ResponseEntity<TestCase> updateTestCase(@PathVariable long testCaseId, @RequestBody TestCase updatedTestCase) {
        TestCase testCase = testCaseService.getTestCaseById(testCaseId);
        if (testCase == null) {
            return ResponseEntity.notFound().build();
        }
        testCase.setName(updatedTestCase.getName());
        testCase.setDescription(updatedTestCase.getDescription());
        testCase.setStepsToPlay(updatedTestCase.getStepsToPlay());
        testCase.setExpectedResult(updatedTestCase.getExpectedResult());
        testCase.setRealResult(updatedTestCase.getRealResult());

        testCaseService.saveTestCase(testCase);

        return ResponseEntity.ok(testCase);
    }

    @DeleteMapping("/{testCaseId}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable long testCaseId) {
        TestCase testCase = testCaseService.getTestCaseById(testCaseId);
        if (testCase == null) {
            return ResponseEntity.notFound().build();
        }

        testCaseService.deleteTestCase(testCase);

        return ResponseEntity.noContent().build();
    }
}
