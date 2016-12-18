package utils;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.en.EnglishMinimalStemFilter;
import org.apache.lucene.analysis.en.EnglishPossessiveFilter;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.util.Arrays;
import java.util.List;

public class CustomEnglishAnalyzer extends Analyzer {
    private final CharArraySet stopwords;

    CustomEnglishAnalyzer() {
        this(ENGLISH_STOP_WORDS_SET);
    }

    private CustomEnglishAnalyzer(CharArraySet stopwords) {
        this.stopwords = stopwords;
    }

    private static final CharArraySet ENGLISH_STOP_WORDS_SET;

    static {
        final List<String> stopWords = Arrays.asList(
                "a", "an", "and", "are", "as", "at", "be", "but", "by",
                "for", "if", "in", "into", "is", "it",
                "no", "not", "of", "on", "or", "such",
                "that", "the", "their", "then", "there", "these",
                "they", "this", "to", "was", "will", "with"
        );
        final CharArraySet stopSet = new CharArraySet(stopWords, false);
        ENGLISH_STOP_WORDS_SET = CharArraySet.unmodifiableSet(stopSet);
    }

    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        final StandardTokenizer src = new StandardTokenizer();
        TokenStream tok = new StandardFilter(src);
        tok = new LowerCaseFilter(tok);
        tok = new StopFilter(tok, stopwords);
        tok = new EnglishPossessiveFilter(tok);
        tok = new EnglishMinimalStemFilter(tok);
        return new TokenStreamComponents(src, tok);
    }
}
