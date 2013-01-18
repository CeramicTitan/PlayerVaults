package me.shock.playervaults;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class Listeners implements Listener 
{

	public Main plugin;
	public Listeners(Main instance)
	{
		this.plugin = instance;
	}
	
	@EventHandler
	public void onJoin(PlayerQuitEvent event){
		saveVault(event.getPlayer());}
	@EventHandler
	public void onDeath(PlayerDeathEvent event){
		saveVault(event.getEntity());}
	@EventHandler
	public void onTP(PlayerTeleportEvent event){
		saveVault(event.getPlayer());}
	@EventHandler
	public void close(InventoryCloseEvent event){
		Player player = (Player)event.getPlayer();
	    saveVault(player);}
	/**
	 * Check if a player is trying to do something while
	 * in a vault.
	 * Don't let them open up another chest.
	 * @param event
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if(plugin.invaultPlayers.containsKey(player.getName()) && event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			Block block = event.getClickedBlock();
			
			/**
			 * Different inventories that
			 * we don't want the player to open.
			 */
			if(block.getType() == Material.CHEST 
					|| block.getType() == Material.ENDER_CHEST
					|| block.getType() == Material.FURNACE
					|| block.getType() == Material.BURNING_FURNACE
					|| block.getType() == Material.STORAGE_MINECART
					|| block.getType() == Material.MINECART
					|| block.getType() == Material.POWERED_MINECART
					|| block.getType() == Material.BREWING_STAND
					|| block.getType() == Material.BEACON)
			{
				event.setCancelled(true);
			}
		}
	}
	
	/**
	 * Don't let a player open a trading inventory
	 * while he has his vault open.
	 * @param event
	 */
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event)
	{
		Player player = event.getPlayer();
		EntityType type = event.getRightClicked().getType();
		if(type == EntityType.VILLAGER && plugin.invaultPlayers.containsKey(player.getName()))
		{
			event.setCancelled(true);
		}
	}
	
	/**
	 * Method to save player's vault.
	 * Serialize his inventory.
	 * Save the vaults.yml
	 * @param player
	 */
	public void saveVault(Player player)
	{
		if(plugin.invaultPlayers.containsKey(player.getName()))
		{
			String inventory = plugin.InventoryToString(player.getOpenInventory().getTopInventory(), player);
			plugin.vaults.set(player.getName() + "." + (String)plugin.invaultPlayers.get(player.getName()), inventory);
			plugin.saveData();
		}
	}
}
