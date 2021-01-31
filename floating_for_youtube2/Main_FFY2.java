package floating_for_youtube2;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main_FFY2 extends Application
{
	boolean check = false, checkpress=false, checkdrag = false;
	double x1=85.6, x2=512.8, 
		   y1=80, y2=320;
	double x, y;
	double orix, oriy;
	@Override
	public void start(Stage stage)
	{
		try {
			FileReader fr = new FileReader("data.txt");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);
			x1 = Double.parseDouble(br.readLine());
			y1 = Double.parseDouble(br.readLine());
			x2 = Double.parseDouble(br.readLine());
			y2 = Double.parseDouble(br.readLine());
		} catch (Exception e1) {
		}
		
		Pane pane = new Pane();
		Scene scene = new Scene(pane, x2-x1, 28);
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setAlwaysOnTop(true);
		stage.initStyle(StageStyle.TRANSPARENT);
		
		TextField tf = new TextField();
		tf.setPrefSize(x2-x1-50, 28);
		tf.setFocusTraversable(false);
		pane.getChildren().add(tf);
		
		Button btn = new Button("Enter");
		btn.setPrefSize(50, 28);
		btn.setLayoutX(tf.getLayoutX()+tf.getPrefWidth());
		pane.getChildren().add(btn);
		
//-------------------
		
		Pane newpn = new Pane();
		Scene newsc = new Scene(newpn, x2-x1, y2-y1);
		Stage newst = new Stage();

		newst.setScene(newsc);
		newst.setAlwaysOnTop(true);
		newst.setResizable(false);
		newst.initStyle(StageStyle.TRANSPARENT);
		
		
		WebView wb = new WebView();
		wb.setPrefSize(scene.getWidth(), scene.getHeight());
		wb.setPrefSize(600, 320);
		wb.setLayoutX(-x1);
		wb.setLayoutY(-y1);
		newpn.getChildren().add(wb);

		WebEngine we = wb.getEngine();

		
		ImageView mask = new ImageView(new Image(getClass().getResourceAsStream("pic.png")));
		mask.setLayoutX(x2-50);
		mask.setLayoutY(0);
		mask.setOpacity(0.2);
		newpn.getChildren().add(mask);

//----------------------
		
		newst.show();
		stage.show();

		stage.setX(newst.getX());
		stage.setY(newst.getY()+newst.getHeight());
		
//----------------------
		
		btn.setOnAction(e->{
			we.load(tf.getText());
		});
		
		mask.setOnMouseEntered(e->{
			mask.setOpacity(1);
		});
		mask.setOnMouseExited(e->{
			mask.setOpacity(0.2);
		});
		mask.setOnMousePressed(e->{
			if(!checkpress)
			{
				x = e.getScreenX();
				y = e.getScreenY();
				orix=newst.getX();
				oriy=newst.getY();
				checkpress = true;
			}
		});
		mask.setOnMouseReleased(e->{
			checkpress = false;
		});;
		mask.setOnMouseDragged(e->{
			newst.setX(orix+e.getScreenX()-x);
			newst.setY(oriy+e.getScreenY()-y);
			stage.setX(newst.getX());
			stage.setY(newst.getY()+newst.getHeight());
		});
		
		stage.setOnCloseRequest(e->{
			newst.close();
		});
		newst.setOnCloseRequest(e->{
			stage.close();
		});
		
		
	}
	
	public static void main(String[] args)
	{
		launch();
	}
}
