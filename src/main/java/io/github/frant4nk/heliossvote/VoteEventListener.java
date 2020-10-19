package io.github.frant4nk.heliossvote;

import com.vexsoftware.votifier.model.Vote;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import com.vexsoftware.votifier.model.VotifierEvent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
            composeRequest(id, replacedMsgCommand);
            composeRequest(id, replacedCommandGive);
        }
    }

    private void composeRequest(String uuid, String command)
    {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://panel.helioss.co/api/client/servers/" + uuid + "/command");

        String json = "{\"command\": \"" + command + "\"}";
        StringEntity entity = null;
        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Connection", "close");
        httpPost.setHeader("Authorization", "Bearer ");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(response);
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
