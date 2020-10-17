package io.github.frant4nk.heliossvote;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VoteEventListener implements Listener
{
    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent event)
    {
        Vote vote = event.getVote();
        System.out.println("Received: " + vote);
    }
}
