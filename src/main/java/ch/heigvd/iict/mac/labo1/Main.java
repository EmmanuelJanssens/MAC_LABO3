package ch.heigvd.iict.mac.labo1;

import ch.heigvd.iict.mac.labo1.indexer.CACMIndexer;
import ch.heigvd.iict.mac.labo1.parsers.CACMParser;
import ch.heigvd.iict.mac.labo1.queries.QueriesPerformer;
import ch.heigvd.iict.mac.labo1.similarities.MySimilarity;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.analysis.Analyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	public static void main(String[] args) {

		// 1.1. create an analyzer
		Analyzer analyser = getAnalyzer();

		// TODO student "Tuning the Lucene Score"
		//Similarity similarity = null;
		Similarity similarity = new MySimilarity();
		//Similarity similarity = new ClassicSimilarity();

		CACMIndexer indexer = new CACMIndexer(analyser, similarity);

		long start = System.currentTimeMillis(); // to determine indexing time execution, part 3.2
		indexer.openIndex();
		CACMParser parser = new CACMParser("documents/cacm.txt", indexer);
		parser.startParsing();
		indexer.finalizeIndex();
		long end = System.currentTimeMillis(); // to determine indexing time execution, part 3.2
		System.out.println("Elapsed time indexing with this analyzer : "  + (end - start));

		QueriesPerformer queriesPerformer = new QueriesPerformer(analyser, similarity);

		// Section "Reading Index"
		readingIndex(queriesPerformer);

		// Section "Searching"
		searching(queriesPerformer);

		queriesPerformer.close();
		
	}

	private static void readingIndex(QueriesPerformer queriesPerformer) {
		queriesPerformer.printTopRankingTerms("author", 10);
		queriesPerformer.printTopRankingTerms("title", 10);
	}

	private static void searching(QueriesPerformer queriesPerformer) {
		// Example
		queriesPerformer.query("compiler program");

		// TODO student
        // queriesPerformer.query(<containing the term Information Retrieval>);
		// queriesPerformer.query(<containing both Information and Retrieval>);
        // and so on for all the queries asked on the instructions...
        //
		// Reminder: it must print the total number of results and
		// the top 10 results.
	}

	private static Analyzer getAnalyzer() {
	    // TODO student... For the part "Indexing and Searching CACM collection
		// - Indexing" use, as indicated in the instructions,
		// the StandardAnalyzer class.
		//
		// For the next part "Using different Analyzers" modify this method
		// and return the appropriate Analyzers asked.

		// Part 3.1 Indexing
		//return new StandardAnalyzer();

		// Part 3.2 Using different Analyzer
		//return new WhitespaceAnalyzer();
		return new EnglishAnalyzer();
		//Analyzer standardAnalyzer = new StandardAnalyzer();
		//return new ShingleAnalyzerWrapper(standardAnalyzer); // shingle 1 and 2
		//return new ShingleAnalyzerWrapper(standardAnalyzer, 3, 3); // shingle 1 and 3
		/*
		try {
			String file ="common_words.txt";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			return new StopAnalyzer(reader);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		*/


		//return new StandardAnalyzer();
	}

}
