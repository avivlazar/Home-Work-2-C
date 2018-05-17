import java.awt.Color;
import java.awt.Graphics;

public class PointDouble{
	
	public static final Color COLOR = Color.BLACK;
	public static final int RADIUS = 2;
	private double x;
	private double y;
	
	public PointDouble(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public PointDouble(PointDouble pd) {
		this(pd.getX(), pd.getY());
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}

	public void drawPointDouble(Graphics g) {
		g.setColor(PointDouble.COLOR);
		g.drawOval((int)x - RADIUS, (int)y - RADIUS, RADIUS * 2, RADIUS * 2);
		g.fillOval((int)x - RADIUS, (int)y - RADIUS, RADIUS * 2, RADIUS * 2);
	}

}
