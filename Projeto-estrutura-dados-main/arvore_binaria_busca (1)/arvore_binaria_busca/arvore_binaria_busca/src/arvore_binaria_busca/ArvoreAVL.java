package arvore_binaria_busca;

public class ArvoreAVL<T extends Comparable<T>> extends Arvore<T> {

    @Override
    public void adicionar(T valor) {
        raiz = inserirAVL(raiz, valor);
    }

    @Override
    public void remover(T valor) {
        raiz = removerAVL(raiz, valor);
    }

    // --- LÓGICA DE BALANCEAMENTO AVL ---

    private Element<T> inserirAVL(Element<T> no, T valor) {
        if (no == null) return new Element<>(valor);

        int comp = valor.compareTo(no.getValor());
        if (comp < 0) no.setLeft(inserirAVL(no.getLeft(), valor));
        else if (comp > 0) no.setRight(inserirAVL(no.getRight(), valor));
        else return no; // Valores duplicados não permitidos

        return balancear(no);
    }

    private Element<T> removerAVL(Element<T> no, T valor) {
        if (no == null) return no;

        int comp = valor.compareTo(no.getValor());
        if (comp < 0) {
            no.setLeft(removerAVL(no.getLeft(), valor));
        } else if (comp > 0) {
            no.setRight(removerAVL(no.getRight(), valor));
        } else {
            if ((no.getLeft() == null) || (no.getRight() == null)) {
                Element<T> temp = (no.getLeft() != null) ? no.getLeft() : no.getRight();
                if (temp == null) return null;
                else no = temp;
            } else {
                Element<T> temp = encontrarMin(no.getRight());
                no.setValor(temp.getValor());
                no.setRight(removerAVL(no.getRight(), temp.getValor()));
            }
        }
        return balancear(no);
    }

    private String ultimoBalanceamento = "Nenhum";

    public String getUltimoBalanceamento() {
        return ultimoBalanceamento;
    }

    private Element<T> balancear(Element<T> no) {
        atualizarAltura(no);
        int fator = getFatorBalanceamento(no);

        // ESQUERDA
        if (fator > 1) {

            if (getFatorBalanceamento(no.getLeft()) >= 0) {
                ultimoBalanceamento = "Rotação Simples à Direita (LL)";
                return rotacionarDireita(no);
            } else {
                ultimoBalanceamento = "Rotação Dupla Esquerda-Direita (LR)";
                no.setLeft(rotacionarEsquerda(no.getLeft()));
                return rotacionarDireita(no);
            }
        }

        // DIREITA
        if (fator < -1) {

            if (getFatorBalanceamento(no.getRight()) <= 0) {
                ultimoBalanceamento = "Rotação Simples à Esquerda (RR)";
                return rotacionarEsquerda(no);
            } else {
                ultimoBalanceamento = "Rotação Dupla Direita-Esquerda (RL)";
                no.setRight(rotacionarDireita(no.getRight()));
                return rotacionarEsquerda(no);
            }
        }

        ultimoBalanceamento = "Nenhum";
        return no;
    }

    private void atualizarAltura(Element<T> no) {
        no.setAltura(Math.max(alturaAVL(no.getLeft()), alturaAVL(no.getRight())) + 1);
    }

    private int alturaAVL(Element<T> no) {
        return (no == null) ? 0 : no.getAltura();
    }

    private int getFatorBalanceamento(Element<T> no) {
        return (no == null) ? 0 : alturaAVL(no.getLeft()) - alturaAVL(no.getRight());
    }

    private Element<T> rotacionarDireita(Element<T> y) {
        Element<T> x = y.getLeft();
        Element<T> T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        atualizarAltura(y);
        atualizarAltura(x);
        return x;
    }

    private Element<T> rotacionarEsquerda(Element<T> x) {
        Element<T> y = x.getRight();
        Element<T> T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        atualizarAltura(x);
        atualizarAltura(y);
        return y;
    }
}