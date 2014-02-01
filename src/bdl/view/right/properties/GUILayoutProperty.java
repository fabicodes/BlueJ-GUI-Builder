package bdl.view.right.properties;

import bdl.build.GObject;
import java.text.DecimalFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

public class GUILayoutProperty implements PanelProperty {

    private GObject gObj;
    private Node node;
    private TextField layoutX;
    private TextField layoutY;
    private DecimalFormat format = new DecimalFormat("#.##");

    public GUILayoutProperty(final GObject gObj, String name, final String getter, final String setter, String fxml, String defaultValue, GridPane gp, int row, Node settingsNode) {
        this.node = (Node)gObj;
        this.gObj = gObj;
        int row1 = row;
        int row2 = row + 1;

        Label lx = new Label("LayoutX:");
        Label ly = new Label("LayoutY:");
        
        gp.add(lx, 0, row1);
        gp.add(ly, 0, row2);
        layoutX = new TextField();
        layoutY = new TextField();

        //Grab value from settingsNode if given
        if (settingsNode != null) {
            node.setLayoutX(settingsNode.getLayoutX());
            node.setLayoutY(settingsNode.getLayoutY());
        }

        layoutX.setText(format.format(node.getLayoutX()));
        layoutY.setText(format.format(node.getLayoutY()));

        gp.add(layoutX, 1, row1);
        gp.add(layoutY, 1, row2);

        node.layoutXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                layoutX.setText(format.format(number2));
            }
        });
        node.layoutYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                layoutY.setText(format.format(number2));
            }
        });

        //Upon losing focus, save to the GObject
        layoutX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                if (!aBoolean2) {
                    try {
                        double value = Double.parseDouble(layoutX.getText());
                        node.setLayoutX(value);
                    } catch (Exception e) {
                        //Reset value
                        layoutX.setText(format.format(node.getLayoutX()));
                    }
                }
            }
        });
        layoutY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                if (!aBoolean2) {
                    try {
                        double value = Double.parseDouble(layoutY.getText());
                        node.setLayoutY(value);
                    } catch (Exception e) {
                        //Reset value
                        layoutY.setText(format.format(node.getLayoutY()));
                    }
                }
            }
        });
        
        layoutX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        double value = Double.parseDouble(layoutX.getText());
                        node.setLayoutX(value);
                    } catch (Exception e) {
                        //Reset value
                        layoutX.setText(format.format(node.getLayoutX()));
                    }
                }
            }
        });
        
        layoutY.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        double value = Double.parseDouble(layoutY.getText());
                        node.setLayoutY(value);
                    } catch (Exception e) {
                        //Reset value
                        layoutY.setText(format.format(node.getLayoutY()));
                    }
                }
            }
        });
    }

    @Override
    public String getJavaCode() {
        return gObj.getFieldName() + ".setLayoutX(" + node.getLayoutX() + ");\n" +
                gObj.getFieldName() + ".setLayoutY(" + node.getLayoutY() + ");";
    }

    @Override
    public String getFXMLCode() {
        return "layoutX=\"" + node.getLayoutX() + "\" layoutY=\"" + node.getLayoutY() + "\"";
    }
}