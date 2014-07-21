package lab.humans;

import lab.repository.InMemoryRepository;
import lab.repository.LongIdGenerator;
import org.springframework.stereotype.Controller;

@Controller
public class Humans {
    private final InMemoryRepository<Long, Human> humanRepository = new InMemoryRepository<Long, Human>(new LongIdGenerator(), new DefaultHumansInitializer());
}
