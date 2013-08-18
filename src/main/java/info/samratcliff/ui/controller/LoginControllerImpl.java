package info.samratcliff.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LoginControllerImpl implements LoginController {
    @FXML
    private Text actiontarget;

    @FXML
    @Override
    public void handleSubmitButtonAction(ActionEvent event) {
        actiontarget.setText("Sign in button pressed");
    }
}
