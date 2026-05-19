package com.example.capstone2.Service;

import com.example.capstone2.Api.ApiException;
import com.example.capstone2.Model.Skill;
import com.example.capstone2.Repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;
    public List<Skill> getAllSkill(){
        return skillRepository.findAll();
    }
    public void addSkill (Skill skill){
        if (skill==null){
            throw new ApiException("skill not found");
        }
        skillRepository.save(skill);
    }
    public void updateSkill (Integer id ,Skill skill ){
        Skill oldSkill = skillRepository.findSkillById(id);
        if (oldSkill==null){
            throw new ApiException("skill not found");
        }
        oldSkill.setName(skill.getName());
        skillRepository.save(oldSkill);
    }
    public void deleteSkill (Integer id){
        Skill skill = skillRepository.findSkillById(id);
        if (skill==null){
            throw new ApiException("skill not found");
        }
        skillRepository.delete(skill);
    }
}
