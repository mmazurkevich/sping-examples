package com.spring.elastic;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneSearch {

    private static final String LUCENE_QUERY = "Information";

    public static void main(String[] args) throws IOException, ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        //Search
        IndexReader reader = DirectoryReader.open(SimpleFSDirectory.open(Paths.get("./elastic/indexes")));
        IndexSearcher searcher = new IndexSearcher(reader);

        Query query = new QueryParser("contents", analyzer).parse(LUCENE_QUERY);

        TopDocs docs = searcher.search(query, 5);
        ScoreDoc[] hits = docs.scoreDocs;

        // Print the result
        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + "  " + d.get("file_name") + "   " + d.get("file_path") + "\t");
        }

        // Reader can only be closed when there is no need to access the documents any more.
        reader.close();
    }
}
