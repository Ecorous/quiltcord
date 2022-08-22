package xyz.ecorous.quiltcord;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import okhttp3.internal.Util;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.config.QuiltConfig;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.lifecycle.api.event.ServerLifecycleEvents;
import org.quiltmc.qsl.lifecycle.api.event.ServerTickEvents;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldLoadEvents;
import org.quiltmc.qsl.lifecycle.api.event.ServerWorldTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Quiltcord implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod name as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "quiltcord";
	public static final Logger LOGGER = LoggerFactory.getLogger("Quiltcord");
	public static final QuiltcordConfig CONFIG = QuiltConfig.create(MOD_ID, "config", QuiltcordConfig.class);

	public static QuiltcordBotSession session;

	@Override
	public void onInitialize(ModContainer mod) {
		//LOGGER.info("Hello Quilt world from {}!", mod.metadata().name());

		ServerLifecycleEvents.READY.register(server -> {
			try {
				session = new QuiltcordBotSession(server);
				session.init();
			} catch (LoginException e) {
				LOGGER.error("Failed to initialize Quiltcord at stage LOGIN_SESSION", e);
				LOGGER.error("Try checking your token in the config file, otherwise check your network connection");
			}
		});
		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
			session.handleMinecraftMessage(message.method_44862().plain());
		});


	}
}
