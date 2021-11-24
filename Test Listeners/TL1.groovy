import com.kazurayam.ks.logging.StepExecutionLoggingNeutralizer
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

class TL1 {
	
	private boolean STOP_IT = true
	
	@BeforeTestSuite
	def beforeTestSuite(TestSuiteContext testSuiteContext) {
		
		if (STOP_IT) {
			/**
			* modify KeywordLogger#startKeyword() and KeywordLogger#endKeyword() so that
			* these methods do nothing
			*/
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
	
	@BeforeTestSuite
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		if (STOP_IT) {
			/**
			* modify KeywordLogger#startKeyword() and KeywordLogger#endKeyword() so that
			* these methods do nothing
			*/
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
}