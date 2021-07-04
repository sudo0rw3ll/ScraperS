package model;

public class Company {

	private String naziv;
	private String ulica;
	private String okrug;
	private int postanskiKod;
	private String regija;
	private String telefon;
	private String fax;
	private String sajt;
	private String deskripcija;
	private String googleCoordinates;
	
	public Company(String naziv, String ulica, String okrug, String telefon, String fax, String sajt) {
		super();
		this.naziv = naziv;
		this.ulica = ulica;
		this.okrug = okrug;
		this.telefon = telefon;
		this.fax = fax;
		this.sajt = sajt;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getUlica() {
		return ulica;
	}

	public void setUlica(String ulica) {
		this.ulica = ulica;
	}

	public String getOkrug() {
		return okrug;
	}

	public void setOkrug(String okrug) {
		this.okrug = okrug;
	}

	public int getPostanskiKod() {
		return postanskiKod;
	}

	public void setPostanskiKod(int postanskiKod) {
		this.postanskiKod = postanskiKod;
	}

	public String getRegija() {
		return regija;
	}

	public void setRegija(String regija) {
		this.regija = regija;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSajt() {
		return sajt;
	}

	public void setSajt(String sajt) {
		this.sajt = sajt;
	}

	public String getDeskripcija() {
		return deskripcija;
	}

	public void setDeskripcija(String deskripcija) {
		this.deskripcija = deskripcija;
	}

	public String getGoogleCoordinates() {
		return googleCoordinates;
	}

	public void setGoogleCoordinates(String googleCoordinates) {
		this.googleCoordinates = googleCoordinates;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof Company) {
			Company kompanija = (Company)obj;
			return this.naziv.equalsIgnoreCase(kompanija.naziv) && this.telefon.equals(telefon);
		}
		return false;
	}
	
	public String exportString() {
		StringBuilder sb = new StringBuilder();
		sb.append(naziv);
		sb.append(",");
		sb.append(ulica);
		sb.append(",");
		sb.append(okrug);
		sb.append(",");
		sb.append(postanskiKod);
		sb.append(",");
		sb.append(regija);
		sb.append(",");
		sb.append(telefon);
		sb.append(",");
		sb.append(fax);
		sb.append(",");
		sb.append(sajt);
		sb.append(",");
		sb.append(googleCoordinates);
		
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(naziv);
		sb.append("#");
		sb.append(ulica);
		sb.append("#");
		sb.append(okrug);
		sb.append("#");
		sb.append(postanskiKod);
		sb.append("#");
		sb.append(regija);
		sb.append("#");
		sb.append(telefon);
		sb.append("#");
		sb.append(fax);
		sb.append("#");
		sb.append(sajt);
		sb.append("#");
		sb.append(googleCoordinates);
		
		return sb.toString();
	}
}
