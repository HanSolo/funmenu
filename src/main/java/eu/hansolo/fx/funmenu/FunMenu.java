/*
 * Copyright (c) 2016 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.funmenu;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.DefaultProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import org.kordamp.ikonli.Ikon;


/**
 * User: hansolo
 * Date: 13.08.16
 * Time: 12:15
 */
@DefaultProperty("children")
public class FunMenu extends Region {
    private static final double PREFERRED_WIDTH  = 400;
    private static final double PREFERRED_HEIGHT = 70;
    private static final double MINIMUM_WIDTH    = 200;
    private static final double MINIMUM_HEIGHT   = 35;
    private static final double MAXIMUM_WIDTH    = 1024;
    private static final double MAXIMUM_HEIGHT   = 1024;
    private static double                   aspectRatio;
    private        boolean                  keepAspect;
    private        double                   size;
    private        double                   itemSize;
    private        double                   width;
    private        double                   height;
    private        Region                   menu;
    private        Region                   button;
    private        Region                   cross;
    private        FunMenuItem              item1;
    private        FunMenuItem              item2;
    private        FunMenuItem              item3;
    private        FunMenuItem              item4;
    private        Pane                     pane;
    private        DropShadow               shadow;
    private        Paint                    backgroundPaint;
    private        Paint                    borderPaint;
    private        double                   borderWidth;
    private        Timeline                 timeline;
    private        EventHandler<MouseEvent> closeHandler;

    private BooleanProperty open;


    // ******************** Constructors **************************************
    public FunMenu() {
        getStylesheets().add(FunMenu.class.getResource("funmenu.css").toExternalForm());
        getStyleClass().add("fun-menu");
        aspectRatio     = PREFERRED_HEIGHT / PREFERRED_WIDTH;
        keepAspect      = true;
        backgroundPaint = Color.TRANSPARENT;
        borderPaint     = Color.TRANSPARENT;
        borderWidth     = 0d;
        open            = new BooleanPropertyBase(false) {
            @Override protected void invalidated() {
                if (!isOpen()) {
                    // close menu
                    KeyValue kvRotate     = new KeyValue(cross.rotateProperty(), 0, Interpolator.EASE_BOTH);
                    KeyValue kvSize       = new KeyValue(menu.prefWidthProperty(), height, Interpolator.EASE_BOTH);
                    KeyValue item1X       = new KeyValue(item1.layoutXProperty(), (width - itemSize) * 0.5, Interpolator.EASE_BOTH);
                    KeyValue item1Opacity = new KeyValue(item1.opacityProperty(), 0, Interpolator.EASE_BOTH);
                    KeyValue item2X       = new KeyValue(item2.layoutXProperty(), (width - itemSize) * 0.5, Interpolator.EASE_BOTH);
                    KeyValue item2Opacity = new KeyValue(item2.opacityProperty(), 0, Interpolator.EASE_BOTH);
                    KeyValue item3X       = new KeyValue(item3.layoutXProperty(), (width - itemSize) * 0.5, Interpolator.EASE_BOTH);
                    KeyValue item3Opacity = new KeyValue(item3.opacityProperty(), 0, Interpolator.EASE_BOTH);
                    KeyValue item4X       = new KeyValue(item4.layoutXProperty(), (width - itemSize) * 0.5, Interpolator.EASE_BOTH);
                    KeyValue item4Opacity = new KeyValue(item4.opacityProperty(), 0, Interpolator.EASE_BOTH);

                    KeyFrame kf0          = new KeyFrame(Duration.millis(50), kvRotate);
                    KeyFrame kf1          = new KeyFrame(Duration.millis(150), kvSize, item1X, item1Opacity, item2X, item2Opacity, item3X, item3Opacity, item4X, item4Opacity);

                    timeline.getKeyFrames().setAll(kf0, kf1);
                } else {
                    // open menu
                    KeyValue kvRotate     = new KeyValue(cross.rotateProperty(), 45, Interpolator.EASE_BOTH);
                    KeyValue kvSize       = new KeyValue(menu.prefWidthProperty(), width, Interpolator.EASE_BOTH);
                    KeyValue item1X       = new KeyValue(item1.layoutXProperty(), width * 0.0225, Interpolator.EASE_BOTH);
                    KeyValue item1Opacity = new KeyValue(item1.opacityProperty(), 1, Interpolator.EASE_BOTH);
                    KeyValue item2X       = new KeyValue(item2.layoutXProperty(), width * 0.21, Interpolator.EASE_BOTH);
                    KeyValue item2Opacity = new KeyValue(item2.opacityProperty(), 1, Interpolator.EASE_BOTH);
                    KeyValue item3X       = new KeyValue(item3.layoutXProperty(), width * 0.67, Interpolator.EASE_BOTH);
                    KeyValue item3Opacity = new KeyValue(item3.opacityProperty(), 1, Interpolator.EASE_BOTH);
                    KeyValue item4X       = new KeyValue(item4.layoutXProperty(), width * 0.8575, Interpolator.EASE_BOTH);
                    KeyValue item4Opacity = new KeyValue(item4.opacityProperty(), 1, Interpolator.EASE_BOTH);

                    KeyFrame kf0          = new KeyFrame(Duration.millis(50), kvRotate);
                    KeyFrame kf1          = new KeyFrame(Duration.millis(150), kvSize, item1X, item1Opacity, item2X, item2Opacity, item3X, item3Opacity, item4X, item4Opacity);

                    timeline.getKeyFrames().setAll(kf0, kf1);
                }
                timeline.play();
            }
            @Override public Object getBean() { return FunMenu.this; }
            @Override public String getName() { return "open"; }
        };
        timeline        = new Timeline();
        closeHandler    = e -> setOpen(false);
        init();
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void init() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 ||
            Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        if (Double.compare(getMinWidth(), 0.0) <= 0 || Double.compare(getMinHeight(), 0.0) <= 0) {
            setMinSize(MINIMUM_WIDTH, MINIMUM_HEIGHT);
        }

        if (Double.compare(getMaxWidth(), 0.0) <= 0 || Double.compare(getMaxHeight(), 0.0) <= 0) {
            setMaxSize(MAXIMUM_WIDTH, MAXIMUM_HEIGHT);
        }
    }

