/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Manish
 */
import java.util.TimerTask;

public class refrsh extends TimerTask{
    private gamelvl1 gam;

    public refrsh(gamelvl1 gam) {
        this.gam=gam;
    }
    public void run() {
        gam.refresh();
    }
}
