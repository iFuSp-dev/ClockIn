package me.aifusp.clockin.gui.menu.boss;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.InventoryName;
import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class Boss_Workers_List {
    String job;
    Player player;
    Inventory inventory;

    public Boss_Workers_List(Player player,String job){
        this.player = player;
        this.job = job;
        createInventory();
    }

    private void createInventory(){
        inventory = Bukkit.createInventory(player, InventoryName.BOSS_WORKERS.getSize(), InventoryName.BOSS_WORKERS.getName());

        File file = new File(Main.getInstance().getDataFolder()+"/saves",job);
        if (file.exists()){
            int index = 0;
            for (File sfile : file.listFiles()) {
                if (sfile.isDirectory()) {
                        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(sfile.getName());
                        item.setItemMeta(meta);

                        NBTItem nbtItem = new NBTItem(item);
                        nbtItem.setBoolean("worker",true);
                        nbtItem.setString("Worker",sfile.getName());
                        nbtItem.setString("Job",job);
                        nbtItem.applyNBT(item);

                        inventory.setItem(index,item);
                        index ++;
                }
            }

        }


    }
    public Inventory getInventory() {
        return this.inventory;
    }

}
