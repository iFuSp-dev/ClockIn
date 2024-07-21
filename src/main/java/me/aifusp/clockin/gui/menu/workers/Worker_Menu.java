package me.aifusp.clockin.gui.menu.workers;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.Buttons;
import me.aifusp.clockin.InventoryName;
import org.aifusp.Main;
import org.aifusp.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class Worker_Menu {

    private Player player;
    private String job;
    private Inventory inventory;
    private Buttons buttons;
    private Main main = Main.getInstance();


    public Worker_Menu(Player player,String job){
        this.player = player;
        this.job = job;
        this.buttons = new Buttons();

        createInventory();








    }


    private void createInventory(){

        inventory = Bukkit.createInventory(player, InventoryName.WORKER_MENU.getSize(), InventoryName.WORKER_MENU.getName());

        inventory.setItem(3,CheckIn());
        inventory.setItem(5,Registry());
        inventory.setItem(0,blackStainedGlass());
        inventory.setItem(1,blackStainedGlass());
        inventory.setItem(2,blackStainedGlass());
        inventory.setItem(4,blackStainedGlass());
        inventory.setItem(6,blackStainedGlass());
        inventory.setItem(7,blackStainedGlass());
        inventory.setItem(8,blackStainedGlass());














    }


    private ItemStack Registry(){
        ItemStack itemStack = new ItemStack(Material.BOOKSHELF);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("#d44e9c&l&nRegister"));
        itemStack.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("registry",true);
        nbtItem.applyNBT(itemStack);
        ItemStack item = jobTag(itemStack);
        return item;
    }

    private ItemStack blackStainedGlass(){
        ItemStack itemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(" ");
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    private ItemStack CheckIn(){
        ItemStack itemStack = new ItemStack(Material.MAP);

        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(MessageUtils.getColoredMessage("#65d267&l&nCheck-In"));
        itemStack.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(itemStack);
        nbtItem.setBoolean("checkIn",true);
        nbtItem.applyNBT(itemStack);
        ItemStack item = jobTag(itemStack);
        return item;
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
