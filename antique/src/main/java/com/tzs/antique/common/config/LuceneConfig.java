package com.tzs.antique.common.config;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class LuceneConfig {

    @Value("${lucene.path}")
    private String lucenePath;

    @Bean
    public Directory directory() throws IOException {
        Path path = Paths.get(lucenePath);
        return FSDirectory.open(path);
    }

    @Bean
    public Analyzer analyzer() {
        return new IKAnalyzer(false); // 启用中文分词
    }

    @Bean
    public IndexWriterConfig indexWriterConfig(Analyzer analyzer) {
        return new IndexWriterConfig(analyzer);
    }

    @Bean
    public IndexWriter indexWriter(Directory directory, IndexWriterConfig config) throws IOException {
        return new IndexWriter(directory, config);
    }
}
