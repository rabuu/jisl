package de.rbuurman.jisl.program.evaluation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.utils.Multiple;
import de.rbuurman.jisl.program.VariableName;

public class EnvironmentTest {
        final VariableName PI = new VariableName("pi", null);
        final VariableName E = new VariableName("e", null);
        final VariableName F1 = new VariableName("field1", null);
        final VariableName F2 = new VariableName("field2", null);
        final VariableName S = new VariableName("struct", null);

        @Test
        public void getValueTest() throws EvaluationException {
                Environment environment = new Environment();

                environment.addDefinition(PI, new NumberPrimitive(3.));

                assertEquals(new NumberPrimitive(3.), environment.getValue(PI));
                environment.reset();
                assertThrows(EvaluationException.class, () -> environment.getValue(PI));
        }

        @Test
        public void structsTest() throws EvaluationException {
                Environment environment = new Environment();

                Multiple<VariableName> fields = new Multiple<VariableName>();
                fields.add(F1);
                fields.add(F2);

                environment.addDefinition(S, new NumberPrimitive(0.));

                assertThrows(EvaluationException.class, () -> environment.addStruct(S, fields));

                environment.reset();

                environment.addStruct(S, fields);
                assertEquals(fields, environment.getStructFields(S));

                assertThrows(EvaluationException.class,
                                () -> environment.addDefinition(new VariableName("make-struct", null),
                                                new NumberPrimitive(0.)));

                environment.reset();

                environment.addDefinition(S, new NumberPrimitive(0.));
                assertThrows(EvaluationException.class, () -> environment.addStruct(S, fields));
        }

        @Test
        public void mergeTest() throws EvaluationException {
                Environment base = new Environment();
                Environment local = new Environment();

                base.addDefinition(PI, new NumberPrimitive(3.));
                local.addDefinition(E, new NumberPrimitive(2.));

                Environment merged = Environment.merge(base, local);
                assertTrue(merged.getDefinitions().keySet().contains(PI));
                assertTrue(merged.getDefinitions().keySet().contains(E));
        }
}
