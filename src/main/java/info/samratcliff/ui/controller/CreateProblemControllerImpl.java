package info.samratcliff.ui.controller;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import jfxtras.labs.dialogs.MonologFX;
import jfxtras.labs.dialogs.MonologFXBuilder;
import jfxtras.labs.dialogs.MonologFXButtonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreateProblemControllerImpl implements CreateProblemController,
        Initializable {
    Logger log = LoggerFactory.getLogger("ProblemController");
    @FXML
    private TextField destinationfile;
    @FXML
    private TextField problemname;
    @FXML
    private TextArea problemdescription;

    private final StringProperty problemNameProperty;
    private final StringProperty problemDescriptionProperty;
    private final StringProperty destinationFileProperty;
    private final ProblemController pc;
    private final FileChooser fc;

    @Inject
    public CreateProblemControllerImpl(ProblemController pc, FileChooser fc) {
        super();
        this.problemNameProperty = new SimpleStringProperty();
        this.problemDescriptionProperty = new SimpleStringProperty();
        this.destinationFileProperty = new SimpleStringProperty();
        this.pc = pc;
        this.fc = fc;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Bindings.bindBidirectional(destinationFileProperty,
                destinationfile.textProperty());
        Bindings.bindBidirectional(problemNameProperty,
                problemname.textProperty());
        Bindings.bindBidirectional(problemDescriptionProperty,
                problemdescription.textProperty());
    }

    @Override
    public void addTestSet(ActionEvent event) {
        List<String> buttonLabels = Lists.newArrayListWithCapacity(4);
        buttonLabels.add("Cancel");
        buttonLabels.add("0");
        buttonLabels.add("1");
        buttonLabels.add("2");
        buttonLabels.add("3");

        MonologFXButtonBuilder cancelButtonBuilder = MonologFXButtonBuilder.create();
        cancelButtonBuilder.cancelButton(true);

        MonologFXBuilder builder = MonologFXBuilder.create();
        builder = builder.stylesheet("login.css");
        builder = builder.message("Add Test Set for how many Qubits?");
        builder.button(cancelButtonBuilder.build());

        for(int x = 1 ; x<=3;x++){
            MonologFXButtonBuilder buttonBuilder = MonologFXButtonBuilder.create();
            buttonBuilder.label(Integer.toString(x));
            builder.button(buttonBuilder.build());
        }

        MonologFX dialog = builder.build();

        dialog.showDialog();
    }

    @Override
    public void chooseFileAction(ActionEvent event) throws IOException {
        File f = fc.showSaveDialog(null);
        if (f != null) {
            destinationFileProperty.setValue(f.getAbsolutePath());
            fc.setInitialDirectory(f.getParentFile());
        }
    }

    @Override
    public void okayAction(ActionEvent event) throws IOException {
        log.info("Creating problem with name : "
                + problemNameProperty.getValue() + " and description : \""
                + problemDescriptionProperty.getValue()
                + "\" to be saved in file "
                + destinationFileProperty.getValue());
        pc.cancelCreateProblem();
    }

    @Override
    public void cancelAction(ActionEvent event) {
        pc.cancelCreateProblem();
    }
}
