package io.github.frant4nk.heliossvote;

import com.stanjg.ptero4j.PteroUserAPI;
import com.stanjg.ptero4j.entities.panel.admin.Server;
import com.stanjg.ptero4j.entities.panel.user.UserServer;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import com.vexsoftware.votifier.model.VotifierEvent;

public class VoteEventListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(@org.jetbrains.annotations.NotNull VotifierEvent event)
    {
        Vote vote = event.getVote();
        System.out.println("Getting vote from: " + vote.getUsername());
        voteTrigger(vote);
    }

    private void voteTrigger(@org.jetbrains.annotations.NotNull Vote vote)
    {
        PteroUserAPI api = new PteroUserAPI("https://panel.helioss.co/", "tjUJDkfA0pSZ4U6F1gworR89Wn62aIprBWpi2bLi630MKWkG");

        UserServer server = api.getServersController().getServer("a9013ecb-244f-4933-8197-99251b79728e");
        server.sendCommand("say " + vote.getUsername() + " voted!!");
    }
}
