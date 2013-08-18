package info.samratcliff.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Sam Ratcliff
 * Date: 18/08/13
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public interface Service {
    boolean start();

    boolean stop();

    boolean isRunning();
}
