package GBD_RPG.Making;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class _MakingGUIManager
{
	//Making GUI Click Unique Number = 0e
	//제작 관련 GUI의 고유 번호는 0e입니다.
	
	//If you want add this system, just Put it in for Main_InventoryClcik!
	//당신이 제작 관련 GUI 기능을 넣고싶을땐, 그냥 Main_InventoryClick 클래스 안에 넣으세요!
	
	public void ClickRouting(InventoryClickEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("00")==0)//전체 영역 목록
			new GBD_RPG.Area.Area_GUI().AreaListGUIClick(event);
	}
	
	public void CloseRouting(InventoryCloseEvent event, String SubjectCode)
	{
		if(SubjectCode.compareTo("04")==0)//영역 낚시 보상 설정
			new GBD_RPG.Area.Area_GUI().FishingSettingInventoryClose(event);
	}
}
