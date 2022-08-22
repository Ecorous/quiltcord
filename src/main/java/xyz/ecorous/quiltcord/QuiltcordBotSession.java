package xyz.ecorous.quiltcord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.quiltmc.config.api.Config;

import javax.security.auth.login.LoginException;
import java.util.concurrent.atomic.AtomicReference;

public class QuiltcordBotSession extends ListenerAdapter {
	private static QuiltcordBotSession instance;
	private MinecraftServer server;
	private JDA jda;
	private QuiltcordConfig config = Quiltcord.CONFIG;
	public QuiltcordBotSession(MinecraftServer server) {
		this.server = server;
	}
	public void init() throws LoginException {
		String token = config.basic.token;
		System.out.println("Token: \"" + token + "\"");
		JDA jda = JDABuilder.createDefault(token).build();
		jda.addEventListener(this);
		QuiltcordBotSession.instance = this;
		this.jda = jda;

	}

	public void handleMinecraftMessage(String mcmessage) {
		GuildChannel channel = jda.getGuildChannelById(config.basic.channelID);
		if (channel != null) {
			if (channel.getType() == ChannelType.TEXT) {
				MessageChannel channel1 = (MessageChannel) channel;
				channel1.sendMessage(mcmessage);

			}
		} else {
			Quiltcord.LOGGER.error("Invalid channel ID! Please check the config or make sure the bot has access to this channel!");
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getChannel().getId().equals(config.basic.channelID)) {
			return;
		}
		Text message = Text.of(event.getMessage().getContentDisplay());
		int color = event.getGuild().getRoleByBot(event.getAuthor().getId()).getColorRaw();
		Style style = Style.EMPTY.withColor(color);
		MutableText username = Text.literal(event.getAuthor().getName()).fillStyle(style);
		Text out = Text.literal("[Discord] " + "<" + username + "> " + message);
		this.server.sendSystemMessage(out);

	}
}
