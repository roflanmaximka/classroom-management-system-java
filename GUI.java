import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GUI {
    PostgreDb postgreDb = new PostgreDb();

    JFrame frame = new JFrame();
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JButton add_button = new JButton("Добавить");
    JButton update_button = new JButton("Обновить");
    JButton delete_button = new JButton("Удалить");
    JButton edit_button = new JButton("Редактировать");


    public GUI(String table_type) {

        switch (table_type) {
            case "main":
                update_button.setBounds(1, 1, 100, 20);
                frame.add(update_button);
                update_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reloadMainTable();
                    }
                });

                model.addColumn("CLASSROOM NUMBER");
                model.addColumn("CLASSROOM PEOPLE");
                model.addColumn("CLASSROOM FLOOR");
                model.addColumn("TEACHER NAME");
                model.addColumn("TEACHER CABINET");
                model.addRow(new Object[]{"CLASSROOM NUMBER", "CLASSROOM PEOPLE", "CLASSROOM FLOOR", "TEACHER NAME", "TEACHER CABINET"});

                reloadMainTable();
                break;

            case "teachers":
                add_button.setBounds(1, 1, 100, 20);
                frame.add(add_button);
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int rowCount = model.getRowCount() - 1;
                        String teacherid = (String) model.getValueAt(rowCount, 0);
                        String teacherName = (String) model.getValueAt(rowCount, 1);
                        String teacherCabinet = (String) model.getValueAt(rowCount, 2);

                        String insert = "insert into teachers (id, name, cabinet) values ('%s', '%s', '%s')".formatted(teacherid, teacherName, teacherCabinet);

                        try {
                            postgreDb.statement.execute(insert);
                            reloadTeachersTable();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                delete_button.setBounds(101, 1, 100, 20);
                frame.add(delete_button);
                delete_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = (String) table.getValueAt(table.getSelectedRow(), 0);
                        String delete = "delete from teachers where id = '%s'".formatted(id);
                        String delete_cascade = "delete from classrooms where teacherid = '%s'".formatted(id);

                        try {
                            postgreDb.statement.execute(delete);
                            reloadTeachersTable();

                            postgreDb.statement.execute(delete_cascade);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                edit_button.setBounds(201, 1, 100, 20);
                frame.add(edit_button);
                edit_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String teacherid = (String) model.getValueAt(table.getSelectedRow(), 0);
                        String teacherName = (String) model.getValueAt(table.getSelectedRow(), 1);
                        String teacherCabinet = (String) model.getValueAt(table.getSelectedRow(), 2);

                        String update = "update teachers set name = '%s', cabinet = '%s' where id = '%s'".formatted(teacherName, teacherCabinet, teacherid);

                        try {
                            postgreDb.statement.execute(update);
                            reloadTeachersTable();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });

                model.addColumn("TEACHER ID");
                model.addColumn("TEACHER NAME");
                model.addColumn("TEACHER CABINET");
                model.addRow(new Object[]{"TEACHER ID", "TEACHER NAME", "TEACHER CABINET"});

                reloadTeachersTable();
                break;

            case "classrooms":
                add_button.setBounds(1, 1, 100, 20);
                frame.add(add_button);
                add_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int rowCount = model.getRowCount() - 1;
                        String classroomId = (String) model.getValueAt(rowCount, 0);
                        String classroomNumber = (String) model.getValueAt(rowCount, 1);
                        String classroomPeople = (String) model.getValueAt(rowCount, 2);
                        String classroomFloor = (String) model.getValueAt(rowCount, 3);
                        String classroomteacherid= (String) model.getValueAt(rowCount, 4);

                        String insert = "insert into classrooms (id, classroomnumber, people, floor, teacherid) values ('%s', '%s', '%s', '%s', '%s')".formatted(classroomId, classroomNumber, classroomPeople, classroomFloor, classroomteacherid);

                        try {
                            postgreDb.statement.execute(insert);
                            reloadClassroomsTable();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                delete_button.setBounds(101, 1, 100, 20);
                frame.add(delete_button);
                delete_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String id = (String) table.getValueAt(table.getSelectedRow(), 0);
                        String delete = "delete from classrooms where id = '%s'".formatted(id);

                        try {
                            postgreDb.statement.execute(delete);
                            reloadClassroomsTable();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });

                edit_button.setBounds(201, 1, 100, 20);
                frame.add(edit_button);
                edit_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String classroomId = (String) model.getValueAt(table.getSelectedRow(), 0);
                        String classroomNumber = (String) model.getValueAt(table.getSelectedRow(), 1);
                        String classroomPeople = (String) model.getValueAt(table.getSelectedRow(), 2);
                        String classroomFloor = (String) model.getValueAt(table.getSelectedRow(), 3);
                        String classroomteacherid= (String) model.getValueAt(table.getSelectedRow(), 4);

                        String update = "update classrooms set classroomnumber = '%s', people = '%s', floor = '%s', teacherid = '%s' where id = '%s'".formatted(classroomNumber, classroomPeople, classroomFloor, classroomteacherid, classroomId);

                        try {
                            postgreDb.statement.execute(update);
                            reloadClassroomsTable();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }

                    }
                });

                update_button.setBounds(301, 1, 100, 20);
                frame.add(update_button);
                update_button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reloadClassroomsTable();
                    }
                });

                model.addColumn("CLASSROOM ID");
                model.addColumn("CLASSROOM NUMBER");
                model.addColumn("CLASSROOM PEOPLE");
                model.addColumn("CLASSROOM FLOOR");
                model.addColumn("CLASSROOM TEACHER ID");
                model.addRow(new Object[]{"CLASSROOM ID", "CLASSROOM NUMBER", "CLASSROOM PEOPLE", "CLASSROOM FLOOR", "CLASSROOM TEACHER ID"});

                reloadClassroomsTable();
                break;
        }

        table = new JTable(model);
        table.setBounds(1, 21, 800, 400);
        frame.add(table);
    }

    private void reloadMainTable() {
        String select = "select classrooms.classroomnumber, classrooms.people, classrooms.floor, teachers.name, teachers.cabinet from classrooms, teachers where classrooms.teacherid = teachers.id";
        model.setRowCount(1);

        try {
            ResultSet resultSet = postgreDb.statement.executeQuery(select);

            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5)});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void reloadTeachersTable() {
        String select = "select id, name, cabinet from teachers";
        model.setRowCount(1);

        try {
            ResultSet resultSet = postgreDb.statement.executeQuery(select);

            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3)});
            }

            model.addRow(new Object[]{"", "", ""});


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void reloadClassroomsTable() {
        String select = "select id, classroomnumber, people, floor, teacherid from classrooms";
        model.setRowCount(1);

        try {
            ResultSet resultSet = postgreDb.statement.executeQuery(select);

            while (resultSet.next()) {
                model.addRow(new Object[]{resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5)});
            }
            model.addRow(new Object[]{"", "", "", "", ""});

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void show() {
        frame.setSize(800, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

}
