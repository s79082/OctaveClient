import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.BufferedReader;



public class Model {
    
    BufferedWriter writer;
    public Model(Process p)
    {   this.writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        //setUpProcessStreamThreads(p, System.out, System.err);
    }

    // load m-file into octave
    public void loadFile() throws IOException
    {
        writer.write("run('stinson.m')" +System.lineSeparator());
        writer.flush();
        
    }

    // calls the function in m-file
    public void calcFunction(String d_2, String d_1, String R_pore, String phi) throws IOException
    {
        String param_list = d_2 + ", " + d_1 + ", " + R_pore + ", " + phi;
        //writer.write("run('stinson.m')"+ System.lineSeparator());
            writer.write("[a1,a2]=calc(15e-3, 15e-3, 0.75e-3, 0.1)"+ System.lineSeparator());
            writer.flush();
        //writer.write("[a1, a2] = calc(" + param_list + ")");
        writer.flush();
        
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
