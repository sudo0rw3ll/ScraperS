package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Base {
	
	private static Base instance = null;
	
	private static final String companyFile = "companies.txt";
	
	private List<Company> kompanije;
	
	private Base() {
		kompanije = new ArrayList<Company>();
		readFile();
	}
	
	private void readFile() {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(new File(companyFile)));
			
			String line = "";
			
			while((line = br.readLine()) != null){
				String parts[] = line.split("#");
				int lene = parts.length;
				
				String naziv = parts[0];
				String ulica = parts[1];
				String okrug = parts[2];
				
				if(naziv.isEmpty()) {
					naziv = "/";
				}
				
				if(ulica.isEmpty()) {
					ulica = "/";
				}
				
				if(okrug.isEmpty()) {
					okrug = "/";
				}
				
				int postanskiKod = 0;
				
				try {
					postanskiKod = Integer.parseInt(parts[3]);
				}catch(NumberFormatException e) {
					e.printStackTrace();
				}
				
				String regija = parts[4];
				String telefon = parts[5];
				String fax = parts[6];
				String sajt = parts[7];
				String koordinate = parts[lene-1];
				
				if(regija.isEmpty()) {
					regija = "/";
				}
				
				if(telefon.isEmpty()) {
					telefon = "/";
				}
				
				if(fax.isEmpty()) {
					fax = "/";
				}
				
				if(sajt.isEmpty()) {
					sajt = "/";
				}
				
				if(koordinate.isEmpty()) {
					koordinate = "/";
				}
				
				Company cmp = new Company(naziv,ulica,okrug,telefon,fax,sajt);
				cmp.setPostanskiKod(postanskiKod);
				cmp.setGoogleCoordinates(koordinate);
				cmp.setRegija(regija);
				
				kompanije.add(cmp);
			}
			kompanije.sort(new CompanyComparator());
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Base getInstance() {
		if(instance == null) {
			instance = new Base();
		}
		return instance;
	}

	public List<Company> getKompanije() {
		return kompanije;
	}

	public void setKompanije(List<Company> kompanije) {
		this.kompanije = kompanije;
	}
}
