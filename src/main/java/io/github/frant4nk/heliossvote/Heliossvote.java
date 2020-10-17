package io.github.frant4nk.heliossvote;

import org.bukkit.plugin.java.JavaPlugin;

public final class Heliossvote extends JavaPlugin {

    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new VoteEventListener(), this);
    }

    @Override
    public void onDisable()
    {

    }
}
