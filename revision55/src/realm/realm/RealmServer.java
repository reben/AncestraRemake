package realm;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import common.Ancestra;

public class RealmServer implements Runnable {

	private ServerSocket _SS;
	private Thread _t;
	private ArrayList<RealmThread> _clients = new ArrayList<RealmThread>();

	public RealmServer()
	{
		try
		{
			_SS = new ServerSocket(Ancestra.REALM_PORT);
			_t = new Thread(this);
			_t.setDaemon(true);
			_t.start();
		}catch(IOException e)
		{
			System.out.println("RealmServer : "+e.getMessage());
			Ancestra.addToRealmLog("RealmServer : "+e.getMessage());
			Ancestra.addToErrorLog("RealmServer : "+e.getMessage());
			Ancestra.closeServers();
		}
	}

	public void run()
	{
		while(Ancestra.isRunning)
		{
			try
			{
				_clients.add(new RealmThread(_SS.accept()));
			}catch(IOException e)
			{
				try {
					if(!_SS.isClosed()) _SS.close();
					} catch (IOException e1) {}
				System.out.println("RealmServerRun : "+e.getMessage());
				Ancestra.addToRealmLog("RealmServerRun : "+e.getMessage());
				Ancestra.addToErrorLog("RealmServerRun : "+e.getMessage());
			}
		}
	}
	
	public void kickAll()
	{
		try
		{
			_SS.close();
		}catch(Exception e)
		{
			System.out.println("RealmServerKickAll : "+e.getMessage());
			Ancestra.addToRealmLog("RealmServerKickAll : "+e.getMessage());
			Ancestra.addToErrorLog("RealmServerKickAll : "+e.getMessage());
		}
		ArrayList<RealmThread> c = new ArrayList<RealmThread>();
		c.addAll(_clients);
		 for(RealmThread RT : c)
		 {
		 	try
		 	{
		 		RT.closeSocket();
		 	}catch(Exception e){};
	 	}
	}
	
	public void delClient(RealmThread RT)
	{
		_clients.remove(RT);
	}

	public Thread getThread()
	{
		return _t;
	}
}
