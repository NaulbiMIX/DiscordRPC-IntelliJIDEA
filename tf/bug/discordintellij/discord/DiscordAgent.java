package tf.bug.discordintellij.discord;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.intellij.ide.util.PropertiesComponent;
import tf.bug.discordintellij.Presence;
import tf.bug.discordintellij.PresenceAgent;

public class DiscordAgent extends PresenceAgent {
    public static final String DISCORD_PRESENCE_ENABLED = "presence.discord.enabled";
    private static final String SMALL_IMAGE_KEY = "";
    private static final String SMALL_IMAGE_TEXT = "";

    static {
        PresenceAgent.addAgent(DiscordAgent.class);
    }

    public DiscordAgent() {
        super(new DiscordTogglePresence());
    }

    public static boolean isEnabled() {
        return PropertiesComponent.getInstance().getBoolean("presence.discord.enabled", true);
    }

    public static void setEnabled(boolean enabled) {
        setEnabled(enabled, DiscordAgent.class);
    }

    public void initializeAgent() {
        DiscordRPC.INSTANCE.Discord_Initialize(DiscordAPIKeys.DISCORD_CLIENT_ID, new DiscordEventHandlers(), true, "");
    }

    public void showPresence(Presence presence) {
        DiscordRPC.INSTANCE.Discord_UpdatePresence(getPresence(presence));
    }

    public void hidePresence() {
        DiscordRPC.INSTANCE.Discord_ClearPresence();
    }

    public void stopAgent() {
        DiscordRPC.INSTANCE.Discord_Shutdown();
    }

    public String getName() {
        return "Discord";
    }

    private DiscordRichPresence getPresence(Presence presence) {
        DiscordRichPresence drp = new DiscordRichPresence();
        if (presence.hasFile()) {
         //   drp.smallImageKey = presence.getFileTypeKey();
        //    drp.smallImageText = presence.getFileTypeString();
            drp.largeImageKey = presence.getApplicationKey();
            drp.largeImageText = presence.getApplicationText();
            drp.details = presence.getProjectName();
            drp.state = presence.getFile();
            drp.startTimestamp = presence.getStartTimeStamp();
        } else {
         //   drp.smallImageKey = "tsun";
         //   drp.smallImageText = "TsundereBug's plugin: https://goo.gl/81tZHT";
            // presence.getVersionName() - Intellij IDEA
            drp.largeImageKey = presence.getApplicationKey();
            drp.largeImageText = presence.getVersionName();
  //          drp.details = presence.getApiVersion();
            drp.startTimestamp = presence.getStartTimeStamp();
            if(presence.hasCurrentProject()) {
                drp.details = presence.getProjectName();
            }else{
                drp.details = "No working";
               // drp.details = String.valueOf(presence.getApplicationKey());
           //     drp.details = String.valueOf(StandardCharsets.UTF_8.encode("Ничего не делает")); здесь будет инвалид символы, поэтому фиксить лень)
            }
/*
            if (presence.hasCurrentProject()) {
                drp.state = presence.getProjectName();
            } else {
                drp.state = String.format("In %s %s", new Object[] { presence.getVersionName(), presence.getFullVersion() });
            } */
        }
        return drp;
    }
}
