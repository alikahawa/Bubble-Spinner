package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TutorialController {
    @FXML
    private transient ImageView difficulties;
    @FXML
    private transient ImageView shoot;
    @FXML
    private transient ImageView destroy;
    @FXML
    private transient Button menu;

    /**
     * Initializes the images used in the tutorial.
     */
    public void initialize() {
        Image i = new Image("/tutorial/diff.png");
        difficulties.setImage(i);

        Image s = new Image("/tutorial/shoot.gif");
        shoot.setImage(s);

        Image d = new Image("/tutorial/destroy.gif");
        destroy.setImage(d);
    }

    /**
     * Handler when clicked on menu button.
     *
     * @param event information about the button that called the method.
     */
    @FXML
    protected void handleMenuButtonAction(ActionEvent event) {
        SceneLoader.loadScene("/mainScreen.fxml");
    }
}
