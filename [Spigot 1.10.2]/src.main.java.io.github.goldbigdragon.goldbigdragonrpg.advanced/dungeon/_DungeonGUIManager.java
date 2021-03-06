package dungeon;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _DungeonGUIManager
{
	//Dungeon GUI Click Unique Number = 0a
	//던전 관련 GUI의 고유 번호는 0a입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 아이템 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("00"))//던전 목록
			new dungeon.DungeonGui().DungeonListMainGUIClick(event);
		else if(SubjectCode.equals("01"))//던전 설정
			new dungeon.DungeonGui().DungeonSetUpGUIClick(event);
		else if(SubjectCode.equals("02"))//던전 보상
			new dungeon.DungeonGui().DungeonChestRewardClick(event);
		else if(SubjectCode.equals("03"))//던전 몬스터 메인
			new dungeon.DungeonGui().DungeonMonsterGUIMainClick(event);
		else if(SubjectCode.equals("04"))//던전 몬스터 타입 선택
			new dungeon.DungeonGui().DungeonMonsterChooseMainClick(event);
		else if(SubjectCode.equals("05"))//던전 일반 몬스터
			new dungeon.DungeonGui().DungeonSelectNormalMonsterChooseClick(event);
		else if(SubjectCode.equals("06"))//던전 커스텀 몬스터
			new dungeon.DungeonGui().DungeonSelectCustomMonsterChooseClick(event);
		else if(SubjectCode.equals("07"))//던전 배경 음악
			new dungeon.DungeonGui().DungeonMusicSettingGUIClick(event);
		else if(SubjectCode.equals("08"))//던전 통행증 설정
			new dungeon.DungeonGui().EnterCardSetUpGUIClick(event);
		else if(SubjectCode.equals("09"))//던전 통행증 연결
			new dungeon.DungeonGui().EnterCardDungeonSettingGUIClick(event);
		else if(SubjectCode.equals("0a"))//던전 제단 목록
			new dungeon.DungeonGui().AltarShapeListGUIClick(event);
		else if(SubjectCode.equals("0b"))//던전 제단 설정
			new dungeon.DungeonGui().AltarSettingGUIClick(event);
		else if(SubjectCode.equals("0c"))//던전 제단 연결
			new dungeon.DungeonGui().AltarDungeonSettingGUIClick(event);
		else if(SubjectCode.equals("0d"))//던전 제단에 등록된 통행증 목록
			new dungeon.DungeonGui().AltarEnterCardSettingGUIClick(event);
		else if(SubjectCode.equals("0e"))//생성된 통행증 목록
			new dungeon.DungeonGui().AltarEnterCardListGUIClick(event);
		else if(SubjectCode.equals("0f"))//던전 제단 열람 화면
			new dungeon.DungeonGui().AltarUseGUIClick(event);
		else if(SubjectCode.equals("10"))//던전 잔류 화인
			new dungeon.DungeonGui().DungeonEXITClick(event);
		
	}

	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.equals("02"))//던전 보상
			new dungeon.DungeonGui().DungeonChestRewardClose(event);
		else if(SubjectCode.equals("0f"))//던전 제단 열람 화면
			new dungeon.DungeonGui().AltarUSEGuiClose(event);
	}
}
