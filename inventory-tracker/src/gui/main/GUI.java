
package gui.main;

import gui.common.DialogBox;
import gui.common.View;
import gui.inventory.InventoryView;
import gui.reports.expired.ExpiredReportView;
import gui.reports.notices.NoticesReportView;
import gui.reports.productstats.ProductStatsReportView;
import gui.reports.removed.RemovedReportView;
import gui.reports.supply.SupplyReportView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import persistence.PersistenceManager;
import persistence.PersistenceType;
import core.model.exception.HITException;


@SuppressWarnings("serial")
public final class GUI extends JFrame implements IMainView {
	
	private IMainController _controller;
	private JMenuBar _menuBar;
	private SessionMenu _sessionMenu;
	private ReportsMenu _reportsMenu;
	private InventoryView _inventoryView;

	private GUI(String[] args) {
		super("Home Inventory Tracker");		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				try {
					PersistenceManager.Factory.getPersistenceManager().save();
				} catch (HITException e) {
					e.printStackTrace();
				}
				/**/
				_sessionMenu.exit();
			}			
		});	
		
		
		try {
			PersistenceManager.Factory.getPersistenceManager().load();
		} catch (HITException e) {
			e.printStackTrace();
		}
		/**/
		
		createMenus();
		createInventoryView();

		_controller = new MainController(this);

		_sessionMenu.setController(_controller);
		_reportsMenu.setController(_controller);
		
		display();
	}

	private void createMenus() {
		_sessionMenu = new SessionMenu(this);
		_sessionMenu.setFont(View.createFont(_sessionMenu.getFont(), View.MenuFontSize));
		
		_reportsMenu = new ReportsMenu(this);
		_reportsMenu.setFont(View.createFont(_reportsMenu.getFont(), View.MenuFontSize));

		_menuBar = new JMenuBar();
		_menuBar.setFont(View.createFont(_menuBar.getFont(), View.MenuFontSize));
		
		_menuBar.add(_sessionMenu);
		_menuBar.add(_reportsMenu);

		setJMenuBar(_menuBar);
	}

	private void createInventoryView() {
		_inventoryView = new InventoryView(this);
		setContentPane(_inventoryView);
	}

	private void display() {
        pack();
        setVisible(true);
	}
	
	public IMainController getController() {
		return _controller;
	}

    //
    // IView overrides
    //

	@Override
	public void displayInformationMessage(String message) {
		displayMessage(message, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void displayWarningMessage(String message) {
		displayMessage(message, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void displayErrorMessage(String message) {
		displayMessage(message, JOptionPane.ERROR_MESSAGE);
	}

	private void displayMessage(final String message, final int messageType) {

		// NOTE: Calling JOptionPane.showMessageDialog from an InputVerifier 
		// does not work (Swing's keyboard focus handling goes bonkers), so 
		// here we call JOptionPane.showMessageDialog using SwingUtilities.invokeLater 
		// to circumvent this problem in the case displayErrorMessage is
		// invoked from an InputVerifier.
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(GUI.this, message, "Inventory Tracker", 
						messageType);
			}
		});
	}

    //
    // IMainView overrides
    //

	@Override
	public void displayExpiredReportView() {
		DialogBox dialog = new DialogBox(this, "Expired Items Report");
		dialog.display(new ExpiredReportView(this, dialog), false);
	}

	@Override
	public void displaySupplyReportView() {
		DialogBox dialog = new DialogBox(this, "N-Month Supply Report");
		dialog.display(new SupplyReportView(this, dialog), false);
	}

	@Override
	public void displayProductReportView() {
		DialogBox dialog = new DialogBox(this, "Product Statistics Report");
		dialog.display(new ProductStatsReportView(this, dialog), false);
	}

	@Override
	public void displayNoticesReportView() {
		DialogBox dialog = new DialogBox(this, "Notices Report");
		dialog.display(new NoticesReportView(this, dialog), false);
	}

	@Override
	public void displayRemovedReportView() {
		DialogBox dialog = new DialogBox(this, "Removed Items Report");
		dialog.display(new RemovedReportView(this, dialog), false);
		
	}
	
	//
	// Main
	//
	
    public static void main(final String[] args) {
     	try {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    		if(args.length > 0 && args[0].compareTo("-sql") == 0) {
    			System.out.println("using sqlite database");
    			PersistenceManager.setPersistence(PersistenceType.sqlite);
    		} else {
    			System.out.println("using serialization");
    			PersistenceManager.setPersistence(PersistenceType.serialization);
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}

 		SwingUtilities.invokeLater(
			new Runnable() {
            	public void run() {
                	new GUI(args);
            	}
        	}
		);
    }

}

