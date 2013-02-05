/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution.QPace4;

import Core.CircuitEvolution.SearchEngineState;
import Utils.SendMail;

public class QPace4_Local extends QPace4_Imp {

	/**
	 * 
	 */
	public QPace4_Local() {
		super();
	}

	@Override
	protected void startSearch() {
		resetProgressBar();

		updateState(SearchEngineState.Searching);
		final String[] pstr = { "-file", filename };
		searchres = new QPaceSearchResult[iterval];
		final String probname = this.ce.getQproblem().getName();
		final QPaceSearchCore[] task_array = new QPaceSearchCore[iterval];
		final int geners = gen;

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < iterval; i++) {
					task_array[i] = new QPaceSearchCore(pstr, cb, ce,
							(StatsPanel) getSearchStatisticsPanel());
					task_array[i].run();
					searchres[i] = (QPaceSearchResult) task_array[i].getResult();
					((StatsPanel) getSearchStatisticsPanel()).getProgressBar()
							.setValue((i + 1) * geners);
				}
				updateState(SearchEngineState.SearchCompleteResultAvailable);
				if (!to.equalsIgnoreCase("")) {
					SendMail sendMail = new SendMail(from, to, subject, message
							.concat(probname));
					sendMail.send();
				}
			}
		}, "Local Search (Thread Based) Manager Thread");
		t.start();
	}
}