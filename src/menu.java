
import java.io.IOException;
import javax.microedition.lcdui.*;

public class menu extends Canvas implements Runnable{
    yomon midlet;
    Image back;
    Image title;
    int strx,stry;
    private int i;
    String[] arr={"New Game","Instructions","HighScore","About","Exit"};
    Image process;
    private int j;
    private int selectedindex;
    
    menu(yomon midlet) {
        this.midlet=midlet;
        this.setFullScreenMode(true);
        
        selectedindex=0;
        try {
            back = Image.createImage("/mnuback.png");
            title = Image.createImage("/title.png");
        } catch (IOException ex) {}
        strx=getWidth()/2;
        stry=getHeight()/2-back.getHeight()/2+title.getHeight()+40;
        
        int width=getWidth();
        int height=Font.getDefaultFont().getHeight(),color;
        int[] raw=new int[width*height];

        int alpha;
            for(i=0;i<width;i++){
                alpha=(int) (255 - 510 * (java.lang.Math.abs((float) width / 2 - (float) i) / (float) width));
                   alpha=(alpha<<24);
                
                    for(j=0;j<height;j++) {
                      if   (j==0 || j==height-1)
                          color=0x00000000+alpha;
                      else
                          color=0x00ffffff+alpha;
                            raw[i+j*width]=color;
                      }
           }
         process=Image.createRGBImage(raw, width, height, true);
         
 }


    protected void paint(Graphics g) {
        g.setGrayScale(255);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(back,getWidth()/2,getHeight()/2,Graphics.HCENTER|Graphics.VCENTER);
        g.drawImage(title,getWidth()/2,getHeight()/2-back.getHeight()/2+60,Graphics.HCENTER|Graphics.VCENTER);
        
        g.setGrayScale(0);
        

        for(i=0;i<arr.length;i++) {
            if (selectedindex==i)
                g.drawImage(process,strx, stry+i*g.getFont().getHeight(),Graphics.HCENTER|Graphics.TOP);
             g.drawString(arr[i],strx , stry+i*g.getFont().getHeight(),Graphics.HCENTER|Graphics.TOP);
             
        }
    }
    protected void keyPressed(int keyCode) {

            if (keyCode==-1) {
                if (selectedindex!=0)
                    selectedindex-=1;
            }else if (keyCode==-2) {
                if (selectedindex!=4)
                    selectedindex+=1;
            }else if(keyCode==-5) {
                dotask();
                
            }

            repaint();
    }
    public void dotask() {
        switch(selectedindex) {
            case 0:
                midlet.newgam();
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                midlet.destroyApp(true);
        }
    }
    public void run() {

    }


}
