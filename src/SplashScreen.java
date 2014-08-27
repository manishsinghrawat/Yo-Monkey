import javax.microedition.lcdui.*;
import java.util.*;

public class SplashScreen extends Canvas implements Runnable {
        Image image = null;
        int alpha;
        int[] raw;
        int bgcolor;
        boolean imgctr;
        yomon midlet;

    public SplashScreen(yomon midlet,String file){
        this.midlet=midlet;

            showimage(file);
            imgctr=true;
            
    }
    public void showimage(String img) {
            alpha=255;
            try {
                image=Image.createImage(img);
                raw=new int[image.getWidth()*image.getHeight()];
            }
            catch(Exception ie){}
            repaint();
            Timer timer=new Timer();
            timer.schedule(new dismiss(this), 500);
    }
    public void run() {
        while(alpha>=0 ) {
            for(int i=0;i<raw.length;i++) {raw[i]=0;}
            image.getRGB(raw,0, image.getWidth(),0, 0, image.getWidth(), image.getHeight());
            blend();
            repaint();
            alpha-=15;
                try{Thread.sleep(50);}
                catch(InterruptedException ie){}
             }
        midlet.showmenu();
    }
    public void blend() {
        int len=raw.length;
        int a;
        int color;
        bgcolor=raw[1];
        for(int i=0;i<len;i++) {
            color=(raw[i]& 0x00FFFFFF) ;
            if (((0xff000000 & raw[i])!=0x00000000))
            {  a=alpha;
                a=(a<<24);
                color+=a;
            }
             raw[i]=color;
       }
    }
    protected void paint(Graphics g) {
        g.setGrayScale(255);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (imgctr)
            g.drawImage(image,getWidth()/2 , getHeight()/2, Graphics.HCENTER|Graphics.VCENTER);
        else
            g.drawImage(Image.createRGBImage(raw,image.getWidth(),image.getHeight(),true),getWidth()/2, getHeight()/2,Graphics.VCENTER|Graphics.HCENTER);
    }

    void doit() {
        imgctr=false;
        Thread t=new Thread(this);
        t.start();

    }
}
