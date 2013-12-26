package objects;

public class Hdv {
	
	private int _mapID;
	private String _strCategories;
	private float _sellTaxe;
	private int _lvlMax;
	private int _accountItem;
	private int _sellTime;
	
	public Hdv(int mapid, String categories, float sellTaxe, int lvlMax, int accountItem, int sellTime)
	{
		_mapID = mapid;
		_strCategories = categories;
		_sellTaxe = sellTaxe;
		_lvlMax = lvlMax;
		_accountItem = accountItem;
		_sellTime = sellTime;
	}
	
	public int get_mapID()
	{
		return _mapID;
	}
	public String get_Categories()
	{
		return _strCategories;
	}
	public String get_SellTaxe()
	{
		String Taxe = _sellTaxe+"";
		return Taxe.replace(",", ".");
	}
	public int get_Taxe()
	{
		return (int)_sellTaxe;
	}
	public int get_LvlMax()
	{
		return _lvlMax;
	}
	public int get_AccountItem()
	{
		return _accountItem;
	}
	public int get_SellTime()
	{
		return _sellTime;
	}
}