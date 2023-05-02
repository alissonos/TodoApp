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
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author Alisson
 */
public class ProjectController {

    public void save(Project project) throws ClassNotFoundException {

        String sql = "INSERT INTO projects (name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Cria uma conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            //Cria um PrepareStatment, classe usada para executar a query
            statement = connection.prepareStatement(sql);

            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            //executa a sql para inserção dos dados
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar projeto " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void update(Project project) {

        String sql = "UPDATE projects SET "
                + "name = ?, "
                + "description = ?, "
                + "createdAt = ?, "
                + "updatedAt = ? "
                + "WHERE idprojects = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //esstabelecendo a conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores do statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando a query
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar projeto " + e.getMessage(), e);
        }
    }

    public List<Project> getAll() throws ClassNotFoundException {

        String sql = "SELECT * FROM projects";

        //lista de projetos que serão devolvidos quando a chamada do método acontecer
        List<Project> projects = new ArrayList<Project>();

        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultSet = null;

        try {
            //Criação de conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();

            //Enquanto houverem valores a serem percorridos no meu resultSet
            while (resultSet.next()) {

                Project project = new Project();

                project.setId(resultSet.getInt("idprojects"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));

                projects.add(project);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar os projetos" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        //lista de tarefas que foi criada e carregada do banco de dados;
        return projects;
    }

    public void removeById(int idProject) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM projects WHERE idprojects = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando a query
            statement = connection.prepareStatement(sql);

            //Setando os valores
            statement.setInt(1, idProject);

            //Executando a query
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar projeto" + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
