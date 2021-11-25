package com.kazurayam.ks.logging

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.kms.katalon.core.logging.KeywordLogger
import java.lang.reflect.Field
import java.lang.reflect.Modifier


@RunWith(JUnit4.class)
public class StepExecutionLoggingNeutralizerTest {

	@Test
	void test_getPrivateBooleanFieldValue(){
		KeywordLogger instance = new KeywordLogger();
		Field targetField = instance.getClass().getDeclaredField("shouldLogTestSteps")
		Boolean actual = StepExecutionLoggingNeutralizer.getPrivateBooleanFieldValue(instance, targetField)
		assert actual == true
	}

	@Test
	void test_setPrivateBooleanFieldWithValue() {
		KeywordLogger instance = new KeywordLogger();
		Field targetField = instance.getClass().getDeclaredField("shouldLogTestSteps")
		assert StepExecutionLoggingNeutralizer.getPrivateBooleanFieldValue(instance, targetField) == true
		StepExecutionLoggingNeutralizer.setPrivateBooleanFieldWithValue(instance, targetField, Boolean.FALSE)
		assert StepExecutionLoggingNeutralizer.getPrivateBooleanFieldValue(instance, targetField) == false
	}
	
	/**
	 * JUnit freshly load the class `com.kms.katalon.core.logging.KeywordLogger` per each @Test,
	 * therefore this test step is not affected by the previous test step
	 */
	@Test
	void test_getValue_shouldLogTestSteps(){
		KeywordLogger instance = new KeywordLogger();
		Boolean actual = StepExecutionLoggingNeutralizer.getValue_shouldLogTestSteps(instance)
		assert actual == true
	}

	@Test
	void test_neutralize() {
		KeywordLogger instance = new KeywordLogger();
		Boolean before_neutralize = StepExecutionLoggingNeutralizer.getValue_shouldLogTestSteps(instance)
		assert before_neutralize == true
		StepExecutionLoggingNeutralizer.neutralize(instance)
		Boolean after_neutralize = StepExecutionLoggingNeutralizer.getValue_shouldLogTestSteps(instance)
		assert after_neutralize == false
	}
}
