package ShowLine;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

public class ShowLine extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new LinePane(), 200, 200);
    primaryStage.setTitle("ShowLine"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}

class LinePane extends Pane {
  public LinePane() {
    Line line1 = new Line(10, 10, 10, 10);
    line1.setEndX(200);
    line1.setEndY(200);
    line1.setStrokeWidth(10);
    line1.setStroke(Color.GREEN);
    getChildren().add(line1);
    
    Line line2 = new Line(10, 10, 10, 10);
    line2.setStartX(200);
    line2.setEndY(200);
    line2.setStrokeWidth(10);
    line2.setStroke(Color.RED);
    getChildren().add(line2);
  }
}
