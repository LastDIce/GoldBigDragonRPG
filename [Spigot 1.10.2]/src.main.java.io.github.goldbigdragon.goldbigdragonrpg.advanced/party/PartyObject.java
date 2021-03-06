package party;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import effect.SoundEffect;
import util.YamlLoader;

public class PartyObject
{
	private Long CreateTime = null;
	private String Title = null;
	private String Leader = null;
	private Boolean PartyLock = false;
	private String PartyPassword = null;
	private byte PartyCapacity = 2;
	private String[] PartyMember = null;
	
	public PartyObject(Long CreateTime, Player player, String PartyName)
	{
		this.Title = PartyName;
		this.CreateTime = CreateTime;
		this.Leader = player.getName();
		this.PartyMember = new String[this.PartyCapacity];
		this.PartyMember[0] = player.getName();
		
		main.MainServerOption.partyJoiner.put(player, CreateTime);
		main.MainServerOption.party.put(CreateTime, this);
	}
	
	public PartyObject(Long CreateTime, String Leader,
			String Title, Boolean PartyLock, String PartyPassword, byte PartyCapacity,
			String[] PartyMember)
	{
		this.Title = Title;
		this.CreateTime = CreateTime;
		this.Leader = Leader;
		this.PartyLock = PartyLock;
		this.PartyPassword = PartyPassword;
		this.PartyCapacity = PartyCapacity;
		this.PartyMember = new String[this.PartyCapacity];
		
		for(int count = 0; count < this.PartyCapacity; count++)
		{
			if(PartyMember[count]=="null")
				this.PartyMember[count] = null;
			else
				this.PartyMember[count] = PartyMember[count];
		}
	}
	
	public void ChangeMaxCpacity(Player player, byte Capacity)
	{
		if(player.getName().equals(this.Leader))
			if(Capacity >= getPartyMembers())
			{
				if(Capacity >= 2)
				{
					YamlLoader configYaml = new YamlLoader();
					configYaml.getConfig("config.yml");
					if(Capacity <= configYaml.getInt("Party.MaxPartyUnit"))
					{
						this.PartyCapacity = Capacity;
						String[] TempMember = this.PartyMember;
						this.PartyMember = new String[Capacity];
						for(int count = 0; count < this.PartyCapacity; count++)
							PartyMember[count] = null;
						byte a=0;
						for(int count = 0; count < TempMember.length; count++)
						{
							if(TempMember[count]!=null&&TempMember[count]!="null")
							{
								PartyMember[a] = TempMember[count];
								a++;
							}
						}
						PartyBroadCastMessage("§a[파티] : 최대 파티원 수가 §e§l"+Capacity+"명§a으로 변경되었습니다!",null);
						PartyBroadCastSound(Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.0F,null);
					}
					else
						Message(player, (byte)3);
				}
				else
					Message(player, (byte)10);
			}
			else
				Message(player, (byte)2);
		else
			Message(player, (byte)1);
	}

	public void ChangeLock(Player player)
	{
		if(player.getName().equals(this.Leader))
		{
			if(this.PartyLock == false)
			{
				this.PartyLock = true;
				PartyBroadCastMessage("§c[파티] : 더이상 파티 모집을 하지 않습니다!",null);
				PartyBroadCastSound(Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F,null);
			}
			else
			{
				this.PartyLock = false;
				PartyBroadCastMessage("§a[파티] : 파티 모집을 시작 합니다!",null);
				PartyBroadCastSound(Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F,null);
			}
		}
		else
			Message(player, (byte)1);
	}
	
	public void SetPassword(Player player, String Message)
	{
		if(player.getName().equals(this.Leader))
		{
			this.PartyPassword = Message;
			PartyBroadCastMessage("§e[파티] : 암호가 새로 설정되었습니다!",null);
			PartyBroadCastSound(Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F,null);
		}
		else
			Message(player, (byte)1);
	}
	
