package barbary.testproject;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class TestProject extends JavaPlugin implements Listener {

    public static TestProject plugin;

    public static TestEnchantment TEST;

    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            TEST = new TestEnchantment();
            Enchantment.registerEnchantment(TEST);
        });

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    private void on(BlockBreakEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.NETHERITE_PICKAXE ||
                event.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE)
            if (event.getPlayer().getItemInHand().getLore() != null) {
                final List<String> lore = event.getPlayer().getItemInHand().getLore();
                lore.add(ChatColor.GRAY + "Тест I");
                event.getPlayer().getItemInHand().setLore(lore);
                event.getPlayer().getItemInHand().addEnchantment(TEST, 1);
            } else {
                final List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Тест I");
                event.getPlayer().getItemInHand().setLore(lore);
                event.getPlayer().getItemInHand().addEnchantment(TEST, 1);
            }
    }

    @EventHandler
    private void on(PlayerJumpEvent event) {
        if (event.getPlayer().getItemInHand().containsEnchantment(TEST)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("У вас в главной руке зачарование Тест!");
            event.getPlayer().sendMessage("Уровень зачарования: "
                    + event.getPlayer().getItemInHand().getEnchantmentLevel(TEST));
        }
    }
}
