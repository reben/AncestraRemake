package objects;

import communication.ComThread;

public class GameServer
{
	private int ID;
	private String IP;
	private int Port;
	private int State;
	private String HostDB;
	private String DBName;
	private String DBUser;
	private String DBPassword;
	private String KEY;
	private ComThread exchangeThread = null;
	private int BlockLevel = 0;
	private int PlayerLimit = 0;
	private int NumPlayer = 0;
	
	public GameServer(int ID, String IP, int Port, int State, String HostDB, String DBName, String DBUser, String DBPassword, String KEY)
	{
		this.ID = ID;
		this.IP = IP;
		this.Port = Port;
		this.State = State;
		this.HostDB= HostDB;
		this.DBName = DBName;
		this.DBUser = DBUser;
		this.DBPassword = DBPassword;
		if (this.DBPassword == null)
			this.DBPassword = "";
		this.KEY = KEY;
	}
	
	public ComThread getThread()
	{
		return exchangeThread;
	}
	
	public void setThread(ComThread t)
	{
		exchangeThread = t;
	}
	
	public String getHost()
	{
		return this.HostDB;
	}
	
	public String getKey()
	{
		return this.KEY;
	}
	
	public String getName()
	{
		return this.DBName;
	}
	
	public String getUser()
	{
		return this.DBUser;
	}
	
	public String getPassword()
	{
		return this.DBPassword;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public String getIP()
	{
		return this.IP;
	}
	
	public int getPort()
	{
		return this.Port;
	}
	
	public int getState()
	{
		return this.State;
	}
	
	public void setState(int State)
	{
		this.State = State;
	}
	
	public void setBlockLevel(int gmlevel)
	{
		this.BlockLevel = gmlevel;
	}
	
	public int getBlockLevel()
	{
		return this.BlockLevel;
	}
	
	public void set_PlayerLimit(int num)
	{
		PlayerLimit = num;
	}
	
	public int get_PlayerLimit()
	{
		return PlayerLimit;
	}
	
	public void set_NumPlayer(int num)
	{
		NumPlayer = num;
	}
	
	public int get_NumPlayer()
	{
		return NumPlayer;
	}
}
