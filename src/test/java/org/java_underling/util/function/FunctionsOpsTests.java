package org.java_underling.util.function;

import org.java_underling.lang.MissingImplementationException;
import org.java_underling.lang.WrappedCheckedException;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionsOpsTests {
  @Test
  public void testExecuteSideEffectNTimes() {
    //also tests VoidSupplier, VoidSupplier, and VoidSupplierCheckedException via the wrapCheckedException() pathways
    var count = 5;
    var arrayCounter = new int[]{0};
    FunctionsOps.executeSideEffectNTimes(
        count,
        () ->
            arrayCounter[0]++);
    assertEquals(count, arrayCounter[0]);
    var reader = new StringReader("x");
    reader.close();
    @SuppressWarnings("ResultOfMethodCallIgnored")
    var wrappedCheckedException = assertThrows(
        WrappedCheckedException.class,
        () ->
            FunctionsOps.executeSideEffectNTimes(2, reader::read));
    assertEquals("java.io.IOException: Stream closed", wrappedCheckedException.getMessage());
    assertEquals(java.io.IOException.class, wrappedCheckedException.getCause().getClass());
    assertEquals("Stream closed", wrappedCheckedException.getCause().getMessage());
  }

  @Test
  public void testRemainingUnimplemented() {
    //TODO: x8 remaining wrapCheckedException() pathways
    //  - BiConsumer*
    //  - BiFunction*
    //  - BinaryOperator*
    //  - BiPredicate*
    //  - BooleanSupplier*
    //  - Consumer*
    //  - DoubleBinaryOperator*
    //  - DoubleConsumer*
    //  - DoubleFunction*
    //  - DoublePredicate*
    //  - DoubleSupplier*
    //  - Function3*
    //  - Function4*
    //  - Function5*
    //  - Function6*
    //  - Function7*
    //  - Function8*
    //  - Function9*
    //  - Function10*
    //  - Function*
    //  - Predicate*
    //  - Supplier*
    //  - UnaryOperator*
    //  - VoidSupplier*

    throw new MissingImplementationException("missing x8 remaining tests");
  }
}
