package gui.reports;

import gui.common.IController;

/**
 * Created with IntelliJ IDEA.
 * User: kmcqueen
 * Date: 7/29/13
 * Time: 9:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IReportController extends IController{
    /**
     * This method is called when any of the fields in the
     * expired items report view is changed by the user.
     */
    void valuesChanged();

    /**
     * This method is called when the user clicks the "OK"
     * button in the expired items report view.
     */
    void display();
}
