package org.java_underling.util;

import org.java_underling.lang.WrappedCheckedException;
import org.java_underling.util.tuple.Tuple2;
import org.java_underling.util.tuple.Tuple3;
import org.java_underling.util.tuple.Tuple4;
import org.java_underling.util.tuple.Tuple5;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

public class Using {
  @NotNull
  public static <A extends AutoCloseable, T> Either<RuntimeException, T> apply(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, T> fAToT
  ) {
    try (
        var a = fSupplierA.get()
    ) {

      return Either.right(fAToT.apply(a));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <A extends AutoCloseable, T> T applyUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, T> fAToT
  ) {

    return apply(
        fSupplierA,
        fAToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      T> Either<RuntimeException, T> apply(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Function<Tuple2<A, B>, T> fAAndBToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fSupplierB.get();
    ) {

      return Either.right(fAAndBToT.apply(new Tuple2<>(a, b)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      T> T applyUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Function<Tuple2<A, B>, T> fAAndBToT
  ) {
    return apply(
        fSupplierA,
        fSupplierB,
        fAAndBToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      T> Either<RuntimeException, T> applyNested(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, T> fTuple2ToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fAToB.apply(a);
    ) {

      return Either.right(fTuple2ToT.apply(new Tuple2<>(a, b)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      T> T applyNestedUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, T> fTuple2ToT
  ) {
    return applyNested(
        fSupplierA,
        fAToB,
        fTuple2ToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      T> Either<RuntimeException, T> apply(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Function<Tuple3<A, B, C>, T> fAAndBAndCToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fSupplierB.get();
        var c = fSupplierC.get();
    ) {

      return Either.right(fAAndBAndCToT.apply(new Tuple3<>(a, b, c)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      T> T applyUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Function<Tuple3<A, B, C>, T> fAAndBAndCToT
  ) {
    return apply(
        fSupplierA,
        fSupplierB,
        fSupplierC,
        fAAndBAndCToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      T> Either<RuntimeException, T> applyNested(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, T> fAAndBAndCToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fAToB.apply(a);
        var c = fAndBToC.apply(new Tuple2<>(a, b));
    ) {

      return Either.right(fAAndBAndCToT.apply(new Tuple3<>(a, b, c)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      T> Either<RuntimeException, T> applyNestedUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, T> fAAndBAndCToT
  ) {
    return applyNested(
        fSupplierA,
        fAToB,
        fAndBToC,
        fAAndBAndCToT);
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      T> Either<RuntimeException, T> apply(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Supplier<D> fSupplierD,
      @NotNull Function<Tuple4<A, B, C, D>, T> fAAndBAndCAndDToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fSupplierB.get();
        var c = fSupplierC.get();
        var d = fSupplierD.get();
    ) {

      return Either.right(fAAndBAndCAndDToT.apply(new Tuple4<>(a, b, c, d)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      T> T applyUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Supplier<D> fSupplierD,
      @NotNull Function<Tuple4<A, B, C, D>, T> fAAndBAndCAndDToT
  ) {
    return apply(
        fSupplierA,
        fSupplierB,
        fSupplierC,
        fSupplierD,
        fAAndBAndCAndDToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      T> Either<RuntimeException, T> applyNested(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, D> fAndBAndCToD,
      @NotNull Function<Tuple4<A, B, C, D>, T> fAAndBAndCAndDToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fAToB.apply(a);
        var c = fAndBToC.apply(new Tuple2<>(a, b));
        var d = fAndBAndCToD.apply(new Tuple3<>(a, b, c));
    ) {

      return Either.right(fAAndBAndCAndDToT.apply(new Tuple4<>(a, b, c, d)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      T> Either<RuntimeException, T> applyNestedUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, D> fAndBAndCToD,
      @NotNull Function<Tuple4<A, B, C, D>, T> fAAndBAndCAndDToT
  ) {
    return applyNested(
        fSupplierA,
        fAToB,
        fAndBToC,
        fAndBAndCToD,
        fAAndBAndCAndDToT);
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      E extends AutoCloseable,
      T> Either<RuntimeException, T> apply(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Supplier<D> fSupplierD,
      @NotNull Supplier<E> fSupplierE,
      @NotNull Function<Tuple5<A, B, C, D, E>, T> fAAndBAndCAndDAndEToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fSupplierB.get();
        var c = fSupplierC.get();
        var d = fSupplierD.get();
        var e = fSupplierE.get();
    ) {

      return Either.right(fAAndBAndCAndDAndEToT.apply(new Tuple5<>(a, b, c, d, e)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      E extends AutoCloseable,
      T> T applyUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Supplier<B> fSupplierB,
      @NotNull Supplier<C> fSupplierC,
      @NotNull Supplier<D> fSupplierD,
      @NotNull Supplier<E> fSupplierE,
      @NotNull Function<Tuple5<A, B, C, D, E>, T> fAAndBAndCAndDAndEToT
  ) {
    return apply(
        fSupplierA,
        fSupplierB,
        fSupplierC,
        fSupplierD,
        fSupplierE,
        fAAndBAndCAndDAndEToT)
        .getRightOrThrowLeft();
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      E extends AutoCloseable,
      T> Either<RuntimeException, T> applyNested(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, D> fAndBAndCToD,
      @NotNull Function<Tuple4<A, B, C, D>, E> fAndBAndCAndDToE,
      @NotNull Function<Tuple5<A, B, C, D, E>, T> fAAndBAndCAndDAndEToT
  ) {
    try (
        var a = fSupplierA.get();
        var b = fAToB.apply(a);
        var c = fAndBToC.apply(new Tuple2<>(a, b));
        var d = fAndBAndCToD.apply(new Tuple3<>(a, b, c));
        var e = fAndBAndCAndDToE.apply(new Tuple4<>(a, b, c, d));
    ) {

      return Either.right(fAAndBAndCAndDAndEToT.apply(new Tuple5<>(a, b, c, d, e)));
    } catch (WrappedCheckedException wrappedCheckedException) {
      return Either.left(
          wrappedCheckedException.getCause() instanceof RuntimeException runtimeException
              ? runtimeException
              : wrappedCheckedException);
    } catch (RuntimeException runtimeException) {
      return Either.left(runtimeException);
    } catch (Exception exception) {
      return Either.left(new WrappedCheckedException(exception));
    }
  }

  @NotNull
  public static <
      A extends AutoCloseable,
      B extends AutoCloseable,
      C extends AutoCloseable,
      D extends AutoCloseable,
      E extends AutoCloseable,
      T> Either<RuntimeException, T> applyNestedUnsafe(
      @NotNull Supplier<A> fSupplierA,
      @NotNull Function<A, B> fAToB,
      @NotNull Function<Tuple2<A, B>, C> fAndBToC,
      @NotNull Function<Tuple3<A, B, C>, D> fAndBAndCToD,
      @NotNull Function<Tuple4<A, B, C, D>, E> fAndBAndCAndDToE,
      @NotNull Function<Tuple5<A, B, C, D, E>, T> fAAndBAndCAndDAndEToT
  ) {
    return applyNested(
        fSupplierA,
        fAToB,
        fAndBToC,
        fAndBAndCToD,
        fAndBAndCAndDToE,
        fAAndBAndCAndDAndEToT);
  }
}
