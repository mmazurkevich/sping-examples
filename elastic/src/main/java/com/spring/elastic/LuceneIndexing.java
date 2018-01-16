package com.spring.elastic;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneIndexing {

    public static void main(String[] args) throws IOException {
        //Directory for storing indexes
        Directory directory = new SimpleFSDirectory(Paths.get("./elastic/indexes"));

        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        indexDoc(indexWriter,  Paths.get("./elastic/docs/one.txt"));
        indexDoc(indexWriter,  Paths.get("./elastic/docs/two.txt"));
        indexDoc(indexWriter,  Paths.get("./elastic/docs/three.txt"));
        indexWriter.close();
    }

    /**
     * Indexes a single document
     */
    private static void indexDoc(IndexWriter writer, Path file) throws IOException {
        long startTime = System.currentTimeMillis();
        try (InputStream stream = Files.newInputStream(file)) {
            Document doc = new Document();

            Field contentField = new TextField("contents", new BufferedReader(new InputStreamReader(stream)));

            //index file name
            Field fileNameField = new StringField("file_name", file.getFileName().toString(), Field.Store.YES);

            //index file path
            Field filePathField = new StringField("file_path", file.normalize().toAbsolutePath().toString(), Field.Store.YES);

            doc.add(fileNameField);
            doc.add(filePathField);
            doc.add(contentField);

            writer.addDocument(doc);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(file.getFileName() + " file indexed, time taken: " +(endTime-startTime)+" ms");
    }
}
