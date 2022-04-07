import java.util.Objects;

/**
 * This record represents an element of a {@link Stack}.
 *
 * @param <T>                    The generic type for the value contained in an instance of this class.
 * @param val                    The value contained in the instance.
 * @param previousElementInStack The (reference to) the previous element in the {@link Stack}
 *                               or null if you are adding the first element of the stack.
 * @author Matteo Ferfoglia
 */
record StackElement<T>(T val, StackElement<T> previousElementInStack) {
    @Override
    public String toString() {
        return String.valueOf(val);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StackElement<?> that = (StackElement<?>) o;

        if (!Objects.equals(val, that.val)) return false;
        return Objects.equals(previousElementInStack, that.previousElementInStack);
    }

    @Override
    public int hashCode() {
        int result = val != null ? val.hashCode() : 0;
        result = 31 * result + (previousElementInStack != null ? previousElementInStack.hashCode() : 0);
        return result;
    }
}