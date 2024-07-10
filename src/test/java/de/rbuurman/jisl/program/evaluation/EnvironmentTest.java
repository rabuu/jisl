package de.rbuurman.jisl.program.evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;
import de.rbuurman.jisl.program.VariableName;

public class EnvironmentTest {


        @Test
	public void getValueTest() {
                Environment environment = new Environment();

                environment.addDefinition("pi", new NumberPrimitive(3.));

                assertEquals(new NumberPrimitive(3.), environment.getValue("pi"));
                environment.reset();
                assertThrows(EvaluationException.class, () -> environment.getValue("pi"));
	}

        @Test
        public void structsTest() {
                Environment environment = new Environment();

                Multiple<VariableName> fieldnames = new Multiple<VariableName>;
                fields.add("fieldname1").add("fieldname2");
                environment.addDefinition("structname", new NumberPrimitive(0.));

                assertThrows(EvaluationException.class, () -> environment.addStruct("structname", fieldnames));

                environment.reset();

                environment.addStruct("structname", fieldnames);
                assertEquals(fieldnames, environment.getStructFields("structname"));

                assertThrows(EvaluationException.class, () -> environment.addDefinition("make-structname", new NumberPrimitive(0.)));

                environment.reset();

                environment.addDefinition("structname", new NumberPrimitive(0.));
                assertThrows(EvaluationException.class, () -> environment.addStruct("structname", fieldnames));
        }

        @Test
        public void mergeTest() {
                Environment base = new Environment();
                Environment local = new Environment();

                base.addDefinition("pi", new Numberprimitive(3.));
                local.addDefinition("e", new Numberprimitive(2.));
        }
}
