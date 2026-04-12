package arvore_binaria_busca;

public class Element<T> {

    private T valor;
    private Element<T> left;
    private Element<T> right;

    public Element(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public Element<T> getLeft() {
        return left;
    }

    public void setLeft(Element<T> left) {
        this.left = left;
    }

    public Element<T> getRight() {
        return right;
    }

    public void setRight(Element<T> right) {
        this.right = right;
    }
}

