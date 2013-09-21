package nl.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SequenceConverter {
    private Sequence sequence;

    public SequenceConverter(Sequence sequence) {
        this.sequence = sequence;
    }

    public List encode() {
        /*
        \x20\x2A\x20  -> \x00\x20\x00\x2A\x02\x01
                " * " - > "NULL NULL*STXSOH"
        */
        throw new UnsupportedOperationException("Encoder is not implemented yet");
    }

    public List decode() {
        List<Character> result = new ArrayList<Character>();
        for (Iterator<Pair> i = sequence.getSequence().iterator(); i.hasNext(); ) {
            Pair pair = i.next();

            if (isFirstCase(pair)) {
                if (pair.second instanceof Character)
                    result.add((Character) pair.getSecond());
                else {
                    /*@TODO
                    * Log4j should be used
                    */
                    System.err.println("Error: invalid Pair(0,int) found, Assuming in case of p=0 q will always be Character");
                    System.err.println("Going to ignore this Pair");
                    result.add('?');
                }
            } else {
                int p = ((Integer) pair.getFirst()).intValue();
                int q = ((Integer) pair.getSecond()).intValue();
                if (result.size() >= p) {
                    List<Character> subSeq = result.subList(result.size() - p, result.size());
                    if (subSeq.size() >= q) {
                        result.addAll(subSeq.subList(0, q));
                    } else
                        System.err.println("Size Error: not enough char in result; q is larger then the size of result array");
                } else
                    System.err.println("Size Error: not enough char in result; p is larger then the size of result array");
            }
        }
        return result;
    }

    private boolean isFirstCase(Pair pair) {
        boolean checked = false;
        if (pair.first instanceof Integer) {
            if (((Integer) pair.getFirst()).intValue() == 0)
                checked = true;
        }
        return checked;
    }
}
