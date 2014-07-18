package lab.food;

import lab.repository.InMemoryRepository;

public class DefaultOrdersInitializer implements InMemoryRepository.Initializer<Long, Order> {
    @Override
    public void setupData(InMemoryRepository<Long, Order> repository) {
        repository.add(Order.builder().setHumanId(1L).setMealId(1L).build());
    }
}
