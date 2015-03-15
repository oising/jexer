/*
 * Jexer - Java Text User Interface
 *
 * License: LGPLv3 or later
 *
 * This module is licensed under the GNU Lesser General Public License
 * Version 3.  Please see the file "COPYING" in this directory for more
 * information about the GNU Lesser General Public License Version 3.
 *
 *     Copyright (C) 2015  Kevin Lamonte
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, see
 * http://www.gnu.org/licenses/, or write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * @author Kevin Lamonte [kevin.lamonte@gmail.com]
 * @version 1
 */
package jexer.demos;

import jexer.*;
import jexer.event.*;
import jexer.menu.*;

/**
 * This window demonstates the TText, THScroller, and TVScroller widgets.
 */
class DemoTextWindow extends TWindow {

    /**
     * Hang onto my TText so I can resize it with the window.
     */
    private TText textField;

    /**
     * Public constructor.
     *
     * @param parent the main application
     */
    public DemoTextWindow(final TApplication parent) {
        super(parent, "Text Areas", 0, 0, 44, 20, RESIZABLE);

        textField = addText(
"This is an example of a reflowable text field.  Some example text follows.\n" +
"\n" +
"This library implements a text-based windowing system loosely\n" +
"reminiscient of Borland's [Turbo\n" +
"Vision](http://en.wikipedia.org/wiki/Turbo_Vision) library.  For those\n" +
"wishing to use the actual C++ Turbo Vision library, see [Sergio\n" +
"Sigala's updated version](http://tvision.sourceforge.net/) that runs\n" +
"on many more platforms.\n" +
"\n" +
"Currently the only console platform supported is Posix (tested on\n" +
"Linux).  Input/output is handled through terminal escape sequences\n" +
"generated by the library itself: ncurses is not required or linked to. \n" +
"xterm mouse tracking using UTF8 coordinates is supported.\n" +
"\n" +
"This library is licensed LGPL (\"GNU Lesser General Public License\")\n" +
"version 3 or greater.  See the file COPYING for the full license text,\n" +
"which includes both the GPL v3 and the LGPL supplemental terms.\n" +
"\n",
            1, 1, 40, 16);
    }

    /**
     * Handle window/screen resize events.
     *
     * @param event resize event
     */
    @Override
    public void onResize(final TResizeEvent event) {
        if (event.getType() == TResizeEvent.Type.WIDGET) {
            // Resize the text field
            textField.setWidth(event.getWidth() - 4);
            textField.setHeight(event.getHeight() - 4);
            textField.reflow();
            return;
        }

        // Pass to children instead
        for (TWidget widget: getChildren()) {
            widget.onResize(event);
        }
    }
}

/**
 * This window demonstates the TRadioGroup, TRadioButton, and TCheckbox
 * widgets.
 */
class DemoCheckboxWindow extends TWindow {

    /**
     * Constructor.
     *
     * @param parent the main application
     */
    DemoCheckboxWindow(final TApplication parent) {
        this(parent, CENTERED | RESIZABLE);
    }

    /**
     * Constructor.
     *
     * @param parent the main application
     * @param flags bitmask of MODAL, CENTERED, or RESIZABLE
     */
    DemoCheckboxWindow(final TApplication parent, final int flags) {
        // Construct a demo window.  X and Y don't matter because it will be
        // centered on screen.
        super(parent, "Radiobuttons and Checkboxes", 0, 0, 60, 15, flags);

        int row = 1;

        // Add some widgets
        addLabel("Check box example 1", 1, row);
        addCheckbox(35, row++, "Checkbox 1", false);
        addLabel("Check box example 2", 1, row);
        addCheckbox(35, row++, "Checkbox 2", true);
        row += 2;

        TRadioGroup group = addRadioGroup(1, row, "Group 1");
        group.addRadioButton("Radio option 1");
        group.addRadioButton("Radio option 2");
        group.addRadioButton("Radio option 3");

        addButton("&Close Window", (getWidth() - 14) / 2, getHeight() - 4,
            new TAction() {
                public void DO() {
                    DemoCheckboxWindow.this.getApplication()
                        .closeWindow(DemoCheckboxWindow.this);
                }
            }
        );
    }

}


/**
 * This window demonstates the TMessageBox and TInputBox widgets.
 */
class DemoMsgBoxWindow extends TWindow {

    /**
     * Constructor.
     *
     * @param parent the main application
     */
    DemoMsgBoxWindow(final TApplication parent) {
        this(parent, TWindow.CENTERED | TWindow.RESIZABLE);
    }

