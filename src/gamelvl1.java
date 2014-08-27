import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.Timer;


public class gamelvl1 extends GameCanvas implements Runnable,CommandListener {
    private yomon midlet;
    private Command cmd=new Command("Back to Menu",Command.EXIT,1);
    private Command setting=new Command("Change Settings",Command.ITEM,1);
    Command save=new Command("Save",Command.ITEM,1);
    Command backs=new Command("Back",Command.EXIT,1);
    TextField cdelay,ck,caccv,caccx,cgravity,csteps;
    private LayerManager lyr;
    public Image images;
    public int[] state,countdown;
    private Sprite pad,mover;
    public Sprite[] obst,bana;
    private boolean isplay,loading,scoring,ending,isdone,suspended;
    private double velx,vely,accx,accv,gravity,curx,cury,amntv;
    private Timer timer;
    private int bananas,i,amnt,k,banleft,monleft,obsts,tempx,delay,height,width,steps;
    private String Status,mem;
    private refrsh refobj;
    public int fuel;

    
    public gamelvl1(yomon midlet){
        super(true);
        this.setFullScreenMode(false);
        this.midlet=midlet;
        addCommand(cmd);
        addCommand(setting);

        this.setCommandListener(this);
        isplay=false;
        loading=false;
        scoring=false;
        ending=false;
        setsettings();
        loadlvl(1);
    }
    public void strnew() {//every level
        loading=true;
        isplay=true;
        scoring=true;
        ending=true;
        tempx=height/2;
        Status="Playing";
        Thread t=new Thread(this);
        t.start();
        timer=new Timer();
        timer.schedule(new refrsh(this), 0, 300);
    }
    public void loadlvl(int level) {//one time setting
        try{
            Image image1=Image.createImage("/sprite.PNG");
            mover=new Sprite(image1,80,43);
            mover.defineReferencePixel(mover.getWidth()/2, mover.getHeight()/2);
            mover.defineCollisionRectangle(9, 0, 60, 42);
            Image image2=Image.createImage("/pad.PNG");
            pad=new Sprite(image2);
            pad.defineReferencePixel(pad.getWidth()/2,pad.getHeight()/2);
        }
        catch(Exception ex){System.out.println(ex);}
    }

    public void finalset() throws Exception{//every level
            lyr=new LayerManager();
            lyr.append(pad);
            lyr.append(mover);
            for( i=0;i<bananas;i++) {
                lyr.append(bana[i]);
             }
            for (i=0;i<obsts;i++) {
             lyr.append(obst[i]);
            }
    }
    public void setsettings() { //one time setting
        delay=50;
        steps=3;
        height=getHeight();
        width=getWidth();
        accv=0.5;
        gravity=accv/3;
        accx=accv/2;
        amnt=10;
        amntv=10;
        k=2;
    }
    public void initialize(int bananas,int obsts) { //required at start of a level
        velx=0;
        vely=-3;
        this.bananas=bananas;
        this.obsts=obsts;
        bana=new Sprite[this.bananas];
        state=new int[this.bananas];
        countdown=new int[this.bananas];
        obst=new Sprite[this.obsts];
        banleft=bananas;
    }
    public void restart() {// req at start of new game
        monleft=3;
 }
    public void commandAction(Command c,Displayable d) {
        if (c==cmd) {
            isplay=false;
            loading=false;
            scoring=false;
            ending=false;
            timer.cancel();
            midlet.showmenu();
        }else if (c==setting) {
            suspended=true;
            Form form=new Form("Setting");
            cdelay=new TextField("Delay",Integer.toString(delay),50,TextField.ANY);
            ck=new TextField("k",Integer.toString(k),50,TextField.ANY);
            caccv=new TextField("accv",Double.toString(accv),50,TextField.ANY);
            caccx=new TextField("accx",Double.toString(accx),50,TextField.ANY);
            cgravity=new TextField("Gravity",Double.toString(gravity),50,TextField.ANY);
           csteps=new TextField("opening ending",Integer.toString(steps),50,TextField.ANY);
            form.append(cdelay);
            form.append(ck);
            form.append(caccv);
           form.append(caccx);
            form.append(cgravity);
           form.append(csteps);

            form.addCommand(save);
            form.addCommand(backs);

            form.setCommandListener(this);
            midlet.display.setCurrent(form);

        }else if (c==save) {
            midlet.display.setCurrent(this);
            retryit();
        }
        else if (c==backs) {
            suspended=false;
            midlet.display.setCurrent(this);
            Thread t=new Thread(this);
            t.start();
        }


    }
    public void retryit() {
        delay=Integer.parseInt(cdelay.getString());
            k=Integer.parseInt(ck.getString());
            accv=Double.parseDouble(caccv.getString());
            accx=Double.parseDouble(caccx.getString());
            gravity=Double.parseDouble(cgravity.getString());
            steps=Integer.parseInt(csteps.getString());
            suspended=false;
            Thread t=new Thread(this);
            t.start();
    }
    public void posobj(int mvrx,int mvry,int padx,int pady) {
        curx=mvrx;
        cury=mvry;
        mover.setRefPixelPosition((int)curx,(int)cury);
        pad.setRefPixelPosition(padx,pady);
        pad.setVisible(false);
    }
    public void refresh() {
        if (!suspended){
        if (isplay) {
           for (i=0;i<=bananas-1;i++) {
               if (state[i]==0) {
                    if( bana[i].getFrame()==2) {
                        bana[i].setFrame(0);
                    } else {
                        bana[i].setFrame(bana[i].getFrame()+1);
                    }
               }else if (state[i]==1){
                   if (countdown[i]==0){
                        state[i]=2;
                        bana[i].setVisible(false);
                    }
                   bana[i].setFrame(3);
                   countdown[i]-=1;
               }
           }
        }
        }
     }
    public void run() {
        
        Graphics g=getGraphics();
        while(loading && !suspended) {
            if (tempx-3>0)
                    tempx-=steps;
            else
                loading=false;
             DrawScreen(g,1);
             try { Thread.sleep(delay);}
             catch(InterruptedException ie){}
        }
        while(isplay && !suspended) {
             input();
             ChkGamStatus();
             DrawScreen(g,0);
             try { Thread.sleep(delay);}
             catch(InterruptedException ie){}
        }

        while(ending && !suspended) {
            if (tempx+3<width/2)
                    tempx+=steps;
            else
                ending=false;
             DrawScreen(g,3);
             try { Thread.sleep(delay);}
             catch(InterruptedException ie){}
        }

        if (Status.equals("You Won"))
        {       midlet.lvls++;
        midlet.loadlevel();}
        else if (Status.equals("lifelost"))
            midlet.loadlevel();
        else if(Status.equals("Lost"))
            midlet.lost();
   
    }
        private void ChkGamStatus() {
            for (i=0;i<bananas;i++) {
                if (mover.collidesWith(bana[i], false))
                    state[i]=1;
            }
            isdone=false;
           if (mover.collidesWith(pad, true)) {
               if ((mover.getRefPixelX()<=pad.getRefPixelX()+amnt) & (mover.getRefPixelX()>=pad.getRefPixelX()-amnt)) {
                    if (mover.getRefPixelY()<=pad.getRefPixelY()) {
                        if (java.lang.Math.abs(velx)<=amntv) {
                            if (java.lang.Math.abs(vely)<=amntv) {
                                Status="You Won";
                                isplay=false;
                                isdone=true;
                                timer.cancel();
                           }
                        }
                    }
               }
               if (isdone==false)
                    lose();
         }
           if(mover.getRefPixelX()<0 || mover.getRefPixelX()>images.getWidth()) lose();
            if(mover.getRefPixelY()<0 || mover.getRefPixelY()>images.getHeight()) lose();

           banleft=0;
           for(i=0;i<=bananas-1;i++) {
               if (state[i]==0){
                   banleft+=1;
               }
           }
           if (banleft==0){
               pad.setVisible(true);
           }
           for(i=0;i<obsts;i++)
               if (mover.collidesWith(obst[i],false))
               if (mover.collidesWith(obst[i],true)) 
               lose();
        }

