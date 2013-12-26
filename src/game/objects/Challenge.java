package objects;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import common.Pathfinding;
import common.SocketManager;

import objects.Fight.Fighter;

public class Challenge
{
	private int type;
	private Fight fight;
	private Fighter _cible;
	private List<Fight.Fighter> _ordreJeu = new ArrayList<Fighter>();
	
	private int xpWin;
	private int dropWin;
	private boolean challengeAlive = false;
	private boolean challengeWin = false;
	private String Args = new String();
	private int Arg = -1;
	private long lastActions_time;
	private String lastActions;
	
	public Challenge(Fight fight, int type, int xp, int drop)
	{
	    this.challengeAlive = true;
	    this.fight = fight;
	    this.type = type;
	    this.xpWin = xp;
	    this.dropWin = drop;
	    
	    this._ordreJeu.clear();
	    this._ordreJeu.addAll(fight.get_ordreJeu());
	    
	    this.lastActions = "";
	    this.lastActions_time = System.currentTimeMillis();
	    
	}
	
	public int getXp()
	{
		return xpWin;
	}
	
	public int getDrop()
	{
		return dropWin;
	}
	
	public boolean get_win()
	{
		return challengeWin;
	}
	
	public void challenge_win()
	{
		challengeWin = true;
		challengeAlive = false;
		SocketManager.GAME_SEND_CHALLENGE_FIGHT(fight, 1, "OK" + type);
	}
	
	public void challenge_loose(Fighter fighter)
	{
		String name = "";
		if(fighter != null && fighter.getPersonnage() != null) name = fighter.getPersonnage().get_name();
		challengeWin = false;
		challengeAlive = false;
		SocketManager.GAME_SEND_CHALLENGE_FIGHT(fight, 7, "KO"+type);
	    SocketManager.GAME_SEND_Im_PACKET_TO_CHALLENGE(fight, 1, "0188;"+name);
	}
	
	public String parsePacket()
	{
		StringBuilder packet = new StringBuilder();
		packet.append(type).append(";").append(_cible != null ? "1":"0").append(";").append(_cible != null ? (Integer.valueOf(_cible.getGUID())) : "").append(";").append(xpWin).append(";0;").append(dropWin).append(";0;");
		if(!challengeAlive)
		{
			if(challengeWin) packet.append("").append(type);
			else packet.append("").append(type);
		}
		return packet.toString();
	}
	
	public void show_cibleToPerso(Personnage p)
	{
		if(!challengeAlive || _cible == null || _cible.get_fightCell() == null || p == null) return;
		
		ArrayList<PrintWriter> Pws = new ArrayList<PrintWriter>();
		Pws.add(p.get_compte().getGameThread().get_out());
		SocketManager.GAME_SEND_FIGHT_SHOW_CASE(Pws, _cible.getGUID(), _cible.get_fightCell().getID());
	}
	
	public void show_cibleToFight()
	{
		if(!challengeAlive || _cible == null || _cible.get_fightCell() == null) return;
		
		ArrayList<PrintWriter> Pws = new ArrayList<PrintWriter>();
		for(Fighter fighter : fight.getFighters(1))
		{
			if(fighter.hasLeft())continue;
			if(fighter.getPersonnage() == null || ! fighter.getPersonnage().isOnline())continue;
			Pws.add(fighter.getPersonnage().get_compte().getGameThread().get_out());
		}
		SocketManager.GAME_SEND_FIGHT_SHOW_CASE(Pws, _cible.getGUID(), _cible.get_fightCell().getID());
	}
	
