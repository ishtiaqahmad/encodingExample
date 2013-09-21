package nl.company;

import nl.company.util.CliOptions;
import nl.company.util.UnicodeFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Sequence<Pair> sequence = new Sequence<Pair>();
        String seqPairStr = "";
        // Set the command line argument options
        //System.out.print("Enter the sequence Pair to decode: ");
        try {
            CliOptions.initialize(constructGnuOptions(), args);
        } catch (ParseException ex) {
            // do something
        }
        CliOptions opts = CliOptions.getopts();
        if (opts.isset("seq")) {
            seqPairStr = (String) opts.getopt("seq");
        } else {
            System.out.println("Please supply pairs/sequence as <program>.jar -seq \"(0, a), (1, 1), (0, b), (3, 2), (3, 3)\"");
            System.exit(-1);
        }

        Pattern r = Pattern.compile("\\((.*?)\\)");
        Matcher m = r.matcher(seqPairStr);
        System.out.println("Your pairs are:");
        while (m.find()) {
            String pq = m.group(0).trim();
            System.out.println(pq);
            //remove first '(' and last ')'
            pq = pq.replaceAll("\\(", "").replaceAll("\\)", "");
            String[] pqPairs = pq.split(",");
            //Pair<byte[], byte[]> pair = new Pair<byte[], byte[]>(pqPairs[0].trim().getBytes(), pqPairs[1].trim().getBytes());

            int p = Integer.parseInt(pqPairs[0].trim());
            int q;
            if (isInteger(pqPairs[1].trim(), 10)) {
                Pair<Integer, Integer> pair = new Pair<Integer, Integer>(p, Integer.parseInt(pqPairs[1].trim()));
                sequence.append(pair);
            } else {
                Pair<Integer, Character> pair = new Pair<Integer, Character>(p, pqPairs[1].trim().charAt(0));
                sequence.append(pair);
            }


        }

        SequenceConverter converter = new SequenceConverter(sequence);
        System.out.println("Raw Result:");
        System.out.println(converter.decode());

        //runTests();
    }

    public static void printBytes(byte[] array, String name) {
        for (int k = 0; k < array.length; k++) {
            System.out.println(name + "[" + k + "] = " + "0x" +
                    UnicodeFormatter.byteToHex(array[k]));
        }
    }

    public static Options constructGnuOptions() {
        final Options gnuOptions = new Options();
        gnuOptions.addOption("seq", "sequence", true, "The sequence Pair to decode: ")
                .addOption("h", false, "help");
        return gnuOptions;
    }

    public static boolean isInteger(String s, int radix) {
        Scanner sc = new Scanner(s.trim());
        if (!sc.hasNextInt(radix)) return false;
        // we know it starts with a valid int, now make sure
        // there's nothing left!
        sc.nextInt(radix);
        return !sc.hasNext();
    }

    public static void runTests() {
        // a small test case
        /*
        * @ToDo
        * need to use unit test
         */

        Pair<Integer, Character> pair1 = new Pair<Integer, Character>(0, 'a');
        Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(0, 1);
        Pair<Integer, Character> pair3 = new Pair<Integer, Character>(0, 'b');
        Pair<Integer, Integer> pair4 = new Pair<Integer, Integer>(2, 2);
        Pair<Integer, Integer> pair5 = new Pair<Integer, Integer>(3, 3);

        Sequence<Pair> sequence = new Sequence<Pair>();
        sequence.append(pair1);
        sequence.append(pair2);
        sequence.append(pair3);
        sequence.append(pair4);
        sequence.append(pair5);
        SequenceConverter converter = new SequenceConverter(sequence);
        System.out.println(converter.decode());
    }
}
