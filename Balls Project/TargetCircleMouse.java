import java.awt.Color;
import java.awt.Graphics;

public class TargetCircleMouse {

	public static final int MULPTIPLE_NUM = 50;
	public static final Color TARGET_COLOR = Color.BLACK;
	public static final int RADIUS = 25;
	public static final int LENGTH = 30;
	public static final int WIDTH = 1;
	private PointDouble pd;
	private Vector v_0 = new Vector(0, LENGTH, "angle");
	private Vector v_30 = new Vector(30, LENGTH, "angle");
	private Vector v_60 = new Vector(60, LENGTH, "angle");
	private Vector v_90 = new Vector(90, LENGTH, "angle");
	private Vector v_120 = new Vector(120, LENGTH, "angle");
	private Vector v_150 = new Vector(150, LENGTH, "angle");
	private Vector v_180 = new Vector(180, LENGTH, "angle");
	private Vector v_210 = new Vector(210, LENGTH, "angle");
	private Vector v_240 = new Vector(240, LENGTH, "angle");
	private Vector v_270 = new Vector(270, LENGTH, "angle");
	private Vector v_300 = new Vector(300, LENGTH, "angle");
	private Vector v_330 = new Vector(330, LENGTH, "angle");
	public TargetCircleMouse(PointDouble center) {
		pd = new PointDouble(center);
	}
	public PointDouble getLocation() {
		return pd;
	}
	public void setLocation(PointDouble center) {
		pd = new PointDouble(center);
	}

	public void drawTargetCircleMouse(Graphics g) {
		g.setColor(TARGET_COLOR);
		g.drawOval((int)pd.getX() - RADIUS, (int)pd.getY() - RADIUS,
				   RADIUS * 2, RADIUS * 2);
		v_0.drawVector(g, getLocation(), MULPTIPLE_NUM);
		v_30.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_60.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_90.drawVector(g, getLocation(), MULPTIPLE_NUM);
		v_120.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_150.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_180.drawVectorWithoutArrow(g, getLocation(), Vector.WIDTH);
		v_210.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_240.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_270.drawVectorWithoutArrow(g, getLocation(), Vector.WIDTH);
		v_300.drawVectorWithoutArrow(g, getLocation(), WIDTH);
		v_330.drawVectorWithoutArrow(g, getLocation(), WIDTH);
	}
}
