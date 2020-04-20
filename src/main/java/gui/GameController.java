package gui;

import auth.DatabaseConnection;
import game.Game;
import game.Observer;
import game.ScoreCalculator;
import game.UpdateType;
import grid.Coordinate;
import grid.Grid;

import grid.GridCreator;
import grid.bubble.Bubble;
import grid.bubble.ShotBubble;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * This class contains the magic of the game.
 * All the logic which is implemented in the game is used in this class.
 *
 * @author Team SEM-13.
 */
public class GameController implements Observer {

    private static final int MIN_OBJECTS = 2;
    private static final Circle NULL_CIRCLE = null;
    private transient Game game;

    @FXML
    private transient Pane panee;

    @FXML
    private transient Pane playField;

    @FXML
    private transient Text scoreText;

    transient Pane canvas;
    transient int bounces = 0;
    transient Timeline timeline;
    transient double originX;
    transient double originY;
    transient Line line;
    transient Circle shotBubble;
    transient Scene scene;
    transient Stage stage;

    private transient Circle[][] circles;
    private transient String gridFile;
    private transient Grid grid;
    private transient List<Circle> removedCircles;
    private transient List<String> levels;
    private transient int level = 0;
    private transient ScoreCalculator scoreCalculator = new ScoreCalculator(Main.player, 0);

    private transient Circle current;
    private transient Circle next;

    private transient int maxLevel;

    /**
     * Method automatically called by the fxml file that initializes the scene for the game.
     */
    @FXML
    public void initialize() {
        setLevels();
        gridFile = levels.get(level);
        Grid grid = GridCreator.makeGridFromFile(gridFile);
        game = new Game(grid,(int)playField.getPrefWidth() / 2,
                (int)playField.getPrefHeight(),scoreCalculator);
        scene = panee.getScene();
        stage = Main.stage;
        circles = new Circle[grid.getWidth()][grid.getHeight()];
        canvas = playField;
        stage.setTitle("Bubble Spinner");
        game.addObserver(this);
        originX = playField.getPrefWidth() / 2;
        originY = playField.getPrefHeight() - 10;
        game.setCannonPosition(new Coordinate((int) originX, (int) originY));
        grid.centerGrid(playField.getPrefWidth(), playField.getPrefHeight());
        removedCircles = new LinkedList<>();
        stage.setOnCloseRequest((WindowEvent event) -> {
            game.quit();
        });
        DatabaseConnection.retrieveScoreBoard();
        //updateGrid();
    }

    private void setLevels() {
        levels = new ArrayList<>();
        levels.add("/levels/easy1.txt");
        levels.add("/levels/easy2.txt");
        levels.add("/levels/easy3.txt");
        levels.add("/levels/normal1.txt");
        levels.add("/levels/normal2.txt");
        levels.add("/levels/normal3.txt");
        levels.add("/levels/hard1.txt");
        levels.add("/levels/hard2.txt");
        levels.add("/levels/hard3.txt");
        levels.add("/levels/extremeBonus.txt");

        maxLevel = 9;
    }


