package br.insper.ecommerce;

import br.insper.ecommerce.cliente.Cliente;
import br.insper.ecommerce.cliente.ClienteService;
import br.insper.ecommerce.compra.Compra;
import br.insper.ecommerce.compra.CompraService;
import br.insper.ecommerce.compra.Item;
import br.insper.ecommerce.pagamento.Boleto;
import br.insper.ecommerce.pagamento.CartaoCredito;
import br.insper.ecommerce.pagamento.MeioPagamento;
import br.insper.ecommerce.pagamento.Pix;
import br.insper.ecommerce.produto.Produto;
import br.insper.ecommerce.produto.ProdutoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String opcao = "0";
        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        CompraService compraService = new CompraService();
        MeioPagamento pagamento = null;


        while(!opcao.equalsIgnoreCase("9")) {

            System.out.println("""
                    1 - Cadastrar Cliente
                    2 - Listar Clientes
                    3 - Excluir Cliente
                    4 - Cadastrar Produto
                    5 - Listar Produtos
                    6 - Excluir Produto
                    7 - Cadastrar Compra
                    8 - Listar Compras
                    9 -Sair
                    """);
            opcao = scanner.nextLine();
            if (opcao.equalsIgnoreCase("1")) {

                System.out.println("Digite o nome do cliente:");
                String nome = scanner.nextLine();
                System.out.println("Digite o CPF do cliente;");
                String cpf = scanner.nextLine();

                clienteService.cadastrarClientes(nome,cpf);
            }

            if (opcao.equalsIgnoreCase("2")) {
                clienteService.listarClientes();

            }

            if (opcao.equalsIgnoreCase("3")) {

                System.out.println("Digite o cpf do cliente para deletar:");
                String cpf = scanner.nextLine();

                clienteService.excluirClientes(cpf);


            }
            if (opcao.equalsIgnoreCase("4")) {

                System.out.println("Digite o nome do produto:");
                String nome = scanner.nextLine();
                System.out.println("Digite o preco do produto;");
                Double preco = scanner.nextDouble();

                produtoService.cadastrarProduto(nome,preco);
            }

            if (opcao.equalsIgnoreCase("5")) {
                produtoService.listarProdutos();

            }

            if (opcao.equalsIgnoreCase("6")) {

                System.out.println("Digite o nome do produto para deletar:");
                String nome = scanner.nextLine();

                produtoService.excluirProdutos(nome);


            }
            if (opcao.equalsIgnoreCase("7")) {

                ArrayList<Item> itens = new ArrayList<>();

                System.out.println("Digite o cpf do cliente da compra:");
                String cpf = scanner.nextLine();

                Cliente clienteCompra = clienteService.buscarCliente(cpf);
                if (clienteCompra == null) {
                    System.out.println("cliente não encontrado");
                    break;
                }

                System.out.println("Digite o numero do meio de Pagamento da compra:");
                System.out.println("""
                    1 - Pix
                    2 - Boleto
                    3 - Cartao de Credito
                    """);
                Integer meioPagamento = Integer.parseInt(scanner.nextLine());

                if (meioPagamento == 1){
                    System.out.println("Digite a chave:");
                    String chave = scanner.nextLine();
                    System.out.println("Digite o qrCode:");
                    String qrcode = scanner.nextLine();

                    pagamento = new Pix(true, LocalDateTime.now(),chave,qrcode);
                }
                if (meioPagamento== 2){
                    System.out.println("Digite o codigo de barras:");
                    String codigoBarra = scanner.nextLine();

                    pagamento = new Boleto(true,LocalDateTime.now(),codigoBarra);
                }
                if (meioPagamento== 3) {
                    System.out.println("Digite o numero do cartao:");
                    String numero = scanner.nextLine();

                    System.out.println("Digite a bandeira do cartao:");
                    String bandeira = scanner.nextLine();

                    pagamento = new CartaoCredito(true,LocalDateTime.now(),numero,bandeira);
                }

                String produto = "";

                while (!produto.trim().equalsIgnoreCase("nenhum")){
                    System.out.println("Digite o nome do produto a ser adiconado (se não houver mais, digite 'nenhum'):");
                    produto = scanner.nextLine();
                    boolean achou = false;
                    for (Produto produto_listado : produtoService.devolveProdutos()) {
                        if (produto.trim().equalsIgnoreCase(produto_listado.getNome())){
                            achou = true;
                            System.out.println("Digite a quantidade do produto: ");
                            int quantidade = scanner.nextInt();
                            Item item = new Item(quantidade,produto_listado);
                            itens.add(item);
                        }
                        }
                    if (!achou && !produto.trim().equalsIgnoreCase("nenhum")) {
                        System.out.println("Produto não encontrado");
                    }
                }
                System.out.println("Todos os produtos cadastrados");
                compraService.cadastrarCompra(clienteCompra,pagamento,itens);

            }
            if (opcao.equalsIgnoreCase("8")) {

                compraService.listarCompras();
            }

        }


    }

}