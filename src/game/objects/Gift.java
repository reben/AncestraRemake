package objects;

import game.GameServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import objects.Objet.ObjTemplate;

import common.World;

public class Gift {

	private int giftId;
	private String title;
	private String description;
	private String pictureUrl;
	private boolean maximizeStat;
	
	private Map<Integer, Integer> items = new HashMap<Integer, Integer>();
		
	public Gift(int giftId, String title,String description, String pictureUrl, String paramItems)
	{
		this.giftId = giftId;
		this.title = title;
		this.description = description;
		this.pictureUrl = pictureUrl;
		
		for(String str : paramItems.split(";"))
		{
			if(!str.contains(","))
			{
				int id = Integer.parseInt(str);
				items.put(id, 1);
			} else
			{
				String[] data  = str.split(",");
				int id = Integer.parseInt(data[0]);
				int qua = Integer.parseInt(data[1]);
				items.put(id, qua);
			}
		}
	}
	
	public String parsePacket()
	{
        StringBuilder packet = new StringBuilder(15+30*items.size());
        packet.append(giftId).append('|').append(getTitleParsed()).append('|').append(getDescriptionParsed()).append('|').append(pictureUrl).append('|');
        int id = 0;
        
        for(Entry<Integer, Integer> entry : items.entrySet()) {
        	id++;
            if(id > 5) break;
            if(id > 1) packet.append(';');
            packet.append(id).append('~');
            packet.append(Integer.toHexString(entry.getKey())).append('~');
            packet.append(Integer.toHexString(entry.getValue())).append("~1~");
            
            ObjTemplate objTemplate = World.getObjTemplate(entry.getKey());
            if(objTemplate == null)
            {
            	GameServer.addToLog("Error GIFT: templateId: "+entry.getKey()+" inexistant.");
            	continue;
            }
            String stats = objTemplate.getStrTemplate();
            if (maximizeStat) stats = objTemplate.generateNewStatsFromTemplate(objTemplate.getStrTemplate(), true).parseToItemSetStats();
            packet.append(stats);
        }
        return packet.toString();
	}
	
	public String getTitleParsed()
	{
		return title.replace(' ', '+');
	}
 
	public String getDescriptionParsed()
	{
		return description.replace(' ', '+');
	}
	
	public int getId()
	{
		return giftId;
	}
	
	public Map<Integer, Integer> getItems()
	{
		return items;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getPicture()
	{
		return pictureUrl;
	}

	public boolean maximizeStat()
	{
		return maximizeStat;
	}

}