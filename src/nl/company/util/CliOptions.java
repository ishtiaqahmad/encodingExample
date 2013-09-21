package nl.company.util;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CliOptions {
    private static CliOptions singletonObj = null;
    private HashMap<String, Object> options = new HashMap<String, Object>();
    private ArrayList<String> arguments = new ArrayList<String>();

    public static CliOptions getopts() {
        if (singletonObj == null) {
            throw new IllegalStateException("[CliOptions] Command line not yet initialized.");
        }

        return singletonObj;
    }

    public static synchronized void initialize(Options optsdef, String[] args)
            throws ParseException {
        if (singletonObj == null) {
            singletonObj = new CliOptions(optsdef, args);
        } else {
            throw new IllegalStateException("[CliOptions] Command line already initialized.");
        }
    }

    //----- prevent cloning -----
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public boolean isset(String opt) {
        return options.containsKey(opt);
    }

    public Object getopt(String opt) {
        Object rc = null;

        if (options.containsKey(opt)) {
            rc = options.get(opt);
        }

        return rc;
    }

    private CliOptions(Options optsdef, String[] args)
            throws ParseException {
        //***** (blindly) parse the command line *****
        CommandLineParser parser = new GnuParser();
        CommandLine cmdline = parser.parse(optsdef, args);
        //----- options -----
        for (Option opt : cmdline.getOptions()) {
            String key = opt.getOpt();
            if (opt.hasArgs()) {
                options.put(key, opt.getValuesList());
            } else {
                options.put(key, opt.getValue());
            }
        }

        for (String str : cmdline.getArgs()) {
            if (str.length() > 0) {
                arguments.add(str);
            }
        }
    }

}
