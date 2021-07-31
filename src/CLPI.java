import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StreamCorruptedException;
import java.nio.Buffer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JFrame;

//import jdk.internal.joptsimple.util.KeyValuePair;

import java.io.FileReader;
import java.io.FileWriter;

public class CLPI {

    String[] startValues = new String[4];

    ViewFrame frame;
    boolean read;
    GraphFrame graphFrame;

    String script_path, exe_path;

    /*
     * public void load() { String pathToOctaveCliExe = "/usr/bin/octave"; String
     * func = "function a = func(b)" + System.lineSeparator() + "a = b;" +
     * System.lineSeparator() + "endfunction" + System.lineSeparator();
     * 
     * // ProcessBuilder builder = new //
     * ProcessBuilder(pathToOctaveCliExe,"--silent","--no-window-system");
     * ProcessBuilder builder = new ProcessBuilder(pathToOctaveCliExe, "--silent");
     * 
     * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     * 
     * try { Process p = builder.start(); String s;
     * 
     * setUpProcessStreamThreads(p, System.out, System.err); BufferedWriter writer =
     * new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
     * this.frame.writer = writer;
     * 
     * BufferedReader file = new BufferedReader(new
     * FileReader("/home/moritz/Downloads/stinson.m")); StringBuffer inputBuffer =
     * new StringBuffer(); String line;
     * 
     * // read the file for each line while ((line = file.readLine()) != null) { //
     * semicolon supress output line = line.replace(";", ""); line = line +
     * System.lineSeparator(); // System.out.print(line); writer.write(line);
     * inputBuffer.append(line); inputBuffer.append('\n'); } file.close(); } catch
     * (Exception e) { e.printStackTrace(); } }
     * 
     */

    public void loadStartValues() {
        File config_file = new File("startvals");
        /*
         * if (config_file.createNewFile()) // succesfully created file if none existed
         * {
         * 
         * 
         * for (int i = 0; i < this.startValues.length; i++) {
         * 
         * } }
         */
    
        try {
            if(config_file.createNewFile())
                saveStartValues(new String[]{"15e-3", "15e-3", "0.75e-3", "0.1"});
        } catch (IOException e) {
            e.printStackTrace();
        }
        // if (config_file.exists() && !config_file.isDirectory())
        
            try {
                BufferedReader r = new BufferedReader(new FileReader(config_file));
                String ln;
                int i = 0;
                while ((ln = r.readLine()) != null) {

                    if(i >= 4)
                        break;
                    this.startValues[i] = ln;
                    System.out.println(startValues[i]);
                    i++;
                }
            } catch (FileNotFoundException fex) {
                fex.printStackTrace();
            } catch (IOException ioex) {
                ioex.printStackTrace();
            }
        
    }

