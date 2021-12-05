package ch.heigvd.iict.mac.labo1.queries;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.misc.HighFreqTerms;
import org.apache.lucene.misc.TermStats;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Comparator;

public class QueriesPerformer {
	
	private Analyzer		analyzer		= null;
	private IndexReader 	indexReader 	= null;
	private IndexSearcher 	indexSearcher 	= null;

	public QueriesPerformer(Analyzer analyzer, Similarity similarity) {
		this.analyzer = analyzer;
		Path path = FileSystems.getDefault().getPath("index");
		Directory dir;
		try {
			dir = FSDirectory.open(path);
			this.indexReader = DirectoryReader.open(dir);
			this.indexSearcher = new IndexSearcher(indexReader);
			if(similarity != null)
				this.indexSearcher.setSimilarity(similarity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void printTopRankingTerms(String field, int numTerms) {
		// TODO student
		// This methods print the top ranking term for a field.
		// See "Reading Index".

		// 3.3
		try {
			TermStats[] termStatsTab = HighFreqTerms.getHighFreqTerms(
					indexReader, numTerms, field, Comparator.comparing(termStats -> termStats.totalTermFreq));
			System.out.println("Top ranking terms for field ["  + field +"] are: ");
			for(TermStats ts : termStatsTab){
				System.out.println(ts.termtext.utf8ToString() + ", freq : " + ts.totalTermFreq);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void query(String q) {
		// TODO student
		// See "Searching" section
		Query query = null;

		try {
			// parse query
			query = new QueryParser("summary", analyzer).parse(q);
			System.out.println("Searching for [" + query +"]");

			// search and get docs for first top ten results
			TopDocs topDocs = indexSearcher.search(query, 10);

			// display total number of results
			System.out.println("Total number of results : " + topDocs.totalHits);

			// display top ten results
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (ScoreDoc hit : hits) {
				int docId = hit.doc;
				Document d = indexSearcher.doc(docId);
				System.out.println(
						docId + ": " +
						d.getField("title").stringValue() +
						"(" + hit.score + ")"
				);
			}

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
	}
	 
	public void close() {
		if(this.indexReader != null)
			try { this.indexReader.close(); } catch(IOException e) { /* BEST EFFORT */ }
	}
	
}