	public void fightStart()//Définit les cibles au début du combat
	{
		if(!challengeAlive) return;
	
		switch (type)
		{
			case 3://Désigné Volontaire
			case 4://Sursis
			case 32://Elitiste
			case 35://Tueur à gages
			    if(_cible == null && _ordreJeu.size() > 0)//Si aucun cible n'est choise on en choisie une
			    {
	    			List<Fighter> Choix = new ArrayList<Fighter>();
	    			Choix.addAll(_ordreJeu);
	    			Collections.shuffle(Choix);//Mélange l'ArrayList
			    	for(Fighter f : Choix)
			    	{
			    		if(f.getPersonnage() != null) continue;
			    		if(f.getMob() != null && f.getTeam2() == 2 && !f.isDead()) _cible = f;
			    	}
			    }
			    show_cibleToFight();//On le montre a tous les joueurs
		    break;
			case 10://Cruel
	    		int levelMin = 2000;
	    		for(Fighter fighter : fight.getFighters(2))//La cible sera le niveau le plus faible
	    		{
	    			if(fighter.getPersonnage() == null && fighter.getMob() != null && fighter.get_lvl() < levelMin)
	    			{
	    				levelMin = fighter.get_lvl();
	    				_cible = fighter;
	    			}
	    		}
		  		if(_cible != null) show_cibleToFight();
		  	break;
			case 25://Ordonné
				int levelMax = 0;
		  		for(Fighter fighter : fight.getFighters(2))//la cible sera le niveau le plus élevé
		  		{
		  			if(fighter.getPersonnage() == null && fighter.getMob() != null && fighter.get_lvl() > levelMax)
		  			{
		  				levelMax = fighter.get_lvl();
		  				this._cible = fighter;
		  			}
		  		}
		   		if(_cible != null) show_cibleToFight();
		   	break;
		}
	}
	
	public void fightEnd()//Vérifie la validité des challenges en fin de combat (si nécessaire)
	{
		if(!challengeAlive) return;
		 
		switch(type)
		{
		 	case 44://Partage
		  	case 46://Chacun son monstre
			  for(Fighter fighter : fight.getFighters(1))
			  {
				  if(!Args.contains(";"+fighter.getGUID()+";"))
				  {
					  challenge_loose(fighter);
					  break;
				  }
			  }
			break;
		}
		challenge_win();
	}
	  
	public void fighterDie(Fighter Deadtarget)//Si un Fighter meurt on vérifie la validité des challenges
	{
		if(!challengeAlive) return;
		
		switch (type)
		{
			case 33://Survivant
				challenge_loose(fight.getCurFighter());
			break;			
			case 49://Protégez vos mules
				if(Deadtarget.getPersonnage() != null)
				{
					int levelMin = 2000;
					for(Fighter f : fight.getFighters(1))
					{
						if(f.get_lvl() < levelMin) levelMin = f.get_lvl();
						if(Deadtarget.get_lvl() <= levelMin)
						{
							challenge_loose(fight.getCurFighter()); 
							break;
						}
					}
				}
			break;
		}
	}
	
