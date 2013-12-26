package communication;

import java.io.IOException;
import java.net.ServerSocket;

import common.Ancestra;

public class ComServer implements Runnable {
	
	private ServerSocket _SS;
	private Thread _t;
	
	public ComServer() 
	{
		try
		{
			_SS = new ServerSocket(Ancestra.REALM_COM_PORT);
			_t = new Thread(this);
			_t.setDaemon(true);
			_t.start();
		}catch(IOException e)
		{
			System.out.println("ComServer : "+e.getMessage());
			Ancestra.addToComLog("ComServer : "+e.getMessage());
			Ancestra.addToErrorLog("ComServer : "+e.getMessage());
			Ancestra.closeServers();
		}
	}
	
	public void run() 
	{
		while(Ancestra.isRunning)
		{
			try
			{
				new ComThread(_SS.accept());
			}catch(IOException e)
			{
				try {
					if(!_SS.isClosed()) _SS.close();
					} catch (IOException e1) {}
				System.out.println("ComServerRun : "+e.getMessage());
				Ancestra.addToComLog("ComServerRun : "+e.getMessage());
				Ancestra.addToErrorLog("ComServerRun : "+e.getMessage());
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
			System.out.println("ComServerKickAll : "+e.getMessage());
			Ancestra.addToComLog("ComServerKickAll : "+e.getMessage());
			Ancestra.addToErrorLog("ComServerKickAll : "+e.getMessage());
		}
	}
	
	public Thread getThread() 
	{
		return _t;
	}
}
