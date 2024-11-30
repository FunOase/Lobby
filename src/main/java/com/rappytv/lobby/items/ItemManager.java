package com.rappytv.lobby.items;

import com.rappytv.lobby.LobbyPlugin;
import net.funoase.sahara.bukkit.i18n.I18n;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemManager {

    private final LobbyPlugin plugin;
    private final NamespacedKey actionKey;
    private final NamespacedKey valueKey;

    public ItemManager(LobbyPlugin plugin) {
        this.plugin = plugin;
        this.actionKey = new NamespacedKey(plugin, "lobby_action");
        this.valueKey = new NamespacedKey(plugin, "lobby_value");
    }

    @Nullable
    public ItemStack getItem(String item, Player player) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("items." + item);
        if(section == null) return null;
        Material material = Material.getMaterial(section.getString("material", "dirt"));
        if(material == null) material = Material.DIRT;
        boolean enchanted = section.getBoolean("enchanted", false);
        ItemAction.Action action = ItemAction.Action.fromString(section.getString("action", "none"));
        String value = section.getString("value", "");
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        if(meta == null) return null;
        meta.displayName(I18n.component(player, "lobby.items." + item));
        if(enchanted) {
            meta.addEnchant(Enchantment.LUCK_OF_THE_SEA, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        stack.setItemMeta(meta);
        return setItemAction(stack, new ItemAction(action, value));
    }

    @NotNull
    public ItemStack setItemAction(ItemStack stack, ItemAction action) {
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(actionKey, PersistentDataType.STRING, action.action.name().toLowerCase());
        meta.getPersistentDataContainer().set(valueKey, PersistentDataType.STRING, action.value);
        stack.setItemMeta(meta);
        return stack;
    }

    @Nullable
    public ItemAction getItemAction(ItemStack stack) {
        if(!stack.getPersistentDataContainer().has(actionKey)) return null;
        return new ItemAction(
                ItemAction.Action.fromString(stack.getPersistentDataContainer().getOrDefault(actionKey, PersistentDataType.STRING, "none")),
                stack.getPersistentDataContainer().getOrDefault(valueKey, PersistentDataType.STRING, "")
        );
    }

    public record ItemAction(Action action, String value) {
        public enum Action {
            NONE,
            SERVER,
            GUI;

            public static Action fromString(String name) {
                try {
                    return valueOf(name.toUpperCase());
                } catch (IllegalArgumentException e) {
                    return NONE;
                }
            }
        }
    }
}
