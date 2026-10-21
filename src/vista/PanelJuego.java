package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import modelo.Arma;
import modelo.ArmaFuego;
import modelo.ArmaHielo;
import modelo.Aro;
import modelo.Enemigo;
import modelo.EnemigoTerrestre;
import modelo.EnemigoVolador;
import modelo.Moneda;
import modelo.Nivel;
import modelo.Pelota;
import modelo.Personaje;
import modelo.Plataforma;
import modelo.Recolectable;
import modelo.Escenario;
import modelo.FabricaNivel1;

public class PanelJuego extends JPanel {

    private static final Color COLOR_PLATAFORMA = new Color(226, 228, 234);
    private static final Color COLOR_PLATAFORMA_BORDE = new Color(150, 154, 166);
    private static final Color COLOR_MADERA = new Color(150, 96, 48);
    private static final Color COLOR_MADERA_BORDE = new Color(94, 58, 26);
    private static final Color COLOR_MONEDA = new Color(250, 204, 21);
    private static final Color COLOR_ENEMIGO = new Color(225, 45, 45);
    private static final Color COLOR_VOLADOR = new Color(249, 140, 30);
    private static final Color COLOR_HIELO = new Color(125, 211, 252);
    private static final Color COLOR_FUEGO = new Color(239, 68, 68);
    private static final Color COLOR_PERSONAJE = new Color(37, 99, 235);

    private Nivel nivel;
    private Escenario escenario;
    
    private Image imgFondoEscenario;

    private String mensaje;
    private long tiempoTranscurrido;

    private PanelPausa panelPausa;
    private PanelFinNivel panelDerrota;
    private PanelFinNivel panelVictoria;

    private HUD hud;

    public PanelJuego(Escenario escenario) {
        super(new GridBagLayout());

        this.mensaje = "";
        this.tiempoTranscurrido = 0;

        this.setPreferredSize(new Dimension(Ventana.ANCHO_PANTALLA,Ventana.ALTO_PANTALLA));
        this.setFocusable(true);
        this.setOpaque(true);

        this.panelPausa = new PanelPausa();
        this.panelDerrota = new PanelFinNivel("src/assets/ui/img-lvl-fail.png",new Dimension(627,37),"src/assets/ui/btn-reset-pause-normal.png", "src/assets/ui/btn-reset-pause-hover.png");
        this.panelVictoria = new PanelFinNivel("src/assets/ui/img-lvl-victory.png",new Dimension(627,37),"src/assets/ui/btn-next-normal.png", "src/assets/ui/btn-next-hover.png");

        this.hud = new HUD(
                           "src/assets/hud/icon-ps-00.png",
                           "src/assets/hud/icon-ps-01.png", 
                           "src/assets/hud/bg-ps.png", 
                           "src/assets/hud/bg-coins.png",
                           "src/assets/hud/icon-coins.png",
                           "src/assets/hud/bg-time.png",
                           "src/assets/hud/text-time.png",
                           "src/assets/hud/bg-score.png",
                           "src/assets/hud/text-score.png"
                        );

        panelPausa.setVisible(false);
        panelDerrota.setVisible(false);
        panelVictoria.setVisible(false);

        this.add(panelPausa);
        this.add(panelDerrota);
        this.add(panelVictoria);
        
        escenarioCambiado(escenario);
    }

    //Getters
    public Nivel getNivel() {
        return this.nivel;
    }

    public Escenario getEscenario() {
        return escenario;
    }
    
    public PanelPausa getPanelPausa(){
        return this.panelPausa;
    }

    public PanelFinNivel getPanelDerrota() {
        return panelDerrota;
    }

    public PanelFinNivel getPanelVictoria() {
        return panelVictoria;
    }

    //Setters
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setTiempoTranscurrido(long tiempoTranscurrido) {
        this.tiempoTranscurrido = tiempoTranscurrido;
    }

    public void togglePausa(){
        panelPausa.setVisible(!panelPausa.isVisible());
    }

    public void toggleDerrota(){
        panelDerrota.setVisible(!panelDerrota.isVisible());
    }

    public void toggleVictoria(){
        panelVictoria.setVisible(!panelVictoria.isVisible());
    }

    public void escenarioCambiado(Escenario escenario){
        this.escenario = escenario;
        this.nivel = escenario.getNivel();
        this.imgFondoEscenario = new ImageIcon(escenario.getRutaFondo()).getImage();
        repaint();    
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.drawImage(imgFondoEscenario, 0, 0, getWidth(), getHeight(), null);

        this.dibujarPlataformas(g2);
        this.dibujarMonedas(g2);
        this.dibujarRecolectables(g2);
        this.dibujarAro(g2);
        this.dibujarEnemigos(g2);
        this.dibujarPersonaje(g2);
        this.dibujarPelotas(g2);
        this.dibujarHud(g2);

        g2.dispose();
    }

