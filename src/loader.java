
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class loader {

    private yomon midlet;
    private gamelvl1 gam;
    InputStream is;
    int chars;
    StringBuffer sb;
    int bananas, obstacles;
    int i;
    String file;
    Image temp;

    public loader(yomon midlet, gamelvl1 gam) {
        this.midlet = midlet;
        this.gam = gam;
        sb = new StringBuffer();

    }

    public void startloading(String file) {
        this.file = new String(file);
        is = getClass().getResourceAsStream(file);
        bananas = Integer.parseInt(getString(is));
        obstacles = Integer.parseInt(getString(is));
        gam.fuel=Integer.parseInt(getString(is));
        gam.initialize(bananas, obstacles);

        try {
             gam.images = Image.createImage(getString(is));
        } catch (IOException ex) {}

        gam.posobj(Integer.parseInt(getString(is)),Integer.parseInt(getString(is)),Integer.parseInt(getString(is)),Integer.parseInt(getString(is)));

        try {
            temp = Image.createImage("/banana.png");
        } catch (IOException ex) {}
        for (i = 0; i < bananas; i++) {
            gam.bana[i] = new Sprite(temp, 24, 19);
            gam.state[i] = 0;
            gam.countdown[i] = 3;

            gam.bana[i].setPosition(Integer.parseInt(getString(is)),Integer.parseInt(getString(is)));
        }
        try {
            if ((obstacles>0)) {
                for (i = 0; i < obstacles; i++) {
                
                    temp = Image.createImage(getString(is));
                    gam.obst[i] = new Sprite(temp);
                
                    gam.obst[i].setPosition(Integer.parseInt(getString(is)), Integer.parseInt(getString(is)));
                }
            }
        } catch (Exception ie) {}

        try {
        gam.finalset();
        }catch (Exception ex) {}
   
        midlet.showgame();
        gam.strnew();

    }

    public String getString(InputStream is) {
        try {
            sb.delete(0, sb.length());
            chars = is.read();
            while (chars != -1) {
                if (chars != '\r') {
                    if (chars == '\n') {
                        return sb.toString();
                    } else {
                        sb.append((char) chars);
                    }
                }
                chars = is.read();
            }
        } catch (IOException ex) {}

        return "";
    }
}
