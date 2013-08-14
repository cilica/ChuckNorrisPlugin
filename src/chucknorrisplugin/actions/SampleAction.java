package chucknorrisplugin.actions;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.osgi.framework.Bundle;

import chucknorrisplugin.Activator;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class SampleAction implements IWorkbenchWindowActionDelegate {
	
	public static Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public SampleAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(
			window.getShell(),
			"ChuckNorrisPlugin",
			getChucksQuote());
	}

	private String getChucksQuote() {
		
		URL chuchFile = getChuckFile();

		
		String sCurrentLine ="";
		Random rnd = new Random();
		
		try
		{
			InputStream inStream = null;
			try {
				inStream = chuchFile.openStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int totalLineNumber = count(inStream);
			int line = rnd.nextInt(totalLineNumber);
			int currLine = 0;
			
			try {
				inStream = chuchFile.openStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			DataInputStream in = new DataInputStream(inStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if (currLine == line)
				{
					System.out.println(sCurrentLine);
					break;
				}
				else
				{
					currLine++;
				}
				
				
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sCurrentLine; 
	}
	
	private int count(InputStream is) throws IOException {
	    try {
	        byte[] c = new byte[1024];
	        int count = 1;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        return (count == 0 && !empty) ? 1 : count;
	    } finally {
	    	is.close();
	    }
	}

	private URL getChuckFile() 
	{
		Path path = new Path("text/chuckquotes.txt");
		
		URL fileURL = FileLocator.find(bundle, path, null);
		
		return fileURL;
	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}