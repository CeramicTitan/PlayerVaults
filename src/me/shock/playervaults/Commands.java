package me.shock.playervaults;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{

	public Main plugin;
	public Commands(Main instance)
	{
		this.plugin = instance;
	}
		
	String pv = ChatColor.DARK_RED + "[" + ChatColor.WHITE + "PlayerVaults" + ChatColor.DARK_RED + "]" + ChatColor.WHITE + ": ";
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		/**
		 * No point in letting console run a command.
		 * Let's just cancel that right away.
		 */
		if(cmd.getName().equalsIgnoreCase("vault"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage("[PlayerVaults] Sorry but the console can't have a vault :(");
				return true;
			}
			else
			{
				Player player = (Player) sender;
				if(args.length == 1)
				{
					if(args[0].matches("[1-9]"))
					{
						if(player.hasPermission("playervaults.amount." + args[0]))
						{
							this.plugin.openLargeVault(args[0], player);
							player.sendMessage(pv + "Opening vault " + ChatColor.GREEN + args[0]);
						}
						else
						{
							player.sendMessage(pv + "You don't have permission for that many chests!");
						}
					}
					else
					{
						player.sendMessage(pv + "Syntax is: /vault <number>");
						return true;
					}
				}
				else
				{
					player.sendMessage(pv + "Syntax is: /vault <number>");
					return true;
				}
			}
		}
		
		return false;
	}
}
