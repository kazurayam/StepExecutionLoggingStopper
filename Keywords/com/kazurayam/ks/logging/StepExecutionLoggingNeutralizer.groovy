package com.kazurayam.ks.logging

import com.kms.katalon.core.logging.KeywordLogger
import java.lang.reflect.Field
import java.lang.reflect.Modifier

/**
 * Modifies the `static private shouldLogTestSteps` property of 
 * `com.kms.katalon.core.logging.KeywordLogger` class runtime
 * using Java Reflection API.
 * 
 * Once the `shouldLogTestSteps` property is turned to be false, 
 * `KeyworLogger` will stop firing the "step execution logs" events at all.
 * By doing so, we can reduce the overhead of superfluous "Start action :" + "End action :" messages.
 * Eventually our tests will run faster.
 *  
 * You can read the source of that class at
 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/logging/KeywordLogger.java
 * 
 * @author kazurayam
 */
public class StepExecutionLoggingNeutralizer {

	/**
	 * return the boolean value of the `private static` property `shouldLogTestSteps` of
	 * a `KeywordLogger` object
	 * 
	 * @return
	 */
	static Boolean getValue_shouldLogTestSteps() {
		KeywordLogger instance = new KeywordLogger();
		Field targetField = instance.getClass().getDeclaredField("shouldLogTestSteps")
		return getPrivateBooleanFieldValue(instance, targetField)
	}

	static Boolean getPrivateBooleanFieldValue(Object obj, Field targetField) {
		Objects.requireNonNull(obj)
		Objects.requireNonNull(targetField)
		targetField.setAccessible(true)
		return targetField.getBoolean(obj)
	}

	static void neutralize() {
		KeywordLogger instance = new KeywordLogger();
		Field targetField = instance.getClass().getDeclaredField("shouldLogTestSteps")
		setPrivateBooleanFieldWithValue(instance, targetField, Boolean.FALSE)
		assert getValue_shouldLogTestSteps() == false
	}

	static void setPrivateBooleanFieldWithValue(Object obj, Field targetField, Object newValue) {
		Objects.requireNonNull(targetField)
		Objects.requireNonNull(newValue)
		//
		targetField.setAccessible(true)
		Field modifiers = Field.class.getDeclaredField("modifiers")
		modifiers.setAccessible(true)
		modifiers.setInt(targetField,
				targetField.getModifiers() & ~Modifier.PRIVATE & ~Modifier.FINAL)
		//
		targetField.set(obj, newValue)
	}
}
