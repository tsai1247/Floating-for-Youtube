package floating_for_youtube;

import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application
{
	boolean check = false, checkpress=false, checkdrag = false;
	double x1=85.6, x2=512.8, 
		   y1=80, y2=320;
	double x, y;
	double orix, oriy;
	@Override
	public void start(Stage stage)
	{
		Pane pane = new Pane();
		Scene scene = new Scene(pane, 1300, 750);

		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("一般頁面");
		
		WebView wb = new WebView();
		wb.setPrefSize(scene.getWidth(), scene.getHeight());
		pane.getChildren().add(wb);

		WebEngine we = wb.getEngine();

		Button btn = new Button("縮小視窗");
		btn.setOpacity(0.2);
		btn.setLayoutX(24);
		btn.setLayoutY(80);
		btn.setFocusTraversable(false);
		pane.getChildren().add(btn);
		
		Pane newpn = new Pane();
		Scene newsc = new Scene(newpn, x2-x1, y2-y1);
		
		Stage newst = new Stage();
		newst.setScene(newsc);
		newst.setAlwaysOnTop(true);
		newst.setResizable(false);
		newst.initStyle(StageStyle.TRANSPARENT);
		newst.setTitle("切割頁面");
		
		ImageView mask = new ImageView(new Image(getClass().getResourceAsStream("pic.png")));
		mask.setLayoutX(405);
		mask.setLayoutY(0);
		mask.setOpacity(0.2);

//-----------------------------------
		stage.show();
		
		we.load("https://www.youtube.com/");

		btn.setOnMouseEntered(e->{
			btn.setOpacity(1);
		});
		btn.setOnMouseExited(e->{
			btn.setOpacity(0.2);
		});

		btn.setOnAction(e->{
			if(check)
			{
				newpn.getChildren().remove(wb);
				pane.getChildren().add(wb);
				wb.setLayoutX(0);
				wb.setLayoutY(0);
				wb.setPrefSize(1300, 750);

				btn.setLayoutX(24);
				btn.setLayoutY(80);
				
				stage.show();
				newst.close();
				newpn.getChildren().remove(mask);
				newpn.getChildren().remove(btn);
				pane.getChildren().add(btn);
				btn.setText("縮小視窗");
			}
			else
			{
				pane.getChildren().remove(wb);
				newpn.getChildren().add(wb);
				wb.setPrefSize(600, 320);
				wb.setLayoutX(-x1);
				wb.setLayoutY(-y1);
				
				btn.setLayoutX(0);
				btn.setLayoutY(0);
				
				newst.show();
				stage.close();
				newpn.getChildren().add(mask);
				pane.getChildren().remove(btn);
				newpn.getChildren().add(btn);
				btn.setText("放大視窗");
			}
			check = !check;
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
		});
	}
	
	public static void main(String[] args)
	{
		launch();
	}
}
