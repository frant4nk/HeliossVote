package io.github.frant4nk.heliossvote;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.entities.panel.user.UserServer;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import com.vexsoftware.votifier.model.VotifierEvent;

import java.util.List;

public class VoteEventListener implements Listener
{
    Heliossvote plugin;

    public VoteEventListener(Heliossvote instance)
    {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(@org.jetbrains.annotations.NotNull VotifierEvent event)
    {
        Vote vote = event.getVote();
        System.out.println("Getting vote from: " + vote.getUsername());
        voteTrigger(vote);
    }

    private void voteTrigger(@org.jetbrains.annotations.NotNull Vote vote)
    {
        List<String> ids = plugin.getCustomConfig().getStringList("ids");
        String command = plugin.getCustomConfig().getString("command");
        String replacedCommandGive = command.replace("%player", vote.getUsername());

        String msgCommand = plugin.getCustomConfig().getString("msgcommand");
        String replacedMsgCommand = msgCommand.replace("%player", vote.getUsername());

        for(String id : ids)
        {
            PteroUserAPI api = new PteroUserAPI("https://panel.helioss.co", "tjUJDkfA0pSZ4U6F1gworR89Wn62aIprBWpi2bLi630MKWkG");

            UserServer server = api.getServersController().getServer(id);
            server.sendCommand(replacedMsgCommand);
            server.sendCommand(replacedCommandGive);
        }

        //UserServer server = api.getServersController().getServer("a9013ecb-244f-4933-8197-99251b79728e");
        //server.sendCommand("say " + vote.getUsername() + " voted!!");
    }
}
