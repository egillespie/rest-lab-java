package lab.humans;

import com.google.common.collect.ImmutableSet;
import lab.humans.favorites.Favorite;
import lab.repository.InMemoryRepository;

public class DefaultHumansInitializer implements InMemoryRepository.Initializer<Long, Human> {
    @Override
    public void setupData(InMemoryRepository<Long, Human> repository) {
        repository.add(Human.builder().setName("Joe").setFavorites(ImmutableSet.of(Favorite.of(1L))).build());
    }
}
