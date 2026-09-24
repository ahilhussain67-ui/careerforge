package carrerforge.controller;

import java.util.List;
import carrerforge.entity.Question;
import carrerforge.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) { this.questionService = questionService; }
    @GetMapping public List<Question> findAll() { return questionService.findAll(); }
    @PostMapping public Question create(@RequestBody Question question) { return questionService.save(question); }
}
