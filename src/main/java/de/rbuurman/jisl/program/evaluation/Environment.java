package de.rbuurman.jisl.program.evaluation;

import java.util.HashMap;
import java.util.Map;

import de.rbuurman.jisl.program.expression.VariableName;
import de.rbuurman.jisl.program.Library;
import de.rbuurman.jisl.program.value.Value;

/**
 * Environment
 */
public class Environment {

	private Map<VariableName, Value> definitions = new HashMap<>();

	protected Map<VariableName, Value> getDefinitions() {
		return this.definitions;
	}

	public void addDefinition(VariableName variable, Value value) throws EvaluationException {
		if (this.definitions.containsKey(variable)) {
			throw new EvaluationException("Definition for " + variable + " already exists");
		}
		this.definitions.put(variable, value);
	}

	public Value getValue(VariableName variable) throws EvaluationException {
		final var value = this.definitions.get(variable);

		if (value == null) {
			throw new EvaluationException("Definition for " + variable + " does not exist");
		}

		return value;
	}

	public static Environment merge(Environment base, Environment local) throws EvaluationException {
		var env = new Environment();

		for (Map.Entry<VariableName, Value> entry : base.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		for (Map.Entry<VariableName, Value> entry : local.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		return env;
	}

	public void loadLibrary(Library library) throws EvaluationException {
		for (var definition : library.definitions()) {
			final var variable = definition.getVariable();
			final var value = definition.getExpression().evaluate(this);
			this.addDefinition(variable, value);
		}
	}
}