        private void lose() {
            if (monleft==1) {
                       isplay=false;
                       monleft=0;
                       Status="Lost";
                    }else{
                        isplay=false;
                        Status="lifelost";
                        monleft-=1;
                    }
                    timer.cancel();
        }
    private void input() {

        int keyStates = getKeyStates();
        mover.setFrame(0);
        vely+=gravity;
    if (fuel>0) {
        if ((keyStates & LEFT_PRESSED) != 0) {
                velx-=accx;
                mover.setFrame(1);
                fuel-=1;
        }
        if ((keyStates & RIGHT_PRESSED) !=0 ) {
                velx+=accx;
                mover.setFrame(3);
                fuel-=1;
        }
        if ((keyStates & UP_PRESSED) != 0) {
            mover.setFrame(2);
            vely-=accv;
            fuel-=1;
            if ((keyStates & LEFT_PRESSED)!=0) {
                mover.setFrame(4);
            }
            if ((keyStates & RIGHT_PRESSED)!=0) {
                mover.setFrame(5);
            }
        }
    }
        curx+=velx/k;
        cury+=vely/k;
    }
    public void DrawScreen(Graphics g,int drawtype)  {
        g.setGrayScale(0);

        g.drawImage(images, width/2, height/2, Graphics.HCENTER | Graphics.VCENTER);
     mover.setRefPixelPosition((int)curx, (int)cury);

        lyr.paint(g, width/2-images.getWidth()/2, height/2-images.getHeight()/2);
        int cornerx=width/2+images.getWidth()/2;
        int cornery=height/2-images.getHeight()/2;
        g.setGrayScale(0);
        g.drawString("Bananas Left : "+java.lang.Integer.toString(banleft), cornerx-50, cornery+10, Graphics.TOP|Graphics.RIGHT);
        g.drawString("Monkey Left : "+java.lang.Integer.toString(monleft), cornerx-50, cornery+10+ g.getFont().getHeight() , Graphics.TOP|Graphics.RIGHT);
        g.drawString("Fuel : "+java.lang.Integer.toString(fuel), cornerx-50, cornery+10+ 2*g.getFont().getHeight() , Graphics.TOP|Graphics.RIGHT);
        g.drawString(java.lang.Long.toString(Runtime.getRuntime().freeMemory(),10), 300, 180, Graphics.RIGHT|Graphics.BOTTOM);
        //g.drawString(Status, width/2, 20, Graphics.HCENTER|Graphics.TOP);
        g.setGrayScale(255);
        if (drawtype==1) {
            g.fillRect(0,0,width,tempx);
        g.fillRect(0,height-tempx,width,height);
        }else if(drawtype==3) {
            g.fillRect(0,0,tempx,height);
            g.fillRect(width-tempx,0,width,height);
        }
        flushGraphics();
    }
}
