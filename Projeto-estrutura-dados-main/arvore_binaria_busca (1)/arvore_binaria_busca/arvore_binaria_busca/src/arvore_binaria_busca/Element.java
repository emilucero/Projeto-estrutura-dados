package arvore_binaria_busca;

public class Element<T> {
    private T valor;
    private Element<T> left;
    private Element<T> right;
    private int altura; // Necessário para o balanceamento da AVL

    public Element(T valor) {
        this.valor = valor;
        this.altura = 1; // Nós recém-criados começam com altura 1
    }

    public T getValor() { return valor; }
    public void setValor(T valor) { this.valor = valor; }

    public Element<T> getLeft() { return left; }
    public void setLeft(Element<T> left) { this.left = left; }

    public Element<T> getRight() { return right; }
    public void setRight(Element<T> right) { this.right = right; }

    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }
}