package objects;

import java.util.Map;
import java.util.TreeMap;

import common.World;

public class Others
{
	public static class Bank{
		private int guid;
		private long bankKamas;
		private Map<Integer, Objet> bankItems = new TreeMap<Integer, Objet>();
		
		public Bank(int guid, int bankKamas, String items)
		{
			this.guid = guid;
			this.bankKamas = bankKamas;
			for (int a = 0; a < items.split(",").length ; a++)
			{
				Objet obj = null;
				try{
				obj = World.getObjet(Integer.parseInt(items.split(",")[a]));
				}catch(Exception e)
				{
					
				}
				if (obj == null)
					continue;
				bankItems.put(obj.getGuid(), obj);
			}
		}
		
		public void addBankKamas(int k)
		{
			this.bankKamas += k;
		}
		
		public void setBankKamas(long i)
		{
			this.bankKamas = i;
		}
		
		public void addBankItem(Objet obj)
		{
			this.bankItems.put(obj.getGuid(), obj);
		}
		
		public String parseBankItems()
		{
			String g = "";
			boolean isFirst = true;
			for (Objet obj : bankItems.values())
			{
				if(!isFirst) g+= ",";
				g+= obj.getGuid();
				isFirst = false;
			}
			return g;
		}
		
		public long getBankKamas()
		{
			return this.bankKamas;
		}
		
		public int getGuid()
		{
			return this.guid;
		}
		
		public Map<Integer, Objet> getBankItems()
		{
			return this.bankItems;
		}
	}
}