package crud.test;


import crud.service.CarrosService;

import java.util.Scanner;

public class CrudTest01 {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        while (true) {
            menuTela();
            opcao = Integer.parseInt(SCANNER.nextLine());
            if(opcao == 0) break;

            CarrosService.menuOpcoes(opcao);

        }

    }


    private static void menuTela() {
        System.out.println("------------------------------------------------------------------------------");
        System.out.println("Digite a operação desejada: ");
        System.out.println("    0 - Para sair");
        System.out.println("    1 - Para BUSCAR o modelo do carro ");
        System.out.println("    2 - Para DELETAR um modelo do carro (após realizar a busca) ");
        System.out.println("    3 - Para SALVAR um novo modelo de carro ");
        System.out.println("    4 - Para ATUALIZAR um carro (após buscar pelo id)");
        System.out.println("------------------------------------------------------------------------------");


    }


}
