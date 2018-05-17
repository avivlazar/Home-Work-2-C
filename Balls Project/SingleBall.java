
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SingleBall //extends Elasic_Collision
{
	private static Vector vspStart = new Vector(2, 2);
	//private static Vector vspStart = new Vector(-45, Formulas.Transfer_Speed_To_Vsp(1), "angle");
	private static PointDouble startPoint = new PointDouble(0, 0);  //start

	// Arrays of colors and radius
	public static final int DELAY = 10;  //Delay
	public static final Color OUT_COLOR = Color.BLACK;
	private int radius; // Ball radius
	private Color color; //Ball color
	private double mass; //mass of ball
	private PointDouble centerPoint = new PointDouble(startPoint);
//	private PointDouble centerPoint = new PointDouble(0, 0);  //start
	private Vector Vsp = new Vector(SingleBall.vspStart);  //Delta X, Y
	
	/**Constructor**/
	public SingleBall(int r, Color c, double m){
		this.radius = r;
		this.color = c;
		this.mass = m;
	}
	
	
	/**Get methods**/
	public double getX(){return centerPoint.getX();}
	public double getY(){return centerPoint.getY();}
	public double getDx(){return  Vsp.getDX();}
	public double getDy(){return  Vsp.getDy();}
	public Vector getVspVector(){return  Vsp;}
	public double getVspSize(){return Vsp.getLength();}
	//public double getVspSize(){return (Math.sqrt( Math.pow(getDx(), 2) + Math.pow(getDy(), 2) ) * BallPanel.TURNING_TO_METER_PER_SECEND_ADDITIONAL);}
	public double getEnergy(){return Formulas.Kinemathics_Energy_Of_SingleBall(this);}
	public PointDouble getCenter(){return centerPoint;}
	public int getRadius(){return radius;}
	public double getMass(){return mass;}
	public static int getDelay(){return DELAY;}
	public Color getColor(){return color;}
	public static PointDouble getStartPoint() {return startPoint;}
	public static Vector getVspStart() {return SingleBall.vspStart;}
	
	/**Set methods**/
	public void setX(double x){centerPoint.setX(x);}
	public void setY(double y){centerPoint.setY(y);}
	public void setDx(double dx){this.getVspVector().setDx(dx);}
	public void setDy(double dy){this.getVspVector().setDy(dy);}
	public void setRadius(int r){this.radius = r;}
	public static void setStartPoint(PointDouble location) {
		SingleBall.startPoint = new PointDouble(location);
	}
	
	public void drawSingleBall(Graphics g) {
		g.setColor(OUT_COLOR);
		g.drawOval((int)getCenter().getX()- getRadius(), (int)getCenter().getY() - getRadius(),
				    getRadius() * 2, getRadius() * 2);
		g.setColor(getColor());
		g.fillOval((int)getCenter().getX()- getRadius(), (int)getCenter().getY() - getRadius(),
			    getRadius() * 2, getRadius() * 2);
	}


	
	
}
	
	
	
	