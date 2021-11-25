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
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
	
	/* usually not necessary
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		
		if (DO_IT) {
			StepExecutionLoggingNeutralizer.neutralize()
		}
	}
	*/
}