    /**
     * Method that updates the grid that is shown on screen.
     */
    private void updateGrid() {
        Grid grid = game.getGrid();
        for (int i = 0; i < grid.getHeight(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {

                Coordinate coordinate = new Coordinate(j, i);
                Bubble bubble = grid.getBubble(coordinate);

                if (bubble == null) {
                    //System.out.println("BUBBLE IS NULL");
                    if (circles[j][i] == null) {
                        continue;
                    }
                    Circle c = circles[j][i];
                    Timeline remove = new Timeline();
                    remove.setCycleCount(1);
                    remove.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                            new KeyValue(c.radiusProperty(), 0)));
                    remove.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            removedCircles.remove(c);
                            canvas.getChildren().remove(c);
                        }
                    });
                    //System.out.println("removed");
                    removedCircles.add(c);
                    circles[j][i] = NULL_CIRCLE;
                    remove.play();

                } else {
                    //System.out.println("CREATING BUBBLE");
                    Coordinate worldCoordinateBubble = Coordinate.positionBubbleWorld(coordinate,
                            grid);
                    java.awt.Color colorBubble = bubble.getColor();
                    Color color = Color.rgb(colorBubble.getRed(), colorBubble.getGreen(),
                            colorBubble.getBlue(), colorBubble.getAlpha() / 255.0);
                    if (circles[j][i] == null) {
                        Circle ball = new Circle(bubble.getSize() / 2f, color);
                        ball.relocate(worldCoordinateBubble.getXcoord(),
                                worldCoordinateBubble.getYcoord());
                        canvas.getChildren().add(ball);
                        circles[j][i] = ball;
                    } else {

                        circles[j][i].setFill(color);
                        circles[j][i].relocate(worldCoordinateBubble.getXcoord(),
                                worldCoordinateBubble.getYcoord());
                    }
                }
            }
        }
        //System.out.println("updating grid2");
    }

    /**
     * Method that plays a sound whenever specific events happen.
     *
     * @param file the name of the sound file that is about to be played.
     */
    public static synchronized void playSound(final String file) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            new File("src/main/resources/sounds/" + file));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println("No audio file found");
                }
            }
        }).start();
    }

    /**
     * Method that adds en event which creates the arrow that moves
     * on the screen depending on the mouse coordinates.
     *
     * @param e event happening
     */
    @SuppressWarnings("PMD")
    public void mouseArrow(MouseEvent e) {
        if (canvas.getChildren().size() > 0
                && canvas.getChildren().get(0) instanceof Line) {
            canvas.getChildren().remove(0);
        }
        double mouseX = e.getX();
        double mouseY = e.getY();

        double distance = Math.sqrt((originX - mouseX) * (originX - mouseX)
                + (originY - mouseY) * (originY - mouseY));
        double ratio = 75 / distance;

        line = new Line(originX, originY, (1 - ratio) * originX + ratio * mouseX,
                (1 - ratio) * originY + ratio * mouseY);

        canvas.getChildren().add(0, line);
    }

    /**
     * Method that shoots the ball.
     *
     * @param stage      stage of the game
     * @param mouseEvent the MOUSE_PRESSED event, which is used for the coordinates of the mouse.
     * @param scene      scene of the game
     */
    @SuppressWarnings("PMD")
    public void shootBall(Stage stage, MouseEvent mouseEvent, Scene scene) {
        int mouseX = (int) mouseEvent.getX();
        int mouseY = (int) mouseEvent.getY();
        game.shoot(mouseX, mouseY);
        //        if(mouseEvent.getY() >= 650) {
        //            //System.out.println(mouseEvent.getY());
        //            return;
        //        }
        //        Circle ball = new Circle(10, Color.GRAY);
        //        ball.relocate(360, 690);
        //        canvas.getChildren().add(ball);
        //
        //        double mouseX = mouseEvent.getX();
        //        double mouseY = mouseEvent.getY();
        //        double angleDeg = Math.atan2(mouseY - originY, mouseX - originX);
        //
        //        timeline = new Timeline(new KeyFrame(Duration.millis(1),
        //                new EventHandler<ActionEvent>() {
        //
        //                    double dx = Math.cos(angleDeg);
        //                    double dy = Math.sin(angleDeg);
        //
        //                    @Override
        //                    public void handle(ActionEvent t) {
        //                        //move the ball
        //                        ball.setLayoutX(ball.getLayoutX() + dx);
        //                        ball.setLayoutY(ball.getLayoutY() + dy);
        //
        //                        /////// reflection with the bounds/////////
        //                        Bounds bounds = canvas.getBoundsInLocal();
        //
        //                        //If the ball reaches the left
        //                        or right border make the step negative
        //                        if (ball.getLayoutX() <= (bounds.getMinX() + ball.getRadius())
        //                        || ball.getLayoutX() >= (bounds.getMaxX() - ball.getRadius())) {
        //                            bounces++;
        //                            dx = -dx;
        //                            if (bounces >= 3) {
        //                                shootOnClick(stage, scene, canvas);
        //                            }
        //                        }
        //
        //                        //If the ball reaches the bottom or
        //                        top border make the step negative
        //                        if ((ball.getLayoutY() >= (bounds.getMaxY()
        //                        - ball.getRadius()))
        //                        || (ball.getLayoutY() <= (bounds.getMinY() + ball.getRadius()))) {
        //                            bounces++;
        //                            dy = -dy;
        //                            if (bounces >= 3) {
        //                                shootOnClick(stage, scene, canvas);
        //                            }
        //                        }
        //                    }
        //                }));
        //
        //        timeline.setCycleCount(Timeline.INDEFINITE);
        //        timeline.play();
    }

    //    /**
    //     * Method that either stops the timeline if one is running,
    //     * or adds a mouse event which initializes the shooting of the next bubble.
    //     *
    //     * @param stage  stage of the game
    //     * @param scene  scene of the game
    //     * @param canvas canvas of the game (Pane)
    //     */
    ////    public void shootOnClick(Stage stage, Scene scene, Pane canvas) {
    ////
    ////        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
    ////            @Override
    ////            public void handle(MouseEvent mouseEvent) {
    ////                mouseEvent.consume();
    ////                shootBall(stage, mouseEvent, scene);
    ////                bounces = 0;
    ////                //canvas.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
    ////            }
    ////        });
    ////    }

    /**
     * Handles the mouseclicking that might lead to firing a ball.
     *
     * @param event mouseevent containg the info of the mouse position.
     */
    public void handleMouse(Event event) {
        event.consume();
        shootBall(stage, (MouseEvent) event, scene);
        bounces = 0;
        //canvas.removeEventFilter(MouseEvent.MOUSE_PRESSED, this);
    }

    /**
     * Method used to pause the game.
     *
     * @param event event used to call the method
     */
    public void pauseGame(Event event) {
        System.out.println("game paused");
        game.pause();
    }

    /**
     * Method used to play the game.
     *
     * @param event event used to call the method.
     */
    public void playGame(Event event) {
        System.out.println("game started");
        game.play();
        displayCurrentAndNext();
    }

    /**
     * When a player wins the game it continues to the win screen.
     */
    private void win() {
        DatabaseConnection.writeScore(scoreCalculator.getPlayer().getUsername(),
                scoreCalculator.getScore());
        if (level >= maxLevel) {
            SceneLoader.loadSceneGame("/winGameScreen.fxml", level, scoreCalculator);
        } else {
            SceneLoader.loadSceneGame("/winScreen.fxml", level, scoreCalculator);
        }
    }

    /**
     * Method that gets called when the game has an update.
     * Handles different things based on the updateType.
     *
     * @param type the type of the update that happens.
     */
    public void update(UpdateType type) {

        switch (type) {
            case GRID:
                Platform.runLater(this::updateGrid);
                Platform.runLater(this::updateScore);
                break;
            case SHOT:
                System.out.println("SHOT");
                createShot();
                playSound("shot.wav");
                displayCurrentAndNext();
                break;
            case MOVE:
                Platform.runLater(this::move);
                break;
            case DESTROY:
                System.out.println("DESTROY");
                playSound("shot.wav");
                Platform.runLater(() -> clearBall());
                break;
            case WIN:
                Platform.runLater(() -> win());
                break;
            case BOUNCE:
                System.out.println("BOUNCE");
                playSound("bounce.wav");
                break;
            case LOSE:
                Platform.runLater(() -> lose());
                break;
            default:
                System.out.println("you called an invalid, or unimplemented update type!");
                System.out.println(type);
                break;
        }
    }

    private void lose() {
        game.pause();
        DatabaseConnection.writeScore(scoreCalculator.getPlayer().getUsername(),
                scoreCalculator.getScore());
        SceneLoader.loadSceneGame("/loseGameScreen.fxml", level, scoreCalculator);
    }

    private void updateScore() {
        scoreText.setText("Score: " + game.getScore());
    }

    private void clearBall() {
        canvas.getChildren().remove(shotBubble);
        shotBubble = NULL_CIRCLE;
    }

    private void move() {
        ShotBubble bub = game.getShotBubble();
        if (bub != null && shotBubble != null) {
            shotBubble.relocate(bub.getXpos() - (bub.getSize() / 2),
                    bub.getYpos() - (bub.getSize() / 2));
        }

    }

    private void createShot() {
        ShotBubble bubble = game.getShotBubble();
        java.awt.Color colorBubble = bubble.getColor();
        Color color = Color.rgb(colorBubble.getRed(), colorBubble.getGreen(),
                colorBubble.getBlue(), colorBubble.getAlpha() / 255.0);
        shotBubble = new Circle(bubble.getSize() / 2, color);
        shotBubble.relocate(bubble.getXpos(), bubble.getYpos());
        playField.getChildren().add(shotBubble);
    }

    /**
     * Sets the level. Calls initialize to push the change through.
     *
     * @param level The level to set the level to.
     */
    public void setLevelScoreCalculatorAndInitialize(int level, ScoreCalculator scoreCalculator) {
        this.level = level;
        this.scoreCalculator = scoreCalculator;
        System.out.println("score in game: " + this.scoreCalculator.getScore());
        System.out.println("score that has to be in game: " + scoreCalculator.getScore());
        Platform.runLater(this::updateScore);
        initialize();
        updateGrid();
    }

    /**
     * Method to display the current and next bubble on the screen.
     */
    public void displayCurrentAndNext() {
        Color colorCurrent = Color.rgb(game.getCurrentColor().getRed(),
                game.getCurrentColor().getGreen(),
                game.getCurrentColor().getBlue(), game.getCurrentColor().getAlpha() / 255.0);
        current = new Circle(10, colorCurrent);
        current.relocate(originX - current.getRadius(), originY);
        playField.getChildren().add(current);

        Color colorNext = Color.rgb(game.getNextColor().getRed(), game.getNextColor().getGreen(),
                game.getNextColor().getBlue(), game.getNextColor().getAlpha() / 255.0);
        next = new Circle(10, colorNext);
        next.relocate(originX - 40, originY);
        playField.getChildren().add(next);
    }
}

