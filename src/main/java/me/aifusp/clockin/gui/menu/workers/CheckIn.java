package me.aifusp.clockin.gui.menu.workers;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.Buttons;
import me.aifusp.clockin.InventoryName;
import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class CheckIn {

    private Player player;
    private String job;
    private Inventory inventory;
    private Buttons buttons;
    private Main main = Main.getInstance();
    public CheckIn(Player player,String job){
        this.player = player;
        this.job = job;
        this.buttons = new Buttons();


        createInventory();
    }


    public void createInventory(){
        inventory = Bukkit.createInventory(player, InventoryName.CHECKIN.getSize(), InventoryName.CHECKIN.getName());

        inventory.setItem(0,blackStainedGlass());
        inventory.setItem(1,blackStainedGlass());
        inventory.setItem(2,blackStainedGlass());
        inventory.setItem(3,blackStainedGlass());
        inventory.setItem(5,blackStainedGlass());
        inventory.setItem(6,blackStainedGlass());
        inventory.setItem(7,blackStainedGlass());
        inventory.setItem(8,blackStainedGlass());



        File file = new File(main.getDataFolder() + "/temp", player.getName() + "_" + job + ".yml");
        if (file.exists()){
            inventory.setItem(4,jobTag(buttons.UnCheck()));
        }else{
            inventory.setItem(4,jobTag(buttons.Check()));

        }





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
