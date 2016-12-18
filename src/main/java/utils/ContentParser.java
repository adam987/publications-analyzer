package utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ContentParser {
    static Map<String, Long> generateFrequencyMap(String text) throws IOException {
        Directory directory = new RAMDirectory();
        Analyzer analyzer = new ShingleAnalyzerWrapper(new CustomEnglishAnalyzer());
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(directory, config);
        FieldType type = new FieldType();
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setTokenized(true);
        type.setStored(true);
        type.setStoreTermVectors(true);
        Document doc = new Document();
        doc.add(new Field("tags", text, type));
        writer.addDocument(doc);
        writer.close();
        DirectoryReader reader = DirectoryReader.open(directory);
        Map<String, Long> frequencyMap = new HashMap<>();

        for (int i = 0; i < reader.numDocs(); i++) {
            Terms terms = reader.getTermVector(i, "tags");
            for (TermsEnum termsEnum = terms.iterator(); (termsEnum.next()) != null; ) {
                frequencyMap.put(termsEnum.term().utf8ToString(), termsEnum.totalTermFreq());
            }
        }
        reader.close();
        analyzer.close();
        directory.close();
        return frequencyMap;
    }

    static List<String> keyWordsToList(String keyWords) {
        List<String> list = new LinkedList<>();
        if (keyWords != null)
            for (String keyWord : keyWords.split(",")) {
                list.add(keyWord.trim());
            }

        return list;
    }
}
