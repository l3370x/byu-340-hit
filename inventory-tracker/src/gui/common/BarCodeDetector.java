package gui.common;

import java.util.*;
import core.model.BarCode;
import core.model.exception.HITException;

public enum BarCodeDetector {
	
	Keyboard{
		Timer timer = new Timer();
		/*
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
			    // Your database code here
			  }
			}, 10*1000);
		*/
	},
	
	Scanner;
	
	public BarCode detectBarCode(String value) {
		try {
			return BarCode.getBarCodeFor(value);
		} catch (HITException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
