package objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import common.Constants;
import common.SQLManager;
import common.SocketManager;
import common.World;

public class PetsEntry {
	
	private int ObjectID;
	private long LastEatDate;
	private int quaEat;
	private int PDV;
	private int Poids;
	private int Corpulence;
	private boolean isEupeoh;
	
	public PetsEntry(int Oid, long LastEatDate, int quaEat, int PDV, int corpulence, boolean isEPO) 
	{
		this.ObjectID = Oid;
		this.LastEatDate = LastEatDate;
		this.quaEat = quaEat;
		this.PDV = PDV;
		this.Corpulence = corpulence;
		get_currentStatsPoids();
		this.isEupeoh = isEPO;
	}
	
	public int get_ObjectID()
	{
		return ObjectID;
	}
	
	public long get_LastEatDate()
	{
		return LastEatDate;
	}
	
	public String parse_LastEatDate()
	{
		String hexDate = "#";
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(this.LastEatDate); 
		
		String[] split = date.split("\\s");
		
		String[] split0 = split[0].split("-");
		hexDate += Integer.toHexString(Integer.parseInt(split0[0]))+"#";
		int mois = Integer.parseInt(split0[1])-1;
		int jour = Integer.parseInt(split0[2]);
		hexDate += Integer.toHexString(Integer.parseInt((mois < 10?"0"+mois:mois)+""+(jour < 10?"0"+jour:jour)))+"#";
		
		String[] split1 = split[1].split(":");
		String heure = split1[0]+split1[1];
		hexDate += Integer.toHexString(Integer.parseInt(heure));
		
		return hexDate;
	}
		
	public int get_quaEat()
	{
		return quaEat;
	}
	
	public int get_PDV()
	{
		return PDV;
	}
	
	public int get_Corpulence()
	{
		return Corpulence;
	}
	
	public boolean get_isEupeoh()
	{
		return isEupeoh;
	}
	
	public int parse_Corpulence()//Pour l'affichage obèse ou maigrichon
	{
		int corpu = 0;
		if(Corpulence > 0 || Corpulence < 0) corpu = 7;
		if(Corpulence == 0) corpu = 0;
		return corpu;
	}
	
	public int get_currentStatsPoids()//Nous donne le poids actuel des stats du pets
	{
		/*
		d6,d5,d4,d3,d2 = 4U de poids
		8a = 2U de poids
		7c = 2U de poids POUR PETIT WABBIT = 3U de poids
		b2 = 8U de poids
		70 = 8U de poids
		le reste a 1U de poids
		 */
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return 0;
		int cumul = 0;
		for(Entry<Integer, Integer> entry : obj.getStats().getMap().entrySet())
		{
			if(entry.getKey() == Integer.parseInt("320", 16)) continue;
			else if(entry.getKey() == Integer.parseInt("326", 16)) continue;
			else if(entry.getKey() == Integer.parseInt("328", 16)) continue;
			else if(entry.getKey() == Integer.parseInt("8a", 16) || entry.getKey() == Integer.parseInt("7c", 16))
				cumul = cumul+(2*entry.getValue());
			else if(entry.getKey() == Integer.parseInt("d2", 16) || entry.getKey() == Integer.parseInt("d3", 16) || entry.getKey() == Integer.parseInt("d4", 16) || entry.getKey() == Integer.parseInt("d5", 16) ||entry.getKey() == Integer.parseInt("d6", 16))
				cumul = cumul+(4*entry.getValue());
			else if(entry.getKey() == Integer.parseInt("b2", 16) || entry.getKey() == Integer.parseInt("70", 16))
				cumul = cumul+(8*entry.getValue());
			else
				cumul = cumul+(1*entry.getValue());
				
		}
		this.Poids = cumul;
		return this.Poids;
	}
	
	public void LooseFight(Personnage p)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = World.get_Pets(obj.getTemplate().getID());
		if(pets == null) return;
		
