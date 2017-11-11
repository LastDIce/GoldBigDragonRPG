package user;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import effect.SoundEffect;
import main.Main_ServerOption;
import servertick.ServerTick_Main;
import util.YamlLoader;

public class User_Command
{
	public void onCommand(Player player, String[] args, String string)
	{
		
		switch(string)
		{
		case "����":
		{
			if(ServerTick_Main.PlayerTaskList.containsKey(player.getName()))
			{
				long UTC = Long.parseLong(ServerTick_Main.PlayerTaskList.get(player.getName()));
				servertick.ServerTick_Object STSO = ServerTick_Main.Schedule.get(UTC);
				String taskType = STSO.getType();
				
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).equals(player.getName()))
						{
							SoundEffect.SP(player, Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							SoundEffect.SP(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Sound.ENTITY_HORSE_ARMOR, 1.0F, 1.7F);
							new user.Equip_GUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)0)), Bukkit.getServer().getPlayer(STSO.getString((byte)1)),null,false,false);
							new user.Equip_GUI().ExChangeGUI(Bukkit.getServer().getPlayer(STSO.getString((byte)1)), Bukkit.getServer().getPlayer(STSO.getString((byte)0)),null,false,false);
							ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)0));
							ServerTick_Main.PlayerTaskList.remove(ServerTick_Main.Schedule.get(UTC).getString((byte)1));
							ServerTick_Main.Schedule.remove(UTC);
						}
					}
					break;
				}
			}
		}
		return;
		case "����":
		{
			if(ServerTick_Main.PlayerTaskList.containsKey(player.getName()))
			{
				servertick.ServerTick_Object STSO = ServerTick_Main.Schedule.get(Long.parseLong(ServerTick_Main.PlayerTaskList.get(player.getName())));
				String taskType = STSO.getType();
				switch(taskType)
				{
					case "P_EC"://Player Exchange
					{
						if(STSO.getString((byte)1).equals(player.getName()))
						{
							servertick.ServerTask_Player SP = new servertick.ServerTask_Player();
							SP.Cancel(STSO.getTick(), (short) 0);
						}
					}
					break;
				}
			}
		}
		return;
		case "��":
  		{
  			long Money = main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).getStat_Money();
  			if(args.length == 0)
  			{
			 	SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 0.8F, 1.8F);
			 	player.sendMessage("��e[���� ���� �ݾ�] ��f��l" +Money + " "+Main_ServerOption.money);
			 	player.sendMessage("��6/�� ������ [�ݾ�]��f �ش� �ݾ� ��ŭ ���� ���������� �����ϴ�.");
			 	player.sendMessage("��6/�� ��ŷ [�г���]��f �ش� �÷��̾��� ��ŷ�� Ȯ���մϴ�.");
  				if(player.isOp()==true)
	  				player.sendMessage("��b/�� �ֱ� [�ݾ�] [�÷��̾�]��f �ش� �ݾ� ��ŭ �÷��̾�� ���� �ݴϴϴ�.��b��l(������)");
  			}
  			else if(args.length == 2&&args[0].equals("������"))
  			{
				try
				{
					if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
					{
						if(Money >= Integer.parseInt(args[1]))
						{
							for(int count = 0; count < 36;count++)
							{
								if(player.getInventory().getItem(count)==null)
								{
									int money = Integer.parseInt(args[1]);
									main.Main_ServerOption.PlayerList.get(player.getUniqueId().toString()).addStat_MoneyAndEXP(-1 * money, 0, false);
									ItemStack Icon;
									if(money <= 50)
										Icon = new MaterialData(Main_ServerOption.Money1ID, (byte) Main_ServerOption.Money1DATA).toItemStack();
									else if(money <= 100)
										Icon = new MaterialData(Main_ServerOption.Money2ID, (byte) Main_ServerOption.Money2DATA).toItemStack();
									else if(money <= 1000)
										Icon = new MaterialData(Main_ServerOption.Money3ID, (byte) Main_ServerOption.Money3DATA).toItemStack();
									else if(money <= 10000)
										Icon = new MaterialData(Main_ServerOption.Money4ID, (byte) Main_ServerOption.Money4DATA).toItemStack();
									else if(money <= 50000)
										Icon = new MaterialData(Main_ServerOption.Money5ID, (byte) Main_ServerOption.Money5DATA).toItemStack();
									else
										Icon = new MaterialData(Main_ServerOption.Money6ID, (byte) Main_ServerOption.Money6DATA).toItemStack();
									Icon.setAmount(1);
									ItemMeta Icon_Meta = Icon.getItemMeta();
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
									Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
									Icon_Meta.setDisplayName(Main_ServerOption.money);
									StringBuffer MoneyString = new StringBuffer();
									short Mok = 0;
									if(money==100000000||money==10000000||money==1000000||
										money==100000||money==10000||money==1000||money==100||
										money==10)
									{
										switch(money)
										{
											case 100000000:
												MoneyString.append("1��");
												break;
											case 10000000:
												MoneyString.append("1õ��");
												break;
											case 1000000:
												MoneyString.append("1�鸸");
												break;
											case 100000:
												MoneyString.append("1�ʸ�");
												break;
											case 10000:
												MoneyString.append("1��");
												break;
											case 1000:
												MoneyString.append("1õ");
												break;
											case 100:
												MoneyString.append("1��");
												break;
											case 10:
												MoneyString.append("1��");
												break;
										}
									}
									else
									{
										if(money >= 10000000)
										{
											Mok = (short) (money / 10000000);
											MoneyString.append(Mok+"õ");
											money = money-(Mok*10000000);
										}
										if(money >= 1000000)
										{
											Mok = (short) (money / 1000000);
											MoneyString.append(Mok+"��");
											money = money-(Mok*1000000);
										}
										if(money >= 100000)
										{
											Mok = (short) (money / 100000);
											MoneyString.append(Mok+"��");
											money = money-(Mok*100000);
										}
										if(money >= 10000)
										{
											Mok = (short) (money / 10000);
											MoneyString.append(Mok+"�� ");
											money = money-(Mok*10000);
										}
										else if(Integer.parseInt(args[1]) >= 10000)
										{
											MoneyString.append("�� ");
										}
										if(money >= 1000)
										{
											Mok = (short) (money / 1000);
											MoneyString.append(Mok+"õ");
											money = money-(Mok*1000);
										}
										if(money >= 100)
										{
											Mok = (short) (money / 100);
											MoneyString.append(Mok+"��");
											money = money-(Mok*100);
										}
										if(money >= 10)
										{
											Mok = (short) (money / 10);
											MoneyString.append(Mok+"��");
											money = money-(Mok*10);
										}
										if(money >= 1)
										{
											Mok = (short) (money / 1);
											MoneyString.append(Mok);
										}
									}
									Icon_Meta.setLore(Arrays.asList("��e[��]             ��f[�Ϲ�]","��f��l"+args[1]+" "+Main_ServerOption.money,"��7("+MoneyString.toString()+" "+ChatColor.stripColor(Main_ServerOption.money)+")","","��7�� Ŭ���� �� ���·�","��7�Աݵ˴ϴ�."));
									Icon.setItemMeta(Icon_Meta);
									player.getInventory().addItem(Icon);
									SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
									player.sendMessage("��a[System] : ��f��l"+args[1]+" "+Main_ServerOption.money+"��a ��(��) ���½��ϴ�!");
									return;
								}
							}
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							player.sendMessage("��c[System] : �κ��丮 ������ �����մϴ�!");
						}
						else
						{
							player.sendMessage("��c[SYSTEM] : ���� ���� �ݾ��� �ʰ��ϴ� ���Դϴ�!");
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
					}
					else
					{
						player.sendMessage("��c[SYSTEM] : �ּ� ��e"+1+"��c, �ִ� ��e"+100000000+"��c ������ ���ڸ� �Է��ϼ���!");
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
					}
				}
				catch(NumberFormatException e)
				{
					player.sendMessage("��c[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. (��e"+1+"��c ~ ��e"+100000000+"��c)");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
  			}
  			else if(args.length == 3&&args[0].equals("�ֱ�")&&player.isOp())
  			{
  				if(Bukkit.getServer().getPlayer(args[2]) != null)
  				{
  					Player target = Bukkit.getServer().getPlayer(args[2]);
	  				if(target.isOnline())
	  				{
						try
						{
							if(args[1].length() >= 1 && Integer.parseInt(args[1]) >= 1&& Integer.parseInt(args[1]) <= 100000000)
							{
								for(int count = 0; count < 36;count++)
								{
									if(target.getInventory().getItem(count)==null)
									{
										int money = Integer.parseInt(args[1]);
										ItemStack Icon;
										if(money <= 50)
											Icon = new MaterialData(Main_ServerOption.Money1ID, (byte) Main_ServerOption.Money1DATA).toItemStack();
										else if(money <= 100)
											Icon = new MaterialData(Main_ServerOption.Money2ID, (byte) Main_ServerOption.Money2DATA).toItemStack();
										else if(money <= 1000)
											Icon = new MaterialData(Main_ServerOption.Money3ID, (byte) Main_ServerOption.Money3DATA).toItemStack();
										else if(money <= 10000)
											Icon = new MaterialData(Main_ServerOption.Money4ID, (byte) Main_ServerOption.Money4DATA).toItemStack();
										else if(money <= 50000)
											Icon = new MaterialData(Main_ServerOption.Money5ID, (byte) Main_ServerOption.Money5DATA).toItemStack();
										else
											Icon = new MaterialData(Main_ServerOption.Money6ID, (byte) Main_ServerOption.Money6DATA).toItemStack();
										Icon.setAmount(1);
										ItemMeta Icon_Meta = Icon.getItemMeta();
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
										Icon_Meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
										Icon_Meta.setDisplayName(Main_ServerOption.money);
										StringBuffer MoneyString = new StringBuffer();
										short Mok = 0;
										if(money==100000000||money==10000000||money==1000000||
											money==100000||money==10000||money==1000||money==100||
											money==10)
										{
											switch(money)
											{
												case 100000000:
													MoneyString.append("1��");
													break;
												case 10000000:
													MoneyString.append("1õ��");
													break;
												case 1000000:
													MoneyString.append("1�鸸");
													break;
												case 100000:
													MoneyString.append("1�ʸ�");
													break;
												case 10000:
													MoneyString.append("1��");
													break;
												case 1000:
													MoneyString.append("1õ");
													break;
												case 100:
													MoneyString.append("1��");
													break;
												case 10:
													MoneyString.append("1��");
													break;
											}
										}
										else
										{
											if(money >= 10000000)
											{
												Mok = (short) (money / 10000000);
												MoneyString.append(Mok+"õ");
												money = money-(Mok*10000000);
											}
											if(money >= 1000000)
											{
												Mok = (short) (money / 1000000);
												MoneyString.append(Mok+"��");
												money = money-(Mok*1000000);
											}
											if(money >= 100000)
											{
												Mok = (short) (money / 100000);
												MoneyString.append(Mok+"��");
												money = money-(Mok*100000);
											}
											if(money >= 10000)
											{
												Mok = (short) (money / 10000);
												MoneyString.append(Mok+"�� ");
												money = money-(Mok*10000);
											}
											else if(Integer.parseInt(args[1]) >= 10000)
											{
												MoneyString.append("�� ");
											}
											if(money >= 1000)
											{
												Mok = (short) (money / 1000);
												MoneyString.append(Mok+"õ");
												money = money-(Mok*1000);
											}
											if(money >= 100)
											{
												Mok = (short) (money / 100);
												MoneyString.append(Mok+"��");
												money = money-(Mok*100);
											}
											if(money >= 10)
											{
												Mok = (short) (money / 10);
												MoneyString.append(Mok+"��");
												money = money-(Mok*10);
											}
											if(money >= 1)
											{
												Mok = (short) (money / 1);
												MoneyString.append(Mok);
											}
										}
										Icon_Meta.setLore(Arrays.asList("��e[��]             ��f[�Ϲ�]","��f��l"+args[1]+" "+Main_ServerOption.money,"��7("+MoneyString.toString()+" "+ChatColor.stripColor(Main_ServerOption.money)+")","","��7�� Ŭ���� �� ���·�","��7�Աݵ˴ϴ�."));
										Icon.setItemMeta(Icon_Meta);
										target.getInventory().addItem(Icon);
										SoundEffect.SP(target, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										target.sendMessage("��a[System] : �����ڷ� ���� ��f��l"+args[1]+" "+Main_ServerOption.money+"��a ��(��) �޾ҽ��ϴ�!");
										SoundEffect.SP(player, org.bukkit.Sound.BLOCK_LAVA_POP, 2.0F, 1.7F);
										player.sendMessage("��a[System] : "+target.getName()+"���� ��f��l"+args[1]+" "+Main_ServerOption.money+"��a ��(��) �־����ϴ�!");
										return;
									}
								}
								SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
								player.sendMessage("��c[System] : �κ��丮 ������ �����մϴ�!");
								return;
							}
							else
							{
								player.sendMessage("��c[SYSTEM] : �ּ� ��e"+1+"��c, �ִ� ��e"+100000000+"��c ������ ���ڸ� �Է��ϼ���!");
								SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
							}
						}
						catch(NumberFormatException e)
						{
							player.sendMessage("��c[SYSTEM] : ���� ������ ��(����)�� �Է��ϼ���. (��e"+1+"��c ~ ��e"+100000000+"��c)");
							SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
						}
	  				}
	  				else
	  				{
						player.sendMessage("��c[SYSTEM] : �ش� �÷��̾�� �������� �ƴմϴ�!");
						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  				}
  				}
  				else
  				{
					player.sendMessage("��c[SYSTEM] : �ش� �÷��̾�� �������� �ƴմϴ�!");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
  				}
  			}
  			else if(args[0].equals("��ŷ"))
  			{
    		  	YamlLoader YAML = new YamlLoader();
    			YAML.getConfig("Ranking/money.yml");
				if(YAML.contains("Rank")&&YAML.getConfigurationSection("Rank").getKeys(false).size()>0)
				{
	  				if(args.length == 2)
	  				{
	  					if(YAML.contains("NameSet."+args[1]))
	  	  					player.sendMessage("��e��l����[��a��l"+ (YAML.getInt("NameSet."+args[1]+".Rank")+1) +"��e��l] ��f��l"+args[1]+ " ��6("+YAML.getLong("NameSet."+args[1]+".Money")+")");
	  	  				else
	  	  				{
	  						player.sendMessage("��c[SYSTEM] : �ش� �÷��̾ ���� �ڷᰡ �����ϴ�!");
	  						SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
	  	  				}
	  				}
	  				else
	  				{
  	  					int rankSize = YAML.getConfigurationSection("Rank").getKeys(false).size();
  	  					if(rankSize > 5)
  	  						rankSize = 5;
  	  					player.sendMessage("��e��l��������������������������[Ranking]��������������������������");
	  	  				for(int count = 0 ; count < rankSize; count++)
	  	  					player.sendMessage("��e��l����[��a��l"+ (count+1) +"��e��l] ��f��l"+YAML.getString("Rank."+count+".Name") + " ��6("+YAML.getLong("Rank."+count+".Money")+")");
  	  					player.sendMessage("��e��l��������������������������[Ranking]��������������������������");
	  				}
				}
				else
				{
					player.sendMessage("��c[SYSTEM] : ��ŷ�� �ҷ� �� ���� �����ϴ�! ��� �� �ٽ� �õ� �� �ֽñ� �ٶ��ϴ�.");
					SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 1.7F);
				}
  			}
  			else
  			{
  				SoundEffect.SP(player, org.bukkit.Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.8F, 1.8F);
  				player.sendMessage("��6/����f ���� �ڽ��� ������ �ݾ��� Ȯ���մϴ�.");
  				player.sendMessage("��6/�� ������ [�ݾ�]��f �ش� �ݾ� ��ŭ ���� ���������� �����ϴ�.");
			 	player.sendMessage("��6/�� ��ŷ [�г���]��f �ش� �÷��̾��� ��ŷ�� Ȯ���մϴ�.");
  				if(player.isOp()==true)
	  				player.sendMessage("��b/�� �ֱ� [�ݾ�] [�÷��̾�]��f �ش� �ݾ� ��ŭ �÷��̾�� ���� �ݴϴϴ�.��b��l(������)");
  			}
  		}
  		return;
		}
	}
}