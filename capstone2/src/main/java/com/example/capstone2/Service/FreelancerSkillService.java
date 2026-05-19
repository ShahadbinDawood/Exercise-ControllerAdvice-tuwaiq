package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.FreelancerSkill;
import com.example.capstone2.Repository.FreelancerProfileRepository;
import com.example.capstone2.Repository.FreelancerSkillRepository;
import com.example.capstone2.Repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreelancerSkillService {
    private final FreelancerSkillRepository freelancerSkillRepository;
    private final SkillRepository skillRepository;
    private final FreelancerProfileRepository profileRepository;

    public List<FreelancerSkill> getAllFreelancerSkill() {
        return freelancerSkillRepository.findAll();
    }

    public void addFreelancerSkill(FreelancerSkill freelancerSkill) {
        if (freelancerSkill == null) {
            throw new ApiException("freelancer Skill  not found");
        }
        if (skillRepository.findSkillById(freelancerSkill.getSkillId())==null){
            throw new ApiException(" Skill  not found");
        }
        if (profileRepository.findFreelancerProfileById(freelancerSkill.getFreelancerProfileId())==null){
            throw new ApiException(" Freelancer Profile  not found");
        }
        freelancerSkillRepository.save(freelancerSkill);
    }
    public void updateFreelancerSkill (Integer id , FreelancerSkill freelancerSkill){
        FreelancerSkill oldFreelancerSkill = freelancerSkillRepository.findFreelancerSkillById(id);
        if (oldFreelancerSkill == null) {
            throw new ApiException("freelancer Skill  not found");
        }
        if (skillRepository.findSkillById(freelancerSkill.getSkillId())==null){
            throw new ApiException(" Skill  not found");
        }
        if (profileRepository.findFreelancerProfileById(freelancerSkill.getFreelancerProfileId())==null){
            throw new ApiException(" Freelancer Profile  not found");
        }
        oldFreelancerSkill.setFreelancerProfileId(freelancerSkill.getFreelancerProfileId());
        oldFreelancerSkill.setSkillId(freelancerSkill.getSkillId());
        freelancerSkillRepository.save(oldFreelancerSkill);
    }
    public void deleteFreelancerSkill(Integer id ){
        FreelancerSkill freelancerSkill = freelancerSkillRepository.findFreelancerSkillById(id);
        if (freelancerSkill == null) {
            throw new ApiException("freelancer Skill  not found");
        }
        freelancerSkillRepository.delete(freelancerSkill);
    }
}
