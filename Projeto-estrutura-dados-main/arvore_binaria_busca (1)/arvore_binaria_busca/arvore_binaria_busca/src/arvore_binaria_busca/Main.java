package arvore_binaria_busca;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private ITree<Integer> arvore;
    private PainelArvore painel;

    public Main() {
        // PERGUNTA AO USUÁRIO NO INÍCIO
        Object[] opcoes = {"Árvore BST", "Árvore AVL"};
        int escolha = JOptionPane.showOptionDialog(null,
                "Qual estrutura deseja utilizar?", "Seleção de Árvore",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoes, opcoes[0]);

        if (escolha == 0) {
            arvore = new Arvore<>();
            setTitle("Simulador - Árvore Binária de Busca (BST)");
        } else if (escolha == 1) {
            arvore = new ArvoreAVL<>();
            setTitle("Simulador - Árvore AVL (Auto-Balanceada)");
        } else {
            System.exit(0); // Fecha se o utilizador cancelar
        }

        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painel = new PainelArvore(arvore);
        add(painel, BorderLayout.CENTER);

        JPanel topo = new JPanel();
        JTextField campo = new JTextField(5);
        JButton adicionar = new JButton("Adicionar");
        JButton remover = new JButton("Remover"); // NOVO BOTÃO
        JButton buscar = new JButton("Buscar");
        JButton salvar = new JButton("Salvar TXT");
        JButton carregar = new JButton("Carregar TXT");
        JButton inverter = new JButton("Inverter");
        JButton limpar = new JButton("Limpar");
        JButton percursos = new JButton("Percursos");
        JButton info = new JButton("Info");
        JButton balanceamento = new JButton("Balanceamento");

        topo.add(new JLabel("Número:"));
        topo.add(campo);
        topo.add(adicionar);
        topo.add(remover); // Adicionado à interface
        topo.add(buscar);
        topo.add(salvar);
        topo.add(carregar);
        topo.add(inverter);
        topo.add(limpar);
        topo.add(percursos);
        topo.add(info);
        topo.add(balanceamento);

        add(topo, BorderLayout.NORTH);

        // --- AÇÕES DO BOTÕES (AGORA FUNCIONAM PARA AMBAS AS ÁRVORES) ---

        adicionar.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campo.getText());
                arvore.adicionar(valor);
                campo.setText("");
                painel.repaint();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido!");
            }
        });

        remover.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campo.getText());
                arvore.remover(valor);
                campo.setText("");
                painel.repaint();
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido!"); }
        });

        buscar.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campo.getText());
                boolean achou = arvore.buscar(valor);
                JOptionPane.showMessageDialog(this, achou ? "Encontrado!" : "Não encontrado!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Número inválido!"); }
        });

        inverter.addActionListener(e -> {
            arvore.inverter();
            painel.repaint();
            JOptionPane.showMessageDialog(this, "Árvore invertida!\nNOTA: Regras de balanceamento podem não ser aplicadas à árvore espelhada.");
        });

        salvar.addActionListener(e -> salvarArquivo());
        carregar.addActionListener(e -> carregarArquivo());

        limpar.addActionListener(e -> { arvore.limpar(); painel.repaint(); });

        percursos.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "InOrder: " + arvore.inOrder() +
                    "\nPreOrder: " + arvore.preOrder() + "\nPostOrder: " + arvore.postOrder());
        });

        info.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campo.getText());
                if (!arvore.buscar(valor)) {
                    JOptionPane.showMessageDialog(this, "Número não existe!"); return;
                }
                String msg = "Caminho: " + arvore.caminho(valor) +
                        "\nNível do nó: " + arvore.nivel(valor) +
                        "\nAltura do nó: " + arvore.alturaNo(valor) +
                        "\n\nAltura da árvore: " + arvore.altura();
                JOptionPane.showMessageDialog(this, msg);
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Digite um número!"); }
        });

        balanceamento.addActionListener(e -> {

            if (arvore instanceof ArvoreAVL) {
                ArvoreAVL<Integer> avl = (ArvoreAVL<Integer>) arvore;

                JOptionPane.showMessageDialog(this,
                        "Último balanceamento realizado:\n" +
                                avl.getUltimoBalanceamento());

            } else {
                JOptionPane.showMessageDialog(this,
                        "A árvore BST comum não realiza balanceamentos.");
            }

        });
    }

    private void salvarArquivo() {
        try {
            String conteudo = arvore.toParentheses();
            java.io.FileWriter writer = new java.io.FileWriter("arvore.txt");
            writer.write(conteudo);
            writer.close();
            JOptionPane.showMessageDialog(this, "Arquivo salvo!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar!"); }
    }

    private void carregarArquivo() {
        try {
            java.io.File arquivo = new java.io.File("arvore.txt");
            if (!arquivo.exists()) return;
            java.util.Scanner scanner = new java.util.Scanner(arquivo);
            String conteudo = scanner.hasNextLine() ? scanner.nextLine() : "";
            scanner.close();
            arvore.limpar();
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("-?\\d+").matcher(conteudo);
            while (m.find()) arvore.adicionar(Integer.parseInt(m.group()));
            painel.repaint();
            JOptionPane.showMessageDialog(this, "Árvore carregada!");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao carregar!"); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}