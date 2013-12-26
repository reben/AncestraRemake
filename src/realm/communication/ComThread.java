package communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



import objects.Account;
import objects.GameServer;
import common.Ancestra;
import common.SQLManager;
import common.Realm;


public class ComThread implements Runnable {
	private BufferedReader _in;
	private Thread _t;
	private PrintWriter _out;
	private Socket _s;
	private GameServer _server = null;
	

	public ComThread(Socket sock) 
	{
		try
		{
			_s = sock;
			_in = new BufferedReader(new InputStreamReader(_s.getInputStream()));
			_out = new PrintWriter(_s.getOutputStream());
			_t = new Thread(this);
			_t.setDaemon(true);
			_t.start();
		}catch(IOException e)
		{
			try
			{
				if(!_s.isClosed()) _s.close();
			}catch (IOException e1) {}
		}
		finally
		{
			if (_server != null)
			{
				_server.setState(0);
				Ancestra.addToComLog("ComThread : Server revoked.");
				System.out.println("ComThread : Server revoked.");
			for (Account acc : Realm.getAccountsMap().values())
			{
				if(acc.getRealmThread() == null) continue;
				acc.getRealmThread().refresh();
			}
				_server.setThread(null);
			}
		}
	}

	public void run() 
	{
		try
		{
			String packet = "";
			char charCur[] = new char[1];
			
			while(_in.read(charCur, 0, 1)!=-1 && Ancestra.isRunning)
	    	{
				if (charCur[0] != '\u0000' && charCur[0] != '\n' && charCur[0] != '\r')
		    	{
	    			packet += charCur[0];
		    	}else if(!packet.isEmpty())
		    	{
		    		if(Ancestra.REALM_DEBUG)
		    		{
		    			System.out.println("ComThread: Recv << "+packet);
		    			Ancestra.addToComLog("ComThread: Recv << "+packet);
		    		}
		    		parsePacket(packet);
		    		packet = "";
		    	}
	    	}
		}catch(IOException e)
		{
			try
			{
			_in.close();
			_out.close();
			if (_server != null)
			{
				_server.setState(0);
				Ancestra.addToComLog("ComThread : Server revoked.");
				System.out.println("ComThread : Server revoked.");
			for (Account acc : Realm.getAccountsMap().values())
			{
				if(acc.getRealmThread() == null) continue;
				acc.getRealmThread().refresh();
			}
				_server.setThread(null);
			}
			if(!_s.isClosed())_s.close();
			_t.interrupt();
		}catch(IOException e1){}
		}finally
		{
			try
			{
			_in.close();
			_out.close();
			if (_server != null)
			{
				_server.setState(0);
				Ancestra.addToComLog("ComThread : Server revoked.");
				System.out.println("ComThread : Server revoked.");
			for (Account acc : Realm.getAccountsMap().values())
			{
				if(acc.getRealmThread() == null) continue;
				acc.getRealmThread().refresh();
			}
				_server.setThread(null);
			}
			if(!_s.isClosed())_s.close();
			_t.interrupt();
			}catch(IOException e1){}
		}
	}
	
	public void kick()
	{
		try
		{
			Ancestra.addToComLog("ComThread : The GameServer has broken the connection.");
			System.out.println("ComThread : The GameServer has broken the connection.");
			if (_server != null)
			{
				_server.setState(0);
				Ancestra.addToComLog("ComThread : Server revoked.");
				System.out.println("ComThread : Server revoked.");
			for (Account acc : Realm.getAccountsMap().values())
			{
				if(acc.getRealmThread() == null) continue;
				acc.getRealmThread().refresh();
			}
				_server.setThread(null);
			}
			_in.close();
			_out.close();
			if (!_s.isClosed()) _s.close();
			_t.interrupt();
		}catch(IOException e)
		{
			System.out.println("ComThreadKick : "+e.getMessage());
			Ancestra.addToErrorLog("ComThreadKick : "+e.getMessage());
			Ancestra.addToComLog("ComThreadKick : "+e.getMessage());
		}
	}
	
	public void sendDeco(int guid)
	{
		System.out.println("ComThread: Send>>LO"+guid);
		System.out.println("ComThread : Envoi du paquet de LoginOut ...");
		Ancestra.addToComLog("ComThread: Send>>LO"+guid);
		Ancestra.addToComLog("ComThread : Envoi du paquet de LoginOut ...");
		try
		{
			_out.print("LO"+guid+(char)0x00);//LogOut
			_out.flush();
			System.out.println("ComThread : Envoi OK.");
		}catch(Exception e)
		{
			System.out.println("ComThreadSendDeco : "+e.getMessage());
			Ancestra.addToErrorLog("ComThreadSendDeco : "+e.getMessage());
			Ancestra.addToComLog("ComThreadSendDeco : "+e.getMessage());
		}
	}
	
