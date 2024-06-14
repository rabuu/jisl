package de.rbuurman.jisl.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.rbuurman.jisl.program.primitive.IntegerPrimitive;

public class TokenQueueTest {

	@Test
	public void generalFunctionality() {
		TokenQueue queue = new TokenQueue();

		queue.queue(new IntegerPrimitive(42).toToken());
		queue.queue(new IntegerPrimitive(0).toToken());
		queue.queue(new IntegerPrimitive(69).toToken());

		assertEquals(new IntegerPrimitive(42).toToken(), queue.peek());
		assertEquals(new IntegerPrimitive(42).toToken(), queue.poll());
		assertEquals(new IntegerPrimitive(69).toToken(), queue.peekNth(2));
		queue.remove();
		assertEquals(queue.size(), 1);
	}
}
