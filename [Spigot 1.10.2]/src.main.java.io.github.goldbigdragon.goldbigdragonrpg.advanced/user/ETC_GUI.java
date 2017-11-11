package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import admin.Navigation_GUI;
import effect.SoundEffect;
import skill.UserSkill_GUI;
import util.Util_GUI;
import util.YamlLoader;

public final class ETC_GUI extends Util_GUI
{
	public void ETCGUI_Main(Player player)
	{
		String UniqueCode = "��0��0��0��0��2��r";
		Inventory inv = Bukkit.createInventory(null, 45, UniqueCode + "��0��Ÿ");

		Stack2("��f����", 397,3,1,Arrays.asList("��7������ Ȯ���մϴ�."), 0, inv);
		Stack2("��f��ų", 403,0,1,Arrays.asList("��7��ų�� Ȯ���մϴ�."), 9, inv);
		Stack2("��f����Ʈ", 358,0,1,Arrays.asList("��7���� �������� ����Ʈ�� Ȯ���մϴ�."), 18, inv);
		Stack2("��f�ɼ�", 145,0,1,Arrays.asList("��7��Ÿ ������ �մϴ�."), 27, inv);
		Stack2("��f��Ÿ", 160,4,1,Arrays.asList("��7��Ÿ ������ Ȯ���մϴ�."), 36, inv);
		
		Stack2("��c ", 66,0,1,Arrays.asList(""), 1, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 7, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 10, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 16, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 19, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 25, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 28, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 34, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 37, inv);
		Stack2("��c ", 66,0,1,Arrays.asList(""), 43, inv);

		Stack2("��f��l���̵�", 340,0,1,Arrays.asList("��7������ ���� ������ �˾ƺ��ϴ�."), 2, inv);
		Stack2("��f��l��Ƽ", 389,0,1,Arrays.asList("��7��Ƽ�� ���� ������ Ȯ���մϴ�."), 3, inv);
		Stack2("��f��l����", 345,0,1,Arrays.asList("��7���� ������ ������ Ȯ���մϴ�."), 4, inv);
		Stack2("��f��lģ��", 397,3,1,Arrays.asList("��7ģ�� ����� Ȯ���մϴ�."), 5, inv);
		Stack2("��f��l�׺���̼�", 358,3,1,Arrays.asList("��7������ ������ �׺���̼���","��7���� ��ŵ�ϴ�."), 6, inv);
		
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�."), 26, inv);

		player.openInventory(inv);
	}

	public void Information(Player player)
	{
		String UniqueCode = "��0��0��0��0��3��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0���̵�");
	  	YamlLoader NewBieYM = new YamlLoader();
		NewBieYM.getConfig("ETC/NewBie.yml");

		if(NewBieYM.contains("Guide")==false)
		{
			NewBieYM.createSection("Guide");
			NewBieYM.saveConfig();
		}
		Object[] a= NewBieYM.getConfigurationSection("Guide").getKeys(false).toArray();

		byte loc = 0;
		for(int count = 0; count < a.length;count++)
			if(NewBieYM.getItemStack("Guide."+count) != null)
			{
				ItemStackStack(NewBieYM.getItemStack("Guide."+count), loc, inv);
				loc++;
			}
		Stack2("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack2("��f��l�ݱ�", 324,0,1,Arrays.asList("��7�۾� ������ â�� �ݽ��ϴ�."), 53, inv);
		
		player.openInventory(inv);
	}
	
	public void FriendsGUI(Player player, short page)
	{
		String UniqueCode = "��0��0��0��0��4��r";
	  	YamlLoader FriendsList = new YamlLoader();
		FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
	  	YamlLoader SideFriendsList = new YamlLoader();
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.createSection("Friends");
			FriendsList.createSection("Waitting");
			FriendsList.saveConfig();
		}
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0ģ�� ��� : " + (page+1));

		if(FriendsList.getConfigurationSection("Waitting").getKeys(false).size()!=0)
			Stack("��f��lģ�� ��û", 386,0,1,Arrays.asList("��7ģ�� ��û�� ��� �� �����Դϴ�!","","��3[   ������� ��û   ]","��f��l"+FriendsList.getConfigurationSection("Waitting").getKeys(false).size()+"��3 ��"), 52, inv);
		
		Object[] Friends= FriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Friends."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			
			day = (byte) (WaitingTime/86400);
			WaitingTime = WaitingTime-(86400*day);

