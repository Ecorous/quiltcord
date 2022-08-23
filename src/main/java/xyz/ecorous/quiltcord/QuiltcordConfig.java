package xyz.ecorous.quiltcord;

import org.quiltmc.config.api.WrappedConfig;
import org.quiltmc.config.api.annotations.Comment;

public final class QuiltcordConfig extends WrappedConfig {

	@Comment("Basic configuration for the bot.")
	public final BasicConfigs basic = new BasicConfigs();

	@Comment("Webhook mode configuration for the bot.")
	public final WebhookModeConfigs webhook = new WebhookModeConfigs();

	@Comment("Message mode configuration for the bot.")
	public final MessageModeConfigs message = new MessageModeConfigs();
	public static class BasicConfigs implements Section {
		@Comment("Bot token. Not application id or client id.")
		public final String token = "";

		@Comment("Bot mode. Valid values are \"message\" and \"webhook\".")
		public final String mode = "message";

		@Comment("Chat channel id.")
		public final String channelID = "";

		@Comment("Announce deaths?")
		public final boolean announceDeaths = true;

		@Comment("Announce advancements?")
		public final boolean announceAdvancements = true;

		@Comment("Announce player join/leave?")
		public final boolean announcePlayerJoinLeave = true;
	}

	public static class WebhookModeConfigs implements Section {
		@Comment("Webhook url.")
		public final String url = "";

		@Comment("Image url for server messages.")
		public final String serverImageUrl = "";

		@Comment("Name for server messages.")
		public final String serverName = "Server";

		@Comment("API to use to get minecraft head. Placeholders are {username} and {uuid}.")
		public final String minecraftHeadApi = " https://crafatar.com/renders/head/{uuid}";

	}

	public static class MessageModeConfigs implements Section {
		@Comment("Format for a message. Placeholders are {username} {displayname} {message}.")
		public final String messageFormat = "<{displayname}> {message}";


	}


}
