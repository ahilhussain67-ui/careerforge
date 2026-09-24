package carrerforge.service;

import java.util.List;
import carrerforge.entity.Career;
import carrerforge.repository.CareerRepository;
import org.springframework.stereotype.Service;

@Service
public class CareerService {
    private final CareerRepository repository;

    public CareerService(CareerRepository repository) { this.repository = repository; }
    public List<Career> findAll() { return repository.findAll(); }
    public Career save(Career career) { return repository.save(career); }
}
