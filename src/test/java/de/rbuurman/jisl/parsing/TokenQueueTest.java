package de.rbuurman.jisl.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.value.primitive.NumberPrimitive;

public class TokenQueueTest {

	@Test
	public void generalFunctionality() {
		TokenQueue queue = new TokenQueue();

		queue.queue(new NumberPrimitive(42.).toToken(null));
		queue.queue(new NumberPrimitive(0.).toToken(null));
		queue.queue(new NumberPrimitive(69.).toToken(null));

		assertEquals(new NumberPrimitive(42.).toToken(null), queue.peek());
		assertEquals(new NumberPrimitive(42.).toToken(null), queue.poll());
		assertEquals(new NumberPrimitive(69.).toToken(null), queue.peekSecond());
		queue.remove();
		assertEquals(queue.size(), 1);
	}
}
