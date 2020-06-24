
import br.senac.sp.dao.produtoDAO;
import br.senac.sp.dao.vendaDAO;
import br.senac.sp.model.Venda;
import br.senac.sp.model.produto;
import java.util.List;

/**
 *
 * @author rafae
 */
public class TesteVenda {

    public static void main(String[] args) {
        /*vendaDAO prodDAO = new vendaDAO();
        testCadastrar();
        testeBuscaTodos();
        testeExcluir();*/
        //testeAlterar();
        testeBuscaTodosporCod();
        /*testeBuscaTudo();
        testCadastrarCliente("Francisco","135431463", "CDSP");*/
    }

  /*  public static void testCadastrar() {
        Venda vd = new Venda();
        vd.setNomeproduto("Arroz");
        vd.setCodigoproduto("1");
        vd.setCategoria("Alimentos");
        vd.setQtd(10);
        vd.setPreco_unitario(5);
        vd.setUsuario("rlouzano");
        vd.setNome_filial("CDSP");

        vendaDAO vdDAO = new vendaDAO();
        vdDAO.cadastrar(vd.getNomeproduto(), vd.getCodigoproduto(), vd.getCategoria(), vd.getQtd(), vd.getPreco_unitario(), vd.getUsuario(), vd.getNome_filial());
    }*/
    
    
    public static void testeAlterar(){
        vendaDAO vdao = new vendaDAO();
        if(vdao.Alterar(100, 15)){
            System.out.println("Entrou");
        }
        
    }
    
        public static void testeBuscaTodosporCod() {
        produtoDAO prodDAO = new produtoDAO();
        List<produto> lista = prodDAO.pegaTodosPorCod("123");
        for (Venda v : lista) {
            System.out.println(v);
        }
    }
    
    
    

    public static void testeBuscaTodos() {
        vendaDAO prodDAO = new vendaDAO();
        List<Venda> lista = prodDAO.pegaTodos();
        for (Venda v : lista) {
            System.out.println(v);
        }
    }

    public static void testeExcluir() {
        Venda vd = new Venda();
        vendaDAO vdao = new vendaDAO();
        vdao.Excluir(1);
    }

    public static void testeBuscaTudo() {
        vendaDAO prodDAO = new vendaDAO();
        List<Venda> v = prodDAO.pegaTudo(7);
        System.out.println(v);
    }

    public static void testCadastrarCliente(String nome, String cpf, String filial) {
        vendaDAO vdao = new vendaDAO();
        vdao.cadastrarCliente(nome, cpf, filial);
    }
}
