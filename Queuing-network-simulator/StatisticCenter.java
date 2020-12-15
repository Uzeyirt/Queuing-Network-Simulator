package bitirme_gui_calisma;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class StatisticCenter 
{
	StatisticCenter (double L,double M,int C,int Size)
	{
		this.Lambda = L ;
		
		this.Mu = M ;
		this.C = C;
		this.Size=Size;
		
	}
	public static double mmck_mql_total;
	public static int C;
	public static double Lambda;
	public static double Mu;
	public static int Size ;
	public static int dropcount;
	public static int queue_length;
	int fact(int n)
	{
		if(n==0)
		return 1;
		else
		return n*fact(n-1);
	}
	public static void update_k(int len) {
		queue_length=len;
	}
	public static void update_size(int drop_count) {
		Size=Size-drop_count;
		dropcount =drop_count;
	}

	double P01(double lambda,double mu,int c)
	{
		double temp=0.0;
		double lambdabolumu = (lambda/mu);
		for(int n=0;n<c;n++)
		{
			temp = temp + (1.0/fact(n))* Math.pow(lambdabolumu,(double)n);
		}
		
		return temp;
	}

	double P02(double lambda,double mu,int c)
	{
		double temp = (  (1.0/fact(c)) * Math.pow((lambda/mu),c) * ((c*mu)/(c*mu-lambda)) );
		return temp;	
	}

	double P0(double lambda,double mu,int c)
	{	
	    double temp = 1.0/(P01(lambda,mu,c)+P02(lambda,mu,c));
		return temp;
	}

	double Lq(double lambda,double mu,int c)
	{
	   return	((lambda*mu*Math.pow((lambda/mu),c))/(fact(c-1)*Math.pow((c*mu-lambda),2)))*P0(lambda,mu,c);
	}

	double L(double lambda,double mu,int c)
	{
		return Lq(lambda,mu,c)+lambda/mu;
	}

	double Wq(double lambda,double mu,int c)
	{
		return Lq(lambda,mu,c)/lambda;
	}


	double W(double lambda,double mu,int c)
	{
		return Wq(lambda,mu,c)+1/mu;
	}
	
	public double Find_Average_of_array(ArrayList<Double> Arrayy) 
	{
		double sum = 0;
		int counter = 0;
		for (counter = 0 ; counter < Size ; counter ++ ) 
		{
			sum = sum + Arrayy.get(counter) ;
		}
		return sum / Size ;
	}
	public double Find_Sum_of_array(ArrayList<Double> Arrayy) 
	{
		double sum = 0;
		int counter = 0;
		for (counter = 0 ; counter < Size ; counter ++ ) 
		{
			sum = sum + Arrayy.get(counter) ;
		}
		return sum  ;
	}
	public String Display_Theoretical_Results() 
	{
		String Results="\n"+"-------- THEORETICAL RESULTS ---------  "+"\n"+"Arrival Time(Theoretical) : " + (1.000000/this.Lambda)+"\n"+"Service Time(Theoretical) : " + (1.00000/this.Mu )+"\n"+"MEAN WAITING TIME (Theoretical) : " + 1.00000*(this.Lambda/(this.Mu*(this.Mu - this.Lambda)))+"\n"+"Mean time spent in the system(Theoretical) : " + 1.00000*(1/(this.Mu - this.Lambda) )+"\n"+"Mean Queue Lenght in system(Theoretical) : "+ 1.00000*(this.Lambda/this.Mu)/(1-(this.Lambda/this.Mu)) +"\n"+"--------------------------------------  ";
		System.out.println("-------- THEORETICAL RESULTS ---------  ");
		System.out.println("Arrival Time(Theoretical) : " + (1.000000/this.Lambda));
		System.out.println("Service Time(Theoretical) : " + (1.00000/this.Mu ));
		System.out.println("MEAN WAITING TIME (Theoretical) : " + 1.00000*(this.Lambda/(this.Mu*(this.Mu - this.Lambda))) );
		System.out.println("Mean time spent in the system(Theoretical) : " + 1.00000*(1/(this.Mu - this.Lambda) ));
		System.out.println("Mean Queue Lenght in system(Theoretical) : "+ 1.00000*(this.Lambda/this.Mu)/(1-(this.Lambda/this.Mu)) ) ;
		System.out.println("--------------------------------------  ");
		
		return Results;
	}
	public String Display_Practical_Results(ArrayList<Double> ATG,ArrayList<Packet_Attributes> PTG) 
	{
		String Results;
		
		Results="-------- PRACTICAL RESULTS ---------  "+"\n"+"Mean Arrival Time : "+  this.Find_Average_of_array(ATG) +"\n"+"Mean Service Time : "+ this.Find_Average_of_array_Service(PTG) +"\n"+"MEAN WAITING TIME : "+this.Find_Average_of_array_Waiting(PTG) +"\n"+"Mean time spent in the system : "+(this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG))+"\n"+"Mean Queue Lenght in system : "+(this.Find_Sum_of_array_Total_Time(PTG)/PTG.get(Size-1).service_end)+"\n"+"--------------------------------------  "+"\n";
		System.out.println("-------- PRACTICAL RESULTS ---------  ");
		System.out.println("Mean Arrival Time : " + this.Find_Average_of_array(ATG) );
		System.out.println("Mean Service Time : " + this.Find_Average_of_array_Service(PTG));
		System.out.println("MEAN WAITING TIME : " + this.Find_Average_of_array_Waiting(PTG) );
		System.out.println("Mean time spent in the system : " + (this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG)) );
		System.out.println("Mean Queue Lenght in system : " + (this.Find_Sum_of_array_Total_Time(PTG)/PTG.get(Size-1).service_end) );
		System.out.println("--------------------------------------  ");
		System.out.println("Sapma Orani MQL : %"+ 100*((W(Lambda,Mu,C)*Lambda)-(this.Find_Sum_of_array_Total_Time(PTG)/PTG.get(Size-1).service_end))/(W(Lambda,Mu,C)*Lambda));
		return Results;
	}
	public String Display_Theoretical_Results_mmc() 
	{
		String Results="\n"+"-------- THEORETICAL RESULTS ---------  "+"\n"+"Arrival Time(Theoretical) : " + (1.000000/this.Lambda)+"\n"+"Service Time(Theoretical) : " + (1.00000/this.Mu )+"\n"+"MEAN WAITING TIME (Theoretical) : " + 1.00000*(this.Lambda/(this.Mu*(this.Mu - this.Lambda)))+"\n"+"Mean time spent in the system(Theoretical) : " + 1.00000*(1/(this.Mu - this.Lambda) )+"\n"+"Mean Queue Lenght in system(Theoretical) : "+ 1.00000*(this.Lambda/this.Mu)/(1-(this.Lambda/this.Mu)) +"\n"+"--------------------------------------  ";
		System.out.println("-------- THEORETICAL RESULTS ---------  ");
		System.out.println("Arrival Time(Theoretical) : " + (1.000000/this.Lambda));
		System.out.println("Service Time(Theoretical) : " + (1.00000/this.Mu ));
		System.out.println("MEAN WAITING TIME (Theoretical) : " + Wq(Lambda,Mu,C) );
		System.out.println("Mean time spent in the system(Theoretical) : " + W(Lambda,Mu,C));
		System.out.println("Mean Queue Lenght in system(Theoretical) : "+ W(Lambda,Mu,C)*Lambda ) ;
		System.out.println("--------------------------------------  ");
		
		return Results;
	}