    /**
     * Constructor.
     *
     * @param parent the main application
     * @param flags bitmask of MODAL, CENTERED, or RESIZABLE
     */
    DemoMsgBoxWindow(final TApplication parent, final int flags) {
        // Construct a demo window.  X and Y don't matter because it
        // will be centered on screen.
        super(parent, "Message Boxes", 0, 0, 60, 15, flags);

        int row = 1;

        // Add some widgets
        addLabel("Default OK message box", 1, row);
        addButton("Open O&K MB", 35, row,
            new TAction() {
                public void DO() {
                    getApplication().messageBox("OK MessageBox",
"This is an example of a OK MessageBox.  This is the\n" +
"default MessageBox.\n" +
"\n" +
"Note that the MessageBox text can span multiple\n" +
"lines.\n" +
"\n" +
"The default result (if someone hits the top-left\n" +
"close button) is OK.\n",
                        TMessageBox.Type.OK);
                }
            }
        );
        row += 2;

        addLabel("OK/Cancel message box", 1, row);
        addButton("O&pen OKC MB", 35, row,
            new TAction() {
                public void DO() {
                    getApplication().messageBox("OK/Cancel MessageBox",
"This is an example of a OK/Cancel MessageBox.\n" +
"\n" +
"Note that the MessageBox text can span multiple\n" +
"lines.\n" +
"\n" +
"The default result (if someone hits the top-left\n" +
"close button) is CANCEL.\n",
                        TMessageBox.Type.OKCANCEL);
                }
            }
        );
        row += 2;

        addLabel("Yes/No message box", 1, row);
        addButton("Open &YN MB", 35, row,
            new TAction() {
                public void DO() {
                    getApplication().messageBox("Yes/No MessageBox",
"This is an example of a Yes/No MessageBox.\n" +
"\n" +
"Note that the MessageBox text can span multiple\n" +
"lines.\n" +
"\n" +
"The default result (if someone hits the top-left\n" +
"close button) is NO.\n",
                        TMessageBox.Type.YESNO);
                }
            }
        );
        row += 2;

        addLabel("Yes/No/Cancel message box", 1, row);
        addButton("Ope&n YNC MB", 35, row,
            new TAction() {
                public void DO() {
                    getApplication().messageBox("Yes/No/Cancel MessageBox",
"This is an example of a Yes/No/Cancel MessageBox.\n" +
"\n" +
"Note that the MessageBox text can span multiple\n" +
"lines.\n" +
"\n" +
"The default result (if someone hits the top-left\n" +
"close button) is CANCEL.\n",
                        TMessageBox.Type.YESNOCANCEL);
                }
            }
        );
        row += 2;

        addLabel("Input box", 1, row);
        addButton("Open &input box", 35, row,
            new TAction() {
                public void DO() {
                    TInputBox in = getApplication().inputBox("Input Box",
"This is an example of an InputBox.\n" +
"\n" +
"Note that the InputBox text can span multiple\n" +
"lines.\n",
                        "some input text");
                    getApplication().messageBox("Your InputBox Answer",
                        "You entered: " + in.getText());
                }
            }
        );

        addButton("&Close Window", (getWidth() - 14) / 2, getHeight() - 4,
            new TAction() {
                public void DO() {
                    getApplication().closeWindow(DemoMsgBoxWindow.this);
                }
            }
        );
    }
}

/**
 * This is the main "demo" application window.  It makes use of the TTimer,
 * TProgressBox, TLabel, TButton, and TField widgets.
 */
class DemoMainWindow extends TWindow {

    // Timer that increments a number.
    private TTimer timer;

    // Timer label is updated with timer ticks.
    TLabel timerLabel;

    /*
    // The modal window is a more low-level example of controlling a window
    // "from the outside".  Most windows will probably subclass TWindow and
    // do this kind of logic on their own.
    private TWindow modalWindow;
    private void openModalWindow() {
        modalWindow = getApplication().addWindow("Demo Modal Window", 0, 0,
            58, 15, TWindow.Flag.MODAL);
        modalWindow.addLabel("This is an example of a very braindead modal window.", 1, 1);
        modalWindow.addLabel("Modal windows are centered by default.", 1, 2);
        modalWindow.addButton("&Close", (modalWindow.width - 8)/2,
            modalWindow.height - 4, &modalWindowClose);
    }
    private void modalWindowClose() {
        getApplication().closeWindow(modalWindow);
    }
     */

    /**
     * We need to override onClose so that the timer will no longer be called
     * after we close the window.  TTimers currently are completely unaware
     * of the rest of the UI classes.
     */
    @Override
    public void onClose() {
        getApplication().removeTimer(timer);
    }

    /**
     * Construct demo window.  It will be centered on screen.
     *
     * @param parent the main application
     */
    public DemoMainWindow(final TApplication parent) {
        this(parent, CENTERED | RESIZABLE);
    }

