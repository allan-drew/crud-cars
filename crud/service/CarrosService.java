package crud.service;

import crud.dominio.Carros;
import crud.repository.CarrosRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CarrosService {

    private static final Scanner SCANNER = new Scanner(System.in);


    public static void menuOpcoes (int opcao) {
        switch (opcao) {
            case 1:
                buscarPeloModelo();
                break;
            case 2:
                delete();
                break;
            case 3:
                save();
                break;
            case 4:
                update();
                break;
            default:
                throw new IllegalArgumentException("Você digitou uma opção inválida!");
        }
    }


    // ===================================================================================================================
    // READ
    private static void buscarPeloModelo() {

        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Digite o modelo ou vazio para buscar todos os modelos");
        System.out.println("------------------------------------------------------------------------------");

        String modeloBuscado = SCANNER.nextLine();
        List<Carros> carros = CarrosRepository.buscarPeloModelo(modeloBuscado);

        System.out.println(carros);

    }
    // ===================================================================================================================


    // ===================================================================================================================
    // DELETE
    private static void delete () {

        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Digite o id do carro a ser deletado");
        System.out.println("------------------------------------------------------------------------------");

        // buscarPeloModelo(); // mostrando os modelos para aparecer o id para o usuário

        int idParaDeletar = Integer.parseInt(SCANNER.nextLine());

        System.out.println("Confirmar exclusão ? Sim (S) / Não (N) ");

        String simOuNao = SCANNER.nextLine();
        if ("S".equalsIgnoreCase(simOuNao)) {
            CarrosRepository.delete(idParaDeletar);
        }

    }
    // ===================================================================================================================



    // ===================================================================================================================
    // CREATE (SAVE)
    private static void save () {

        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Digite a marca, o modelo e a quilometragem do carro a ser incluído");
        System.out.println("------------------------------------------------------------------------------");

        System.out.println("Digita a marca do carro a ser incluído: ");
        String marcaParaIncluir = SCANNER.nextLine();

        System.out.println("Digita o modelo do carro a ser incluído: ");
        String modeloParaIncluir = SCANNER.nextLine();

        System.out.println("Digita a quilometragem do carro a ser incluído: ");
        int quilometragemParaIncluir = Integer.parseInt(SCANNER.nextLine());

        Carros carroParaIncluir = Carros.CarrosBuilder.builder()
                .marca(marcaParaIncluir)
                .modelo(modeloParaIncluir)
                .quilometragem(quilometragemParaIncluir)
                .build();

        CarrosRepository.save(carroParaIncluir);

    }
    // ===================================================================================================================



    // ===================================================================================================================
    // UPDATE
    private static void update () {

        System.out.println();
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Digite o 'id' do carro para atualizá-lo ");
        System.out.println("------------------------------------------------------------------------------");

        int idBuscado = Integer.parseInt(SCANNER.nextLine());

        // primeiro, encontramos pelo id
        // Com esse id, temos um Optional
        Optional<Carros> carroOptional = CarrosRepository.buscarPeloId(idBuscado);

        if (carroOptional.isEmpty()) {
            System.out.println("Carro não encontrado!");
            return;
        }

        Carros carroFromDB = carroOptional.get();
        System.out.println("carro encontrado: " + carroFromDB);

        System.out.println("Atualize a marca do carro encontrado ou tecle enter para manter o mesmo valor:  ");
        String marcaParaAlterar = SCANNER.nextLine();
        marcaParaAlterar = marcaParaAlterar.isEmpty() ? carroFromDB.getMarca() : marcaParaAlterar;
                        // se a entrada do teclado for vazia (enter), pegamos a mesma marca do objeto encontrado
                        // se não for vazia, pegamos a entrada do teclado

        System.out.println("Atualize o modelo do carro encontrado ou tecle enter para manter o mesmo valor: ");
        String modeloParaAlterar = SCANNER.nextLine();
        modeloParaAlterar = modeloParaAlterar.isEmpty() ? carroFromDB.getModelo() : modeloParaAlterar;


        System.out.println("Atualize a quilometragem do carro encontrado ou tecle enter para manter o mesmo valor: ");

//        int quilometragemParaAlterar = Integer.parseInt(SCANNER.nextLine());
        String quilometragemTeclado = SCANNER.nextLine();

        int quilometragemParaAlterar = quilometragemTeclado.isEmpty() ? carroFromDB.getQuilometragem() : Integer.parseInt(quilometragemTeclado);

        Carros carroParaAlterar = Carros.CarrosBuilder.builder()
                .id(carroFromDB.getId())  // o id não será alterado, portanto, vamos pegar o id do carro encontrado no DB e construir o novo objeto com o id encontrado
                .marca(marcaParaAlterar)
                .modelo(modeloParaAlterar)
                .quilometragem(quilometragemParaAlterar)
                .build();

        CarrosRepository.update(carroParaAlterar);

    }

    // ===================================================================================================================



}


