package com.sorgrum.toamissingpickaxeindicator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ToaMissingPickaxeIndicatorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ToaMissingPickaxeIndicatorPlugin.class);
		RuneLite.main(args);
	}
}