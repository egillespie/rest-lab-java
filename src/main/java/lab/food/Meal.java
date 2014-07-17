package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Meal {
    private final long id;
    private final String name;
    private final Description description;
    private final ImmutableSet<String> ingredients;

    @JsonCreator
    public Meal(@JsonProperty("id") long id,
                @JsonProperty("name") String name,
                @JsonProperty("description") Description description,
                @JsonProperty("ingredients") Iterable<String> ingredients) {
        this.id = id;
        this.name = checkNotNull(name);
        this.description = checkNotNull(description);
        this.ingredients = ImmutableSet.copyOf(ingredients);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Description getDescription() {
        return description;
    }

    public ImmutableSet<String> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        if (id != meal.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
