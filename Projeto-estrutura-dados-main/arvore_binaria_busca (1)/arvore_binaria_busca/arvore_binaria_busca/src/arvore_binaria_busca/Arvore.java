package arvore_binaria_busca;

public class Arvore<T extends Comparable<T>> implements ITree<T> {

    // Agora é protected para a AVL poder modificar
    protected Element<T> raiz;

    @Override
    public Element<T> getRaiz() {
        return raiz;
    }

    @Override
    public void adicionar(T valor) {
        Element<T> novo = new Element<>(valor);
        if (raiz == null) {
            raiz = novo;
            return;
        }
        Element<T> atual = raiz;
        while (true) {
            if (valor.compareTo(atual.getValor()) < 0) {
                if (atual.getLeft() == null) { atual.setLeft(novo); return; }
                atual = atual.getLeft();
            } else if (valor.compareTo(atual.getValor()) > 0) {
                if (atual.getRight() == null) { atual.setRight(novo); return; }
                atual = atual.getRight();
            } else {
                return; // Evita duplicatas
            }
        }
    }

    @Override
    public void remover(T valor) {
        raiz = removerRecursivo(raiz, valor);
    }

    protected Element<T> removerRecursivo(Element<T> no, T valor) {
        if (no == null) return null;

        int comp = valor.compareTo(no.getValor());
        if (comp < 0) {
            no.setLeft(removerRecursivo(no.getLeft(), valor));
        } else if (comp > 0) {
            no.setRight(removerRecursivo(no.getRight(), valor));
        } else {
            // Nó com 1 ou 0 filhos
            if (no.getLeft() == null) return no.getRight();
            else if (no.getRight() == null) return no.getLeft();

            // Nó com 2 filhos: pega o menor da subárvore direita
            Element<T> temp = encontrarMin(no.getRight());
            no.setValor(temp.getValor());
            no.setRight(removerRecursivo(no.getRight(), temp.getValor()));
        }
        return no;
    }

    protected Element<T> encontrarMin(Element<T> no) {
        while (no.getLeft() != null) no = no.getLeft();
        return no;
    }

    @Override
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

    // ============ MANTEMOS TODOS OS CÓDIGOS ORIGINAIS ABAIXO ============

    @Override
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

    @Override
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

    @Override
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

    @Override
    public String toParentheses() {
        return toParentheses(raiz);
    }
    private String toParentheses(Element<T> no) {
        if (no == null) return "()";
        return no.getValor() + "(" + toParentheses(no.getLeft()) + ")(" + toParentheses(no.getRight()) + ")";
    }

    @Override
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

    @Override
    public int altura() { return altura(raiz); }
    private int altura(Element<T> no) {
        if (no == null) return -1;
        return Math.max(altura(no.getLeft()), altura(no.getRight())) + 1;
    }

    @Override
    public int profundidadeNo(T valor) { return nivel(valor); }

    @Override
    public int profundidadeArvore() { return altura(); }

    @Override
    public int nivel(T valor) { return nivel(raiz, valor, 0); }
    private int nivel(Element<T> no, T valor, int nivel) {
        if (no == null) return -1;
        int comp = valor.compareTo(no.getValor());
        if (comp == 0) return nivel;
        else if (comp < 0) return nivel(no.getLeft(), valor, nivel + 1);
        else return nivel(no.getRight(), valor, nivel + 1);
    }

    @Override
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

    @Override
    public void limpar() { raiz = null; }

    @Override
    public void inverter() { inverter(raiz); }
    private void inverter(Element<T> no) {
        if (no == null) return;
        Element<T> temp = no.getLeft();
        no.setLeft(no.getRight());
        no.setRight(temp);
        inverter(no.getLeft());
        inverter(no.getRight());
    }
}