package skill;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;

import admin.OPbox_GUI;
import effect.SoundEffect;
import main.Main_ServerOption;
import user.UserData_Object;
import util.Util_GUI;
import util.YamlLoader;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class OPboxSkill_GUI extends Util_GUI
{
	public void AllSkillsGUI(Player player, short page, boolean isJobGUI,String WhatJob)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		String UniqueCode = "��0��0��b��0��0��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��ü ��ų ��� : " + (page+1));
		Object[] a = SkillList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String SkillName = a[count].toString();
			short JobLevel=0;
			if(SkillList.contains(a[count].toString()+".SkillRank"))
				JobLevel= (short) SkillList.getConfigurationSection(a[count].toString()+".SkillRank").getKeys(false).size();
			if(count > a.length || loc >= 45) break;
			
			if(isJobGUI == true)
			{
				YamlLoader JobList = new YamlLoader();
				JobList.getConfig("Skill/JobList.yml");
				if(WhatJob.equals("Maple"))
				{
					UserData_Object u = new UserData_Object();
					if(JobList.contains("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName)==false)
					{
						Stack2("��f��l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("��3�ִ� ��ų ���� : ��f"+JobLevel,"","��e[�� Ŭ���� ��ų ���]"), loc, inv);
						loc++;	
					}
				}
				else
				{
					if(JobList.contains("Mabinogi.Added."+SkillName)==false)
					{
						Stack2("��f��l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("��3�ִ� ��ų ���� : ��f"+JobLevel,"","��e[�� Ŭ���� ��ų ���]"), loc, inv);
						loc++;	
					}
				}

			}
			else
			{
				Stack2("��f��l" + SkillName,  SkillList.getInt(a[count].toString()+".ID"),SkillList.getInt(a[count].toString()+".DATA"),SkillList.getInt(a[count].toString()+".Amount"),Arrays.asList("��3�ִ� ��ų ���� : ��f"+JobLevel,"","��e[�� Ŭ���� ��ų ���� ����]","��e[Shift + �� Ŭ���� ������ ����]","��c[Shift + �� Ŭ���� ��ų ����]"), loc, inv);
				loc++;	
			}
		}
		
		if(a.length-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 48, inv);

		if(isJobGUI == false)
			Stack2("��f��l�� ��ų", 386,0,1,Arrays.asList("��7���ο� ��ų�� �����մϴ�."), 49, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�.","��0"+WhatJob), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�.","��0"+isJobGUI), 53, inv);
		player.openInventory(inv);
	}

	public void IndividualSkillOptionGUI(Player player, short page, String SkillName)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");

		String UniqueCode = "��0��0��b��0��1��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��ų ���� : " + (page+1));

		Set<String> b= SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false);
		
		byte loc=0;
		for(int count = page*45; count < b.size();count++)
		{
			if(count > b.size() || loc >= 45) break;
			String lore = "";
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command").equalsIgnoreCase("null"))
				lore = "��7[������ Ŀ�ǵ� ����]%enter%";
			else
			{
				lore = "��3Ŀ�ǵ� : ��f"+SkillList.getString(SkillName+".SkillRank."+(count+1)+".Command")+"%enter%";
				if(SkillList.getBoolean(SkillName+".SkillRank."+(count+1)+".BukkitPermission") == false)
					lore = lore + "��f[���� ���� ���]%enter%";
				else
					lore = lore + "��d[�ܼ� ���� ���]%enter%";
			}
			
			if(SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells").equalsIgnoreCase("null"))
				lore = lore + "��7[���� �������� ����]%enter%";
			else
				lore = lore + "��3�������� : ��f"+SkillList.getString(SkillName+".SkillRank."+(count+1)+".MagicSpells")+"%enter%";

			if((page*45)+count != 0)
			{
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel") >= 1)
					lore = lore + "��b�±޽� �ʿ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedLevel")+"%enter%";
				if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel") >= 1)
					lore = lore + "��b�±޽� �ʿ� ���� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".NeedRealLevel")+"%enter%";

				lore = lore + "��b�±޽� �ʿ� ��ų ����Ʈ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".SkillPoint")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")<0)
				lore = lore + "��c�±޽� ���ʽ� ������ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")>0)
				lore = lore + "��b�±޽� ���ʽ� ������ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusHP")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")<0)
				lore = lore + "��c�±޽� ���ʽ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")>0)
				lore = lore + "��b�±޽� ���ʽ� ���� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMP")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")<0)
				lore = lore + "��c�±޽� ���ʽ� "+Main_ServerOption.statSTR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")>0)
				lore = lore + "��b�±޽� ���ʽ� "+Main_ServerOption.statSTR+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusSTR")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")<0)
				lore = lore + "��c�±޽� ���ʽ� "+Main_ServerOption.statDEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")>0)
				lore = lore + "��b�±޽� ���ʽ� "+Main_ServerOption.statDEX+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEX")+"%enter%";
				
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")<0)
				lore = lore + "��c�±޽� ���ʽ� "+Main_ServerOption.statINT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")>0)
				lore = lore + "��b�±޽� ���ʽ� "+Main_ServerOption.statINT+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusINT")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")<0)
				lore = lore + "��c�±޽� ���ʽ� "+Main_ServerOption.statWILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")>0)
				lore = lore + "��b�±޽� ���ʽ� "+Main_ServerOption.statWILL+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusWILL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")<0)
				lore = lore + "��c�±޽� ���ʽ� "+Main_ServerOption.statLUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")>0)
				lore = lore + "��b�±޽� ���ʽ� "+Main_ServerOption.statLUK+" : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusLUK")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")<0)
				lore = lore + "��c�±޽� ���ʽ� �뷱�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")>0)
				lore = lore + "��b�±޽� ���ʽ� �뷱�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusBAL")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")<0)
				lore = lore + "��c�±޽� ���ʽ� ũ��Ƽ�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")>0)
				lore = lore + "��b�±޽� ���ʽ� ũ��Ƽ�� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusCRI")+"%enter%";
			
			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")<0)
				lore = lore + "��c�±޽� ���ʽ� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")>0)
				lore = lore + "��b�±޽� ���ʽ� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")<0)
				lore = lore + "��c�±޽� ���ʽ� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")>0)
				lore = lore + "��b�±޽� ���ʽ� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusPRO")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")<0)
				lore = lore + "��c�±޽� ���ʽ� ���� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")>0)
				lore = lore + "��b�±޽� ���ʽ� ���� ��� : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMDEF")+"%enter%";

			if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")<0)
				lore = lore + "��c�±޽� ���ʽ� ���� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			else if(SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")>0)
				lore = lore + "��b�±޽� ���ʽ� ���� ��ȣ : "+SkillList.getInt(SkillName+".SkillRank."+(count+1)+".BonusMPRO")+"%enter%";
			
			}
			if((page*45)+count ==0)
				lore = lore + "%enter%��e[�� Ŭ���� ��ũ ���� ����]%enter%��c[1���� ��ų�� ���� �Ұ���]";
			else if((count +1) == b.size())
				lore = lore + "%enter%��e[�� Ŭ���� ��ũ ���� ����]%enter%��c[Shift + �� Ŭ���� ����]";
			else
				lore = lore + "%enter%��e[�� Ŭ���� ��ũ ���� ����]%enter%��c[���� ��ũ ���� �� ���� ����]";
			
			String[] scriptA = lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  scriptA[counter];
			Stack2("��f��l" + SkillName +" ���� "+(count +1) , 340,0,1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(b.size()-(page*44)>45)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 48, inv);
		Stack2("��f��l�� ��ũ", 386,0,1,Arrays.asList("��7���ο� ��ų ��ũ�� ���� �մϴ�."), 49, inv);
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�.","��0"+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	public void SkillRankOptionGUI(Player player, String SkillName, short SkillLevel)
	{
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		String UniqueCode = "��0��0��b��0��2��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0��ũ "+SkillLevel);
		String lore = "";
		if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command").equalsIgnoreCase("null"))
			lore = "��7[  ����  ]";
		else
			lore = "��f"+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Command");
			
		Stack2("��3[Ŀ�ǵ� ����]", 137,0,1,Arrays.asList("","��3[���� ��ϵ� Ŀ�ǵ�]",lore,"","��e[�� Ŭ���� Ŀ�ǵ� ����]"), 11, inv);
		if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission")==false)
			Stack2("��3[Ŀ�ǵ� ����]", 397,3,1,Arrays.asList("","��3[Ŀ�ǵ� ����� ���� ����]","��7[  ����  ]","","��e[�� Ŭ���� ���� ����]"), 13, inv);
		else
			Stack2("��3[Ŀ�ǵ� ����]", 137,3,1,Arrays.asList("","��3[Ŀ�ǵ� ����� ���� ����]","��d[  ��Ŷ  ]","","��e[�� Ŭ���� ���� ����]"), 13, inv);

		if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
		{
			if(SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells").equalsIgnoreCase("null"))
				Stack2("��3[���� ����]", 280,0,1,Arrays.asList("","��3[���� ��ϵ� ����]","��7[  ����  ]","","��e[�� Ŭ���� ���� ����]"), 15, inv);
			else
				Stack2("��3[���� ����]", 369,0,1,Arrays.asList("","��3[���� ��ϵ� ����]","��f"+SkillList.getString(SkillName+".SkillRank."+SkillLevel+".MagicSpells"),"","��e[�� Ŭ���� ���� ����]"), 15, inv);

			if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".AffectStat")==false)
			{
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");
				SkillList.saveConfig();
			}
			String IncreaseDamage = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
			
			if(IncreaseDamage.equals("����"))
				Stack2("��f��l[��ų ���ݷ� ���]", 166,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c[��ų ���� �������� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals("������"))
				Stack2("��f��l[��ų ���ݷ� ���]", 351,1,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c[���� �����¿� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals("����"))
				Stack2("��f��l[��ų ���ݷ� ���]", 351,12,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c[���� �������� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals(Main_ServerOption.statSTR))
				Stack2("��f��l[��ų ���ݷ� ���]", 267,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c["+Main_ServerOption.statSTR+"�� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals(Main_ServerOption.statDEX))
				Stack2("��f��l[��ų ���ݷ� ���]", 261,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c["+Main_ServerOption.statDEX+"�� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals(Main_ServerOption.statINT))
				Stack2("��f��l[��ų ���ݷ� ���]", 369,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c["+Main_ServerOption.statINT+"�� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals(Main_ServerOption.statWILL))
				Stack2("��f��l[��ų ���ݷ� ���]", 370,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c["+Main_ServerOption.statWILL+"�� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else if(IncreaseDamage.equals(Main_ServerOption.statLUK))
				Stack2("��f��l[��ų ���ݷ� ���]", 322,0,1,Arrays.asList("","��f[  "+IncreaseDamage+"  ]","��c["+Main_ServerOption.statLUK+"�� ����Ͽ� ����� ���]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
			else
				Stack2("��f��l[��ų ���ݷ� ���]", 322,0,1,Arrays.asList("","��c[�� ������ �ʿ��մϴ�!]","","��e[�� Ŭ���� ���� �ִ� ���� ����]"), 21, inv);
		}
		else
		{
			Stack2("��c[���� ����]", 166,0,1,Arrays.asList("","��c[MagicSpells �÷������� ã�� �� ����]","��7MagicSpells �÷������� ���� ���","��7����� �� �ִ� �ɼ��Դϴ�.",""), 15, inv);
			Stack2("��c[��ų ���ݷ� ���]", 166,0,1,Arrays.asList("","��c[MagicSpells �÷������� ã�� �� ����]","��7MagicSpells �÷������� ���� ���","��7����� �� �ִ� �ɼ��Դϴ�.",""), 21, inv);
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
			SkillList.saveConfig();
		}
		String WeaponType = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
		switch(WeaponType)
		{
			case "����":
				Stack2("��f��l[���� Ÿ�� ����]", 166,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��b[���� ���� ��ų �ߵ� ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���� ����":
				Stack2("��f��l[���� Ÿ�� ����]", 267,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[�ش�Ǵ� ������ ������ �־�� �ߵ�]","��c  �Ѽ� ��","��c  ��� ��","��c  ����","��c  ��","��c  ���� ����","��d  �Ϲ� ��/����/���� ������","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "�Ѽ� ��":
				Stack2("��f��l[���� Ÿ�� ����]", 267,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '�Ѽ� ��'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� �� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "��� ��":
				Stack2("��f��l[���� Ÿ�� ����]", 267,0,2,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '��� ��'�� �־�� �ߵ�]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2("��f��l[���� Ÿ�� ����]", 258,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '����'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� ���� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "��":
				Stack2("��f��l[���� Ÿ�� ����]", 292,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '��'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� ���� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���Ÿ� ����":
				Stack2("��f��l[���� Ÿ�� ����]", 261,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[�ش�Ǵ� ������ ������ �־�� �ߵ�]","��c  Ȱ","��c  ����","��c  ���Ÿ� ����","��d  �Ϲ� Ȱ, �߻�� ������","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "Ȱ":
				Stack2("��f��l[���� Ÿ�� ����]", 261,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ 'Ȱ'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� Ȱ ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2("��f��l[���� Ÿ�� ����]", 23,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '����'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� �߻�� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "���� ����":
				Stack2("��f��l[���� Ÿ�� ����]", 280,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[�ش�Ǵ� ������ ������ �־�� �ߵ�]","��c  ����","��c  ������","��c  ���� ����","��d  �Ϲ� �����/��/�������� ���� ������","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "����":
				Stack2("��f��l[���� Ÿ�� ����]", 280,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '����'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� �����/�� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			case "������":
				Stack2("��f��l[���� Ÿ�� ����]", 369,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��c[������ ������ '������'�� �־�� �ߵ�]","��d[Ȥ�� �Ϲ� �������� ���� ������ ����]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
			default :
				Stack2("��f��l[���� Ÿ�� ����]", 369,0,1,Arrays.asList("","��f[  "+WeaponType+"  ]","��f[������ ������ '"+ChatColor.stripColor(WeaponType)+"'�� �־�� �ߵ�]","","��e[�� Ŭ���� ��� ���� ����]"), 19, inv);
				break;
		}

		if(SkillList.contains(SkillName+".SkillRank."+SkillLevel+".Lore")==false)
		{
			SkillList.set(SkillName+".SkillRank."+SkillLevel+".Lore", "��7     [���� ����]     ");
			SkillList.saveConfig();
		}
		
		String lore2 = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".Lore");

		String[] scriptA = lore2.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  scriptA[counter];
		Stack2("��f��l[��ų ����]", 386,0,1,Arrays.asList(scriptA), 23, inv);
		
		if(SkillLevel != 1)
		{
			Stack2("��f��l[�ʿ� ����]", 384,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��ϴµ� �ʿ��� ����]","��f��l ���� "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedLevel")+" �̻�","��f��l ���� ���� "+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".NeedRealLevel") + " �̻�","","��e[�� Ŭ���� ���� ����]"), 3, inv);
			Stack2("��f��l[�ʿ� ��ų ����Ʈ]", 399,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��ϴµ� �ʿ��� ��ų ����Ʈ]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".SkillPoint") +"����Ʈ","","��e[�� Ŭ���� ��ų ����Ʈ ����]"), 4, inv);
			Stack2("��f��l[���ʽ� ������]", 351,1,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ������]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusHP") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 28, inv);
			Stack2("��f��l[���ʽ� ����]", 351,12,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ����]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMP"),"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 29, inv);
			Stack2("��f��l[���ʽ� "+Main_ServerOption.statSTR+"]", 267,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� "+Main_ServerOption.statSTR+"]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusSTR"),"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 30, inv);
			Stack2("��f��l[���ʽ� "+Main_ServerOption.statDEX+"]", 261,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� "+Main_ServerOption.statDEX+"]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEX") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 31, inv);
			Stack2("��f��l[���ʽ� "+Main_ServerOption.statINT+"]", 369,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� "+Main_ServerOption.statINT+"]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusINT"),"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 32, inv);
			Stack2("��f��l[���ʽ� "+Main_ServerOption.statWILL+"]", 370,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� "+Main_ServerOption.statWILL+"]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusWILL") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 33, inv);
			Stack2("��f��l[���ʽ� "+Main_ServerOption.statLUK+"]", 322,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� "+Main_ServerOption.statLUK+"]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusLUK") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 34, inv);
			Stack2("��f��l[���ʽ� �뷱��]", 283,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� �뷱��]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusBAL") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 37, inv);
			Stack2("��f��l[���ʽ� ũ��Ƽ��]", 262,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ũ��Ƽ��]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusCRI") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 38, inv);
			Stack2("��f��l[���ʽ� ���]", 307,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ���]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusDEF") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 39, inv);
			Stack2("��f��l[���ʽ� ��ȣ]", 306,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ��ȣ]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusPRO") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 40, inv);
			Stack2("��f��l[���ʽ� ���� ���]", 311,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ���� ���]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMDEF") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 41, inv);
			Stack2("��f��l[���ʽ� ���� ��ȣ]", 310,0,1,Arrays.asList("","��b["+SkillName+" "+(SkillLevel-1)+" ��������","��b���� ������ �±��� �� ��� ���� ��ȣ]","��f     ��l"+SkillList.getInt(SkillName+".SkillRank."+SkillLevel+".BonusMPRO") ,"","��e[�� Ŭ���� ���ʽ� ���� ����]"), 42, inv);
		}
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�.","��0"+SkillLevel), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�.","��0"+SkillName), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	public void AllSkillsGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		

		boolean isJobGUI = Boolean.parseBoolean(ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1)));
		String WhatJob = ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1));
		UserData_Object u = new UserData_Object();
		if(slot == 53)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			if(isJobGUI == true && WhatJob.equals("Maple"))
				u.clearAll(player);
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//���� ���
			{
				if(isJobGUI==true)
				{
					if(WhatJob.equals("Maple"))
					{
						new job.Job_GUI().MapleStory_JobSetting(player, u.getString(player, (byte)3));
						u.clearAll(player);
					}
					else
					{
						new job.Job_GUI().Mabinogi_ChooseCategory(player, (short) 0);
						u.clearAll(player);
					}
				}
				else
				{
					new OPbox_GUI().opBoxGuiMain(player, (byte) 2);
					u.clearAll(player);
				}
			}
			else if(slot == 48)//���� ������
				AllSkillsGUI(player,(short) (page-1),isJobGUI,WhatJob);
			else if(slot == 50)//���� ������
				AllSkillsGUI(player,(short) (page+1),isJobGUI,WhatJob);
			else if(slot == 49)//�� ��ų
			{
				player.closeInventory();
				player.sendMessage("��d[��ų] : ���ο� ��ų �̸��� ������ �ּ���!");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "CS");
			}
			else
			{
				if(isJobGUI == true)
				{
					String SkillName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
					if(WhatJob.equals("Maple"))
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						YamlLoader JobList = new YamlLoader();
						JobList.getConfig("Skill/JobList.yml");
						JobList.createSection("MapleStory."+u.getString(player, (byte)3)+"."+u.getString(player, (byte)2)+".Skill."+SkillName);
						JobList.saveConfig();
						job.Job_GUI JGUI = new job.Job_GUI();
						JGUI.MapleStory_JobSetting(player, u.getString(player, (byte)3));
						u.clearAll(player);
						YamlLoader Config = new YamlLoader();
						Config.getConfig("Config.yml");
						Config.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
						Config.saveConfig();
						new job.Job_Main().AllPlayerFixAllSkillAndJobYML();
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						YamlLoader JobList = new YamlLoader();
						JobList.getConfig("Skill/JobList.yml");
						JobList.set("Mabinogi.Added."+SkillName, WhatJob);
						JobList.set("Mabinogi."+WhatJob + "."+SkillName, false);
						JobList.saveConfig();
						AllSkillsGUI(player, page, isJobGUI, WhatJob);
					}
					return;
				}
				else
				{
					if(event.isShiftClick()==true&&event.isLeftClick()==true)
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						player.closeInventory();
						player.sendMessage("��d[��ų] : ��ų �������� ID���� �Է� �� �ּ���!!");
						u.setType(player, "Skill");
						u.setString(player, (byte)1, "CSID");
						u.setString(player, (byte)2, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else if(event.isLeftClick()==true&&event.isRightClick()==false)
					{
						SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
						IndividualSkillOptionGUI(player, (short) 0, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					}
					else if(event.isShiftClick()==true&&event.isRightClick()==true)
					{
						SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
						YamlLoader Config = new YamlLoader();
						Config.getConfig("Config.yml");
						Config.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
						Config.saveConfig();
						YamlLoader SkillList = new YamlLoader();
						SkillList.getConfig("Skill/SkillList.yml");
						SkillList.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
						SkillList.saveConfig();
						AllSkillsGUI(player, (short) (Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1),false,"Maple");
						job.Job_Main J = new job.Job_Main();
						J.AllPlayerFixAllSkillAndJobYML();
					}
				}
			}
		}
	}

	public void IndividualSkillOptionGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 53)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//���� ���
				AllSkillsGUI(player, (short) 0,false,"Maple");
			else if(slot == 48)//���� ������
				IndividualSkillOptionGUI(player,(short) (page-1),SkillName);
			else if(slot == 50)//���� ������
				IndividualSkillOptionGUI(player,(short) (page+1),SkillName);
			else if(slot == 49)//�� ��ũ
			{
				YamlLoader SkillList = new YamlLoader();
				SkillList.getConfig("Skill/SkillList.yml");
				short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				SkillList.set(SkillName+".SkillRank."+(size+1)+".Command","null");
				SkillList.set(SkillName+".SkillRank."+(size+1)+".BukkitPermission",false);
				SkillList.set(SkillName+".SkillRank."+(size+1)+".MagicSpells","null");
				SkillList.set(SkillName+".SkillRank."+(size+1)+".SkillPoint",1000);
				SkillList.saveConfig();
				IndividualSkillOptionGUI(player,  page, SkillName);
			}
			else
			{
				YamlLoader Config = new YamlLoader();
				Config.getConfig("Config.yml");
				YamlLoader SkillList = new YamlLoader();
				SkillList.getConfig("Skill/SkillList.yml");
				short size= (short) SkillList.getConfigurationSection(SkillName+".SkillRank").getKeys(false).size();
				if(event.isLeftClick()==true&&event.isRightClick()==false)
				{
					SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					SkillRankOptionGUI(player,SkillName,(short) ((page*45)+event.getSlot()+1));
				}
				else if(event.isShiftClick()==true&&event.isRightClick()==true&&(page*45)+event.getSlot()!=0&&(page*45)+event.getSlot()+1==size)
				{
					Config.set("Time.LastSkillChanged", new util.Util_Number().RandomNum(0, 100000)-new util.Util_Number().RandomNum(0, 100000));
					Config.saveConfig();
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 0.8F, 1.0F);
					SkillList.removeKey(SkillName+".SkillRank."+(size));
					SkillList.saveConfig();
					IndividualSkillOptionGUI(player, page,SkillName);
					job.Job_Main J = new job.Job_Main();
					J.AllPlayerSkillRankFix();
				}
			}
		}
	}
	
	public void SkillRankOptionGUIClick(InventoryClickEvent event)
	{
		String SkillName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
		short SkillLevel = Short.parseShort(ChatColor.stripColor(event.getInventory().getItem(45).getItemMeta().getLore().get(1)));
		
		YamlLoader SkillList = new YamlLoader();
		SkillList.getConfig("Skill/SkillList.yml");
		

		UserData_Object u = new UserData_Object();
		
		Player player = (Player) event.getWhoClicked();
		switch (event.getSlot())
		{
		case 3://�ʿ� ����
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			player.closeInventory();
			player.sendMessage("��d[��ų] : ��ų�� ��� �� �ִ� ������ ������ �ּ���!");
			player.sendMessage("��d[���� ���� : 0] [�ִ� : "+Integer.MAX_VALUE+"]");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "NeedLV");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
		}
		return;
		case 4://�ʿ� ��ų ����Ʈ
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				player.sendMessage("��d[��ų] : �ʿ��� ��ų ����Ʈ�� ������ �ּ���!");
				player.sendMessage("��d[�ּ� : 0] [�ִ� : "+Byte.MAX_VALUE+"]");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SP");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
			}
			return;
		case 11://Ŀ�ǵ� ����
			{
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.sendMessage("��3[��ų] : /Ŀ�ǵ� [������ Ŀ�ǵ� �Է�]");
				player.sendMessage("��d  /Ŀ�ǵ� ������f �Է½� Ŀ�ǵ� ����");
				u.setType(player, "Skill");
				u.setString(player, (byte)1, "SKC");
				u.setString(player, (byte)2, SkillName);
				u.setInt(player, (byte)4,SkillLevel);
				player.closeInventory();
			}
			return;
		case 13://Ŀ�ǵ� ����
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(SkillList.getBoolean(SkillName+".SkillRank."+SkillLevel+".BukkitPermission") == true)
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", false);
			else
				SkillList.set(SkillName+".SkillRank."+SkillLevel+".BukkitPermission", true);
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 15://���� ����
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 0.8F, 1.9F);
				player.closeInventory();
				new otherplugins.SpellMain().ShowAllMaigcGUI(player, (short)0,SkillName,SkillLevel,(byte)0);
			}
			else
				SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
			return;
		case 19://���� ���� ����
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String DistrictWeapon = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon");
			YamlLoader Target = new YamlLoader();
			Target.getConfig("Item/CustomType.yml");

		  	Object[] Type = Target.getKeys().toArray();
		  	if(Type.length==0)
		  	{
				switch(DistrictWeapon)
				{
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "�Ѽ� ��");
						break;
					case "�Ѽ� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��� ��");
						break;
					case "��� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��");
						break;
					case "��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���Ÿ� ����");
						break;
					case "���Ÿ� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "Ȱ");
						break;
					case "Ȱ":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "������");
						break;
					case "������":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					default:
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
				}
		  	}
		  	else
		  	{
				switch(DistrictWeapon)
				{
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "�Ѽ� ��");
						break;
					case "�Ѽ� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��� ��");
						break;
					case "��� ��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "��");
						break;
					case "��":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���Ÿ� ����");
						break;
					case "���Ÿ� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "Ȱ");
						break;
					case "Ȱ":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "���� ����");
						break;
					case "���� ����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "����");
						break;
					case "����":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", "������");
						break;
					case "������":
						SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon", Type[0].toString());
						break;
					default:
						{
							boolean isExit = false;
							for(int count = 0; count < Type.length; count++)
							{
								if((SkillList.getString(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon").contains(Type[count].toString())))
								{
									if(count+1 == Type.length)
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","����");
									else
										SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","��f"+Type[(count+1)].toString());
									isExit = true;
									break;
								}
							}
							if(isExit==false)
								SkillList.set(SkillName+".SkillRank."+SkillLevel+".DistrictWeapon","����");
						}
						break;
				}
		  	}
			SkillList.saveConfig();
			SkillRankOptionGUI(player, SkillName, SkillLevel);
			return;
		case 21://��ų ����� ���
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(Bukkit.getPluginManager().isPluginEnabled("MagicSpells") == true)
			{
				String switchNeed = SkillList.getString(SkillName+".SkillRank."+SkillLevel+".AffectStat");
				if(switchNeed.equals("����"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "������");
				else if(switchNeed.equals("������"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");
				else if(switchNeed.equals("����"))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", Main_ServerOption.statSTR);
				else if(switchNeed.equals(Main_ServerOption.statSTR))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", Main_ServerOption.statDEX);
				else if(switchNeed.equals(Main_ServerOption.statDEX))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", Main_ServerOption.statINT);
				else if(switchNeed.equals(Main_ServerOption.statINT))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", Main_ServerOption.statWILL);
				else if(switchNeed.equals(Main_ServerOption.statWILL))
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", Main_ServerOption.statLUK);
				else
					SkillList.set(SkillName+".SkillRank."+SkillLevel+".AffectStat", "����");

				SkillList.saveConfig();
				SkillRankOptionGUI(player, SkillName, SkillLevel);
			}
			else
			{
				SoundEffect.SP(player, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.8F);
			}
			return;

		case 23://��ų ����
			player.sendMessage("��d[��ų] : ��ų ������ �ۼ� �� �ּ���!");
			player.sendMessage("��6%enter%��f - ���� ��� ���� -");
			player.sendMessage("��f��l&l ��0&0 ��1&1 ��2&2 "+
			"��3&3 ��4&4 ��5&5 " +
					"��6&6 ��7&7 ��8&8 " +
			"��9&9 ��a&a ��b&b ��c&c" +
					"��d&d ��e&e ��f&f");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "SKL");
			u.setString(player, (byte)2, SkillName);
			u.setInt(player, (byte)4,SkillLevel);
			player.closeInventory();
			return;
		case 28://���ʽ� ������
			player.sendMessage("��d[��ų] : ���ʽ� ������ ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BH");
			break;
		case 29://���ʽ� ����
			player.sendMessage("��d[��ų] : ���ʽ� ���� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BM");
			break;
		case 30://���ʽ� "+GoldBigDragon_RPG.ServerOption.STR+"
			player.sendMessage("��d[��ų] : ���ʽ� "+Main_ServerOption.statSTR+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BSTR");
			break;
		case 31://���ʽ� "+GoldBigDragon_RPG.ServerOption.DEX+"
			player.sendMessage("��d[��ų] : ���ʽ� "+Main_ServerOption.statDEX+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEX");
			break;
		case 32://���ʽ� "+GoldBigDragon_RPG.ServerOption.INT+"
			player.sendMessage("��d[��ų] : ���ʽ� "+Main_ServerOption.statINT+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BINT");
			break;
		case 33://���ʽ� "+GoldBigDragon_RPG.ServerOption.WILL+"
			player.sendMessage("��d[��ų] : ���ʽ� "+Main_ServerOption.statWILL+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BWILL");
			break;
		case 34://���ʽ� "+GoldBigDragon_RPG.ServerOption.LUK+"
			player.sendMessage("��d[��ų] : ���ʽ� "+Main_ServerOption.statLUK+" ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BLUK");
			break;
		case 37://���ʽ� �뷱��
			player.sendMessage("��d[��ų] : ���ʽ� �뷱�� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BBAL");
			break;
		case 38://���ʽ� ũ��Ƽ��
			player.sendMessage("��d[��ų] : ���ʽ� ũ��Ƽ�� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BCRI");
			break;
		case 39://���ʽ� ���
			player.sendMessage("��d[��ų] : ���ʽ� ��� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BDEF");
			break;
		case 40://���ʽ� ��ȣ
			player.sendMessage("��d[��ų] : ���ʽ� ��ȣ ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BPRO");
			break;
		case 41://���ʽ� ���� ���
			player.sendMessage("��d[��ų] : ���ʽ� ���� ��� ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMDEF");
			break;
		case 42://���ʽ� ���� ��ȣ
			player.sendMessage("��d[��ų] : ���ʽ� ���� ��ȣ ��ġ�� �Է��� �ּ���!");
			u.setType(player, "Skill");
			u.setString(player, (byte)1, "BMPRO");
			break;
		case 45://���� ���
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			IndividualSkillOptionGUI(player, (short) 0, SkillName);
			return;
		case 53://������
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
			return;
		}
		player.closeInventory();
		player.sendMessage("��d[�ּ� : "+Byte.MIN_VALUE+"] [�ִ� : "+Byte.MAX_VALUE+"]");
		SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
		u.setType(player, "Skill");
		u.setString(player, (byte)2, SkillName);
		u.setInt(player, (byte)4,SkillLevel);
	}

}