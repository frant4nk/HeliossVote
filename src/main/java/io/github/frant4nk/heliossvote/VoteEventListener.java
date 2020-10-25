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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class VoteEventListener implements Listener
{
    Heliossvote plugin;
    Connection con;

    String host = "54.36.165.164";
    String port = "3306";
    String database = "s174_votes";
    String user = "u174_TIa0d5swlX";
    String password = "d=A3iVcZLvnd6=D8bT0hdTaG";

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
        List<String> commands = plugin.getCustomConfig().getStringList("commands");

        for(String id : ids)
        {
            for(String command : commands)
            {
                String replacedCommand = command.replace("%player", vote.getUsername()).replace("%web", vote.getServiceName());
                composeRequest(id, replacedCommand);
            }
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            PreparedStatement ps = con.prepareStatement("INSERT INTO votes (username, dayvotes, totalvotes) VALUES (\"" + vote.getUsername() + "\", 1, 1) ON DUPLICATE KEY UPDATE dayvotes = dayvotes + 1, totalvotes = totalvotes + 1;");
            ps.executeUpdate();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
        httpPost.setHeader("Authorization", "Bearer " + plugin.getCustomConfig().getString("token"));
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
