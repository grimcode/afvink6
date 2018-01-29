import java.io.IOException;

public class GraphApp {

    public static void main(String[] args) {
        try {
            Bestand bestand = new Bestand();
            bestand.kiesBestand(".txt");
            String inhoud = bestand.leesBestand();
            String[] inhoudGesplits = inhoud.split("\n");
            for(int i = 0; i < inhoudGesplits.length;i= i+2){
                Graph.addNode(inhoudGesplits[i], inhoudGesplits[i+1]);
            }
            Graph.showEdges();
            Graph.getContig();
        } catch (NotFileTypeError e){
            System.out.println("Error");
        } catch (NoFileInObject e){
            System.out.println("Error");
        } catch (IOException e){
            System.out.println("Error");
        }
    }
}