	public void onFighters_attacked(ArrayList<Fighter> targets, Fighter caster, int effectID)//Si on attaque un fighter (soins ou attaque)
	{
		if(!challengeAlive) return;
		String DamagingEffects = "|82|85|86|87|88|89|91|92|93|94|95|96|97|98|99|100|141|";
		String HealingEffects = "|108|";
	 	String MPEffects = "|77|127|";
	 	String APEffects = "|84|101|";
	 	String OPEffects = "|116|320|";
	 	int eID = effectID;
	 	switch(type)
	 	{
	 		case 17://Intouchable
				if(DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 0 && !target.isInvocation())
						{
							challenge_loose(caster);
							break;
						}
					}
				}
			break;
			case 18://Incurable
				if((caster.getTeam() == 0) && !caster.isInvocation() && HealingEffects.contains("|"+effectID+"|"))
				{
					challenge_loose(caster);
					break;
				}
			break;
			case 19://Mains propres
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1 && !target.isInvocation())
						{
							challenge_loose(caster);
							break;
						}
					}
				}
			break;
			case 20://Elémentaire
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|") && effectID != 141)
				{
					eID -= 96;
					if(eID >= 0)
					{
						if(Arg == -1)
						{
							Arg = eID;
						}else
						if(Arg != eID)
						{
							challenge_loose(caster);
						}
					}else
					{
						eID += 5;
					}
					if(eID >= 0)
					{
						if(Arg == -1)
						{
							Arg = eID;
						}else
						if(Arg != eID)
						{
							challenge_loose(caster);
						}
					}else
					{
						eID += 6;
					}
					if(eID >= 0)
					{
						if(Arg == -1)
						{
							Arg = eID;
						}else
						if(Arg != eID)
						{
							challenge_loose(caster);
						}
					}
				}			
			break;
			case 21://Circulez !
				if((caster.getTeam() == 0) && MPEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							challenge_loose(caster);
							break;
						}
					}
				}
			break;
			case 22://Le temps qui court !
				if((caster.getTeam() == 0) && APEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							challenge_loose(caster);
							break;
						}
					}
				}
				break;
			case 23://Perdu de vue !
				if((caster.getTeam() == 0) && OPEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							challenge_loose(caster);
							break;
						}
					}
				}
			break;
			case 31://Focus
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							if(Args.isEmpty()) Args += ""+target.getGUID();
							else if(!Args.contains(""+target.getGUID())) challenge_loose(caster);
						}
					}
				}
			break;
			case 32://Elitiste
			case 34://Imprévisible
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{	
						if(target.getTeam() == 1)
						{
							if(_cible == null || _cible.getGUID() != target.getGUID()) challenge_loose(caster);
						}
					}
				}
			break;
			case 38://Blitzkrieg
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							StringBuilder ID = new StringBuilder();
							ID.append(";").append(target.getGUID()).append(",");
							if(!Args.contains(ID.toString()))
							{
								ID.append(caster.getGUID());
								Args += ID.toString();
							}
						}
					}
				}
			case 43://Abnégation
				if((caster.getTeam() == 0) && HealingEffects.contains("|"+effectID+"|") && caster.getInvocator() == null)
				{
					for(Fighter target : targets)
					{
						if(target.getGUID() == caster.getGUID()) challenge_loose(caster);
					}
				}
			break;
			case 45://Duel
			case 46://Chacun son monstre
				if((caster.getTeam() == 0) && DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 1)
						{
							if(!Args.contains(";"+target.getGUID()+",")) Args += ";"+target.getGUID()+","+caster.getGUID()+";";
							else if(Args.contains(";"+target.getGUID()+",") && !Args.contains(";"+target.getGUID()+","+caster.getGUID()+";")) challenge_loose(target);
						}
					}
				}
			break;
			case 47://Contamination
				if(DamagingEffects.contains("|"+effectID+"|"))
				{
					for(Fighter target : targets)
					{
						if(target.getTeam() == 0)
						{
							if(!Args.contains(";"+target.getGUID()+",")) Args += ";"+target.getGUID()+","+"3;";
						}
					}
				}
			break;
		  }
	  }
	
	public void onMob_die(Fighter mob, Fighter caster)
	{
		if(!challengeAlive) return;
		if(caster == null)
		{
			challenge_loose(null);
			return;
		}
		
		boolean isKiller = (caster.getGUID() == mob.getGUID() ? false : true);
		
		switch (type)
		{
			case 3://Désigné Volontaire
				if(_cible == null) return;
				if (_cible.getGUID() != mob.getGUID())
				{
					challenge_loose(fight.getCurFighter());
				}else
				{
					challenge_win();
				}
				_cible = null;
			break;//Sursis
			case 4:
				if(_cible == null) return;
				if(_cible.getGUID() == mob.getGUID() && !fight.verifIfTeamIsDead())
				{
					challenge_loose(fight.getCurFighter());
				}
			break;
			case 28://Ni Pioutes ni Soumises
				if(isKiller && caster.getPersonnage() != null)
				{
					if(caster.getPersonnage().get_sexe() == 0)
					{
						challenge_loose(fight.getCurFighter());
					}
				}
			break;
			case 29://Ni Pioutes ni Soumises
				if(isKiller && caster.getPersonnage() != null)
				{
					if(caster.getPersonnage().get_sexe() == 1)
					{
						challenge_loose(fight.getCurFighter());
					}
				}
			break;
			case 31:// Focus
				if(Args.contains(""+mob.getGUID())) Args = "";
				else challenge_loose(caster);
			break;
			case 32://Elitiste
				if(_cible == null) return;
				if(_cible.getGUID() == mob.getGUID()) challenge_win();
			break;
			case 34://Imprévisible
				_cible = null;
			case 42://Deux pour le prix d'un
			case 44://Partage
			case 46://Chacun son monstre
				if(isKiller) Args += ";"+caster.getGUID()+";";		
			break;
			case 30://Les petits d'abord
			case 48://Les mules d'abord
				if(isKiller)
				{
					int levelMin = 2000;
					for(Fighter f : fight.getFighters(1))
					{
						if(f.get_lvl() < levelMin) levelMin = f.get_lvl();
						if(caster.get_lvl() > levelMin)
						{
							challenge_loose(fight.getCurFighter());
						}
					}
				}
			break;
			case 35://Tueur à gages
		    	if(_cible == null) return;
				if(_cible.getGUID() != mob.getGUID())
				{
					challenge_loose(fight.getCurFighter());
				}
				_cible = null;
			    if(_cible == null && _ordreJeu.size() > 0)//Choix d'une nouvelle cible
			    {
	    			List<Fighter> Choix = new ArrayList<Fighter>();
	    			Choix.addAll(_ordreJeu);
	    			Collections.shuffle(Choix);//Mélange l'ArrayList
			    	for(Fighter f : Choix)
			    	{
			    		if(f.getPersonnage() != null) continue;
			    		if(f.getMob() != null && f.getTeam2() == 2 && !f.isDead()) _cible = f;
			    	}
			    }
			    show_cibleToFight();
		    break;
		    case 10://Cruel
		    	if(_cible == null) return;
				if(_cible.getGUID() != mob.getGUID())
				{
					challenge_loose(fight.getCurFighter());
				}
		    	
				int levelMin = 2000;
		    	for(Fighter fighter : fight.getFighters(2))
		    	{
		    		if(fighter.isDead()) continue;
		    		if(fighter.getPersonnage() == null && fighter.get_lvl() < levelMin)
		    		{
		    			levelMin = fighter.get_lvl();
		    			_cible = fighter;
		    		}
		    	}
		    	if(_cible != null) show_cibleToFight();
		    break;
		    case 25://Ordonné
		    	if (_cible == null) return;
				if (_cible.getGUID() != mob.getGUID())
				{
					challenge_loose(fight.getCurFighter());
				}
				
		    	int levelMax = 0;
		    	for(Fighter fighter : fight.getFighters(2))
		    	{
		    		if(fighter.isDead()) continue;
		    		if(fighter.getPersonnage() == null && fighter.get_lvl() > levelMax)
		    		{
		    			levelMax = fighter.get_lvl();
		    			this._cible = fighter;
		    		}
		    	}
		    	if(_cible != null) show_cibleToFight();
		    break;
		}
	}
	  
	public void onPlayer_move(Fighter f)
	{
		if(!challengeAlive) return;
		switch(type)
		{
			case 1://Zombie
				if((f.getPM() - f.getCurPM(fight)) > 1)
				{
					challenge_loose(fight.getCurFighter());
				}
			break;
		}
	}
	  
	public void onPlayer_action(Fighter fighter, int actionID)
	{
		if(!challengeAlive || fighter == null || fighter.getTeam() == 1) return;
		if(System.currentTimeMillis() - lastActions_time < 500) return;
			
		lastActions_time = System.currentTimeMillis();
		StringBuilder action = new StringBuilder();
		action.append(";").append(fighter.getGUID());
		action.append(",").append(actionID).append(";");
		
		switch(type)
		{
			case 6://Versatile
			case 5://Econome
				if(lastActions.contains(action.toString())) challenge_loose(fight.getCurFighter());
				lastActions += action.toString();
			break;
			case 24://Borné
				if(!lastActions.contains(action.toString()) && lastActions.contains(";"+fighter.getGUID()+",")) challenge_loose(fight.getCurFighter());
				lastActions += action.toString();
			break;
		  }
		  return;
	  }
	
	public void onPlayer_cac(Fighter fighter)
	{
		if(!challengeAlive) return;
		
		switch(type)
		{
			case 11://Mystique
				challenge_loose(fight.getCurFighter());
			break;
			case 6://Versatile
			case 5://Econome
				if(System.currentTimeMillis() - lastActions_time < 500) return;
				lastActions_time = System.currentTimeMillis();
				StringBuilder action = new StringBuilder();
				action.append(";").append(fighter.getGUID());
				action.append(",").append("cac").append(";");
				if(lastActions.contains(action.toString())) challenge_loose(fight.getCurFighter());
				lastActions += action.toString();
			break;		
		  }
	  }
	
	public void onPlayer_spell(Fighter fighter)
	{
		if(!challengeAlive) return;
		switch(type)
		{
			case 9://Barbare
				challenge_loose(fight.getCurFighter());
			break;
		}
	}
	
	public void onfight_StartTurn(Fighter fighter)
	{
		if(!challengeAlive) return;
		
		switch(type)
		{
			case 2://Statue
				Arg = fighter.get_fightCell().getID();
			break;
			case 6://Versatile
				lastActions = "";
			break;
			case 34://Imprévisible
				if(fighter.getTeam() == 1) return;
				_cible = null;
			    if(_cible == null && _ordreJeu.size() > 0)//Si aucun cible n'est choise on en choisie une
			    {
	    			List<Fighter> Choix = new ArrayList<Fighter>();
	    			Choix.addAll(_ordreJeu);
	    			Collections.shuffle(Choix);//Mélange l'ArrayList
			    	for(Fighter f : Choix)
			    	{
			    		if(f.getPersonnage() != null) continue;
			    		if(f.getMob() != null && f.getTeam2() == 2 && !f.isDead()) _cible = f;
			    	}
			    }
			    show_cibleToFight();
		    break;
			case 38://Blitzkrieg
				if(fighter.getTeam() == 1 && Args.contains(";"+fighter.getGUID()+","))
				{
					String[] str = Args.split(";");
					int fighterID = 0;
					for(String string : str)
					{
						if(string.contains(""+fighter.getGUID()))
						{
							for(String test : string.split(","))
							{
								fighterID = Integer.parseInt(test);
							}
							break;
						}
					}
					for(Fighter f : fight.getFighters(1))
					{
						if(f.getGUID() == fighterID) challenge_loose(f);
					}
				}
			break;
			case 47://Contamination
				if(fighter.getTeam() == 0)
				{
					String str = ";"+fighter.getGUID()+",";
					if(Args.contains(str+"1;")) challenge_loose(fighter);
					else if(Args.contains(str+"2;")) Args += str+"1;";
					else if(Args.contains(str+"3;")) Args += str+"2;";
				}
			break;
		  }
	  }
	
	public void onfight_EndTurn(Fighter fighter)
	{
		if(!challengeAlive) return;
		
		ArrayList<Fighter> Neighbours = new ArrayList<Fighter>();
		Neighbours = Pathfinding.getFightersAround(fighter.get_fightCell().getID(), fight.get_map(), fight);
		boolean hasFailed = false;
		
		switch(type)
		{
			case 1://Zombie
				int diff = fighter.getPM() - fighter.getCurPM(fight);
				if(diff > 1 || diff < 1) challenge_loose(fighter);
			break;
			case 2://Statue
				if(fighter.get_fightCell().getID() != Arg) challenge_loose(fighter);
			break;
			case 7://Jardinier
				if(fighter.getPersonnage() != null)
				{
					if(fighter.canLaunchSpell(367)) challenge_loose(fighter);
				}
			break;
			case 8://Nomade
				if(fighter.getCurPM(fight) != 0) challenge_loose(fighter);
			break;
			case 12://Fossoyeur
				if(fighter.getPersonnage() != null)
				{
					if(fighter.canLaunchSpell(373)) challenge_loose(fighter);
				}
			break;
			case 14://Casino Royal
				if(fighter.getPersonnage() != null)
				{
					if(fighter.canLaunchSpell(101)) challenge_loose(fighter);
				}
			break;
			case 15://Araknophile
				if(fighter.getPersonnage() != null)
				{
					if(fighter.canLaunchSpell(370)) challenge_loose(fighter);
				}
			break;
			case 36://Hardi
				hasFailed = true;
				if(!Neighbours.isEmpty())
				{
					for(Fighter f : Neighbours)
					{
						if(f.getTeam() != fighter.getTeam()) hasFailed = false;
					}
				}
			break;
			case 37://Collant
				hasFailed = true;
				if(!Neighbours.isEmpty())
				{
					for(Fighter f : Neighbours)
					{
							if(f.getTeam() == fighter.getTeam()) hasFailed = false;
					}
				}
			break;
			case 39://Anachorète
				if(!Neighbours.isEmpty())
				{
					for(Fighter f : Neighbours)
					{
						if(f.getTeam() == fighter.getTeam()) challenge_loose(fighter);
					}
				}
			break;
			case 40://Pusillanime
				if(!Neighbours.isEmpty())
				{
					for(Fighter f : Neighbours)
					{
						if(f.getTeam() != fighter.getTeam()) challenge_loose(fighter);
					}
				}
			break;
			case 41://Pétulant
				if(fighter.getCurPA(fight) > 0) challenge_loose(fighter);
			break;
			case 42://Deux pour le prix d'un
				String GUID = ""+fighter.getGUID();
				int compteur = 0;
				for(String ID : Args.split(";"))
				{	
					if(ID.equals(GUID))
						compteur++;
				}
				if(compteur == 2 || compteur == 0) Args= "";
				else challenge_loose(fighter);
			break;
			default:
			break;
		}
		if(hasFailed)
			challenge_loose(fighter);
		return;
	}
}
