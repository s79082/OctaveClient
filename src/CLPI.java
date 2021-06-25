import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import java.io.FileReader;

public class CLPI {

    ViewFrame frame;
    boolean read;
    GraphFrame graphFrame;

    public void load() {
        String pathToOctaveCliExe = "/usr/bin/octave";
        String func = "function a = func(b)" + System.lineSeparator() + "a = b;" + System.lineSeparator()
                + "endfunction" + System.lineSeparator();

        // ProcessBuilder builder = new
        // ProcessBuilder(pathToOctaveCliExe,"--silent","--no-window-system");
        ProcessBuilder builder = new ProcessBuilder(pathToOctaveCliExe, "--silent");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            Process p = builder.start();
            String s;

            setUpProcessStreamThreads(p, System.out, System.err);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            this.frame.writer = writer;

            BufferedReader file = new BufferedReader(new FileReader("/home/moritz/Downloads/stinson.m"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            // read the file for each line
            while ((line = file.readLine()) != null) {
                // semicolon supress output
                line = line.replace(";", "");
                line = line + System.lineSeparator();
                // System.out.print(line);
                writer.write(line);
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        String pathToOctaveCliExe = "/usr/bin/octave";
        String func = "function a = func(b)" + System.lineSeparator() + "a = b;" + System.lineSeparator()
                + "endfunction" + System.lineSeparator();

        // ProcessBuilder builder = new
        // ProcessBuilder(pathToOctaveCliExe,"--silent","--no-window-system");
        ProcessBuilder builder = new ProcessBuilder(pathToOctaveCliExe, "--silent");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            Process p = builder.start();
            String s;

            setUpProcessStreamThreads(p, System.out, System.err);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            this.frame.writer = writer;

            BufferedReader file = new BufferedReader(new FileReader("/home/moritz/Downloads/stinson.m"));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            // read the file for each line
            while ((line = file.readLine()) != null) {
                // semicolon supress output
                line = line.replace(";", "");
                line = line + System.lineSeparator();
                // System.out.print(line);
                writer.write(line);
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            // writer.write("quit");

            // writer.write("d_2 = 55e-6");
            writer.newLine();
            writer.flush();

            while ((s = br.readLine()).compareToIgnoreCase("exit") != 0) {
                writer.write(s);
                writer.newLine();
                writer.flush();
            }

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
                alpha21 = new ArrayList<>();
                try {

                    while ((line = br.readLine()) != null) {
                        if (line.contains("Column"))
                            continue;
                        if (line.isBlank())
                            continue;

                        if (line.contains("alpha21")) {
                            
                            read = true;
                        }

                        if (!read)
                            continue;
                        List<String> line_list = extractNumbers(line);

                        alpha21.addAll(line_list);
                        //alpha2.addAll(line_list);
                        line_list.remove("alpha2");
                        line_list.remove("alpha21");
                        ops.println(alpha21.size());
                        graphFrame.values = alpha21;
                        graphFrame.draw();

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
        List<String> ret = new ArrayList<>();
        char c;
        boolean new_number = true;
        String number = "";
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