    // These are used by the timer loop.  They have to be at class scope so
    // that they can be accessed by the anonymous TAction class.
    int timerI = 0;
    TProgressBar progressBar;

    /**
     * Constructor.
     *
     * @param parent the main application
     * @param flags bitmask of MODAL, CENTERED, or RESIZABLE
     */
    private DemoMainWindow(final TApplication parent, final int flags) {
        // Construct a demo window.  X and Y don't matter because it will be
        // centered on screen.
        super(parent, "Demo Window", 0, 0, 60, 23, flags);

        int row = 1;

        // Add some widgets
        if (!isModal()) {
            addLabel("Message Boxes", 1, row);
            addButton("&MessageBoxes", 35, row,
                new TAction() {
                    public void DO() {
                        new DemoMsgBoxWindow(getApplication());
                    }
                }
            );
        }
        row += 2;

        addLabel("Open me as modal", 1, row);
        addButton("W&indow", 35, row,
            new TAction() {
                public void DO() {
                    new DemoMainWindow(getApplication(), MODAL);
                }
            }
        );

        row += 2;

        addLabel("Variable-width text field:", 1, row);
        addField(35, row++, 15, false, "Field text");

        addLabel("Fixed-width text field:", 1, row);
        addField(35, row, 15, true);
        row += 2;

        if (!isModal()) {
            addLabel("Radio buttons and checkboxes", 1, row);
            addButton("&Checkboxes", 35, row,
                new TAction() {
                    public void DO() {
                        new DemoCheckboxWindow(getApplication());
                    }
                }
            );
        }
        row += 2;

        /*
        if (!isModal()) {
            addLabel("Editor window", 1, row);
            addButton("Edito&r", 35, row,
                {
                    new TEditor(application, 0, 0, 60, 15);
                }
            );
        }
        row += 2;
         */

        if (!isModal()) {
            addLabel("Text areas", 1, row);
            addButton("&Text", 35, row,
                new TAction() {
                    public void DO() {
                        new DemoTextWindow(getApplication());
                    }
                }
            );
        }
        row += 2;

        /*
        if (!isModal()) {
            addLabel("Tree views", 1, row);
            addButton("Tree&View", 35, row,
                {
                    new DemoTreeViewWindow(application);
                }
            );
        }
        row += 2;

        if (!isModal()) {
            addLabel("Terminal", 1, row);
            addButton("Termi&nal", 35, row,
                {
                    getApplication().openTerminal(0, 0);
                }
            );
        }
        row += 2;
         */

        progressBar = addProgressBar(1, row, 22, 0);
        row++;
        timerLabel = addLabel("Timer", 1, row);
        timer = getApplication().addTimer(100, true,
            new TAction() {

                public void DO() {
                    timerLabel.setText(String.format("Timer: %d", timerI));
                    timerLabel.setWidth(timerLabel.getText().length());
                    if (timerI < 100) {
                        timerI++;
                    }
                    progressBar.setValue(timerI);
                    DemoMainWindow.this.setRepaint();
                }
            }
        );
    }
}

/**
 * The demo application itself.
 */
class DemoApplication extends TApplication {

    /**
     * Public constructor.
     *
     * @throws Exception if TApplication can't instantiate the Backend.
     */
    public DemoApplication() throws Exception {
        super(null, null);
        new DemoMainWindow(this);

        // Add the menus
        addFileMenu();
        addEditMenu();

        TMenu demoMenu = addMenu("&Demo");
        TMenuItem item = demoMenu.addItem(2000, "&Checkable");
        item.setCheckable(true);
        item = demoMenu.addItem(2001, "Disabled");
        item.setEnabled(false);
        item = demoMenu.addItem(2002, "&Normal");
        TSubMenu subMenu = demoMenu.addSubMenu("Sub-&Menu");
        item = demoMenu.addItem(2010, "N&ormal A&&D");

        item = subMenu.addItem(2000, "&Checkable (sub)");
        item.setCheckable(true);
        item = subMenu.addItem(2001, "Disabled (sub)");
        item.setEnabled(false);
        item = subMenu.addItem(2002, "&Normal (sub)");

        subMenu = subMenu.addSubMenu("Sub-&Menu");
        item = subMenu.addItem(2000, "&Checkable (sub)");
        item.setCheckable(true);
        item = subMenu.addItem(2001, "Disabled (sub)");
        item.setEnabled(false);
        item = subMenu.addItem(2002, "&Normal (sub)");

        addWindowMenu();

    }
}

/**
 * This class provides a simple demonstration of Jexer's capabilities.
 */
public class Demo1 {
    /**
     * Main entry point.
     *
     * @param args Command line arguments
     */
    public static void main(final String [] args) {
        try {
            DemoApplication app = new DemoApplication();
            app.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
