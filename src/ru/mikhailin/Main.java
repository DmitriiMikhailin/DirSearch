package ru.mikhailin;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {
    Date fromDate;
    Date toDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        // write your code here
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setPadding(new Insets(10));
//        pane.setHgap(10);
//        pane.setVgap(10);

//        pane.add(new Label("Search directory"), 0, 0);
//        pane.add(new Label("Temp directory"), 0, 1);
//        pane.add(new Label("Date from"), 0, 2);
//        pane.add(new Label("Date to"), 0, 3);
//        pane.add(new Label("Client"), 0, 4);
        Label searchDirectoryLabel = new Label("Search directory");
        Label tempDirectoryLabel = new Label("Temp directory");
        Label dateFromLabel = new Label("Date from");
        Label dateToLabel = new Label("Date to");
        Label clientLabel = new Label("Client");

        pane.getChildren().add(searchDirectoryLabel);
        AnchorPane.setTopAnchor(searchDirectoryLabel, 5d);
        AnchorPane.setLeftAnchor(searchDirectoryLabel, 5d);

        pane.getChildren().add(tempDirectoryLabel);
        AnchorPane.setTopAnchor(tempDirectoryLabel, 35d);
        AnchorPane.setLeftAnchor(tempDirectoryLabel, 5d);

        pane.getChildren().add(dateFromLabel);
        AnchorPane.setTopAnchor(dateFromLabel, 65d);
        AnchorPane.setLeftAnchor(dateFromLabel, 5d);

        pane.getChildren().add(dateToLabel);
        AnchorPane.setTopAnchor(dateToLabel, 95d);
        AnchorPane.setLeftAnchor(dateToLabel, 5d);

        pane.getChildren().add(clientLabel);
        AnchorPane.setTopAnchor(clientLabel, 125d);
        AnchorPane.setLeftAnchor(clientLabel, 5d);

        TextField searchField = new TextField();
        TextField tempField = new TextField();
        TextField dateFromField = new TextField();
        TextField dateToField = new TextField();
        TextField clientField = new TextField();

//        pane.add(searchField, 1, 0, 10, 1);
//        pane.add(tempField, 1, 1,10,1);
//        pane.add(dateFromField, 1, 2,10,1);
//        pane.add(dateToField, 1, 3,10,1);
//        pane.add(clientField, 1, 4,10,1);

        pane.getChildren().add(searchField);
        AnchorPane.setTopAnchor(searchField, 2d);
        AnchorPane.setLeftAnchor(searchField, 110d);

        pane.getChildren().add(tempField);
        AnchorPane.setTopAnchor(tempField, 32d);
        AnchorPane.setLeftAnchor(tempField, 110d);

        pane.getChildren().add(dateFromField);
        AnchorPane.setTopAnchor(dateFromField, 62d);
        AnchorPane.setLeftAnchor(dateFromField, 110d);

        pane.getChildren().add(dateToField);
        AnchorPane.setTopAnchor(dateToField, 92d);
        AnchorPane.setLeftAnchor(dateToField, 110d);

        pane.getChildren().add(clientField);
        AnchorPane.setTopAnchor(clientField, 122d);
        AnchorPane.setLeftAnchor(clientField, 110d);

        Button searchButton = new Button("Search");
        Button exportButton = new Button("Export");

//        pane.add(searchButton, 11,0);
//        pane.add(exportButton, 11,1);

        pane.getChildren().add(searchButton);
        AnchorPane.setTopAnchor(searchButton, 2d);
        AnchorPane.setLeftAnchor(searchButton, 280d);

        pane.getChildren().add(exportButton);
        AnchorPane.setTopAnchor(exportButton, 32d);
        AnchorPane.setLeftAnchor(exportButton, 280d);

        ListView<File> filesView = new ListView<>();
        filesView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        pane.add(filesView, 0, 5, 41, 10);

        pane.getChildren().add(filesView);
        AnchorPane.setTopAnchor(filesView, 155d);
        AnchorPane.setLeftAnchor(filesView, 5d);
        AnchorPane.setBottomAnchor(filesView, 5d);
        AnchorPane.setRightAnchor(filesView, 5d);

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<File> listOfFiles = null;

                try{
                    fromDate = dateFormat.parse(dateFromField.getText());
                    toDate = dateFormat.parse(dateToField.getText());

                    SearchEngine searchEngine = new SearchEngine();
                    searchEngine.setDirectory(searchField.getText());
                    searchEngine.setClient(clientField.getText());
                    searchEngine.setFromDate(fromDate);
                    searchEngine.setToDate(toDate);

                    filesView.getItems().remove(0, filesView.getItems().size());
//                    long startTime = System.currentTimeMillis();
                    listOfFiles = searchEngine.getFilteredFiles();
//                    long stopTime = System.currentTimeMillis();
//                    System.out.println(stopTime - startTime);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }

                if (listOfFiles != null){
                    for (File file : listOfFiles){
                        filesView.getItems().add(file);
                    }
                }
            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<File> selectedFiles = filesView.getSelectionModel().getSelectedItems();
                try{
                    for (File file : selectedFiles){
                        Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(tempField.getText() + "\\" + file.getName()));
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("Directory search");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.show();
    }
}
