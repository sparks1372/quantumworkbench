/**
 * @Author = Sam Ratcliff
 */
package Utils;

import java.util.LinkedList;
import java.util.List;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.client.JPPFResultCollector;
import org.jppf.server.protocol.JPPFTask;

/**
 * @author Sam Ratcliff
 * 
 */
public class JPPFHelper {

	public static synchronized List<JPPFTask> runJob(JPPFTask t,
			int iterations, boolean block) {
		List<JPPFTask> results = new LinkedList<JPPFTask>();
		JPPFClient jppfClient = null;
		try {
			jppfClient = new JPPFClient();
			// create a JPPF job
			JPPFJob job = new JPPFJob();

			// give this job a readable unique id that we can use to monitor and
			// manage it.
			job.setId("Template Job Id");

			for (int i = 0; i < iterations; i++) {
				// add a task to the job.
				job.addTask(t);
			}
			// add more tasks here ..

			// create a runner instance.
			JPPFHelper runner = new JPPFHelper();

			if (block) {
				// execute a blocking job
				results = runner.executeBlockingJob(jppfClient, job);
			} else {
				// execute a non-blocking job
				JPPFResultCollector collector1 = new JPPFResultCollector(job
						.getTasks().size());
				runner.executeNonBlockingJob(jppfClient, job, collector1);
				results = collector1.waitForResults();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (jppfClient != null) {
				jppfClient.close();
			}
		}
		return results;
	}

	/**
	 * Execute a job in blocking mode. The application will be blocked until the
	 * job execution is complete.
	 * 
	 * @param job
	 *            the JPPF job to execute.
	 * @throws Exception
	 *             if an error occurs while executing the job.
	 */
	public List<JPPFTask> executeBlockingJob(JPPFClient jppfClient, JPPFJob job)
			throws Exception {
		// set the job in blocking mode.
		job.setBlocking(true);
		return jppfClient.submit(job);
	}

	/**
	 * Execute a job in non-blocking mode. The application has the
	 * responsibility for handling the notification of job completion and
	 * collecting the results.
	 * 
	 * @param job
	 *            the JPPF job to execute.
	 * @throws Exception
	 *             if an error occurs while executing the job.
	 */
	public void executeNonBlockingJob(JPPFClient jppfClient, JPPFJob job,
			JPPFResultCollector jrc) throws Exception {
		// set the job in non-blocking (or asynchronous) mode.
		job.setBlocking(false);

		// We need to be notified of when the job execution has completed.
		// To this effect, we define an instance of the TaskResultListener
		// interface,
		// which we will register with the job.
		// Here, we use an instance of JPPFResultCollector, conveniently
		// provided by the JPPF API.
		// JPPFResultCollector implements TaskResultListener and has a
		// constructor that takes
		// the number of tasks in the job as a parameter.
		job.setResultListener(jrc);

		// Submit the job. This call returns immediately without waiting for the
		// execution of
		// the job to complete. As a consequence, the object returned for a
		// non-blocking job is
		// always null. Note that we are calling the exact same method as in the
		// blocking case.
		jppfClient.submit(job);

	}
}
