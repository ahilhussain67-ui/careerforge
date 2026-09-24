package carrerforge.controller;

import java.util.List;
import carrerforge.entity.Skill;
import carrerforge.service.SkillService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) { this.skillService = skillService; }
    @GetMapping public List<Skill> findAll() { return skillService.findAll(); }
    @PostMapping public Skill create(@RequestBody Skill skill) { return skillService.save(skill); }
}
