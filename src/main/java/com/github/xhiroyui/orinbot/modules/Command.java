package com.github.xhiroyui.orinbot.modules;

import com.github.xhiroyui.orinbot.modules.command.RequiredPermissions;
import com.github.xhiroyui.orinbot.util.BotUtil;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Permission;
import discord4j.core.object.util.PermissionSet;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredPermissions
public abstract class Command {
	private String commandName;
	private String commandDescription;
	private int requiredParameters;
	private List<String> parameterDescription;
	private List<String> commandAlias;

	public Command(String description, int requiredParameters, List<String> parameterDescription, List<String> commandAlias) {
		this.commandName = getClass().getSimpleName();
		this.commandDescription = description;
		this.requiredParameters = requiredParameters;
		this.parameterDescription = parameterDescription;
		this.commandAlias = commandAlias;
	}

	protected Mono<Void> executeCommand(MessageCreateEvent event, String args) {
		return Mono.justOrEmpty(this.getClass().getAnnotation(RequiredPermissions.class).value())
				.zipWith(event.getMessage().getAuthorAsMember()
						.flatMap(Member::getBasePermissions))
				.flatMap(this::validatePermissions)
				.flatMap(ignored -> processParameters(args))
				.flatMap(processedArgs -> runCommand(event, processedArgs))
				.onErrorResume(error -> BotUtil.COMMAND_ERROR_HANDLER.handle(this, error, event)
						.flatMap(errorMessage -> event.getMessage().getChannel()
								.flatMap(channel -> channel.createMessage(spec -> spec.setContent(errorMessage))))
						.then(Mono.empty()))
				.then();
	}

	protected Mono<Boolean> validatePermissions(Tuple2<Permission[], PermissionSet> permCheck) {
		for (Permission p : permCheck.getT1())
			if (!permCheck.getT2().contains(p))
				return Mono.error(new MissingPermissionsException());
		return Mono.just(true);
	}

	public void getCommandInfo(EmbedCreateSpec spec) {
		spec.setTitle(this.commandName)
				.setDescription(this.commandDescription)
				.addField("Required Parameters", formatParameterDescription(), true)
				.addField("Alias", formatCommandAlias(), true)
				.setColor(new Color(ThreadLocalRandom.current().nextInt(0, 255 + 1),
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
				sb.append(i + 1).append(" : ").append(parameterDescription.get(i));
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

	private Mono<String[]> processParameters(String arg) { // Potentially bad code as pointed out by alphahelix
		return Mono.just(arg)
				.map(args -> args.equalsIgnoreCase("") ? BotUtil.EMPTY_ARRAY : args.split(" ", requiredParameters + 1))
				.filterWhen(args -> verifyParameterCount(args, requiredParameters))
				.switchIfEmpty(Mono.error(new CommandParameterCountException()))
				.filterWhen(this::validateParameters)
				.switchIfEmpty(Mono.error(new CommandParameterValidationException()))
				;
	}

	private Mono<Boolean> verifyParameterCount(String[] args, int requiredParameters) {
		if (args.length >= requiredParameters)
			return Mono.just(true);
		return Mono.empty();
	}

	private Mono<Boolean> validateParameters(String[] args) {
		return Mono.just(true);
	}

	public List<String> getAlias() {
		return this.commandAlias;
	}

	protected abstract Mono<Void> runCommand(MessageCreateEvent event, String[] args);

}
