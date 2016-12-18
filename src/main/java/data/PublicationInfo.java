package data;

import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by adam1 on 17.12.2016.
 */
public class PublicationInfo {
    private Path filePath;
    private String title;
    private String abstractContent;
    private String keyWordsContent;
    private Map<String, Long> abstractTermFrequencies;
    private List<String> keyWords = new LinkedList<>();
    private List<String> titleTerms;

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }

    public String getKeyWordsContent() {
        return keyWordsContent;
    }

    public void setKeyWordsContent(String keyWordsContent) {
        this.keyWordsContent = keyWordsContent;
    }

    public Map<String, Long> getAbstractTermFrequencies() {
        return abstractTermFrequencies;
    }

    public void setAbstractTermFrequencies(Map<String, Long> abstractTermFrequencies) {
        this.abstractTermFrequencies = abstractTermFrequencies;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<String> getTitleTerms() {
        return titleTerms;
    }

    public void setTitleTerms(List<String> titleTerms) {
        this.titleTerms = titleTerms;
    }
}
