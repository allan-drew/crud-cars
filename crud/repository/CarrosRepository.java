package crud.repository;

import crud.conexao.ConnectionFactory;
import crud.dominio.Carros;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarrosRepository {


    // ===================================================================================================================
    // READ
    // implementando a busca pelo modelo usando prepared statement
    public static List<Carros> buscarPeloModelo (String modeloBuscado) {

        List<Carros> carrosList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementBuscarPeloModelo(connection,modeloBuscado);
             ResultSet rs = ps.executeQuery();
        ){

            while (rs.next()) {
                Carros carroToList = Carros.CarrosBuilder
                        .builder()
                        .id(rs.getInt("id"))
                        .marca(rs.getString("marca"))
                        .modelo(rs.getString("modelo"))
                        .quilometragem(rs.getInt("quilometragem"))
                        .build();

                carrosList.add(carroToList);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carrosList;

    }

    private static PreparedStatement preparedStatementBuscarPeloModelo (Connection connection, String modelo) throws SQLException {
        String sql = "select * from car_store.carros where modelo like ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, String.format("%%%s%%", modelo));
        return ps;
    }
    // ===================================================================================================================


    // ===================================================================================================================
    // DELETE
    public static void delete (int id) {

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementDelete(connection, id))
        {
            ps.execute();
            System.out.println("O carro " + id + " foi deletado da base de dados");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static PreparedStatement preparedStatementDelete (Connection connection, Integer id) throws SQLException {
        String sql = "DELETE FROM `car_store`.`carros` WHERE (`id` = ?);";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }
    // ===================================================================================================================




    // ===================================================================================================================
    // CREATE (SAVE)
    public static void save (Carros carro) {

        System.out.println("Salvando " + carro.getModelo() + " na base de dados......aguarde!");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementSave(connection, carro)) {

            ps.execute();

            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("O carro " + carro.getModelo() + " foi adicionado da base de dados com sucesso!");
            System.out.println("-------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static PreparedStatement preparedStatementSave(Connection connection, Carros carro) throws SQLException {

        // String sql = "INSERT INTO `car_store`.`carros` (`nome`) VALUES (?);";
        String sql = "INSERT INTO `car_store`.`carros` (`marca`, `modelo`, `quilometragem`) VALUES (?, ?, ?);";

        // INSERT INTO `car_store`.`carros` (`marca`, `modelo`, `quilometragem`) VALUES ('VW', 'Gol', '50000');

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, carro.getMarca());
        ps.setString(2, carro.getModelo());
        ps.setInt(3, carro.getQuilometragem());

        return ps;

    }
    // ===================================================================================================================



    // ===================================================================================================================
    // UPDATE
    // para fazer o update precisamos de um objeto, e para isso precisamos buscar pelo id
    public static Optional<Carros> buscarPeloId (Integer id) {

        List<Carros> carrosList = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementBuscarPeloId(connection, id);
             ResultSet rs = ps.executeQuery();
        ){

            if (!rs.next()) Optional.empty();

            return Optional.of(Carros.CarrosBuilder
                    .builder()
                    .id(rs.getInt("id"))
                    .marca(rs.getString("marca"))
                    .modelo(rs.getString("modelo"))
                    .quilometragem(rs.getInt("quilometragem"))
                    .build());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();

    }

    private static PreparedStatement preparedStatementBuscarPeloId (Connection connection, Integer id) throws SQLException {
        String sql = "select * from car_store.carros where id = ?;";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }


    //----
    // UPDATE de algum objeto Carro usando PreparedStatement:

    public static void update (Carros carro) {

        System.out.println("Fazendo o Update do carro na base de dados......aguarde!");

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = preparedStatementUpdate(connection, carro)) {

            ps.executeUpdate();
            System.out.println("O(A) carro(a) de id " + carro.getId() + " foi atualizado(a) no banco de dados");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static PreparedStatement preparedStatementUpdate(Connection connection, Carros carro) throws SQLException {

        // String sql = "UPDATE `car_store`.`carros` SET `modelo` = ? WHERE (`id` = ?);";
        String sql = "UPDATE `car_store`.`carros` SET `marca` = ?, `modelo` = ?, `quilometragem` = ? WHERE (`id` = ?);";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, carro.getMarca());
        ps.setString(2, carro.getModelo());
        ps.setInt(3, carro.getQuilometragem());
        ps.setInt(4, carro.getId());

        return ps;

    }






}
