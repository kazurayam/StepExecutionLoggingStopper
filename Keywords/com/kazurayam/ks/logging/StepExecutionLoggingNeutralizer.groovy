package com.kazurayam.ks.logging

import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.logging.KeywordLogger.KeywordStackElement

/**
 * Modifies  methods of the com.kms.katalon.core.logging.KeywordLogger class runtime
 * using Groovy's Meta-programming technique.
 * 
 * See the source of that class at
 * https://github.com/katalon-studio/katalon-studio-testing-framework/blob/master/Include/scripts/groovy/com/kms/katalon/core/logging/KeywordLogger.java
 * 
 * Will override `startKeyword()` and `endKeyword()` in order to stop "step execution logs" (START keyword, END keyword)
 * so that we can reduce the overhead of superfluous events, and eventually our tests can run far faster.
 * 
 * @author kazurayam
 */
public class StepExecutionLoggingNeutralizer {

	static void neutralize() {
		neutralizeStartKeyword()
		neutralizeEndKeyword()
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
		println StepExecutionLoggingNeutralizer.class.getName() + "#neutralizeStartKeyword() was called"
		
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
		println StepExecutionLoggingNeutralizer.class.getName() + "#neutralizeEndKeyword() finished"
	}
}
