package carrerforge.service;

import java.util.List;
import carrerforge.entity.Question;
import carrerforge.repository.QuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) { this.repository = repository; }
    public List<Question> findAll() { return repository.findAll(); }
    public Question save(Question question) { return repository.save(question); }
}
