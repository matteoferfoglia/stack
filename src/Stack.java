import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Objects;

/**
 * Implementation of a generic thread-safe stack.
 * The stack can contain at most {@link Integer#MAX_VALUE} elements.
 *
 * @param <T> The generic type for elements to be contained in the stack.
 * @author Matteo Ferfoglia
 */
public class Stack<T> implements Iterable<T> {

    private volatile int size = 0;
    private StackElement<T> lastInserted = null;

    /**
     * @return the size of the stack.
     */
    public int size() {
        return size;
    }

    /**
     * @return true if the stack is empty, false otherwise.
     */
    public synchronized boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Removes all elements from this instance.
     */
    public synchronized void clear() {
        while (!isEmpty()) {
            pop();
        }
    }

    /**
     * Inserts a new element in this stack.
     *
     * @param el The new element to add.
     * @throws EmptyStackException if the stack is full.
     */
    public synchronized void push(T el) throws StackIsFullException {
        if (size() == Integer.MAX_VALUE) {
            throw new StackIsFullException();
        } else {
            lastInserted = new StackElement<>(el, lastInserted);
            size++;
        }
    }

    /**
     * @return the last inserted element.
     * @throws EmptyStackException if the stack is empty.
     */
    public synchronized T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        } else {
            var val = lastInserted.val();
            lastInserted = lastInserted.previousElementInStack();
            size--;
            return val;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stack<?> stack = (Stack<?>) o;

        if (size != stack.size) return false;
        return Objects.equals(lastInserted, stack.lastInserted);
    }

    @Override
    public int hashCode() {
        int result = size;
        result = 31 * result + (lastInserted != null ? lastInserted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Stack{" + " size=" + size + ": [");
        {
            // Print elements
            boolean isFirstElement = true;
            for (var el : this) {
                if (isFirstElement) {
                    isFirstElement = false;
                } else {
                    sb.append(", ");
                }
                sb.append(el);
            }
        }
        sb.append("] }");
        return sb.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new StackIterator<>(lastInserted);
    }

    /**
     * Exception thrown by methods in class {@link Stack} to indicate that the
     * instance is full and no more elements can be inserted.
     */
    public static class StackIsFullException extends RuntimeException {
    }
}
