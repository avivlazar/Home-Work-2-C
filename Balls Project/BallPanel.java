import javax.swing.Timer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BallPanel extends JPanel {
	
	public static final int TIME_PEICE = 25;
	public static final double TCM_PARAMETER = TIME_PEICE * 2.0 / (double)SingleBall.DELAY;
	private TargetCircleMouse tcm = new TargetCircleMouse(SingleBall.getStartPoint());
    private Vector tcmVector = getTcmVector();
	
    public static final int MULTIPLE_NUM = 50;
    public static final int TCM_NUM = 1;
	public static final double LIMIT = 0.99;
	private BallControl ballControl;  //ballControl 
	private double totalEnergy = 0;  //Energy
	private double avgEnergy = 0;
	private double totalSpeed = 0; //Speed
	private double avgSpeed = 0;
	private double kineticFriction = 0;   //The Friction in area
	private double percentWastedEnergy = 0;
	
	private ArrayList<SingleBall> arrayOfBalls = new ArrayList<SingleBall>();  // The arrayList Ball
	private SingleBall currentBall;  //Single ball
	private Timer THE_TIMER = new Timer(SingleBall.DELAY, new TimerListener());  //Main timer
	private boolean isSuspend = false;
	private boolean isUserClickedAddInSuspend = false;
	private boolean isUserChangeLocation = false;

	/**Constructor**/
	public BallPanel(BallControl ballControl) { 
		this.ballControl = ballControl;
		//Mouse Listener 
		this.addMouseListener(new MyMouseListener());
		this.addMouseMotionListener(new MyMouseListener());
	}
	
	private class MyMouseListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			if(isSuspend && isUserChangeLocation) {
				Vector temp = new Vector(SingleBall.getStartPoint().getX(),
						               SingleBall.getStartPoint().getY(),
						               e.getX(), e.getY());
				tcmVector = temp.multipleWith(1);
				repaint();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if(isSuspend && !isUserChangeLocation) {
				int x = e.getX();
				int y = e.getY();
				tcm.setLocation(new PointDouble(x, y));
				repaint();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(isSuspend) {
				isUserChangeLocation = true;
				SingleBall.setStartPoint(tcm.getLocation());
				repaint();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	/**Timer Listener**/
	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
	
	//Get methods
	public double getTotalEnergy() {return totalEnergy;}  //Get Total Energy Of System
	public double getAvgEnergyInSystem(){return (totalEnergy / (double)arrayOfBalls.size());}  // Get Avg Energy Of System
	public double getTotalSpeed() {return totalSpeed;}  ///Get Total Speed Of System
	public double getAvgSpeedInSystem(){return (totalSpeed / (double)arrayOfBalls.size());}
	public int getNumOdBalls() {return arrayOfBalls.size();}  //Number of balls
	public BallControl getControlPanel(){return ballControl;}
	public double getKineticFriction() {return kineticFriction;}   //Get Kinetic Friction
	public double getPercentWstedEnergy() {return percentWastedEnergy;}
	//tcm = tcm * (start vec);
	public Vector getTcmVector() {return SingleBall.getVspStart().multipleWith(TCM_PARAMETER);}
	// Get Speed of single ball in m/sec
	private double getSpeedOfSingleBall(SingleBall currentBall) {  
		return Formulas.Transfer_Vsp_To_Speed(currentBall);
	}
	private double getEnergyOfSingleBall(SingleBall currentBall) {  //Energy of single ball
		return Formulas.Kinemathics_Energy_Of_SingleBall(currentBall);
	}
	
	//Set methods
	private void setTotalSpeed(double newTotalSpeed) {
		this.totalSpeed = newTotalSpeed;
		setAvgSpeed(newTotalSpeed / (double)getNumOdBalls());
	}
	private void setAvgSpeed(double newAvgSpped){
		this.avgSpeed = newAvgSpped;
	}
	private void setTotalEnergy(double newTotalEnergy) {
		this.totalEnergy = newTotalEnergy;
		setAvgEnergy(newTotalEnergy / (double)getNumOdBalls());
	}
	private void setAvgEnergy(double newAvgEnergy){
		this.avgEnergy = newAvgEnergy;
	}
	

	/////////////////////////////////**Drawing**Drawing**Drawing**///////////////////////////////////
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		SingleBall.getStartPoint().drawPointDouble(g);
		tcmVector.drawVector(g, SingleBall.getStartPoint(), 1);
		if(!isSuspend) {
			for(int i = 0; i < arrayOfBalls.size(); i++){
				SingleBall currentBall = arrayOfBalls.get(i);
				g.setColor(currentBall.getColor());
				for(int j = i+1; j < arrayOfBalls.size(); j++ ){  //Check all the balls
					if(isThereCollision(currentBall, arrayOfBalls.get(j))){
						//There is a collision
						SingleBall currentBall2 = arrayOfBalls.get(j); //currentBall2 set
						////**Collision code**////
						Elasic_Collision collision_E = new Elasic_Collision(currentBall, currentBall2);
						double Speed_Change = collision_E.getSpeedChange();     //set total Speed
						double ts = totalSpeed + Speed_Change;
						setTotalSpeed(ts);   //Change the total and avg speed
						double Energy_Change = collision_E.getWastedEnergyInCollision();   //set total Speed
						double te = totalEnergy + Energy_Change;
						setTotalEnergy(te);
						setAllTextsInBallControl();   //Set Texts
						
					}
				}
				double x = currentBall.getX();
				double y = currentBall.getY();
				double dx = currentBall.getDx();
				double dy = currentBall.getDy();
				int radius = currentBall.getRadius();
				
				if (x < radius)
					dx = Math.abs(dx); // Check boundaries
				if (x > getWidth() - radius)
					dx = -Math.abs(dx);
				if (y < radius){
					dy = Math.abs(dy);
				}
				if (y > getHeight() - radius){
					dy = -Math.abs(dy);
				}
				x += dx; // Adjust ball position
				y += dy;
				currentBall.setX(x);
				currentBall.setY(y);
				currentBall.drawSingleBall(g);
				currentBall.setDx(dx);
				currentBall.setDy(dy);
			}
		}
		
		else{
			for(int k = 0; k < arrayOfBalls.size(); k++){
				SingleBall ball = arrayOfBalls.get(k);
				ball.drawSingleBall(g);
				
			}
			for(int m = 0; m < arrayOfBalls.size(); m++){
				SingleBall ball = arrayOfBalls.get(m);
				Vector v = arrayOfBalls.get(m).getVspVector();
				v.drawVector(g, ball.getCenter(), TCM_PARAMETER);
			}
			tcm.drawTargetCircleMouse(g);
		}
	}

	/********************************************************Collision Event**********************************************************/
	//Collision Event


	
	public void suspend() {
		if(THE_TIMER.isRunning() && arrayOfBalls.size() > 0){
			THE_TIMER.stop();
			isSuspend = true;
		}
		repaint();
	} // Suspend timer

	public void resume() {
		if(!THE_TIMER.isRunning() && arrayOfBalls.size() > 0){
			THE_TIMER.start();
			isSuspend = false;
			isUserClickedAddInSuspend = false;
			isUserChangeLocation = false;
			tcm.setLocation(SingleBall.getStartPoint());
		}
	} // Resume timer

	public void ballAdding() {
		if(!isUserClickedAddInSuspend){
			SingleBall currentBall = new SingleBall(ballControl.getRBPanel().getSelectedRadius(), 
                    ballControl.getRBPanel().getSelectedColor(), 
                    ballControl.getRBPanel().getSelectedMass());//new SingleBall();
            arrayOfBalls.add(currentBall);
            setTotalEnergy(totalEnergy +  getEnergyOfSingleBall(currentBall));
            setTotalSpeed(totalSpeed + getSpeedOfSingleBall(currentBall));
            if(!THE_TIMER.isRunning() && !isSuspend){  //Check if the user suspend
                     THE_TIMER.start();  //The timer start clocking
             }
            if(isSuspend){
            	isUserClickedAddInSuspend = true;
            }
            if(arrayOfBalls.size() > 0){
                   setAllTextsInBallControl();
            }
            repaint();
		}
	}//End of Add ball
	
   
    /**Remove single ball**/
	public void ballRemoving() {
		if(arrayOfBalls.size() > 0){
			int index = arrayOfBalls.size() - 1;
			currentBall = arrayOfBalls.get(index);
			arrayOfBalls.remove(index);
			setTotalEnergy(totalEnergy -  getEnergyOfSingleBall(currentBall));
			setTotalSpeed(totalSpeed - getSpeedOfSingleBall(currentBall));
			setAllTextsInBallControl();
		}
		repaint();
	}
	
	public void setAllTextsInBallControl() {
	    ballControl.setSpeedText(getSpeedMessage());
		ballControl.setAvgSpeedText(getAvgSpeedMessage());
		ballControl.setEnergyText(getEnergyMessage());
		ballControl.setAvgEnergyText(getAvgEnergyMessage());
		ballControl.setKmSpeedText(getKmSpeedMessage());
		ballControl.setAvgKmSpeedText(getAvgKmSpeedMessage());
	}
	
	/**The massage that will be printed (for control panel)**/
	//(Total Energy)
	public String getEnergyMessage() {
		return getNumForMessage(totalEnergy, " Jaol");
	}
	//Average Energy
	public String getAvgEnergyMessage() {
		return getNumForMessage(avgEnergy, " Jaol");
	}
	//Total Speed
	public String getSpeedMessage(){
		return getNumForMessage(totalSpeed, " m/sec");
	}
	//Average Speed
	public String getAvgSpeedMessage(){
		return getNumForMessage(avgSpeed, " m/sec");
	}
	//Total Speed in km/h
	public String getKmSpeedMessage(){
		double speedKm = totalSpeed * 3.6;
		return getNumForMessage(speedKm, " km/h");
	}
	//Avg Speed in km/h
	public String getAvgKmSpeedMessage(){
		double avgSpeedKm = totalSpeed * 3.6 / (double)(arrayOfBalls.size());
		return getNumForMessage(avgSpeedKm, " km/h");
	}
	
	/////**New method**////
	public String getNumForMessage(double num, String str){
		if(arrayOfBalls.size() == 0){
			return 0 + str;
		}
		else{
			if(arrayOfBalls.size() > 0){
				if(num >= LIMIT){
					return Formulas.castDoubleToSmallOne(num) + str;
				}
				else
					if(num >= LIMIT*Math.pow(10, -3)){
						return Formulas.castDoubleToSmallOne(num*Math.pow(10, 3)) + " mili " + str;
					}
					else
						if(num >= LIMIT*Math.pow(10, -6)){
							return Formulas.castDoubleToSmallOne(num*Math.pow(10, 6)) + " micro " + str;
						}
						else
							if(num >= LIMIT*Math.pow(10, -9)){
								return Formulas.castDoubleToSmallOne(num*Math.pow(10, 9)) + " nano " + str;
							}
							else
								if(num >= LIMIT*Math.pow(10, -12)){
									return  Formulas.castDoubleToSmallOne(num*Math.pow(10, 12)) + " pico " + str;
								}
			}
		}
		return "Inhale By Zero!";
	}
	
	
	public boolean isThereCollision(SingleBall ball1, SingleBall ball2) {
		double distance;  //Between center of cb and center of cb2
		distance = Math.sqrt( Math.pow(ball1.getX() - ball2.getX() , 2) + Math.pow(ball1.getY() - ball2.getY() , 2) );
		if(distance <= ball1.getRadius() + ball2.getRadius()){
			return true;
		}
		return false;
	}
	
	
}
