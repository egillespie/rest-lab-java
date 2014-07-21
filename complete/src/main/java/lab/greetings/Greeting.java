package lab.greetings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {
    private final int id;
    private final String language;
    private final String display;

    @JsonCreator
    public Greeting(@JsonProperty("id") int id,
                    @JsonProperty("language") String language,
                    @JsonProperty("display") String display) {
        this.id = id;
        this.language = language;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getDisplay() {
        return display;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int id;
        private String language;
        private String display;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setDisplay(String display) {
            this.display = display;
            return this;
        }

        public Greeting build() {
            return new Greeting(id, language, display);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Greeting greeting = (Greeting) o;

        if (id != greeting.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Greeting{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", display='" + display + '\'' +
                '}';
    }
}
