import jdk.jshell.spi.ExecutionControl.ExecutionControlException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.IOException;

public class Controler {
    Model model;
    View view;

    private String exe_path;

    public Controler() throws Exception
    {
        ProcessBuilder builder;
        if (System.getProperty("os.name").contains("Windows"))
        {
            exe_path = "\\Program Files\\GNU Octave\\Octave-6.2.0\\mingw64\\bin\\octave-gui.exe";
            //script_path = "C:\\Users\\morit\\Downloads\\stinson.m";
            builder = new ProcessBuilder('"' + exe_path + '"', "--silent");
        }
        else{
            exe_path = "/usr/bin/octave";
            builder = new ProcessBuilder(exe_path, "--silent");
            //script_path = "/home/moritz/Downloads/stinson.m";

        }

        Process p = builder.start();
        setUpProcessStreamThreads(p, System.out, System.err);
        model = new Model(p);

        model.loadFile();

        model.calcFunction("15e-3", "55e-3", "0.75e-3", "0.1");

        //while(true);
    }

    public void setUpProcessStreamThreads(final Process p, final PrintStream ops, PrintStream eps) {
        final InputStreamReader osr = new InputStreamReader(p.getInputStream());
        final InputStreamReader esr = new InputStreamReader(p.getErrorStream());

        Thread outputThread = new Thread(new Runnable() {
            public void run() {
                BufferedReader br = new BufferedReader(osr);
                String line;
                ops.println("test1");
                //TODO implement working extraction of values
                try {

                    
                    while ((line = br.readLine()) != null) {

                        ops.println(line);
                        
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

}
