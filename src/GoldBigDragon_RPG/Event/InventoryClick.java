package GoldBigDragon_RPG.Event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick
{
	public void InventoryClickRouter(InventoryClickEvent event, String InventoryName)
	{
		if(event.getClickedInventory() == null)
			return;
		if(event.getClickedInventory().getTitle().equalsIgnoreCase("container.inventory") == true)
			return;
		if(InventoryName.compareTo("��ȯ")==0)
		{
			new GoldBigDragon_RPG.GUI.EquipGUI().ExchangeGUIclick(event);return;
	    }
		if (event.getCurrentItem() == null||event.getCurrentItem().getType() == Material.AIR||event.getCurrentItem().getAmount() == 0)
		{return;}
		
		if(InventoryName.compareTo("����")==0)
		{
		    if(event.getClickedInventory().getType() != InventoryType.CHEST)
		    {
		    	event.setCancelled(true);
		    	return;
		    }
		    GoldBigDragon_RPG.GUI.StatsGUI SGUI = new GoldBigDragon_RPG.GUI.StatsGUI();
			SGUI.StatusInventoryclick(event); 
			return;
		}
		else if(InventoryName.compareTo("���� ��� ����")==0)
		{
	    	GoldBigDragon_RPG.ETC.Monster MC = new GoldBigDragon_RPG.ETC.Monster();
			MC.ArmorGUIClick(event);return;
	    }
		else if(InventoryName.compareTo("��� ����")==0)
	    {
		    GoldBigDragon_RPG.GUI.EquipGUI EqGUI = new GoldBigDragon_RPG.GUI.EquipGUI();
			EqGUI.optionInventoryclick(event);return;
	    }
		else if(InventoryName.compareTo("�ɼ�")==0)
	    {
		    GoldBigDragon_RPG.GUI.OptionGUI OGUI = new GoldBigDragon_RPG.GUI.OptionGUI();
			OGUI.optionInventoryclick(event);return;
	    }
		else if(InventoryName.compareTo("�ش� ������ ĳ�� ���� ������")==0)
	    {
		    GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
		    AGUI.AreaBlockItemSettingGUIClick(event);return;
	    }

	    GoldBigDragon_RPG.GUI.ETCGUI EGUI = new GoldBigDragon_RPG.GUI.ETCGUI();
	    GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();
	    GoldBigDragon_RPG.GUI.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.GUI.OPBoxSkillGUI();
	    GoldBigDragon_RPG.GUI.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.GUI.PlayerSkillGUI();
		switch(InventoryName)
		{
			case "������ ���":
				PSKGUI.AddQuickBarGUIClick(event);
			break;
			case "�ý��� ����":
				JGUI.ChooseSystemGUIClick(event);
			break;
			case "��� ��� �Ͻðڽ��ϱ�?":
				QGUI.KeepGoingClick(event);
				break;
			case "���� ���":
			case "ä�� �ؾ� �� ���� ���":
			case "��� �ؾ� �� ���� ���":
				QGUI.ShowItemGUIInventoryClick(event); return;
			case "��Ÿ" : 
				EGUI.ETCInventoryclick(event);return;
			case "���̵�" : 
				EGUI.ETCInventoryclick(event); return;
			case "������Ʈ �߰�":
				QGUI.ObjectAddInventoryClick(event);return;
			default :
				if(InventoryName.contains("NPC"))
				{IC_NPC(event, InventoryName);return;}
				else if(InventoryName.contains("��Ƽ"))
				{IC_Party(event, InventoryName); return;}
				else if(InventoryName.contains("������"))
				{IC_Item(event, InventoryName);return;}
				else if(InventoryName.contains("����Ʈ"))
				{IC_Quest(event, InventoryName);return;}
				else if(InventoryName.contains("��ϵ�"))
				{JGUI.AddedSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("[MapleStory]"))
				{IC_MapleStory(event, InventoryName);return;}
				else if(InventoryName.contains("[Mabinogi]"))
				{IC_Mabinogi(event, InventoryName);return;}
				else if(InventoryName.contains("��ų"))
				{IC_Skill(event, InventoryName);return;}
				else if(InventoryName.contains("��ũ"))
				{SKGUI.SkillRankOptionGUIClick(event);return;}
				else if(InventoryName.contains("������"))
				{PSKGUI.MapleStory_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("ī�װ���"))
				{PSKGUI.Mabinogi_MainSkillsListGUIClick(event);return;}
				else if(InventoryName.contains("������"))
				{IC_OP(event, InventoryName);return;}
				else if(InventoryName.contains("�̺�Ʈ"))
				{IC_Event(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Area(event, InventoryName);return;}
				else if(InventoryName.contains("������"))
				{IC_Upgrade(event, InventoryName);return;}
				else if(InventoryName.contains("�ʽ���"))
				{IC_NewBie(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Monster(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_World(event, InventoryName);return;}
				else if(InventoryName.contains("����"))
				{IC_Warp(event, InventoryName);return;}
				else if(InventoryName.contains("��������"))
				{new OtherPlugins.SpellMain().ShowAllMaigcGUIClick(event);return;}
				else if(InventoryName.contains("ģ��"))
				{IC_Friend(event, InventoryName);return;}
				else if(InventoryName.contains("�׺�"))
				{IC_Navi(event, InventoryName);return;}
				return;
		}
		return;
	}
	
	private void IC_NPC(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NPC_GUI NPGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
    	if(InventoryName.compareTo("NPC ���� ����")==0)
    		NPGUI.NPCJobClick(event, ChatColor.stripColor(event.getInventory().getItem(18).getItemMeta().getLore().get(1)));
    	else if(InventoryName.contains("NPC"))
	    {
        	if(InventoryName.contains("[NPC]"))
    	    {
    			if(event.getInventory().getSize() <= 9)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 27)
    				NPGUI.NPCclickMain(event, InventoryName.split("C] ")[1]);
    			else if(event.getInventory().getSize() <= 54)
    				NPGUI.NPCclickMain(event,InventoryName.split("C] ")[1]);	
    	    }
        	else if(InventoryName.contains("����"))
			{
    			if(InventoryName.contains("����"))
    				NPGUI.WarpMainGUIClick(event);
    			else if(InventoryName.contains("���"))
    				NPGUI.WarperGUIClick(event);
			}
        	else if(InventoryName.contains("����"))
        	{
    			if(InventoryName.contains("����"))
    				NPGUI.UpgraderGUIClick(event);
    			else
    				NPGUI.SelectUpgradeRecipeGUIClick(event);
    				
        	}
        	else if(InventoryName.contains("����ĥ"))
				NPGUI.AddAbleSkillsGUIClick(event);
        	else if(InventoryName.contains("��"))
				NPGUI.RuneEquipGUIClick(event);
        	else if(InventoryName.contains("���"))
        	{
        		if(InventoryName.contains("����"))
            		NPGUI.TalkSettingGUIClick(event);
        		else
        			NPGUI.TalkGUIClick(event);
        	}
	    }
		return;
	}

	private void IC_Quest(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NPC_GUI NPGUI = new GoldBigDragon_RPG.GUI.NPC_GUI();
	    GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();

		if(InventoryName.compareTo("����Ʈ �ɼ�")==0)
			QGUI.QuestOptionClick(event);
		else if(InventoryName.compareTo("[����Ʈ]")==0)
			QGUI.QuestScriptTypeGUIClick(event);
		else if(InventoryName.contains("����"))
			NPGUI.QuestAcceptclickMain(event);
		else if(InventoryName.contains("��ü"))
			QGUI.OPboxAllQuestListInventoryclick(event);
		else if(InventoryName.contains("���"))
			NPGUI.NPCQuestclickMain(event);
		else if(InventoryName.contains("�帧��"))
			QGUI.FixQuestListInventoryclick(event);
		else if(InventoryName.contains("�׺�"))
			QGUI.Quest_NavigationListGUIClick(event);
		else if(InventoryName.contains("����"))
		{
			if(InventoryName.contains("Ȯ��"))
				QGUI.Quest_OPChoiceClick(event);
			else
				QGUI.Quest_UserChoiceClick(event);
		}
		else
			QGUI.MyQuestListInventoryclick(event);
		return;
	}

	private void IC_MapleStory(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();

		if(InventoryName.contains("��ü"))
			JGUI.MapleStory_ChooseJobClick(event);
		else if(InventoryName.contains("����"))
			JGUI.MapleStory_JobSettingClick(event);
		return;
	}

	private void IC_Mabinogi(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.JobGUI JGUI = new GoldBigDragon_RPG.GUI.JobGUI();

		if(InventoryName.contains("��ü"))
			JGUI.Mabinogi_ChooseCategoryClick(event);
		else if(InventoryName.contains("����"))
			JGUI.MapleStory_JobSettingClick(event);
		else if(InventoryName.contains("����"))
			JGUI.Mabinogi_SkillSettingClick(event);
		return;
	}

	private void IC_Skill(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.OPBoxSkillGUI SKGUI = new GoldBigDragon_RPG.GUI.OPBoxSkillGUI();
	    GoldBigDragon_RPG.GUI.PlayerSkillGUI PSKGUI = new GoldBigDragon_RPG.GUI.PlayerSkillGUI();

		if(InventoryName.contains("��ü"))
			SKGUI.AllSkillsGUIClick(event);
		else if(InventoryName.contains("����"))
			SKGUI.IndividualSkillOptionGUIClick(event);
		else if(InventoryName.contains("����"))
			PSKGUI.SkillListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    {
	    	GoldBigDragon_RPG.GUI.UseableItemGUI UIGUI = new GoldBigDragon_RPG.GUI.UseableItemGUI();
	    	UIGUI.SelectSkillGUIClick(event);
	    }
		return;
	}

	private void IC_OP(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.OPBoxGUI OPGUI = new GoldBigDragon_RPG.GUI.OPBoxGUI();

	    if(InventoryName.contains("���̵�"))
	    	OPGUI.OPBoxGuideInventoryclick(event);
	    else if(InventoryName.contains("����"))
	    	OPGUI.OPBoxGUIInventoryclick(event);
	    else if(InventoryName.contains("�ɼ�"))
	    	OPGUI.OPBoxGUI_SettingInventoryClick(event);
	    else if(InventoryName.contains("��������"))
	    	OPGUI.OPBoxGUI_BroadCastClick(event);
	    else if(InventoryName.contains("����"))
	    	OPGUI.OPBoxGUI_StatChangeClick(event);
	    else if(InventoryName.contains("ȭ��"))
	    	OPGUI.OPBoxGUI_MoneyClick(event);
	    return;
	}

	private void IC_Party(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.PartyGUI PGUI = new GoldBigDragon_RPG.GUI.PartyGUI();

	    if(InventoryName.compareTo("��Ƽ")==0)
	    	PGUI.partyInventoryclick(event);
		else if(InventoryName.contains("���"))
			PGUI.PartyListInventoryclick(event);
		else if(InventoryName.contains("���")||InventoryName.contains("��ü"))
			PGUI.PartyMemberInformationClick(event);
		return;
	}
	
	private void IC_Item(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.QuestGUI QGUI = new GoldBigDragon_RPG.GUI.QuestGUI();
	    GoldBigDragon_RPG.GUI.ItemGUI IGUI = new GoldBigDragon_RPG.GUI.ItemGUI();

	    if(InventoryName.contains("�Ҹ�")==true)
	    {
	    	GoldBigDragon_RPG.GUI.UseableItemGUI UIGUI = new GoldBigDragon_RPG.GUI.UseableItemGUI();
			if(InventoryName.contains("���"))
		    	UIGUI.UseableItemListGUIClick(event);
			else if(InventoryName.contains("Ÿ��"))
		    	UIGUI.ChooseUseableItemTypeGUIClick(event);
			else if(InventoryName.contains("��"))
		    	UIGUI.NewUseableItemGUIclick(event);
	    }
	    else
	    {
		    if(InventoryName.compareTo("��ƾ� �� ������ ���")==0||InventoryName.compareTo("������ ������ ���")==0)
		    {
				if(event.getSlot() == 8)
					event.getWhoClicked().closeInventory();
		    }
		    else  if(InventoryName.compareTo("���� ������ ���")==0)
				QGUI.SettingPresentClick(event);
		    else  if(InventoryName.contains("��"))
				IGUI.NewItemGUIclick(event);
		    else  if(InventoryName.compareTo("��ƾ� �� ������ ���")==0)
				QGUI.ShowItemGUIInventoryClick(event);
			else if(InventoryName.contains("���"))
				IGUI.ItemListInventoryclick(event);
	    }
		return;
	}

	private void IC_Area(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.AreaGUI AGUI = new GoldBigDragon_RPG.GUI.AreaGUI();
	    if(InventoryName.contains("����"))
			AGUI.AreaGUIInventoryclick(event);
	    else if(InventoryName.contains("��ü"))
	    	AGUI.AreaListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    {
		    if(InventoryName.contains("��ü"))
		    	AGUI.AreaMonsterSettingGUIClick(event);
		    else if(InventoryName.contains("����"))
		    	AGUI.AreaAddMonsterListGUIClick(event);
		    else if(InventoryName.contains("����"))
		    	AGUI.AreaAddMonsterSpawnRuleGUIClick(event);
		    else if(InventoryName.contains("Ư��"))
		    	AGUI.AreaSpawnSpecialMonsterListGUIClick(event);
	    }
	    else if(InventoryName.contains("Ư��ǰ"))
	    	AGUI.AreaBlockSettingGUIClick(event);
	    else if(InventoryName.contains("���"))
	    	AGUI.AreaFishSettingGUIClick(event);
	    else if(InventoryName.contains("�����"))
	    	AGUI.AreaMusicSettingGUIClick(event);
		return;
	}

	private void IC_Upgrade(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.UpGradeGUI UGUI = new GoldBigDragon_RPG.GUI.UpGradeGUI();
	    if(InventoryName.contains("���"))
	    	UGUI.UpgradeRecipeGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	UGUI.UpgradeRecipeSettingGUIClick(event);
		return;
	}	
	
	private void IC_NewBie(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NewBieGUI NGUI = new GoldBigDragon_RPG.GUI.NewBieGUI();
	    if(InventoryName.contains("�ɼ�"))
	    	NGUI.NewBieGUIMainInventoryclick(event);
	    else if(InventoryName.contains("����")||InventoryName.contains("���̵�"))
	    	NGUI.NewBieSupportItemGUIInventoryclick(event);
	    else if(InventoryName.contains("�⺻��"))
	    	NGUI.NewBieQuestGUIInventoryclick(event);
		return;
	}
	
	private void IC_Monster(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.MonsterGUI MGUI = new GoldBigDragon_RPG.GUI.MonsterGUI();
	    if(InventoryName.contains("���"))
	    	MGUI.MonsterListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	MGUI.MonsterOptionSettingGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	MGUI.MonsterPotionGUIClick(event);
		return;
	}	
	
	private void IC_World(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.WorldCreateGUI WGUI = new GoldBigDragon_RPG.GUI.WorldCreateGUI();
	    if(InventoryName.contains("����"))
	    	WGUI.WorldCreateGUIClick(event);
		return;
	}	

	private void IC_Warp(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.WarpGUI WGUI = new GoldBigDragon_RPG.GUI.WarpGUI();
	    if(InventoryName.contains("���"))
	    	WGUI.WarpListGUIInventoryclick(event);
		return;
	}
	
	private void IC_Event(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.EventGUI EGUI = new GoldBigDragon_RPG.GUI.EventGUI();
	    if(InventoryName.contains("����"))
	    	EGUI.AllPlayerGiveEventGUIclick(event);
		else if(InventoryName.contains("����"))
			EGUI.EventGUIInventoryclick(event);
		return;
	}		

	private void IC_Friend(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.ETCGUI EGUI = new GoldBigDragon_RPG.GUI.ETCGUI();
	    if(InventoryName.contains("���"))
	    	EGUI.FriendsGUIclick(event);
	    if(InventoryName.contains("��û"))
	    	EGUI.WaittingFriendsGUIclick(event);
		return;
	}

	private void IC_Navi(InventoryClickEvent event, String InventoryName)
	{
	    GoldBigDragon_RPG.GUI.NavigationGUI NGUI = new GoldBigDragon_RPG.GUI.NavigationGUI();
	    if(InventoryName.contains("���"))
	    	NGUI.NavigationListGUIClick(event);
	    else if(InventoryName.contains("����"))
	    	NGUI.NavigationOptionGUIClick(event);
	    else if(InventoryName.contains("���"))
	    	NGUI.UseNavigationGUIClick(event);
		return;
	}
}