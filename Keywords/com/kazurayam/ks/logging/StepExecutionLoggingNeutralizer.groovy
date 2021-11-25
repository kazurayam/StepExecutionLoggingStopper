package com.kazurayam.ks.logging

import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.logging.KeywordLogger.KeywordStackElement
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap

/**
 * Modifies the `com.kms.katalon.core.logging.KeywordLogger` class runtime
 * using Java Reflection API and Groovy's Meta-programming technique.
 * My objective is to reduce the overhead of superfluous "Start action :" + "End actio :" logs, and
 * Eventually my tests run faster. 
 * I wanted to do this in Katalon Studio Free version where I can not disable "Log executed test steps" option.
 * 
 * The strategy of this Neutralizer is as follows.
 * 
 * 1. modify the `startKeyword()` and `endKeyword()` methods of `KeywordLogger` class so that
 *   the methods does nothing. The methods does not call the logger any longer.
 *   I do it using Groovy's metaClass.
 *
 * But this is not enough. KeywordLogger class maintains a static cache of KeywordLogger instances and
 * Katalon Studio fills the cache as soon as it starts. The modified `KeywordLogger` class is not used then.
 * Therefore the cached instances will have the unmodified `startKeyword()` and `endKeyword` implementation.
 * So I should be able to clear the cache at the timing I want to.
 *
 * 2. initialize the `private static final Map<String, KeywordLogger keywordLoggerLookup` property of
 *   the KeywordLogger class when I want to.
 *
 * You can read the source of that class at
 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/logging/KeywordLogger.java
 * 
 * I learned this way of Java Reflection from
 * - https://qiita.com/5at00001040/items/83bd7ea85d0f545ae7c3
 * - https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
 * 
 * @author kazurayam
 */

public class StepExecutionLoggingNeutralizer {

	static void neutralize() {
		neutralizeStartKeyword()
		neutralizeEndKeyword()
		clearCache()
	}

	static void neutralizeStartKeyword() {
		KeywordLogger.metaClass.startKeyword = { String name, String actionType, Map<String, String> attributes, Stack<KeywordStackElement> keywordStack ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#startKeyword(String,String,Map,Stack) was called"
		}
		KeywordLogger.metaClass.startKeyword = { String name, Map<String, String> attributes, Stack<KeywordStackElement> keywordStack ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#startKeyword(String,Map,Stack) was called"
		}
		KeywordLogger.metaClass.startKeyword = { String name, Map<String, String> attributes, int nestedLevel ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#startKeyword(String,Map,int) was called"
		}
	}

	static void neutralizeEndKeyword() {
		KeywordLogger.metaClass.endKeyword = { String name, String actionType, Map<String, String> attributes, Stack<KeywordStackElement> keywordStack ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#endKeyword(String, String, Map, Stack) was called"
		}
		KeywordLogger.metaClass.endKeyword = { String name, Map<String, String> attributes, Stack<KeywordStackElement> keywordStack ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#endKeyword(String,Map,Stack) was called"
		}
		KeywordLogger.metaClass.endKeyword = { String name, Map<String, String> attributes, int nestedLevel ->
			/* does no logging */
			println KeywordLogger.class.getName() + "#endKeyword(String,Map,int) was called"
		}
	}

	// private static final Map<String, KeywordLogger> keywordLoggerLookup = new ConcurrentHashMap<>();
	static void clearCache() {
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass())
		Field targetField = instance.getClass().getDeclaredField("keywordLoggerLookup")
		Object obj = getPrivateFieldValue(instance, targetField)
		assert obj instanceof Map
		Map m = (Map)obj
		m.clear()
	}

	static int sizeOfKeywordLoggerLookup() {
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass())
		Field targetField = instance.getClass().getDeclaredField("keywordLoggerLookup")
		Object obj = getPrivateFieldValue(instance, targetField)
		assert obj instanceof Map
		Map m = (Map)obj
		return m.size()
	}
	
	static Set<String> keySetOfKeywordLoggerLookup() {
		KeywordLogger instance = KeywordLogger.getInstance(this.getClass())
		Field targetField = instance.getClass().getDeclaredField("keywordLoggerLookup")
		Object obj = getPrivateFieldValue(instance, targetField)
		assert obj instanceof Map
		Map m = (Map)obj
		return m.keySet()
	}

	static Object getPrivateFieldValue(Object obj, Field targetField) {
		Objects.requireNonNull(obj)
		Objects.requireNonNull(targetField)
		targetField.setAccessible(true)
		return targetField.get(obj)
	}
}












