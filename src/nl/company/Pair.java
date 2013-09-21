package nl.company;

public class Pair<P,Q> {
    public final P first;
    public final Q second;
    public static <P,Q> Pair<P,Q> of(P first, Q second) {
        return new Pair<P,Q>(first, second);
    }

    public Pair(P first, Q second) {
        this.first = first;
        this.second = second;
    }

    public P getFirst() {
        return first;
    }

    public Q getSecond() {
        return second;
    }

    @Override public String toString() {
        return String.format("(%s, %s)", first, second);
    }

}
