package blueprint.zulu;

import java.util.ArrayList;
import blueprint.zulu.command.*;

import blueprint.zulu.database.Auth;
import blueprint.zulu.database.Mongo;
import blueprint.zulu.util.*;

public class Test {

	public static void main(String[] args) throws Exception {
		Mongo.DBConnect();
		Mongo.deleteCollectionElements("login");
		Mongo.deleteCollectionElements("users");
		Mongo.deleteCollectionElements("calendars");
		Mongo.deleteCollectionElements("projects");
		Mongo.deleteCollectionElements("tasks");
		Mongo.deleteCollectionElements("messages");
		Mongo.deleteCollectionElements("dashboards");
		
		//User 1
		String u1 = "caganselim@gmail.com";
		String n1 = "Cagan Selim Coban";
		String pw1 = "12345";
		
		//User 2
		String u2 = "oksijen.h2o@gmail.com";
		String n2 = "Elif Aygun";
		String pw2 = "elif1997";
		
		//User 3
		String u3 = "ahmetburakcati@gmail.com";
		String n3 = "Ahmet Burak Catli";
		String pw3 = "phys2016";
		
		//User 4
		String u4 = "mertcanatan@gmail.com";
		String n4 = "Mert Canatan";
		String pw4 = "webshowieee";
		
		//User 5
		String u5 = "bkunkcu@gmail.com";
		String n5 = "Burak Kunkcu";
		String pw5 = "captain71";
		
		//Register users
		User.registerNewUser(u1,n1,pw1);
		User.registerNewUser(u2, n2, pw2);
		User.registerNewUser(u3, n3, pw3);
		User.registerNewUser(u4,n4, pw4);
		User.registerNewUser(u5, n5, pw5);
		
		//Create project 1
		User us1 = User.getByEmail("caganselim@gmail.com");
		ProjectCommand p = new ProjectCommand();
		String[] pr1 = new String[3];
		pr1[0] = "Mobile Days 2016";
		pr1[1] = "IEEE";
		pr1[2] = us1.getID();	
		p.execute("project", "create", pr1);
		us1 = User.getByEmail("caganselim@gmail.com");
				
		//Create project 2
		User us2 = User.getByEmail("oksijen.h2o@gmail.com");
		String[] pr2 = new String[3];
		pr2[0] = "CS Fair 2016";
		pr2[1] = "IEEE";
		pr2[2] = us2.getID();	
		p.execute("project", "create", pr2);
		us2 = User.getByEmail("oksijen.h2o@gmail.com");
		
		System.out.println(us1);
		System.out.println(us2);
		
		
		//Add users to project 1
		String[] uid = new String[1];
		uid[0] = us1.getID();
		System.out.println( us1.getID());
		UserCommand u = new UserCommand();
		ArrayList<Project> a = (ArrayList<Project>) u.execute("user", "getprojects", uid);
		System.out.println(a);
		String[] pid = new String[2];
		pid[0] = a.get(0).getID();
		pid[1] = u2;
		p.execute("project", "adduserbyemail", pid);
		pid[1] = u3;
		p.execute("project", "adduserbyemail", pid);
		pid[1] = u4;
		p.execute("project", "adduserbyemail", pid);
		
		//Add user to project 2
		String[] uid2 = new String[1];
		uid2[0] = us2.getID();
		ArrayList<Project> aa = (ArrayList<Project>) u.execute("user", "getprojects",uid2);
		String[] pid2 = new String[2];
		pid2[0] = aa.get(0).getID();
		pid2[1] = u1;
		p.execute("project", "adduserbyemail", pid2);
		pid2[1] = u3;
		p.execute("project", "adduserbyemail", pid2);
		pid[1] = u5;
		p.execute("project", "adduserbyemail", pid2);
		
		//Adding task 1 to project 1
		String[] ts1 = new String[3];
		ts1[0] = "Arrange the invitations";
		ts1[1] = "18/05/2016 06";
		ts1[2] = a.get(0).getCalendar().getID();
		TaskCommand t = new TaskCommand();
		t.execute("task", "create", ts1);
		
		
		//Adding task 2 to project 1
		String[] ts2 = new String[3];
		ts2[0] = "Send sponsorship e-mails";
		ts2[1] = "13/05/2016 08";
		ts2[2] = a.get(0).getCalendar().getID();
		t.execute("task", "create", ts2);
		
		//Adding task 1 to project 2
		String[] ts3 = new String[3];
		ts3[0] = "Meeting with Oznur Tastan";
		ts3[1] = "17/05/2016 11";
		ts3[2] = aa.get(0).getCalendar().getID();
		t.execute("task", "create", ts3);
		
		//Adding task 2 to project 2
		String[] ts4 = new String[3];
		ts4[0] = "Make reservation to Bilkent Hotel";
		ts4[1] = "19/05/2016 09";
		ts4[2] = aa.get(0).getCalendar().getID();
		t.execute("task", "create", ts4);
		
		//Insert messages to project 1
		String[] ms1 = new String[3];
		
		ms1[0]= us1.getID();
		ms1[1]= a.get(0).getID();
		ms1[2]= "Hi, we may have an appointment with dean";
		MessageCommand m = new MessageCommand();
		m.execute("message", "send", ms1);
		
		String[] ms2 = new String[3];
		ms2[0]= us2.getID();
		ms2[1]= a.get(0).getID();
		ms2[2]= "Okay, we should be ready for it";
		m.execute("message", "send", ms2);
		

		
		//Check
		System.out.println("--------------LOGIN---------------");
		Mongo.printCollection("login");
		System.out.println("-------------USERS-----------------");
		Mongo.printCollection("users");
		System.out.println("---------------PROJECTS--------------");
		Mongo.printCollection("projects");
		System.out.println("--------------TASKS----------------");
		Mongo.printCollection("tasks");
		System.out.println("---------------MESSAGES--------------");
		Mongo.printCollection("messages");
		System.out.println("---------------CALS-----------------");
		Mongo.printCollection("calendars");
		System.out.println("--------------DASHS-------------------");
		Mongo.printCollection("dashboards");
	}

}
