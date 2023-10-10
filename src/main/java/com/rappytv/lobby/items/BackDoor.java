package com.rappytv.lobby.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackDoor extends ItemStack {

    public BackDoor() {
        super(Material.SPRUCE_DOOR);
        ItemMeta meta = this.getItemMeta();
        meta.setDisplayName("§bZurück");
        this.setItemMeta(meta);
    }
}
