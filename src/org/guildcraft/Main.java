package org.guildcraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		this.sendConsole("[StrengthNerf] StrengthNerf has been enabled!");
	}
	
	public void onDisable() {
		
		this.sendConsole("[StrengthNerf] StrengthNerf has been disbaled!");
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			if (p.getItemInHand() == null)
				return;
			
			if (p.getItemInHand().getType() != Material.POTION)
				return;
			
			short data = p.getItemInHand().getDurability();
			
			if (data == 8233 || data == 16425) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You are not allowed to use Strength II potions!");
			}	
		}
	}
	
	@EventHandler
	public void onInventoryClickGlowstone(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getType() != InventoryType.BREWING)
			return;
		
		BrewerInventory inv = (BrewerInventory) e.getInventory();
		
		if (e.getCurrentItem().getType() != Material.GLOWSTONE_DUST)
			return;
		
		boolean potion = false;
		
		for (ItemStack i : inv.getContents()) {
			
			if (i == null)
				continue;
			
			short data = i.getDurability();
			
			if (data == 8201 || data == 8265 || data == 16457 || data == 16393) {
				potion = true;
				break;
			}
		}
		
		if (potion == true) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not allowed to brew strength II potions!");
		}
	}
	
	@EventHandler
	public void onInventoryClickPotion(InventoryClickEvent e) {
		
		if (!(e.getWhoClicked() instanceof Player))
			return;
		
		Player p = (Player) e.getWhoClicked();
		
		if (e.getInventory().getType() != InventoryType.BREWING)
			return;
		
		BrewerInventory inv = (BrewerInventory) e.getInventory();
		
		boolean glowstone = false;
		
		for (ItemStack i : inv.getContents()) {
			
			if (i == null)
				continue;
			
			if (i.getType() == Material.GLOWSTONE_DUST) {
				glowstone = true;
				break;
			}
		}
		
		if (glowstone != true)
			return;
		
		short data = e.getCurrentItem().getDurability();
		
		if (data == 8201 || data == 8265 || data == 16457 || data == 16393) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not allowed to brew strength II potions!");
		}
	}
	
	private void sendConsole(String message) {
		this.getLogger().info(message);
	}
}
