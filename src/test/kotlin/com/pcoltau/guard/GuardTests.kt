package com.pcoltau.guard

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

@Suppress("UNREACHABLE_CODE", "UnusedEquals")
class GuardTests {
    @Test
    fun optionalGuardShouldSucceedWithNonOptionalValue() {
        val str: String? = "test"

        val value = guard(str) otherwise {
            fail("The guard should not fail")
        }

        assertEquals("test", value)
    }

    @Test
    fun optionalGuardShouldFailWithOptionalValue() {
        guard(null) otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun optionalGuardShouldThrowWithNoReturn() {
        assertFailsWith<IllegalStateException> {
            guard(null) otherwise {
            }
        }
    }

    @Test
    fun conditionalGuardShouldSucceedWithTrue() {
        val str = "test"

        guard(str == "test") otherwise {
            fail("The guard should not fail")
        }
    }

    @Test
    fun conditionalGuardShouldFailWithFalse() {
        val str = "test"

        guard(str != "test") otherwise {
            return
        }

        fail("The guard should fail")
    }


    @Test
    fun conditionalGuardShouldThrowWithNoReturn() {
        assertFailsWith<IllegalStateException> {
            guard(false) otherwise {
            }
        }
    }

    @Test
    fun chainingConditionalGuardsShouldSucceedWithTrueAndTrue() {
        guard(true) and(true) otherwise {
            fail("The guard should not fail")
        }
    }

    @Test
    fun chainingConditionalGuardsShouldFailWithTrueAndFalse() {
        guard(true) and(false) otherwise {
            return
        }
        fail("The guard should fail")
    }

    @Test
    fun chainingConditionalGuardsShouldFailWithFalseAndTrue() {
        guard(false) and(true) otherwise {
            return
        }
        fail("The guard should fail")
    }

    @Test
    fun chainingOptionalGuardAndConditionalGuardShouldSucceedWithNonOptionalAndTrue() {
        val str: String? = "test"

        val value = guard(str) where { it == "test" } otherwise {
            fail("The guard should not fail")
        }

        assertEquals("test", value)
    }

    @Test
    fun chainingOptionalGuardAndConditionalGuardsShouldFailWithOptionalNullAndTrue() {
        guard(null) where { it == "test" } otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingOptionalAndConditionalGuardsShouldFailWithNonOptionalAndFalse() {
        val str: String? = "test"

        guard(str) where { it != "test" } otherwise {
            return
        }

        fail("The guard should fail")
    }


    @Test
    fun chainingOptionalAndConditionalGuardsShouldFailWithOptionalNullAndFalse() {
        guard(null) where { it != "test" } otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingConditionalAndOptionalGuardShouldSucceedWithTrueAndNonOptional() {
        val str: String? = "test"

        val value = guard(true) and(str) otherwise {
            fail("The guard should not fail")
        }

        assertEquals("test", value)
    }

    @Test
    fun chainingConditionalAndOptionalGuardsShouldFailWithTrueAndOptionalNull() {
        guard(true) and(null) otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingConditionalAndOptionalGuardShouldFailWithFalseAndNonOptional() {
        guard(false) and(null) otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingConditionalAndOptionalGuardShouldFailWithFalseAndOptionalNull() {
        val str: String? = "test"

        guard(false) and(str) otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingOptionalAndTwoConditionalGuardsShouldFailWithNonOptionalFalseAndLambdaTrue() {
        val str: String? = "test"

        guard(str) and(false) where { it == "test" } otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingOptionalAndTwoConditionalGuardsShouldFailWithWithNonOptionalLambdaFalseAndTrue() {
        val str: String? = "test"

        guard(str) where { it != "test" } and(true) otherwise {
            return
        }

        fail("The guard should fail")
    }

    @Test
    fun chainingTwoOptionalGuardsShouldSucceedWithNonOptionals() {
        val str: String? = "test"
        val int: Int? = 1

        val value = guard(str) and(int) otherwise {
            fail("The guard should not fail")
        }

        assertEquals(1, value)
    }

    @Test
    fun chainingTwoOptionalAndTwoConditionedGuardsShouldSucceedWithNonOptionalLambdaTrueNonOptionalAndLambdaTrue() {
        val str: String? = "test"
        val int: Int? = 1

        val value = guard(str) where { it == "test" } and(int) where { it == 1 } otherwise {
            fail("The guard should not fail")
        }

        assertEquals(1, value)
    }

    @Test
    fun chainingTwoOptionalAndTwoConditionedGuardsShouldFailWithNonOptionalLambdaFalseNonOptionalAndLambdaTrue() {
        val str: String? = "test"
        val int: Int? = 1

        guard(str) where { it != "test" } and(int) where { it == 1 } otherwise {
            return
        }

        fail("The guard should fail")
    }
}
