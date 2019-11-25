import network.Config;
import network.Network;

public class GeneratorApp {

    public static void run() {
        Network nw = new Network();
        nw.generate();
        nw.printNetworkToFile(Config.OutputFile);
    }
    public static void main(String[] args) {
        System.out.println("=== Start ===");
        GeneratorApp.run();
//        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("=== End ===");
    }
}
