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
import java.util.concurrent.ConcurrentHashMap

@RunWith(JUnit4.class)
public class StepExecutionLoggingNeutralizerTest {

	@Test
	void test_getPrivateFieldValue() {
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass())
		Field targetField = instance.getClass().getDeclaredField("keywordLoggerLookup")
		Object obj = StepExecutionLoggingNeutralizer.getPrivateFieldValue(instance, targetField)
		assert obj instanceof Map
		Map m = (Map)obj
		println "keyworLoggerLoockup.keyset(): ${m.keySet()}"
	}
	
	@Test
	void test_sizeOfKeywordLoggerLookup() {
		int size = StepExecutionLoggingNeutralizer.sizeOfKeywordLoggerLookup()
		assert size > 3
	}

	@Test
	void test_newtralizeStartKeyword() {
		StepExecutionLoggingNeutralizer.neutralizeStartKeyword()
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass());
		instance.startKeyword("", [:], 0)
	}

	@Test
	void test_newtralizeEndKeyword() {
		StepExecutionLoggingNeutralizer.neutralizeEndKeyword()
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass());
		instance.endKeyword("", [:], 0)
	}

	@Test
	void test_setPrivateStaticFinalFieldWithValue() {
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass());
		Field targetField = instance.getClass().getDeclaredField("keywordLoggerLookup")
		StepExecutionLoggingNeutralizer.setPrivateStaticFinalFieldWithValue(instance, targetField,
				new ConcurrentHashMap<>())
	}
	
	@Ignore
	@Test
	void test_clearCache() {
		StepExecutionLoggingNeutralizer.clearCache()
	}

	@Ignore
	@Test
	void test_neutralize() {
		StepExecutionLoggingNeutralizer.neutralize()
	}
}
