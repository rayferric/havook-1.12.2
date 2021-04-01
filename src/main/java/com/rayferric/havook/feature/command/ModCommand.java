package com.rayferric.havook.feature.command;

import com.rayferric.havook.feature.Command;
import com.rayferric.havook.feature.Mod;
import com.rayferric.havook.feature.mod.ModAttribute;
import com.rayferric.havook.feature.mod.ModAttributeBoolean;
import com.rayferric.havook.feature.mod.ModAttributeDouble;
import com.rayferric.havook.feature.mod.ModAttributeString;
import com.rayferric.havook.feature.mod.ModCategoryEnum;
import com.rayferric.havook.manager.ModManager;
import com.rayferric.havook.util.ChatUtil;

public class ModCommand extends Command {
	public ModCommand() {
		super("mod", ".mod <list|toggle <id>|t <id>|reset <id>|attrib <id> <list|set <attribute> <value>>>",
				"Allows you to toggle havook mods and modify their attributes.");
	}

	@Override
	public void execute(String[] args) {
		if (args.length < 1) {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
		if (args[0].equals("list")) {
			if (args.length > 1) {
				ChatUtil.warning("Too many arguments.");
			}
			ChatUtil.info("\2473\247l--------------------------------");
			ChatUtil.info("\247lAll available mods:");
			ChatUtil.info("");
			ChatUtil.info("COMBAT:");
			for (Mod mod : ModManager.MODS) {
				if (mod.category == ModCategoryEnum.COMBAT) {
					String active = mod.isEnabled() ? "\247a" : "\247c";
					ChatUtil.info(active + "\247l" + mod.name + " \247e[\2477" + mod.id + "\247e]");
				}
			}
			ChatUtil.info("");
			ChatUtil.info("MISC:");
			for (Mod mod : ModManager.MODS) {
				if (mod.category == ModCategoryEnum.MISC) {
					String active = mod.isEnabled() ? "\247a" : "\247c";
					ChatUtil.info(active + "\247l" + mod.name + " \247e[\2477" + mod.id + "\247e]");
				}
			}
			ChatUtil.info("");
			ChatUtil.info("MOVEMENT:");
			for (Mod mod : ModManager.MODS) {
				if (mod.category == ModCategoryEnum.MOVEMENT) {
					String active = mod.isEnabled() ? "\247a" : "\247c";
					ChatUtil.info(active + "\247l" + mod.name + " \247e[\2477" + mod.id + "\247e]");
				}
			}
			ChatUtil.info("");
			ChatUtil.info("RENDER:");
			for (Mod mod : ModManager.MODS) {
				if (mod.category == ModCategoryEnum.RENDER) {
					String active = mod.isEnabled() ? "\247a" : "\247c";
					ChatUtil.info(active + "\247l" + mod.name + " \247e[\2477" + mod.id + "\247e]");
				}
			}
			ChatUtil.info("\2473\247l--------------------------------");
			return;
		} else if (args[0].equals("toggle")) {
			if (args.length < 2) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			Mod targetMod = ModManager.getModById(args[1]);
			if (targetMod == null) {
				ChatUtil.error("There's no such mod with id \2477" + args[1] + "\247c.");
				return;
			}
			targetMod.setEnabled(!targetMod.isEnabled());
			ChatUtil.info("Mod " + (targetMod.isEnabled() ? "\247a" : "\247c") + "\247l" + targetMod.name + " \247ehas been toggled " + (targetMod.isEnabled() ? "\247a\247lON" : "\247c\247lOFF") + "\247e.");
			return;
		} else if (args[0].equals("t")) {
			if (args.length < 2) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			Mod targetMod = ModManager.getModById(args[1]);
			if (targetMod == null) {
				ChatUtil.error("There's no such mod with id \2477" + args[1] + "\247c.");
				return;
			}
			targetMod.setEnabled(!targetMod.isEnabled());
			return;
		} else if (args[0].equals("reset")) {
			if (args.length < 2) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			if (args.length > 2) {
				ChatUtil.warning("Too many arguments.");
			}
			Mod targetMod = ModManager.getModById(args[1]);
			if (targetMod == null) {
				ChatUtil.error("There's no such mod with id \2477" + args[1] + "\247c.");
				return;
			}
			for (ModAttribute attribute : targetMod.ATTRIBUTES) {

				if (attribute instanceof ModAttributeBoolean)
					((ModAttributeBoolean) attribute).value = ((ModAttributeBoolean) attribute).getNativeValue();
				else if (attribute instanceof ModAttributeDouble)
					((ModAttributeDouble) attribute).value = ((ModAttributeDouble) attribute).getNativeValue();
				else if (attribute instanceof ModAttributeString)
					((ModAttributeString) attribute).value = ((ModAttributeString) attribute).getNativeValue();
			}
			ChatUtil.info("Mod " + (targetMod.isEnabled() ? "\247a" : "\247c") + "\247l" + targetMod.name + " \247ehas been reset.");
			ModManager.saveMods();
			return;
		} else if (args[0].equals("attrib")) {
			if (args.length < 3) {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			Mod targetMod = ModManager.getModById(args[1]);
			if (targetMod == null) {
				ChatUtil.error("There's no such mod with id \2477" + args[1] + "\247c.");
				return;
			}
			if(args[2].equals("list")) {
				ChatUtil.info((targetMod.isEnabled() ? "\247a" : "\247c") + "\247l" + targetMod.name + " \2473\247l| \247e" + targetMod.description);
				ChatUtil.info("\2473\247l--------------------------------");
				ChatUtil.info("\247lAttributes:");
				ChatUtil.info("");
				if (targetMod.ATTRIBUTES.size() < 1) {
					ChatUtil.info("\247c\247l<NONE>");
				} else
					for (ModAttribute attribute : targetMod.ATTRIBUTES) {
						if (attribute instanceof ModAttributeBoolean) {
							String value = "\247d" + ((ModAttributeBoolean) attribute).value;
							ChatUtil.info("\247c\247l" + attribute.name + " \247e\247l: " + value
									+ " \247e[\247d\247lBOOLEAN\247e]");
						} else if (attribute instanceof ModAttributeDouble) {
							String value = "\2479" + ((ModAttributeDouble) attribute).value;
							ChatUtil.info("\247c\247l" + attribute.name + " \247e\247l: " + value
									+ " \247e[\2479\247lDOUBLE\247e]");
						} else if (attribute instanceof ModAttributeString) {
							String value = "\247a" + ((ModAttributeString) attribute).value;
							ChatUtil.info("\247c\247l" + attribute.name + " \247e\247l: " + value
									+ " \247e[\247a\247lSTRING\247e]");
						}
					}
				ChatUtil.info("\2473\247l--------------------------------");
			} else if(args[2].equals("set")) {
				if (args.length < 5) {
					ChatUtil.error("Invalid syntax.");
					ChatUtil.syntax(syntax);
					return;
				}
				ModAttribute targetAttribute = targetMod.getAttribByName(args[3]);
				if (targetAttribute == null) {
					String active = targetMod.isEnabled() ? "\247a" : "\247c";
					ChatUtil.error("Mod " + active + "\247l" + targetMod.name
							+ "\247c has no such attribute with name \2477" + args[3] + "\247c.");
					return;
				}
				if (targetAttribute instanceof ModAttributeBoolean) {
					if (args[4].equalsIgnoreCase("true") || args[4].equalsIgnoreCase("false")) {
						if (args.length > 5) {
							ChatUtil.warning("Too many arguments.");
						}
						Boolean value = Boolean.parseBoolean(args[4]);
						((ModAttributeBoolean) targetAttribute).value = value;
						ChatUtil.info("Attribute \247c\247l" + targetAttribute.name + "\247e has been set to \247d"
								+ value + " \247e[\247d\247lBOOLEAN\247e].");
						ModManager.saveMods();
						return;
					} else {
						ChatUtil.error("You must choose between \2477true \247cand \2477false\247c.");
						return;
					}
				} else if (targetAttribute instanceof ModAttributeDouble) {
					if (args.length > 5) {
						ChatUtil.warning("Too many arguments.");
					}
					double number;
					try {
						number = Double.parseDouble(args[4]);
					} catch (NullPointerException | NumberFormatException e) {
						ChatUtil.error("\2477" + args[4] + "\247c is not a valid number.");
						return;
					}
					((ModAttributeDouble) targetAttribute).value = number;
					ChatUtil.info("Attribute \247c\247l" + targetAttribute.name + "\247e has been set to \2479" + number
							+ " \247e[\2479\247lDOUBLE\247e].");
					ModManager.saveMods();
					return;
				} else if (targetAttribute instanceof ModAttributeString) {
					String val = args[4];
					for (int i = 5; i < args.length; i++)val += " " + args[i];
					((ModAttributeString) targetAttribute).value = val;
					ChatUtil.info("Attribute \247c\247l" + targetAttribute.name + "\247e has been set to \247a"
							+ args[4] + " \247e[\247a\247lSTRING\247e].");
					ModManager.saveMods();
					return;
				}
			} else {
				ChatUtil.error("Invalid syntax.");
				ChatUtil.syntax(syntax);
				return;
			}
			return;
		} else {
			ChatUtil.error("Invalid syntax.");
			ChatUtil.syntax(syntax);
			return;
		}
	}
}
