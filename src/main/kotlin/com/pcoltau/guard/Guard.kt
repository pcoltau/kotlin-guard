package com.pcoltau.guard

open class ConditionalGuard(val condition: Boolean) {
    inline infix fun otherwise(otherwise: () -> Unit) {
        if (!condition) {
            otherwise()
            check(false) {
                "If a guard fails, the 'otherwise' block must return!"
            }
        }
    }

    infix fun and(condition: Boolean): ConditionalGuard {
        return if (this.condition) {
            ConditionalGuard(condition)
        } else {
            FailedConditionalGuard()
        }
    }

    infix fun <R> and(optional: R?): ConditionalOptionalGuard<R> {
        return if (condition) {
            ConditionalOptionalGuard(condition, optional)
        } else {
            FailedOptionalGuard()
        }
    }
}

open class ConditionalOptionalGuard<T>(val condition: Boolean, val optional: T?) {
    inline infix fun otherwise(otherwise: () -> Unit): T {
        return if (optional != null && this.condition) {
            optional
        } else {
            otherwise()
            check(false) {
                "If a guard fails, the 'otherwise' block must return!"
            }
            optional!!
        }
    }

    infix fun and(condition: Boolean): ConditionalOptionalGuard<T> {
        return if (this.condition) {
            ConditionalOptionalGuard(condition, optional)
        } else {
            FailedOptionalGuard()
        }
    }

    infix fun <R> and(optional: R?): ConditionalOptionalGuard<R> {
        return if (this.optional != null) {
            ConditionalOptionalGuard(this.condition, optional)
        } else {
            FailedOptionalGuard()
        }
    }

    inline infix fun where(condition: (T) -> Boolean): ConditionalOptionalGuard<T> {
        return if (optional != null && this.condition) {
            ConditionalOptionalGuard(condition(this.optional), optional)
        } else {
            FailedOptionalGuard()
        }
    }
}

class FailedConditionalGuard: ConditionalGuard(false)

class FailedOptionalGuard<T>: ConditionalOptionalGuard<T>(false, null)

class OptionalGuard<T>(optional: T?): ConditionalOptionalGuard<T>(true, optional)

fun guard(condition: Boolean) = ConditionalGuard(condition)

fun <T> guard(optional: T?) = OptionalGuard(optional)