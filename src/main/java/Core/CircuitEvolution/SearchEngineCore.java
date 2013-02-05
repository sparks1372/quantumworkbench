/**
 * @Author = Sam Ratcliff
 */
package Core.CircuitEvolution;

import org.jppf.server.protocol.JPPFTask;

/**
 * @author Sam Ratcliff
 */
public abstract class SearchEngineCore extends JPPFTask {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6996895023072068277L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		search();
	}

	public abstract void search();
}
