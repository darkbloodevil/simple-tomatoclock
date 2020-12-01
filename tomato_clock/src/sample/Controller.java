package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.lang.System.*;

public class Controller extends HBox implements Runnable {
    @FXML
    Button one_hour, forty_min, twenty5_min, fifteen_min;
    @FXML
    Button ten_min, five_min, three_min;
    @FXML
    Button restart_btn;
    @FXML
    Button log_btn;
    @FXML
    Label label_date;
    @FXML
    TextField item_text_field;

    ArrayList<Button> buttons;

    FXMLLoader fxmlLoader;

    long start_time;
    long end_time;

    String style_init = "-fx-background-color: white;\n" +
            "-fx-effect: dropshadow(gaussian, black, 10, 0, 2, 2);";
    String style_enter = "-fx-background-color: white;\n" +
            "-fx-effect: dropshadow(gaussian, gray, 10, 0, 2, 2);";
    String style_click = "-fx-background-color: white;\n" +
            "-fx-effect: innershadow(gaussian, gray, 15, 0, 0, 0);";
    String style_block = "-fx-background-color: white;\n" +
            "-fx-effect: innershadow(gaussian, black, 15, 0, 0, 0);";
    String restart_init_style = "-fx-background-color: ghostwhite;\n" +
            "-fx-font-family:\"Ink Free\";\n" +
            "-fx-effect: dropshadow(gaussian, black, 10, 0, 0, 0);";
    String restart_style_block = "-fx-background-color: ghostwhite;\n" +
            "-fx-font-family:\"Ink Free\";\n" +
            "-fx-effect: innershadow(gaussian, black, 15, 0, 0, 0);";
    String restart_style_enter = "-fx-background-color: ghostwhite;\n" +
            "-fx-font-family:\"Ink Free\";" +
            "-fx-effect: dropshadow(gaussian, gray, 10, 0, 0, 0);";
    String restart_style_click = "-fx-background-color: ghostwhite;\n" +
            "-fx-font-family:\"Ink Free\";" +
            "-fx-effect: innershadow(gaussian, gray, 10, 0, 0, 0);";
    boolean start, close_program;
    Thread t1;
    Stage stage;
    String program_start_time;
    ArrayList<clockLog> clock_logs;
    clockLog clock_now;
    String package_path;
    public Controller(Stage primaryStage) {
        load_fxml();
        btn_init();
        restart_init();
        log_init();
        t1 = new Thread(this);
        this.stage = primaryStage;
        stage.setResizable(true);
        t1.start();
        package_path=System.getProperty("user.dir");
        primaryStage.getIcons().add(new Image
         (String.format("file:%s\\resource\\icon1.png",
                 package_path)));
        stage.setTitle("TOMATO CLOCK");
        primaryStage.setOnCloseRequest(event -> close_program = true);
        date_init();
    }

