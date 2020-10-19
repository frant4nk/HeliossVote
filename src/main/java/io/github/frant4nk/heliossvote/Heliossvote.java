package io.github.frant4nk.heliossvote;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Heliossvote extends JavaPlugin
{

    private File customConfigFile;
    private FileConfiguration customConfig;

    @Override
    public void onEnable()
    {
        getLogger().info("HeliossVote has been enabled");
        createCustomConfig();
        getServer().getPluginManager().registerEvents(new VoteEventListener(this), this);
    }

    @Override
    public void onDisable()
    {

    }

    public FileConfiguration getCustomConfig()
    {
        return this.customConfig;
    }

    private void createCustomConfig()
    {

        customConfigFile = new File(getDataFolder(), "/voteConfigFile.yml");
        if(!customConfigFile.exists())
        {
            customConfigFile.getParentFile().mkdirs();
            copy(getResource("voteConfigFile.yml"), customConfigFile);
        }
        customConfig = new YamlConfiguration();
        try
        {
            customConfig.load(customConfigFile);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
