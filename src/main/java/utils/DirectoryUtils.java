package utils;

import data.PublicationInfo;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

class DirectoryUtils {
    static List<Path> findFilesRecursively(Path directory, final String extension)
            throws IOException {

        final List<Path> files = new LinkedList<>();

        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {
                if (file.toString().toLowerCase().endsWith(extension)) {
                    files.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }
}
