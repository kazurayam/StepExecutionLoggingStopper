import com.kazurayam.ks.logging.StepExecutionLoggingNeutralizer
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TL1 {
	
	private boolean DO_IT = true
	
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		if (DO_IT) {
			/**
			* modify KeywordLogger#startKeyword() and KeywordLogger#endKeyword() so that
			* these methods do nothing
			*/
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
	
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		if (DO_IT) {
			/**
			* modify KeywordLogger#startKeyword() and KeywordLogger#endKeyword() so that
			* these methods do nothing
			*/
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
}