package de.rbuurman.jisl.program.evaluation;

import java.util.HashMap;
import java.util.Map;

import de.rbuurman.jisl.program.Identifier;
import de.rbuurman.jisl.program.Value;

/**
 * Environment
 */
public class Environment {

	private Map<Identifier, Value> definitions = new HashMap<>();

	protected Map<Identifier, Value> getDefinitions() {
		return this.definitions;
	}

	public void addDefinition(Identifier identifier, Value value) throws EvaluationException {
		if (this.definitions.containsKey(identifier)) {
			throw new EvaluationException("Definition for " + identifier + " already exists");
		}
		this.definitions.put(identifier, value);
	}

	public Value getValue(Identifier identifier) throws EvaluationException {
		final var value = this.definitions.get(identifier);

		if (value == null) {
			throw new EvaluationException("Definition for " + identifier + " does not exist");
		}

		return value;
	}

	public static Environment merge(Environment base, Environment local) throws EvaluationException {
		var env = new Environment();

		for (Map.Entry<Identifier, Value> entry : base.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<Identifier, Value> entry : local.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		return env;
	}

}
