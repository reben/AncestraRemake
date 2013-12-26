package objects;

public class HdvEntry {
		private int _ObjetID;
		private Objet _obj;
		private int _HdvMapID;
		private int _ownerGuid;
		private int _price;
		private int _qua;
		//private int _sellDate;
		
		public HdvEntry(int ObjetID, Objet obj, int HdvMapID, int ownerGuid, int price, int qua/*, int sellDate*/)
		{
			_ObjetID = ObjetID;
			_obj = obj;
			_HdvMapID = HdvMapID;
			_ownerGuid = ownerGuid;
			_price = price;
			_qua = qua;
			//_sellDate = sellDate;
		}
		
		public int get_ObjetID()
		{
			return _ObjetID;
		}
		public Objet get_obj()
		{
			return _obj;
		}
		public int get_HdvMapID()
		{
			return _HdvMapID;
		}
		public int get_ownerGuid()
		{
			return _ownerGuid;
		}
		public int get_price()
		{
			return _price;
		}
		public int get_qua()
		{
			return _qua;
		}/*
		public int get_SellDate()
		{
			return _sellDate;
		}*/
		public String parseToEmK()
		{
			return get_ObjetID()+"|"+get_qua()+"|"+_obj.getTemplate().getID()+"|"+_obj.parseStatsString()+"|"+get_price()+"|350";//350 = temps restant
		}
		public String parseToEL()
		{
			return get_ObjetID()+";"+get_qua()+";"+_obj.getTemplate().getID()+";"+_obj.parseStatsString()+";"+get_price()+";350";//350 = temps restant
		}
	}