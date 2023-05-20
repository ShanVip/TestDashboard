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


    public Bug getBugById(Long bugId) {
        return bugRepository.findById(bugId).orElse(null);
    }
}
