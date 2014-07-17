package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Order {
    private final long id;
    private final long humanId;
    private final long mealId;

    @JsonCreator
    public Order(@JsonProperty("id") long id,
                 @JsonProperty("human_id") long humanId,
                 @JsonProperty("meal_id") long mealId) {
        this.id = id;
        this.humanId = humanId;
        this.mealId = mealId;
    }

    public long getId() {
        return id;
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
