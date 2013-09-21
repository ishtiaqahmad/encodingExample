package nl.company;

import java.util.ArrayList;
import java.util.List;

public class Sequence<E> {
    private ArrayList<E> seq;

    public Sequence() {
        seq = new ArrayList<E>();
    }

    public Sequence(E pair) {
        seq.add(pair);
    }

    public List getSequence() {
        return seq;
    }

    public void append(E pair) {
        seq.add(pair);

    }

    public void prepend(E pair) {
        seq.add(0, pair);
    }

}
