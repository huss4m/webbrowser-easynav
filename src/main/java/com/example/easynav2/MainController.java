package com.example.easynav2;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private WebView webView;

    @FXML
    private TextField urlField;

    private WebEngine engine;


    private String homePage;

    private double webZoom;

    private WebHistory history;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homePage="www.google.com";
        urlField.setText(homePage);
        engine = webView.getEngine();
        loadURL();
        // Ajouter un gestionnaire d'événements au TextField pour détecter l'appui sur la touche "Entrée"
        urlField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loadURL();
            }
        });
    }
    // Méthode pour charger l'URL dans le WebView
    private void loadURL() {
        String url = urlField.getText().trim(); // Supprime les espaces blancs au début et à la fin de l'URL
        if (!url.isEmpty()) {
            // Ajouter "http://" si l'URL ne commence pas par "http://" ou "https://"
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
           engine.load("http://"+urlField.getText());
        }
    }

    @FXML
    private void handleRefreshButtonAction() {
        loadURL();
    }

    @FXML
    private void handleZoomInButtonAction() {
        webZoom+=0.25;
        webView.setZoom(webZoom);
    }


    @FXML
    private void handleZoomOutButtonAction() {
        webZoom -= 0.25;
        webView.setZoom(webZoom);

    }


    public void displayHistory() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();


        for(WebHistory.Entry entry : entries) {
            System.out.println(entry);
        }
    }


    @FXML
    private void handlePreviousButtonAction() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();

        history.go(-1);
        urlField.setText(entries.get(history.getCurrentIndex()).getUrl());

    }


    @FXML
    private void handleNextButtonAction() {
        history = engine.getHistory();
        ObservableList<WebHistory.Entry> entries = history.getEntries();

        history.go(+1);
        urlField.setText(entries.get(history.getCurrentIndex()).getUrl());
    }




    /*TODO:
    for next session on this:
    -design appealing (css)
    -json file for history
    - open multiple tabs
    */
}