package utils;

import data.ContentType;
import data.PublicationInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class PublicationParser {
    public static List<PublicationInfo> parse(String directoryPath) throws IOException {
        List<Path> files = DirectoryUtils.findFilesRecursively(Paths.get(directoryPath), ".tex");
        List<PublicationInfo> publications = new LinkedList<>();

        for (Path file : files) {
            PublicationInfo publication = readPublication(file);
            final Map<String, Long> terms = new LinkedHashMap<>();
            ContentParser.generateFrequencyMap(publication.getAbstractContent()).entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEachOrdered(x -> terms.put(x.getKey(), x.getValue()));
            publication.setAbstractTermFrequencies(terms);
            publication.setTitleTerms(new LinkedList<>(ContentParser.generateFrequencyMap(publication.getTitle()).keySet()));
            if (publication.getKeyWords() != null)
                publication.setKeyWords(ContentParser.keyWordsToList(publication.getKeyWordsContent()));
            publications.add(publication);
        }

        return publications;
    }

    private static PublicationInfo readPublication(Path path) throws IOException {
        ContentType type = ContentType.None;
        StringBuilder sb = null;
        PublicationInfo publicationInfo = new PublicationInfo();
        publicationInfo.setFilePath(path);
        BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
        for (String line; (line = br.readLine()) != null; ) {
            if (line.contains("%"))
                continue;

            if (type == ContentType.None) {
                if (line.contains("\\title{") || line.contains("\\title*{"))
                    type = ContentType.Title;
                else if (line.contains("\\begin{abstract}") || line.contains("\\abstract{") || line.contains("\\abstr{"))
                    type = ContentType.Abstract;
                else if (line.contains("\\begin{keyword}") || line.contains("\\{keywords"))
                    type = ContentType.KeyWords;
            }

            if (type != ContentType.None && sb == null)
                sb = new StringBuilder();

            String filtered;

            switch (type) {
                case Title:
                    filtered = line.replace("\\title{", "");
                    filtered = filtered.replace("\\title*{", "");
                    filtered = filtered.replace("}", "");
                    sb.append(filtered);
                    sb.append("\n");
                    if (line.contains("}")) {
                        type = ContentType.None;
                        publicationInfo.setTitle(sb.toString());
                        sb = null;
                    }
                    break;
                case Abstract:
                    filtered = line.replace("\\begin{abstract}", "");
                    filtered = filtered.replace("\\end{abstract}", "");
                    filtered = filtered.replace("\\abstract{", "");
                    filtered = filtered.replace("}", "");
                    filtered = filtered.replace("\\abstr{", "");
                    sb.append(filtered);
                    sb.append("\n");
                    if (line.contains("\\end{abstract}") || line.contains("}") && !line.contains("\\begin{abstract}")) {
                        type = ContentType.None;
                        publicationInfo.setAbstractContent(sb.toString());
                        sb = null;
                    }
                    break;
                case KeyWords:
                    filtered = line.replace("\\begin{keyword}", "");
                    filtered = filtered.replace("\\end{keyword}", "");
                    filtered = filtered.replace("\\{keywords", "");
                    filtered = filtered.replace("}", "");
                    sb.append(filtered);
                    sb.append("\n");
                    if (line.contains("\\end{keyword}")|| line.contains("}") && !line.contains("\\begin{keyword}")) {
                        type = ContentType.None;
                        publicationInfo.setKeyWordsContent(sb.toString());
                        sb = null;
                    }
                    break;
            }
        }
        br.close();
        return publicationInfo;
    }
}
