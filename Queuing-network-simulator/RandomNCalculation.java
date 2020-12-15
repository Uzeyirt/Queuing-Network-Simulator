package bitirme_gui_calisma;
import java.util.ArrayList;
import java.util.Random;

public class RandomNCalculation {
	
	RandomNCalculation() {return;}
	
	public double  exp_probability_formula(double value,double ArrivalOrServiceTime)  //probability formula for P(X <= x)
	{
		
		return 1000* (1 - ( (Math.pow((2.71828),(-((ArrivalOrServiceTime)*(value))))))) ;
	
	}
	public double Calculate_New_Lambda_Mu(double VAL) 
	{
		
		Random random_number_generator = new Random();
		double Time = 1.00000 / VAL ;
			
		int random_number = random_number_generator.nextInt(1000); //range is 0 to 999 (this represents probability)
				
			
		double counter = 0.00000 ;
			
		while (true)
		{
			double result = exp_probability_formula(counter,Time) ; 
			
		
			if (result > random_number) 
			{
				
				return counter;  
				
			}
			
			counter = counter + 0.0001 ; 
	
		}
	}
			
}
