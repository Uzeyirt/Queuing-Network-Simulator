package bitirme_gui_calisma;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.ItemSelectable;
 



public class yes extends JFrame{
	static String s1[] = { "MM1", "MM1K", "MMC" };
	static JComboBox c1=new JComboBox(s1);;
	static int queue_length;
	static ArrayList<object> packet_generator_list=new ArrayList<object>();
	static ArrayList<object> server_list=new ArrayList<object>();
	static ArrayList<Graphics> link_list=new ArrayList<Graphics>();
	public static int x = 10;
	public static int canvas_number=0;
	private JFrame frame;
	public static int pg_number=0;
	public static int sv_number=0;
	public static int link_number=0;
	public static int x1 = 0;
	public static int y1 = 0;
	public static int x2 = 0;
	public static int y2 = 0;
	public static int n1 = 0;
	public static int n2 = 0;
	public static double util;
	public static String Practical_Results ="";
	public static String Theoretical_Results ="";
	public static String Final_Results = "";
	static ArrayList<Double> arrival_time_generated = new ArrayList<Double>();
	static ArrayList <ArrayList<Packet_Attributes>> Server_List = new ArrayList<ArrayList<Packet_Attributes>>();
	static int C;
	static int Size;
	
	
	static double Lambda;
	static double Mu;
	static ArrayList<Packet_Attributes> Packet_Generator = new ArrayList<Packet_Attributes>();

	
public static void find_sizes() {
	C = server_list.size();
	System.out.println(C);
	System.out.println(Mu);
	System.out.println(Lambda);
	System.out.println(queue_length);
	
}
	
public static void clear_everything() {
	arrival_time_generated.clear();
	Server_List.clear();
	Packet_Generator.clear();
}
public static void calculate_Size(int C) {
	
	util=Lambda/(Mu*C);
	if(util<0.85) {
		Size=100000;
	}
	else if(util<0.90) {
		Size=10000000; //10 saniye bekle
	}
	else
		Size=10000000; // 30 saniye bekle
	
	
}
public void mm1() {
	Scanner myObj = new Scanner(System.in); // this is for taking input from user
	RandomNCalculation CalculatorSample =new RandomNCalculation();
	
	
	System.out.println("enter lambda:");
	
	 
	
	System.out.println("enter Mu:");
	//System.out.print(packet_generator_list.size()+" "+server_list.size());
	
	calculate_Size(C);
	StatisticCenter StatisticC = new StatisticCenter(    Lambda,Mu,C,Size) ;
	
	int c = 0 ;
	
	for (c=0; c<Size; c++) 
	{
		arrival_time_generated.add(CalculatorSample.Calculate_New_Lambda_Mu(1/Lambda));
	}
	cal_arrival_time();
	for (c=0; c<Size; c++) 
	{
		Packet_Generator.get(c).service_time_generated =CalculatorSample.Calculate_New_Lambda_Mu(1/Mu);
	}		
	
	
	cal_service_start();
	//cal_service_end();
	cal_waiting_time();
	cal_total_time();
	cal_Idle();
	
	System.out.println("");
	Practical_Results=StatisticC.Display_Practical_Results(arrival_time_generated,Packet_Generator); //shows statistics collected from simulation
	System.out.println("");
	//Theoretical_Results=StatisticC.Display_Theoretical_Results();//shows statistics taken from theoretical formulas
	Final_Results= Practical_Results ;
	 JOptionPane.showMessageDialog( yes.this,Final_Results,"Progress",JOptionPane.INFORMATION_MESSAGE);
	//JOptionPane.showMessageDialog(yes.this,Final_Results,"Results",JOptionPane.INFORMATION_MESSAGE);
	//Calculate_queue_length_MM1();
	StatisticC.display_Everything_MM1(arrival_time_generated,Packet_Generator);
	StatisticC.Display_Theoretical_Results();
	StatisticC.Display_Practical_Results(arrival_time_generated,Packet_Generator);

	
	System.out.print("\n"+packet_generator_list.size()+ " "+ server_list.size());
	}


public static void Calculate_queue_length_MM1() {
	
	int i;
	int k;
	int j=Packet_Generator.size();
	int sum=0;
	for(i=0;i<j;i++) {
		
		for(k=i+1;k<j;k++) {
			if(Packet_Generator.get(k).arrival_time<Packet_Generator.get(i).service_start) {
				sum++;
			}
			
			else
				break;
			
		}
		
		Packet_Generator.get(i).number_of_packets_in_queue= sum;
		sum=0;
	}
}
public static boolean is_There_Drop_mm1(double arrival_time,ArrayList<ArrayList<Packet_Attributes>> main_tables) {
	int counter = 0;
	int i;
	int j;
	for(j=0;j<main_tables.size();j++) {
		for(i=(main_tables.get(j).size())-1;i>=0;i--) {
			if(arrival_time<main_tables.get(j).get(i).service_end) {
				counter++;
			}
			else
			{
				if(counter!=0) {
					counter--;
				}
				break;
			}
	}
		if(counter<queue_length) {
			return false;
		}
		else
			return true;
	}
	
	
	
	
	return false;
}
public static int is_There_Drop_mmc(double arrival_time,ArrayList<ArrayList<Packet_Attributes>> main_tables) {
	ArrayList<Integer> server_drop_counts = new ArrayList<Integer>();
	
	
	int counter = 0;
	int i;
	int j;
	
	for(j=0;j<main_tables.size();j++) {
		counter=0;
		
		for(i=(main_tables.get(j).size())-1;i>=0;i--) {
			if(arrival_time<main_tables.get(j).get(i).service_end) {
				counter++;
			}
			else
			{
				if(counter!=0) {
					counter--;
				}
				
				
				break;
				
			}
	}
		server_drop_counts.add(counter);
	}
	
	
	

	int min_server=0;
	
	int min=server_drop_counts.get(0);
	for(i=0;i<Server_List.size();i++) {
		if(server_drop_counts.get(i)<min) {
			min=server_drop_counts.get(i);
			min_server=i;
		}
	}
	if(server_drop_counts.get(min_server)<queue_length) {
		return min_server;

	}

	return -1;
	
	
	
	
	
	
}
public  void mmc_with_drop() {
	
	int drop_count=0;
	queue_length=6;
	int c ;
	RandomNCalculation CalculatorSample =new RandomNCalculation();
	
	Lambda=80;
	Mu=33;
	
	calculate_Size(C); 
	int first_size=Size;
	StatisticCenter StatisticC = new StatisticCenter(Lambda,Mu,C,Size) ;
	for (c=0; c<Size; c++) 
	{
		arrival_time_generated.add(CalculatorSample.Calculate_New_Lambda_Mu(1/Lambda));
	}
	cal_arrival_time();
	for (c=0; c<Size; c++) 
	{
		Packet_Generator.get(c).service_time_generated =CalculatorSample.Calculate_New_Lambda_Mu(1/Mu);
	}	
	
	
	
	
	int sv_number=C;
	int i;
	for(i=0;i<sv_number;i++) {
		ArrayList<Packet_Attributes> P1 = new ArrayList<Packet_Attributes>();
		Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
		F.service_start = F.arrival_time ;
		F.service_end = F.arrival_time + F.service_time_generated;
		
		F.Idle_time = F.arrival_time ;
		F.waiting_time = 0 ;
		F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
		P1.add(F);
		
		
		Server_List.add(P1);
		}
	
		for (i=sv_number;i<Size;i++) {
			ArrayList<Packet_Attributes> S = new ArrayList<Packet_Attributes>();
			
			Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
			int which_server = is_There_Drop_mmc(F.arrival_time,Server_List);
			if (which_server==-1){
				drop_count++;
				continue;
			}
			S=Server_List.get(which_server);
				
		
			if (F.arrival_time> getLastServiceEnd(S)) 
			{
				F.service_start =F.arrival_time;
			}
			else 
			{
				F.service_start = getLastServiceEnd(S);
			}
			F.service_end = F.service_start + F.service_time_generated;
			F.Idle_time = F.service_start - getLastServiceEnd(S);
			F.waiting_time = F.service_start - F.arrival_time ;
			F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
			S.add(F);
		}
		
		ArrayList<Packet_Attributes> Final_ArrayList = new ArrayList<Packet_Attributes>() ;
		
		for (ArrayList<Packet_Attributes> S:Server_List) 
		{
			for (Packet_Attributes entry:S)
			{
			Final_ArrayList.add(entry) ;
			}
		}
		for(i=0;i<Server_List.size();i++) {
		StatisticC.Display_Practical_Results_mmc_drop(arrival_time_generated, Server_List.get(i),Server_List.get(i).size());
		}
		StatisticC.update_size(drop_count);
		//StatisticC.display_Everything_MMC(Final_ArrayList);
		StatisticC.Display_Theoretical_Results_mmc();
		Final_Results=StatisticC.Display_Practical_Results_mmc(arrival_time_generated, Final_ArrayList);
		
		JOptionPane.showMessageDialog( yes.this,Final_Results,"Progress",JOptionPane.INFORMATION_MESSAGE);
		System.out.println("drop count = "+ drop_count);
		System.out.println("drop_percentage = % "+ 100*(drop_count/(double)first_size));
		
		
}
public  void mm1_with_drop() {
	
	int drop_count=0;
	
	int c ;
	RandomNCalculation CalculatorSample =new RandomNCalculation();
	
	
	
	calculate_Size(C); 
	int first_size=Size;
	StatisticCenter StatisticC = new StatisticCenter(Lambda,Mu,C,Size) ;
	for (c=0; c<Size; c++) 
	{
		arrival_time_generated.add(CalculatorSample.Calculate_New_Lambda_Mu(1/Lambda));
	}
	cal_arrival_time();
	for (c=0; c<Size; c++) 
	{
		Packet_Generator.get(c).service_time_generated =CalculatorSample.Calculate_New_Lambda_Mu(1/Mu);
	}	
	
	
	System.out.println("enter server count:");
	
	int sv_number=C;
	int i;
	for(i=0;i<sv_number;i++) {
		ArrayList<Packet_Attributes> P1 = new ArrayList<Packet_Attributes>();
		Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
		F.service_start = F.arrival_time ;
		F.service_end = F.arrival_time + F.service_time_generated;
		
		F.Idle_time = F.arrival_time ;
		F.waiting_time = 0 ;
		F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
		P1.add(F);
		
		
		Server_List.add(P1);
		}
	
		for (i=sv_number;i<Size;i++) {
			ArrayList<Packet_Attributes> S = new ArrayList<Packet_Attributes>();
			S=Find_Min();
			Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
			if (is_There_Drop_mm1(F.arrival_time,Server_List)==true){
				drop_count++;
				continue;
			}
			
				
		
			if (F.arrival_time> getLastServiceEnd(S)) 
			{
				F.service_start =F.arrival_time;
			}
			else 
			{
				F.service_start = getLastServiceEnd(S);
			}
			F.service_end = F.service_start + F.service_time_generated;
			F.Idle_time = F.service_start - getLastServiceEnd(S);
			F.waiting_time = F.service_start - F.arrival_time ;
			F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
			S.add(F);
		}
		
		ArrayList<Packet_Attributes> Final_ArrayList = new ArrayList<Packet_Attributes>() ;
		
		for (ArrayList<Packet_Attributes> S:Server_List) 
		{
			for (Packet_Attributes entry:S)
			{
			Final_ArrayList.add(entry) ;
			}
		}
	
		StatisticC.update_size(drop_count);
		
		//StatisticC.display_Everything_MMC(Final_ArrayList);
		Final_Results="";
		Final_Results=StatisticC.Display_Practical_Results_mmc(arrival_time_generated, Final_ArrayList);
		StatisticC.update_k(queue_length);
		StatisticC.Display_Theoretical_Results_mm1k();
		JOptionPane.showMessageDialog( yes.this,Final_Results,"Progress",JOptionPane.INFORMATION_MESSAGE);
		//StatisticC.Display_Theoretical_Results_mmc();
		
		
		System.out.println("mm1k printlendi");
		
}
public boolean check_mm1_conditions() {
	double a = Mu;
	double result = Mu*C*0.95;
	if(result > Lambda)
		return true;
	
	else {
		String info = "Utilization should be smaller then 0.95";
		JOptionPane.showMessageDialog( yes.this,info,"Progress",JOptionPane.INFORMATION_MESSAGE);
		return false;
	}
}
public  void mmc() {
	
	int c ;
	RandomNCalculation CalculatorSample =new RandomNCalculation();
	

	
	calculate_Size(C); 
	StatisticCenter StatisticC = new StatisticCenter(Lambda,Mu,C,Size) ;
	for (c=0; c<Size; c++) 
	{
		arrival_time_generated.add(CalculatorSample.Calculate_New_Lambda_Mu(1/Lambda));
	}
	cal_arrival_time();
	for (c=0; c<Size; c++) 
	{
		Packet_Generator.get(c).service_time_generated =CalculatorSample.Calculate_New_Lambda_Mu(1/Mu);
	}	
	
	
	System.out.println("enter server count:");
	
	int sv_number=C;
	int i;
	for(i=0;i<sv_number;i++) {
		ArrayList<Packet_Attributes> P1 = new ArrayList<Packet_Attributes>();
		Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
		F.service_start = F.arrival_time ;
		F.service_end = F.arrival_time + F.service_time_generated;
		
		F.Idle_time = F.arrival_time ;
		F.waiting_time = 0 ;
		F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
		P1.add(F);
		
		
		Server_List.add(P1);
		}
	
		for (i=sv_number;i<Size;i++) {
			ArrayList<Packet_Attributes> S = new ArrayList<Packet_Attributes>();
			S=Find_Min();
			Packet_Attributes F = new Packet_Attributes(Packet_Generator.get(i).service_time_generated,Packet_Generator.get(i).arrival_time);
			if (F.arrival_time> getLastServiceEnd(S)) 
			{
				F.service_start =F.arrival_time;
			}
			else 
			{
				F.service_start = getLastServiceEnd(S);
			}
			F.service_end = F.service_start + F.service_time_generated;
			F.Idle_time = F.service_start - getLastServiceEnd(S);
			F.waiting_time = F.service_start - F.arrival_time ;
			F.Total_time_in_system =F.waiting_time + F.service_time_generated ;
			S.add(F);
		}
		ArrayList<Packet_Attributes> Final_ArrayList = new ArrayList<Packet_Attributes>() ;
		
		for (ArrayList<Packet_Attributes> S:Server_List) 
		{
			for (Packet_Attributes entry:S)
			{
			Final_ArrayList.add(entry) ;
			}
		}
		int count=0;
		for (Packet_Attributes entry:Final_ArrayList)
		{
			System.out.println(count + 1);
			System.out.println(entry.service_end);
			count++;
		}
		StatisticC.Display_Theoretical_Results_mmc();
		StatisticC.display_Everything_MMC(Final_ArrayList);
		Final_Results=StatisticC.Display_Practical_Results_mmc(arrival_time_generated, Final_ArrayList);
		
		JOptionPane.showMessageDialog( yes.this,Final_Results,"Progress",JOptionPane.INFORMATION_MESSAGE);
		//StatisticC.display_Everything_MMC(Final_ArrayList);
		
		System.out.println("mmc printlendi");
		
		
}
public static double getLastServiceEnd(ArrayList<Packet_Attributes> S)
{
		
		
	return S.get(S.size()-1).service_end;
		
}
public static ArrayList<Packet_Attributes> Find_Min() {
	int i = 0;
	ArrayList<Packet_Attributes> min_table = Server_List.get(0);
	for (i = 1 ; i<Server_List.size(); i ++) {
		if (getLastServiceEnd(min_table) > getLastServiceEnd(Server_List.get(i))) {
			min_table = Server_List.get(i);
		}
	
	}
	return min_table ;
}

public static void cal_total_time() {//okey
	int i;
	for(i=0;i<Size;i++) {
		Packet_Generator.get(i).Total_time_in_system=Packet_Generator.get(i).waiting_time+Packet_Generator.get(i).service_time_generated;
	}
}
public static void cal_waiting_time() {//okey
	int i;
	for(i=0;i<Size;i++) {
		Packet_Generator.get(i).waiting_time=Packet_Generator.get(i).service_start-Packet_Generator.get(i).arrival_time;
	}
}
public static void cal_arrival_time() {//okey
	int i;
	//arrival_time.add((double) 0);
	Packet_Attributes Packet = new Packet_Attributes(0);
	Packet_Generator.add(Packet);
	for(i=0;i<Size-1;i++) {
		Packet_Attributes Packet1 = new Packet_Attributes(Packet_Generator.get(i).arrival_time+arrival_time_generated.get(i));
		Packet_Generator.add(Packet1);
		//arrival_time.add(arrival_time.get(i)+arrival_time_generated.get(i));
	}
	
}
public static void cal_service_start() {
	int i;
	Packet_Generator.get(0).service_start= 0;
	//service_start.add((double) 0);
	Packet_Generator.get(0).service_end= Packet_Generator.get(0).service_time_generated;
	//service_end.add(service_time_generated.get(0));
	for(i=1;i<Size;i++) {
		if (Packet_Generator.get(i-1).service_end<Packet_Generator.get(i).arrival_time) //i
			Packet_Generator.get(i).service_start=Packet_Generator.get(i).arrival_time; 
		else
			Packet_Generator.get(i).service_start=Packet_Generator.get(i-1).service_start+Packet_Generator.get(i-1).service_time_generated;
	
		Packet_Generator.get(i).service_end=Packet_Generator.get(i).service_start+Packet_Generator.get(i).service_time_generated;
	}
	
}
public static void cal_Idle() {
	Packet_Generator.get(0).Idle_time=0;
	int i;
	for(i=1;i<Size;i++) {
		Packet_Generator.get(i).Idle_time=Packet_Generator.get(i).service_start-Packet_Generator.get(i-1).service_end;
		
	}
}


public static void cal_service_end() {//
	int i;
	for(i=0;i<Size;i++) {
		Packet_Generator.get(i).service_end=Packet_Generator.get(i).service_start+Packet_Generator.get(i).service_time_generated;
	}
}
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					yes window = new yes();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public yes() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 910, 767);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel menu = new JPanel();
		menu.setBackground(Color.DARK_GRAY);
		menu.setForeground(new Color(0, 0, 0));
		menu.setToolTipText("Components");
		menu.setBounds(0, 0, 188, 717);
		frame.getContentPane().add(menu);
		
		JPanel environment = new JPanel();
		environment.setBackground(Color.GRAY);
		environment.setToolTipText("Components\r\n");
		environment.setBounds(186, 0, 708, 717);
		frame.getContentPane().add(environment);
		environment.setLayout(null);
		
		
		JButton packetGenerator = new JButton("Add Packet Generator");
		packetGenerator.setBounds(10, 71, 162, 23);
		packetGenerator.addMouseListener(new MouseAdapter()  {
			public void mousePressed(MouseEvent e) {
				JLabel label_packet_generator = new JLabel("");
				label_packet_generator.setIcon(new ImageIcon("C:\\Program Files\\Queuing_Simulation\\a.jpg"));
				//packet_generator_list.add(label_packet_generator);
				environment.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						//packet_generator_list.get(packet_generator_list.size()-1).setBounds(e.getX(),e.getY() , 100, 100);
						
						object label_pg = new object(label_packet_generator,e.getX()-50,e.getY()-30,e.getX()-50+100,e.getY()-30+100);
						
						packet_generator_list.add(label_pg);
						packet_generator_list.get(packet_generator_list.size()-1).j_label.setBounds(e.getX()-50,e.getY()-30 , 100, 100);
						environment.add(packet_generator_list.get(packet_generator_list.size()-1).j_label);
						environment.repaint();
						environment.removeMouseListener(environment.getMouseListeners()[0]);
					}
				});
				
			}
		});
		menu.setLayout(null); 
		menu.add(packetGenerator);
		
		JButton server = new JButton("Add Server");
		server.setBounds(10, 167, 162, 23);
		server.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JLabel server_label = new JLabel("");
				server_label.setIcon(new ImageIcon("C:\\Program Files\\Queuing_Simulation\\b.jpg"));
				//server_list.add(server_label);
				
				environment.addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						object label_sv = new object(server_label,e.getX()-50,e.getY()-50,e.getX()-50+100,e.getY()-50+100);
						
						server_list.add(label_sv);
						server_list.get(server_list.size()-1).j_label.setBounds(e.getX()-50,e.getY()-50 , 100, 100);
						environment.add(server_list.get(server_list.size()-1).j_label);
						environment.repaint();
						environment.removeMouseListener(environment.getMouseListeners()[0]);
						
					}
				});
				
			}
		});
		menu.add(server);
		//
		JButton link = new JButton("Link");
		link.setBounds(10, 265, 162, 23);
		link.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				n1=0;
				n2=0;
				Graphics g = environment.getGraphics();
				link_list.add(g);
				environment.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						x1=e.getX();
						y1=e.getY();
						x2=e.getX();
						y2=e.getY();
						
						if (n1!=0){
							link_list.get(link_list.size()-1).drawLine(n1, n2, x2, y2);
							n1=0;
							n2=0;
							environment.removeMouseListener(environment.getMouseListeners()[0]);
						}
						else if(x1==x2) {
							n1=x1;
							n2=y1;
						}
						
						
						
						}
					
				});
				environment.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						x2=e.getX();
						y2=e.getY();
						int i;
						for(i=0;i<server_list.size();i++) {
							if(x2>server_list.get(i).x1&&x2<server_list.get(i).x2&&y2>server_list.get(i).y1&&y2<server_list.get(i).y2) {
						link_list.get(link_list.size()-1).drawLine(x1, y1, x2, y2);
						environment.removeMouseListener(environment.getMouseListeners()[0]);
					
							}}
							}
				});
				
				
			}
		});
		menu.add(link);
		
		JButton del_PG = new JButton("Delete Packet Generator");
		del_PG.setBounds(10, 122, 162, 23);
		del_PG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==del_PG) {
					environment.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							x2=e.getX();
							y2=e.getY();
							
							int i;
							for(i=0;i<packet_generator_list.size();i++) {
								if(x2>packet_generator_list.get(i).x1&&x2<packet_generator_list.get(i).x2&&y2>packet_generator_list.get(i).y1&&y2<packet_generator_list.get(i).y2) {
									JLabel d=packet_generator_list.get(i).j_label;
									packet_generator_list.remove(d);
									environment.remove(d);
									environment.repaint();
									environment.removeMouseListener(environment.getMouseListeners()[0]);
								}
									
							}
						}
					});
				}
			}
		});
		menu.add(del_PG);
		
		JButton del_SV = new JButton("Delete Server");
		del_SV.setBounds(10, 215, 162, 23);
		del_SV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==del_SV) {
					environment.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							x2=e.getX();
							y2=e.getY();
							
							int i;
							for(i=0;i<server_list.size();i++) {
								if(x2>server_list.get(i).x1&&x2<server_list.get(i).x2&&y2>server_list.get(i).y1&&y2<server_list.get(i).y2) {
									JLabel d=server_list.get(i).j_label;
									server_list.remove(d);
									environment.remove(d);
									environment.repaint();
									environment.removeMouseListener(environment.getMouseListeners()[0]);
								}
									
							}
						}
					});

				}
			}
		});
		menu.add(del_SV);
		
		JButton compile = new JButton("Compile");
		compile.setBounds(10, 316, 162, 24);
		compile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				if(c.getSource()==compile) {
					clear_everything();
					find_sizes();
					
					String simulation_type=(String) c1.getSelectedItem();
					//"MM1", "MM1K", "MMC", "MMCK", "FeedBack"
					if(simulation_type.equals("MM1")) {
						if(check_mm1_conditions())
						mm1();
					}
						
					if(simulation_type.equals("MM1K")) {
						if(check_mm1_conditions())
						mm1_with_drop();
					}
						
					if(simulation_type.equals("MMC")) {
						if(check_mm1_conditions())
						mmc();
					}
						
					
					
					
				}
			
		
		
		
		
			}
		
		});
		menu.add(compile);

		JButton enter_lambda = new JButton("Enter Lambda");
		enter_lambda.setBounds(10, 366, 162, 24);
		enter_lambda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				if(c.getSource()==enter_lambda) {
					
							
									String s_Lambda = JOptionPane.showInputDialog("Enter New Lambda");
									Lambda = Double.parseDouble(s_Lambda);
								
							
							
						
					
				}
			
		
		
		
		
			}
		
		});
		menu.add(enter_lambda);
		JButton enter_mu = new JButton("Enter Mu");
		enter_mu.setBounds(10, 416, 162, 24);
		enter_mu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				if(c.getSource()==enter_mu) {
					
							
									String s_Mu = JOptionPane.showInputDialog("Enter New Mu");
									Mu = Double.parseDouble(s_Mu);
								
							
							
						
					
				}
			
		
		
		
		
			}
		
		});
		menu.add(enter_mu);
		JButton q_capacity = new JButton("Enter Queue Capacity");
		q_capacity.setBounds(10, 466, 162, 24);
		q_capacity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent c) {
				if(c.getSource()==q_capacity) {
					
							
									String q_Capacity = JOptionPane.showInputDialog("Enter New Queue Capacity");
									queue_length = Integer.parseInt(q_Capacity);
								
							
							
						
					
				}
			
		
		
		
		
			}
		
		});
		menu.add(q_capacity);
		
		
		
		c1.setBounds(10, 516, 162, 24);
		menu.add(c1);
		
		
		
	}
	

	
}
	

