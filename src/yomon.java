
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class yomon extends MIDlet {
    public Display display;
    public gamelvl1 gam;
    public loader lodr;
    public menu mnu;
    public int lvls;
    public int totallvls;

    public void startApp() {
        try{
            totallvls=5;
        display=Display.getDisplay(this);
        SplashScreen screen=new SplashScreen(this,"/loader.png");
        gam=new gamelvl1(this);
        lodr=new loader(this,gam);
        mnu=new menu(this);

        display.setCurrent(screen);
        }
        catch(Exception e){System.out.println(e);}  
    }

    public Display getDisplay() {
        return display;
    }
    public void showmenu(){
        display.setCurrent(mnu);
    }
    public void newgam()  {
        lvls=1;
        lodr.startloading("/lvl"+java.lang.Integer.toString(lvls)+".txt");
        gam.restart();
    }
    public void loadlevel() {
        if (totallvls<lvls) {
         SplashScreen screen=new SplashScreen(this,"/gamcom.png");
         display.setCurrent(screen);
         }
        else
            lodr.startloading("/lvl"+java.lang.Integer.toString(lvls)+".txt");


    }
    public void showgame() {
        display.setCurrent(gam);
    }
    public void pauseApp() {
    }
    public void startgame() {
        display.setCurrent(mnu);
    }
    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }

    void lost() {
        SplashScreen screen=new SplashScreen(this,"/gamovr.png");
        display.setCurrent(screen);
    }


}
