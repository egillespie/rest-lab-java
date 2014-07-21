package lab.meals;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Ordering;
import lab.repository.Identifiable;

import javax.annotation.Nullable;

public final class Meal implements Identifiable<Long, Meal> {
    public static final Function<Meal, String> NAME_EXTRACTOR = new Function<Meal, String>() {
        @Nullable
        @Override
        public String apply(@Nullable Meal meal) {
            return meal.getName();
        }
    };

    private final Long id;
    private final String name;
    private final Description description;
    private final ImmutableSet<String> ingredients;

    public Meal(Long id,
                String name,
                Description description,
                Iterable<String> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients == null ? ImmutableSet.<String>of() : ImmutableSet.copyOf(ingredients);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Meal mealToCopy) {
        return new Builder(mealToCopy);
    }

    public static class Builder {
        private Long id;
        private String name;
        private Description description;
        private ImmutableSet.Builder<String> ingredients = ImmutableSet.builder();

        public Builder() { }

        public Builder(Meal mealToCopy) {
            this.id = mealToCopy.id;
            this.name = mealToCopy.name;
            this.description = mealToCopy.description;
            ingredients.addAll(mealToCopy.ingredients);
        }

        public Long getId() {
            return id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(Description description) {
            this.description = description;
            return this;
        }

        public Builder addIngredients(Iterable<String> ingredients) {
            this.ingredients.addAll(ingredients);
            return this;
        }

        public Builder addIngredient(String ingredient) {
            this.ingredients.add(ingredient);
            return this;
        }

        public Builder merge(Builder updates) {
            if (updates.name != null) {
                this.name = updates.name;
            }
            if (updates.description != null) {
                this.description = updates.description;
            }
            if (updates.ingredients != null) {
                this.ingredients = updates.ingredients;
            }
            return this;
        }

        public Meal build() {
            return new Meal(id, name, description, ingredients.build());
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Meal withId(Long id) {
        return builder(this).setId(id).build();
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

        if (id != null ? !id.equals(meal.id) : meal.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int compareTo(Meal o) {
        return Ordering.natural().nullsFirst().compare(this.id, o.id);
    }
}
