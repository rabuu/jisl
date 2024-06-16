package de.rbuurman.jisl.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.primitive.NumberPrimitive;

public class TokenQueueTest {

	@Test
	public void generalFunctionality() {
		TokenQueue queue = new TokenQueue();

		queue.queue(new NumberPrimitive(42.).toToken());
		queue.queue(new NumberPrimitive(0.).toToken());
		queue.queue(new NumberPrimitive(69.).toToken());

		assertEquals(new NumberPrimitive(42.).toToken(), queue.peek());
		assertEquals(new NumberPrimitive(42.).toToken(), queue.poll());
		assertEquals(new NumberPrimitive(69.).toToken(), queue.peekSecond());
		queue.remove();
		assertEquals(queue.size(), 1);
	}
}
