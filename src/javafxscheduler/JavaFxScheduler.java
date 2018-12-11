package javafxscheduler;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * CALENDAR PROJECT - CECS 343. 
 * @author Liam Nguyen and Meng Cha.
 */

public class JavaFxScheduler extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        DatabaseHandler db = new DatabaseHandler(); 
        //Create a database for the calendar if it's not existed yet. 
        db.createDatabase();
        //Launch the UI
        launch(args);
    }
    
}
