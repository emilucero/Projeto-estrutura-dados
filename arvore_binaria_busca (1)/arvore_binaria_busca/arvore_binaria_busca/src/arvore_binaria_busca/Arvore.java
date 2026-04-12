package arvore_binaria_busca;

public class Arvore<T extends Comparable<T>> {

    private Element<T> raiz;

    public Element<T> getRaiz() {
        return raiz;
    }

    public void adicionar(T valor) {
        Element<T> novo = new Element<>(valor);

        if (raiz == null) {
            raiz = novo;
            return;
        }

        Element<T> atual = raiz;

        while (true) {
            if (valor.compareTo(atual.getValor()) < 0) {
                if (atual.getLeft() == null) {
                    atual.setLeft(novo);
                    return;
                }
                atual = atual.getLeft();
            } else {
                if (atual.getRight() == null) {
                    atual.setRight(novo);
                    return;
                }
                atual = atual.getRight();
            }
        }
    }

    public boolean buscar(T valor) {
        Element<T> atual = raiz;

        while (atual != null) {
            int comp = valor.compareTo(atual.getValor());

            if (comp == 0) return true;
            else if (comp < 0) atual = atual.getLeft();
            else atual = atual.getRight();
        }
        return false;
    }

    // PERCURSOS
    public String inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(raiz, sb);
        return sb.toString();
    }

    private void inOrder(Element<T> no, StringBuilder sb) {
        if (no != null) {
            inOrder(no.getLeft(), sb);
            sb.append(no.getValor()).append(" ");
            inOrder(no.getRight(), sb);
        }
    }

    public String preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(raiz, sb);
        return sb.toString();
    }

    private void preOrder(Element<T> no, StringBuilder sb) {
        if (no != null) {
            sb.append(no.getValor()).append(" ");
            preOrder(no.getLeft(), sb);
            preOrder(no.getRight(), sb);
        }
    }

    public String postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(raiz, sb);
        return sb.toString();
    }

    private void postOrder(Element<T> no, StringBuilder sb) {
        if (no != null) {
            postOrder(no.getLeft(), sb);
            postOrder(no.getRight(), sb);
            sb.append(no.getValor()).append(" ");
        }
    }

    // PARÊNTESES ANINHADOS
    public String toParentheses() {
        return toParentheses(raiz);
    }

    private String toParentheses(Element<T> no) {
        if (no == null) return "()";

        return no.getValor() +
                "(" + toParentheses(no.getLeft()) + ")" +
                "(" + toParentheses(no.getRight()) + ")";
    }

    // ALTURA DO NÓ
    public int alturaNo(T valor) {
        Element<T> no = buscarNo(raiz, valor);
        if (no == null) return -1;
        return altura(no);
    }

    private Element<T> buscarNo(Element<T> no, T valor) {
        if (no == null) return null;

        int comp = valor.compareTo(no.getValor());

        if (comp == 0) return no;
        else if (comp < 0) return buscarNo(no.getLeft(), valor);
        else return buscarNo(no.getRight(), valor);
    }

    // ALTURA DA ÁRVORE
    public int altura() {
        return altura(raiz);
    }

    private int altura(Element<T> no) {
        if (no == null) return -1;

        int esq = altura(no.getLeft());
        int dir = altura(no.getRight());

        return Math.max(esq, dir) + 1;
    }

    // PROFUNDIDADE DO NÓ
    public int profundidadeNo(T valor) {
        return nivel(valor);
    }

    // PROFUNDIDADE DA ÁRVORE
    public int profundidadeArvore() {
        return altura();
    }

    // NÍVEL DO NÓ
    public int nivel(T valor) {
        return nivel(raiz, valor, 0);
    }

    private int nivel(Element<T> no, T valor, int nivel) {
        if (no == null) return -1;

        int comp = valor.compareTo(no.getValor());

        if (comp == 0) return nivel;
        else if (comp < 0) return nivel(no.getLeft(), valor, nivel + 1);
        else return nivel(no.getRight(), valor, nivel + 1);
    }

    // CAMINHO
    public String caminho(T valor) {
        StringBuilder sb = new StringBuilder();
        Element<T> atual = raiz;

        while (atual != null) {
            sb.append(atual.getValor()).append(" -> ");

            int comp = valor.compareTo(atual.getValor());

            if (comp == 0) return sb.toString();
            else if (comp < 0) atual = atual.getLeft();
            else atual = atual.getRight();
        }

        return "Não encontrado";
    }

    // LIMPAR
    public void limpar() {
        raiz = null;
    }

    // ==========================================
    // ITEM (E): INVERTER OS NÓS DA ÁRVORE
    // ==========================================
    public void inverter() {
        inverter(raiz);
    }

    private void inverter(Element<T> no) {
        if (no == null) {
            return;
        }

        // Troca o nó da esquerda pelo da direita
        Element<T> temporario = no.getLeft();
        no.setLeft(no.getRight());
        no.setRight(temporario);

        // Chama recursivamente para inverter os próximos níveis
        inverter(no.getLeft());
        inverter(no.getRight());
    }
}