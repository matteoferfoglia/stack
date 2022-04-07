import java.util.function.BiPredicate;
import java.util.stream.IntStream;

/**
 * Test class for {@link Stack}.
 * <strong>Use the VM option flag "-ea" to enable assertions.</strong>
 *
 * @author Matteo Ferfoglia
 */
class StackTest {
    private static final Integer testValue = 10;

    public static void main(String[] args) {

        // assert false; // use this line to check if assertions are enabled: if this line is uncommented, an AssertionError should be thrown

        var test = new StackTest();
        test.size();
        test.isEmpty();
        test.clear();
        test.pushAndPop();
        test.equals();

        // test thread safe for push
        final int NThreads = 1000;
        final int NumOfElementsInsertedByEachThread = 10000;
        final int ExpectedStackSize = NThreads * NumOfElementsInsertedByEachThread;
        var s = new Stack<Integer>();
        assert s.isEmpty();
        IntStream.range(0, NThreads)
                .mapToObj(ignored ->
                        new Thread(() -> IntStream.range(0, NumOfElementsInsertedByEachThread).forEach(s::push)))
                .peek(Thread::start)
                .forEach(thread -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        assert false;
                    }
                });
        assert s.size() == ExpectedStackSize;

        // test thread safe for pop
        IntStream.range(0, NThreads)
                .mapToObj(ignored ->
                        new Thread(() -> IntStream.range(0, NumOfElementsInsertedByEachThread).forEach(i -> s.pop())))
                .peek(Thread::start)
                .forEach(thread -> {
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        assert false;
                    }
                });
        assert s.isEmpty();

    }

    private static Stack<Integer> createNewEmptyStack() {
        var s = new Stack<Integer>();
        assert s.isEmpty() && s.size() == 0;
        return s;
    }

    private static void insertNIntegersInStack(Stack<Integer> s, int N) {
        IntStream.range(0, N).forEachOrdered(s::push);
    }

    void size() {
        var s = createNewEmptyStack();
        assert s.size() == 0;
        s.push(1);
        assert s.size() == 1;
    }

    void isEmpty() {
        var s = createNewEmptyStack();
        assert s.isEmpty();
        s.push(1);
        assert !s.isEmpty();
    }

    void clear() {
        var s = createNewEmptyStack();
        final int NElementsToAddForTest = 10;
        insertNIntegersInStack(s, NElementsToAddForTest);
        assert !s.isEmpty();
        s.clear();
        assert s.isEmpty();
    }

    void pushAndPop() {
        var s = createNewEmptyStack();
        assert s.isEmpty();
        s.push(testValue);
        assert !s.isEmpty();
        assert s.size() == 1;
        assert s.pop().equals(testValue);
        assert s.isEmpty();
    }

    void equals() {
        BiPredicate<Stack<?>, Stack<?>> testEquality = (s1, s2) -> s1.equals(s2) && s2.equals(s1) && s1.hashCode() == s2.hashCode();
        BiPredicate<Stack<?>, Stack<?>> testInEquality = (s1, s2) -> !(s1.equals(s2) || s2.equals(s1));

        var s1 = createNewEmptyStack();
        var s2 = createNewEmptyStack();
        assert testEquality.test(s1, s2);

        final int NElementsToAddForTest = 10;
        insertNIntegersInStack(s1, NElementsToAddForTest);
        insertNIntegersInStack(s2, NElementsToAddForTest);
        assert testEquality.test(s1, s2);

        insertNIntegersInStack(s2, NElementsToAddForTest);
        assert testInEquality.test(s1, s2);
    }

}