    void date_init() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        program_start_time = sdf.format(date);
        out.println("starts at " + program_start_time);
        this.clock_logs = new ArrayList<>();
    }

    void load_fxml() {
        fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    void btn_init() {
        this.buttons = new ArrayList<>();
        this.buttons.add(one_hour);
        this.buttons.add(forty_min);
        this.buttons.add(twenty5_min);
        this.buttons.add(fifteen_min);
        this.buttons.add(ten_min);
        this.buttons.add(five_min);
        this.buttons.add(three_min);
        for (Button btn : buttons) {
            btn.setStyle(style_init);
            btn.setOnAction(this::btn_on_action);
            btn.setOnMouseEntered(this::btn_on_enter);
            btn.setOnMousePressed(this::btn_on_press);
            btn.setOnMouseExited(this::btn_on_exit);
        }
    }

    void restart_init() {
        restart_btn.setStyle(restart_style_block);
        restart_btn.setOnAction(event -> {
            if (start) {
                end_clock();
                clock_now.failed();
            }
        });
        restart_btn.setOnMouseEntered(event -> {
            if (start) restart_btn.setStyle(restart_style_enter);
        });
        restart_btn.setOnMousePressed(event -> {
            if (start)
                restart_btn.setStyle(restart_style_click);
        });
        restart_btn.setOnMouseExited(event -> {
            if (start)
                restart_btn.setStyle(restart_init_style);
        });
    }

    void log_init() {
        String log_init_style = "-fx-background-color: whitesmoke ;\n" +
                "-fx-font-family:\"Ink Free\";\n" +
                "-fx-effect: dropshadow(gaussian, black, 5, 0, 0, 0);" +
                "-fx-font-size:16px;";
        String log_enter_style =
                "-fx-background-color: whitesmoke ;" +
                        "-fx-font-family:\"Ink Free\";" +
                        "-fx-font-size:16px;" +
                        "-fx-effect: dropshadow(gaussian, grey, 5, 0, 0, 0);";
        String restart_style_click =
                "-fx-background-color: whitesmoke ;" +
                        "-fx-font-family:\"Ink Free\";" +
                        "-fx-font-size:16px;" +
                        "-fx-effect: innershadow(gaussian, gray, 8, 0, 0, 0);";
        log_btn.setStyle(log_init_style);
        log_btn.setOnAction(event -> dialog_func());
        log_btn.setOnMouseEntered(event -> log_btn.setStyle(log_enter_style));
        log_btn.setOnMousePressed(event -> log_btn.setStyle(restart_style_click));
        log_btn.setOnMouseReleased(event -> log_btn.setStyle(log_enter_style));
        log_btn.setOnMouseExited(event -> log_btn.setStyle(log_init_style));
    }

    void dialog_func() {
        if (clock_now != null)
            clock_now.setMission(item_text_field.getText());

        Dialog dialog = new Dialog();
        ButtonType yes_btn = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        dialog.setTitle("Log Info");
        dialog.getDialogPane().getButtonTypes().add(yes_btn);
        String logs = log_text();
        TextArea textArea = new TextArea(logs);
        textArea.setEditable(false);
        dialog.getDialogPane().setContent(textArea);
        dialog.showAndWait();
    }

    String log_text() {
        String ling_str = "\n\n=========--------------------%16s\t--------------------=========\n\n";
        String latest_formatter = "| KIND\t\t%s\n| STATUS\t\t%s\n| MISSION\t%s\n| START TIME\t%s";
        StringBuilder logs = new StringBuilder(program_start_time);
        if (clock_now != null) {
            logs.append(String.format(ling_str, "LATEST")).append(String.format(latest_formatter,
                    clock_now.getClock_kind(), clock_now.getClock_status(),
                    clock_now.getMission(), clock_now.getStart_time()));
        }
        HashMap<String, Integer> success_clocks = new HashMap<>();
        HashMap<String, Integer> fail_clocks = new HashMap<>();
        for (clockLog temp_log : clock_logs) {
            HashMap<String, Integer> tempMap = new HashMap<>();
            if (temp_log.getClock_status().equals(clockLog.SUCCESS_END)) {
                tempMap = success_clocks;
            } else if (temp_log.getClock_status().equals(clockLog.FAILED)) {
                tempMap = fail_clocks;
            }
            if (tempMap.containsKey(temp_log.getClock_kind())) {
                int counts = tempMap.get(temp_log.getClock_kind()) + 1;
                tempMap.put(temp_log.getClock_kind(), counts);
            } else {
                tempMap.put(temp_log.getClock_kind(), 1);
            }
        }

        logs.append(String.format(ling_str, "SUCCEEDED"));
        String simple_formatter = "| %s\t%d\t\t";
        int column_ = 0;
        for (String temp : success_clocks.keySet()) {
            logs.append(String.format(simple_formatter, temp, success_clocks.get(temp)));
            column_++;
            if (column_ % 2 == 0)
                logs.append("\n|\t\t\t\t|\n");
        }
        logs.append(String.format(ling_str, "FAILED"));
        column_ = 0;
        for (String temp : fail_clocks.keySet()) {
            logs.append(String.format(simple_formatter, temp, fail_clocks.get(temp)));
            column_++;
            if (column_ % 2 == 0)
                logs.append("\n|\t\t\t\t|\n");
        }

        logs.append("\n").append(String.format(ling_str, "DETAIL"));
        String detail_formatter = "| START TIME\t%s\t\t| END TIME\t%s\n" +
                "| KIND\t%s\t| STATUS\t%s\t\t| MISSION\t%s\n";
        for (clockLog cl : clock_logs) {
            logs.append(String.format(detail_formatter, cl.getStart_time(),
                    cl.getEnd_time(), cl.getClock_kind(), cl.getClock_status(),
                    cl.getMission())).append("|\n");
        }
        return logs.toString();
    }

    void block_btns() {
        for (Button btn : buttons) {
            if (start)
                btn.setStyle(style_block);
            else btn.setStyle(style_init);
        }
        if (start) restart_btn.setStyle(restart_init_style);
    }

    @FXML
    void btn_on_action(ActionEvent ae) {
        if (!start) {
            start = true;
            block_btns();
            start_time = currentTimeMillis();
            end_time = start_time + get_time_duration(((Button) ae.getSource()).getText());
            clock_now = new clockLog(((Button) ae.getSource()).getText(), item_text_field.getText());
        }
        out.println(((Button) ae.getSource()).getText());
    }

    @FXML
    void btn_on_enter(MouseEvent ae) {
        if (!start) get_btn(((Button) ae.getSource()).getText()).setStyle(style_enter);
    }

    @FXML
    void btn_on_press(MouseEvent ae) {
        if (!start) get_btn(((Button) ae.getSource()).getText()).setStyle(style_click);
    }

    @FXML
    void btn_on_exit(MouseEvent ae) {
        if (!start) get_btn(((Button) ae.getSource()).getText()).setStyle(style_init);
    }

    Button get_btn(String text) {
        Button temp_btn = null;
        for (Button btn : buttons) {
            if (btn.getText().equals(text)) {
                temp_btn = btn;
                break;
            }
        }
        return temp_btn;
    }

    long get_time_duration(String text) {
        if (text.equals(one_hour.getText())) {
            return 60 * 60 * 1000;
        } else if (text.equals(forty_min.getText())) {
            return 40 * 60 * 1000;
        } else if (text.equals(twenty5_min.getText())) {
            return 25 * 60 * 1000;
        } else if (text.equals(fifteen_min.getText())) {
            return 15 * 60 * 1000;
        } else if (text.equals(ten_min.getText())) {
            return 10 * 60 * 1000;
        } else if (text.equals(five_min.getText())) {
            return 5 * 60 * 1000;
        } else if (text.equals(three_min.getText())) {
            return 3 * 60 * 1000;
        } else return 0;
    }

    void change_date_label(long time_left) {
        String date_str = String.format("%02d:%02d", time_left / (60000), (time_left % (60000)) / 1000);
        label_date.setText(date_str);
    }

    @FXML
    void lose_focus() {
        restart_btn.requestFocus();
        out.println(item_text_field.getText());
    }

    void end_clock() {
        stage.requestFocus();
        this.clock_logs.add(clock_now);
        java.awt.Toolkit.getDefaultToolkit().beep();
        start = false;
        block_btns();
        this.restart_btn.setStyle(restart_style_block);
        change_date_label(0);
        this.clock_now.setMission(this.item_text_field.getText());
        save(package_path+"\\log_saves\\"+program_start_time
                .replace(":","_")
                .replace(" ","_")
                .replace("-","_")
                +".txt",log_text());
    }

    void save(String file_name,String content){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file_name))) {
            //一次写一行
            bw.write(content);
            //关闭流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!close_program) {
            try {
                Platform.runLater(() -> {
                    if (start) {
                        long durating_temp = end_time - currentTimeMillis();
                        if (durating_temp > 0)
                            change_date_label(durating_temp);
                        else {
                            this.clock_now.succeeded();
                            end_clock();
                        }
                    }
                });
                Thread.sleep(250);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}

class clockLog {
    static final String RUNNING = "running", SUCCESS_END = "succeeded", FAILED = "failed";
    private String clock_status;
    private final String clock_kind;
    private final String start_time;
    private String end_time;
    private String mission;

    public clockLog(String clock_kind, String mission) {
        clock_status = clockLog.RUNNING;
        this.clock_kind = clock_kind;
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        start_time = sdf.format(date);
        this.mission = mission;
    }

    public void failed() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        end_time = sdf.format(date);
        clock_status = clockLog.FAILED;
    }

    public void succeeded() {
        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");// a为am/pm的标记
        Date date = new Date();// 获取当前时间
        end_time = sdf.format(date);
        clock_status = clockLog.SUCCESS_END;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getClock_status() {
        return clock_status;
    }

    public String getClock_kind() {
        return clock_kind;
    }


    public String getEnd_time() {
        return end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getMission() {
        return mission;
    }
}