package lab.orders;

import lab.repository.InMemoryRepository;

public class DefaultOrdersInitializer implements InMemoryRepository.Initializer<Long, Order> {
    @Override
    public void setupData(InMemoryRepository<Long, Order> repository) {
        repository.add(Order.builder().setHumanId(1L).setMealId(1L).build());
        repository.add(Order.builder().setHumanId(2L).setMealId(2L).build());
        repository.add(Order.builder().setHumanId(3L).setMealId(2L).build());
        repository.add(Order.builder().setHumanId(4L).setMealId(1L).build());
        repository.add(Order.builder().setHumanId(5L).setMealId(3L).build());
        repository.add(Order.builder().setHumanId(6L).setMealId(3L).build());
    }
}