	public void sendAddWaiting(String str)
	{
		System.out.println("ComThread: Send>>AW"+str);
		System.out.println("ComThread : Envoi du paquet d'ajout de compte.");
		Ancestra.addToComLog("ComThread: Send>>AW"+str);
		Ancestra.addToComLog("ComThread : Envoi du paquet d'ajout de compte.");
		try
		{
			_out.print("AW"+str+(char)0x00);//AddWaiting
			_out.flush();
			System.out.println("ComThread : Envoi OK.");
		}catch(Exception e)
		{
			System.out.println("ComThreadSendAddWaiting : "+e.getMessage());
			Ancestra.addToErrorLog("ComThreadSendAddWaiting : "+e.getMessage());
			Ancestra.addToComLog("ComThreadSendAddWaiting : "+e.getMessage());
		}
	}
	
	public void sendGetOnline()
	{
		System.out.println("ComThread: Send>>GO");
		System.out.println("ComThread : Envoi du paquet GetOnline ...");
		Ancestra.addToComLog("ComThread: Send>>GO");
		Ancestra.addToComLog("ComThread : Envoi du paquet GetOnline ...");
		try
		{
			_out.print("GO"+(char)0x00);//GetOnline
			_out.flush();
			System.out.println("ComThread : Envoi OK.");
		}catch(Exception e)
		{
			System.out.println("ComThreadSendGetOnline : "+e.getMessage());
			Ancestra.addToComLog("ComThreadSendGetOnline : "+e.getMessage());
			Ancestra.addToErrorLog("ComThreadSendGetOnline : "+e.getMessage());
		}
	}
	
	public void parsePacket(String packet)
	{
		switch (packet.charAt(0))
		{
		case 'G'://Game && Get
			switch (packet.charAt(1))
			{
			case 'A'://Add
				Ancestra.addToComLog("ComThread : Packet GA recu, ajout d'un serveur...");
				System.out.println("ComThread : Packet GA recu, ajout d'un serveur...");
				String key = packet.substring(2);
				Ancestra.addToComLog("ComThread : Serveur KEY : "+key);
				System.out.println("ComThread : Serveur KEY : "+key);
				for(GameServer G : Realm.GameServers.values())
				{
					if(key.equalsIgnoreCase(G.getKey()))
						_server = G;
				}
				if(_server == null)
				{
					kick();
					return;
				}
				_server.setThread(this);
				_server.setState(1);
				Ancestra.addToComLog("ComThread : Serveur OK!");
				System.out.println("ComThread : Serveur OK!");
			break;
			case 'O'://Online
				if (_server == null)
				{
					kick();
					return;
				}
				String[] str = packet.substring(2).split(";");
				_server.set_PlayerLimit(Integer.parseInt(str[0]));
				_server.set_NumPlayer(Integer.parseInt(str[1]));
			break;
			}
		break;
		case 'S'://Server
			if (_server == null)
			{
				kick();
				return;
			}
			switch (packet.charAt(1))
			{
			case 'O'://Open
				Ancestra.addToComLog("ComThread : Packet SO recu, changement d'etat : 1.");
				System.out.println("ComThread : Packet SO recu, changement d'etat : 1.");
				_server.setState(1);
			break;
			case 'S'://Save
				Ancestra.addToComLog("ComThread : Packet SS recu, changement d'etat : 2.");
				System.out.println("ComThread : Packet SS recu, changement d'etat : 2.");
				_server.setState(2);
			break;
			case 'D'://Disconnected
				Ancestra.addToComLog("ComThread : Packet SD recu, changement d'etat : 0.");
				System.out.println("ComThread : Packet SD recu, changement d'etat : 1.");
				_server.setState(0);
			break;
			}
		break;
		case 'R'://RealmThread
			if (_server == null)
			{
				kick();
				return;
			}
			switch (packet.charAt(1))
			{
			case 'G'://GMLEVEL BLOCK, arg : int[level]
				Ancestra.addToComLog("ComThread : Packet RG recu, blocage du serveur au GMlevels < "+Integer.parseInt(packet.substring(2)));
				System.out.println("ComThread : Packet RG recu, blocage du serveur au GMlevels < "+Integer.parseInt(packet.substring(2)));
				_server.setBlockLevel(Integer.parseInt(packet.substring(2)));
			break;
			case 'A'://ADD BANIP, arg : String[ip]
				Ancestra.addToComLog("ComThread : Packet RA recu, ban de l'IP : "+packet.substring(2));
				System.out.println("ComThread : Packet RA recu, ban de l'IP : "+packet.substring(2));
				SQLManager.ADD_BANIP(packet.substring(2));
				Realm.BAN_IP += packet.substring(2)+",";
			break;
			}
		break;
				
		}
		for (Account r : Realm.getAccountsMap().values())
		{
			r.getRealmThread().refresh();
		}
		
		
	}
}