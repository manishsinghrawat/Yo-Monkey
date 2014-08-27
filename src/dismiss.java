
import java.util.TimerTask;

public class dismiss extends TimerTask{
    private SplashScreen screen;
    public dismiss(SplashScreen screen) {
        this.screen=screen;
    }

    public void run() {
        screen.doit();
    }

}
