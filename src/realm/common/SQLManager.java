package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;

import objects.Account;
import objects.GameServer;

import com.mysql.jdbc.PreparedStatement;

public class SQLManager
{
	private static Connection othCon;

	private static Timer timerCommit;
	private static boolean needCommit;
	
	
	public synchronized static ResultSet executeQuery(String query,String DBNAME) throws SQLException
	{
		if(!Ancestra.isInit)
			return null;
		Connection DB = othCon;
		
		
		Statement stat = DB.createStatement();
		ResultSet RS = stat.executeQuery(query);
		stat.setQueryTimeout(300);
		return RS;
	}
	
	public synchronized static ResultSet executeQueryG(String query, GameServer G) throws SQLException
	{
		if (!Ancestra.isInit)
			return null;
		try
		{
			Connection DB = DriverManager.getConnection("jdbc:mysql://" + G.getHost() + "/" + G.getName(),G.getUser(),G.getPassword());
			DB.setAutoCommit(false);
			if (!DB.isValid(1000))
				return null;
			Statement stat = DB.createStatement();
			ResultSet RS = stat.executeQuery(query);
			stat.setQueryTimeout(300);
			return RS;
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized static PreparedStatement newTransact(String baseQuery,Connection dbCon) throws SQLException
	{
		PreparedStatement toReturn = (PreparedStatement) dbCon.prepareStatement(baseQuery);
		
		needCommit = true;
		return toReturn;
	}
	
	public synchronized static void commitTransacts()
	{
		try
		{
			if(othCon.isClosed())
			{
				closeCons();
				setUpConnexion();
			}
			
			
			othCon.commit();
			
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
			commitTransacts();
		}
	}
	
	public synchronized static void closeCons()
	{
		try
		{
			commitTransacts();
			
			othCon.close();
			
		}catch (Exception e)
		{
			System.out.println("SQL : Erreur à la fermeture des connexions : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : Erreur à la fermeture des connexions : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static final boolean setUpConnexion()
	{
		try
		{
			othCon = DriverManager.getConnection("jdbc:mysql://"+Ancestra.REALM_DB_HOST+"/"+Ancestra.REALM_DB_NAME,Ancestra.REALM_DB_USER,Ancestra.REALM_DB_PASSWORD);
			othCon.setAutoCommit(false);
			
			if(!othCon.isValid(1000))
			{
				Ancestra.addToErrorLog("SQL : Connexion a la BD invalide!");
				return false;
			}
			
			needCommit = false;
			TIMER(true);
			
			return true;
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	public static void TIMER(boolean start)
	{
		if(start)
		{
			timerCommit = new Timer();
			timerCommit.schedule(new TimerTask() {
				
				public void run() {
					if(!needCommit)return;
					
					commitTransacts();
					needCommit = false;
					
				}
			}, Ancestra.REALM_DB_COMMIT, Ancestra.REALM_DB_COMMIT);
		}
		else
			timerCommit.cancel();
	}
	
	private static void closeResultSet(ResultSet RS)
	{
		try
		{
			RS.getStatement().close();
			RS.close();
		}catch(SQLException e) 
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	private static void closePreparedStatement(PreparedStatement p)
	{
		try
		{
			p.clearParameters();
			p.close();
		}catch(SQLException e) 
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void UPDATE_ACCOUNT(String ip, boolean UpdateSub, int Sub, int guid)
	{
		String bquery;
		if(UpdateSub) bquery = "UPDATE accounts SET `curIP`=?, `subscription`=? WHERE `guid`=? ;";
		else bquery = "UPDATE accounts SET `curIP`=? WHERE `guid`=? ;";
		try
		{
			PreparedStatement p = newTransact(bquery, othCon);
			p.setString(1, ip);
			if(UpdateSub) p.setInt(2, Sub);
			p.setInt((UpdateSub?3:2), guid);
			p.execute();
			closePreparedStatement(p);
		}catch(SQLException e)
		{
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			try {
				if(othCon.isClosed()) setUpConnexion();
				if(!othCon.isClosed()) UPDATE_ACCOUNT(ip, UpdateSub, Sub, guid);
			} catch (SQLException e1) {
				System.out.println("SQL : "+e.getMessage());
				Ancestra.addToErrorLog("SQL : "+e.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	public static void RESET_CUR_IP()
	{
		String bquery = "UPDATE accounts SET `curIP`=?;";
		try
		{
			PreparedStatement p = newTransact(bquery, othCon);
			p.setString(1, "");
			p.execute();
			closePreparedStatement(p);
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static int getNumberPersosOnThisServer(int guid, int ID)
	{
		int a = 0;
		GameServer G = Realm.GameServers.get(ID);
		try
		{
			ResultSet RS = SQLManager.executeQueryG("SELECT COUNT(*) As row from personnages WHERE account=" + guid + ";", G);
			RS.next();
			a = RS.getInt("row");
			closeResultSet(RS);
		}catch(SQLException e)
		{
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			try {
				if(othCon.isClosed()) setUpConnexion();
				if(!othCon.isClosed()) getNumberPersosOnThisServer(guid,ID);
			} catch (SQLException e1) {
				System.out.println("SQL : "+e.getMessage());
				Ancestra.addToErrorLog("SQL : "+e.getMessage());
				e1.printStackTrace();
			}
		}
		return a;
	}
	
	public static void LOAD_ACCOUNT_BY_USER(String user)
	{
		try
		{
			ResultSet RS = SQLManager.executeQuery("SELECT * from accounts WHERE `account` LIKE '"+user+"';",Ancestra.REALM_DB_NAME);
			
			while(RS.next())
			{
				Realm.addAccount(new Account(
					RS.getInt("guid"),
					RS.getString("account").toLowerCase(),
					RS.getString("pass"),
					RS.getString("pseudo"),
					RS.getString("question"),
					RS.getString("reponse"),
					RS.getInt("level"),
					RS.getInt("subscription"),
					(RS.getInt("banned") == 1),
					RS.getString("lastIP"),
					RS.getString("lastConnectionDate"),
					RS.getString("giftID")));
			}
			
			closeResultSet(RS);
		}catch(SQLException e)
		{
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			try {
				if(othCon.isClosed()) setUpConnexion();
				if(!othCon.isClosed()) LOAD_ACCOUNT_BY_USER(user);
			} catch (SQLException e1) {
				System.out.println("SQL : "+e.getMessage());
				Ancestra.addToErrorLog("SQL : "+e.getMessage());
				e1.printStackTrace();
			}
		}
	}
	
	public static void LOAD_SERVERS()
	{
		try
		{
		ResultSet RS = SQLManager.executeQuery("SELECT * from gameservers;", Ancestra.REALM_DB_NAME);
		while(RS.next())
		{
			Realm.GameServers.put(
			RS.getInt("ID"),
			new GameServer(
				RS.getInt("ID"),
				RS.getString("ServerIP"),
				RS.getInt("ServerPort"),
				RS.getInt("State"),
				RS.getString("ServerBDD"),
				RS.getString("ServerDBName"),
				RS.getString("ServerUser"),
				RS.getString("ServerPassword"),
				RS.getString("Key")));
		}
		closeResultSet(RS);
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static int LOAD_BANIP() 
	{
		int i = 0;
		try
		{
			ResultSet RS = SQLManager.executeQuery("SELECT ip from banip;",Ancestra.REALM_DB_NAME); 
		      while (RS.next()) 
		      { 
		    	  	if(!RS.isLast())
		    	  		Realm.BAN_IP += RS.getString("ip")+",";
		    	  	else
		    	  		Realm.BAN_IP += RS.getString("ip");
					
		    	  	i++;
		      }
				closeResultSet(RS);
		}catch(SQLException e)
		{
			System.out.println("SQL : "+e.getMessage());
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			e.printStackTrace();
		}
		return i;
	}
	
	public static void ADD_BANIP(String ip)
	{
		String baseQuery = "INSERT INTO `banip` VALUES (?);";
		try
		{
			PreparedStatement p = newTransact(baseQuery, othCon);
			p.setString(1, ip);
			p.execute();
			closePreparedStatement(p);
		}catch(SQLException e)
		{
			Ancestra.addToErrorLog("SQL : "+e.getMessage());
			try {
				if(othCon.isClosed()) setUpConnexion();
				if(!othCon.isClosed()) ADD_BANIP(ip);
			} catch (SQLException e1) {
				System.out.println("SQL : "+e.getMessage());
				Ancestra.addToErrorLog("SQL : "+e.getMessage());
				e1.printStackTrace();
			}
		}
	}
}
