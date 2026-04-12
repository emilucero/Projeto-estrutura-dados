package arvore_binaria_busca;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Arvore<Integer> arvore = new Arvore<>();
    private PainelArvore painel;

    public Main() {
        setTitle("Árvore Binária de Busca");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        painel = new PainelArvore(arvore);
        add(painel, BorderLayout.CENTER);

        JPanel topo = new JPanel();

        JTextField campo = new JTextField(10);
        JButton adicionar = new JButton("Adicionar");
        JButton buscar = new JButton("Buscar");
        JButton salvar = new JButton("Salvar TXT");
        JButton carregar = new JButton("Carregar TXT");
        JButton limpar = new JButton("Limpar");
        JButton percursos = new JButton("Percursos");
        JButton info = new JButton("Info Nó");
        // Novo botão
        JButton inverter = new JButton("Inverter");

        topo.add(new JLabel("Número:"));
        topo.add(campo);
        topo.add(adicionar);
        topo.add(buscar);
        topo.add(salvar);
        topo.add(carregar);
        topo.add(inverter); // Botão inverter adicionado
        topo.add(limpar);
        topo.add(percursos);
        topo.add(info);

        add(topo, BorderLayout.NORTH);

        // AÇÕES
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

        buscar.addActionListener(e -> {
            try {
                int valor = Integer.parseInt(campo.getText());
                boolean achou = arvore.buscar(valor);
                JOptionPane.showMessageDialog(this,
                        achou ? "Encontrado!" : "Não encontrado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido!");
            }
        });

        // Ação para inverter e "imprimir" a árvore
        inverter.addActionListener(e -> {
            arvore.inverter();
            painel.repaint(); // Imprime graficamente a árvore invertida

            // Imprime textualmente a árvore após inversão
            String msg = "Árvore invertida com sucesso!\n\n" +
                    "Nova Estrutura (Pré-ordem): \n" + arvore.preOrder();
            JOptionPane.showMessageDialog(this, msg);
        });

        salvar.addActionListener(e -> salvarArquivo());
        carregar.addActionListener(e -> carregarArquivo());

        limpar.addActionListener(e -> {
            arvore.limpar();
            painel.repaint();
        });

        percursos.addActionListener(e -> {
            String msg =
                    "InOrder: " + arvore.inOrder() + "\n" +
                            "PreOrder: " + arvore.preOrder() + "\n" +
                            "PostOrder: " + arvore.postOrder();

            JOptionPane.showMessageDialog(this, msg);
        });

        info.addActionListener(e -> {

            String texto = campo.getText().trim();

            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite um número!");
                return;
            }

            try {
                int valor = Integer.parseInt(texto);

                if (!arvore.buscar(valor)) {
                    JOptionPane.showMessageDialog(this, "Número não existe!");
                    return;
                }

                String msg =
                        "Caminho: " + arvore.caminho(valor) + "\n" +
                                "Nível do nó: " + arvore.nivel(valor) + "\n" +
                                "Profundidade do nó: " + arvore.profundidadeNo(valor) + "\n" +
                                "Altura do nó: " + arvore.alturaNo(valor) + "\n\n" +
                                "Altura da árvore: " + arvore.altura() + "\n" +
                                "Profundidade da árvore: " + arvore.profundidadeArvore();

                JOptionPane.showMessageDialog(this, msg);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Número inválido!");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    private void salvarArquivo() {
        try {
            String conteudo = arvore.toParentheses();

            java.io.FileWriter writer = new java.io.FileWriter("arvore.txt");
            writer.write(conteudo);
            writer.close();

            JOptionPane.showMessageDialog(this, "Arquivo salvo com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar!");
        }
    }

    private void carregarArquivo() {
        try {
            java.io.File arquivo = new java.io.File("arvore.txt");

            if (!arquivo.exists()) {
                JOptionPane.showMessageDialog(this, "Nenhum arquivo 'arvore.txt' encontrado!");
                return;
            }

            java.util.Scanner scanner = new java.util.Scanner(arquivo);
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine());
            }
            scanner.close();

            String conteudo = sb.toString();

            arvore.limpar();

            java.util.regex.Pattern p = java.util.regex.Pattern.compile("-?\\d+");
            java.util.regex.Matcher m = p.matcher(conteudo);

            while (m.find()) {
                int valor = Integer.parseInt(m.group());
                arvore.adicionar(valor);
            }

            painel.repaint();
            JOptionPane.showMessageDialog(this, "Árvore carregada com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar arquivo: " + e.getMessage());
        }
    }
}