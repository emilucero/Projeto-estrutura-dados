package arvore_binaria_busca;

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
}