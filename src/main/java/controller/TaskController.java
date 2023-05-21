/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;


/**
 *
 * @author Alisson
 */
public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks ("
                + "idProject, "
                + "name, "
                + "description, "
                + "completed, "
                + "notes, "
                + "deadline, "
                + "createdAt, "
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao salvar a tarefa " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET "
                + "idProject = ?, "
                + "name = ?, "
                + "description = ?, "
                + "completed = ?, "
                + "notes = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //esstabelecendo a conex�o com o banco de dados
            connection = ConnectionFactory.getConnection();

            //preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.getIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.setInt(9, task.getId());

            //Executando a query
            statement.execute();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao atualizar tarefa " + e.getMessage(), e);
        }
    }

    public List<Task> getAll(int idProject) {

        String sql = "SELECT * FROM tasks WHERE idProject = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        //lista de tarefas que ser� devolvida quando a chamada do m�todo acontecer
        List<Task> tasks = new ArrayList<Task>();

        try {
            //Cria��o de conex�o
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Setando os valores que corresponde ao filtro de busca
            statement.setInt(1, idProject);

            //Valor retornado pela execu��o da query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos no meu resultSet
            while (resultSet.next()) {

                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdateAt(resultSet.getDate("updatedAt"));

                tasks.add(task);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Erro ao inserir tarefa" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        //lista de tarefas que foi criada e carregada do banco de dados;
        return tasks;
    }

    public void removeById(int taskId) {

        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Cria��o da conex�o com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, taskId);

            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar tarefa" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
