import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.Button;

public class LayoutDemo extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button1 = new Button("Button1");

        HBox hbox = new HBox(button1);

        primaryStage.setScene(new Scene(hbox));
        primaryStage.setTitle("Layout Demo");
        primaryStage.show();
    }
}
