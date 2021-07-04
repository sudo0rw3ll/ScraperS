module ScrapeS {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires org.jsoup;
	
	opens application to javafx.graphics, javafx.fxml;
	opens model to javafx.base;
}
