package com.example.capstone2.Repository;

import com.example.capstone2.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
        Project findProjectById(Integer id);
        List<Project> findProjectsByStatus(String status);
        List<Project> findProjectsByBudgetBetween(int budgetAfter, int budgetBefore);
}
