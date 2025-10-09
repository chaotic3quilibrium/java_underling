package org.java_underling.util.function;

import java.util.function.DoubleBinaryOperator;

/**
 * Enables the providing of a {@link DoubleBinaryOperator} Lambda function which can throw a checked exception,
 * explicitly specifying {@link Exception}.
 */
@FunctionalInterface
public interface DoubleBinaryOperatorCheckedException extends DoubleBinaryOperatorChecked<Exception> {

}