		this.PDV--;
		obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
		obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString((this.PDV>0?(this.PDV):0)));
		
		if(this.PDV <= 0)
		{
			this.PDV = 0;
			obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
			obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString(0));//Mise a 0 des pdv
			
			if(pets.get_DeadTemplate() == 0)// Si Pets DeadTemplate = 0 remove de l'item et pet entry
			{
				World.removeItem(obj.getGuid());
				p.removeItem(obj.getGuid());
				SocketManager.GAME_SEND_REMOVE_ITEM_PACKET(p, obj.getGuid());
			}else
			{
				obj.set_Template(pets.get_DeadTemplate());
				if(obj.getPosition() == Constants.ITEM_POS_FAMILIER)
				{
					obj.setPosition(Constants.ITEM_POS_NO_EQUIPED);
					SocketManager.GAME_SEND_OBJET_MOVE_PACKET(p,obj);
				}
			}
			SocketManager.GAME_SEND_Im_PACKET(p, "154");
		}
		SocketManager.GAME_SEND_UPDATE_OBJECT_DISPLAY_PACKET(p, obj);
	}
	
	public void Eat(Personnage p, int min, int max, int statsID)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = World.get_Pets(obj.getTemplate().getID());
		if(pets == null) return;
		
		if(this.Corpulence < 0)//Si il est maigrichon (X repas ratés) on peu le nourrire plusieurs fois
		{
			//Update du petsEntry
			this.LastEatDate = System.currentTimeMillis();
			this.Corpulence++;
			//Update de l'item
			obj.getTxtStat().remove(Constants.STATS_PETS_POIDS);
			obj.getTxtStat().put(Constants.STATS_PETS_POIDS, Integer.toHexString(this.Corpulence));
			SocketManager.GAME_SEND_Im_PACKET(p, "029");
		}else if(((this.LastEatDate+(min*3600000)) > System.currentTimeMillis()) && this.Corpulence >= 0)//Si il n'est pas maigrichon, et on le nourri trop rapidement
		{
			//Update du petsEntry
			this.LastEatDate = System.currentTimeMillis();
			this.Corpulence++;
			//Update de l'item
			obj.getTxtStat().remove(Constants.STATS_PETS_POIDS);
			obj.getTxtStat().put(Constants.STATS_PETS_POIDS, Integer.toHexString(this.Corpulence));
			if(Corpulence == 1) SocketManager.GAME_SEND_Im_PACKET(p, "026");
			else
			{
				this.PDV--;
				obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
				obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString((this.PDV>0?(this.PDV):0)));
				SocketManager.GAME_SEND_Im_PACKET(p, "027");
			}
		}else if(((this.LastEatDate+(min*3600000)) < System.currentTimeMillis()) && this.Corpulence >= 0)//Si il n'est pas maigrichon, et que le temps minimal est écoulé
		{
			//Update du petsEntry
			this.LastEatDate = System.currentTimeMillis();
			
			if(statsID != 0) this.quaEat++;
			else return;
			if(this.quaEat >= 3)
			{
				//Update de l'item
				if((get_isEupeoh()==true?pets.get_Max()*1.1:pets.get_Max()) > this.get_currentStatsPoids())//Si il est sous l'emprise d'EPO on augmente de +10% le jet maximum
				{
					if(obj.getStats().getMap().containsKey(statsID))
					{
						int value = obj.getStats().getMap().get(statsID)+1;
						obj.getStats().getMap().remove(statsID);
						obj.getStats().addOneStat(statsID, value);
					}
					else
						obj.getStats().addOneStat(statsID, 1);
				}
				this.quaEat = 0;
			}
			SocketManager.GAME_SEND_Im_PACKET(p, "032");
		}
			
		if(this.PDV <= 0)
		{
			this.PDV = 0;
			obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
			obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString((this.PDV>0?(this.PDV):0)));//Mise a 0 des pdv
			if(pets.get_DeadTemplate() == 0)// Si Pets DeadTemplate = 0 remove de l'item et pet entry
			{
				World.removeItem(obj.getGuid());
				p.removeItem(obj.getGuid());
				SocketManager.GAME_SEND_REMOVE_ITEM_PACKET(p, obj.getGuid());
			}else
			{
				obj.set_Template(pets.get_DeadTemplate());
				
				if(obj.getPosition() == Constants.ITEM_POS_FAMILIER)
				{
					obj.setPosition(Constants.ITEM_POS_NO_EQUIPED);
					SocketManager.GAME_SEND_OBJET_MOVE_PACKET(p,obj);
				}
				SQLManager.SAVE_ITEM(obj);
			}
			SocketManager.GAME_SEND_Im_PACKET(p, "154");
		}
	}
	
	public void EatSouls(Personnage p, Map<Integer, Integer> souls)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pet = World.get_Pets(obj.getTemplate().getID());
		if(pet == null || pet.get_Type() != 1) return;
		//Ajout a l'item les SoulStats tués
		for(Entry<Integer, Integer> entry : souls.entrySet())
		{
			int soul = entry.getKey();
			int count = entry.getValue();
			if(pet.canEat(-1, -1, soul))
			{
				int statsID = pet.statsIDbyEat(-1, -1, soul);
				if(statsID == 0) return;
				if((get_isEupeoh()==true?pet.get_Max()*1.1:pet.get_Max()) > this.get_currentStatsPoids())//Si il est sous l'emprise d'EPO on augmente de +10% le jet maximum
				{
					int soulCount = (obj.getSoulStat().get(soul)!=null?obj.getSoulStat().get(soul):0);
					if(soulCount > 0)//Si existe sur l'item
					{
						obj.getSoulStat().remove(soul);
						obj.getSoulStat().put(soul, count+soulCount);
					}else
					{
						obj.getSoulStat().put(soul, count);
					}
				}
			}
		}
		//Re-Calcul des points gagnés
		for(Entry<Integer, ArrayList<Map<Integer, Integer>>> ent : pet.get_Monsters().entrySet())
		{
				for(Map<Integer, Integer> entry : ent.getValue())
				{
					for(Entry<Integer, Integer> monsterEntry : entry.entrySet())
					{
						if(pet.get_NumbMonster(ent.getKey(), monsterEntry.getKey()) != 0)
						{
							int pts = 0;
							for(Entry<Integer, Integer> list : obj.getSoulStat().entrySet())
							{
								int howIkill = list.getValue();
								int howIneed = pet.get_NumbMonster(ent.getKey(), list.getKey());
								pts += ((int)Math.floor(howIkill/howIneed)*pet.get_Gain());
							}
							if(pts > 0)
							{
								if(obj.getStats().getMap().containsKey(ent.getKey())) obj.getStats().getMap().remove(ent.getKey());
								obj.getStats().getMap().put(ent.getKey(), pts);
							}
						}
					}
				}
		}
		SocketManager.GAME_SEND_UPDATE_OBJECT_DISPLAY_PACKET(p, obj);
	}
	
	public void update_pets(Personnage p, int max)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = World.get_Pets(obj.getTemplate().getID());
		if(pets == null) return;
		if(this.PDV <= 0 && obj.getTemplate().getID() == pets.get_DeadTemplate()) return;//Ne le met pas a jour si deja mort
		
		if(this.LastEatDate+(max*3600000) < System.currentTimeMillis())//Oublier de le nourrir
		{
			//On calcul le nombre de repas oublier arrondi au supérieur :
			int nbrepas = (int) Math.floor((System.currentTimeMillis()-this.LastEatDate)/(max*3600000));
			//Perte corpulence
			this.Corpulence = this.Corpulence - nbrepas;
			
			if(nbrepas != 0) 
			{
				obj.getTxtStat().remove(Constants.STATS_PETS_POIDS);
				obj.getTxtStat().put(Constants.STATS_PETS_POIDS, Integer.toHexString(this.Corpulence));
			}
			//Perte PDV
			this.PDV--;
			obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
			obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString((this.PDV>0?(this.PDV):0)));
			this.LastEatDate = System.currentTimeMillis();
		}else
		{
			if(this.PDV > 0) SocketManager.GAME_SEND_Im_PACKET(p, "025");
		}
		
		if(this.PDV <= 0)
		{
			this.PDV = 0;
			obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
			obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString((this.PDV>0?(this.PDV):0)));
			
			if(pets.get_DeadTemplate() == 0)//Si Pets DeadTemplate = 0 remove de l'item et pet entry
			{
				World.removeItem(obj.getGuid());
				p.removeItem(obj.getGuid());
				SocketManager.GAME_SEND_REMOVE_ITEM_PACKET(p, obj.getGuid());
			}else
			{
				obj.set_Template(pets.get_DeadTemplate());
				if(obj.getPosition() == Constants.ITEM_POS_FAMILIER)
				{
					obj.setPosition(Constants.ITEM_POS_NO_EQUIPED);
					SocketManager.GAME_SEND_OBJET_MOVE_PACKET(p,obj);
				}
				SQLManager.SAVE_ITEM(obj);
			}
			SocketManager.GAME_SEND_Im_PACKET(p, "154");
		}
		SocketManager.GAME_SEND_UPDATE_OBJECT_DISPLAY_PACKET(p, obj);
	}
		
	public void resurrection()
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = null;
		int LiveTemplate = 0;
		
		for(Entry<Integer, Pets> Template : World.get_Pets().entrySet())
		{
			if(Template.getValue().get_DeadTemplate() == obj.getTemplate().getID())
			{
				LiveTemplate = Template.getKey();
				pets = World.get_Pets(LiveTemplate);
			}
		}
		if(LiveTemplate == 0 || pets == null) return;
		obj.set_Template(LiveTemplate);
		
		this.PDV = 10;
		this.Corpulence = 0;
		this.quaEat = 0;
		this.LastEatDate = System.currentTimeMillis();
		
		
		obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
		obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString(this.PDV));
		SQLManager.SAVE_ITEM(obj);
	}
	
	public void RestoreLife(Personnage p)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = World.get_Pets(obj.getTemplate().getID());
		if(pets == null) return;
		
		if(this.PDV >= 10)
		{
			//Il la mange pas de pdv en plus
			SocketManager.GAME_SEND_Im_PACKET(p, "032");
		}else
		if(this.PDV < 10 && this.PDV > 0)
		{
			this.PDV++;
			
			obj.getTxtStat().remove(Constants.STATS_PETS_PDV);
			obj.getTxtStat().put(Constants.STATS_PETS_PDV, Integer.toHexString(this.PDV));
			
			this.LastEatDate = System.currentTimeMillis();
			SocketManager.GAME_SEND_Im_PACKET(p, "032");
		}else
		{
			return;
		}
	}
	
	public void Give_EPO(Personnage p)
	{
		Objet obj = World.getObjet(this.ObjectID);
		if(obj == null) return;
		Pets pets = World.get_Pets(obj.getTemplate().getID());
		if(pets == null) return;
		if(this.isEupeoh) return;
		
		obj.getTxtStat().put(Constants.STATS_PETS_EPO, Integer.toHexString(1));
		
		SocketManager.GAME_SEND_Im_PACKET(p, "032");
		SocketManager.GAME_SEND_UPDATE_OBJECT_DISPLAY_PACKET(p, obj);
	}
}