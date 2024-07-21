package org.aifusp.commands;

import de.tr7zw.nbtapi.NBTItem;
import me.aifusp.clockin.gui.menu.workers.CheckIn;
import me.aifusp.clockin.gui.menu.workers.Worker_Menu;
import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    private Main plugin;

    public MainCommand(Main plugin){this.plugin = plugin;}


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ClockIn")){
            if (!(sender instanceof Player)){
                return true;
            }

            Player player = (Player) sender;
            if (args.length == 2){
                switch (args[0]){
                    case "worker":
                        ItemStack item = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName("[ClockIn] "+args[1]+" Worker");

                        List<String> lore = new ArrayList<>();
                           lore.add("Si no eres administrador");
                           lore.add("Contacta con un admin para devovlerlo");
                        meta.setLore(lore);

                        item.setItemMeta(meta);
                        NBTItem nbtitem = new NBTItem(item);
                        nbtitem.setBoolean("clockIn",true);
                        nbtitem.setBoolean("boss",false);
                        nbtitem.setString("job",args[1]);

                        nbtitem.applyNBT(item);

                        player.getInventory().addItem(nbtitem.getItem());
                        player.sendMessage("ClockIn: Has generado un boton de Trabajador del trabajo: "+args[1]);
                        break;
                    case "boss":
                        ItemStack item2 = new ItemStack(Material.POLISHED_BLACKSTONE_BUTTON);
                        ItemMeta meta2 = item2.getItemMeta();
                        meta2.setDisplayName("[ClockIn] "+args[1]+" Boss");
                        List<String> lore2 = new ArrayList<>();
                        lore2.add("Si no eres administrador");
                        lore2.add("Contacta con un admin para devovlerlo");
                        meta2.setLore(lore2);

                        item2.setItemMeta(meta2);
                        NBTItem nbtitem2 = new NBTItem(item2);
                        nbtitem2.setBoolean("clockIn",true);
                        nbtitem2.setBoolean("boss",true);
                        nbtitem2.setString("job",args[1]);

                        nbtitem2.applyNBT(item2);

                        player.getInventory().addItem(nbtitem2.getItem());
                        player.sendMessage("ClockIn: Has generado un boton de Jefe del trabajo: "+args[1]);

                        break;
                }

            }


        }



        return true;
    }

}
