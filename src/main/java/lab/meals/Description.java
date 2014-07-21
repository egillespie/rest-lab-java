package lab.meals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Description {
    private final String brief;
    private final String full;

    private Description(String brief, String full) {
        this.brief = checkNotNull(brief);
        this.full = checkNotNull(full);
    }

    @JsonCreator
    public static Description of(@JsonProperty("short") String brief,
                                 @JsonProperty("full") String full) {
        return new Description(brief, full);
    }

    @JsonProperty("short")
    public String getBrief() {
        return brief;
    }

    public String getFull() {
        return full;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (!brief.equals(that.brief)) return false;
        if (!full.equals(that.full)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = brief.hashCode();
        result = 31 * result + full.hashCode();
        return result;
    }
}
