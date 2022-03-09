// athor: athanasiou lydia p3170003

import acm.program.*;
import acm.graphics.*;

public class House extends GraphicsProgram {
	public void run() {

		private static final int y = 200;
		private static final int width = 80;
		private static final int height = 550;

		for (int i=0;i<1;i++) {
			GRect rect1 = new GRect(x(i)+width,y+140,520-(x(i)+width),height-140);
			rect1.setFilled(false);  
			add(rect1);

			GLine tr1a = new GLine(x(i)+width,y+140,260+(x(i)+width)/2,height/5+10);
			add(tr1a);	
			  
			GLine tr1b = new GLine(520,y+140,260+(x(i)+width)/2,height/5+10);
			add(tr1b);				   

			GRect door = new GRect(3*width+10,y+height-140,width+30,140);
			door.setFilled(false);	   
			add(door);

			GLine updoora = new GLine(3*width+10,y+height-140,3.5*width+25,550);
			add(updoora);			   

			GLine updoorb = new GLine(3*width+120,y+height-140,3.5*width+25,550);
			add(updoorb);			   
		}

		for (int i=0;i<2;i++) {
			GRect rects2 = new GRect(x(i),y,width,height);
			rects2.setFilled(false); 
			add(rects2);

			GLine tr2a = new GLine(x(i),y,x(i) + width/2,y/4);
			add(tr2a);                

			GLine tr2b = new GLine(x(i)+width,y,x(i)+width/2,y/4);
			add(tr2b);                

			GOval win = new GOval((2*width+20)*(i+1),2*y+30,3*width/4,3*width/4);
			win.setFilled(false);     
			add(win);
		}

		for (int i=0;i<3;i++) {
			GRect rects3 = new GRect((1+i)*2*width+540,2*y+100,5*width/8,height-y-100);
			rects3.setFilled(false);  
			add(rects3);

			GLine tr3a = new GLine((1+i)*2*width+540,2*y+100,(1+i)*2*width+540+5*width/16,y+140);
			add(tr3a);				  

			GLine tr3b = new GLine((1+i)*2*width+590,2*y+100,(1+i)*2*width+590-5*width/16,y+140);
			add(tr3b);				  

		}
	}

	public double x(int i) {
		return 500*i + 20;				
	}
	
}