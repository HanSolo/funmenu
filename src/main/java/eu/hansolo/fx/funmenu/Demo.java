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

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Scene;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;


/**
 * User: hansolo
 * Date: 13.08.16
 * Time: 12:30
 */
public class Demo extends Application {
    private FunMenu funMenu;

    @Override public void init() {
        funMenu = new FunMenu();

        funMenu.setItem1IconCode(MaterialDesign.MDI_CALENDAR);
        funMenu.setItem2IconCode(Material.GROUP);
        funMenu.setItem3IconCode(Material.MAIL);
        funMenu.setItem4IconCode(Material.FAVORITE);

        funMenu.setOnItem1MousePressed(e -> System.out.println("Icon 1 pressed"));
        funMenu.setOnItem2MousePressed(e -> System.out.println("Icon 2 pressed"));
        funMenu.setOnItem3MousePressed(e -> System.out.println("Icon 3 pressed"));
        funMenu.setOnItem4MousePressed(e -> System.out.println("Icon 4 pressed"));
    }

    @Override public void start(Stage stage) {
        StackPane pane = new StackPane(funMenu);
        pane.setPadding(new Insets(20));
        pane.setBackground(new Background(new BackgroundFill(Color.web("#825ECB"), CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);

        stage.setTitle("FunMenu");
        stage.setScene(scene);
        stage.show();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
