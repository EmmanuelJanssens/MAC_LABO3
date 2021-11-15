package ch.heigvd.iict.mac.labo1.parsers;

public interface ParserListener {

	void onNewDocument(Long id, String authors, String title, String summary);
	
}
