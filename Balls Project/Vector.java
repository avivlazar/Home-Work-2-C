import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

public class Vector {

	public static final String ANGLE = "Angle";
	public static final double NUMBER_OF_DEGREES_IN_ONE_ROUND = 360.0;
	public static final Color COLOR = Color.BLACK;
	public static final double LENGTH_MULTIPLY_PARAMETER = 1;
//	public static final double LENGTH_MULTIPLY_PARAMETER = BallPanel.TCM_PARAMETER;
	public static final double WIDTH = 2;
	private PointDouble direction;
	private double length;
	
	/**Full Vector constructor **/
	public Vector(double X_start, double Y_start, double X_end, double Y_end) {
		double vec_X = X_end - X_start;
		double vec_Y = Y_end - Y_start;
		this.length = Math.sqrt(vec_X * vec_X + vec_Y * vec_Y);
		vec_X /= length;
		vec_Y /= length;
		direction = new PointDouble(vec_X, vec_Y);
	}
	
	/**Constructor: dx, dy**/
	public Vector(double dx, double dy) {
		double vec_X = dx;
		double vec_Y = dy;
		length = Math.sqrt(vec_X * vec_X + vec_Y * vec_Y);
		vec_X /= length;
		vec_Y /= length;
		direction = new PointDouble(vec_X, vec_Y);
	}
	
	/**Constructor: direction, length**/
	public Vector(PointDouble direction, double length) {
		this.direction = direction;
		this.length = length;
	}
	
	/**Constructor: angle, length**/
	public Vector(double angle, double length, String angleWord) {
		if(angleWord.equalsIgnoreCase(ANGLE)) {
			System.out.println("angle:" + angle);
			double radianAngle = (angle / NUMBER_OF_DEGREES_IN_ONE_ROUND) * 2.0 * Math.PI;
			System.out.println("a:  " + radianAngle);
			this.direction = new PointDouble(Math.cos(radianAngle), Math.sin(radianAngle));
			System.out.println("cos:" + Math.cos(radianAngle));
			System.out.println("sin:" + Math.sin(radianAngle));
			System.out.println();
			this.length = length;
		}
	}
	
	public Vector(Vector v) {
		this(v.getDX(), v.getDy());
	}

	public PointDouble getDirection(){return direction;}
	public double getLength(){return length;}
	
	public void setLength(double length){this.length = length;}

	public double multiple(Vector v) {  //////// Multiple vector with Vector//////
		return ((this.getDirection().getX() * v.getDirection().getX()) + (this.getDirection().getY() * v.getDirection().getY())) * v.length * this.length;
	}
	
	public double getCos(Vector v){
		return (this.getDirection().getX() * v.getDirection().getX()) + 
				(this.getDirection().getY() * v.getDirection().getY());
	}

	
	
	public double getDX(){
		return this.getDirection().getX() * this.getLength();
	}
	
	public double getDy() {
		return this.getDirection().getY() * this.getLength();
	}
	
	//ADD Actions
	public Vector addWith(Vector v) {    //Vector + Vector
		double dx = this.getDX() + v.getDX();
		double dy = this.getDy() + v.getDy();
		return new Vector(dx, dy);
	}
	
	public PointDouble addWith(PointDouble pd){ //Vector + PointDouble
		return new PointDouble(getDX() + pd.getX(), getDy() + pd.getY());
	}
	
	//SUB Actions
	public Vector subWith(Vector v) { //Vector - Vector
		return this.addWith(new Vector(v.getDirection(), -v.getLength()));
	}
	
	public PointDouble subWith(PointDouble start) {  // PointDouble - Vector 
		Vector v = this.getNegativeVector();
		return v.addWith(start);
	}
	
	public Vector multipleWith(double num) {  //num * vector
		return new Vector(num * getDX(), num * getDy());
	}

	public Vector getNegativeVector() {
		Double dx = -getDX();
		Double dy = -getDy();
		return new Vector(dx, dy);
	}

	public void setLength(Double l){
		length = l;
	}
	
	public void setDirection(Double dirX, Double dirY) {
		direction = new PointDouble(dirX, dirY);
	}
	
	public void setDx(double dx) {
		double dy = this.getDy();
		setLength(Math.sqrt(dx*dx + dy*dy));
		Double dirX = dx / getLength();
		Double dirY = dy / getLength();
		setDirection(dirX, dirY);
	}

	public void setDy(double dy) {
		double dx = this.getDX();
		setLength(Math.sqrt(dx*dx + dy*dy));
		Double dirX = dx / getLength();
		Double dirY = dy / getLength();
		setDirection(dirX, dirY);
	}

