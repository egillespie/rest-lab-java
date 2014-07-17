package lab.food;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import lab.repository.Identifiable;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Human implements Identifiable<Long, Human> {
    private final Long id;
    private final String name;
    private final ImmutableSet<Favorite> favorites;

    @JsonCreator
    public Human(@JsonProperty("id") Long id,
                 @JsonProperty("name") String name,
                 @JsonProperty("favorites") Iterable<Favorite> favorites) {
        this.id = id;
        this.name = checkNotNull(name);
        this.favorites = favorites == null ? ImmutableSet.<Favorite>of() : ImmutableSet.copyOf(favorites);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Human humanToCopy) {
        return new Builder(humanToCopy);
    }

    public static class Builder {
        private Long id;
        private String name;
        private ImmutableSet<Favorite> favorites;

        public Builder() {
        }

        public Builder(Human humanToCopy) {
            this.id = humanToCopy.id;
            this.name = humanToCopy.name;
            this.favorites = humanToCopy.favorites;
        }

        public Long getId() {
            return this.id;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setFavorites(Iterable<Favorite> favorites) {
            this.favorites = ImmutableSet.copyOf(favorites);
            return this;
        }

        public Builder merge(Builder updates) {
            if (updates.name != null) {
                this.name = updates.name;
            }
            if (updates.favorites != null) {
                this.favorites = updates.favorites;
            }
            return this;
        }

        public Human build() {
            return new Human(id, name, favorites);
        }
    }

    @Override
    public Human withId(Long id) {
        return builder(this).setId(id).build();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ImmutableSet<Favorite> getFavorites() {
        return favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        if (id != null ? !id.equals(human.id) : human.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