			String TimeSetting=day+"�� ° ģ�� ���� ���� ��";
			
			if(target!=null)
			{
				Stack2("��e��l"+target.getName(), 166, 0, 1, Arrays.asList("��7[   ��������   ]","","��c[Shift + �� Ŭ���� ģ�� ����]","","","��6"+TimeSetting), loc, inv);
				SideFriendsList.getConfig("Friend/"+target.getUniqueId().toString()+".yml");
				Object[] SideFriends = SideFriendsList.getConfigurationSection("Friends").getKeys(false).toArray();
				for(int counter=0;counter<SideFriends.length;counter++)
				{
					if(SideFriends[counter].equals(player.getName()))
						if(target.isOnline())
						{
							ItemStackStack(getPlayerSkull("��e��l"+target.getName(), 1, Arrays.asList("��3[   �¶���   ]","","��3[����] : ��f"+target.getLocation().getWorld().getName(),
							"��3[��ǥ] : ��f"+(int)target.getLocation().getX()+","+(int)target.getLocation().getY()+","+(int)target.getLocation().getZ(),
							"","��c[Shift + �� Ŭ���� ģ�� ����]","","","��6"+TimeSetting), target.getName()), loc, inv);
							break;
						}
				}
			}
			else
				Stack2("��e��l"+Friends[count].toString(), 166, 0, 1, Arrays.asList("��7[   ��������   ]","","��c[Shift + �� Ŭ���� ģ�� ����]","","","��6"+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 48, inv);
		Stack("��f��l�� ģ��", 397,3,1,Arrays.asList("��7���ο� ģ���� �߰��մϴ�."), 49, inv);
		Stack("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	public void WaittingFriendsGUI(Player player, short page)
	{
	  	YamlLoader FriendsList = new YamlLoader();
		FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
		if(FriendsList.contains("Name")==false)
		{
			FriendsList.set("Name", player.getName());
			FriendsList.createSection("Friends");
			FriendsList.createSection("Waitting");
			FriendsList.saveConfig();
		}
		String UniqueCode = "��0��0��0��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0ģ�� ��û : " + (page+1));

		Object[] Friends= FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray();
		byte loc=0;
		Long nowTime = new util.ETC().getNowUTC();
		for(int count = page*45; count < Friends.length;count++)
		{
			if(loc >= 45) break;
			Player target = (Player) Bukkit.getServer().getPlayer(Friends[count].toString());
			Long AcceptedTime = FriendsList.getLong("Waitting."+Friends[count].toString());
			Long WaitingTime = (nowTime-AcceptedTime)/1000;
			byte day = 0;
			byte week = 0;
			String TimeSetting=null;
			
			if(WaitingTime >= 2419200)
				TimeSetting = "���� ��";
			else
			{
				week = (byte)(WaitingTime/604800);
				WaitingTime = WaitingTime-(604800*week);

				day = (byte) (WaitingTime/86400);
				WaitingTime = WaitingTime-(86400*day);

				if(week>0)
					TimeSetting = week+"�� ��";
				else if(day>=0)
					if(day==0)
						TimeSetting = "����";
					else
						TimeSetting = day+"�� ��";
			}
			if(TimeSetting == null)
				TimeSetting = "�� �� ����";
			
			if(target!=null)
			{
				Stack2("��e��l"+target.getName(), 166, 0, 1, Arrays.asList("��7[   ��������   ]","","��e[�� Ŭ���� ģ�� ����]","��c[Shift + �� Ŭ���� ����]","","","��6��û�� : "+TimeSetting), loc, inv);
				if(target.isOnline())
				{
					ItemStackStack(getPlayerSkull("��e��l"+target.getName(), 1, Arrays.asList("��3[   �¶���   ]","","��e[�� Ŭ���� ģ�� ����]","��c[Shift + �� Ŭ���� ����]","","","��6��û�� : "+TimeSetting), target.getName()), loc, inv);
					break;
				}
			}
			else
				Stack2("��e��l"+Friends[count].toString(), 166, 0, 1, Arrays.asList("��7[   ��������   ]","","��e[�� Ŭ���� ģ�� ����]","��c[Shift + �� Ŭ���� ����]","","","��6��û�� : "+TimeSetting), loc, inv);
			
			loc++;
		}
		
		if(Friends.length-(page*44)>45)
		Stack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 50, inv);
		if(page!=0)
		Stack("��f��l���� ������", 323,0,1,Arrays.asList("��7���� �������� �̵� �մϴ�."), 48, inv);
		Stack("��f��l���� ���", 323,0,1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), 45, inv);
		Stack("��f��l�ݱ�", 324,0,1,Arrays.asList("��7â�� �ݽ��ϴ�."), 53, inv);
		player.openInventory(inv);
	}
	
	
	
	
	public void ETCInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 26)//�ݱ�
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 0)
				new user.Stats_GUI().StatusGUI(player);
			else if(slot == 9)
				new UserSkill_GUI().MainSkillsListGUI(player, (short) 0);
			else if(slot == 18)
				new quest.Quest_GUI().MyQuestListGUI(player, (short) 0);
			else if(slot == 27)
				new user.Option_GUI().optionGUI(player);
			else if(slot == 36)
				new ETC_GUI().ETCGUI_Main(player);
			else
			{
				if(slot == 2)//���̵�
					Information(player);
				else if(slot ==3)//��Ƽ
					new party.Party_GUI().PartyGUI_Main(player);
				else if(slot ==4)//����
					new warp.Warp_GUI().WarpListGUI(player, 0);
				else if(slot ==5)//ģ��
					FriendsGUI(player, (short) 0);
				else if(slot ==6)//�׺���̼�
					new Navigation_GUI().useNavigationGui(player, (short) 0);
			}
		}
		return;
	}

	public void GuideInventoryclick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		if(slot == 45)//���� ���
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			ETCGUI_Main(player);
		}
		else if(slot == 53)//�ݱ�
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else if(slot == 36)//��Ʃ�� ������
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			player.sendMessage("��4��l[YouTube] ��f��l: ��9��lhttps://www.youtube.com/playlist?list=PLxqihkJXVJABIlxU3n6bNhhC8x6xPbORP   ��e��l[Ŭ���� ���̵� �������� ����˴ϴ�]");
			player.closeInventory();
		}
		return;
	}
	
	public void FriendsGUIclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();
		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int slot = event.getSlot();
		
		if(slot == 53)//�ݱ�
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//���� ���
				ETCGUI_Main(player);
			else if(slot == 52)//ģ�� ��û
				WaittingFriendsGUI(player, (short) 0);
			else if(slot == 48)//���� ������
				FriendsGUI(player, (short) (page-1));
			else if(slot == 49)//�� ģ��
			{
				player.closeInventory();
				player.sendMessage("��a[ģ��] : ģ�� ��û�� �Ͻ� ������ �г����� �Է� �ϼ���!");
				new UserData_Object().setTemp(player, "FA");
			}
			else if(slot == 50)//���� ������
				FriendsGUI(player, (short) (page+1));
			else if(event.isShiftClick()&&event.isRightClick())
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlLoader FriendsList = new YamlLoader();
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				FriendsList.removeKey("Friends."+FName);
				FriendsList.saveConfig();
				SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 0.7F);
				player.sendMessage("��d[ģ��] : ��e"+FName+"��d���� ģ�� ��Ͽ��� �����Ͽ����ϴ�!");
				FriendsGUI(player, (short) page);
			}
		}
	}

	public void WaittingFriendsGUIclick(InventoryClickEvent event)
	{
		
		Player player = (Player) event.getWhoClicked();

		int page =  Integer.parseInt(event.getInventory().getTitle().split(" : ")[1])-1;
		int slot = event.getSlot();
		
		if(slot == 53)//�ݱ�
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//���� ���
				FriendsGUI(player, (short) 0);
			else if(slot == 48)//���� ������
				WaittingFriendsGUI(player, (short) (page-1));
			else if(slot == 50)//���� ������
				WaittingFriendsGUI(player, (short) (page+1));
			else
			{
				String FName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());
			  	YamlLoader FriendsList = new YamlLoader();
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if(event.isShiftClick()&&event.isRightClick())
				{
					FriendsList.removeKey("Waitting."+FName);
					FriendsList.saveConfig();
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 0.7F);
				}
				else if(event.isLeftClick()&&!event.isShiftClick())
					new user.Equip_GUI().SetFriends(player, Bukkit.getPlayer(FName));
				FriendsList.getConfig("Friend/"+player.getUniqueId().toString()+".yml");
				if(FriendsList.getConfigurationSection("Waitting").getKeys(false).toArray().length == 0)
					FriendsGUI(player, (short) 0);
				else
					WaittingFriendsGUI(player, (short) page);
			}
		}
	}
}