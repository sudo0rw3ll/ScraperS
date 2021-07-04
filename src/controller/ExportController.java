package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Base;
import model.Company;

public class ExportController implements EventHandler<ActionEvent>{

	private static final String csvOut = "ScraperS_csv.csv";
	
	@Override
	public void handle(ActionEvent arg0) {
		exportCsv();
	}

	private void exportCsv() {
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new File(csvOut));
			
			StringBuilder sb = new StringBuilder();
			sb.append("Naziv");
			sb.append(",");
			sb.append("Ulica");
			sb.append(",");
			sb.append("Okrug");
			sb.append(",");
			sb.append("Postanski Kod");
			sb.append(",");
			sb.append("Regija");
			sb.append(",");
			sb.append("Telefon");
			sb.append(",");
			sb.append("Fax");
			sb.append(",");
			sb.append("Sajt");
			sb.append(",");
			sb.append("Koordinate");
			
			pw.println(sb.toString());
			
			for(Company cmp : Base.getInstance().getKompanije()) {
				pw.println(cmp.exportString());
			}
			
			new Alert(AlertType.INFORMATION,"Export zavrsen").show();
			
		}catch(IOException e) {
			System.out.println("Greska prilikom upisa u fajl");
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
		
	}
}
