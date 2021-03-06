package monster;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import admin.OPboxGui;
import battle.BattleCalculator;
import effect.SoundEffect;
import main.MainServerOption;
import user.UserDataObject;
import util.UtilGui;
import util.YamlLoader;



public class MonsterGui extends UtilGui
{
	public void MonsterListGUI(Player player, int page)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§8§0§0§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 목록 : " + (page+1));

		Object[] a= monsterListYaml.getKeys().toArray();

		byte loc=0;
		for(int count = page*45; count < a.length;count++)
		{
			if(count > a.length || loc >= 45) break;
			String MonsterName =a[count].toString();
			String Lore=null;
			
			Lore = "%enter%§f§l 이름 : §f"+monsterListYaml.getString(MonsterName+".Name")+"%enter%";
			Lore = Lore+"§f§l 타입 : §f"+monsterListYaml.getString(MonsterName+".Type")+"%enter%";
			Lore = Lore+"§f§l 스폰 바이옴 : §f"+monsterListYaml.getString(MonsterName+".Biome")+"%enter%";
			Lore = Lore+"§c§l 생명력 : §f"+monsterListYaml.getInt(MonsterName+".HP")+"%enter%";
			Lore = Lore+"§b§l 경험치 : §f"+monsterListYaml.getInt(MonsterName+".EXP")+"%enter%";
			Lore = Lore+"§e§l 드랍 금액 : §f"+monsterListYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(MonsterName+".MAX_Money")+"%enter%";
			Lore = Lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterListYaml.getInt(MonsterName+".STR")
			+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), false) + "]%enter%";
			Lore = Lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterListYaml.getInt(MonsterName+".DEX")
			+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
			Lore = Lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterListYaml.getInt(MonsterName+".INT")
			+"§7 [폭공 : " + (monsterListYaml.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(monsterListYaml.getInt(MonsterName+".INT")/2.5)+"]%enter%";
			Lore = Lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterListYaml.getInt(MonsterName+".WILL")
			+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterListYaml.getInt(MonsterName+".LUK")
			+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
			Lore = Lore+"§7§l 방어 : §f"+monsterListYaml.getInt(MonsterName+".DEF")+"%enter%";
			Lore = Lore+"§b§l 보호 : §f"+monsterListYaml.getInt(MonsterName+".Protect")+"%enter%";
			Lore = Lore+"§9§l 마법 방어 : §f"+monsterListYaml.getInt(MonsterName+".Magic_DEF")+"%enter%";
			Lore = Lore+"§1§l 마법 보호 : §f"+monsterListYaml.getInt(MonsterName+".Magic_Protect")+"%enter%";
			Lore = Lore+"%enter%§e§l[Shift + 좌 클릭시 스폰알 지급]%enter%§c§l[Shift + 우 클릭시 몬스터 제거]";

			String[] scriptA = Lore.split("%enter%");
			for(int counter = 0; counter < scriptA.length; counter++)
				scriptA[counter] =  " "+scriptA[counter];
			int id = 383;
			byte data = 0;
			switch(monsterListYaml.getString(MonsterName+".Type"))
			{
				case "번개크리퍼" : case "크리퍼" : data=50; break;
				case "네더스켈레톤" : case "스켈레톤" : data=51; break;
				case "거미" : data=52; break;
				case "좀비" :case "자이언트" : data=54; break;
				case "초대형슬라임" :case "특대슬라임" : case "큰슬라임" :case "보통슬라임" : case "작은슬라임" : data=55; break;
				case "가스트" : data=56; break;
				case "좀비피그맨" : data=57; break;
				case "엔더맨" : data=58; break;
				case "동굴거미" : data=59; break;
				case "좀벌레" : data=60; break;
				case "블레이즈" : data=61; break;
				case "큰마그마큐브" :case "특대마그마큐브" : case "보통마그마큐브": case "마그마큐브" : case "작은마그마큐브" : data=62; break;
				case "박쥐" : data=65; break;
				case "마녀" : data=66; break;
				case "엔더진드기" : data=67; break;
				case "수호자" : data=68; break;
				case "돼지" : data=90; break;
				case "양" : data=91; break;
				case "소" : data=92; break;
				case "닭" : data=93; break;
				case "오징어" : data=94; break;
				case "늑대" : data=95; break;
				case "버섯소" : data=96; break;
				case "오셀롯" : data=98; break;
				case "말" : data=100; break;
				case "토끼" : data=101; break;
				case "주민" : data=120; break;
				case "위더" : id=399; break;
				case "엔더드래곤" : id=122; break;
				case "엔더크리스탈" : id=46; break;
				//case "휴먼" : id=379; data = 3; break;
			}
			
			Stack("§f"+MonsterName, id, data, 1,Arrays.asList(scriptA), loc, inv);
			loc++;
		}
		
		if(a.length-(page*44)>45)
			Stack("§f§l다음 페이지", 323,0,1,Arrays.asList("§7다음 페이지로 이동 합니다."), 50, inv);
		if(page!=0)
			Stack("§f§l이전 페이지", 323,0,1,Arrays.asList("§7이전 페이지로 이동 합니다."), 48, inv);

		Stack("§f§l새 몬스터", 339,0,1,Arrays.asList("§7새로운 몬스터를 생성합니다."), 49, inv);
		Stack("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다."), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterOptionSettingGUI(Player player,String MonsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		String UniqueCode = "§0§0§8§0§1§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 설정");

		String Lore=null;			
		Lore = "%enter%§f§l 이름 : §f"+monsterListYaml.getString(MonsterName+".Name")+"%enter%";
		Lore = Lore+"§f§l 타입 : §f"+monsterListYaml.getString(MonsterName+".Type")+"%enter%";
		Lore = Lore+"§f§l 스폰 바이옴 : §f"+monsterListYaml.getString(MonsterName+".Biome")+"%enter%";
		Lore = Lore+"§c§l 생명력 : §f"+monsterListYaml.getInt(MonsterName+".HP")+"%enter%";
		Lore = Lore+"§b§l 경험치 : §f"+monsterListYaml.getInt(MonsterName+".EXP")+"%enter%";
		Lore = Lore+"§e§l 드랍 금액 : §f"+monsterListYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(MonsterName+".MAX_Money")+"%enter%";
		Lore = Lore+"§c§l "+MainServerOption.statSTR+" : §f"+monsterListYaml.getInt(MonsterName+".STR")
		+"§7 [물공 : " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), true) + " ~ " + BattleCalculator.getCombatDamage(null, 0, monsterListYaml.getInt(MonsterName+".STR"), false) + "]%enter%";
		Lore = Lore+"§a§l "+MainServerOption.statDEX+" : §f"+monsterListYaml.getInt(MonsterName+".DEX")
		+"§7 [활공 : " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, true) + " ~ " + BattleCalculator.returnRangeDamageValue(null, monsterListYaml.getInt(MonsterName+".DEX"), 0, false) + "]%enter%";
		Lore = Lore+"§9§l "+MainServerOption.statINT+" : §f"+monsterListYaml.getInt(MonsterName+".INT")
		+"§7 [폭공 : " + (monsterListYaml.getInt(MonsterName+".INT")/4)+ " ~ "+(int)(monsterListYaml.getInt(MonsterName+".INT")/2.5)+"]%enter%";
		Lore = Lore+"§7§l "+MainServerOption.statWILL+" : §f"+monsterListYaml.getInt(MonsterName+".WILL")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+"§e§l "+MainServerOption.statLUK+" : §f"+monsterListYaml.getInt(MonsterName+".LUK")
		+"§7 [크리 : " + BattleCalculator.getCritical(null,monsterListYaml.getInt(MonsterName+".LUK"), (int)monsterListYaml.getInt(MonsterName+".WILL"),0) + " %]%enter%";
		Lore = Lore+"§7§l 방어 : §f"+monsterListYaml.getInt(MonsterName+".DEF")+"%enter%";
		Lore = Lore+"§b§l 보호 : §f"+monsterListYaml.getInt(MonsterName+".Protect")+"%enter%";
		Lore = Lore+"§9§l 마법 방어 : §f"+monsterListYaml.getInt(MonsterName+".Magic_DEF")+"%enter%";
		Lore = Lore+"§1§l 마법 보호 : §f"+monsterListYaml.getInt(MonsterName+".Magic_Protect")+"%enter%";

		
		String[] scriptA = Lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];

		Stack2("§c[    몬스터    ]", 52,0,1,null, 9, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 10, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 11, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 18, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 20, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 27, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 28, inv);
		Stack2("§c[    몬스터    ]", 52,0,1,null, 29, inv);
		int id = 383;
		byte data = 0;
		String Type = monsterListYaml.getString(MonsterName+".Type");
		switch(Type)
		{
			case "번개크리퍼" : case "크리퍼" : data=50; break;
			case "네더스켈레톤" : case "스켈레톤" : data=51; break;
			case "거미" : data=52; break;
			case "좀비" :case "자이언트" : data=54; break;
			case "초대형슬라임" :case "특대슬라임" : case "큰슬라임" :case "보통슬라임" : case "작은슬라임" : data=55; break;
			case "가스트" : data=56; break;
			case "좀비피그맨" : data=57; break;
			case "엔더맨" : data=58; break;
			case "동굴거미" : data=59; break;
			case "좀벌레" : data=60; break;
			case "블레이즈" : data=61; break;
			case "큰마그마큐브" :case "특대마그마큐브" : case "보통마그마큐브": case "마그마큐브" : case "작은마그마큐브" : data=62; break;
			case "박쥐" : data=65; break;
			case "마녀" : data=66; break;
			case "엔더진드기" : data=67; break;
			case "수호자" : data=68; break;
			case "돼지" : data=90; break;
			case "양" : data=91; break;
			case "소" : data=92; break;
			case "닭" : data=93; break;
			case "오징어" : data=94; break;
			case "늑대" : data=95; break;
			case "버섯소" : data=96; break;
			case "오셀롯" : data=98; break;
			case "말" : data=100; break;
			case "토끼" : data=101; break;
			case "주민" : data=120; break;
			case "눈사람" :id = 332; data=0; break;
			case "골렘" :id = 265; data=0; break;
			case "위더" : id=399; break;
			case "엔더드래곤" : id=122; break;
			case "엔더크리스탈" : id=46; break;
			//case "휴먼" : id=379; data=3;break;
		}

		Stack2("§f"+ MonsterName, id,data,1,Arrays.asList(scriptA), 19, inv);
		
		
		Stack2("§3[    이름 변경    ]", 421,0,1,Arrays.asList("§f몬스터의 이름을","§f변경합니다.","","§f[    현재 이름    ]"," §f"+monsterListYaml.getString(MonsterName+".Name"),""), 13, inv);
		Stack2("§3[    타입 변경    ]", 383,0,1,Arrays.asList("§f몬스터의 타입을","§f변경합니다.","","§f[    현재 타입    ]"," §f"+monsterListYaml.getString(MonsterName+".Type"),""), 14, inv);

		data = 0;
		switch(monsterListYaml.getString(MonsterName+".Biome"))
		{
		case "BEACH" : id=337;break;
		case "DESERT" : id=12;break;
		case "EXTREME_HILLS" : id=129;break;
		case "FOREST" : id=17;break;
		case "HELL" : id=87;break;
		case "JUNGLE" : id=6;data=3;break;
		case "MESA" : id=159;break;
		case "OCEAN" : id=410;break;
		case "PLAINS" : id=2;break;
		case "RIVER" : id=346;break;
		case "SAVANNA" : id=32;break;
		case "SKY" : id=121;break;
		case "SMALL_MOUNTAINS" : id=6;data=0;break;
		case "SWAMPLAND" : id=111;break;
		case "TAIGA" : id=78;break;
		default : id=166;break;
		}
		
		Stack2("§3[ 스폰 바이옴 변경 ]", id,data,1,Arrays.asList("§f몬스터가 등장하는","§f바이옴을 변경합니다.","","§f[    등장 바이옴    ]"," §f"+monsterListYaml.getString(MonsterName+".Biome"),""), 15, inv);
		Stack2("§3[    생명력 변경    ]", 351,1,1,Arrays.asList("§f몬스터의 생명력을","§f변경합니다.","","§f[    현재 생명력    ]"," §f"+monsterListYaml.getInt(MonsterName+".HP"),""), 16, inv);
		Stack2("§3[    경험치 변경    ]", 384,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f경험치 량을 변경합니다.","","§f[    현재 경험치    ]"," §f"+monsterListYaml.getInt(MonsterName+".EXP"),""), 22, inv);
		Stack2("§3[  드랍 금액 변경  ]", 266,0,1,Arrays.asList("§f몬스터 사냥시 얻는","§f금액을 변경합니다.","","§f[    현재 금액    ]"," §f"+monsterListYaml.getInt(MonsterName+".MIN_Money")+" ~ "+monsterListYaml.getInt(MonsterName+".MAX_Money"),""), 23, inv);
		Stack2("§3[    장비 변경    ]", 307,0,1,Arrays.asList("§f몬스터의 장비를","§f설정 합니다.","","§e[    좌클릭시 변경    ]",""), 24, inv);
		Stack2("§3[  장비 드랍률 변경  ]", 54,0,1,Arrays.asList("§f몬스터 사냥시 드랍되는","§f장비 확룰을 변경합니다.","","§f[    현재 드랍률    ]"," §f머리 : "+monsterListYaml.getInt(MonsterName+".Head.DropChance")/10.0+"%"
				," §f갑옷 : "+monsterListYaml.getInt(MonsterName+".Chest.DropChance")/10.0+"%"
				," §f바지 : "+monsterListYaml.getInt(MonsterName+".Leggings.DropChance")/10.0+"%"
				," §f신발 : "+monsterListYaml.getInt(MonsterName+".Boots.DropChance")/10.0+"%"
				," §f무기 : "+monsterListYaml.getInt(MonsterName+".Hand.DropChance")/10.0+"%","","§e[    좌클릭시 변경   ]",""), 25, inv);
		Stack2("§3[  몬스터 스텟 변경  ]", 399,0,1,Arrays.asList("§f몬스터의 기본 스텟을","§f변경합니다.",""), 31, inv);
		Stack2("§3[  몬스터 방어 변경  ]", 310,0,1,Arrays.asList("§f몬스터의 방어 및 보호를","§f변경합니다.",""), 32, inv);
		
		Lore = "§f몬스터의 AI를 변경합니다.%enter%%enter%§f[    현재 AI    ]%enter%§f"+monsterListYaml.getString(MonsterName+".AI")+"%enter%%enter%";
		if(Type.equals("초대형슬라임")||Type.equals("특대슬라임")||Type.equals("큰슬라임")||
		Type.equals("보통슬라임")||Type.equals("작은슬라임")||Type.equals("큰마그마큐브")||Type.equals("특대마그마큐브")||Type.equals("보통마그마큐브")||
		Type.equals("마그마큐브")||Type.equals("작은마그마큐브")||Type.equals("가스트")||Type.equals("위더")
		||Type.equals("엔더드래곤")||Type.equals("셜커")||Type.equals("양")||Type.equals("소")
		||Type.equals("돼지")||Type.equals("말")||Type.equals("박쥐")||Type.equals("토끼")
		||Type.equals("오셀롯")||Type.equals("늑대")||Type.equals("닭")||Type.equals("버섯소")
		||Type.equals("오징어")||Type.equals("주민")||Type.equals("눈사람")||Type.equals("골렘")
		)
		Lore = Lore + "§c[현재 선택 된 몬스터 타입은%enter%§c무조건 근접 AI만을 사용합니다.]";
		else
		{
			switch(monsterListYaml.getString(MonsterName+".AI"))
			{
			case "일반 행동" :
				Lore = Lore+"§f일반적인 행동을 합니다.%enter%";
				break;
			case "선공" :
				Lore = Lore+"§f무조건 선제 공격을합니다.%enter%";break;
			case "비선공" :
				Lore = Lore+"§f공격받기 전에는 공격하지 않습니다.%enter%";break;
			case "무뇌아" :
				Lore = Lore+"§f공격및 이동을 하지 않습니다.%enter%";break;
			case "동물" :
				Lore = Lore+"§f공격받을 경우 도망치기 바쁘며,%enter%§f절대로 공격하지 않습니다.%enter%";break;
			}
		}
		
		scriptA = Lore.split("%enter%");
		for(int counter = 0; counter < scriptA.length; counter++)
			scriptA[counter] =  " "+scriptA[counter];
		
		
		Stack2("§3[  몬스터 AI 변경  ]", 137,0,1,Arrays.asList(scriptA), 33, inv);
		Stack2("§3[    포션 효과    ]", 373,0,1,Arrays.asList("§f몬스터에게 포션 효과를","§f부여합니다.",""), 34, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	
	public void MonsterPotionGUI(Player player, String MonsterName)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String UniqueCode = "§0§0§8§0§2§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 포션");
		
		Stack2("§3[  재생  ]", 373,8193,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Regenerate")), 10, inv);
		Stack2("§3[  독  ]", 373,8196,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Poison")), 11, inv);
		Stack2("§3[  신속  ]", 373,8194,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Speed")), 12, inv);
		Stack2("§3[  구속  ]", 373,8234,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Slow")), 13, inv);
		Stack2("§3[  힘  ]", 373,8201,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Strength")), 14, inv);
		Stack2("§3[  나약함  ]", 373,8232,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.Weak")), 15, inv);
		Stack2("§3[  도약  ]", 373,8267,1,Arrays.asList("§f[  포션 농도  ]","§e "+monsterListYaml.getInt(MonsterName+".Potion.JumpBoost")), 16, inv);

		if(monsterListYaml.getInt(MonsterName+".Potion.FireRegistance")!=0)
			Stack2("§3[  화염 저항  ]", 373,8227,1,Arrays.asList("§a[  포션 적용  ]"), 19, inv);
		else
			Stack2("§3[  화염 저항  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 19, inv);
		if(monsterListYaml.getInt(MonsterName+".Potion.WaterBreath")!=0)
			Stack2("§3[  수중 호홉  ]", 373,8237,1,Arrays.asList("§a[  포션 적용  ]"), 20, inv);
		else
			Stack2("§3[  수중 호홉  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 20, inv);
		if(monsterListYaml.getInt(MonsterName+".Potion.Invisible")!=0)
			Stack2("§3[  투명  ]", 373,8238,1,Arrays.asList("§a[  포션 적용  ]"), 21, inv);
		else
			Stack2("§3[  투명  ]", 166,0,1,Arrays.asList("§c[  포션 미적용  ]"), 21, inv);
			

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}

	public void ArmorGUI(Player player, String mob)
	{
		YamlLoader monsterListYaml = new YamlLoader();
		String UniqueCode = "§1§0§8§0§3§r";
		Inventory inv = Bukkit.createInventory(null, 9, UniqueCode + "§0몬스터 장비 설정");
		monsterListYaml.getConfig("Monster/MonsterList.yml");

		if(monsterListYaml.contains(mob + ".Head.Item")==true&&
			monsterListYaml.getItemStack(mob + ".Head.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(0, monsterListYaml.getItemStack(mob + ".Head.Item"));
		else
			Stack("§f머리", 302,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)0, inv);

		if(monsterListYaml.contains(mob + ".Chest.Item")==true&&
				monsterListYaml.getItemStack(mob + ".Chest.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(1, monsterListYaml.getItemStack(mob + ".Chest.Item"));
		else
			Stack("§f갑옷", 303,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)1, inv);

		if(monsterListYaml.contains(mob + ".Leggings.Item")==true&&
				monsterListYaml.getItemStack(mob + ".Leggings.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(2, monsterListYaml.getItemStack(mob + ".Leggings.Item"));
		else
			Stack("§f바지", 304,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)2, inv);

		if(monsterListYaml.contains(mob + ".Boots.Item")==true&&
		monsterListYaml.getItemStack(mob + ".Boots.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(3, monsterListYaml.getItemStack(mob + ".Boots.Item"));
		else
			Stack("§f부츠", 305,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)3, inv);

		if(monsterListYaml.contains(mob + ".Hand.Item")==true&&
		monsterListYaml.getItemStack(mob + ".Hand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(4, monsterListYaml.getItemStack(mob + ".Hand.Item"));
		else
			Stack("§f오른손", 267,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)4, inv);

		if(monsterListYaml.contains(mob + ".OffHand.Item")==true&&
		monsterListYaml.getItemStack(mob + ".OffHand.Item").equals(new ItemStack(Material.AIR))==false)
			inv.setItem(5, monsterListYaml.getItemStack(mob + ".OffHand.Item"));
		else
			Stack("§f왼손", 267,(byte)0,(byte)1,Arrays.asList("§7이곳에 아이템을 넣어 주세요."), (byte)5, inv);
		
		Stack("§f"+ mob, 416,(byte)0,(byte)1,Arrays.asList("§8"+ mob+"의 현재 장비입니다." ), (byte)8, inv);
		Stack("§f", 30,(byte)0,(byte)1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."), (byte)7, inv);
		Stack("§f", 30,(byte)0,(byte)1,Arrays.asList("§7이곳에는 아이템을","§7올려두지 마세요."), (byte)6, inv);
		
		player.openInventory(inv);
		return;
	}

	public void MonsterTypeGUI(Player player, String MonsterName)
	{
		String UniqueCode = "§1§0§8§0§b§r";
		Inventory inv = Bukkit.createInventory(null, 54, UniqueCode + "§0몬스터 타입 설정");

		Stack2("§2§l[좀비]", 367,0,1,null, 0, inv);
		Stack2("§2§l[자이언트]", 367,0,2,null, 1, inv);
		Stack2("§d§l[좀비 피그맨]", 283,0,1,null, 2, inv);
		Stack2("§a§l[크리퍼]", 289,0,1,null, 3, inv);
		Stack2("§a§l[번개 크리퍼]", 289,0,1,null, 4, inv);
		Stack2("§f§l[스켈레톤]", 352,0,1,null, 5, inv);
		Stack2("§7§l[위더 스켈레톤]", 263,0,1,null, 6, inv);
		Stack2("§7§l[위더]", 399,0,1,null, 7, inv);
		Stack2("§7§l[거미]", 287,0,1,null, 8, inv);
		Stack2("§7§l[동굴거미]", 287,0,2,null, 9, inv);
		Stack2("§a§l[작은 슬라임]", 341,0,1,null, 10, inv);
		Stack2("§a§l[보통 슬라임]", 341,0,2,null, 11, inv);
		Stack2("§a§l[큰 슬라임]", 341,0,4,null, 12, inv);
		Stack2("§a§l[특대 슬라임]", 341,0,8,null, 13, inv);
		Stack2("§a§l[초대형 슬라임]", 341,0,16,null, 14, inv);
		Stack2("§7§l[작은 마그마큐브]", 378,0,1,null, 15, inv);
		Stack2("§7§l[보통 마그마큐브]", 378,0,1,null, 16, inv);
		Stack2("§7§l[큰 마그마큐브]", 378,0,1,null, 17, inv);
		Stack2("§7§l[특대 마그마큐브]", 378,0,1,null, 18, inv);
		Stack2("§7§l[초대형 마그마큐브]", 378,0,1,null, 19, inv);
		Stack2("§7§l[박쥐]", 362,0,1,null, 20, inv);
		Stack2("§f§l[가스트]", 370,0,1,null, 21, inv);
		Stack2("§e§l[블레이즈]", 369,0,1,null, 22, inv);
		Stack2("§7§l[좀벌레]", 1,0,1,null, 23, inv);
		Stack2("§5§l[엔더 진드기]", 432,0,1,null, 24, inv);
		Stack2("§a§l[주민]", 388,0,1,null, 25, inv);
		Stack2("§5§l[마녀]", 438,0,1,null, 26, inv);
		Stack2("§3§l[가디언]", 409,0,1,null, 27, inv);
		Stack2("§7§l[엔더맨]", 368,0,1,null, 28, inv);
		Stack2("§5§l[셜커]", 443,0,1,null, 29, inv);
		Stack2("§7§l[엔더드래곤]", 122,0,1,null, 30, inv);
		Stack2("§d§l[돼지]", 319,0,1,null, 31, inv);
		Stack2("§f§l[양]", 423,0,1,null, 32, inv);
		Stack2("§7§l[소]", 363,0,1,null, 33, inv);
		Stack2("§c§l[버섯 소]", 40,0,1,null, 34, inv);
		Stack2("§f§l[닭]", 365,0,1,null, 35, inv);
		Stack2("§7§l[오징어]", 351,0,1,null, 36, inv);
		Stack2("§7§l[늑대]", 280,0,1,null, 37, inv);
		Stack2("§e§l[오셀롯]", 349,0,1,null, 38, inv);
		Stack2("§f§l[눈사람]", 332,0,1,null, 39, inv);
		Stack2("§f§l[철골렘]", 265,0,1,null, 40, inv);
		Stack2("§f§l[토끼]", 411,0,1,null, 41, inv);
		Stack2("§f§l[북극곰]", 349,1,1,null, 42, inv);
		Stack2("§6§l[말]", 417,0,1,null, 43, inv);
		Stack2("§5§l[엔더 크리스탈]", 426,0,1,null, 44, inv);

		Stack2("§f§l이전 목록", 323,0,1,Arrays.asList("§7이전 화면으로 돌아갑니다."), 45, inv);
		Stack2("§f§l닫기", 324,0,1,Arrays.asList("§7창을 닫습니다.","§0"+MonsterName), 53, inv);
		player.openInventory(inv);
	}
	

	public void MonsterListGUIClick(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		int slot = event.getSlot();

		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			short page =  (short) (Short.parseShort(event.getInventory().getTitle().split(" : ")[1])-1);
			if(slot == 45)//이전 목록
				new OPboxGui().opBoxGuiMain(player, (byte) 1);
			else if(slot == 48)//이전 페이지
				MonsterListGUI(player, page-1);
			else if(slot == 49)//새 몬스터
			{
				player.closeInventory();
				player.sendMessage("§a[몬스터] : 새로운 몬스터 이름을 지어 주세요!");
				UserDataObject u = new UserDataObject();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "NM");
			}
			else if(slot == 50)//다음 페이지
				MonsterListGUI(player, page+1);
			else
			{
				if(event.isLeftClick() == true&&event.isShiftClick()==false)
					MonsterOptionSettingGUI(player, ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isLeftClick() == true&&event.isShiftClick())
					new monster.MonsterSpawn().SpawnEggGive(player,ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
				else if(event.isRightClick()&&event.isShiftClick())
				{
					SoundEffect.SP(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
					YamlLoader monsterListYaml = new YamlLoader();
					monsterListYaml.getConfig("Monster/MonsterList.yml");
					monsterListYaml.removeKey(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
					monsterListYaml.saveConfig();
					MonsterListGUI(player, page);
				}
			}
		}
	}

	public void MonsterOptionSettingGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterListGUI(player, 0);
			else if(slot == 14)//몹 타입 변경
				MonsterTypeGUI(player, MonsterName);
			else if(slot == 15)//스폰 바이옴 변경
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String Type = monsterListYaml.getString(MonsterName+".Biome");
				if(Type.equals("NULL"))
					monsterListYaml.set(MonsterName+".Biome", "BEACH");
				else if(Type.equals("BEACH"))
					monsterListYaml.set(MonsterName+".Biome", "DESERT");
				else if(Type.equals("DESERT"))
					monsterListYaml.set(MonsterName+".Biome", "EXTREME_HILLS");
				else if(Type.equals("EXTREME_HILLS"))
					monsterListYaml.set(MonsterName+".Biome", "FOREST");
				else if(Type.equals("FOREST"))
					monsterListYaml.set(MonsterName+".Biome", "HELL");
				else if(Type.equals("HELL"))
					monsterListYaml.set(MonsterName+".Biome", "JUNGLE");
				else if(Type.equals("JUNGLE"))
					monsterListYaml.set(MonsterName+".Biome", "MESA");
				else if(Type.equals("MESA"))
					monsterListYaml.set(MonsterName+".Biome", "OCEAN");
				else if(Type.equals("OCEAN"))
					monsterListYaml.set(MonsterName+".Biome", "PLAINS");
				else if(Type.equals("PLAINS"))
					monsterListYaml.set(MonsterName+".Biome", "RIVER");
				else if(Type.equals("RIVER"))
					monsterListYaml.set(MonsterName+".Biome", "SAVANNA");
				else if(Type.equals("SAVANNA"))
					monsterListYaml.set(MonsterName+".Biome", "SKY");
				else if(Type.equals("SKY"))
					monsterListYaml.set(MonsterName+".Biome", "SMALL_MOUNTAINS");
				else if(Type.equals("SMALL_MOUNTAINS"))
					monsterListYaml.set(MonsterName+".Biome", "SWAMPLAND");
				else if(Type.equals("SWAMPLAND"))
					monsterListYaml.set(MonsterName+".Biome", "TAIGA");
				else if(Type.equals("TAIGA"))
					monsterListYaml.set(MonsterName+".Biome", "NULL");
				else
					monsterListYaml.set(MonsterName+".Biome", "NULL");
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 33)
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				String Type = monsterListYaml.getString(MonsterName+".AI");
				if(Type.equals("일반 행동"))
					monsterListYaml.set(MonsterName+".AI", "선공");
				else if(Type.equals("선공"))
					monsterListYaml.set(MonsterName+".AI", "비선공");
				else if(Type.equals("비선공"))
					monsterListYaml.set(MonsterName+".AI", "동물");
				else if(Type.equals("동물"))
					monsterListYaml.set(MonsterName+".AI", "무뇌아");
				else if(Type.equals("무뇌아"))
					monsterListYaml.set(MonsterName+".AI", "일반 행동");
				else
					monsterListYaml.set(MonsterName+".AI", "일반 행동");
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
			else if(slot == 24)//장비 변경
			{
				SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
				ArmorGUI(player, MonsterName);
			}
			else if(slot == 34)//몬스터 포션 효과
				MonsterPotionGUI(player, MonsterName);
			else if(!((event.getSlot()>=9&&event.getSlot()<=11)||(event.getSlot()>=18&&event.getSlot()<=20)||(event.getSlot()>=27&&event.getSlot()<=29)))
			{
				UserDataObject u = new UserDataObject();
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)2, ChatColor.stripColor(event.getInventory().getItem(19).getItemMeta().getDisplayName()));
				if(slot==13)//몹 이름 변경
				{
					player.sendMessage("§a[몬스터] : 몬스터의 보여주는 이름을 설정하세요!");
					player.sendMessage("§f§l&l §0&0 §1&1 §2&2 "+
					"§3&3 §4&4 §5&5 " +
							"§6&6 §7&7 §8&8 " +
					"§9&9 §a&a §b&b §c&c " +
							"§d&d §e&e §f&f");
					u.setString(player, (byte)1, "CN");
				}
				else if(slot == 16)//생명력 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터의 생명력을 설정 해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "HP");
				}
				else if(slot == 22)//경험치 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터의 경험치를 설정 해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "EXP");
				}
				else if(slot == 23)//드랍 금액 변경
				{
					player.sendMessage("§a[몬스터] : 해당 몬스터가 드랍하는 최소 골드량을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "MIN_Money");
				}
				else if(slot == 25)//장비 드랍률 변경
				{
					player.sendMessage("§7(확률 계산 : 1000 = 100%, 1 = 0.1%)");
					player.sendMessage("§a[몬스터] : 몬스터의 투구 드랍률을 설정해 주세요!");
					player.sendMessage("§3(0 ~ 1000)");
					u.setString(player, (byte)1, "Head.DropChance");
				}
				else if(slot == 31)//몬스터 스텟 변경
				{
					player.sendMessage("§7("+MainServerOption.statSTR+"은 몬스터의 물리 공격력을 상승시켜 줍니다.)");
					player.sendMessage("§a[몬스터] : 몬스터의 "+MainServerOption.statSTR+"을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "STR");
				}
				else if(slot == 32)//몬스터 방어 변경
				{
					player.sendMessage("§7(물리방어는 몬스터의 물리적인 방어력을 상승시켜 줍니다.)");
					player.sendMessage("§a[몬스터] : 몬스터의 물리 방어력을 설정해 주세요!");
					player.sendMessage("§3(1 ~ "+Integer.MAX_VALUE+")");
					u.setString(player, (byte)1, "DEF");
				}
			}
		}
	}

	public void MonsterPotionGUIClick(InventoryClickEvent event)
	{
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		
		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else if(slot >= 10 && slot <= 16)
			{
				UserDataObject u = new UserDataObject();
				SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
				player.closeInventory();
				u.setType(player, "Monster");
				u.setString(player, (byte)1, "Potion");
				u.setString(player, (byte)3, MonsterName);
				if(slot == 10)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 재생 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Regenerate");
				}
				else if(slot == 11)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 독 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Poision");
				}
				else if(slot == 12)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 신속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Speed");
				}
				else if(slot == 13)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 구속 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Slow");
				}
				else if(slot == 14)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 힘 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Strength");
				}
				else if(slot == 15)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 나약함 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Weak");
				}
				else if(slot == 16)
				{
					player.sendMessage("§a[몬스터] : 몬스터의 도약 효과는 몇 으로 설정하실건가요?");
					player.sendMessage("§e(0 ~ 100)");
					u.setString(player, (byte)2, "Jump");
				}
			}
			else if(slot >= 19)
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				if(slot == 19)//화염 저항
				{
					if(monsterListYaml.getInt(MonsterName+".Potion.FireRegistance")==0)
						monsterListYaml.set(MonsterName+".Potion.FireRegistance", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.FireRegistance", 0);
				}
				else if(slot == 20)//수중 호홉
				{
					if(monsterListYaml.getInt(MonsterName+".Potion.WaterBreath")==0)
						monsterListYaml.set(MonsterName+".Potion.WaterBreath", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.WaterBreath", 0);
				}
				else if(slot == 21)//투명
				{
					if(monsterListYaml.getInt(MonsterName+".Potion.Invisible")==0)
						monsterListYaml.set(MonsterName+".Potion.Invisible", 1);
					else
						monsterListYaml.set(MonsterName+".Potion.Invisible", 0);
				}
				monsterListYaml.saveConfig();
				SoundEffect.SP(player, Sound.ENTITY_GENERIC_DRINK, 1.0F, 1.0F);
				MonsterPotionGUI(player, MonsterName);
			}
		}
	}

	public void ArmorGUIClick(InventoryClickEvent event)
	{
		if(event.getSlot() >=6 && event.getSlot() <= 8)
			event.setCancelled(true);
		else if(event.getCurrentItem().hasItemMeta())
			if(event.getCurrentItem().getItemMeta().hasLore())
				if(event.getCurrentItem().getItemMeta().getLore().get(0).equals("§7이곳에 아이템을 넣어 주세요."))
					event.getInventory().remove(event.getCurrentItem());
		return;
	}
	
	public void ArmorGUIClose(InventoryCloseEvent event)
	{
		YamlLoader monsterListYaml = new YamlLoader();

		monsterListYaml.getConfig("Monster/MonsterList.yml");
		String MonsterName = ChatColor.stripColor(event.getInventory().getItem(8).getItemMeta().getDisplayName().toString());
		if(event.getInventory().getItem(0)==new util.UtilGui().getItemStack("§f머리", 302,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Head.Item", null);
		else
			monsterListYaml.set(MonsterName+".Head.Item", event.getInventory().getItem(0));
		
		if(event.getInventory().getItem(1)==new util.UtilGui().getItemStack("§f갑옷", 303,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
					monsterListYaml.set(MonsterName+".Chest.Item", null);
		else
			monsterListYaml.set(MonsterName+".Chest.Item", event.getInventory().getItem(1));
		if(event.getInventory().getItem(2)==new util.UtilGui().getItemStack("§f바지", 304,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Leggings.Item", null);
		else
			monsterListYaml.set(MonsterName+".Leggings.Item", event.getInventory().getItem(2));
		if(event.getInventory().getItem(1)==new util.UtilGui().getItemStack("§f부츠", 305,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Boots.Item", null);
		else
			monsterListYaml.set(MonsterName+".Boots.Item", event.getInventory().getItem(3));
		if(event.getInventory().getItem(4)==new util.UtilGui().getItemStack("§f무기", 267,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".Hand.Item", null);
		else
			monsterListYaml.set(MonsterName+".Hand.Item", event.getInventory().getItem(4));
		if(event.getInventory().getItem(5)==new util.UtilGui().getItemStack("§f무기", 267,0,1,Arrays.asList("§7이곳에 아이템을 넣어 주세요.")))
			monsterListYaml.set(MonsterName+".OffHand.Item", null);
		else
			monsterListYaml.set(MonsterName+".OffHand.Item", event.getInventory().getItem(5));
		monsterListYaml.saveConfig();
		event.getPlayer().sendMessage("§a[SYSTEM] : 아이템 설정이 저장되었습니다.");
		return;
	}

	public void MonsterTypeGUIClick(InventoryClickEvent event)
	{
		event.setCancelled(true);
		int slot = event.getSlot();
		Player player = (Player) event.getWhoClicked();

		
		if(slot == 53)//나가기
		{
			SoundEffect.SP(player, Sound.BLOCK_PISTON_CONTRACT, 0.8F, 1.8F);
			player.closeInventory();
		}
		else
		{
			String MonsterName = ChatColor.stripColor(event.getInventory().getItem(53).getItemMeta().getLore().get(1));
			
			SoundEffect.SP(player, Sound.ENTITY_ITEM_PICKUP, 0.8F, 1.0F);
			if(slot == 45)//이전 목록
				MonsterOptionSettingGUI(player, MonsterName);
			else
			{
				YamlLoader monsterListYaml = new YamlLoader();
				monsterListYaml.getConfig("Monster/MonsterList.yml");
				if(slot == 0)
					monsterListYaml.set(MonsterName+".Type", "좀비");
				else if(slot == 1)
					monsterListYaml.set(MonsterName+".Type", "자이언트");
				else if(slot == 2)
					monsterListYaml.set(MonsterName+".Type", "좀비피그맨");
				else if(slot == 3)
					monsterListYaml.set(MonsterName+".Type", "크리퍼");
				else if(slot == 4)
					monsterListYaml.set(MonsterName+".Type", "번개크리퍼");
				else if(slot == 5)
					monsterListYaml.set(MonsterName+".Type", "스켈레톤");
				else if(slot == 6)
					monsterListYaml.set(MonsterName+".Type", "네더스켈레톤");
				else if(slot == 7)
					monsterListYaml.set(MonsterName+".Type", "위더");
				else if(slot == 8)
					monsterListYaml.set(MonsterName+".Type", "거미");
				else if(slot == 9)
					monsterListYaml.set(MonsterName+".Type", "동굴거미");
				else if(slot == 10)
					monsterListYaml.set(MonsterName+".Type", "작은슬라임");
				else if(slot == 11)
					monsterListYaml.set(MonsterName+".Type", "보통슬라임");
				else if(slot == 12)
					monsterListYaml.set(MonsterName+".Type", "큰슬라임");
				else if(slot == 13)
					monsterListYaml.set(MonsterName+".Type", "특대슬라임");
				else if(slot == 14)
					monsterListYaml.set(MonsterName+".Type", "초대형슬라임");
				else if(slot == 15)
					monsterListYaml.set(MonsterName+".Type", "작은마그마큐브");
				else if(slot == 16)
					monsterListYaml.set(MonsterName+".Type", "보통마그마큐브");
				else if(slot == 17)
					monsterListYaml.set(MonsterName+".Type", "큰마그마큐브");
				else if(slot == 18)
					monsterListYaml.set(MonsterName+".Type", "특대마그마큐브");
				else if(slot == 19)
					monsterListYaml.set(MonsterName+".Type", "초대형마그마큐브");
				else if(slot == 20)
					monsterListYaml.set(MonsterName+".Type", "박쥐");
				else if(slot == 21)
					monsterListYaml.set(MonsterName+".Type", "가스트");
				else if(slot == 22)
					monsterListYaml.set(MonsterName+".Type", "블레이즈");
				else if(slot == 23)
					monsterListYaml.set(MonsterName+".Type", "좀벌레");
				else if(slot == 24)
					monsterListYaml.set(MonsterName+".Type", "엔더진드기");
				else if(slot == 25)
					monsterListYaml.set(MonsterName+".Type", "주민");
				else if(slot == 26)
					monsterListYaml.set(MonsterName+".Type", "마녀");
				else if(slot == 27)
					monsterListYaml.set(MonsterName+".Type", "수호자");
				else if(slot == 28)
					monsterListYaml.set(MonsterName+".Type", "엔더맨");
				else if(slot == 29)
					monsterListYaml.set(MonsterName+".Type", "셜커");
				else if(slot == 30)
					monsterListYaml.set(MonsterName+".Type", "엔더드래곤");
				else if(slot == 31)
					monsterListYaml.set(MonsterName+".Type", "돼지");
				else if(slot == 32)
					monsterListYaml.set(MonsterName+".Type", "양");
				else if(slot == 33)
					monsterListYaml.set(MonsterName+".Type", "소");
				else if(slot == 34)
					monsterListYaml.set(MonsterName+".Type", "버섯소");
				else if(slot == 35)
					monsterListYaml.set(MonsterName+".Type", "닭");
				else if(slot == 36)
					monsterListYaml.set(MonsterName+".Type", "오징어");
				else if(slot == 37)
					monsterListYaml.set(MonsterName+".Type", "늑대");
				else if(slot == 38)
					monsterListYaml.set(MonsterName+".Type", "오셀롯");
				else if(slot == 39)
					monsterListYaml.set(MonsterName+".Type", "눈사람");
				else if(slot == 40)
					monsterListYaml.set(MonsterName+".Type", "골렘");
				else if(slot == 41)
					monsterListYaml.set(MonsterName+".Type", "토끼");
				else if(slot == 42)
					monsterListYaml.set(MonsterName+".Type", "북극곰");
				else if(slot == 43)
					monsterListYaml.set(MonsterName+".Type", "말");
				else if(slot == 44)
					monsterListYaml.set(MonsterName+".Type", "엔더크리스탈");
				monsterListYaml.saveConfig();
				MonsterOptionSettingGUI(player, MonsterName);
			}
		}
	}
}
