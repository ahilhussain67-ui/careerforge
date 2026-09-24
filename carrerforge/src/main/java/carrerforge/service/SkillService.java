package carrerforge.service;

import java.util.List;
import carrerforge.entity.Skill;
import carrerforge.repository.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillService {
    private final SkillRepository repository;

    public SkillService(SkillRepository repository) { this.repository = repository; }
    public List<Skill> findAll() { return repository.findAll(); }
    public Skill save(Skill skill) { return repository.save(skill); }
}
