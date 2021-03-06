package party;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import util.YamlLoader;

public class PartyDataManager
{
	public void saveParty()
	{
		YamlLoader partyYaml = new YamlLoader();
		partyYaml.getConfig("Party/PartyList.yml");
		
		String[] PartyList = main.MainServerOption.party.keySet().toArray(new String[0]);
		for(int count = 0; count < PartyList.length; count++)
		{
			Player[] PartyMember = main.MainServerOption.party.get(PartyList[count]).getMember();
			partyYaml.set("Party."+PartyList[count]+".Title", main.MainServerOption.party.get(PartyList[count]).getTitle());
			partyYaml.set("Party."+PartyList[count]+".Leader", main.MainServerOption.party.get(PartyList[count]).getLeader());
			partyYaml.set("Party."+PartyList[count]+".PartyLock", main.MainServerOption.party.get(PartyList[count]).getLock());
			partyYaml.set("Party."+PartyList[count]+".Password", main.MainServerOption.party.get(PartyList[count]).getPassword());
			partyYaml.set("Party."+PartyList[count]+".Capacity", main.MainServerOption.party.get(PartyList[count]).getCapacity());
			for(int counter = 0; counter < PartyMember.length; counter++)
			{
				Player m = main.MainServerOption.party.get(PartyList[count]).getMember()[counter];
				if(m != null)
					partyYaml.set("Party."+PartyList[count]+".Member."+counter, PartyMember[counter].getName());
				else
					partyYaml.set("Party."+PartyList[count]+".Member."+counter, "null");
			}
		}
		partyYaml.saveConfig();
		main.MainServerOption.party.clear();
		main.MainServerOption.partyJoiner.clear();
		return;
	}
	
	public void loadParty()
	{
		YamlLoader partyYaml = new YamlLoader();
		partyYaml.getConfig("Party/PartyList.yml");

		if(partyYaml.contains("Party"))
		{
			String[] PartyList = partyYaml.getConfigurationSection("Party").getKeys(false).toArray(new String[0]);
			String PT = null;
			String PL = null;
			String PP = null;
			String[] PM = null;
			for(int count = 0; count < PartyList.length; count++)
			{
				if(partyYaml.contains("Party."+PartyList[count].toString()+".Member"))
				{
					long PCT = Long.parseLong(PartyList[count].toString());
					PT = partyYaml.getString("Party."+PartyList[count]+".Title");
					PL = partyYaml.getString("Party."+PartyList[count]+".Leader");
					boolean PLock = partyYaml.getBoolean("Party."+PartyList[count]+".PartyLock");
					PP = partyYaml.getString("Party."+PartyList[count]+".Password");
					byte PC = (byte) partyYaml.getInt("Party."+PartyList[count]+".Capacity");
					PM = new String[PC];
					for(int counter = 0; counter < partyYaml.getConfigurationSection("Party."+PartyList[count]+".Member").getKeys(false).size();counter++)
						PM[counter] = partyYaml.getString("Party."+PCT+".Member."+counter);
					main.MainServerOption.party.put(PCT, new PartyObject(PCT, PL, PT, PLock, PP, PC, PM));
					
					for(int counter = 0; counter < PM.length;counter++)
						if(PM[counter] != null)
							if(Bukkit.getServer().getPlayer(PM[counter])==null)
								main.MainServerOption.party.get(PCT).QuitPartyOfflinePlayer(PM[counter]);
							else
							{
								if(Bukkit.getServer().getPlayer(PM[counter]).isOnline()==false)
									main.MainServerOption.party.get(PCT).QuitParty((Player) Bukkit.getServer().getOfflinePlayer(PM[counter]));
								else
									main.MainServerOption.partyJoiner.put((Player) Bukkit.getServer().getPlayer(PM[counter]), PCT);
							}
					if(Bukkit.getServer().getOfflinePlayer(PL).isOnline() == false)
						main.MainServerOption.party.get(PCT).QuitPartyOfflinePlayer(PL);
					if(main.MainServerOption.party.get(PCT).getPartyMembers()==0)
						main.MainServerOption.party.remove(PCT);
				}
			}
		}
		partyYaml.removeKey("Party");
		partyYaml.removeKey("PartyJoiner");
		partyYaml.saveConfig();
		return;
	}
}
