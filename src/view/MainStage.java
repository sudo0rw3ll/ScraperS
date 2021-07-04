package view;

import controller.ExportController;
import controller.ScraperController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Base;
import model.Company;

public class MainStage extends Stage{
	
	private static MainStage instance = null;
	
	private Scene scene;
	private VBox root = new VBox(10);
	
	//Components
	private HBox hbTop = new HBox(10);
	private Button btnScrape = new Button("Scrape");
	private Button btnExport = new Button("Export");
	
	private TableView<Company> tblCompanies = new TableView<Company>();
	
	private HBox hbBot = new HBox(10);
	
	private MainStage() {
		init();
		build();
		buildTable();
		addActions();
		scene = new Scene(root);
		this.setWidth(1000);
		this.setHeight(600);
		this.setScene(scene);
		this.setTitle("ScraperS");
		this.show();
	}
	
	private void init() {
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		hbTop.setAlignment(Pos.TOP_RIGHT);
		hbBot.setAlignment(Pos.BOTTOM_LEFT);
	}
	
	private void build() {
		hbTop.getChildren().add(btnScrape);
		hbTop.getChildren().add(btnExport);
	
		root.getChildren().add(hbTop);
		root.getChildren().add(tblCompanies);
		
		root.getChildren().add(hbBot);
	}
	
	private void addActions() {
		btnScrape.setOnAction(new ScraperController());
		btnExport.setOnAction(new ExportController());
	}
	
	@SuppressWarnings("unchecked")
	private void buildTable() {
		TableColumn<Company, String> tcNaziv = new TableColumn<Company, String>("Naziv");
		TableColumn<Company, String> tcUlica = new TableColumn<Company, String>("Ulica");
		TableColumn<Company, String> tcOkrug = new TableColumn<Company, String>("Okrug");
		TableColumn<Company, Integer> tcPostanski = new TableColumn<Company, Integer>("Postanski kod");
		TableColumn<Company, String> tcRegija = new TableColumn<Company, String>("Regija");
		TableColumn<Company, String> tcTelefon = new TableColumn<Company, String>("Telefon");
		TableColumn<Company, String> tcFax = new TableColumn<Company, String>("Fax");
		TableColumn<Company, String> tcSajt = new TableColumn<Company, String>("Sajt");
		TableColumn<Company, String> tcCoordinates = new TableColumn<Company, String>("Koordinate");
		
		tcNaziv.setCellValueFactory(new PropertyValueFactory<Company,String>("naziv"));
		tcUlica.setCellValueFactory(new PropertyValueFactory<Company,String>("ulica"));
		tcOkrug.setCellValueFactory(new PropertyValueFactory<Company,String>("okrug"));
		tcPostanski.setCellValueFactory(new PropertyValueFactory<Company,Integer>("postanskiKod"));
		tcRegija.setCellValueFactory(new PropertyValueFactory<Company,String>("regija"));
		tcTelefon.setCellValueFactory(new PropertyValueFactory<Company,String>("telefon"));
		tcFax.setCellValueFactory(new PropertyValueFactory<Company,String>("fax"));
		tcSajt.setCellValueFactory(new PropertyValueFactory<Company,String>("sajt"));
		tcCoordinates.setCellValueFactory(new PropertyValueFactory<Company,String>("googleCoordinates"));
	
		tblCompanies.getColumns().addAll(tcNaziv,tcUlica,tcOkrug,tcPostanski,tcRegija,tcTelefon,tcFax,tcSajt,tcCoordinates);
		tblCompanies.setItems(FXCollections.observableArrayList(Base.getInstance().getKompanije()));
	}
	
	public static MainStage getInstance() {
		if(instance == null) {
			instance = new MainStage();
		}
		return instance;
	}

	public TableView<Company> getTblCompanies() {
		return tblCompanies;
	}

	public void setTblCompanies(TableView<Company> tblCompanies) {
		this.tblCompanies = tblCompanies;
	}
}
