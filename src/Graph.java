import java.util.ArrayList;
import java.util.Collections;

public class Graph{

    private static ArrayList<String> headers = new ArrayList<>(), sequences = new ArrayList<>(),
                                    contigSeq = new ArrayList<>(), oldSeq = new ArrayList<>();
    private static ArrayList<ArrayList<Integer>> weightEdges = new ArrayList<>();

    public static void addNode(String header, String seq){
        headers.add(header);
        sequences.add(seq);
    }

    public static void showEdges(){
        weightEdges = createEdges(sequences,weightEdges);
        int i = 0, j = 0;
        for(ArrayList<Integer> list : weightEdges){
            i = weightEdges.indexOf(list);
            j=0;
            for(Integer waarde : list) {
                if(waarde>0) {
                    String tail = headers.get(j);
                    String head = headers.get(i);
                    System.out.println(head + "-->" + tail + " " + waarde);
                }
                j++;
            }
        }
    }

    public static void getContig(){
        ArrayList<ArrayList<Integer>> scores = createEdges(sequences,weightEdges);
        oldSeq = copySeqList(sequences);
        Boolean doorgaan = true;

        while (doorgaan) {

            Integer maximumscore = Collections.max(scores.get(0));
            int counter = 0;
            while (counter != scores.size()) {
                if (maximumscore != 0) {
                    int tailIndex = scores.get(counter).indexOf(maximumscore);
                    String tail = oldSeq.get(tailIndex);
                    String head = oldSeq.get(counter);
                    oldSeq.remove(scores.get(counter).indexOf(maximumscore));
                    oldSeq.remove(counter);

                    contigSeq.add(head + tail.substring(maximumscore, tail.length()));
                    counter = scores.size();
                } else {
                    counter++;
                }
            }
            contigSeq.addAll(oldSeq);
            oldSeq = copySeqList(contigSeq);
            scores = createEdges(contigSeq, new ArrayList<ArrayList<Integer>>());
            contigSeq = new ArrayList<>();
            if(scores.size() <= 1){
                doorgaan = false;
            }
        }
        System.out.println(oldSeq.get(0));

    }

    private static ArrayList<String> copySeqList(ArrayList<String> org){
        ArrayList<String> copy = new ArrayList<>();
        for(String s : org){
            copy.add(s);
        }
        return copy;
    }

    private static ArrayList<ArrayList<Integer>> createEdges(ArrayList<String> vergelijkSeq, ArrayList<ArrayList<Integer>> scoreList){
        for(String seq1 : vergelijkSeq){
            for(String seq2 : vergelijkSeq){
                char[] letters = new StringBuilder(seq1).reverse().toString().toCharArray();
                String subSeq = "";
                Integer score = 0;
                for(char letter : letters){
                    subSeq = letter+subSeq;
                    if(seq2.indexOf(subSeq) == 0){
                        score = subSeq.length();

                    }
                }
                if(score == seq1.length()) {
                    score = 0;
                }
                try {
                    scoreList.get(vergelijkSeq.indexOf(seq1)).add(score);
                } catch (IndexOutOfBoundsException e){
                    scoreList.add(new ArrayList<Integer>());
                    scoreList.get(vergelijkSeq.indexOf(seq1)).add(score);
                }

            }
        }
        return scoreList;
    }




}


