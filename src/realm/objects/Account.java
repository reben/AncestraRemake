package objects;

import common.*;
import realm.RealmThread;


public class Account {

	private int _GUID;
	private String _name;
	private String _pass;
	private String _pseudo;
	private String _lastIP = "";
	private String _question;
	private String _reponse;
	private boolean _banned = false;
	private int _gmLvl = 0;
	private int _subscriber = 0;//Timestamp en secondes
	private String _curIP = "";
	private String _lastConnectionDate = "";
	private String _giftID = "";
	
	private RealmThread _realmThread = null;
	
	public Account(int aGUID,String aName,String aPass, String aPseudo,String aQuestion,String aReponse,int aGmLvl, int asubscriber, boolean aBanned, String aLastIp, String aLastConnectionDate, String agiftID)
	{
		this._GUID 					= aGUID;
		this._name 					= aName;
		this._pass					= aPass;
		this._pseudo 				= aPseudo;
		this._question				= aQuestion;
		this._reponse				= aReponse;
		this._gmLvl					= aGmLvl;
		this._subscriber			= asubscriber;
		this._banned				= aBanned;
		this._lastIP				= aLastIp;
		this._lastConnectionDate 	= aLastConnectionDate;
		this._giftID 				= agiftID;
	}
	
	public void setCurIP(String ip)
	{
		_curIP = ip;
	}
	
	public String getLastConnectionDate() 
	{
		return _lastConnectionDate;
	}
	
	public void setLastIP(String _lastip) 
	{
		_lastIP = _lastip;
	}
	
	public String getLastIP()
	{
		return _lastIP;
	}
	
	public void setLastConnectionDate(String connectionDate) 
	{
		_lastConnectionDate = connectionDate;
	}
	
	public void setRealmThread(RealmThread thread)
	{
		_realmThread = thread;
	}
	
	public RealmThread getRealmThread()
	{
		return _realmThread;
	}
	
	public boolean isValidPass(String pass, String hash) 
	{
		return pass.equals(CryptManager.CryptPassword(hash, _pass));
	}
	
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
	
	public int get_subscriberTime()//Renvoi le temps restant
	{
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
			SQLManager.UPDATE_ACCOUNT(_curIP, true, 0, get_GUID());
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
	
	public int get_subscriber()//Renvoi la date limite d'abonnement TimeStamp
	{
		return _subscriber;
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
	
	public String get_curIP() 
	{
		return _curIP;
	}
	
	public void setGmLvl(int gmLvl)
	{
		_gmLvl = gmLvl;
	}

	public String get_giftID()
	{
		return _giftID;
	}

	public void set_giftID(String _giftID)
	{
		this._giftID = _giftID;
	}
}
