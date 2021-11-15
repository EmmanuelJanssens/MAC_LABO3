package ch.heigvd.iict.mac.labo1.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CACMParser {

	private String filename = null;
	private ParserListener parserListener = null;
	
	public CACMParser(String filename, ParserListener parserListener) {
		this.filename = filename;
		this.parserListener = parserListener;
	}

	private void parseLine(String l) {
		//split by tab
		String[] parts = l.split("\t");
		if(parts == null || parts.length == 0)
			return;

		//id
		Long id = null;
		if(parts.length >= 1)
			id = Long.parseLong(parts[0]);

		//authors
		String authors = null;
		if(parts.length >= 2) {
			authors = parts[1];
		}

		//title
		String title = null;
		if(parts.length >= 3)
			title = parts[2].trim();

		//summary
		String summary = null;
		if(parts.length >= 4)
			summary = parts[3].trim();

		if(this.parserListener != null)
			this.parserListener.onNewDocument(id, authors, title, summary);
	}
	
	public void startParsing() {
		File f = new File(this.filename);
		
		if(!f.isFile() || !f.canRead())
			throw new RuntimeException("File cannot be read");
		
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
			String l;
			while((l = in.readLine()) != null) {
				parseLine(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
