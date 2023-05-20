package com.project.testdashboard.services;


import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BugService {

    private final BugRepository bugRepository;

    @Autowired
    public BugService(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    public Bug saveBug(Bug bug) {
        // Дополнительная обработка или валидация данных перед сохранением (если необходимо)
        return bugRepository.save(bug);
    }

    public int getTotalBugs() {
        return bugRepository.findAll().size();
    }

    public int getOpenBugsCount() {
        return bugRepository.countByStatus("Open");
    }

    public int getClosedBugsCount() {
        return bugRepository.countByStatus("Closed");
    }

    public int getOpenBugsPercentage() {
        int totalBugs = getTotalBugs();
        int openBugs = getOpenBugsCount();

        if (totalBugs == 0) {
            return 0;
        }

        return Math.round((openBugs / (float) totalBugs) * 100);
    }

    public int getClosedBugsPercentage() {
        int totalBugs = getTotalBugs();
        int closedBugs = getClosedBugsCount();

        if (totalBugs == 0) {
            return 0;
        }

        return Math.round((closedBugs / (float) totalBugs) * 100);
    }


    public Bug getBugById(Long bugId) {
        return bugRepository.findById(bugId).orElse(null);
    }
}
