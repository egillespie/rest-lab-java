package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Favorite {
    private final long mealId;
    private final double rating;

    @JsonCreator
    public Favorite(@JsonProperty("meal_id") long mealId,
                    @JsonProperty("rating") double rating) {
        this.mealId = mealId;
        this.rating = rating;
    }

    public static Favorite of(long mealId, double rating) {
        return new Favorite(mealId, rating);
    }

    public long getMealId() {
        return mealId;
    }

    public double getRating() {
        return rating;
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
