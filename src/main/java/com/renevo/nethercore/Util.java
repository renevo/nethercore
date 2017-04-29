package com.renevo.nethercore;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public final class Util {
    private Util() {}

    public static final String MODID = "nethercore";

    public static String resource(String res) {
        return String.format("%s:%s", MODID, res);
    }

    public static ResourceLocation getResource(String res) {
        return new ResourceLocation(MODID, res);
    }

    public static String prefix(String name) {
        return String.format("%s.%s", MODID, name.toLowerCase(Locale.US));
    }

    public static String translateRecursive(String key, Object... params) {
        return I18n.format(key, params);
    }
}
