package bitirme_gui_calisma;

import java.util.ArrayList;

public class Packet_Attributes {

   double service_time_generated ;
   double arrival_time;
   double service_start;
   double service_end ; 
   double waiting_time;
   double Idle_time;
   double Total_time_in_system; 
   int number_of_packets_in_queue;
   
   public Packet_Attributes(double arrival_time) {
	super();
	this.arrival_time = arrival_time;

   }

   public Packet_Attributes(double service_time_generated, double arrival_time) {
	super();
	this.service_time_generated = service_time_generated;
	this.arrival_time = arrival_time;
}
   
}