public static double util() {
		
		
		return util1()*(1-Math.pow(util1(), queue_length))/(1-Math.pow(util1(), queue_length+1));
	}
public static double util1() {
		
		return 1.0000*(Lambda/Mu);
	}
	public static double P0() {
		
		return (1-util())/(1-Math.pow(util(),(queue_length+1)));
	}
	public static double MQL() {
		double p = util();
		double k = queue_length;
		
		return (p/(1-p)-((k+1)*(Math.pow(p, k+1)))/(1-Math.pow(p, k+1)));
	}
	public static double throughput() {
		double p = util();
		double k = queue_length;
		return Lambda*(1-((1-p)*(Math.pow(p, k)))/(1-Math.pow(p, k+1)));
	}
	public static double Lq() {
		
		
		return MQL()-(1-P0());
	}
	public static double util_limited() {
		double p =util();
		double k= queue_length;
		
		return p*(1-Math.pow(p, k))/(1-Math.pow(p, k+1));
	}
	public String Display_Practical_Results_mmc(ArrayList<Double> ATG,ArrayList<Packet_Attributes> PTG) 
	{
		String Results;
		
		Results="-------- PRACTICAL RESULTS ---------  "+"\n"+"Mean Arrival Time : "+  this.Find_Average_of_array(ATG) +"\n"+"Mean Service Time : "+ this.Find_Average_of_array_Service(PTG) +"\n"+"MEAN WAITING TIME : "+this.Find_Average_of_array_Waiting(PTG) +"\n"+"Mean time spent in the system : "+(this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG))+"\n"+"Mean Queue Lenght in system : "+(this.Find_Sum_of_array_Total_Time(PTG)/PTG.get(Size-1).service_end)+"\n"+"--------------------------------------  "+"\n";
		System.out.println("-------- PRACTICAL RESULTS ---------  ");
		System.out.println("Mean Arrival Time : " + this.Find_Average_of_array(ATG) );
		System.out.println("Mean Service Time : " + this.Find_Average_of_array_Service(PTG));
		System.out.println("MEAN WAITING TIME : " + this.Find_Average_of_array_Waiting(PTG) );
		System.out.println("Mean time spent in the system : " + (this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG)) );
		System.out.println("Mean Queue Lenght in system : " + (mmck_mql_total/C));
		System.out.println("--------------------------------------  ");
		System.out.println("Sapma Orani Mean time spent in the system :  %" + 100*(W(Lambda,Mu,C)-(this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG)))/(W(Lambda,Mu,C)));
		System.out.println("Sapma Orani Mean time spent in the system : %" +  100*((W(Lambda,Mu,C)*Lambda)-(this.Find_Sum_of_array_Total_Time(PTG)/find_serviceend_max(PTG)))/(W(Lambda,Mu,C)*Lambda) );
		return Results;
	}
	public String Display_Practical_Results_mmc_drop(ArrayList<Double> ATG,ArrayList<Packet_Attributes> PTG,int new_size) 
	{
		Size = new_size;
		String Results;
		mmck_mql_total = mmck_mql_total + this.Find_Sum_of_array_Total_Time(PTG)/find_serviceend_max(PTG);
		Results="-------- PRACTICAL RESULTS ---------  "+"\n"+"Mean Arrival Time : "+  this.Find_Average_of_array(ATG) +"\n"+"Mean Service Time : "+ this.Find_Average_of_array_Service(PTG) +"\n"+"MEAN WAITING TIME : "+this.Find_Average_of_array_Waiting(PTG) +"\n"+"Mean time spent in the system : "+(this.Find_Average_of_array_Service(PTG) + this.Find_Average_of_array_Waiting(PTG))+"\n"+"Mean Queue Lenght in system : "+(this.Find_Sum_of_array_Total_Time(PTG)/PTG.get(Size-1).service_end)+"\n"+"--------------------------------------  "+"\n";
		
		return Results;
	}
	public void display_Everything_MMC(ArrayList<Packet_Attributes> PTG) {
		int i;
		for(i=0;i<PTG.size();i++) {
			System.out.println(PTG.get(i).service_time_generated+"\t"+PTG.get(i).arrival_time+"\t"+PTG.get(i).service_start+"\t"+PTG.get(i).service_end+"\t"+PTG.get(i).waiting_time+"\t"+PTG.get(i).Idle_time+"\t"+PTG.get(i).Total_time_in_system);
		}
	}
	public void display_Everything_MM1(ArrayList<Double>ATG,ArrayList<Packet_Attributes> PTG) {
		int i;
		for(i=0;i<PTG.size();i++) {
			System.out.println(ATG.get(i)+"\t"+PTG.get(i).service_time_generated+"\t"+PTG.get(i).arrival_time+"\t"+PTG.get(i).service_start+"\t"+PTG.get(i).service_end+"\t"+PTG.get(i).waiting_time+"\t"+PTG.get(i).Idle_time+"\t"+PTG.get(i).Total_time_in_system+"  \t   "+PTG.get(i).number_of_packets_in_queue);
		}
	}
	public double find_serviceend_max(ArrayList<Packet_Attributes>PTG) {
		float max;
		max=(float) PTG.get(0).service_end;
		int i;
		for(i=0;i<PTG.size();i++) {
			if(max<PTG.get(i).service_end) {
				max=(float) PTG.get(i).service_end;
			}
		}
		
		
		return max;
	}
	public double Find_Average_of_array_Service(ArrayList<Packet_Attributes> A) {
		int i;
		double sum =0;
		for(i=0;i<Size;i++) {
			sum = sum + A.get(i).service_time_generated;
		}
		
		return sum/Size;
	}
	
