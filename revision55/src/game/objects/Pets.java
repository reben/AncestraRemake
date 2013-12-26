package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import common.Constants;

public class Pets {
	
	private int TemplateID;
	private int Type;//0 ne mange rien, 1 mange des creatures, 2 mange des objets, 3 mange un groupe d'objet.
	private String Gap;//En heure 5,72 si Type = 2 ou 3
	private String StatsUp;
	private int Max;
	private int Gain;
	private int DeadTemplate;
	private Map<Integer, ArrayList<Integer>> Categ = new TreeMap<Integer, ArrayList<Integer>>();// si type 3 StatID|categID#categID;StatID2| ...
	private Map<Integer, ArrayList<Integer>> Template = new TreeMap<Integer, ArrayList<Integer>>();// si type 2 StatID|TemplateID#TemplateID#;StatID2| ...
	private Map<Integer, ArrayList<Map<Integer, Integer>>> Monster = new HashMap<Integer, ArrayList<Map<Integer, Integer>>>();// si type 1 StatID|monsterID,qua#monsterID,qua;StatID2|monsterID,qua#monsterID,qua ...
	
	
	public Pets(int Tid, int type, String gap, String StatsUp, int max, int Gain, int Dtemplate) 
	{
		this.TemplateID = Tid;
		this.Type = type;
		this.Gap = gap;
		this.StatsUp = StatsUp;
		DecompileStatsUpItem();
		this.Max = max;
		this.Gain = Gain;
		this.DeadTemplate = Dtemplate;
	}
	
	public int get_Tid()
	{
		return TemplateID;
	}
	
	public int get_Type()
	{
		return Type;
	}
	
	public String get_Gap()
	{
		return Gap;
	}
	
	public String get_StatsUp()
	{
		return StatsUp;
	}
	
	public int get_Max()
	{
		return Max;
	}
	
	public int get_Gain()
	{
		return Gain;
	}
	
	public int get_DeadTemplate()
	{
		return DeadTemplate;
	}
	
	public Map<Integer, ArrayList<Map<Integer, Integer>>> get_Monsters()
	{
		return Monster;
	}
	
	public int get_NumbMonster(int StatID, int MonsterID)
	{
		for(Entry<Integer, ArrayList<Map<Integer, Integer>>> ID : Monster.entrySet())
		{
			if(ID.getKey() == StatID)
			{
				for(Map<Integer, Integer> entry : ID.getValue())
				{
					for(Entry<Integer, Integer> monsterEntry : entry.entrySet())
					{
						if(monsterEntry.getKey() == MonsterID)
						{
							return monsterEntry.getValue();
						}
					}
				}
			}
		}
		return 0;
	}
	
