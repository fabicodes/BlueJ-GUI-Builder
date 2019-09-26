package bdl;

import bdl.controller.Controller;
import bluej.extensions.*;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class BlueJConnector extends Extension implements PackageListener, Interface {
    private static BlueJConnector instance;
    private Controller controller;
    private BlueJ blueJ;
    private Main main;

    public static BlueJConnector getInstance() {
        if (instance == null) {
            instance = new BlueJConnector();
        }
        return instance;
    }

    /*
     * This method must decide if this Extension is compatible with the
     * current release of the BlueJ Extensions API
     */
    @Override
    public boolean isCompatible() {
        return true;
    }

    /*
     * When this method is called, the extension may start its work.
     */
    @Override
    public void startup(BlueJ blueJ) {
        this.blueJ = blueJ;
        blueJ.addPackageListener(this);
        Main main = new Main();
        main.setInterface(this);
        blueJ.setMenuGenerator(new MenuGenerator() {

            @Override
            public JMenuItem getToolsMenuItem(BPackage bp) {
                return new JMenuItem(new AbstractAction() {
                    {
                        putValue(AbstractAction.NAME, "Open GUI Builder");
                    }

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        EventQueue.invokeLater(() -> {
                            new JFXPanel();
                            Platform.setImplicitExit(false);
                            Platform.runLater(() -> {
                                try {
                                    main.start(new Stage());
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        });
                    }
                });
            }
        });
    }

    /*
     * A package has been opened. Print the name of the project it is part of.
     * System.out is redirected to the BlueJ debug log file.
     * The location of this file is given in the Help/About BlueJ dialog box.
     */
    @Override
    public void packageOpened(PackageEvent packageEvent) {
        instance = this;
        try {
            System.out.println("Project " + packageEvent.getPackage().getProject().getName() + " opened.");
        } catch (ExtensionException e) {
            System.out.println("Project closed by BlueJ");
        }
    }

    /*
     * A package is closing.
     */
    @Override
    public void packageClosing(PackageEvent packageEvent) {

    }

    @Override
    public String getDescription() {
        return "This is a GUI-Builder, which was further developed within the SoC4S project to enable and facilitate the development of a GUI for students in the BlueJ development environment.";
    }

    @Override
    public URL getURL() {
        try {
            return new URL("https://github.com/fabicodes/BlueJ-GUI-Builder");
        } catch (MalformedURLException e) {
            System.out.println("Fail: " + e.getStackTrace().toString());
            return null;
        }
    }

    @Override
    public void terminate() {
        super.terminate();
    }

    public static void main(String[] args) {
        Main.launch(args);
    }

    @Override
    public String getName() {
        return "BlueJ JavaFX GUI Builder";
    }

    @Override
    public String getVersion() {
        return "0.0.1";
    }

    /*
    Starting with Interface Methods
     */

    @Override
    public void setGUIBuilderController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public File getWorkingDirectory() {
        try {
            return blueJ.getCurrentPackage().getDir();
        } catch (ProjectNotOpenException | PackageNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean isEditingGUI() {
        return false;
    }

    @Override
    public String getOpenGUIName() {
        return null;
    }

    @Override
    public File getOpenGUIFile() {
        return null;
    }

    @Override
    public void notifyRemovedClass(Object ct) {

    }

    @Override
    public void markAsDirty() {

    }

    @Override
    public void recompileOpenGUI() {

    }

    @Override
    public File getUserPrefDir() {
        return null;
    }

    @Override
    public void sendMessageToBlueJ(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    @Override
    public boolean isBlueJAttached() {
        return blueJ != null;
    }

    @Override
    public void show() {
        controller.showStage();
    }

    @Override
    public void hide() {

    }
}
