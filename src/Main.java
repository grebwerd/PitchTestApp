 import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
public class Main extends Application{

	public static Stage stage;
	public static void main(String[] args){
		
		/*Player player = new Player();
		String[] octave = {"Cw", "Dbw", "Dw", "EbW", "EW", "FW", "GbW", "GW", "AbW", "AW", "BbW", "BW"};
		
		List<Note> octaveList = new ArrayList<>();
		
		for(String s: octave){
			octaveList.add( new Note(s));
		}
		
		for(Note n: octaveList){
			player.play(n);
			//System.out.println("The octave of the note " + n.originalString + " is " + n.getOctave());
		}*/
		
       launch(args);		
	
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane grid = new GridPane();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.show();
	}
	
}
