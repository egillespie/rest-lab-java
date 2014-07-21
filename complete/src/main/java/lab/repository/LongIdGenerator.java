package lab.repository;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdGenerator implements InMemoryRepository.IdGenerator<Long> {
    private final AtomicLong lastUsedId = new AtomicLong(0L);

    @Override
    public Long generateId() {
        return lastUsedId.incrementAndGet();
    }
}
