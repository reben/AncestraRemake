package objects;

import game.GameThread;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.Timer;
import objects.Others.Bank;

import common.*;

public class Compte {
	
	public static class FriendList
	{
		private ArrayList<String> _pseudos = new ArrayList<String>();
		
		public FriendList(String chain)
		{
			
			try
			{
				String pseudo[] = chain.split(",");
				for (int a = 0; a < pseudo.length; a++)
				{
					_pseudos.add(pseudo[a]);
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		public ArrayList<String> GetFriends()
		{
			return _pseudos;
		}
		
		public String parseFriends()
		{
			String g = "";
			boolean isFirst = true;
			for (String f : _pseudos)
			{
				if(f.isEmpty()) continue;
				if(!isFirst)g+=",";
				g+=f;
				isFirst = false;
			}
			return g;
		}
	}
	
	public static class EnemyList
	{
		private ArrayList<String> _pseudos = new ArrayList<String>();
		
		public EnemyList(String chain)
		{
			
			try
			{
				String pseudo[] = chain.split(",");
				for (int a = 0; a < pseudo.length; a++)
					_pseudos.add(pseudo[a]);
			}
			catch(Exception e)
			{
				
			}
		}
		
		public ArrayList<String> GetEnemys()
		{
			return _pseudos;
		}
		
		public String parseEnemys()
		{
			String g = "";
			boolean isFirst = true;
			for (String f : _pseudos)
			{
				if(f.isEmpty()) continue;
				if(!isFirst)g+=",";
				g+=f;
				isFirst = false;
			}
			return g;
		}
	}
	
	private int _GUID;
	private String _name;
	private String _pass;
	private String _pseudo;
	private String _lastIP = "";
	private String _question;
	private String _reponse;
	private boolean _banned = false;
	private int _gmLvl = 0;
	private int _subscriber = 0;//Time en secondes
	private boolean _subscriberMessage = true;
	private String _curIP = "";
	private String _lastConnectionDate = "";
	private GameThread _gameThread;
	private Personnage _curPerso;
	private boolean _mute = false;
	public Timer _muteTimer;
	private Map<Integer,ArrayList<HdvEntry>> _hdvsItems;// Contient les items des HDV format : <hdvID,<cheapestID>>
	private Map<Integer, Personnage> _persos = new TreeMap<Integer, Personnage>();
	private Bank _bank;
	private FriendList _friends;
	private EnemyList _enemys;
	private String _ClientKey;
	private Map<Integer, Gift> giftList = new TreeMap<Integer, Gift>();
	
	public Compte(int aGUID, String aName, String aPass, String aPseudo, String aQuestion, String aReponse, int aGmLvl, int subscriber, boolean aBanned, String aLastIp, String aLastConnectionDate, String curIP, String gifts)
	{
		this._GUID 					= aGUID;
		this._name 					= aName;
		this._pass					= aPass;
		this._pseudo 				= aPseudo;
		this._question				= aQuestion;
		this._reponse				= aReponse;
		this._gmLvl					= aGmLvl;
		this._subscriber 			= subscriber;
		this._banned				= aBanned;
		this._lastIP				= aLastIp;
		this._lastConnectionDate	= aLastConnectionDate;
		this._hdvsItems 			= World.getMyItems(_GUID);
		this._curIP 				= curIP;
		
		
		this._bank = World.GetBank(this._GUID);
		this._friends = World.GetFriends(this._GUID);
		this._enemys = World.GetEnemys(this._GUID);
		
		if (this._friends == null && this._enemys == null && _bank == null)
		{
			this._bank = new Bank(this._GUID, 0 , "");
			World.AddBank(new Bank(this._GUID, 0, ""));
			
			this._friends = new FriendList("");
			World.AddFriendList(this._GUID, new FriendList(""));
			
			this._enemys = new EnemyList("");
			World.AddEnemyList(this._GUID, new EnemyList(""));
			
			SQLManager.ADD_ACCOUNT_DATA(this._GUID);
		}
		if(!gifts.isEmpty())
		{
			if(gifts.contains(";"))
			{ 
				for(String gift : gifts.split(";"))
				{
					int giftId = Integer.parseInt(gift);
					Gift toAdd = World.getGift(giftId);
					this.giftList.put(giftId, toAdd);
				}
			}else
			{
				int giftId = Integer.parseInt(gifts);
				Gift toAdd = World.getGift(giftId);
				this.giftList.put(giftId, toAdd);
			}
		}
	}
	
	/** Bank **/
	public Map<Integer, Objet> get_bank()
	{
		return _bank.getBankItems();
	}
	
	public Bank getBank()
	{
		return _bank;
	}
	
	public int getBankCost()
	{
		return _bank.getBankItems().size();
	}
	
	public long GetBankKamas()
	{
		return _bank.getBankKamas();
	}
	
	public void setBankKamas(long i)
	{
		_bank.setBankKamas(i);
	}
	
	public String getBankItemsIDSplitByChar(String splitter)
	{
		String str = "";
		
		for (Objet obj : _bank.getBankItems().values())
		{
			str += obj.getGuid() + splitter;
		}
		return str;
	}
	/** Bank **/
	/** Friend/Enemy **/
	public FriendList GetFriends()
	{
		return _friends;
	}
	
	public EnemyList GetEnemys()
	{
		return _enemys;
	}
	
	public String parseFriendList()
	{
		String str = "";
		for (String g : this._friends.GetFriends())
		{
			str += "|" + g;
			if (World.getCompteByPseudo(g) == null)
				continue;
			Personnage P = World.getCompteByPseudo(g).get_curPerso();
			if (P == null)
				continue;
			str += P.parseToFriendList(_GUID);
		}
		return str;
	}
	
	public String parseEnemyList() 
	{
		String str = "";
		for(String g : this._enemys.GetEnemys())
		{
			str += "|" + g;
			if (World.getCompteByPseudo(g) == null)
				continue;
			Personnage P = World.getCompteByPseudo(g).get_curPerso();
			if (P == null)
				continue;
			str += P.parseToEnemyList(_GUID);
		}
		return str;
	}
	
	public void SendOnline()
	{
		for (Compte c : World.getComptes())
		{
			if(c.isFriendWith(this._pseudo) && c.get_curPerso().is_showFriendConnection() && c != this)
				SocketManager.GAME_SEND_FRIEND_ONLINE(this.get_curPerso(), c.get_curPerso());
		}
	}

	public void addFriend(String ps)
	{
		if(ps.compareTo(_pseudo) == 0)
		{
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ey");
			return;
		}
		if(!_friends._pseudos.contains(ps))
		{
			_friends._pseudos.add(ps);
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"K"+ps+World.getCompteByPseudo(ps).get_curPerso().parseToFriendList(_GUID));
			SQLManager.UPDATE_FL_AND_EL(get_GUID(), _friends.parseFriends(), _enemys.parseEnemys());
		}
		else SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ea");
	}
	
	public void addEnemy(String ps)
	{
		if(ps.compareTo(_pseudo) == 0)
		{
			SocketManager.GAME_SEND_FA_PACKET(_curPerso,"Ey");
			return;
		}
		if(!_enemys._pseudos.contains(ps))
		{
			_enemys._pseudos.add(ps);
			SocketManager.GAME_SEND_ADD_ENEMY(_curPerso, ps+World.getCompteByPseudo(ps).get_curPerso().parseToEnemyList(_GUID));
			SQLManager.UPDATE_FL_AND_EL(get_GUID(), _friends.parseFriends(), _enemys.parseEnemys());
		}
		else SocketManager.GAME_SEND_iAEA_PACKET(_curPerso);
	}
	public void removeFriend(String ps)
	{
		if(_friends._pseudos.remove(ps))SQLManager.UPDATE_FL_AND_EL(get_GUID(), _friends.parseFriends(), _enemys.parseEnemys());
		SocketManager.GAME_SEND_FD_PACKET(_curPerso,"K");
	}
	
	public void removeEnemy(String ps)
	{
		if(_enemys._pseudos.remove(ps))SQLManager.UPDATE_FL_AND_EL(get_GUID(), _friends.parseFriends(), _enemys.parseEnemys());
		SocketManager.GAME_SEND_iD_COMMANDE(_curPerso,"K");
	}
	
	public boolean isFriendWith(String ps)
	{
		for (String g : _friends._pseudos)
		{
			if (g.equalsIgnoreCase(ps))
				return true;
		}
		return false;
	}
	
	public boolean isEnemyWith(String ps)
	{
		for (String g : _enemys._pseudos)
		{
			if (g.equalsIgnoreCase(ps))
				return true;
		}
		return false;
	}
	/** Friend/Enemy **/
	/** Mute **/
	public boolean isMuted()
	{
		return _mute;
	}

	public void mute(boolean b, int time)
	{
		_mute = b;
		String msg = "";
		if(_mute)msg = "Vous avez ete mute";
		else msg = "Vous n'etes plus mute";
		SocketManager.GAME_SEND_MESSAGE(_curPerso, msg, Ancestra.CONFIG_MOTD_COLOR);
		if(time == 0)return;
		if(_muteTimer == null && time >0)
		{
			_muteTimer = new Timer(time*1000,new ActionListener()
			{
				public void actionPerformed(ActionEvent arg0)
				{
					mute(false,0);
					_muteTimer.stop();
				}
			});
			_muteTimer.start();
		}else if(time ==0)
		{
			//SI 0 on désactive le Timer (Infinie)
			_muteTimer = null;
		}else
		{
			if (_muteTimer.isRunning()) _muteTimer.stop(); 
			_muteTimer.setInitialDelay(time*1000); 
			_muteTimer.start(); 
		}
	}
	/** Mute **/
	/** HDV **/
	public int countHdvItems(int hdvID)
	{
		if(_hdvsItems.get(hdvID) == null)
			return 0;
		
		return _hdvsItems.get(hdvID).size();
	}
	public HdvEntry[] getHdvItems(int hdvID)
	{
		if(_hdvsItems.get(hdvID) == null)
			return new HdvEntry[1];
		
		HdvEntry[] toReturn = new HdvEntry[20];
		for (int i = 0; i < _hdvsItems.get(hdvID).size(); i++)
		{
			toReturn[i] = _hdvsItems.get(hdvID).get(i);
		}
		return toReturn;
	}
	
	public boolean recoverItem(int itemID, int amount)
	{
		if(_curPerso == null) return false;
		if(_curPerso.get_isTradingWith() >= 0) return false;
		int hdvID = Math.abs(_curPerso.get_isTradingWith());//Récupère l'ID de l'HDV
		HdvEntry entry = null;
		
		for(HdvEntry tempEntry : _hdvsItems.get(hdvID))//Boucle dans la liste d'entry de l'HDV pour trouver un entry avec le meme cheapestID que spécifié
		{
			if(tempEntry.get_ObjetID() == itemID)//Si la boucle trouve un objet avec le meme cheapestID, arrete la boucle
			{
				entry = tempEntry;
				break;
			}
		}
		if(entry == null)//Si entry == null cela veut dire que la boucle s'est effectué sans trouver d'item avec le meme cheapestID
			return false;
		_hdvsItems.get(hdvID).remove(entry);//Retire l'item de la liste des objets a vendre du compte
		
		Objet obj = entry.get_obj();
		if(obj == null)return false;
		boolean OBJ = _curPerso.addObjet(obj,true);//False = Meme item dans l'inventaire donc augmente la qua
		if(!OBJ)
		{
			World.removeItem(obj.getGuid());
		}
		World.removeHdvItem(_curPerso.get_compte().get_GUID(), hdvID, entry);//Retire l'item des ventes
		return true;
	}
	
	/** HDV **/
	public int get_GUID() 
	{
		return _GUID;
	}
	
	public String get_name() 
	{
		return _name;
	}

	public String get_pass() 
	{
		return _pass;
	}

	public String get_pseudo() 
	{
		return _pseudo;
	}
	
	public void setLastIP(String _lastip) 
	{
		_lastIP = _lastip;
	}
	
	public String get_lastIP() 
	{
		return _lastIP;
	}

	public String get_question() 
	{
		return _question;
	}
	
	public String get_reponse() 
	{
		return _reponse;
	}

	public boolean isBanned() 
	{
		return _banned;
	}

	public void setBanned(boolean banned) 
	{
		_banned = banned;
	}
	
	public int get_gmLvl() 
	{
		return _gmLvl;
	}
	
	public void setGmLvl(int gmLvl)
	{
		_gmLvl = gmLvl;
	}
	
	public int get_subscriber()
	{
		//Retourne le temps restant
		if(!Ancestra.USE_SUBSCRIBE) return 525600;
		if(_subscriber == 0)
		{
			//Si non abo ou abo dépasser
			return 0;
		}else
		if((System.currentTimeMillis()/1000) > _subscriber)
		{
			//Il faut désabonner le compte
			_subscriber = 0;
			SQLManager.UPDATE_ACCOUNT_SUBSCRIBE(get_GUID(), 0);
			return 0;
		}else
		{
			//Temps restant
			int TimeRemaining = (int) (_subscriber - (System.currentTimeMillis()/1000));
			//Conversion en minute
			int TimeRemMinute = (int) Math.floor(TimeRemaining/60);
			
			return TimeRemMinute;
		}
	}
	
	public boolean get_subscriberMessage()
	{
		return _subscriberMessage;
	}
	
	public void set_subscriberMessage(boolean b)
	{
		_subscriberMessage = b;
	}
	
	public void setCurIP(String ip)
	{
		_curIP = ip;
	}
	
	public String get_curIP() 
	{
		return _curIP;
	}
	
	public String getLastConnectionDate() 
	{
		return _lastConnectionDate;
	}
	
	public void setLastConnectionDate(String connectionDate) 
	{
		_lastConnectionDate = connectionDate;
	}
	
	public GameThread getGameThread()
	{
		return _gameThread;
	}
	
	public void setGameThread(GameThread t)
	{
		_gameThread = t;
	}
	
	public void setClientKey(String ID)
	{
		_ClientKey = ID;
	}
	
	public String getClientKey()
	{
		return _ClientKey;
	}
	
	public boolean isOnline()
	{
		if(_gameThread != null)return true;
		return false;
	}
	
	public Map<Integer, Personnage> get_persos() 
	{
		return _persos;
	}
	
	public Personnage get_curPerso() 
	{
		return _curPerso;
	}
	
	public int GET_PERSO_NUMBER()
	{
		return _persos.size();
	}
	
	public void addPerso(Personnage perso)
	{
		_persos.put(perso.get_GUID(),perso);
	}
	
	public boolean createPerso(String name, int sexe, int classe,int color1, int color2, int color3)
	{
		
		Personnage perso = Personnage.CREATE_PERSONNAGE(name, sexe, classe, color1, color2, color3, this);
		if(perso==null)
		{
			return false;
		}
		_persos.put(perso.get_GUID(), perso);
		return true;
	}
	
	public void deletePerso(int guid)
	{
		if(!_persos.containsKey(guid))return;
		World.deletePerso(_persos.get(guid));
		_persos.remove(guid);
	}
	
	public void setCurPerso(Personnage perso)
	{
		_curPerso = perso;
	}
	
	public void deconnexion()
	{
		_curPerso = null;
		_gameThread = null;
		_curIP = "";
		resetAllChars(true);
		SQLManager.UPDATE_ACCOUNT_DATA(this);
	}

	public void resetAllChars(boolean save)
	{
		for(Personnage P : _persos.values())
		{
			//Si Echange avec un joueur
			if(P.get_curExchange() != null)P.get_curExchange().cancel();
			//Si en groupe
			if(P.getGroup() != null)P.getGroup().leave(P);
			//Si dans livre
			if(P.is_onCraftBookCrafter())
			{
				P.set_onCraftBookCrafter(false);
				World.removeCrafterOnBook(P.get_GUID());
			}
			//Mettre fin aux demande d'échange
			if(P.get_isTradingWith() > 0)
			{
				Personnage p = World.getPersonnage(P.get_isTradingWith());
				if(p != null)
				{
					if(p.isOnline())
					{
						PrintWriter out = p.get_compte().getGameThread().get_out();
						SocketManager.GAME_SEND_EV_PACKET(out);
						p.set_isTradingWith(0);
					}
				}
			}
			//Mettre fin au demande d'échange craft
			if(P.get_isCraftingWith() != 0)
			{
				Personnage target = World.getPersonnage(P.get_isCraftingWith());
				if(target == null || target.get_isCraftingWith() != P.get_GUID()) return;
				SocketManager.GAME_SEND_EV_PACKET(target.get_compte().getGameThread().get_out());
				target.set_isCraftingWith(0);
				target.set_isCraftingWithskID(0);
			}
			
			//Si en combat
			if(P.get_fight() != null)P.get_fight().leftFight(P, null);
			else//Si hors combat
			{
				P.get_curCell().removePlayer(P.get_GUID());
				if(P.get_curCarte() != null && P.isOnline())SocketManager.GAME_SEND_ERASE_ON_MAP_TO_MAP(P.get_curCarte(), P.get_GUID());
			}
			P.set_Online(false);
			//Reset des vars du perso
			P.resetVars();
			if(save)SQLManager.SAVE_PERSONNAGE(P,true);
			World.unloadPerso(P.get_GUID());
		}
		_persos.clear();
		World.removeAccount(_GUID, get_name().toLowerCase());
	}
	
	public Map<Integer,Gift> getGifts()
	{
	   return giftList;
	}

	public Gift getGift(int id)
	{
	  return giftList.get(id);
	}
	  
	public void addGift(Gift gift)
	{
	  giftList.put(gift.getId(), gift);
	}
	
	public void sendListGift()
	{
		if(giftList.size() > 0)
		{
			for(Gift cgift : giftList.values())
			{
				if(cgift == null) continue;
				SocketManager.GAME_SEND_GIFT(_gameThread.get_out(), cgift.parsePacket());
				break;
			}
		}
	}
	
	public void removeGift(int giftId)
	{
		giftList.remove(giftId);
	}
}
