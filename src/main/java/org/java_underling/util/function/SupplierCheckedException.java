package org.java_underling.util.function;

import java.util.function.Supplier;

/**
 * Enables the providing of a {@link Supplier} Lambda function which can throw a checked exception, explicitly specifying
 * {@link Exception}.
 */
@FunctionalInterface
public interface SupplierCheckedException<R> extends SupplierChecked<R, Exception> {

}
