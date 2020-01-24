package Data.SQL;

/**
 * Functional interface with a function
 * that has no parameters and returns nothing.
 *
 * Used for SQL tasks.
 *
 * @see SQLTask
 */

@FunctionalInterface
public interface Procedure {

    void invoke();

}
