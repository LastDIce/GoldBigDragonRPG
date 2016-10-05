package GBD_RPG.Admin;

import java.util.Arrays;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import GBD_RPG.User.UserData_Object;
import GBD_RPG.Util.Util_GUI;
import GBD_RPG.Util.YamlController;
import GBD_RPG.Util.YamlManager;

public class NewBie_GUI extends Util_GUI
{
	public void NewBieGUIMain(Player player)
	{
		String UniqueCode = "��0��0��1��1��7��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0�ʽ��� �ɼ�");

		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�⺻ ������", 54,0,1,Arrays.asList(ChatColor.GRAY + "ù ���ӽ� �������� �����մϴ�."), 2, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�⺻ ����Ʈ", 386,0,1,Arrays.asList(ChatColor.GRAY + "���� ���ڸ��� ����Ʈ�� �ݴϴ�.",ChatColor.GRAY+"(������ Ʃ�丮�� �Դϴ�.)","",ChatColor.DARK_AQUA+"[   �⺻ ����Ʈ   ]",ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getString("FirstQuest"),"",ChatColor.YELLOW+"[Ŭ���� ����Ʈ�� �����մϴ�.]",ChatColor.RED+"[Shift + �� Ŭ���� ����Ʈ�� �����մϴ�.]"), 3, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�⺻ ���� ��ġ", 368,0,1,Arrays.asList(ChatColor.GRAY + "���� ���ڸ��� �̵� ��ŵ�ϴ�.","",ChatColor.DARK_AQUA+"[   ���� ��ġ   ]",ChatColor.DARK_AQUA+" - ���� : "+ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getString("TelePort.World")
		,ChatColor.DARK_AQUA+" - ��ǥ : "+ChatColor.WHITE+""+ChatColor.BOLD+NewBieYM.getInt("TelePort.X")+","+NewBieYM.getInt("TelePort.Y")+","+NewBieYM.getInt("TelePort.Z"),"",ChatColor.YELLOW+"[Ŭ���� ���� ��ġ�� ��� �˴ϴ�.]"), 4, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���̵�", 340,0,1,Arrays.asList(ChatColor.GRAY + "���̵�â�� �����մϴ�.","",ChatColor.GRAY+"/��Ÿ",ChatColor.DARK_GRAY+"���ɾ ���� ������",ChatColor.DARK_GRAY+"���̵带 Ȯ���Ͻ� �� �ֽ��ϴ�."), 5, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 0, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 8, inv);
		player.openInventory(inv);
	}
	
	public void NewBieSupportItemGUI(Player player)
	{
		String UniqueCode = "��1��0��1��1��8��r";
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�ʽ��� ����");

		Object[] a= NewBieYM.getConfigurationSection("SupportItem").getKeys(false).toArray();

		byte num = 0;
		for(byte count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("SupportItem."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("SupportItem."+count), num, inv);
				num++;
			}

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 46, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 47, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�⺻ ������", 266,0,1,Arrays.asList(ChatColor.GRAY + "�⺻������ ������ �ִ� ���� �����մϴ�.","",ChatColor.YELLOW+"[���� ������]",ChatColor.WHITE+""+ChatColor.BOLD+""+NewBieYM.getInt("SupportMoney")),49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 50, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 51, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 52, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void NewBieQuestGUI(Player player, short page)
	{
		String UniqueCode = "��0��0��1��1��9��r";
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager QuestList  = YC.getNewConfig("Quest/QuestList.yml");
		
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�ʽ��� �⺻�� ���� : " + (page+1));

		Object[] a = QuestList.getKeys().toArray();
		
		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			String QuestName = a[count].toString();
			Set<String> QuestFlow= QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false);
			if(count > a.length || loc >= 45) break;
			switch(QuestList.getString(a[count].toString() + ".Type"))
			{
				case "N" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 340,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ϲ� ����Ʈ",""), loc, inv);
					break;
				case "R" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 386,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �ݺ� ����Ʈ",""), loc, inv);
					break;
				case "D" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,1,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "W" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,7,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : ���� ����Ʈ",""), loc, inv);
					break;
				case "M" :
					Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + QuestName, 403,0,31,Arrays.asList(ChatColor.WHITE+"����Ʈ ���� ��� : "+QuestFlow.size()+"��",ChatColor.DARK_AQUA+"����Ʈ Ÿ�� : �Ѵ� ����Ʈ",""), loc, inv);
					break;
			}
			loc++;
		}
		
		if(a.length-(page*44)>45)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ������", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� �������� �̵� �մϴ�."), 48, inv);

		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	
	public void NewBieGuideGUI(Player player)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.createSection("Guide");
			NewBieYM.saveConfig();
		}

		String UniqueCode = "��1��0��1��1��a��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�ʽ��� ���̵�");

		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte num = 0;
		for(short count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), num, inv);
				num++;
			}

		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 46, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 47, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 48, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 49, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 50, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 51, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "-", 166,0,1,null, 52, inv);
		
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "���� ���", 323,0,1,Arrays.asList(ChatColor.GRAY + "���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack(ChatColor.WHITE + "" + ChatColor.BOLD + "�ݱ�", 324,0,1,Arrays.asList(ChatColor.GRAY + "â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	
	public void NewBieGUIMainInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		if(slot == 8)//�ݱ�
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			if(slot == 3)//�⺻ ����Ʈ
			{
				if(event.isLeftClick())
					NewBieQuestGUI(player, (short) 0);
				else if(event.isRightClick()&&event.isShiftClick())
				{
					s.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
					NewBieYM.set("FirstQuest", "null");
					NewBieYM.saveConfig();
					NewBieGUIMain(player);
				}
			}
			else
			{
				s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
				if(slot == 0)//���� ���
					new GBD_RPG.Admin.OPbox_GUI().OPBoxGUI_Main(player, (byte) 2);
				else if(slot == 2)//�⺻ ������
					NewBieSupportItemGUI(player);
				else if(slot == 4)//�⺻ ���� ��ġ
				{
					YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
					YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
					NewBieYM.set("TelePort.World", player.getLocation().getWorld().getName());
					NewBieYM.set("TelePort.X", player.getLocation().getX());
					NewBieYM.set("TelePort.Y", player.getLocation().getY());
					NewBieYM.set("TelePort.Z", player.getLocation().getZ());
					NewBieYM.set("TelePort.Pitch", player.getLocation().getPitch());
					NewBieYM.set("TelePort.Yaw", player.getLocation().getYaw());
					NewBieYM.saveConfig();
					player.sendMessage(ChatColor.GREEN+"[���� �ڷ���Ʈ����] : ���ӽ� �̵� ��ġ ���� �Ϸ�!");
					NewBieGUIMain(player);
				}
				else if(slot == 5)//���̵�
					NewBieGuideGUI(player);
			}
		}
	}
	
	public void NewBieSupportItemGUIInventoryclick(InventoryClickEvent event, String SubjectCode)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		if(slot >= 45)
			event.setCancelled(true);
		if(slot == 53)//�ݱ�
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//���� ���
				NewBieGUIMain(player);
			else if(slot == 49)//�⺻ ����
			{
				if(SubjectCode.compareTo("1a")!=0)//�ʽ��� ���̵� ����â�� �ƴ� ���
				{
					s.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
					player.closeInventory();
					player.sendMessage(ChatColor.DARK_AQUA+"[���� ������] : �󸶸� ������ �����ϵ��� �Ͻðڽ��ϱ�?");
					player.sendMessage(ChatColor.DARK_AQUA+"(0 ~ "+Integer.MAX_VALUE+")");
					UserData_Object u = new UserData_Object();
					u.setType(player, "NewBie");
					u.setString(player, (byte)1, "NSM");
				}
			}
		}
	}
	
	public void NewBieQuestGUIInventoryclick(InventoryClickEvent event)
	{
		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();
		
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();
		
		if(slot == 53)
		{
			s.SP(player, Sound.BLOCK_PISTON_CONTRACT, 1.0F, 1.0F);
			player.closeInventory();
		}
		else
		{
			s.SP(player, Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
			if(slot == 45)//���� ���
				NewBieGUIMain(player);
			else if(slot == 48)//���� ������
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-2));
			else if(slot == 50)//���� ������
				NewBieQuestGUI(player, (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])));
			else
			{
				String QuestName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
				YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
				YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
				YamlManager QuestList=YC.getNewConfig("Quest/QuestList.yml");
				
				if(QuestList.contains(QuestName)==true)
				{
					if(QuestList.getConfigurationSection(QuestName+".FlowChart").getKeys(false).toArray().length != 0)
					{
						NewBieYM.set("FirstQuest", QuestName);
						NewBieYM.saveConfig();
						NewBieGUIMain(player);
					}
					else
					{
						s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage(ChatColor.RED+"[���� ����Ʈ] : �ش� ����Ʈ�� ����Ʈ ������Ʈ�� �������� �ʽ��ϴ�!");
					}
				}
				else
				{
					s.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
					player.sendMessage(ChatColor.RED+"[���� ����Ʈ] : �ش� ����Ʈ�� �������� �ʽ��ϴ�!");
				}
			}
		}
	}

	public void InventoryClose_NewBie(InventoryCloseEvent event, String SubjectCode)
	{
		YamlController YC = new YamlController(GBD_RPG.Main_Main.Main_Main.plugin);
		YamlManager NewBieYM = YC.getNewConfig("ETC/NewBie.yml");
		short num = 0;
		for(int count = 0; count < 45;count++)
		{
			if(event.getInventory().getItem(count) != null)
			{
				if(SubjectCode.compareTo("1a")==0)//���̵�
					NewBieYM.set("Guide."+num, event.getInventory().getItem(count));
				else//if(SubjectCode.compareTo("18")==0)//���� ������
					NewBieYM.set("SupportItem."+num, event.getInventory().getItem(count));
				num++;
			}
			else
				if(SubjectCode.compareTo("1a")==0)//���̵�
					NewBieYM.removeKey("Guide."+count);
				else//if(SubjectCode.compareTo("18")==0)//���� ������
					NewBieYM.removeKey("SupportItem."+count);
		}
		NewBieYM.saveConfig();

		GBD_RPG.Effect.Effect_Sound s = new GBD_RPG.Effect.Effect_Sound();

		if(SubjectCode.compareTo("1a")==0)//���̵�
			event.getPlayer().sendMessage(ChatColor.GREEN + "[���� ���̵�] : ���������� ���� �Ǿ����ϴ�!");
		else//if(SubjectCode.compareTo("18")==0)//���� ������
			event.getPlayer().sendMessage(ChatColor.GREEN + "[���� ������] : ���������� ���� �Ǿ����ϴ�!");
		s.SP((Player) event.getPlayer(), Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F);
	}
}