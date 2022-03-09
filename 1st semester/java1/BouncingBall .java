//athor: athanasiou lydia p3170003

import acm.program.*;
import acm.graphics.*;

public class BouncingBall extends GraphicsProgram {
	public void run() {

		private static final double cirlce_size = 50;
		private static final int pause_time = 10;

		double dx = 1;
		double dy = 1;

		double x = getWidth()/2 - cirlce_size;
		double y = getHeight()/2 - cirlce_size;

		GOval circle = new GOval(x,y,cirlce_size,cirlce_size);
		circle.setFilled(true);
		add(circle);

		while (true) {
			x = x + dx;
			y = y + dy;

			if (x < 0 || x > getWidth() - cirlce_size) {
				dx = -dx;
			}

			if (y < 0 || y > getHeight() - cirlce_size) {
				dy = -dy;
			}

			circle.move(dx,dy);
			pause(pause_time);
		}
	}
	
}