    private void initGraphics() {
        shadow = new DropShadow(BlurType.TWO_PASS_BOX, Color.rgb(0, 0, 0, 0.5), 5, 0, 0, 5);

        menu = new Region();
        //menu.setEffect(shadow);
        menu.getStyleClass().add("menu");

        item1 = new FunMenuItem();
        item1.setOpacity(0);
        item1.getStyleClass().add("menu-item");

        item2 = new FunMenuItem();
        item2.setOpacity(0);
        item2.getStyleClass().add("menu-item");

        item3 = new FunMenuItem();
        item3.setOpacity(0);
        item3.getStyleClass().add("menu-item");

        item4 = new FunMenuItem();
        item4.setOpacity(0);
        item4.getStyleClass().add("menu-item");

        button = new Region();
        button.setPickOnBounds(true);
        button.getStyleClass().add("rotating-button");

        cross = new Region();
        cross.setMouseTransparent(true);
        cross.getStyleClass().add("cross");

        pane = new Pane(menu, item1, item2, item3, item4, button, cross);
        pane.getStyleClass().add("fun-menu");
        pane.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBorder(new Border(new BorderStroke(borderPaint, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(borderWidth))));

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        button.setOnMousePressed(e -> setOpen(!isOpen()));
        timeline.setOnFinished(o -> {

        });
    }


    // ******************** Methods *******************************************
    @Override public void layoutChildren() {
        super.layoutChildren();
        menu.relocate((width - menu.getPrefWidth()) * 0.5, 0);
    }

