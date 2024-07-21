package org.aifusp.listeners;

import org.aifusp.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class PlayerLeave implements Listener {
    private final Main main;
    public PlayerLeave(Main plugin){this.main = plugin;}


    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
     String name = e.getPlayer().getName();
     File file = new File(main.getDataFolder(),"temp");
     File[] files = file.listFiles();
        if (files != null) {
            for (File sfile : files) {
                String[] split = sfile.getName().split("_");
                if (split[0].equals(name)){
                 sfile.delete();
                }
            }
        }
    }
}
