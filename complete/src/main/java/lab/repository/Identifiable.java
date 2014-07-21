package lab.repository;

/**
 * A class, T, where instances can be uniquely identified by a single value, S.
 *
 * @param <S> The type of the identifying value.
 * @param <T> The class whose instances are being identified.
 */
public interface Identifiable<S, T> {
    S getId();
    T withId(S id);
}
