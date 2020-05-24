package animation;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import javafx.scene.Node;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node){
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setFromX(-20f);
        tt.setByX(20f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
    }

    public void playAnim(){
        tt.playFromStart();
    }

}
