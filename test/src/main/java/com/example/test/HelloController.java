package com.example.test;

import com.example.test.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HelloController {
    public TextField txtName;
    public TextField txtNum;
    public TextField txtPhone;
    public DatePicker txtDate;
    @FXML
    public RadioButton man;
    @FXML
    public RadioButton woman;

    public static StudentDaoImpl studentDao=new StudentDaoImpl();

    @FXML
    public TableView<Student> studentTable;
    public Button btnInsert;
    public Button btnEdit;
    public Button btnEditSave;
    public Text text1;
    public Label count;
    @FXML
    private void jumpequipment(ActionEvent event) throws IOException {
        // 加载新的界面
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    TableColumn<Student, String> sno;
    @FXML
    TableColumn<Student, String> name;
    @FXML
    TableColumn<Student, String> date;
    @FXML
    TableColumn<Student, String> sex;
    @FXML
    TableColumn<Student, String> phone;
    @FXML
    private Pagination pageTable;//分页组件
    private int itemsPerPage = 20;//每页显示的数据条数
    //初始化分页控件，设置总页数，并添加页码变化监听器。
    @FXML
    private void initializePagination() {
// 设置分页控件的页数
        pageTable.setPageCount(calculatePageCount());
// 监听页码变化事件，当页码变化时刷新表格数据
        pageTable.currentPageIndexProperty().addListener((observable,
                                                          oldValue, newValue) -> {
// 根据新的页码加载数据
            loadPageData(newValue.intValue());

        });
    }

    private int calculatePageCount() {
        StudentDaoImpl studentDao = new
                StudentDaoImpl();
// 计算总页数，并向上取整
        return (int) Math.ceil((double)
                studentDao.queryAll().size()/ itemsPerPage);
    }

    //加载指定页码的数据到 TableView 中
    private void loadPageData(int pageIndex) {
        System.out.println(pageIndex);
        System.out.println(itemsPerPage);
// 将数据加载到 TableView 中
        updateTableView(studentDao.queryWithPagination(pageIndex+1,itemsPerPage));
    }
    static List<Student> students = new ArrayList<>();


    public void btnQuery(ActionEvent actionEvent) {
        System.out.println(txtDate.getValue().toString());
        System.out.println("hello");
        String sex=man.isSelected()?"运营":"非运营";
        Student student = new Student(txtNum.getText(),txtName.getText(),txtDate.getValue().toString(),sex,txtPhone.getText());
        students=studentDao.searchByCondition(student.sno,student.name,student.sex,student.date, student.phone);
        System.out.println(students);
        updateTableView(students);
        text1.setVisible(true);
        count.setText(String.valueOf(students.size()));
    }

    public void btnInsert(ActionEvent actionEvent) {
        initializePagination();
        students=studentDao.queryWithPagination(1,itemsPerPage);
        String sex;
        if(man.isSelected()){
            sex="运营";
        }else if (woman.isSelected()){
            sex="非运营";
        }
        else {
            sex="";
        }

        Student student = new Student(txtNum.getText(),txtName.getText(),txtDate.getValue().toString(),sex,txtPhone.getText());
        studentDao.insert(student);
        students=studentDao.queryAll();
        updateTableView(students);
        initializePagination();
        students=studentDao.queryWithPagination(1,itemsPerPage);
        updateTableView(students);
    }


    public void btnEdit(ActionEvent actionEvent) {
        btnEditSave.setVisible(true);
        Student s1 = (Student)
                studentTable.getSelectionModel().getSelectedItem();
        txtNum.textProperty().set(s1.sno);
        btnInsert.setDisable(true);
        txtPhone.textProperty().set(s1.phone);
        txtName.textProperty().set(s1.name);
        txtDate.setValue(LocalDate.parse(s1.date));
        if (s1.sex.equals("运营")) {
            man.setSelected(true);
        }else{
            woman.setSelected(true);
        }
        txtNum.setDisable(true);

    }
    @FXML
    private void btnDel(ActionEvent actionEvent) {
//获取选定行数据
        Student s1 = (Student)
                studentTable.getSelectionModel().getSelectedItem();
//使用系统确认框询问
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("您确定要删除选中的车辆信息吗？");
// 显示对话框，并等待按钮返回
        Optional<ButtonType> result = alert.showAndWait();
// 判断返回的按钮类型是确定还是取消，再据此分别进一步处理
        if (result.get() == ButtonType.OK) { // 单击确定按钮OK
            studentDao.delete(s1.getSno());
            students.remove(s1);//删除指定行学生数据
            initializePagination();
            students=studentDao.queryWithPagination(1,itemsPerPage);
            updateTableView(students);
        }
    }
    public void btnEditSave(ActionEvent actionEvent) {
        Student s1 = (Student)
                studentTable.getSelectionModel().getSelectedItem();
        System.out.println(s1);
        btnEditSave.setVisible(true);
        String sex=man.isSelected()?"运营":"非运营";
        Student student = new Student(txtNum.getText(),txtName.getText(),txtDate.getValue().toString(),sex,txtPhone.getText());
        //实现更新
        studentDao.update(student,s1.sno);
        initializePagination();
        students=studentDao.queryWithPagination(1,itemsPerPage);;
        updateTableView(students);
        btnInsert.setDisable(false);
        txtNum.setDisable(false);
    }
    //更新TableView中的数据
    private void updateTableView(List<Student> students) {
// 传入List集合，更新TableView
        studentTable.getItems().setAll(students);
// 为每列设置单元格值工厂，以从学生对象中获取属性值显示。
        sno.setCellValueFactory(new PropertyValueFactory<>
                ("sno"));
        name.setCellValueFactory(new PropertyValueFactory<>
                ("name"));
        date.setCellValueFactory(new PropertyValueFactory<>
                ("date"));
        sex.setCellValueFactory(new PropertyValueFactory<>
                ("sex"));
        phone.setCellValueFactory(new PropertyValueFactory<>
                ("phone"));
    }
}