    public void saveStartValues(String[] vs) {
        File file = new File("config");
        try {
            FileWriter writer = new FileWriter("startvals");
            for (int i = 0; i < vs.length; i++) {
                writer.write(vs[i]);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void init() {

        loadStartValues();
        /*
         * else { config_file.createNewFile(); }
         */

        ProcessBuilder builder;
        if (System.getProperty("os.name").contains("Windows")) {
            exe_path = "\\Program Files\\GNU Octave\\Octave-6.2.0\\mingw64\\bin\\octave-gui.exe";
            script_path = "C:\\Users\\morit\\Downloads\\stinson.m";
            builder = new ProcessBuilder('"' + exe_path + '"');
        } else {
            exe_path = "/usr/bin/octave";
            builder = new ProcessBuilder(exe_path);
            script_path = "/home/moritz/Downloads/stinson.m";

        }

        // ProcessBuilder builder = new
        // ProcessBuilder(pathToOctaveCliExe,"--silent","--no-window-system");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            Process p = builder.start();
            String s;

            setUpProcessStreamThreads(p, System.out, System.err);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            this.frame.writer = writer;

            BufferedReader file = new BufferedReader(new FileReader(script_path));
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            // load file into octave
            writer.write("run('stinson.m')" + System.lineSeparator());

            // testcall function
            // writer.write("[a1,a2]=calc(15e-3, 15e-3, 0.75e-3, 0.1)"+
            // System.lineSeparator());
            startValues = new String[]{"15e-3", "15e-3", "0.75e-3", "0.1"};
            writer.flush();
            String cmd = "[a1,a2]=calc(" + startValues[0] + ", " + startValues[1] + ", " + startValues[2] + ", "
                    + startValues[3] + ")";
            writer.write(cmd);
            // read the file for each line

            /*
             * while ((line = file.readLine()) != null) { // semicolon supress output line =
             * line.replace(";", ""); line = line + System.lineSeparator(); //
             * System.out.print(line); writer.write(line); inputBuffer.append(line);
             * inputBuffer.append('\n'); }
             */
            // while ((line = file.readLine()) != null);
            file.close();
            System.out.println("finished");
            // writer.write("quit");

            // writer.write("d_2 = 55e-6");
            writer.newLine();
            writer.flush();
            /*
             * while ((s = br.readLine()).compareToIgnoreCase("exit") != 0) {
             * writer.write(s); writer.newLine(); writer.flush(); }
             */

            // writer.write("quit");
            writer.newLine();
            writer.flush();

            // p.destroy();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Closing program!");
    }

    public static void main(String[] args) {

        new CLPI(new ViewFrame());
    }

    public CLPI(ViewFrame f) {
        this.frame = f;
        this.frame.clpi = this;
        this.graphFrame = new GraphFrame();
        init();
    }

    public void setUpProcessStreamThreads(final Process p, final PrintStream ops, PrintStream eps) {
        final InputStreamReader osr = new InputStreamReader(p.getInputStream());
        final InputStreamReader esr = new InputStreamReader(p.getErrorStream());

        Thread outputThread = new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(osr);
                String line = null;
                read = false;
                int nread = 500;
                List<String> alpha2, alpha21;
                boolean alpha2_done = false;
                alpha21 = alpha2 = new ArrayList<>();

                boolean read_a2, read_a21;
                read_a2 = read_a21 = false;
                read = false;
/*
                // top is reading
                Stack<AbstractMap.SimpleEntry<String, List<String>>> result_matrices = new Stack<>();
                result_matrices.push(new AbstractMap.SimpleEntry<>("alpha21", new ArrayList<>()));
                result_matrices.push(new AbstractMap.SimpleEntry<>("alpha2", new ArrayList<>()));
                System.out.println(result_matrices);

                List<OutputFormatElement> matrices = new ArrayList<>();
                matrices.add(new OutputFormatElement("a1"));
                matrices.add(new OutputFormatElement("a2"));

                
*/

                Timer timer = new Timer();
                List<String> result = new ArrayList<>();

                // TODO implement working extraction of values
                try {

                    while ((line = br.readLine()) != null) {

                        // ops.println(line);

                        /*
                         * for (OutputFormatElement element: matrices) { if
                         * (line.contains(element.ident)) { element.reading = true; break; } }
                         */

                        // if(line.contains(result_matrices.peek().getKey()))
                        if (line.contains("1241")) {
                            // copy the list 
                            graphFrame.draw(new ArrayList<>(result));
                            
                            result.clear();
                            read = false;
                            // result = new ArrayList<>();
                        }

                        if (line.contains("Column"))
                            continue;
                        if (line.isBlank())
                            continue;

                        if (line.contains("a2")) {
                            ops.println("now redin");
                            read = true;
                        }
                        if (!read)
                            continue;

                        /*
                         * if (line.contains("alpha21")) {
                         * 
                         * alpha21 = new ArrayList<>(); read_a21 = true; continue; } if
                         * (line.contains("alpha2")) { alpha2 = new ArrayList<>(); read_a2 = true;
                         * read_a21 = false; graphFrame.values = alpha21; graphFrame.draw(); continue; }
                         */

                        List<String> line_list = extractNumbers(line);
                        //ops.println(line_list);
                        //ops.println(result.size());

                        result.addAll(line_list);

                        /*
                         * if (read_a21) alpha21.addAll(line_list);
                         * 
                         * if (read_a2) alpha2.addAll(line_list);
                         * 
                         */
                        // alpha2.addAll(line_list);
                        // line_list.remove("alpha2");
                        // line_list.remove("alpha21");
                        // ops.println(alpha21.size());
                        // graphFrame.values = alpha21;
                        if (line.equals(System.lineSeparator())) {
                            System.out.println("end");
                        }

                        // ops.println(line);
                        /*
                         * char c; String[] values; for(int i = 0; i < line.length(); i++) { String
                         * current = ""; c = line.charAt(i); if (c == ' ') { // 3 spaces seperate values
                         * i += 3; System.out.println("space!"); } else { current += c;
                         * System.out.println("no space :("); }
                         * 
                         * }
                         */
                        // ops.println("pos: " + line);
                    }
                    System.out.println("End of OutputStream!");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread errorThread = new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(esr);
                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        eps.println("pes: " + line);
                    }
                    System.out.println("End of ErrorStream!");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        outputThread.setDaemon(true);
        errorThread.setDaemon(true);

        outputThread.start();
        errorThread.start();

        // frame.main = outputThread;
        // frame.err = errorThread;

    }

    public static List<String> extractNumbers(String s) {
        // the numbers of the line
        List<String> ret = new ArrayList<>();
        // current char
        char c;
        boolean new_number = true;
        String number = "";
        // for(char x : s.chars().forEach(action);)
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            // the regex '.' mathes all but newline
            if (c == ' ' || !String.valueOf(c).matches(".")) {
                if (!number.isBlank()) {
                    ret.add(number);
                    number = "";
                }
                continue;

            } else
                number += c;

        }

        return ret;
    }
}