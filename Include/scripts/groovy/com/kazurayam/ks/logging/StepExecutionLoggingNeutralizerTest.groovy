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
		println "[test_getPrivateFieldValue] keyset: ${m.keySet()}"
	}
	
	@Test
	void test_sizeOfKeywordLoggerLookup() {
		int size = StepExecutionLoggingNeutralizer.sizeOfKeywordLoggerLookup()
		assert size > 1
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
	void test_clearCache() {
		StepExecutionLoggingNeutralizer.clearCache()
		Set<String> keySet = StepExecutionLoggingNeutralizer.keySetOfKeywordLoggerLookup()
		assert keySet.size() > 0 
		println "[test_clearCache] keySet: ${keySet}"
	}

	@Test
	void test_neutralize() {
		StepExecutionLoggingNeutralizer.neutralize()
	}
}
