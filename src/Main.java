import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	private ToggleGroup choicesToggleGroup;
	private RadioButton[] choices;

	Button playQuizNoteButton;
	Button submitAnswer;
	Button nextButton;
	Button startButton;
	Button stopButton;

	Text correctAnswer;
	Text userAnswer;
	Text correctOrIncorrect;
	Text totalAnswers;
	Text correctAnswers;
	Text percentCorrect;

	private int total = 0;
	private int correct = 0;
	private float percent = 0;

	public static void main(String[] args) {
		initializeLisNote();
		launch(args);
	}

	private static void initializeLisNote() {
		String[] octave = { "CW", "DbW", "DW", "EbW", "EW", "FW", "GbW", "GW", "AbW", "AW", "BbW", "BW" };
		for (String note : octave) {
			octaveList.add(new Note(note));
		}
	}

	private void setPlayButtonEventHandler() {
		playQuizNoteButton.setOnAction((event) -> {
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

		playQuizNoteButton = createButton("play", "play", false);
		setPlayButtonEventHandler();

		grid.add(playQuizNoteButton, 0, 1);

		VBox choicesBox = new VBox();
		choicesToggleGroup = new ToggleGroup();

		choices = createChoices(choicesToggleGroup);

		choicesBox.getChildren().addAll(choices);

		grid.add(choicesBox, 0, 3);

		correctAnswer = createText("Correct Answer: ", "correctanswer", false);
		grid.add(correctAnswer, 0, 4);

		userAnswer = createText("user Answer: ", "useranswer", false);
		grid.add(userAnswer, 1, 4);

		correctOrIncorrect = createText("Correct or Incorrect", "correctorincorrect", false);
		grid.add(correctOrIncorrect, 2, 4);

		totalAnswers = createText("Total Answers: ", "totalanswers", false);
		grid.add(totalAnswers, 0, 5);

		correctAnswers = createText("Correct Answer: ", "correctanswer", false);
		grid.add(correctAnswers, 1, 5);

		percentCorrect = createText("Percent Correct", "percentcorrect", false);
		grid.add(percentCorrect, 2, 5);

		submitAnswer = createButton("submit", "submit", false);
		setOnActionSubmitButton();
		grid.add(submitAnswer, 0, 6);

		nextButton = createButton("next", "next", false);
		setOnActionNextButton();
		grid.add(nextButton, 5, 6);

		startButton = createButton("start", "start", true);
		stopButton = createButton("stop", "stop", false);

		setOnActionStartPitchTest();
		setOnActionStopButtonButton();

		grid.add(startButton, 5, 10);
		grid.add(stopButton, 5, 10);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setOnActionNextButton() {
		nextButton.setOnAction((event) -> {
			setQuizNote();
			setChoices();
			nextButton.setVisible(false);
			correctAnswer.setVisible(false);
			userAnswer.setVisible(false);
			correctOrIncorrect.setVisible(false);
			totalAnswers.setVisible(false);
			correctAnswers.setVisible(false);
			percentCorrect.setVisible(false);
			submitAnswer.setVisible(true);
			choicesToggleGroup.getSelectedToggle().setSelected(false);
		});
	}

	private void setOnActionSubmitButton() {

		submitAnswer.setOnAction(event -> {
			if (isChoiceSelect()) {
				String guess = getChoiceSelect();
				correctAnswer.setText("Correct Answer: " + filterChoices(playQuizNote.toString()));
				userAnswer.setText("User Answer: " + guess);

				if (filterChoices(playQuizNote.toString()).equals(guess)) {
					correctOrIncorrect.setText("correct!");
				} else {
					correctOrIncorrect.setText("INCORRECT");
				}
				submitAnswer.setVisible(false);

				nextButton.setVisible(true);
				correctAnswer.setVisible(true);
				userAnswer.setVisible(true);
				correctOrIncorrect.setVisible(true);

				totalAnswers.setVisible(true);
				correctAnswers.setVisible(true);
				percentCorrect.setVisible(true);
			} else {
				System.out.println("Need to select a choice");
			}
		});
	}

	private boolean isChoiceSelect() {
		return Arrays.stream(choices).anyMatch(b -> b.isSelected());
	}

	private String getChoiceSelect() {
		return Arrays.asList(choices).stream().filter(b -> b.isSelected()).findFirst().get().getText();
	}

	private Text createText(String value, String id, boolean isVisible) {
		Text text = new Text();
		text.setText(value);
		text.setId(id);
		text.setVisible(isVisible);
		return text;
	}

	private Button createButton(String value, String id, boolean isVisible) {
		Button button = new Button();
		button.setText(value);
		button.setId(id);
		button.setVisible(isVisible);
		return button;
	}

	private void setOnActionStartPitchTest() {
		startButton.setOnAction((event) -> {
			setQuizNote();
			setChoices();
			startButton.setVisible(false);
			stopButton.setVisible(true);
			playQuizNoteButton.setVisible(true);
			submitAnswer.setVisible(true);

			for (RadioButton choice : choices) {
				choice.setVisible(true);
			}
		});
	}

	private void setOnActionStopButtonButton() {
		stopButton.setOnAction((event) -> {
			startButton.setVisible(true);
			stopButton.setVisible(false);
			playQuizNoteButton.setVisible(false);
			submitAnswer.setVisible(false);
			correctAnswer.setVisible(false);
			userAnswer.setVisible(false);
			correctOrIncorrect.setVisible(false);
			totalAnswers.setVisible(false);
			correctAnswers.setVisible(false);
			percentCorrect.setVisible(false);
			for (RadioButton choice : choices) {
				choice.setVisible(false);
			}
		});

	}

	private void setChoices() {

		// Filter out all notes that are not the answer
		Object[] notes = (Object[]) octaveList.stream().filter(n -> !n.toString().equals(playQuizNote.toString()))
				.toArray();

		Random random = new Random();
		int answer = random.nextInt(choices.length);

		// Possibly use a predicate lambda expression
		for (int i = 0; i < 5; i++) {
			if (i == answer) {
				choices[i].setText(filterChoices(playQuizNote.toString()));
			} else {
				choices[i].setText(filterChoices(notes[i].toString()));
			}
		}
	}

	private String filterChoices(String note) {
		String[] choice = note.toString().split("w");
		if (choice[0].length() > 1 && choice[0].charAt(1) == 'B') {
			String correctNote = choice[0].charAt(0) + "b";
			return correctNote;
		}
		return choice[0];
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
			choice.setVisible(false);
			val++;
		}
		return choices;
	}

}
