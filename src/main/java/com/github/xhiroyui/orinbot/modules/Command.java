package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.modules.command.RequiredPermissions;
import com.github.xhiroyui.orinbot.util.BotUtil;
import com.github.xhiroyui.orinbot.util.CommandUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import discord4j.rest.util.Permission;
import discord4j.rest.util.PermissionSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@RequiredPermissions
public abstract class Command {

	@Autowired
	protected CommandUtil commandUtil;

	final private String commandName = getClass().getSimpleName();
	final private String commandDescription;
	final private int requiredParameters;
	final private LinkedHashMap<String, String> parameterDescription;
	final private List<String> commandAlias;

	protected Mono<Void> executeCommand(MessageCreateEvent event, String args) {
		log.debug("Executing command [" + this.commandName + "] with the following arguments : " + args);
		return Mono.justOrEmpty(this.getClass().getAnnotation(RequiredPermissions.class).value())
				.flatMap(requiredPermissions -> event.getMessage()
						.getAuthorAsMember()
						.flatMap(Member::getBasePermissions)
						.filterWhen(permissionSet ->
								validatePermissions(requiredPermissions, permissionSet, event.getMessage()
										.getAuthor()
										.orElseThrow()
								)
						))
				.flatMap(ignored -> Mono.just(args.equalsIgnoreCase("") ?
						BotUtil.EMPTY_ARRAY :
						args.split(" ", requiredParameters + 1)))
				.filterWhen(commandArgs -> verifyParameterCount(event, commandArgs, this.requiredParameters))
				.filterWhen(commandArgs -> validateParameters(event, commandArgs))
				.flatMap(commandArgs -> runCommand(event, commandArgs))
				.onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error, event)
						.flatMap(errorMessage -> event.getMessage().getChannel()
								.flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
						.then(Mono.empty()))
				.then();
	}

	protected Mono<Boolean> validatePermissions(Permission[] requiredPermissions, PermissionSet userPermissions, User user) {
		log.debug(" == Permission Checking for Command [{}]. == ", this.commandName);
		log.debug("Checking user against whitelist : " + commandUtil.ownerBypassCheck(user.getId()));
		if (commandUtil.ownerBypassCheck(user.getId())) {
			log.debug("User whitelisted. Bypassing permission requirements for command [{}]", this.commandName);
			return Mono.just(true);
		}
		log.debug("User is not whitelisted. Checking permissions of user.");
		log.debug("Required permissions are " + Arrays.toString(requiredPermissions) + ".");
		log.debug("Given permissions are " + userPermissions.toString());

		for (Permission p : requiredPermissions)
			if (!userPermissions.contains(p))
				return Mono.error(new MissingPermissionsException(
						user,
						p,
						this.commandName)
				);
		return Mono.just(true);
	}

	private Mono<Boolean> verifyParameterCount(MessageCreateEvent mce, String[] args, int requiredParameters) {
		if (args.length >= requiredParameters)
			return Mono.just(true);
		return Mono.error(new CommandParameterCountException(
				commandUtil.getGuildPrefix(mce.getGuildId().orElseThrow()),
				requiredParameters,
				this.commandName)
		);
	}

	/**
	 * Each {@link Command} that has arguments should override this method individually to validate the required parameters
	 * @param args Given arguments in the command
	 * @return Always true unless {@link Override} is implemented in {@link Command}
	 */
	protected Mono<Boolean> validateParameters(MessageCreateEvent mce, String[] args) {
		return Mono.just(true);
	}

	public void getCommandInfo(EmbedCreateSpec spec) {
		spec.setTitle(this.commandName)
				.setDescription(this.commandDescription)
				.addField("Required Parameters", formatParameterDescription(), true)
				.addField("Alias", formatCommandAlias(), true)
				.setColor(Color.of(
						ThreadLocalRandom.current().nextInt(0, 255 + 1),
						ThreadLocalRandom.current().nextInt(0, 255 + 1),
						ThreadLocalRandom.current().nextInt(0, 255 + 1)))
				.setFooter("Nya :3c - OrinBot", null)
		;
	}

	private String formatParameterDescription() {
		StringBuilder sb = new StringBuilder();
		if (parameterDescription.isEmpty())
			sb.append("None");
		else
			for (int i = 0; i < parameterDescription.size(); i++) {
				sb.append(i + 1).append(" : ").append(parameterDescription.get(i)); // TODO issue here change to reading from map
			}

		return sb.toString();
	}

	private String formatCommandAlias() {
		StringBuilder sb = new StringBuilder();
		for (String each : commandAlias) {
			if (sb.length() != 0)
				sb.append("\n");
			sb.append(each);
		}
		return sb.toString();
	}

	public List<String> getAlias() {
		return this.commandAlias;
	}

	public int getRequiredParameters() {
		return this.requiredParameters;
	}

	protected abstract Mono<Void> runCommand(MessageCreateEvent event, String[] args);

}
