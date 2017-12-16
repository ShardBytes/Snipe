import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller implements Initializable
{
    
    // NODES
    @FXML JFXButton cameraButton;
    @FXML ImageView imageView;
    
    // OpenCV
    
    private VideoCapture cap = new VideoCapture();
    private boolean cameraActive = false;
    private static int cameraId = 1;

    private ScheduledExecutorService timer;
    private Runnable frameGrabber = () -> {
        Mat frame = grabFrame();
        CVUtility.onFXThread(imageView.imageProperty(), CVUtility.mat2Image(frame));
    };
    
    // Handling methods
    
    @FXML
    protected void startCamera(ActionEvent event)
    {
        if (!cameraActive)
        {
            cap.open(cameraId);
            if (cap.isOpened())
            {
                cameraActive = true;
                timer = Executors.newSingleThreadScheduledExecutor();
                timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
                
                cameraButton.setText("Stop Camera");
            } else {
                System.err.println("Cant open camera");
                cameraButton.setText("ERROR");
            }
        } else {
            cameraActive = false;
            cameraButton.setText("Start Camera");
            stopAcquisition();
        }
    }
    
    // cv methods
    private Mat grabFrame()
    {
        Mat frame = new Mat(); // empty mat
        if (cap.isOpened())
        {
            try
            {
                cap.read(frame);
                if (!frame.empty()) process(frame);
            }
            catch(Exception ex)
            {
                System.err.println("Error during image processing.");
            }
        }
        
        return frame;
    }
    
    private void stopAcquisition()
    {
        if (timer!=null && !timer.isShutdown())
        {
            try
            {
                timer.shutdown();
                timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {e.printStackTrace();}
        }
        
        if (cap.isOpened()) cap.release();
    }
    
    // process the frame here
    
    private void process(Mat f)
    {
        
    }
    
    // controller methods
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        
    }
    
    protected void close()
    {
        stopAcquisition();
    }
    
}
