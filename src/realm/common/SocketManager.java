package common;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import objects.Account;
import objects.GameServer;
import common.Realm;

public class SocketManager
{
	public static void send(PrintWriter out, String packet) 
	{
		if(out != null && !packet.equals("") && !packet.equals(""+(char)0x00))
		{
			packet = CryptManager.toUtf(packet);
			out.print((packet)+(char)0x00);
			out.flush();
			if(Ancestra.REALM_DEBUG)
			{
				Ancestra.addToRealmLog("Realm: Send>>"+packet.toString());
				System.out.println("Realm: Send>>"+packet);
			}
		}
	}
	
	public static void SEND_POLICY_FILE(PrintWriter out) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		packet.append("<cross-domain-policy>");
		packet.append("<allow-access-from domain=\"*\" to-ports=\"*\" secure=\"false\" />");
		packet.append("<site-control permitted-cross-domain-policies=\"master-only\" />");
		packet.append("</cross-domain-policy>");
		send(out, packet.toString());
	}
	
	public static void SEND_REQUIRED_VERSION(PrintWriter out) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AlEv").append(Ancestra.CLIENT_VERSION);
		send(out, packet.toString());
	}
	
	public static void SEND_BANNED(PrintWriter out) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AlEb");
		send(out, packet.toString());
	}
	
	public static void SEND_TOO_MANY_PLAYER_ERROR(PrintWriter out)
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AlEw");
		send(out, packet.toString());
	}
	
	public static String SEND_HC_PACKET(PrintWriter out) 
	{
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		Random rand = new Random();
		
		StringBuilder hashkey = new StringBuilder();
		StringBuilder packet = new StringBuilder();
		for (int i = 0; i < 32; i++) 
		{
			hashkey.append(alphabet.charAt(rand.nextInt(alphabet.length())));
		}
		packet.append("HC").append(hashkey);
		send(out, packet.toString());
		
		return hashkey.toString();
	}
	
	public static void SEND_LOGIN_ERROR(PrintWriter out) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AlEf");
		send(out, packet.toString());
	}
	
	public static void SEND_Af_PACKET(PrintWriter out, int position,
			int totalAbo, int totalNonAbo, int subscribe, int queueID) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("Af").append(position).append("|").append(totalAbo).append("|");
		packet.append(totalNonAbo).append("|").append(subscribe).append("|").append(queueID);
		send(out, packet.toString());
	}
	
	public static void SEND_Ad_Ac_AH_AlK_AQ_PACKETS(PrintWriter out,
			String pseudo, int level, String question, int gmlevel) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("Ad").append(pseudo).append((char)0x00);
		packet.append("Ac0").append((char)0x00);
		ArrayList<GameServer> list = new ArrayList<GameServer>();
		list.addAll(Realm.GameServers.values());
		boolean isFirst  = true;
		for (GameServer G : list)
		{
			if (isFirst)
			{
				packet.append("AH").append(G.getID()).append(";");
				if(G.getBlockLevel() > gmlevel)
				{
					packet.append("0");
				}else
				{
					packet.append(G.getState());
				}
				packet.append(";110;1");
			}
			else
			{
				packet.append("|").append(G.getID()).append(";");
				if(G.getBlockLevel() > gmlevel)
				{
					packet.append("0");
				}else
				{
					packet.append(G.getState());
				}
				packet.append(";110;1");
			}
			isFirst = false;
		}
		packet.append((char)0x00);
		packet.append("AlK").append(level).append((char)0x00);
		packet.append("AQ").append(question.replace(" ", "+"));

		send(out, packet.toString());
	}
	
	public static void SEND_ALREADY_CONNECTED(PrintWriter out) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AlEc");
		send(out, packet.toString());
	}
	
	public static void refresh(PrintWriter out)
	{
		ArrayList<GameServer> list = new ArrayList<GameServer>();
		list.addAll(Realm.GameServers.values());
		StringBuilder packet = new StringBuilder();
		boolean isFirst  = true;
		for (GameServer G : list)
		{
			if (isFirst)
				packet.append("AH").append(G.getID()).append(";").append(G.getState()).append(";110;1");
			else
				packet.append("|").append(G.getID()).append(";").append(G.getState()).append(";110;1");
			isFirst = false;
		}
		send(out, packet.toString());
	}
	
	public static void SEND_PERSO_LIST(PrintWriter out, int subscriber, int number) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("AxK").append((subscriber*60)+"000");//Conversion en millisecondes
		ArrayList<GameServer> list = new ArrayList<GameServer>();
		list.addAll(Realm.GameServers.values());
		
		for (GameServer G : list)
		{
			packet.append("|").append(G.getID()).append(",").append(SQLManager.getNumberPersosOnThisServer(number, G.getID()));
		}
		
		send(out, packet.toString());
	}

	public static void SEND_GAME_SERVER_IP(PrintWriter out, int guid, int server) 
	{
		StringBuilder packet = new StringBuilder();
		packet.append("A");
		GameServer G = Realm.GameServers.get(server);
		if (G == null)
			return;
		
		Account acc = Realm.getCompteByID(guid);
		String str = acc.get_GUID()+"|"+acc.get_name()+"|"+acc.get_pass()+"|"
		+acc.get_pseudo()+"|"+acc.get_question()+"|"+acc.get_reponse()+"|"
		+acc.get_gmLvl()+"|"+acc.get_subscriber()+"|"+(acc.isBanned()?1:0)+"|"
		+acc.getLastIP()+"|"+acc.getLastConnectionDate()+"|"+acc.get_curIP()+"|"+acc.get_giftID();
		G.getThread().sendAddWaiting(str);
		
		packet.append("YK").append(G.getIP()).append(":").append(G.getPort()).append(";").append(guid);
		send(out, packet.toString());
	}
}