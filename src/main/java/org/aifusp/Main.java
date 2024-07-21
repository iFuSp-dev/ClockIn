package org.aifusp;

import me.aifusp.clockin.gui.menu.listeners.ClickGui;
import org.aifusp.commands.MainCommand;
import org.aifusp.listeners.PlayerInteractButton;
import org.aifusp.listeners.PlayerLeave;
import org.aifusp.listeners.PlayerPlaceButton;
import org.aifusp.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    public static  String prefix = "&#3161f2&l&nClock&l&#fe2a2aIn";
    public  static Main getInstance() {
        return getPlugin(Main.class);
    }

    @Override
    public void onEnable(){
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(" "));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(prefix + " Ha sido inicializado"));
        Bukkit.getConsoleSender().sendMessage(MessageUtils.getColoredMessage(" "));


        loadFolder();

        registerGUIListeners();
        registerMainCommand();
        registerPlayerLeave();
        registerPlayerPlaceButton();
        registerPlayerInteractButton();
    }






    private void loadFolder(){
        File dataFolder = getDataFolder(); // Obtiene la carpeta de datos de tu plugin

        if (!dataFolder.exists()) {
            dataFolder.mkdirs(); // Crear la carpeta y cualquier directorio faltante
            getLogger().info("Carpeta del plugin creada correctamente.");
        } else {
            getLogger().info("Carpeta del plugin ya existe.");
        }

        // Puedes crear subdirectorios si los necesitas de la misma manera
        File temp = new File(dataFolder, "temp");
        if (!temp.exists()) {
            temp.mkdirs();
            getLogger().info("PDA creada correctamente.");
        }
        File save = new File(dataFolder, "saves");
        if (!save.exists()) {
            save.mkdirs();
            getLogger().info("PDA creada correctamente.");
        }
    }

    public void registerMainCommand(){this.getCommand("ClockIn").setExecutor(new MainCommand(this));}


    public void registerGUIListeners() {
        getServer().getPluginManager().registerEvents(new ClickGui(this), this);
    }
    public void registerPlayerLeave() {
        getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);
    }
    public void registerPlayerInteractButton() {
        getServer().getPluginManager().registerEvents(new PlayerInteractButton(this), this);
    }
    public void registerPlayerPlaceButton() {
        getServer().getPluginManager().registerEvents(new PlayerPlaceButton(this), this);
    }



}
