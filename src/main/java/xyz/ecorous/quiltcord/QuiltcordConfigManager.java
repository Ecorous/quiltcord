package xyz.ecorous.quiltcord;

import org.quiltmc.config.api.values.TrackedValue;

import java.util.List;

@SuppressWarnings("unchecked")
public class QuiltcordConfigManager {

	public static final TrackedValue<String> TOKEN = (TrackedValue<String>) Quiltcord.CONFIG.getValue(List.of("basic", "token"));
	public static final TrackedValue<String> CHANNELID = (TrackedValue<String>) Quiltcord.CONFIG.getValue(List.of("basic", "channelID"));

}
