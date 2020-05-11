package universitysystem.controllers;

import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import universitysystem.animations.Shake;
import universitysystem.model.Const;
import universitysystem.model.structs.BaseStruct;
import universitysystem.model.structs.PeriodStruct;
import universitysystem.model.structs.PostStruct;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DateDialog {

    static public enum WorkState {
        ONLY_STRUCT,
        STRUCT_AND_DATES,
        STRUCT_AND_DATES_AND_SALARY
    }

    static WorkState workState;

    public static PeriodStruct createDialogPeriod(String headerText, String tableName) {
        // Create the custom dialog.

        Dialog<Pair<BaseStruct, Pair<LocalDate, LocalDate>>> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Const.IMAGE_URL));

        dialog.setHeaderText(headerText);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the dateBegin and dateEnd labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<BaseStruct> comboBox = new ComboBox<BaseStruct>
                (BaseStruct.getForComboBox(BaseStruct.getBaseStructs(tableName)));
        DatePicker dateBegin = new DatePicker();
        dateBegin.setPromptText("Начальная дата");
        DatePicker dateEnd = new DatePicker();
        dateEnd.setPromptText("Конечная дата");
        TextField textField = new TextField();

        grid.add(new Label("Выберете:"), 0, 0);
        grid.add(comboBox, 1, 0);


        grid.add(new Label("Дата начала:"), 0, 1);
        grid.add(dateBegin, 1, 1);

        grid.add(new Label("Дата конца(если есть):"), 0, 2);
        grid.add(dateEnd, 1, 2);


        dialog.getDialogPane().setContent(grid);

        // Request focus on the dateBegin field by default.
        Platform.runLater(comboBox::requestFocus);

        // Convert the result to a dateBegin-dateEnd-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton != ButtonType.CANCEL) {
                if (dateBegin.getValue() != null && comboBox.getValue() != null) {
                    return new Pair<>(comboBox.getValue(), new Pair<>(dateBegin.getValue(), dateEnd.getValue()));
                }
            }
            return null;
        });

        Optional<Pair<BaseStruct, Pair<LocalDate, LocalDate>>> result = dialog.showAndWait();
        if (result.isPresent()) {
            return new PeriodStruct(
                    result.get().getKey(),
                    result.get().getValue().getKey(),
                    result.get().getValue().getValue());
        }

        return null;
    }

    public static PostStruct createDialogForPost(String headerText, String tableName) {
        // Create the custom dialog.

        Dialog<PostStruct> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Const.IMAGE_URL));

        dialog.setHeaderText(headerText);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the dateBegin and dateEnd labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<BaseStruct> comboBox = new ComboBox<BaseStruct>
                (BaseStruct.getForComboBox(BaseStruct.getBaseStructs(tableName)));
        DatePicker dateBegin = new DatePicker();
        dateBegin.setPromptText("Начальная дата");
        DatePicker dateEnd = new DatePicker();
        dateEnd.setPromptText("Конечная дата");
        TextField textField = new TextField();

        grid.add(new Label("Выберете:"), 0, 0);
        grid.add(comboBox, 1, 0);


        grid.add(new Label("Дата начала:"), 0, 1);
        grid.add(dateBegin, 1, 1);

        grid.add(new Label("Дата конца(если есть):"), 0, 2);
        grid.add(dateEnd, 1, 2);

        grid.add(new Label("Зарплата:"), 0, 3);
        grid.add(textField, 1, 3);


        dialog.getDialogPane().setContent(grid);

        // Request focus on the dateBegin field by default.
        Platform.runLater(comboBox::requestFocus);

        // Convert the result to a dateBegin-dateEnd-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton != ButtonType.CANCEL) {
                Double salary = null;
                try {
                    salary = Double.parseDouble(textField.getText());

                    if (dateBegin.getValue() != null && comboBox.getValue() != null && salary != null) {
                        PostStruct postStruct = new PostStruct(
                                comboBox.getValue(),
                                dateBegin.getValue(),
                                dateEnd.getValue(),
                                salary);

                        return postStruct;
                    }
                } catch (NumberFormatException e) {
                    textField.setText("0");
                }
            }
            return null;
        });
        Optional<PostStruct> res = dialog.showAndWait();
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    public static BaseStruct createDialogBase(String headerText, String tableName) {
        // Create the custom dialog.

        Dialog<BaseStruct> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Const.IMAGE_URL));

        dialog.setHeaderText(headerText);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the dateBegin and dateEnd labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ComboBox<BaseStruct> comboBox = new ComboBox<BaseStruct>
                (BaseStruct.getForComboBox(BaseStruct.getBaseStructs(tableName)));

        grid.add(new Label("Выберете:"), 0, 0);
        grid.add(comboBox, 1, 0);


        dialog.getDialogPane().setContent(grid);

        // Request focus on the dateBegin field by default.
        Platform.runLater(comboBox::requestFocus);

        // Convert the result to a dateBegin-dateEnd-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton != ButtonType.CANCEL) {
                Double salary = null;

                if (comboBox.getValue() != null) {
                    return comboBox.getValue();
                }
            }
            return null;
        });

        Optional<BaseStruct> res = dialog.showAndWait();
        if (res.isPresent()) {
            return res.get();
        }
        return null;

    }

    public static Integer createDialogGetYear(String headerText, Set<Integer> years) {
        // Create the custom dialog.

        if (years == null) {
            years = new HashSet<>();
        }
        Set<Integer> finalYears = years;

        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Choice Dialog");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Const.IMAGE_URL));

        dialog.setHeaderText(headerText);

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Подтвердить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the dateBegin and dateEnd labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField textField = new TextField();


        grid.add(new Label("Введите год:"), 0, 0);
        grid.add(textField, 1, 0);


        dialog.getDialogPane().setContent(grid);

        // Request focus on the dateBegin field by default.
        Platform.runLater(textField::requestFocus);

        // Convert the result to a dateBegin-dateEnd-pair when the login button is clicked.

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton != ButtonType.CANCEL) {
                if (!textField.getText().trim().equals("")) {
                    try {
                        int year = Integer.parseInt(textField.getText());
                        if (year > 0 && year <= (LocalDate.now().getYear()) && !finalYears.contains(year)) {
                            return year;
                        }
                        Alert alert = new Alert(Alert.AlertType.WARNING, "Неверный текст");
                        alert.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        });

        Optional<Integer> res = dialog.showAndWait();
        if (res.isPresent()) {
            return res.get();
        }
        return null;

    }
}

