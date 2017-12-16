import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.io.IOException;

public class App extends Application
{
    FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
    Parent guiroot;
    Scene sc;
    Controller contr;


    public void start(Stage stg) throws IOException
    {
        guiroot = loader.load();
        sc = new Scene(guiroot);
        
        contr = loader.getController();

        stg.setScene(sc);
        stg.sizeToScene();
        stg.setTitle("");
        stg.setMinHeight(stg.getHeight());
        stg.setMinWidth(stg.getWidth());
        stg.show();
        
        stg.setOnCloseRequest( event ->
        {
            System.out.println("CLOSING");
            contr.close();
        });
        
    }

    public void stop()
    {
        System.out.println("STOP");
    }

    public static void main(String[] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
