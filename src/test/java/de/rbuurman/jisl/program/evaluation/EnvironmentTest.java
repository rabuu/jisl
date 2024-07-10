package de.rbuurman.jisl.program.evaluation.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;

public class EnvironmentTest {

	@Test
	public void generalFunctionality() {
        Environment environment = new Environment();

        environment.addDefinition("pi", new NumberPrimitive(3.));


		    assertEquals(new NumberPrimitive(3.), environment.getValue("pi"));
        environment.reset();
        assertThrows(EvaluationException.class, () -> environment.getValue("pi"));
	}
}