public double Find_Average_of_array_Waiting(ArrayList<Packet_Attributes> A) {
	int i;
	double sum =0;
	for(i=0;i<Size;i++) {
		sum = sum + A.get(i).waiting_time;
	}
		
		return sum/Size;
	}
public double Find_Sum_of_array_Total_Time(ArrayList<Packet_Attributes> A) {
	int i;
	double sum =0;
	for(i=0;i<Size;i++) {
		sum = sum + A.get(i).Total_time_in_system;
	}
	
	return sum;
}
public double Find_Sum_of_array_waiting_total_time(ArrayList<Packet_Attributes> A) {
	int i;
	double sum =0;
	for(i=0;i<Size;i++) {
		sum = sum + A.get(i).waiting_time;
	}
	
	return sum;
}
	public double Find_Average_Queue_Lenght(ArrayList<Double> AT,ArrayList<Double> SE) 
	{
		int counter1=0;
		int counter2=0;
		int total_queue_lenght = 0;
		for (counter1=0; counter1<Size; counter1++)
		{
			
			for (counter2=counter1+1; counter2<Size; counter2++)
			{
				if (SE.get(counter1) > AT.get(counter2)) 
				{
					total_queue_lenght++;
				}
				else 
					break;
			}
			
			
		}
		return (total_queue_lenght/(Size));
		
	}
	public void Display_Theoretical_Results_mm1k() {
		
		
		System.out.println("The Mean Queue Lenght : " + MQL());
		System.out.println("The Throughput : " + throughput());
		System.out.println("Average Waiting Time : " + Lq()/throughput());
		System.out.println("Utilization : " + util_limited());
		System.out.println("Response Time: " +MQL()/throughput() );
	}
}