	public void JoinParty(Player player)
	{
		if(main.MainServerOption.partyJoiner.containsKey(player) == false)
			if(this.PartyCapacity > getPartyMembers())
				if(this.PartyLock == false)
					if(this.PartyPassword == null)
					{
						for(int count = 0; count < this.PartyCapacity; count++)
							if(this.PartyMember[count] == null)
							{
								if(player.isOnline())
								{
									player.sendMessage("§a[파티] : 파티에 가입 하였습니다!");
									SoundEffect.SP(player, Sound.BLOCK_WOODEN_DOOR_OPEN, 1.1F, 1.0F);
								}
								this.PartyMember[count] = player.getName();
								main.MainServerOption.partyJoiner.put(player, this.CreateTime);
								PartyBroadCastMessage("§a[파티] : §e§l"+player.getName()+"§a님께서 파티에 가입하셨습니다!",player);
								PartyBroadCastSound(Sound.BLOCK_WOODEN_DOOR_OPEN, 1.1F, 1.0F,player);
								return;
							}
					}
					else
					{
						
					}
				else
					Message(player, (byte)5);
			else
				Message(player, (byte)4);
		else
			Message(player, (byte)6);
	}

	public void QuitParty(Player player)
	{
		main.MainServerOption.partyJoiner.remove(player);
		if(player.isOnline())
		{
			player.sendMessage("§c[파티] : 파티를 탈퇴하였습니다!");
			SoundEffect.SP(player, Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.1F, 1.0F);
		}
		if(getPartyMembers() == 1)
		{
			main.MainServerOption.party.remove(this.CreateTime);
			return;
		}
		for(int count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] == player.getName()||this.PartyMember[count].equals(player.getName()))
			{
				PartyBroadCastMessage("§c[파티] : §e§l"+player.getName()+"§c님께서 파티를 탈퇴하셨습니다!",player);
				PartyBroadCastSound(Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.1F, 1.0F,player);
				for(int counter = count; counter < this.PartyCapacity-1; counter++)
					this.PartyMember[counter] = this.PartyMember[counter+1];
				this.PartyMember[this.PartyMember.length-1] = null;
				break;
			}
		if(player.getName().equals(this.Leader))
		{
			for(int count = 0; count < this.PartyCapacity; count++)
				if(this.PartyMember[count] != null)
				{
					ChangeLeader(Bukkit.getServer().getPlayer(this.PartyMember[count]));
					break;
				}
		}
	}
	
	public void QuitPartyOfflinePlayer(String playerName)
	{
		if(getPartyMembers() == 1)
		{
			main.MainServerOption.party.remove(this.CreateTime);
			return;
		}
		for(int count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count]!=null)
				if(this.PartyMember[count].equals(playerName))
				{
					PartyBroadCastMessage("§c[파티] : §e§l"+playerName+"§c님께서 파티를 탈퇴하셨습니다!",null);
					PartyBroadCastSound(Sound.BLOCK_WOODEN_DOOR_CLOSE, 1.1F, 1.0F,null);
					for(int counter = count; counter < this.PartyCapacity-1; counter++)
						this.PartyMember[counter] = this.PartyMember[counter+1];
					this.PartyMember[this.PartyMember.length-1] = null;
					break;
				}
		if(playerName.equals(this.Leader))
		{
			for(int count = 0; count < this.PartyCapacity; count++)
				if(this.PartyMember[count] != null)
				{
					ChangeLeader(Bukkit.getServer().getPlayer(this.PartyMember[count]));
					break;
				}
		}
	}
	
	public void ChangeLeader(Player player)
	{
		this.Leader = player.getName();
		PartyBroadCastMessage("§e[파티] : §e§l"+player.getName()+"§e님께서 파티 리더가 되셨습니다!",null);
		PartyBroadCastSound(Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.5F,null);
	}
	
	public void ChangeLeader(Player player,String target)
	{
		if(player.getName().equals(this.Leader))
		{
			for(int count = 0; count < getMember().length; count ++)
			{	if(getMember()[count].getName() == target ||getMember()[count].getName().equals(target))
				{
					if(Bukkit.getServer().getOfflinePlayer(target).isOnline())
						if(this.Leader != target &&!this.Leader.equals(target))
							ChangeLeader(Bukkit.getPlayer(target));
						else
							Message(player, (byte)9);
					else
						Message(player, (byte)8);
					return;
				}
			}
			Message(player, (byte)11);
		}
		else
			Message(player, (byte)1);
	}

	public void ChangeTitle(Player player, String Message)
	{
		if(player.getName().equals(this.Leader))
		{
			this.Title = Message;
			PartyBroadCastMessage("§e[파티] : 파티 제목이 변경되었습니다!",null);
			PartyBroadCastSound(Sound.ENTITY_ITEM_PICKUP, 1.0F, 1.8F,null);
		}
		else
			Message(player, (byte)1);
	}
	
	public void KickPartyMember(Player player, String target)
	{
		if(player.getName()==this.Leader||player.getName().equals(this.Leader))
		{
			if(player.getName().equals(target)==false)
				if(Bukkit.getServer().getOfflinePlayer(target).isOnline())
					for(int count = 0; count < this.PartyCapacity; count++)
					{
						if(this.PartyMember[count].equals(target))
						{
							QuitParty((Player) Bukkit.getServer().getPlayer(target));
							return;
						}
					}
				else
					Message(player, (byte)8);
			else
				Message(player, (byte)7);
		}
		else
			Message(player, (byte)1);
	}
	
	public String getLeader()
	{
		return this.Leader;
	}
	
	public Player[] getMember()
	{
		Player[] p = new Player[this.PartyCapacity];
		byte a = 0;
		for(int count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] != null)
				if(Bukkit.getServer().getOfflinePlayer(this.PartyMember[count]).isOnline())
				{
					p[a] = Bukkit.getServer().getPlayer(this.PartyMember[count]);
					a++;
				}
				else
					this.PartyMember[count] = null;
		Player[] pp = new Player[a];
		for(int count = 0; count < pp.length; count++)
			pp[count] = p[count];
		
		return pp;
	}
	
	public boolean getLock()
	{
		return this.PartyLock;
	}

	public String getTitle()
	{
		return this.Title;
	}

	public int getCapacity()
	{
		return this.PartyCapacity;
	}

	public String getPassword()
	{
		return this.PartyPassword;
	}
	
	public void getPartyInformation()
	{
		
	}
		
	public int getPartyMembers()
	{
		int Members = 0;
		for(int count = 0; count < this.PartyCapacity; count++)
			if(this.PartyMember[count] != null)
				if(Bukkit.getServer().getOfflinePlayer(this.PartyMember[count]).isOnline())
					Members = Members+1;
		return Members;
	}
	
	public void PartyBroadCastMessage(String Message, Player noAlertMember)
	{
		Player[] p = getMember();
		
		for(int count = 0; count < p.length; count++)
		{
			if(p[count] != null && p[count] != noAlertMember)
				if(Bukkit.getServer().getOfflinePlayer(p[count].getName()).isOnline())
					p[count].sendMessage(Message);
		}
	}

	public void PartyBroadCastSound(Sound s, float volume, float pitch, Player noAlertMember)
	{
		Player[] p = getMember();
		for(int count = 0; count < p.length; count++)
			if(p[count] != null && p[count] != noAlertMember)
				if(Bukkit.getServer().getOfflinePlayer(p[count].getName()).isOnline())
					SoundEffect.SP(p[count], s, volume, pitch);
	}
	
	public void Message(Player player, byte num)
	{
		SoundEffect.SP(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.8F);
		switch(num)
		{
		case 1:
			player.sendMessage("§c[파티] : 당신은 파티 리더가 아닙니다!");
			return;
		case 2:
			player.sendMessage("§c[파티] : 최대 인원수를 현재 파티 인원 수 보다 적게할 수 없습니다!");
			return;
		case 3:
			{
				YamlLoader configYaml = new YamlLoader();
				configYaml.getConfig("config.yml");
				player.sendMessage("§c[파티] : 최대 파티 인원 수 : §e§l"+configYaml.getInt("Party.MaxPartyUnit")+"명");
			}
			return;
		case 4:
			player.sendMessage("§c[파티] : 파티 인원이 다 찼습니다!");
			return;
		case 5:
			player.sendMessage("§c[파티] : 해당 파티는 더이상 파티원을 모집하지 않습니다!");
			return;
		case 6:
			player.sendMessage("§c[파티] : 당신은 이미 다른 파티에 참여 중입니다!");
			return;
		case 7:
			player.sendMessage("§c[파티] : 자기 자신은 강퇴 시킬 수 없습니다!");
			return;
		case 8:
			player.sendMessage("§c[파티] : 해당 플레이어는 파티원이 아닙니다!");
			return;
		case 9:
			player.sendMessage("§c[파티] : 당신은 이미 파티 리더입니다!");
			return;
		case 10:
			player.sendMessage("§c[파티] : 파티 인원은 최소 2명 이상이어야 합니다!");
			return;
		case 11:
			player.sendMessage("§c[파티] : 해당 플레이어는 파티원이 아닙니다!");
			return;
		}
	}
}
