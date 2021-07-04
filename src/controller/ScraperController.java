package controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Company;
import model.CompanyComparator;
import view.MainStage;

public class ScraperController implements EventHandler<ActionEvent>{

	private static final String file_links = "linkovi.txt";
	private static final String output_file = "companies.txt";
	private static final String agents_file = "user_agents.txt";
	
	private List<String> agents;
	
	@Override
	public void handle(ActionEvent arg0) {
		agents = new ArrayList<String>();
		loadAgents();
		MainStage ms = MainStage.getInstance();
		ms.getTblCompanies().getItems().clear();
		Task<ObservableList<Company>> scrapeTask = scrapeData();
		
		scrapeTask.setOnSucceeded((WorkerStateEvent t1)->{
			ObservableList<Company> companies = scrapeTask.getValue();
			new Alert(AlertType.INFORMATION,"Scrape gotov!").show();
			companies.sort(new CompanyComparator());
			ms.getTblCompanies().setItems(companies);
		});
				
		Thread thread = new Thread(scrapeTask);
		thread.setDaemon(true);
		thread.start();
		
	}
	
	private void loadAgents() {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(new File(agents_file)));
			
			String line = "";
			
			while((line = br.readLine()) != null) {
				agents.add(line);
			}
			
		}catch(IOException e) {
			System.out.println("Greska prilikom citanja fajla");
		}finally {
			if(br != null) {
				try {
					br.close();
				}catch(IOException e) {
					System.out.println("Greska prilikom zatvaranja resursa");
				}
			}
		}
	}
	
	/*private void scrapeLinks() {
		PrintWriter pw = null;
		
		try {
			
			int agentIndex = new Random().nextInt(agents.size());
			
			String userAgent = agents.get(agentIndex);
			
			pw = new PrintWriter(new File(file_links));
			
			System.out.println("Started scraping links");
			Thread.sleep(3000);
			
			String firstPartUrl = "https://web2.cylex.de/s?q=&c=wuppertal&z=&p=";
			String secondPartUrl = "&dst=&sUrl=&cUrl=Wuppertal";
			
			for(int i=1;i<=11;i++){
				Document doc = Jsoup.connect(firstPartUrl + i + secondPartUrl).userAgent(userAgent).timeout(30 * 1000).get();
				
				Elements premiumAds = doc.getElementsByClass("lm-item premium");
				
				for(Element premiumAd : premiumAds) {
					String link = premiumAd.select(".block.bold.h4 a").attr("href");
					pw.println(link);
				}
				
				System.out.println("Scrape pause");
				Thread.sleep(4000);
				System.out.println("Scrape continue");
			}
			
			for(int i=11;i<=25;i++) {
				Document doc = Jsoup.connect(firstPartUrl + i + secondPartUrl).userAgent(userAgent).timeout(30 * 1000).get();
				
				Elements basicAds = doc.getElementsByClass("lm-item basic");
				
				for(Element basicAd : basicAds) {
					String link = basicAd.select(".block.bold.h4 a").attr("href");
					pw.println(link);
				}
				
				System.out.println("Scrape pause");
				Thread.sleep(4000);
				System.out.println("Scrape continue");
			}
		}catch(IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			if(pw != null) {
				pw.close();
			}
		}
	}*/
	
	private Task<ObservableList<Company>> scrapeData(){
		
		Task<ObservableList<Company>> scrapeTask = new Task<ObservableList<Company>>() {

			@Override
			protected ObservableList<Company> call() throws Exception {
				ObservableList<Company> rezultati = FXCollections.observableArrayList(new ArrayList<Company>());
				
				BufferedReader br = null;
				PrintWriter pw = null;
				
				try {
					System.out.println("Startovan proces");
					br = new BufferedReader(new FileReader(new File(file_links)));
					pw = new PrintWriter(new File(output_file));
					
					String url = "";
					
					while((url = br.readLine()) != null) {
						int timeOut = new Random().nextInt(20-10+1)+10;
						
						int randomAgent = new Random().nextInt(agents.size());
						
						String userAgent = agents.get(randomAgent);
						
						System.out.println("url -> " + url);
						System.out.println(userAgent);
						System.out.println(timeOut);
						
						Document doc = Jsoup.connect(url).userAgent("Mozilla").timeout(30*1000).ignoreHttpErrors(false).get();
						Thread.sleep(timeOut/2);
						Elements primaryData = doc.getElementsByClass("prmry-cntct-dtls align-with-logo");
						Elements secondaryData = doc.getElementsByClass("c-contact cp-contact");
						
						String naziv = "";
						String adresa = "";
						String postanski = "";
						String okrug = "";
						String regija = "";
						String telefon = "";
						String fax = "";
						String sajt = "";
						String koordinate = "";
						
						Element coordinates = doc.getElementById("map-wrapper");
						String koordinateLink = coordinates.select("img[class=img-responsive]").attr("data-src");
						
						if(!koordinateLink.isEmpty()){
							String parts[] = koordinateLink.split("=");
							String parts1[] = parts[1].split(",");
							int len = parts1[1].length();
							koordinate = parts1[0] + ":" + parts1[1].substring(0,len-5); 
						}else {
							koordinate = "/";
						}
						
						Thread.sleep(timeOut * 1000);
						System.out.println("timeout");
						
						for(Element element : primaryData) {
							naziv = element.getElementById("cntct-name").text();
							adresa = element.select("span[itemprop=streetAddress]").text();
							postanski = element.select("span[itemprop=postalCode]").text();
							okrug = element.select("span[itemprop=addressLocality]").text();
							regija = element.select("span[itemprop=addressRegion]").text();
						
							
							if(naziv.isEmpty()) {
								naziv = "/";
							}else if(adresa.isEmpty()) {
								adresa = "/";
							}else if(postanski.isEmpty()) {
								postanski = "/";
							}else if(okrug.isEmpty()) {
								okrug = "/";
							}else if(regija.isEmpty()) {
								regija = "/";
							}
							
						}
						
						Thread.sleep(timeOut * 1000);
						
						for(Element element : secondaryData) {
							telefon = element.select("meta[itemprop=telephone]").attr("content");
							sajt = element.select("span[class=contact-data-url]").attr("content");
							fax = element.select("meta[itemprop=faxNumber]").attr("content");
							String faxHidden = element.select("small[class=text-muted hidden-xs]").text();
							fax = fax + faxHidden;
							
							if(telefon.isEmpty()) {
								telefon = "/";
							}else if(sajt.isEmpty()) {
								sajt = "/";
							}else if(fax.isEmpty()) {
								fax = "/";
							}
						}
						
						int postanskiKod = 0;
						
						try {
							postanskiKod = Integer.parseInt(postanski);
						}catch(NumberFormatException e) {
							System.out.println("Pogresan format broja [postanski]");
						}
						
						Company cmp = new Company(naziv,adresa,okrug,telefon,fax,sajt);
						cmp.setRegija(regija);
						cmp.setGoogleCoordinates(koordinate);
						cmp.setPostanskiKod(postanskiKod);
						
						rezultati.add(cmp);
						pw.println(cmp);
					}
				}catch(HttpStatusException e) {
					System.out.println("HTTP Status: " + e.getStatusCode());
				}catch(IOException e) {
					System.out.println("IO Greska");
				}finally {
					if(br != null) {
						try {
							br.close();
						}catch(IOException e) {
							System.out.println("Greska prilikom zatvaranja resursa");
						}
					}
					
					if(pw != null) {
						pw.close();
					}
				}
				return rezultati;
			}
		};
		return scrapeTask;
	}

}
