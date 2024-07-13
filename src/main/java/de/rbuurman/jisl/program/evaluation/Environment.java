package de.rbuurman.jisl.program.evaluation;

import java.util.HashMap;
import java.util.Map;

import de.rbuurman.jisl.program.VariableName;
import de.rbuurman.jisl.program.builtin.struct.FieldSelector;
import de.rbuurman.jisl.program.builtin.struct.IsStruct;
import de.rbuurman.jisl.program.builtin.struct.MakeStruct;
import de.rbuurman.jisl.program.Library;
import de.rbuurman.jisl.program.value.Value;
import de.rbuurman.jisl.utils.Multiple;

/**
 * Environment
 */
public class Environment {

	private Map<VariableName, Value> definitions = new HashMap<>();
	private Map<VariableName, Multiple<VariableName>> structs = new HashMap<>();

	public Map<VariableName, Value> getDefinitions() {
		return this.definitions;
	}

	protected Map<VariableName, Multiple<VariableName>> getStructs() {
		return this.structs;
	}

	public void addDefinition(final VariableName variable, final Value value) throws EvaluationException {
		if (this.definitions.containsKey(variable) || this.structs.containsKey(variable)) {
			throw new EvaluationException("Duplicate definition for " + variable, variable.getSourcePosition());
		}
		this.definitions.put(variable, value);
	}

	public void addStruct(final VariableName name, final Multiple<VariableName> fields) throws EvaluationException {
		if (this.definitions.containsKey(name) || this.structs.containsKey(name)) {
			throw new EvaluationException("Duplicate definition for struct " + name, name.getSourcePosition());
		}

		final var makeStruct = new VariableName("make-" + name.getInner(), name.getSourcePosition());
		final var isStruct = new VariableName(name.getInner() + "?", name.getSourcePosition());

		final var fieldSelectors = new Multiple<VariableName>();
		for (final var field : Multiple.copy(fields)) {
			fieldSelectors.add(new VariableName(name.getInner() + "-" + field.getInner(), name.getSourcePosition()));
		}

		if (this.definitions.containsKey(makeStruct) || this.definitions.containsKey(isStruct)) {
			throw new EvaluationException("Conflicting definition for " + name, name.getSourcePosition());
		}

		for (final var selector : Multiple.copy(fieldSelectors)) {
			if (this.definitions.containsKey(selector)) {
				throw new EvaluationException("Conflicting definition for " + name, name.getSourcePosition());
			}
		}

		this.structs.put(name, fields);
		this.definitions.put(makeStruct, new MakeStruct(name, name.getSourcePosition()));
		this.definitions.put(isStruct, new IsStruct(name, name.getSourcePosition()));

		final var fieldsCopy = Multiple.copy(fields);
		for (final var selector : fieldSelectors) {
			final var field = fieldsCopy.poll();
			this.definitions.put(selector, new FieldSelector(name, field, name.getSourcePosition()));
		}
	}

	public Value getValue(final VariableName variable) throws EvaluationException {
		final var value = this.definitions.get(variable);

		if (value == null) {
			throw new EvaluationException("No definition for " + variable, variable.getSourcePosition());
		}

		return value;
	}

	public Multiple<VariableName> getStructFields(final VariableName struct) throws EvaluationException {
		final var fields = this.structs.get(struct);

		if (fields == null) {
			throw new EvaluationException("No struct definition for " + struct, struct.getSourcePosition());
		}

		return fields;
	}

	public int getStructFieldIndex(final VariableName struct, final VariableName field) throws EvaluationException {
		final var fields = this.getStructFields(struct);
		final int index = fields.find(field);
		if (index < 0) {
			throw new EvaluationException("Cannot find field " + field + " in " + struct, field.getSourcePosition());
		}
		return index;
	}

	public static Environment merge(final Environment base, final Environment local) throws EvaluationException {
		final var env = new Environment();

		for (final Map.Entry<VariableName, Value> entry : base.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		for (final Map.Entry<VariableName, Multiple<VariableName>> entry : base.getStructs().entrySet()) {
			env.getStructs().put(entry.getKey(), entry.getValue());
		}

		for (final Map.Entry<VariableName, Value> entry : local.getDefinitions().entrySet()) {
			env.getDefinitions().put(entry.getKey(), entry.getValue());
		}

		for (final Map.Entry<VariableName, Multiple<VariableName>> entry : local.getStructs().entrySet()) {
			env.getStructs().put(entry.getKey(), entry.getValue());
		}

		return env;
	}

	public void loadLibrary(final Library library) throws EvaluationException {
		Environment merged = merge(this, library.environment());
		this.definitions = merged.definitions;
		this.structs = merged.structs;
	}

	public void reset() {
		this.definitions.clear();
		this.structs.clear();
	}
}
