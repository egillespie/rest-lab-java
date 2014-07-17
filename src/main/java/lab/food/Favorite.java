package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Favorite {
    private final long mealId;

    @JsonCreator
    public Favorite(@JsonProperty("meal_id") long mealId) {
        this.mealId = mealId;
    }

    public static Favorite of(long mealId) {
        return new Favorite(mealId);
    }

    public long getMealId() {
        return mealId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Favorite favorite = (Favorite) o;

        if (mealId != favorite.mealId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (mealId ^ (mealId >>> 32));
    }
}
