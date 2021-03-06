package com.yaelev.sakilagui.controllers;

import com.yaelev.sakilagui.dao.FilmDAO;
import com.yaelev.sakilagui.dao.LanguageDAO;
import com.yaelev.sakilagui.entity.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FilmTabController implements Initializable {

    private FilmDAO filmDAO = new FilmDAO();
    private LanguageDAO languageDAO = new LanguageDAO();

    @FXML
    private TableView<Film> filmTableView;
    @FXML
    private TableColumn<Film, Integer> filmIdColumn;
    @FXML
    private TableColumn<Film, String> titleColumn;
    @FXML
    private TableColumn<Film, Language> languageColumn;
    @FXML
    private TableColumn<Film, Integer> releaseYearColumn;
    @FXML
    private TableColumn<Film, Integer> playTimeColumn;
    @FXML
    private TableColumn<Film, String> ratingColumn;
    @FXML
    private TableColumn<Film, Timestamp> lastUpdateColumn;
    @FXML
    private TableView<Film> rentalDetailsTableView;
    @FXML
    private TableColumn<Film, Integer> filmIdRentDetailsColumn;
    @FXML
    private TableColumn<Film, Integer> rentalPeriodColumn;
    @FXML
    private TableColumn<Film, BigDecimal> rentalCostColumn;
    @FXML
    private TableColumn<Film, BigDecimal> replacementCost;
    @FXML
    private TableColumn<Film, Timestamp> rentalLastUpdateColumn;
    @FXML
    private TableView<Film> filmDescriptionTableView;
    @FXML
    private TableColumn<Film, Integer> filmDescriptionFilmIdColumn;
    @FXML
    private TableColumn<Film, String> filmDescriptionColumn;
    @FXML
    private TableView<Actor> filmActorTableView;
    @FXML
    private TableColumn<Film, Integer> actorIdListColumn;
    @FXML
    private TableColumn<Film, String> actorFirstNameColumn;
    @FXML
    private TableColumn<Film, String> actorLastNameColumn;
    @FXML
    private ChoiceBox<String> ratingChoiceBox = new ChoiceBox<>();
    @FXML
    private ChoiceBox<Language> languageChoiceBox = new ChoiceBox<>();

    private List<String> ratingList = Arrays.asList("PG", "G", "NC-17", "PG-13", "R");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupChoiceBoxes();
        setupTableViews();
    }

    public void setupTableViews() {
        // Film TableView settings
        filmTableView.setItems(FXCollections.observableList(filmDAO.read()));
        filmIdColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        languageColumn.setCellValueFactory(new PropertyValueFactory<>("language"));
        languageColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(languageChoiceBox.getItems()));
        releaseYearColumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
        releaseYearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        playTimeColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        playTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingColumn.setCellFactory(ChoiceBoxTableCell.forTableColumn(ratingChoiceBox.getItems()));
        lastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        filmTableView.getItems().addAll();

        // Rental detail TablewView settings
        rentalDetailsTableView.setItems(FXCollections.observableList(filmDAO.read()));
        filmIdRentDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        rentalPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("rentalPeriod"));
        rentalPeriodColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        rentalCostColumn.setCellValueFactory(new PropertyValueFactory<>("rentalRate"));
        rentalCostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        replacementCost.setCellValueFactory(new PropertyValueFactory<>("replacementCost"));
        replacementCost.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        rentalLastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        rentalDetailsTableView.getItems().addAll();

        // Film description TablewView settings
        filmDescriptionTableView.setItems(FXCollections.observableList(filmDAO.read()));
        filmDescriptionFilmIdColumn.setCellValueFactory(new PropertyValueFactory<>("filmId"));
        filmDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        filmDescriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        filmDescriptionTableView.getItems().addAll();

        // Film actor TablewView settings
        actorIdListColumn.setCellValueFactory(new PropertyValueFactory<>("actorId"));
        actorFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        actorLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    }

    public void setupChoiceBoxes() {
        ratingChoiceBox.setItems(FXCollections.observableArrayList(ratingList));
        languageChoiceBox.setItems(FXCollections.observableArrayList(languageDAO.read()));
    }

    public void updateFilmTableView() {
        filmTableView.setItems(FXCollections.observableList(filmDAO.read()));
        filmTableView.getItems().addAll();
    }

    public void editTitle(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        filmTableView.getSelectionModel().getSelectedItem().setTitle(customerStringCellEditEvent.getNewValue());
        filmTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmTableView.getSelectionModel().getSelectedItem());
        updateFilmTableView();
    }

    public void editLanguage(TableColumn.CellEditEvent<Customer, Language> customerAddressCellEditEvent) {
        filmTableView.getSelectionModel().getSelectedItem().setLanguage(customerAddressCellEditEvent.getNewValue());
        filmTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmTableView.getSelectionModel().getSelectedItem());
        updateFilmTableView();
    }

    public void editReleaseYear(TableColumn.CellEditEvent<Customer, Integer> customerIntegerCellEditEvent) {
        filmTableView.getSelectionModel().getSelectedItem().setReleaseYear(customerIntegerCellEditEvent.getNewValue());
        filmTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmTableView.getSelectionModel().getSelectedItem());
        updateFilmTableView();
    }

    public void editLength(TableColumn.CellEditEvent<Customer, Integer> customerIntegerCellEditEvent) {
        filmTableView.getSelectionModel().getSelectedItem().setLength(customerIntegerCellEditEvent.getNewValue());
        filmTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmTableView.getSelectionModel().getSelectedItem());
        updateFilmTableView();
    }

    public void editRating(TableColumn.CellEditEvent<Customer, String> customerStringCellEditEvent) {
        filmTableView.getSelectionModel().getSelectedItem().setRating(customerStringCellEditEvent.getNewValue());
        filmTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmTableView.getSelectionModel().getSelectedItem());
        updateFilmTableView();
    }

    public void editRentalPeriod(TableColumn.CellEditEvent<Customer, Integer> filmIntegerCellEditEvent) {
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setRentalPeriod(filmIntegerCellEditEvent.getNewValue());
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(rentalDetailsTableView.getSelectionModel().getSelectedItem());
        updateRentalDetailsTableView();
    }

    public void editRentalRate(TableColumn.CellEditEvent<Customer, BigDecimal> filmBigDecimalCellEditEvent) {
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setRentalRate(filmBigDecimalCellEditEvent.getNewValue());
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(rentalDetailsTableView.getSelectionModel().getSelectedItem());
        updateRentalDetailsTableView();
    }

    public void editReplacementCost(TableColumn.CellEditEvent<Customer, BigDecimal> filmBigDecimalCellEditEvent) {
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setReplacementCost(filmBigDecimalCellEditEvent.getNewValue());
        rentalDetailsTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(rentalDetailsTableView.getSelectionModel().getSelectedItem());
        updateRentalDetailsTableView();
    }

    public void updateRentalDetailsTableView() {
        rentalDetailsTableView.setItems(FXCollections.observableList(filmDAO.read()));
        rentalDetailsTableView.getItems().addAll();
    }

    public void editDescription(TableColumn.CellEditEvent<Customer, String> filmStringCellEditEvent) {
        filmDescriptionTableView.getSelectionModel().getSelectedItem().setDescription(filmStringCellEditEvent.getNewValue());
        filmDescriptionTableView.getSelectionModel().getSelectedItem().setLastUpdate(Timestamp.from(Instant.now()));
        filmDAO.update(filmDescriptionTableView.getSelectionModel().getSelectedItem());
        updateFilmDescriptionTableView();
    }

    public void updateFilmDescriptionTableView() {
        filmDescriptionTableView.setItems(FXCollections.observableList(filmDAO.read()));
        filmDescriptionTableView.getItems().addAll();

    }

    public void updateFilmActorTableView() {
        if (filmTableView.getSelectionModel().getSelectedItem() != null) {
            filmActorTableView.setItems(FXCollections.observableArrayList(filmTableView.getSelectionModel()
                    .getSelectedItem()
                    .getActorSet()));
        }
    }
}
