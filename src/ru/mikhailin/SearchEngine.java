package ru.mikhailin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchEngine {
    String directory;
    String client;

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    Date fromDate;
    Date toDate;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void setClient(String client) {
        this.client = client;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public ObservableList<String> getFiles(){
        if (directory.equals(""))
            return null;

        File dir = new File(directory);
        if (dir == null)
            return null;

        File[] files = dir.listFiles();

        List<String> listOfFiles = new ArrayList<>();
        for (File file : files){
            if (file.isFile())
                listOfFiles.add(file.getName());
        }

        ObservableList<String> obListOfFiles = FXCollections.observableArrayList(listOfFiles);

        return obListOfFiles;
    }

    public ObservableList<File> getFilteredFiles() throws ParseException {
        if (directory.equals("") || client.equals(""))
            return null;

        File dir = new File(directory);
        if (dir == null)
            return null;

        File[] files = dir.listFiles();

        List<File> listOfFiles = new ArrayList<>();
        for (File file : files){
            if (file.isFile()){
                String filename = file.getName();
                String[] fileNameSplitted = filename.split("-");
                Date fileDate = dateFormat.parse(fileNameSplitted[2] + "-" + fileNameSplitted[3] + "-" + fileNameSplitted[4]);

                if (fileNameSplitted[1].equals(client)){
                    if (fileDate.compareTo(fromDate) >= 0 && fileDate.compareTo(toDate) <= 0){
                        listOfFiles.add(file);
                    }
                }
            }
        }

        ObservableList<File> obListOfFiles = FXCollections.observableArrayList(listOfFiles);

        return obListOfFiles;
    }

}
