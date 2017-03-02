package com.softserve.edu.jroutes.service;
import com.softserve.edu.jroutes.dao.CountryDAO;
import com.softserve.edu.jroutes.dao.RouteConnectionDAO;
import com.softserve.edu.jroutes.dao.RouteDAO;
import com.softserve.edu.jroutes.dao.SavedRouteDAO;
import com.softserve.edu.jroutes.dao.SecurityRoleDAO;
import com.softserve.edu.jroutes.dao.TransportDAO;
import com.softserve.edu.jroutes.dao.RoutePointDAO;
import com.softserve.edu.jroutes.dao.UserDAO;
import com.softserve.edu.jroutes.entity.Country;
import com.softserve.edu.jroutes.entity.Route;
import com.softserve.edu.jroutes.entity.RoutePoint;
import com.softserve.edu.jroutes.entity.SavedRoute;
import com.softserve.edu.jroutes.entity.SecurityRole;
import com.softserve.edu.jroutes.entity.Transport;
import com.softserve.edu.jroutes.entity.User;
import com.softserve.edu.jroutes.exception.NonUniqueException;
import com.softserve.edu.jroutes.entity.RouteConnection;

public class DataInput {
		private Transport[] transportTypes;
		private Country[] countryNames;
		private RoutePoint[] routepointNames;
		private RouteConnection[] routeconnectionId;
		private User[] userNames;
		private SavedRoute[] savedrouteNames;
		private SecurityRole admin;
		private SecurityRole user;
		
	//TransportInput
 	void transportDataInput(int count) throws NonUniqueException{
 		transportTypes = new Transport[count];
 		TransportDAO transportDAO = new TransportDAO();
		for(int i=0;i<count;i++){	
			Transport tr =  new Transport();
			tr.setName("Transport#"+(i+1));
			transportTypes[i] = tr;
			transportDAO.addElement(tr);
		}
	}
 	
 	//CountryInput
 	 	void CountryDataInput(int count) throws NonUniqueException{
 	 		
 	 		countryNames = new Country[count];
 	 		CountryDAO countryDAO = new CountryDAO();
 			for(int i=0;i<count;i++){	
 				Country cntr =  new Country();
 				cntr.setName("Country#"+(i+1));
 				countryNames[i] = cntr;
 				countryDAO.addElement(cntr);
 			}
 			
 			for(Country value : countryNames ){
 				
 				System.out.println(value.getName());
 			}
 		}
 	
 	//RoutePointInput
 	 	void RoutePointDataInput(int count) throws NonUniqueException{
 	 		
 	 		routepointNames = new RoutePoint[count*countryNames.length];
 	 		int temp =0;
 	 		RoutePointDAO routepointDAO = new RoutePointDAO();
 	 	for(int j=0;j<countryNames.length;j++){
 			for(int i=0;i<count;i++){
 				RoutePoint rp =  new RoutePoint();
 				rp.setName("RoutePoint#"+(i+1)+"-"+(j+1));
 				System.out.println("RoutePoint#"+(i+1)+"-"+(j+1));
 				rp.setCountry(countryNames[j]);
 				routepointNames[temp++] = rp;
 				routepointDAO.addElement(rp);
  			}
 			
 	 	}
 				  
 	}
 	 	
 	 //RouteConnectionInput
 	 	void RouteConnectionDataInput() throws NonUniqueException{
 	
 	 		routeconnectionId = new RouteConnection[(routepointNames.length-1)*transportTypes.length];	
 	 		int temp =0; long totaltime =0;
 	 		RouteConnectionDAO routepointDAO = new RouteConnectionDAO();
 	 		
 	 		for(int j=0;j<transportTypes.length;j++){ 		 
 	 			for(int i=0;i<routepointNames.length-1;i++){
 	 				RouteConnection rconn = new  RouteConnection();
 	 				rconn.setRoutePointAId(routepointNames[i]);
 	 				rconn.setRoutePointBId(routepointNames[i+1]);
 	 				rconn.setTransportId(transportTypes[j]);
 	 				rconn.setPrice((long)(Math.random()*100));
 	 				rconn.setTime((long)(Math.random()*100));
 	 				routeconnectionId[temp++] = rconn;
 	 				
 	 				long time_before = System.currentTimeMillis();
 	 				 routepointDAO.addElement(rconn);
 	 				long time_after = System.currentTimeMillis();
 	 				totaltime+=time_after-time_before;
 	 				System.out.println("Route_Connection:  ID="+routeconnectionId[temp-1].getId()+
 	 						"  point A="+routeconnectionId[temp-1].getRoutePointAId().getName()+
 	 						"  point B="+routeconnectionId[temp-1].getRoutePointBId().getName()+
 	 						"  transport="+routeconnectionId[temp-1].getTransportId().getName()+
 	 						"  milliseconds:"+((time_after-time_before)));
 	 			}
 	 		}	
 	 		
 	 		System.out.println("TotalTime:"+totaltime);
     }
 	 	