	public void DecompileStatsUpItem()
	{
		if(Type == 3 || Type == 2)
		{
			if(StatsUp.contains(";"))//Plusieurs stats
			{
				for(String cut : StatsUp.split(";"))//On coupe b2|41#49#62 puis 70|63#64
				{
					String[] cut2 = cut.split("\\|");
					int statsID = Integer.parseInt(cut2[0], 16);
					ArrayList<Integer> ar = new ArrayList<Integer>();
					
					for(String categ : cut2[1].split("#"))
					{
						int categID = Integer.parseInt(categ);
						ar.add(categID);
					}
					if(Type == 3) Categ.put(statsID, ar);
					if(Type == 2) Template.put(statsID, ar);
				}
				
			}else//Un seul stats
			{
				String[] cut2 = StatsUp.split("\\|");//On coupe b2 puis 41#49#62
				int statsID = Integer.parseInt(cut2[0], 16);
				ArrayList<Integer> ar = new ArrayList<Integer>();
				for(String categ : cut2[1].split("#"))
				{
					int categID = Integer.parseInt(categ);
					ar.add(categID);
				}
				if(Type == 3) Categ.put(statsID, ar);
				if(Type == 2) Template.put(statsID, ar);
			}
		}else
		if(Type == 1) //StatID|monsterID,qua#monsterID,qua;StatID2|monsterID,qua#monsterID,qua
		{
			if(StatsUp.contains(";"))//Plusieurs stats
			{
				for(String cut : StatsUp.split(";"))//On coupe
				{
					String[] cut2 = cut.split("\\|");
					int statsID = Integer.parseInt(cut2[0], 16);
					ArrayList<Map<Integer, Integer>> ar = new ArrayList<Map<Integer, Integer>>();
					for(String soustotal : cut2[1].split("#"))
					{
						int MonsterID = 0;
						int qua = 0;
						for(String Iqua : soustotal.split(","))
						{
							if(MonsterID == 0)
							{
								MonsterID = Integer.parseInt(Iqua);
							}else
							{
								qua = Integer.parseInt(Iqua);
								Map<Integer, Integer> Mqua = new TreeMap<Integer, Integer>();
								Mqua.put(MonsterID, qua);
								ar.add(Mqua);
								Monster.put(statsID, ar);
								MonsterID = 0;
							}
						}
					}
				}
			}else//Un seul stats 8a|64,50#65,50#68,50#72,50#96,50#97,40#99,40#179,40#182,10#181,10#180,1
			{
				String[] cut2 = StatsUp.split("\\|");//On coupe 8a puis 64,50#65,50#68,50#72,50#96,50#97,40#99,40#179,40#182,10#181,10#180,1
				int statsID = Integer.parseInt(cut2[0], 16);
				ArrayList<Map<Integer, Integer>> ar = new ArrayList<Map<Integer, Integer>>();
				for(String categ : cut2[1].split("#"))
				{
					int MonsterID = 0;
					int qua = 0;
					for(String Iqua : categ.split(","))
					{
						if(MonsterID == 0)
						{
							MonsterID = Integer.parseInt(Iqua);
						}else
						{
							qua = Integer.parseInt(Iqua);
							Map<Integer, Integer> Mqua = new TreeMap<Integer, Integer>();
							Mqua.put(MonsterID, qua);
							ar.add(Mqua);
							Monster.put(statsID, ar);
						}
					}
				}
			}
		}
	}
	
	public boolean canEat(int Tid, int categID, int monsterId)
	{
		if(Type == 1)
		{
			for(Entry<Integer, ArrayList<Map<Integer, Integer>>> ID : Monster.entrySet())
			{
				for(Map<Integer, Integer> entry : ID.getValue())
				{
					for(Entry<Integer, Integer> monsterEntry : entry.entrySet())
					{
						if(monsterEntry.getKey() == monsterId)
						{
							return true;
						}
					}
				}
			}
			return false;
		}else
		if(Type == 2)
		{
			for(Entry<Integer, ArrayList<Integer>> ID : Template.entrySet())
			{
				if(ID.getValue().contains(Tid))
				{
					return true;
				}
			}
			return false;
		}else
		if(Type == 3)
		{
			for(Entry<Integer, ArrayList<Integer>> ID : Categ.entrySet())
			{
				if(ID.getValue().contains(categID))
				{
					return true;
				}
			}
			return false;
		}else
		{
			return false;
		}
	}
	
	public int statsIDbyEat(int Tid, int categID, int monsterId)
	{
		if(Type == 1) 
		{
			for(Entry<Integer, ArrayList<Map<Integer, Integer>>> ID : Monster.entrySet())
			{
				for(Map<Integer, Integer> entry : ID.getValue())
				{
					for(Entry<Integer, Integer> monsterEntry : entry.entrySet())
					{
						if(monsterEntry.getKey() == monsterId)
						{
							return ID.getKey();
						}
					}
				}
			}
			return 0;
		}else
		if(Type == 2)
		{
			for(Entry<Integer, ArrayList<Integer>> ID : Template.entrySet())
			{
				if(ID.getValue().contains(Tid)) return ID.getKey();
			}
			return 0;
		}else
		if(Type == 3)
		{
			for(Entry<Integer, ArrayList<Integer>> ID : Categ.entrySet())
			{
				if(ID.getValue().contains(categID)) return ID.getKey();
			}
			return 0;
		}else
		{
			return 0;
		}
	}
	
	public Map<Integer, String> generateNewtxtStatsForPets()
	{
		Map<Integer, String> txtStat = new TreeMap<Integer, String>();
		txtStat.put(Constants.STATS_PETS_PDV, "a");
		txtStat.put(Constants.STATS_PETS_DATE, "0");
		txtStat.put(Constants.STATS_PETS_POIDS, "0");
		return txtStat;
	}
}