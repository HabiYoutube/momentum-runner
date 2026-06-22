package com.habioutube.momentumrunner;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.habioutube.momentumrunner.module.MomentumModule;

public class MomentumRunner implements ClientModInitializer {
    public static final String MOD_ID = "momentumrunner";
    public static KeyBinding momentumToggleKey;
    public static final MomentumModule momentumModule = new MomentumModule();

    @Override
    public void onInitializeClient() {
        momentumToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.momentumrunner.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                "category.momentumrunner"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            while (momentumToggleKey.wasPressed()) {
                momentumModule.toggle();
            }

            momentumModule.tick(client.player);
        });
    }
}
