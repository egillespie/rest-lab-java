package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class Favorite {
    private final long id;
    private final long mealId;
    private final double rating;

    @JsonCreator
    public Favorite(@JsonProperty("id") long id,
                    @JsonProperty("meal_id") long mealId,
                    @JsonProperty("rating") double rating) {
        this.id = id;
        this.mealId = mealId;
        this.rating = rating;
    }

    public static Favorite of(long id, long mealId, double rating) {
        return new Favorite(id, mealId, rating);
    }

    public long getId() {
        return id;
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

        if (id != favorite.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
