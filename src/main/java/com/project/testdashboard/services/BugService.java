package com.project.testdashboard.services;


import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BugService {

    private final BugRepository bugRepository;

    @Autowired
    public BugService(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    public void deleteBug(Bug bug) {
        bugRepository.delete(bug);
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

    public List<Bug> getAllEntities() {
        return bugRepository.findAll();
    }


    public Bug getBugById(Long bugId) {
        return bugRepository.findById(bugId).orElse(null);
    }
    public List<Bug> getAllEntitiesSortedById() {
        return bugRepository.findAllByOrderById();
    }
    public List<Bug> getAllEntitiesSortedByName() {
        return bugRepository.findAllByOrderByName();
    }

    public List<Bug> getAllEntitiesSortedByPriority() {
        return  bugRepository.findAllByOrderByPriority();
    }

    public List<Bug> getAllEntitiesSortedByStatus() {
        return  bugRepository.findAllByOrderByStatus();
    }
}
