import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class to iterate over elements of a {@link Stack}.
 *
 * @author Matteo Ferfoglia
 */
public class StackIterator<T> implements Iterator<T> {

    private StackElement<T> next;

    public StackIterator(StackElement<T> topElementOfStack) {
        this.next = topElementOfStack;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public T next() {
        if (next == null) {
            throw new NoSuchElementException();
        } else {
            var val = next.val();
            next = next.previousElementInStack();
            return val;
        }
    }
}