	/****/
	@SuppressWarnings("null")
	public void drawVector(Graphics g, PointDouble start, double multipleNum){
		Polygon poly;  // the Polygon
		Double Length = getLength() * multipleNum;
	//	Double Width = Length * WIDTH_PARAMETER;
		PointDouble verticalDirection = new PointDouble(0, 0);
		if(getDirection().getX() == 0){
			 verticalDirection.setX(1);
			 verticalDirection.setY(0);
		}
		else
			if(getDirection().getY() == 0){
				verticalDirection.setX(0);
				 verticalDirection.setY(1);
			}
		else{
			double a = - (getDirection().getY());
			double b = getDirection().getX();
			verticalDirection.setX(a);
			verticalDirection.setY(b);
		}
		Vector lengthVector = new Vector(getDirection(), Length);
		Vector widthVector = new Vector(verticalDirection, WIDTH);
		Vector longVector = new Vector(getDirection(), lengthVector.getLength() + (widthVector.getLength()));
		PointDouble p1 = new Vector(verticalDirection, widthVector.getLength() / 2.0).addWith(start);
		PointDouble p2 =new Vector(verticalDirection, widthVector.getLength() / 2.0).subWith(start);
		PointDouble p3 = lengthVector.addWith(p2);
		PointDouble p4 = widthVector.subWith(p3);
		PointDouble p5 = longVector.addWith(start);
		PointDouble p7 = lengthVector.addWith(p1);
		PointDouble p6 = widthVector.addWith(p7);
		int[] arrayX = {Formulas.castDoubleToInt(p1.getX()), 
				               Formulas.castDoubleToInt(p2.getX()),
				               Formulas.castDoubleToInt(p3.getX()),
				               Formulas.castDoubleToInt(p4.getX()),
				               Formulas.castDoubleToInt(p5.getX()),
				               Formulas.castDoubleToInt(p6.getX()),
				               Formulas.castDoubleToInt(p7.getX())};
		int[] arrayY = {Formulas.castDoubleToInt(p1.getY()), 
            	               Formulas.castDoubleToInt(p2.getY()),
	                           Formulas.castDoubleToInt(p3.getY()),
	                           Formulas.castDoubleToInt(p4.getY()),
	                           Formulas.castDoubleToInt(p5.getY()),
	                           Formulas.castDoubleToInt(p6.getY()),
	                           Formulas.castDoubleToInt(p7.getY())};
		poly = new Polygon(arrayX, arrayY, arrayX.length);
		g.setColor(COLOR);
		g.drawPolygon(poly);
		g.fillPolygon(poly);
	}
	
	//***********************DRAWING VECTOR WITHOUT ARROW*************************//
		public void drawVectorWithoutArrow(Graphics g, PointDouble start, double width){
			Polygon poly;  // the Polygon
			Double Length = getLength() * LENGTH_MULTIPLY_PARAMETER;
			PointDouble verticalDirection = new PointDouble(0, 0);
			if(getDirection().getX() == 0){
				 verticalDirection.setX(1);
				 verticalDirection.setY(0);
			}
			else
				if(getDirection().getY() == 0){
					verticalDirection.setX(0);
					 verticalDirection.setY(1);
				}
			else{
				double a = - (getDirection().getY());
				double b = getDirection().getX();
				verticalDirection.setX(a);
				verticalDirection.setY(b);
			}
			Vector lengthVector = new Vector(getDirection(), Length);
			Vector widthVector = new Vector(verticalDirection, width);
		//	Vector longVector = new Vector(getDirection(), lengthVector.getLength() + (widthVector.getLength()));
			PointDouble p1 = new Vector(verticalDirection, widthVector.getLength() / 2.0).addWith(start);
			PointDouble p2 =new Vector(verticalDirection, widthVector.getLength() / 2.0).subWith(start);
			PointDouble p3 = lengthVector.addWith(p2);
			PointDouble p7 = lengthVector.addWith(p1);
			int[] arrayX = {Formulas.castDoubleToInt(p1.getX()), 
					               Formulas.castDoubleToInt(p2.getX()),
					               Formulas.castDoubleToInt(p3.getX()),
					               Formulas.castDoubleToInt(p7.getX())};
			int[] arrayY = {Formulas.castDoubleToInt(p1.getY()), 
	            	               Formulas.castDoubleToInt(p2.getY()),
		                           Formulas.castDoubleToInt(p3.getY()),
		                           Formulas.castDoubleToInt(p7.getY())};
			poly = new Polygon(arrayX, arrayY, arrayX.length);
			g.setColor(COLOR);
			g.drawPolygon(poly);
			g.fillPolygon(poly);
		}
	
}
