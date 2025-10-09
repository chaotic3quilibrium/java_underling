package org.java_underling.util.function;

import java.util.function.DoubleConsumer;

/**
 * Enables the providing of a {@link DoubleConsumer} Lambda function which can throw a checked exception, explicitly
 * specifying {@link Exception}.
 */
@FunctionalInterface
public interface DoubleConsumerCheckedException extends DoubleConsumerChecked<Exception> {

}