    private void dibujarPlataformas(Graphics2D g2) {
        for (Plataforma plataforma : this.nivel.getPlataformas()) {
            if (plataforma.isDestruida()) {
                continue;
            }

            boolean madera = plataforma.esDestructible();
            g2.setColor(madera ? COLOR_MADERA : COLOR_PLATAFORMA);
            g2.fill(new RoundRectangle2D.Double(
                    plataforma.getPosicionX(), plataforma.getPosicionY(),
                    plataforma.getAncho(), plataforma.getAlto(), 10, 10));

            g2.setColor(madera ? COLOR_MADERA_BORDE : COLOR_PLATAFORMA_BORDE);
            g2.setStroke(new BasicStroke(3f));
            g2.draw(new RoundRectangle2D.Double(
                    plataforma.getPosicionX(), plataforma.getPosicionY(),
                    plataforma.getAncho(), plataforma.getAlto(), 10, 10));

            if (madera) {
                g2.setStroke(new BasicStroke(2f));
                for (int i = 1; i < 4; i++) {
                    double x = plataforma.getPosicionX() + (plataforma.getAncho() * i / 4.0);
                    g2.draw(new java.awt.geom.Line2D.Double(
                            x, plataforma.getPosicionY() + 3,
                            x, plataforma.getPosicionY() + plataforma.getAlto() - 3));
                }
            }
        }
    }

    private void dibujarMonedas(Graphics2D g2) {
        for (Moneda moneda : this.nivel.getMonedas()) {
            if (moneda.isRecolectado()) {
                continue;
            }

            g2.setColor(COLOR_MONEDA);
            g2.fill(new Ellipse2D.Double(
                    moneda.getPosicionX(), moneda.getPosicionY(),
                    moneda.getAncho(), moneda.getAlto()));

            g2.setColor(new Color(180, 130, 10));
            g2.setStroke(new BasicStroke(3f));
            g2.draw(new Ellipse2D.Double(
                    moneda.getPosicionX(), moneda.getPosicionY(),
                    moneda.getAncho(), moneda.getAlto()));
        }
    }

    private void dibujarRecolectables(Graphics2D g2) {
        for (Recolectable recolectable : this.nivel.getRecolectables()) {
            if (recolectable.isRecolectado()) {
                continue;
            }

            double x = recolectable.getPosicionX();
            double y = recolectable.getPosicionY();
            double w = recolectable.getAncho();
            double h = recolectable.getAlto();

            Color relleno;
            Color borde;

            if (recolectable.getArma() instanceof ArmaHielo) {
                relleno = COLOR_HIELO;
                borde = new Color(56, 140, 190);
            } else if (recolectable.getArma() instanceof ArmaFuego) {
                relleno = COLOR_FUEGO;
                borde = new Color(160, 30, 30);
            } else {
                relleno = COLOR_MONEDA;
                borde = new Color(180, 130, 10);
            }

            Ellipse2D circulo = new Ellipse2D.Double(x, y, w, h);
            g2.setColor(relleno);
            g2.fill(circulo);
            g2.setColor(borde);
            g2.setStroke(new BasicStroke(3f));
            g2.draw(circulo);
        }
    }

    private void dibujarAro(Graphics2D g2) {
        Aro aro = this.nivel.getAro();

        double x = aro.getPosicionX();
        double y = aro.getPosicionY();
        double w = aro.getAncho();
        double h = aro.getAlto();

        double centroX = x + w * 0.5;
        double centroY = y + h * 0.55;

        g2.setColor(new Color(235, 240, 245));
        g2.fill(new RoundRectangle2D.Double(x + w - 20, y + 8, 20, h * 0.62, 6, 6));
        g2.setColor(new Color(120, 130, 145));
        g2.setStroke(new BasicStroke(3f));
        g2.draw(new RoundRectangle2D.Double(x + w - 20, y + 8, 20, h * 0.62, 6, 6));

        g2.setColor(aro.isEncestado() ? new Color(250, 204, 21) : new Color(230, 80, 40));
        g2.setStroke(new BasicStroke(6f));
        g2.draw(new Ellipse2D.Double(centroX - 24, centroY - 8, 48, 16));

        g2.setStroke(new BasicStroke(3f));
        g2.setColor(new Color(235, 240, 245));
        g2.draw(new java.awt.geom.Line2D.Double(centroX - 20, centroY + 8, centroX - 12, y + h));
        g2.draw(new java.awt.geom.Line2D.Double(centroX, centroY + 8, centroX, y + h));
        g2.draw(new java.awt.geom.Line2D.Double(centroX + 20, centroY + 8, centroX + 12, y + h));
    }

