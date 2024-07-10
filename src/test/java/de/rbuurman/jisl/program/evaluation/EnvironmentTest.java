package de.rbuurman.jisl.program.evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.program.VariableName;

public class EnvironmentTest {

        @Test
        public void generalFunctionality() throws EvaluationException {
                Environment environment = new Environment();

                final var PI = new VariableName("pi", null);
                environment.addDefinition(PI, new NumberPrimitive(3.));

                assertEquals(new NumberPrimitive(3.), environment.getValue(PI));
                environment.reset();
                assertThrows(EvaluationException.class, () -> environment.getValue(PI));
        }
}
