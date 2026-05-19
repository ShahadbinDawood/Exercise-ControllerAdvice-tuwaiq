package com.example.capstone2.Repository;

import com.example.capstone2.Model.FreelancerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreelancerProfileRepository extends JpaRepository<FreelancerProfile,Integer> {
    FreelancerProfile findFreelancerProfileById(Integer id);
    FreelancerProfile findFreelancerProfilesByUserId(Integer userId);

    List<FreelancerProfile> findFreelancerProfilesByHourlyRateLessThanEqual(double hourlyRateIsGreaterThan);

}
