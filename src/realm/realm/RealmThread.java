package realm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;

import objects.Account;
import objects.GameServer;

import common.Ancestra;
import common.SQLManager;
import common.SocketManager;
import common.Realm;
import communication.ComServer;
import communication.ComThread;

public class RealmThread implements Runnable {
	private BufferedReader _in;
	private Thread _t;
	private PrintWriter _out;
	private Socket _s;
	private String _hashKey;
	private int _packetNum = 0;
	private String _accountName;
	private String _hashPass;
	private Account _compte;
	public static ComThread exchangeThread;
	public static ComServer exchangeServer;
	

	public RealmThread(Socket sock)
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
			if(_compte != null)
			{
				_compte.setRealmThread(null);
				Realm.deleteAccount(_compte);
			}
		}
	}

	public void run()
	{
		try
		{
			String packet = "";
			char charCur[] = new char[1];
			
			SocketManager.SEND_POLICY_FILE(_out);
			_hashKey = SocketManager.SEND_HC_PACKET(_out);
			
			while(_in.read(charCur, 0, 1) != -1 && Ancestra.isRunning)
			{
				if(charCur[0] != '\u0000' && charCur[0] != '\n' && charCur[0] != '\r')
				{
					packet += charCur[0];
				}else if(!packet.isEmpty())
				{
					Ancestra.addToRealmLog("Realm: Recv << "+packet);
					if(Ancestra.REALM_DEBUG)
					{
						System.out.println("Realm: Recv << "+packet);
						Ancestra.addToRealmLog("Realm: Recv << "+packet);
					}
					_packetNum++;
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
				if(_compte != null)
				{
					SQLManager.UPDATE_ACCOUNT("", false, _compte.get_subscriberTime(), _compte.get_GUID());
					_compte.setRealmThread(null);
					Realm.deleteAccount(_compte);
				}
				if (!_s.isClosed())_s.close();
				_t.interrupt();
			}catch(IOException e1){}
		}finally
		{
			try
			{
				_in.close();
				_out.close();
				if(_compte != null)
				{
					SQLManager.UPDATE_ACCOUNT("", false, _compte.get_subscriberTime(), _compte.get_GUID());
					_compte.setRealmThread(null);
					Realm.deleteAccount(_compte);
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
			Ancestra.realmServer.delClient(this);
			Ancestra.addToRealmLog("Client was kicked by the server.");
			System.out.println("Client was kicked by the server.");
			_in.close();
			_out.close();
			if(_compte != null)
			{
				_compte.setRealmThread(null);
				Realm.deleteAccount(_compte);
			}
			if(!_s.isClosed())_s.close();
			_t.interrupt();
		}catch(IOException e)
		{
			System.out.println("RealmThreadKick : "+e.getMessage());
			Ancestra.addToRealmLog("RealmThreadKick : "+e.getMessage());
			Ancestra.addToErrorLog("RealmThreadKick : "+e.getMessage());
		}
	}
	
	public void closeSocket()
	{
		try {
			this._s.close();
		} catch (IOException e) {}
	}
	
	public void refresh()
	{
		try
		{
			Ancestra.addToRealmLog("RealmThread : Refreshing server list.");
			SocketManager.refresh(_out);
			System.out.println("RealmThread : Refreshing server list.");
		}catch(Exception e)
		{
			System.out.println("RealmThreadRefresh : "+e.getMessage());
			Ancestra.addToRealmLog("RealmThreadRefresh : "+e.getMessage());
			Ancestra.addToErrorLog("RealmThreadRefresh : "+e.getMessage());
		}
	}
	
	private void parsePacket(String packet) 
	{
		switch (_packetNum) 
		{
		case 1:// Version
			if(!packet.equalsIgnoreCase(Ancestra.CLIENT_VERSION) && !Ancestra.REALM_IGNORE_VERSION)
			{
				SocketManager.SEND_REQUIRED_VERSION(_out);
				kick();
			}
		break;
		case 2:// Account Name
			_accountName = packet.toLowerCase();
		break;
		case 3:// HashPass
			if(!packet.substring(0, 2).equalsIgnoreCase("#1"))
			{
				kick();
				return;
			}
			
			_hashPass = packet;
			Account acc = Realm.getCompteByName(_accountName);
			
			if(acc != null && acc.isValidPass(_hashPass, _hashKey))//Si il existe alors il est connecté au Realm && mot de passe OK
			{
				SocketManager.SEND_ALREADY_CONNECTED(acc.getRealmThread()._out);
				SocketManager.SEND_ALREADY_CONNECTED(_out);
				return;
			}
			if(acc != null && !acc.isValidPass(_hashPass, _hashKey))//Si il existe alors il est connecté au Realm && mot de passe Invalide
			{
				SocketManager.SEND_LOGIN_ERROR(_out);
				return;
			}
			
			SQLManager.LOAD_ACCOUNT_BY_USER(_accountName);//On le "charge"
			
			_compte = Realm.getCompteByName(_accountName);
			
			if(_compte == null)//Il n'existe pas
			{
				SocketManager.SEND_LOGIN_ERROR(_out);
				return;
			}
			
			if(!_compte.isValidPass(_hashPass, _hashKey))//Mot de passe invalide
			{
				SocketManager.SEND_LOGIN_ERROR(_out);
				return;
			}
			
			if(_compte.isBanned())//Compte Ban
			{
				SocketManager.SEND_BANNED(_out);
				return;
			}
			
			String ip = _s.getInetAddress().getHostAddress();
			
			if(Realm.IPcompareToBanIP(ip))//IP Ban
			{
				SocketManager.SEND_BANNED(_out);
				return;
			}
			
			for(Entry<Integer, GameServer> G : Realm.GameServers.entrySet())//On vérifie qu'il n'est pas connecté dans un GameThread
			{
				if(G.getValue().getThread() == null) continue;
				G.getValue().getThread().sendDeco(_compte.get_GUID());//On le déconnete du GameThread
			}
			
			if(_compte.getRealmThread() != null)//Ne devrait pas arriver
			{
				SocketManager.SEND_ALREADY_CONNECTED(_out);
				SocketManager.SEND_ALREADY_CONNECTED(_compte.getRealmThread()._out);
				return;
			}
			
			_compte.setRealmThread(this);
			_compte.setCurIP(ip);
			
			SQLManager.UPDATE_ACCOUNT(ip, false, _compte.get_subscriberTime(), _compte.get_GUID());
			
			SocketManager.SEND_Ad_Ac_AH_AlK_AQ_PACKETS(_out, _compte.get_pseudo(), (_compte.get_gmLvl() > 0 ? (1) : (0)), _compte.get_question(), _compte.get_gmLvl());
		break;
		default:
			String ip2 = _s.getInetAddress().getHostAddress();
			if(packet.substring(0, 2).equals("Af"))
			{
				int queueID = 1;
				int position = 1;
				_packetNum--;
				SocketManager.SEND_Af_PACKET(_out, position, 1, 1, 0, queueID);
			}else
			if(packet.substring(0, 2).equals("Ax"))
			{
				if (_compte == null)return;
				SocketManager.SEND_PERSO_LIST(_out, _compte.get_subscriberTime(), _compte.get_GUID());
			}else
			if(packet.substring(0, 2).equals("AX"))
			{
				int number = Integer.parseInt(packet.substring(2,3));
				Realm.GameServers.get(number).getThread().sendGetOnline();
				
				try
				{
					Thread.sleep(2000);
				}catch(Exception e){}
				
				int ActualP = Realm.GameServers.get(number).get_NumPlayer();
				int MaxP = Realm.GameServers.get(number).get_PlayerLimit();
				
				if(ActualP >= MaxP)
				{
					SocketManager.SEND_TOO_MANY_PLAYER_ERROR(_out);
					return;
				}
				System.out.println("RealmThreadOUT : Connexion to the server with the following ip:" +ip2);
				Ancestra.addToRealmLog("RealmThreadOUT : Connexion to the server with the following ip:" +ip2);
				SocketManager.SEND_GAME_SERVER_IP(_out, _compte.get_GUID(), number);
			}else
			if(packet.substring(0, 2).equals("AF"))
			{
				//TODO
				@SuppressWarnings("unused")
				String name = packet.substring(2);
			}
		break;
		}
	}
}