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
}
