package com.example.parserwb;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class WindowController {

    public ComboBox<String> typebox;
    public ComboBox<String> podtype;
    public Parser parser = new Parser();

    public WindowController() throws IOException, ParseException {
    }

    @FXML
    public void initialize(){
        ObservableList<String> list_type = FXCollections.observableArrayList(parser.getProductTypes().keySet());
        typebox.setItems(list_type);
    }

    public void changeType(ActionEvent actionEvent) {
        String[] list_type_reader = parser.getProductTypes().get(typebox.getValue())
                .replace("[","")
                .replace("]", "")
                .split(",");
        ObservableList<String> list_podtype = FXCollections.observableArrayList(list_type_reader);
        podtype.setItems(list_podtype);
        podtype.setValue(list_podtype.get(0));
    }
}