    private void dibujarEnemigos(Graphics2D g2) {
        for (Enemigo enemigo : this.nivel.getEnemigos()) {
            if (enemigo instanceof EnemigoVolador) {
                this.dibujarVolador(g2, (EnemigoVolador) enemigo);
            } else if (enemigo instanceof EnemigoTerrestre) {
                this.dibujarTerrestre(g2, (EnemigoTerrestre) enemigo);
            } else {
                this.dibujarEnemigoGenerico(g2, enemigo);
            }

            if (enemigo.estaQuemado()) {
                this.dibujarFuego(g2, enemigo);
            }
        }
    }

    private void dibujarTerrestre(Graphics2D g2, EnemigoTerrestre enemigo) {
        boolean congelado = enemigo.estaCongelado();

        g2.setColor(congelado ? COLOR_HIELO : COLOR_ENEMIGO);
        g2.fill(new Ellipse2D.Double(
                enemigo.getPosicionX(), enemigo.getPosicionY(),
                enemigo.getAncho(), enemigo.getAlto()));

        g2.setColor(congelado ? new Color(37, 99, 180) : new Color(140, 20, 20));
        g2.setStroke(new BasicStroke(3f));
        g2.draw(new Ellipse2D.Double(
                enemigo.getPosicionX(), enemigo.getPosicionY(),
                enemigo.getAncho(), enemigo.getAlto()));

        g2.setColor(Color.WHITE);
        double cx = enemigo.getPosicionX() + enemigo.getAncho() / 2.0;
        double cy = enemigo.getPosicionY() + enemigo.getAlto() / 2.0;
        g2.fill(new Ellipse2D.Double(cx - 12, cy - 7, 8, 8));
        g2.fill(new Ellipse2D.Double(cx + 4, cy - 7, 8, 8));
    }

    private void dibujarEnemigoGenerico(Graphics2D g2, Enemigo enemigo) {
        boolean congelado = enemigo.estaCongelado();

        g2.setColor(congelado ? COLOR_HIELO : COLOR_ENEMIGO);
        g2.fill(new Ellipse2D.Double(
                enemigo.getPosicionX(), enemigo.getPosicionY(),
                enemigo.getAncho(), enemigo.getAlto()));
        g2.setColor(congelado ? new Color(37, 99, 180) : new Color(140, 20, 20));
        g2.setStroke(new BasicStroke(3f));
        g2.draw(new Ellipse2D.Double(
                enemigo.getPosicionX(), enemigo.getPosicionY(),
                enemigo.getAncho(), enemigo.getAlto()));
    }

