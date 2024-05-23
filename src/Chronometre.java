import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Chronometre extends Text{
    private Timeline timeline;
    private KeyFrame keyFrame;
    private long startTime;

    public Chronometre(){
        setText("0:0:0");
        setFont(Font.font("Arial", 20));
        setTextAlignment(TextAlignment.CENTER);

        keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            long elapsedMillis = System.currentTimeMillis() - startTime;
            setTime(elapsedMillis);
        });

        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    public void setTime(long tempsMillisec){
        long secondes = tempsMillisec / 1000;
        long minutes = secondes / 60;
        long heures = minutes / 60;

        setText(String.format("%d:%d:%d", heures, minutes % 60, secondes % 60));
    }

    public void start(){
        startTime = System.currentTimeMillis();
        timeline.play();
    }

    public void stop(){
        timeline.stop();
    }

    public void resetTime(){
        startTime = System.currentTimeMillis();
        setTime(0);
    }

    public double getTime(){
        return System.currentTimeMillis() - startTime;
    }
}