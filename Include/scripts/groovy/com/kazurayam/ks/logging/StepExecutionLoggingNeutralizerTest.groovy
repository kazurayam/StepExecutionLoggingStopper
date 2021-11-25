package com.kazurayam.ks.logging

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

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
	void test_getValue_shouldLogTestSteps(){
		Boolean actual = StepExecutionLoggingNeutralizer.getValue_shouldLogTestSteps()
		assert actual == true
	}
	
	@Test
	void test_setPrivateBooleanFieldWithValue() {
		KeywordLogger instance = new KeywordLogger();
		Field targetField = instance.getClass().getDeclaredField("shouldLogTestSteps")
		StepExecutionLoggingNeutralizer.setPrivateBooleanFieldWithValue(instance, targetField, Boolean.FALSE)
		assert StepExecutionLoggingNeutralizer.getPrivateBooleanFieldValue(instance, targetField) == false
	}

	@Test
	void test_neutralize() {
		StepExecutionLoggingNeutralizer.neutralize()
		Boolean actual = StepExecutionLoggingNeutralizer.getValue_shouldLogTestSteps()
		assert actual == false
	}
}
