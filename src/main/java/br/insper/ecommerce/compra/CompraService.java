package br.insper.ecommerce.compra;

import br.insper.ecommerce.cliente.Cliente;
import br.insper.ecommerce.pagamento.MeioPagamento;
import br.insper.ecommerce.produto.Produto;

import java.util.ArrayList;

public class CompraService {

    ArrayList<Compra> compras= new ArrayList<>();
    public void cadastrarCompra (Cliente cliente,
                                 MeioPagamento meioPagamento,
                                 ArrayList<Item> itens) {

        Compra compra = new Compra(null,cliente,meioPagamento);
        for (Item item : itens) {
            compra.adicionarItem(item);
        }
        compras.add(compra);
    }

    public void listarCompras (){
        System.out.println("Lista de produtos:");
        if (compras.size() == 0) {
            System.out.println("Não há compras cadastradas");
        }
        for (Compra compra : compras) {
            System.out.println("Nome: " + compra.getCliente().toString());
            System.out.println("preco total: " + compra.getPrecoTotal());
            System.out.println("meio de pagamento: " + compra.getMeioPagamento().toString());
        }

    }
}
