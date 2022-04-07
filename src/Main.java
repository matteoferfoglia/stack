import java.util.stream.IntStream;

/**
 * Main class for illustrative purposes.
 * Usage of class {@link Stack} is showed.
 *
 * @author Matteo Ferfoglia.
 */
public class Main {

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        System.out.println(s);
        IntStream.range(0, 10).forEachOrdered(s::push);
        System.out.println(s);
        IntStream.range(0, s.size())
                .forEachOrdered(ignored -> System.out.println("Pop of " + s.pop()));
        System.out.println(s);


        Stack<Integer> s1 = new Stack<>(), s2 = new Stack<>();
        System.out.println(s1.equals(s2)); // true
        IntStream.range(0, 10).forEachOrdered(i -> {
            s1.push(i);
            s2.push(i);
        });
        System.out.println(s1.equals(s2)); // true
        IntStream.range(0, 10).forEachOrdered(s1::push);
        System.out.println(s1.equals(s2)); // false

        System.out.println("s1: " + s1);
        System.out.println("s2: " + s2);
    }
}
