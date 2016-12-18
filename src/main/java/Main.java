import data.PublicationInfo;
import utils.PublicationParser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        List<PublicationInfo> publications = PublicationParser.parse("data");

        for (PublicationInfo publication : publications) {
            System.out.println(publication.getFilePath().getFileName());
            System.out.println("Title:");
            for (String term : publication.getTitleTerms()) {
                System.out.println(term);
            }
            System.out.println("Key words:");
            for (String term : publication.getKeyWords()) {
                System.out.println(term);
            }
            System.out.println("Abstract:");
            for (Map.Entry<String, Long> term : publication.getAbstractTermFrequencies().entrySet()) {
                System.out.println(term.getKey() + " : " + term.getValue());
            }
        }
    }
}
