package me.aifusp.clockin.gui.menu.boss;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.InventoryName;
import org.aifusp.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Boss_Menu {
    String job;
    Player player;
    Inventory inventory;
    public Boss_Menu(Player player,String job){
        this.player = player;
        this.job = job;
        createInventory();
    }

    private void createInventory(){
        inventory = Bukkit.createInventory(player, InventoryName.BOSS_MENU.getSize(), InventoryName.BOSS_MENU.getName());


        inventory.setItem(1,jobTag(workers()));
        inventory.setItem(3,jobTag(deleteFiles()));
        inventory.setItem(0,blackStainedGlass());
        inventory.setItem(2,blackStainedGlass());
        inventory.setItem(4,blackStainedGlass());
        inventory.setItem(5,blackStainedGlass());
        inventory.setItem(6,blackStainedGlass());
        inventory.setItem(7,blackStainedGlass());
        inventory.setItem(8,blackStainedGlass());
    }

    private ItemStack workers(){
        ItemStack item = new ItemStack(Material.BOOKSHELF);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("#6fd6d8&lWorkers"));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("workers",true);
        nbtItem.applyNBT(item);

        return item;
    }
    private ItemStack deleteFiles(){
        ItemStack item = new ItemStack(Material.BOOKSHELF);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("#ff1a1a&n&lDelete All Files&r &#ff1a1a&l!"));
        item.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("deleteFiles",true);
        nbtItem.applyNBT(item);

        return item;
    }
    private ItemStack blackStainedGlass(){
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private ItemStack jobTag(ItemStack item){
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("Job",this.job);
        nbtItem.applyNBT(item);

        return item;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}

