
public class Formulas {
	
	public static final double HALF = 0.5;
	public static final int NUM_OF_PIXEL_PER_METER = 500;  // = T
	public static final int NUM_OF_PIXELS_PER_METER = 11800;  //number of pixels in one meter
	public static final int NUM_OF_MS_IN_SEC = 1000;  //number of ms in one second
	public static final double GRAVITATION = 9.8; //Gravitation in meter per sec^2  // = G
	
	public static final double TURNING_Vsp_TO_SPEED_ADDITIONAL = 
			(double)NUM_OF_MS_IN_SEC / (double)NUM_OF_PIXELS_PER_METER;
	public static final double TURNING_Vac_TO_ACCELERATION_ADDITIONAL = 
			Math.pow(NUM_OF_MS_IN_SEC, 2) / (double)NUM_OF_PIXEL_PER_METER;
			
	//relation between true and 'fake' meter
	public static final double RELATION_BETWEEN_FAKE_PER_TRUE_METER =  
			(double)NUM_OF_PIXELS_PER_METER / (double)NUM_OF_PIXEL_PER_METER;  // = T/11,800
	public static final double MISTAKE = RELATION_BETWEEN_FAKE_PER_TRUE_METER;
	

	//Formulas
		public static double Transfer_Vsp_To_Speed (SingleBall currentBall){  //of one ball
			// The real Size of V in pixel/ms
			double Vsp_Real_Size = currentBall.getVspVector().getLength()  /  SingleBall.getDelay();
			// The fake Size of V in pixel/ms  (is fixed by RELATION_BETWEEN_TRUE_PER_FAKE_METER)
			double Vsp_Fake_Size = Vsp_Real_Size * RELATION_BETWEEN_FAKE_PER_TRUE_METER;
			return Vsp_Fake_Size* TURNING_Vsp_TO_SPEED_ADDITIONAL;  //become from pixel/ms to meter/sec
		}
		
		/**Transfer speed in pixel/ms to m/s (in our System)**/
		public static double Transfer_Vsp_To_Speed (double V_Size){  //of one ball
			// The real Size of V in pixel/ms
			double Vsp_Real_Size = V_Size  /  SingleBall.getDelay();
			// The fake Size of V in pixel/ms  (is fixed by RELATION_BETWEEN_TRUE_PER_FAKE_METER)
			double Vsp_Fake_Size = Vsp_Real_Size * RELATION_BETWEEN_FAKE_PER_TRUE_METER;
			return Vsp_Fake_Size* TURNING_Vsp_TO_SPEED_ADDITIONAL;  //become from pixel/ms to meter/sec
		}
		
		public static double Transfer_Speed_To_Vsp(double Speed) {
			double Vsp_Fake_Size = Speed / TURNING_Vsp_TO_SPEED_ADDITIONAL;
			double Vsp_Real_Size = Vsp_Fake_Size / RELATION_BETWEEN_FAKE_PER_TRUE_METER;
			return Vsp_Real_Size / SingleBall.getDelay();
		}
		
		/**Transfer acceleration in pixel/(ms^2) to m/(sec^2) (in our System)**/
		public static double Transfer_Vac_To_Acceleration (double A_Size){  //of one ball  acceleration
			double Vac_Real_Size = A_Size / Math.pow(SingleBall.getDelay(), 2);  //The real Size of A in pixel/(ms^2)
			double Vac_Fake_Size = Vac_Real_Size * RELATION_BETWEEN_FAKE_PER_TRUE_METER;
			return Vac_Fake_Size * TURNING_Vac_TO_ACCELERATION_ADDITIONAL;
		}
		
		/**Get Movement Energy: Ek = 0.5*mass*(speed)^2 **/
		public static double Kinemathics_Energy_Of_SingleBall (SingleBall currentBall){
			double Speed = Transfer_Vsp_To_Speed(currentBall);
			return HALF * currentBall.getMass() * Math.pow(Speed, 2) ;  
		}
		
		/**Cast a double number to small one**/
		public static double castDoubleToSmallOne(double dNum){
			int num;
			if(dNum % 0.1 >= 0.6)
				num = (int) (dNum*10.0) + 1;
			else
				num = (int) (dNum*10.0) ;
			dNum =  num/10.0;
			return dNum;
		}
		
		/**Cast double to integer**/
		public static int castDoubleToInt(double dNum){
			if(dNum % 1.0 > 0.5){
				return (int) dNum + 1;
			}
			return (int) dNum;
		}
		
		public static double getMistake() {return castDoubleToSmallOne(MISTAKE);}
		public static String getMistakeString() {return ":" + castDoubleToSmallOne(MISTAKE);}
}
