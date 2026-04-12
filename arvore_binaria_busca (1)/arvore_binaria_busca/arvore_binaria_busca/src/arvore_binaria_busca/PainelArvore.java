package arvore_binaria_busca;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

public class PainelArvore extends JPanel {
    private Arvore<Integer> arvore;

    private Map<Element<Integer>, Integer> logicosX;
    private Map<Element<Integer>, Integer> logicosY;
    private int contadorX;
    private int alturaMax;

    public PainelArvore(Arvore<Integer> arvore) {
        this.arvore = arvore;
    }

    private void calcularCoordenadas(Element<Integer> no, int profundidade) {
        if (no == null) return;

        calcularCoordenadas(no.getLeft(), profundidade + 1);

        logicosX.put(no, contadorX++);
        logicosY.put(no, profundidade);
        if (profundidade > alturaMax) alturaMax = profundidade;

        calcularCoordenadas(no.getRight(), profundidade + 1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (this.arvore != null && this.arvore.getRaiz() != null) {
            logicosX = new HashMap<>();
            logicosY = new HashMap<>();
            contadorX = 1;
            alturaMax = 1;

            calcularCoordenadas(this.arvore.getRaiz(), 1);

            int maxX = contadorX - 1;
            int maxY = alturaMax;

            // 1. LIMITES: Define uma distância fixa ideal e bonita entre os nós
            int gapXIdeal = 60;
            int gapYIdeal = 70;

            int margemLateral = 40;
            int larguraUtil = this.getWidth() - 2 * margemLateral;
            int alturaUtil = this.getHeight() - 80;

            // 2. ADAPTAÇÃO: Se a árvore couber, usa o gap ideal. Se for maior que a tela, espreme.
            int gapX = maxX > 1 ? Math.min(gapXIdeal, larguraUtil / (maxX - 1)) : gapXIdeal;
            int gapY = maxY > 1 ? Math.min(gapYIdeal, alturaUtil / (maxY - 1)) : gapYIdeal;

            // 3. TAMANHO REAL: Calcula o tamanho total que o "bloco" da árvore vai ocupar
            int larguraArvore = (maxX - 1) * gapX;
            int alturaArvore = (maxY - 1) * gapY;

            // 4. CENTRALIZAÇÃO: Centraliza perfeitamente o bloco desenhado no meio da janela
            int offsetX = (this.getWidth() - larguraArvore) / 2;
            int offsetY = Math.max(40, (this.getHeight() - alturaArvore) / 2);

            desenhar(g2d, this.arvore.getRaiz(), offsetX, offsetY, gapX, gapY);
        }
    }

    private void desenhar(Graphics2D g, Element<Integer> no, int offsetX, int offsetY, int gapX, int gapY) {
        if (no != null) {
            int raio = 15;

            // Agora a posição baseia-se no offset centralizado + o gap calculado
            int posX = offsetX + (logicosX.get(no) - 1) * gapX;
            int posY = offsetY + (logicosY.get(no) - 1) * gapY;

            if (no.getLeft() != null) {
                int filhoX = offsetX + (logicosX.get(no.getLeft()) - 1) * gapX;
                int filhoY = offsetY + (logicosY.get(no.getLeft()) - 1) * gapY;
                g.drawLine(posX, posY, filhoX, filhoY);
                desenhar(g, no.getLeft(), offsetX, offsetY, gapX, gapY);
            }

            if (no.getRight() != null) {
                int filhoX = offsetX + (logicosX.get(no.getRight()) - 1) * gapX;
                int filhoY = offsetY + (logicosY.get(no.getRight()) - 1) * gapY;
                g.drawLine(posX, posY, filhoX, filhoY);
                desenhar(g, no.getRight(), offsetX, offsetY, gapX, gapY);
            }

            g.setColor(Color.WHITE);
            g.fillOval(posX - raio, posY - raio, 2 * raio, 2 * raio);
            g.setColor(Color.BLACK);
            g.drawOval(posX - raio, posY - raio, 2 * raio, 2 * raio);

            String valorTexto = no.getValor().toString();
            FontMetrics fm = g.getFontMetrics();
            int larguraTexto = fm.stringWidth(valorTexto);
            int alturaTexto = fm.getAscent() - fm.getDescent();
            g.drawString(valorTexto, posX - (larguraTexto / 2), posY + (alturaTexto / 2));
        }
    }
}