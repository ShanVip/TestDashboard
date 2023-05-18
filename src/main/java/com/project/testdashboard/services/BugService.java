package com.project.testdashboard.services;


import com.project.testdashboard.entities.Bug;
import com.project.testdashboard.repositories.BugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BugService {

    private final BugRepository bugRepository;

    @Autowired
    public BugService(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    public Optional<Bug> getBugById(Long id) {
        return bugRepository.findById(id);
    }

    public Bug createBug(Bug bug) {
        return bugRepository.save(bug);
    }

    public Bug updateBug(Bug bug) {
        return bugRepository.save(bug);
    }

    public void deleteBug(Long id) {
        bugRepository.deleteById(id);
    }

    public int countBugsByStatus(String status) {
        return bugRepository.countByStatus(status);
    }
}
