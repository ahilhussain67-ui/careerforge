package carrerforge.controller;

import java.util.List;
import carrerforge.entity.Career;
import carrerforge.service.CareerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/careers")
public class CareerController {
    private final CareerService careerService;

    public CareerController(CareerService careerService) { this.careerService = careerService; }
    @GetMapping public List<Career> findAll() { return careerService.findAll(); }
    @PostMapping public Career create(@RequestBody Career career) { return careerService.save(career); }
}
