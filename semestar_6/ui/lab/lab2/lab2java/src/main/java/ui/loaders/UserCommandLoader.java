package ui.loaders;

import ui.UserCommandInfo;
import ui.commands.UserCommands;

public class UserCommandLoader extends AbstractLoader<UserCommandInfo> {

	@Override
	protected UserCommandInfo load(String str) {
		return UserCommands.getFromString(str);
	}

}
