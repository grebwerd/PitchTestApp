import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.jfugue.player.Player;
import org.jfugue.theory.Note;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	public static Stage stage;
	final static private List<Note> octaveList = new ArrayList<>();
	final static private Player player = new Player();
	private Note playQuizNote = null;
	private RadioButton[] choices;

	public static void main(String[] args) {
		initializeLisNote();
		launch(args);
	}

	private static void initializeLisNote() {
		String[] octave = { "Cw", "Dbw", "Dw", "EbW", "EW", "FW", "GbW", "GW", "AbW", "AW", "BbW", "BW" };
		for (String note : octave) {
			octaveList.add(new Note(note));
		}

	}

	private void setPlayButtonEventHandler(Button playButton) {
		playButton.setOnAction((event) -> {
			player.play(playQuizNote);
		});
	}

	private void setQuizNote() {
			Collections.shuffle(octaveList);
            playQuizNote = octaveList.get(0);			
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Pitch Test");
		primaryStage.sizeToScene();

		GridPane grid = createGridPane();
		Scene scene = new Scene(grid, 650, 500);

		Text sceneTitle = createSceneTitle();
		grid.add(sceneTitle, 0, 0, 2, 1);

		Button playQuizNote = createPlayQuizNote();

		grid.add(playQuizNote, 0, 1);

		VBox choicesBox = new VBox();
		ToggleGroup choicesToggleGroup = new ToggleGroup();

		choices = createChoices(choicesToggleGroup);

		choicesBox.getChildren().addAll(choices);

		grid.add(choicesBox, 0, 3);

		Text correctAnswer = new Text();
		correctAnswer.setText("Correct Answer: ");
		grid.add(correctAnswer, 0, 4);

		Text userAnswer = new Text();
		userAnswer.setText("Your Answer: ");
		grid.add(userAnswer, 1, 4);

		Text correctOrIncorrect = new Text();
		correctOrIncorrect.setText("TODO: Green or Red");
		grid.add(correctOrIncorrect, 2, 4);

		Text totalAnswers = new Text();
		totalAnswers.setText("Total Answers: ");
		grid.add(totalAnswers, 0, 5);

		Text correctAnswers = new Text();
		correctAnswers.setText("Correct Answer: ");
		grid.add(correctAnswers, 1, 5);

		Text percentCorrect = new Text();
		percentCorrect.setText("Percent correct: ");
		grid.add(percentCorrect, 2, 5);

		Button submitAnswer = new Button();
		submitAnswer.setText("submit");
		submitAnswer.setId("submit");

		grid.add(submitAnswer, 0, 6);

		Button nextQuestion = createNextButton();
		grid.add(nextQuestion, 5, 6);

		Button startButton = createStartButton();
		Button stopButton = createStopButton();

		grid.add(startButton, 4, 10);
		grid.add(stopButton, 5, 10);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private Button createNextButton() {
		Button nextQuestion = new Button();
		nextQuestion.setText("next");
		nextQuestion.setId("next");
		return nextQuestion;
	}

	private Button createStopButton() {
		Button stopButton = new Button();
		stopButton.setText("Stop");
		stopButton.setId("cancel");
		stopButton.setVisible(true);
		return stopButton;
	}

	private Button createStartButton() {
		Button startButton = new Button();
		startButton.setText("Start");
		startButton.setId("start");
		startButton.setVisible(true);
		startButton.setOnAction((event) -> {
			setQuizNote();
			setChoices();
		});
		return startButton;
	}

	private void setChoices() {
		
		Object[] notes =  (Object[]) octaveList.stream().filter(n -> !n.toString().equals(playQuizNote.toString())).toArray();
		
		Random random = new Random();
		int answer = random.nextInt(choices.length);
		
		for (int i = 0; i < 5; i++ ) {
			if (i == answer) {
				String[] choice = playQuizNote.toString().split("w");
				choices[i].setText(choice[0]);
			}  
			else{
				String[] choice = notes[i].toString().split("w");
				choices[i].setText(choice[0]);
			}
		}
	}

	private GridPane createGridPane() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(20);
		grid.setVgap(20);

		grid.setPadding(new Insets(25, 25, 25, 25));
		return grid;
	}

	private Text createSceneTitle() {
		Text sceneTitle = new Text("Pitch Test");
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		return sceneTitle;
	}

	private RadioButton[] createChoices(ToggleGroup choicesToggleGroup) {
		RadioButton[] choices = new RadioButton[5];

		choices[0] = new RadioButton();
		choices[1] = new RadioButton();
		choices[2] = new RadioButton();
		choices[3] = new RadioButton();
		choices[4] = new RadioButton();

		int val = 0;
		for (RadioButton choice : choices) {
			choice.setId("choice" + val);
			choice.setText("choice" + val);
			choice.setToggleGroup(choicesToggleGroup);
			val++;
		}
		return choices;
	}

	private Button createPlayQuizNote() {
		Button playQuizNote = new Button();
		playQuizNote.setText("Play Note");
		playQuizNote.setVisible(true);
		setPlayButtonEventHandler(playQuizNote);
		return playQuizNote;
	}

	private void setChoicesText(RadioButton radioButton) {

	}

}
