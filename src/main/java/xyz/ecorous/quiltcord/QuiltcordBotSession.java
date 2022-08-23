package xyz.ecorous.quiltcord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.quiltmc.config.api.Config;

import javax.security.auth.login.LoginException;
import java.util.Objects;
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

		System.out.println("Token: \"" + QuiltcordConfigManager.TOKEN.value() + "\"");
		JDA jda = JDABuilder.createDefault(QuiltcordConfigManager.TOKEN.value()).build();
		jda.addEventListener(this);
		QuiltcordBotSession.instance = this;
		this.jda = jda;

	}

	public void handleMinecraftMessage(String mcmessage, ServerPlayerEntity player) {
		GuildChannel channel = jda.getGuildChannelById(QuiltcordConfigManager.CHANNELID.value());
		if (channel != null) {
			if (channel.getType() == ChannelType.TEXT) {
				MessageChannel channel1 = (MessageChannel) channel;
 				channel1.sendMessage("<" + player.getName() + "| " + player.getEntityName() + ">" + mcmessage);

			}
		} else {
			Quiltcord.LOGGER.error("Invalid channel ID! Please check the config or make sure the bot has access to this channel!");
		}
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (!event.getChannel().getId().equals(QuiltcordConfigManager.CHANNELID.value())) {
			System.out.println("Returning... " + event.getChannel().getName() + ": " + event.getChannel().getId() + "/!O" + QuiltcordConfigManager.CHANNELID.value() );
			return;
		}
		int color = 0xfffff;
		Text message = Text.of(event.getMessage().getContentStripped());

		if (event.getGuild().getRoleByBot(event.getAuthor()) != null) {
			color = Objects.requireNonNull(event.getGuild().getRoleByBot(event.getAuthor())).getColorRaw();
		}
		Style style = Style.EMPTY.withColor(color);
		MutableText username = (MutableText) Text.of(event.getAuthor().getName());
		username.fillStyle(style);

		Text out2 = Text.empty().append("[Discord] ").append("<").append(username).append("> ").append(message);
		this.server.sendSystemMessage(out2);

	}
}
