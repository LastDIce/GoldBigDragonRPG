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
import util.GuiUtil;
import util.YamlLoader;

public class StructBoard extends GuiUtil
{
	public void BoardMainGUI(Player player, String BoardCode, byte page)
	{
		YamlLoader Board = new YamlLoader();
		Board.getConfig("Structure/"+BoardCode+".yml");
		String UniqueCode = "§0§0§d§0§5§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0게시판 : "+(page+1));
		
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
				Memo.add("§9제목 : §f"+PostTitle);
				Memo.add("");
				for(int count2=0;count2<(PostMemo.length()/20)+1;count2++)
				{
					if((count2+1)*20<PostMemo.length())
						Memo.add("§f"+PostMemo.substring(0+(count2*20), ((count2+1)*20)));
					else
						Memo.add("§f"+PostMemo.substring(0+(count2*20), PostMemo.length()));
				}
				Memo.add("");
				Memo.add("§9작성자 : §f"+PostUser);
				Memo.add("§9게시글 번호 : §f"+Post);
				Memo.add("");
				Memo.add("§e[Shift 우 클릭시 떼어내기]");
				Memo.add("§0"+Post);
				removeFlagStack("§6§l"+PostedTime +"전 작성된 게시글", 358,(byte)0,(byte)1,Memo, (byte)loc, inv);
				if(loc==16||loc==25||loc==34||loc==43)
					loc = (byte) (loc+3);
				else
					loc++;
				count=(short) (count+1);
			}
		}
		removeFlagStack("§c ", 160,(byte)12,(byte)1,Arrays.asList(BoardCode), (byte)0, inv);
		
		for(int count2 = 1; count2 < 9; count2++)
			removeFlagStack("§c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 44; count2 < 54; count2++)
			removeFlagStack("§c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 9; count2 < 45; count2=(byte) (count2+9))
			removeFlagStack("§c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);
		for(int count2 = 17; count2 < 54; count2=(byte) (count2+9))
			removeFlagStack("§c ", 160,(byte)12,(byte)1,Arrays.asList(""), (byte)count2, inv);

		if(!Board.getString("Notice").equals("null"))
		{
			List<String> Memo = new ArrayList<String>();
			for(int count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add("§f"+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add("§f"+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			removeFlagStack("§f§l[게시판 알림]", 321,(byte)0,(byte)1,Memo, (byte)4, inv);
		}
		
		if(AllPost>(28*page)+28)
			removeFlagStack("§f§l다음 페이지", 323,(byte)0,(byte)1,Arrays.asList("§7다음 페이지로 이동 합니다."), (byte)50, inv);
		if(page!=0)
			removeFlagStack("§f§l이전 페이지", 323,(byte)0,(byte)1,Arrays.asList("§7이전 페이지로 이동 합니다."), (byte)48, inv);

		removeFlagStack("§f§l새 게시글", 386,(byte)0,(byte)1,Arrays.asList("§7새로운 게시글을 작성합니다."), (byte)49, inv);
		player.openInventory(inv);
		return;
	}
	
	public void BoardSettingGUI(Player player, String BoardCode)
	{
		YamlLoader Board = new YamlLoader();
		Board.getConfig("Structure/"+BoardCode+".yml");

		String UniqueCode = "§0§0§d§0§6§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0게시판 설정");
		
		if(Board.contains("Post_Number")==false)
		{
			Board.set("Post_Number", 0);
			Board.createSection("User");
			Board.set("Notice","null");
			Board.set("OnlyUseOP",false);
			Board.saveConfig();
		}
		if(Board.getString("Notice").equals("null"))
			removeFlagStack("§f§l[게시판 알림]", 166,(byte)0,(byte)1,Arrays.asList("§c[게시판 알림 없음]"), (byte)2, inv);
		else
		{
			List<String> Memo = new ArrayList<String>();
			for(int count2=0;count2<(Board.getString("Notice").length()/20)+1;count2++)
			{
				if((count2+1)*20<Board.getString("Notice").length())
					Memo.add("§f"+Board.getString("Notice").substring(0+(count2*20), ((count2+1)*20)));
				else
					Memo.add("§f"+Board.getString("Notice").substring(0+(count2*20), Board.getString("Notice").length()));
			}
			removeFlagStack("§f§l[게시판 알림]", 321,(byte)0,(byte)1,Memo, (byte)2, inv);
		}

		if(Board.getBoolean("OnlyUseOP"))
			removeFlagStack("§f§l[사용 권한]", 137,(byte)0,(byte)1,Arrays.asList("§9[관리자 전용]"), (byte)4, inv);
		else
			removeFlagStack("§f§l[사용 권한]", 397,(byte)3,(byte)1,Arrays.asList("§a[전체 이용]"), (byte)4, inv);
			
		removeFlagStack("§f§l[게시글 전체 삭제]", 325,(byte)0,(byte)1,Arrays.asList("§7게시판에 붙여진 모든 게시글을","§7삭제합니다."), (byte)6, inv);

		removeFlagStack(BoardCode, 160,(byte)8,(byte)1,null, (byte)1, inv);
		removeFlagStack(BoardCode, 160,(byte)8,(byte)1,null, (byte)3, inv);
		removeFlagStack(BoardCode, 160,(byte)8,(byte)1,null, (byte)5, inv);
		removeFlagStack(BoardCode, 160,(byte)8,(byte)1,null, (byte)7, inv);
		
		removeFlagStack("§f§l이전 목록", 323,(byte)0,(byte)1,Arrays.asList("§7이전 화면으로 돌아갑니다."), (byte)0, inv);
		removeFlagStack("§f§l닫기", 324,(byte)0,(byte)1,Arrays.asList("§7창을 닫습니다."), (byte)8, inv);
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
				SoundEffect.playSound(player, Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 0.8F, 1.0F);
				if(slot == 48)//이전 페이지
					BoardMainGUI(player, Code, (byte) (page-1));
				else if(slot == 50)//다음 페이지
					BoardMainGUI(player, Code, (byte) (page+1));
			}
		}
		else if(slot == 49)//새 게시글
		{
			YamlLoader Board = new YamlLoader();
			Board.getConfig("Structure/"+Code+".yml");
			if(Board.getBoolean("OnlyUseOP")&&player.isOp()==false)
			{
				SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
				player.sendMessage("§c[게시판] : 게시글 작성 권한이 없습니다!");
				return;
			}
			UserDataObject u = new UserDataObject();
			SoundEffect.playSound(player, Sound.BLOCK_CLOTH_STEP, 0.8F, 1.8F);
			u.setTemp(player, "Structure");
			u.setType(player, "Board");
			u.setString(player, (byte)0, "Title");
			u.setString(player, (byte)1, "§f제목 없음");//게시글 제목
			u.setString(player, (byte)2, "§f내용 없음");//게시글 내용
			u.setString(player, (byte)3, Code);//게시판 코드
			player.closeInventory();
			player.sendMessage("§a[게시판] : 게시글 제목을 입력 해 주세요.");
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
						SoundEffect.playSound(player, Sound.ENTITY_SHEEP_SHEAR, 1.0F, 1.5F);
						Board.removeKey("User."+PostNumber);
						Board.saveConfig();
						BoardMainGUI(player, Code, page);
					}
					else
					{
						SoundEffect.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
						player.sendMessage("§c[게시판] : 자신이 작성한 게시글만 삭제할 수 있습니다.");
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
		
		
		if(slot == 8)//나가기
		{
			SoundEffect.playSound(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.playSound(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			String Code = event.getInventory().getItem(1).getItemMeta().getDisplayName();
			if(slot == 0)//이전 목록
				new structure.StructureGui().StructureListGUI(player, 0);
			else if(slot == 2)//게시판 알림 설정
			{
				UserDataObject u = new UserDataObject();
				u.setTemp(player, "Structure");
				u.setType(player, "Board");
				u.setString(player, (byte)0, "Notice");
				u.setString(player, (byte)1, Code);//게시판 코드
				player.closeInventory();
				player.sendMessage("§a[게시판] : 게시판 알림을 입력 해 주세요.");
			}
			else if(slot == 4)//게시판 권한
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
			else if(slot == 6)//게시판 비우기
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
		case 1://동
		case 3://서
			switch(LineNumber)
			{
			case 0:
				return "/summon minecraft:armor_stand ~-0.216 ~0.57 ~-0.20 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon minecraft:armor_stand ~-0.5 ~1.07 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon minecraft:armor_stand ~-0.5 ~1.896 ~-0.14 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon minecraft:armor_stand ~-0.216 ~2.288 ~-0.26 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon minecraft:armor_stand ~-0.216 ~0.57 ~1.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon minecraft:armor_stand ~-0.5 ~1.07 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon minecraft:armor_stand ~-0.5 ~1.896 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon minecraft:armor_stand ~-0.216 ~2.288 ~1.80 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon minecraft:armor_stand ~-0.216 ~"+(1.268+((LineNumber-7)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon minecraft:armor_stand ~-0.216 ~"+(1.268+((LineNumber-10)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon minecraft:armor_stand ~-0.216 ~"+(1.268+((LineNumber-13)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon minecraft:armor_stand ~-0.216 ~"+(1.268+((LineNumber-16)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon minecraft:armor_stand ~-0.216 ~"+(1.268+((LineNumber-19)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon minecraft:armor_stand ~-0.046 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~-0.28 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon minecraft:armor_stand ~-0.386 ~2.628 ~1.60 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		case 5://남
		case 7://북
			switch(LineNumber)
			{
			case 0:
				return "/summon minecraft:armor_stand ~-0.20 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 1:
				return "summon minecraft:armor_stand ~-0.14 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 2:
				return "summon minecraft:armor_stand ~-0.14 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 3:
				return "/summon minecraft:armor_stand ~-0.26 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 4:
				return "/summon minecraft:armor_stand ~1.76 ~0.57 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 5:
				return "summon minecraft:armor_stand ~1.80 ~1.07 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 6:
				return "summon minecraft:armor_stand ~1.80 ~1.896 ~0.12 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:stick,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[350f,90f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 7:
				return "/summon minecraft:armor_stand ~1.80 ~2.288 ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			if(LineNumber<=22) //판떼기
			{
				if(LineNumber<=10)
					return "/summon minecraft:armor_stand ~0.08 ~"+(1.268+((LineNumber-7)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=13)
					return "/summon minecraft:armor_stand ~0.42 ~"+(1.268+((LineNumber-10)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=16)
					return "/summon minecraft:armor_stand ~0.76 ~"+(1.268+((LineNumber-13)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=19)
					return "/summon minecraft:armor_stand ~1.10 ~"+(1.268+((LineNumber-16)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				else if(LineNumber<=22)
					return "/summon minecraft:armor_stand ~1.46 ~"+(1.268+((LineNumber-19)*0.34))+" ~-0.216 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			switch(LineNumber)
			{
			case 23:
				return "/summon minecraft:armor_stand ~-0.28 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 24:
				return "/summon minecraft:armor_stand ~0.08 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 25:
				return "/summon minecraft:armor_stand ~0.42 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 26:
				return "/summon minecraft:armor_stand ~0.76 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 27:
				return "/summon minecraft:armor_stand ~1.10 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 28:
				return "/summon minecraft:armor_stand ~1.46 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 29:
				return "/summon minecraft:armor_stand ~1.60 ~2.628 ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";

			case 30:
				return "/summon minecraft:armor_stand ~-0.28 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 31:
				return "/summon minecraft:armor_stand ~0.08 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 32:
				return "/summon minecraft:armor_stand ~0.42 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 33:
				return "/summon minecraft:armor_stand ~0.76 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 34:
				return "/summon minecraft:armor_stand ~1.10 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 35:
				return "/summon minecraft:armor_stand ~1.46 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			case 36:
				return "/summon minecraft:armor_stand ~1.60 ~2.628 ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:wooden_slab,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
			}
			break;
		}

		switch(Direction)
		{
			case 1://동
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon minecraft:armor_stand ~-0.386 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon minecraft:armor_stand ~-0.386 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon minecraft:armor_stand ~-0.386 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon minecraft:armor_stand ~-0.386 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon minecraft:armor_stand ~-0.386 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon minecraft:armor_stand ~0.3 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon minecraft:armor_stand ~0.3 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon minecraft:armor_stand ~0.3 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon minecraft:armor_stand ~0.0 ~0.5 ~0.9 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 3://서
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon minecraft:armor_stand ~-0.046 ~"+(1.268+((LineNumber-36)*0.34))+" ~0.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon minecraft:armor_stand ~-0.046 ~"+(1.268+((LineNumber-40)*0.34))+" ~0.42 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon minecraft:armor_stand ~-0.046 ~"+(1.268+((LineNumber-43)*0.34))+" ~0.76 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon minecraft:armor_stand ~-0.046 ~"+(1.268+((LineNumber-46)*0.34))+" ~1.10 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon minecraft:armor_stand ~-0.046 ~"+(1.268+((LineNumber-49)*0.34))+" ~1.46 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon minecraft:armor_stand ~-0.1 ~1.63 ~1.08 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon minecraft:armor_stand ~-0.1 ~1.27 ~1.58 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon minecraft:armor_stand ~-0.1 ~1.73 ~1.98 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{},{},{},{}],Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon minecraft:armor_stand ~-1.0 ~0.5 ~1.1 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Rotation:[90f],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 5://남
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon minecraft:armor_stand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon minecraft:armor_stand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon minecraft:armor_stand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon minecraft:armor_stand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon minecraft:armor_stand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.386 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				switch(LineNumber)
				{
				case 53:
					return "/summon minecraft:armor_stand ~1.08 ~1.63 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon minecraft:armor_stand ~1.58 ~1.27 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon minecraft:armor_stand ~1.98 ~1.73 ~-0.32 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon minecraft:armor_stand ~1.0 ~0.5 ~0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
			case 7://북
				if(LineNumber<=52) //뒷 판떼기
				{
					if(LineNumber<=40)
						return "/summon minecraft:armor_stand ~0.08 ~"+(1.268+((LineNumber-36)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=43)
						return "/summon minecraft:armor_stand ~0.42 ~"+(1.268+((LineNumber-40)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=46)
						return "/summon minecraft:armor_stand ~0.76 ~"+(1.268+((LineNumber-43)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=49)
						return "/summon minecraft:armor_stand ~1.10 ~"+(1.268+((LineNumber-46)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
					else if(LineNumber<=52)
						return "/summon minecraft:armor_stand ~1.46 ~"+(1.268+((LineNumber-49)*0.34))+" ~-0.046 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:planks,Damage:5,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[346f,44f,270f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				
				switch(LineNumber)
				{
				case 53:
					return "/summon minecraft:armor_stand ~1.08 ~1.63 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 54:
					return "/summon minecraft:armor_stand ~1.58 ~1.27 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 55:
					return "/summon minecraft:armor_stand ~1.98 ~1.73 ~-0.70 {CustomName:\""+StructureCode+"\",ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,HandItems:[{id:filled_map,Count:1},{}],Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[270f,0f,320f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				case 56:
					return "/summon minecraft:armor_stand ~0.96 ~0.5 ~-0.5 {CustomName:\""+StructureCode+"\",CustomNameVisible:1,ShowArms:1,Invisible:1,NoBasePlate:1,NoGravity:1,Pose:{Body:[0f,0f,0f],LeftArm:[0f,0f,0f],RightArm:[0f,0f,0f],LeftLeg:[0f,0f,0f],RightLeg:[0f,0f,0f],Head:[0f,0f,0f]}}";
				}
				break;
		}
		return "null";
	}
}