    public boolean isOpen() { return open.get(); }
    public void setOpen(final boolean OPEN) { open.set(OPEN); }
    public BooleanProperty openProperty() { return open; }

    public Ikon getItem1IconCode() { return item1.getIconCode(); }
    public void setItem1IconCode(final Ikon CODE) {
        if (null == CODE) return;
        item1.setIconCode(CODE);
        item1.getIcon().setOnMousePressed(closeHandler);
    }

    public Ikon getItem2IconCode() { return item2.getIconCode(); }
    public void setItem2IconCode(final Ikon CODE) {
        if (null == CODE) return;
        item2.setIconCode(CODE);
        item2.getIcon().setOnMousePressed(closeHandler);
    }

    public Ikon getItem3IconCode() { return item3.getIconCode(); }
    public void setItem3IconCode(final Ikon CODE) {
        if (null == CODE) return;
        item3.setIconCode(CODE);
        item3.getIcon().setOnMousePressed(closeHandler);
    }

    public Ikon getItem4IconCode() { return item4.getIconCode(); }
    public void setItem4IconCode(final Ikon CODE) {
        if (null == CODE) return;
        item4.setIconCode(CODE);
        item4.getIcon().setOnMousePressed(closeHandler);
    }

    public void setOnItem1MousePressed(final EventHandler<MouseEvent> HANDLER) { item1.setOnMousePressed(HANDLER); }
    public void setOnItem2MousePressed(final EventHandler<MouseEvent> HANDLER) { item2.setOnMousePressed(HANDLER); }
    public void setOnItem3MousePressed(final EventHandler<MouseEvent> HANDLER) { item3.setOnMousePressed(HANDLER); }
    public void setOnItem4MousePressed(final EventHandler<MouseEvent> HANDLER) { item4.setOnMousePressed(HANDLER); }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }


    // ******************** Resizing ******************************************
    private void resize() {
        width  = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size   = width < height ? width : height;

        if (keepAspect) {
            if (aspectRatio * width > height) {
                width = 1 / (aspectRatio / height);
            } else if (1 / (aspectRatio / height) > width) {
                height = aspectRatio * width;
            }
        }

        if (width > 0 && height > 0) {
            pane.setMaxSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            itemSize = 0.68571429 * height;
            item1.setPrefSize(itemSize, itemSize);
            item2.setPrefSize(itemSize, itemSize);
            item3.setPrefSize(itemSize, itemSize);
            item4.setPrefSize(itemSize, itemSize);

            double itemY = (height - itemSize) * 0.5;

            if (isOpen()) {
                menu.setPrefSize(width, height);
                item1.relocate(width * 0.0225, itemY);
                item2.relocate(width * 0.21, itemY);
                item3.relocate(width * 0.67, itemY);
                item4.relocate(width * 0.8575, itemY);
            } else {
                menu.setPrefSize(height, height);
                item1.relocate((width - itemSize) * 0.5, itemY);
                item2.relocate((width - itemSize) * 0.5, itemY);
                item3.relocate((width - itemSize) * 0.5, itemY);
                item4.relocate((width - itemSize) * 0.5, itemY);
            }
            menu.relocate((width - menu.getPrefWidth()) * 0.5, 0);


            button.setPrefSize(0.85714286 * height, 0.85714286 * height);
            button.relocate((width - button.getPrefWidth()) * 0.5, (height - button.getPrefHeight()) * 0.5);

            cross.setPrefSize(0.375 * button.getPrefWidth(), 0.375 * button.getPrefHeight());
            cross.relocate((width - cross.getPrefWidth()) * 0.5, (height - cross.getPrefHeight()) * 0.5);

            redraw();
        }
    }

    private void redraw() {
        pane.setBackground(new Background(new BackgroundFill(backgroundPaint, CornerRadii.EMPTY, Insets.EMPTY)));
        pane.setBorder(new Border(new BorderStroke(borderPaint, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(borderWidth / PREFERRED_WIDTH * size))));

    }
}
