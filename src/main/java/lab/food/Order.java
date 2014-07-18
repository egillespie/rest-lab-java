package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lab.repository.Identifiable;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Order implements Identifiable<Long, Order> {
    private final Long id;
    private final Long humanId;
    private final Long mealId;

    @JsonCreator
    public Order(@JsonProperty("id") Long id,
                 @JsonProperty("human_id") Long humanId,
                 @JsonProperty("meal_id") Long mealId) {
        this.id = id;
        this.humanId = checkNotNull(humanId);
        this.mealId = checkNotNull(mealId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Order orderToCopy) {
        return builder()
                .setId(orderToCopy.id)
                .setHumanId(orderToCopy.humanId)
                .setMealId(orderToCopy.mealId);
    }

    public static class Builder {
        private Long id;
        private Long humanId;
        private Long mealId;

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setHumanId(Long humanId) {
            this.humanId = humanId;
            return this;
        }

        public Builder setMealId(Long mealId) {
            this.mealId = mealId;
            return this;
        }

        public Builder merge(Builder updates) {
            if (updates.humanId != null) {
                this.humanId = updates.humanId;
            }
            if (updates.mealId != null) {
                this.mealId = updates.mealId;
            }
            return this;
        }

        public Order build() {
            return new Order(id, humanId, mealId);
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Order withId(Long id) {
        return builder(this).setId(id).build();
    }

    @JsonProperty("human_id")
    public long getHumanId() {
        return humanId;
    }

    @JsonProperty("meal_id")
    public long getMealId() {
        return mealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
