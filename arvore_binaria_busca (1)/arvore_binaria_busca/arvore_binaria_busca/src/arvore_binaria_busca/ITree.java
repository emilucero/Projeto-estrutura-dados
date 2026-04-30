package arvore_binaria_busca;

import java.util.function.Consumer;

public interface ITree<T extends Comparable<T>> {
    Element<T> getRaiz();
    void adicionar(T valor);
    void remover(T valor);
    boolean buscar(T valor);

    // Percursos
    String inOrder();
    String preOrder();
    String postOrder();
    String toParentheses();

    // Métricas
    int alturaNo(T valor);
    int altura();
    int profundidadeNo(T valor);
    int profundidadeArvore();
    int nivel(T valor);
    String caminho(T valor);

    // Operações Especiais
    void limpar();
    void inverter();

    // NOVO MÉTODO: Permite que a interface gráfica escute eventos de balanceamento
    // default para que a classe Arvore (BST) não seja obrigada a implementar isso.
    default void setOnBalanceListener(Consumer<String> listener) {}
}