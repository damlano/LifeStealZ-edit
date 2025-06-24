package org.strassburger.lifestealz.listeners;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.strassburger.lifestealz.LifeStealZ;
import org.strassburger.lifestealz.util.customitems.CustomItemManager;
import org.strassburger.lifestealz.util.customitems.CustomItemType;

public final class PrepareItemCraft implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Bukkit.getServer().broadcast(player.getName() + " has created a heart.", Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
        }
    }

    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        ItemStack[] matrix = inventory.getMatrix();

        if (result == null || result.getItemMeta() == null) return;

        // ❌ Prevent non-custom items from being crafted using custom item ingredients
        if (CustomItemManager.isCustomItem(result)) return;
        if (!matrixHasCustomItem(matrix)) return;

        inventory.setResult(null);
    }

    private boolean matrixHasCustomItem(ItemStack[] matrix) {
        for (ItemStack item : matrix) {
            if (item == null || item.getItemMeta() == null) continue;
            if (CustomItemManager.isCustomItem(item)) return true;
        }
        return false;
    }
}
