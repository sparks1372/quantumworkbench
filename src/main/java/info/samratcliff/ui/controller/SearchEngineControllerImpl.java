package info.samratcliff.ui.controller;

import Core.CircuitEvolution.searchenginemanager;
import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchEngineControllerImpl implements SearchEngineController,
        Initializable {

    private final searchenginemanager sem;
    @FXML
    private ComboBox<String> searchengineselectioncombo;

    @FXML
    private Text searchengineselectiondescription;

    @Inject
    public SearchEngineControllerImpl(searchenginemanager sem) {
        super();
        this.sem = sem;
    }

    @Override
    @FXML
    public ObservableList<String> getItems() {
        return FXCollections.observableArrayList(sem
                .getAvailableSearchEngines());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        searchengineselectioncombo.getItems().setAll(getItems());
        // listen for changes to the fruit combo box selection and update the
        // displayed fruit image accordingly.
        searchengineselectioncombo.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends String> selected,
                            String oldSelection, String newSelection) {
                        searchengineselectiondescription.setText(sem
                                .getSearchEngineDesc(newSelection));
                    }
                });
    }

}
