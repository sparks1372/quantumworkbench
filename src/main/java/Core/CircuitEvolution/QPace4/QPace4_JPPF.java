/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

import java.util.Iterator;
import java.util.List;

import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.server.protocol.JPPFTask;

import Core.CircuitEvolution.SearchEngineState;
import Utils.JPPFHelper;
import Utils.SendMail;

public class QPace4_JPPF extends QPace4_Imp {

	/**
	 * 
	 */
	public QPace4_JPPF() {
		super();
	}

	@Override
	protected void startSearch() {
		// getSearchStatisticsPanel().setVisible(true);
		// resetProgressBar();
		updateState(SearchEngineState.Searching);
		String[] pstr = { "-file", filename };
		searchres = new QPaceSearchResult[iterval];
		boolean block = false;

		JPPFClient jppfClient = null;
		try {
			jppfClient = new JPPFClient();
			// create a JPPF job
			final JPPFJob job = new JPPFJob();

			// give this job a readable unique id that we can use to monitor and
			// manage it.
			job.setName("QPaceSearch");
			final QPaceSearchCore[] task_array = new QPaceSearchCore[iterval];

			for (int i = 0; i < iterval; i++) {
				task_array[i] = new QPaceSearchCore(pstr, cb, ce, null);
				// add a task to the job.
				job.addTask(task_array[i]);
			}
			// add more tasks here ..

			// create a runner instance.
			final JPPFHelper runner = new JPPFHelper();

			final JPPFClient da = jppfClient;
			final String probname = this.ce.getQproblem().getName();
			// execute a non-blocking job
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					List<JPPFTask> jppfli;
					try {
						jppfli = runner.executeBlockingJob(da, job);
						Iterator<JPPFTask> iter = jppfli.listIterator();
						int index = 0;
						while (iter.hasNext()) {
							QPaceSearchResult sr = (QPaceSearchResult) iter
									.next().getResult();
							if (sr != null) {
								searchres[index] = sr;
							} else {
								System.out.println("sr==null index==" + index);
							}
							index++;
						}
						updateState(SearchEngineState.SearchCompleteResultAvailable);
					} catch (Exception e) {
						e.printStackTrace();
						updateState(SearchEngineState.Start);
					} finally {
						if (da != null) {
							da.close();
						}
					}

					if (!to.equalsIgnoreCase("")) {
						SendMail sendMail = new SendMail(from, to, subject,
								message.concat(probname));
						sendMail.send();
					}
				}
			}, "JPPF Search Manager Thread");
			t.start();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (block && (jppfClient != null)) {
				jppfClient.close();
			}
		}
	}
}