    private void dibujarVolador(Graphics2D g2, EnemigoVolador volador) {
        double x = volador.getPosicionX();
        double y = volador.getPosicionY();
        double w = volador.getAncho();
        double h = volador.getAlto();
        double cx = x + w / 2.0;
        double cy = y + h / 2.0;
        boolean congelado = volador.estaCongelado();

        Color colorCuerpo = congelado ? COLOR_HIELO : COLOR_ENEMIGO;
        Color colorBorde = congelado ? new Color(37, 99, 180) : new Color(140, 20, 20);
        Color colorAla = congelado ? new Color(147, 197, 253) : COLOR_VOLADOR;
        Color colorAlaBorde = congelado ? new Color(96, 165, 250) : new Color(194, 106, 20);

        Path2D alaIzq = new Path2D.Double();
        alaIzq.moveTo(cx, cy);
        alaIzq.lineTo(x - 26, y - 2);
        alaIzq.lineTo(x - 4, y + h * 0.42);
        alaIzq.lineTo(x - 18, y + h + 2);
        alaIzq.lineTo(cx, cy);
        alaIzq.closePath();

        Path2D alaDer = new Path2D.Double();
        alaDer.moveTo(cx, cy);
        alaDer.lineTo(x + w + 26, y - 2);
        alaDer.lineTo(x + w + 4, y + h * 0.42);
        alaDer.lineTo(x + w + 18, y + h + 2);
        alaDer.lineTo(cx, cy);
        alaDer.closePath();

        g2.setColor(colorAla);
        g2.fill(alaIzq);
        g2.fill(alaDer);

        g2.setColor(colorAlaBorde);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(alaIzq);
        g2.draw(alaDer);

        g2.setColor(colorCuerpo);
        g2.fill(new Ellipse2D.Double(x + w * 0.25, y + h * 0.25, w * 0.5, h * 0.5));
        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2f));
        g2.draw(new Ellipse2D.Double(x + w * 0.25, y + h * 0.25, w * 0.5, h * 0.5));
    }

    private void dibujarFuego(Graphics2D g2, Enemigo enemigo) {
        double x = enemigo.getPosicionX();
        double y = enemigo.getPosicionY();
        double w = enemigo.getAncho();
        int llamas = 3;

        for (int i = 0; i < llamas; i++) {
            double cx = x + w * (i + 1) / (llamas + 1.0);
            double base = y + 6;
            double alto = 18 + (i % 2) * 8;

            Path2D llama = new Path2D.Double();
            llama.moveTo(cx - 7, base);
            llama.quadTo(cx - 3, base - alto * 0.6, cx, base - alto);
            llama.quadTo(cx + 3, base - alto * 0.6, cx + 7, base);
            llama.closePath();

            g2.setColor(new Color(249, 115, 22));
            g2.fill(llama);

            Path2D nucleo = new Path2D.Double();
            nucleo.moveTo(cx - 3, base);
            nucleo.quadTo(cx - 1.5, base - alto * 0.5, cx, base - alto * 0.6);
            nucleo.quadTo(cx + 1.5, base - alto * 0.5, cx + 3, base);
            nucleo.closePath();

            g2.setColor(new Color(253, 224, 71));
            g2.fill(nucleo);
        }
    }

    private void dibujarPersonaje(Graphics2D g2) {
        Personaje p = this.nivel.getPersonaje();

        if (!p.estaVivo()) {
            return;
        }

        if (p.estaInvulnerable() && (System.currentTimeMillis() / 120) % 2 == 0) {
            return;
        }

        double x = p.getPosicionX();
        double y = p.getPosicionY();
        double w = p.getAncho();
        double h = p.getAlto();

        g2.setColor(COLOR_PERSONAJE);
        g2.fill(new RoundRectangle2D.Double(x + 4, y + h * 0.42, w - 8, h * 0.58, 10, 10));

        g2.setColor(new Color(253, 224, 71));
        g2.fill(new Ellipse2D.Double(x + w / 4.0, y + h * 0.08, w / 2.0, h * 0.36));

        double pieIzq = p.isEnElSuelo() ? 0 : 10;
        g2.setColor(new Color(30, 41, 59));
        g2.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.draw(new java.awt.geom.Line2D.Double(x + w / 2.0, y + h, x + w / 2.0 - 12, y + h + pieIzq));
        g2.draw(new java.awt.geom.Line2D.Double(x + w / 2.0, y + h, x + w / 2.0 + 12, y + h + pieIzq));

        Arma armaEquipada = p.getArmaEquipada();
        if (armaEquipada instanceof ArmaFuego) {
            g2.setColor(COLOR_FUEGO);
        } else if (armaEquipada instanceof ArmaHielo) {
            g2.setColor(COLOR_HIELO);
        } else {
            g2.setColor(COLOR_MONEDA);
        }

        if (p.getDireccion() < 0) {
            g2.fill(new Ellipse2D.Double(x + w / 2.0 - 28, y + h * 0.55, 18, 18));
        } else {
            g2.fill(new Ellipse2D.Double(x + w / 2.0 + 10, y + h * 0.55, 18, 18));
        }
    }

    private void dibujarPelotas(Graphics2D g2) {
        for (Pelota pelota : this.nivel.getPelotas()) {
            if (pelota.esDeFuego()) {
                g2.setColor(COLOR_FUEGO);
            } else if (pelota.esDeHielo()) {
                g2.setColor(COLOR_HIELO);
            } else {
                g2.setColor(COLOR_MONEDA);
            }

            g2.fill(new Ellipse2D.Double(
                    pelota.getPosicionX(), pelota.getPosicionY(),
                    pelota.getAncho(), pelota.getAlto()));
        }
    }

    private void dibujarHud(Graphics2D g2) {
        Personaje p = this.nivel.getPersonaje();

        //Monedas
        int recogidas = p.getMonedasRecolectadas();
        int totales = FabricaNivel1.MONEDAS_TOTALES;

        //PS
        int vidas = p.getPuntosVida();

        //Tiempo
        long tiempoSegundos = this.tiempoTranscurrido / 1000;

        hud.dibujar(g2, vidas,p.getPuntaje(), tiempoSegundos, recogidas, totales);

        if (!this.mensaje.isEmpty()) {
            g2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 48));
            g2.setColor(new Color(250, 204, 21));
            int anchoTexto = g2.getFontMetrics().stringWidth(this.mensaje);
            g2.drawString(this.mensaje, (getWidth() - anchoTexto) / 2, getHeight() / 2);
        }
    }
}