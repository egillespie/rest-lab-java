package lab.greetings;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import lab.support.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class GreetingDao {
    private static int SEQUENCE = 1;
    private final Collection<Greeting> repo = Lists.newArrayList();

    public Greeting findById(int id) throws NotFoundException {
        for (Greeting greeting : repo) {
            if (greeting.getId() == id) {
                return greeting;
            }
        }
        throw new NotFoundException(String.format("Unable to find greeting with id %d.", id));
    }

    public Iterable<Greeting> findByLanguage(String language) {
        ImmutableSet.Builder<Greeting> greetings = ImmutableSet.builder();
        for (Greeting greeting : repo) {
            if (greeting.getLanguage().equals(language)) {
                greetings.add(greeting);
            }
        }
        return greetings.build();
    }

    public Iterable<Greeting> findAll() {
        return ImmutableSet.copyOf(repo);
    }

    public Greeting create(Greeting.Builder greetingBuilder) {
        Greeting greeting = greetingBuilder.setId(SEQUENCE++).build();
        repo.add(greeting);
        return greeting;
    }

    public void update(Greeting greeting) throws NotFoundException {
        if (repo.remove(greeting)) {
            repo.add(greeting);
        } else {
            throw new NotFoundException(String.format("Unable to update greeting with id %d.", greeting.getId()));
        }
    }

    public void delete(int id) throws NotFoundException {
        Greeting greeting = findById(id);
        repo.remove(greeting);
    }
}
