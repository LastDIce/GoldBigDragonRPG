package structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import effect.SoundEffect;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;

public class StructBoard extends UtilGui
{
	public void BoardMainGUI(Player player, String BoardCode, byte page)
	{
		YamlLoader Board = new YamlLoader();
		Board.getConfig("Structure/"+BoardCode+".yml");
		String UniqueCode = "��0��0��d��0��5��r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "��0�Խ��� : "+(page+1));
		
		if(Board.contains("Post_Number")==false)
		{
			Board.set("Post_Number", 0);
			Board.createSection("User");
			Board.set("Notice","null");
			Board.set("OnlyUseOP",false);
			Board.saveConfig();
		}
		int postNumber = Board.getInt("Post_Number");
		
		byte loc=10;
		short count = 0;
		int AllPost = 0;
		
		for(int Post = postNumber; Post >= 0; Post--)
			if(Board.contains("User."+Post+".User")==true)
				AllPost++;
		if(page!=0)
		{
			for(int Post = postNumber; Post >= 0; Post--)
			{
				if(Board.contains("User."+Post+".User")==true)
					count++;
				if(count > 28*page)
				{
					postNumber = Post;
					break;
				}
			}
		}
		count = 0;
		
		for(int Post = postNumber;Post >= 0; Post--)
		{
			if(count>28)
				break;
			if(Board.contains("User."+Post+".User")==true)
			{
				String PostUser = Board.getString("User."+Post+".User");
				String PostTitle = Board.getString("User."+Post+".Title");
				String PostMemo = Board.getString("User."+Post+".Memo");
				long PostUTC = Board.getLong("User."+Post+".UTC");
				
				String PostedTime = new util.ETC().getFrom(new servertick.ServerTickMain().nowUTC, PostUTC);

				List<String> Memo = new ArrayList<String>();
				Memo.add("");
				Memo.add("��9���� : ��f"+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add("��f"+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add("��f"+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add("��9�ۼ��� : ��f"+PostUser);
				Memo.add("��9�Խñ� ��ȣ : ��f"+Post);
				Memo.add("");
				Memo.add("��e[Shift �� Ŭ���� �����]");
				Memo.add("��0"+Post);
				Stack2("��6��l"+PostedTime +"�� �ۼ��� �Խñ�", 358,(byte)0,(byte)1,Memo, (byte)loc, inv);
				if(loc==16||loc==25||loc==34||loc==43)
					loc = (byte) (loc+3);
				else
					loc++;
				count=(short) (count+1);
			}
		}
		Stack2("��c ", 160,(byte)12,(byte)1,Arrays.asList(BoardCode), (byte)0, inv);
		
		for(int count2 = 1; count2 < 9; count2++)
			Stack2("��c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 44; count2 < 54; count2++)
			Stack2("��c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 9; count2 < 45; count2=(byte) (count2+9))
			Stack2("��c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 17; count2 < 54; count2=(byte) (count2+9))
			Stack2("��c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);

		if(!Board.getString("Notice").equals("null"))
		{
			List<String> Memo = new ArrayList<String>();
			for(int count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add("��f"+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add("��f"+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			Stack2("��f��l[�Խ��� �˸�]", 321,(byte)0,(byte)1,Memo, (byte)4, inv);
		}
		
		if(AllPost>(28*page)+28)
			Stack2("��f��l���� ������", 323,(byte)0,(byte)1,Arrays.asList("��7���� �������� �̵� �մϴ�."), (byte)50, inv);
		if(page!=0)
			Stack2("��f��l���� ������", 323,(byte)0,(byte)1,Arrays.asList("��7���� �������� �̵� �մϴ�."), (byte)48, inv);

		Stack2("��f��l�� �Խñ�", 386,(byte)0,(byte)1,Arrays.asList("��7���ο� �Խñ��� �ۼ��մϴ�."), (byte)49, inv);
		player.openInventory(inv);
		return;
	}
	
	public void BoardSettingGUI(Player player, String BoardCode)
	{
		YamlLoader Board = new YamlLoader();
		Board.getConfig("Structure/"+BoardCode+".yml");

		String UniqueCode = "��0��0��d��0��6��r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "��0�Խ��� ����");
		
		if(Board.contains("Post_Number")==false)
		{
			Board.set("Post_Number", 0);
			Board.createSection("User");
			Board.set("Notice","null");
			Board.set("OnlyUseOP",false);
			Board.saveConfig();
		}
		if(Board.getString("Notice").equals("null"))
			Stack2("��f��l[�Խ��� �˸�]", 166,(byte)0,(byte)1,Arrays.asList("��c[�Խ��� �˸� ����]"), (byte)2, inv);
		else
		{
			List<String> Memo = new ArrayList<String>();
			for(int count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add("��f"+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add("��f"+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			Stack2("��f��l[�Խ��� �˸�]", 321,(byte)0,(byte)1,Memo, (byte)2, inv);
		}

		if(Board.getBoolean("OnlyUseOP"))
			Stack2("��f��l[��� ����]", 137,(byte)0,(byte)1,Arrays.asList("��9[������ ����]"), (byte)4, inv);
		else
			Stack2("��f��l[��� ����]", 397,(byte)3,(byte)1,Arrays.asList("��a[��ü �̿�]"), (byte)4, inv);
			
		Stack2("��f��l[�Խñ� ��ü ����]", 325,(byte)0,(byte)1,Arrays.asList("��7�Խ��ǿ� �ٿ��� ��� �Խñ���","��7�����մϴ�."), (byte)6, inv);

		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)1, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)3, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)5, inv);
		Stack2(BoardCode, 160,(byte)8,(byte)1,null, (byte)7, inv);
		
		Stack2("��f��l���� ���", 323,(byte)0,(byte)1,Arrays.asList("��7���� ȭ������ ���ư��ϴ�."), (byte)0, inv);
		Stack2("��f��l�ݱ�", 324,(byte)0,(byte)1,Arrays.asList("��7â�� �ݽ��ϴ�."), (byte)8, inv);
		player.openInventory(inv);
	}
	
	
	public void BoardMainGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		

		byte page =  (byte) (Byte.parseByte(event.getInventory().getTitle().split(" : ")[1])-1);
		String Code = event.getInventory().getItem(0).getItemMeta().getLore().get(0);
		if(slot == 48 || slot == 50)
		{
			if(event.getCurrentItem().getTypeId()==323)
			{
				SoundEffect.SP(player, Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 0.8F, 1.0F);
				if(slot == 48)//���� ������
					BoardMainGUI(player, Code, (byte) (page-1));
				else if(slot == 50)//���� ������
					BoardMainGUI(player, Code, (byte) (page+1));
			}
		}
		else if(slot == 49)//�� �Խñ�
		{
			YamlLoader Board = new YamlLoader();
			Board.getConfig("Structure/"+Code+".yml");
			if(Board.getBoolean("OnlyUseOP")&&player.isOp()==false)
			{
				SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("��c[�Խ���] : �Խñ� �ۼ� ������ �����ϴ�!");
				return;
			}
			UserDataObject u = new UserDataObject();
			SoundEffect.SP(player, Sound.BLOCK_CLOTH_STEP, 0.8F, 1.8F);
			u.setTemp(player, "Structure");
			u.setType(player, "Board");
			u.setString(player, (byte)0, "Title");
			u.setString(player, (byte)1, "��f���� ����");//�Խñ� ����
			u.setString(player, (byte)2, "��f���� ����");//�Խñ� ����
			u.setString(player, (byte)3, Code);//�Խ��� �ڵ�
			player.closeInventory();
			player.sendMessage("��a[�Խ���] : �Խñ� ������ �Է� �� �ּ���.");
		}
		else if((slot>=10&&slot<=16)||(slot>=19&&slot<=25)||
				(slot>=28&&slot<=34)||(slot>=37&&slot<=43))
		{
			if(event.getCurrentItem().getTypeId()!=358)
				return;
			if(event.isRightClick()&&event.isShiftClick())
			{
				YamlLoader Board = new YamlLoader();
				Board.getConfig("Structure/"+Code+".yml");
				short PostNumber = Short.parseShort(ChatColor.stripColor((event.getCurrentItem().getItemMeta().getLore().get(event.getCurrentItem().getItemMeta().getLore().size()-1))));
				if(Board.contains("User."+PostNumber))
				{
					if(Board.getString("User."+PostNumber+".User").equals(player.getName())||player.isOp())
					{
						SoundEffect.SP(player, Sound.ENTITY_SHEEP_SHEAR, 1.0F, 1.5F);
						Board.removeKey("User."+PostNumber);
						Board.saveConfig();
						BoardMainGUI(player, Code, page);
					}
					else
					{
						SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("��c[�Խ���] : �ڽ��� �ۼ��� �Խñ۸� ������ �� �ֽ��ϴ�.");
					}
				}
				else
					BoardMainGUI(player, Code, page);
			}
		}
	}

	public void BoardSettingGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();
		
		
		if(slot == 8)//������
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String Code = event.getInventory().getItem(1).getItemMeta().getDisplayName();
			if(slot == 0)//���� ���
				new structure.StructureGui().StructureListGUI(player, 0);
			else if(slot == 2)//�Խ��� �˸� ����
			{
				UserDataObject u = new UserDataObject();
				u.setTemp(player, "Structure");
				u.setType(player, "Board");
				u.setString(player, (byte)0, "Notice");
				u.setString(player, (byte)1, Code);//�Խ��� �ڵ�
				player.closeInventory();
				player.sendMessage("��a[�Խ���] : �Խ��� �˸��� �Է� �� �ּ���.");
			}
			else if(slot == 4)//�Խ��� ����
			{
				YamlLoader Board = new YamlLoader();
				Board.getConfig("Structure/"+Code+".yml");
				if(Board.getBoolean("OnlyUseOP"))
					Board.set("OnlyUseOP", false);
				else
					Board.set("OnlyUseOP", true);
				Board.saveConfig();
				BoardSettingGUI(player, Code);
			}
			else if(slot == 6)//�Խ��� ����
			{
				YamlLoader Board = new YamlLoader();
				Board.getConfig("Structure/"+Code+".yml");
				Board.removeKey("User");
				Board.set("Post_Number", 0);
				Board.createSection("User");
				Board.saveConfig();
			}
		}
	}

	
	public String CreateBoard(int LineNumber, String StructureCode, byte Direction)
	{
		switch(Direction)
		{
		case 1://��
		case 3://��
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.216 ~0.57 ~-0.20 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.5 ~1.07 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.5 ~1.896 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.216 ~2.288 ~-0.26 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~-0.216 ~0.57 ~1.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~-0.5 ~1.07 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~-0.5 ~1.896 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~-0.216 ~2.288 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //�Ƕ���
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-7)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-10)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-13)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-16)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~-0.216 ~"+(1.268+((LineNumber-19)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.046 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~-0.046 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~-0.046 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.386 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~-0.386 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~-0.386 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://��
		case 7://��
			switch(LineNumber)
			{
			case 0:
				return "/summon ArmorStand ~-0.20 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon ArmorStand ~-0.14 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon ArmorStand ~-0.14 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon ArmorStand ~-0.26 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon ArmorStand ~1.76 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon ArmorStand ~1.80 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon ArmorStand ~1.80 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon ArmorStand ~1.80 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //�Ƕ���
			{
				if(LineNumber<=10)
					return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-7)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-10)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-13)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-16)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-19)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon ArmorStand ~-0.28 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon ArmorStand ~0.08 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon ArmorStand ~0.42 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon ArmorStand ~0.76 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon ArmorStand ~1.10 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon ArmorStand ~1.46 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon ArmorStand ~1.60 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}

		switch(Direction)
		{
			case 1://��
				if(LineNumber<=52) //�� �Ƕ���
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.386 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~0.3 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~0.3 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~0.3 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~0.0 ~0.5 ~0.9 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 3://��
				if(LineNumber<=52) //�� �Ƕ���
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~-0.046 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~-0.1 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~-0.1 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~-0.1 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~-1.0 ~0.5 ~1.1 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 5://��
				if(LineNumber<=52) //�� �Ƕ���
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.08 ~1.63 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~1.58 ~1.27 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~1.98 ~1.73 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~1.0 ~0.5 ~0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 7://��
				if(LineNumber<=52) //�� �Ƕ���
				{
					if(LineNumber<=40)
						return "/summon ArmorStand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon ArmorStand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon ArmorStand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon ArmorStand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon ArmorStand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				
				switch(LineNumber)
				{
				case 53:
					return "/summon ArmorStand ~1.08 ~1.63 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon ArmorStand ~1.58 ~1.27 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon ArmorStand ~1.98 ~1.73 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon ArmorStand ~0.96 ~0.5 ~-0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
		}
		return "null";
	}
}