 	 //SecurityRole
 	 	void SecurityRoleDataInput() throws NonUniqueException{	
 	 		
 	 	    admin = new SecurityRole();
 	 		user = new SecurityRole();
 	 		admin.setName("Administrator");
 	 		user.setName("User");
 	 		SecurityRoleDAO securityRoleDAO = new SecurityRoleDAO();
 	 		securityRoleDAO.addElement(admin);
 	 		securityRoleDAO.addElement(user);
 	 	}
 	 	
 	 //UserInput
 	 	void UserDataInput(int count) throws NonUniqueException{	
 	 		
 	 		userNames = new User[count];
 	 		UserDAO userDAO = new UserDAO();
 	 		for(int i=0;i<count;i++){	
 				User us =  new User();
 				us.setFirstName("FirstName#"+(i+1));
 				us.setLastName("LastName#"+(i+1));
 				us.setEmail("email_"+(i+1)+"@gmail.com");
 				us.setPassword("pass_"+(i+1));
 				if(i%2!=0){
 					us.getRoles().add(admin);
 					us.getRoles().add(user);
 				}else{
 					us.getRoles().add(user);
 				}
 				userNames[i] = us;
 				userDAO.addElement(us);
 			}
 	 		
 	 	}
 	 	
 	 //SavedRouteInput
 	 	
 	 	void SavedRouteDataInput() throws NonUniqueException{
 	 		
 	 		savedrouteNames =  new SavedRoute[5];
 	 		SavedRouteDAO savedrouteDAO = new SavedRouteDAO();
 	 		
 	 		for(int i=0;i<5;i++){
 	 		SavedRoute savedRoute1 = new SavedRoute();	
 			savedRoute1.setName("Saved_Route#"+(i+1));
 			savedRoute1.setUserId(userNames[i]);
 			savedRoute1.setModificationTime(new java.sql.Date((long) (new java.util.Date()).getTime()));
 				if(i%2!=0){
 					savedRoute1.setIsCompanyRoute(true);
 				}else{
 					savedRoute1.setIsCompanyRoute(false);
 				}
 				savedrouteNames[i] = savedRoute1;
 	 	 		savedrouteDAO.addElement(savedRoute1);	
 	 		}
 	 		
  		}
 	 	
 	 //RoutedataInput
 	 	void RouteDataInput() throws NonUniqueException{
 	 		
 	 		long k=1; int n=0;
 	 		RouteDAO routeDAO = new RouteDAO();
 	 		
 	 		for(int i=0;i<(savedrouteNames.length*3);i++){
 	 		Route route = new Route();
 	 		route.setRouteConnectionId(routeconnectionId[i]);
 	 			if(k<=3){ 
 	 				route.setSequenceNumber(k);	
 	 				route.setSavedRouteId(savedrouteNames[n]);
 	 				k++;
 	 			}else{ 
 	 				k=1;
 	 				route.setSequenceNumber(k);
 	 				route.setSavedRouteId(savedrouteNames[++n]);
 	 				k++;
 	 			} 	 	
 	 	 	routeDAO.addElement(route);
 	 		}
 	 		
 	 	}
 	 		
 		 	
	public static void main(String[] args) throws NonUniqueException {
		
		DataInput dtInput = new DataInput();
		dtInput.transportDataInput(5);// <==Transport count input;
		dtInput.CountryDataInput(5);//<==Country count input;
		dtInput.RoutePointDataInput(5);//<== Route_Point count input
		dtInput.RouteConnectionDataInput();//<== generated by RoutePoints and Countries count;
		dtInput.SecurityRoleDataInput();//<==administrator and user Roles generating;
		dtInput.UserDataInput(100);//<== User count input; 
		dtInput.SavedRouteDataInput();//<== Add 5 routes;
		dtInput.RouteDataInput();//<== generated by RoutePoints and Countries count;
		
		
	}

}
