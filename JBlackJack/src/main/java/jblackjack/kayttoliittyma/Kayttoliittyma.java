package jblackjack.kayttoliittyma;

import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import jblackjack.domain.Pelilogiikka;

/**
 *
 * @author pyjopy
 */
public class Kayttoliittyma implements Runnable {

    private JFrame frame;
    public Pelilogiikka peli;    
    private PainikeListener nappainKuuntelija;
    
    public JButton uusiJako;
    public JButton lyoKortti;
    public JButton jaaTahan;
    public JButton doubleDown;
    public JButton lisaaPanoksenMaaraa;
    public JButton vahennaPanoksenMaaraa;
    
    private JLabel rahaaJaljella;
    private JLabel nykyinenPanos;
    private JLabel jakajankasi;
    private JLabel pelaajankasi;
    private JLabel voittoStatus;

    /**
     *
     * @param peli
     */
    public Kayttoliittyma(Pelilogiikka peli) {
        this.peli = peli;
        this.nappainKuuntelija = new PainikeListener(this, peli);
    }

    /**
     *
     */
    @Override
    public void run() {
        frame = new JFrame("JBlackJack");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setBackground(Color.green);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        luoKomponentit(frame.getContentPane());

        frame.pack();
        frame.setVisible(true);
        paivita();
    }

    private void luoKomponentit(Container container) {
        BoxLayout layout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(layout);
        
        container.add(luoInfopalkki());
        jakajankasi = new JLabel("Jakajankäsi");
        jakajankasi.setFont(new Font("Serif", Font.PLAIN, 15));
        pelaajankasi = new JLabel("Pelaajan käsi");
        pelaajankasi.setFont(new Font("Serif", Font.PLAIN, 15));
        container.add(jakajankasi);
        container.add(new JLabel("-   -   -   -   -   -"));
        container.add(pelaajankasi);
        container.add(luoPeliValikko());
    }
    /**
     * Ilmoittaa pelaajalle pelin häviöstä pop-up-ikkunalla.
     * 
     */
    public void rahatLoppuivat(){
        JOptionPane.showMessageDialog(null, "Hävisit pelin ja rahasi loppuivat. Aloita uusi peli");
    }
    
    /**
     * Ilmoittaa pelaajalle pelin voitosta pop-up-ikkunalla.
     */
    
    public void voititPelin() {
        JOptionPane.showMessageDialog(null, "Onneksi olkoon! Voitit pelin. Ethän vain juksannut järjestelmää? ;)");
    }
    
    /**
     * Päivittää käyttöliittymän tiedot.
     * Jos peli on käynnissä niin piilottaa panoksenmuuttamisnappulat.
     * Jos peli ei ole käynnissä niin piilottaa "Hit", "Stay" ja "Doubledown" nappulat.
     */

    public void paivita() {
        rahaaJaljella.setText("Rahaa: " + peli.pelaaja.rahaa);
        nykyinenPanos.setText("Panos: " + peli.pelaaja.panos);
        jakajankasi.setText(peli.jakaja.luetteleKortit());
        pelaajankasi.setText(peli.pelaaja.kasi.luetteleKortit());
        voittoStatus.setText(peli.getStatus());
        
        piilotaNapit();
    }
    
    /**
     * Piilottaa käyttöliittymän painikkeet pelistatuksen mukaan.
     * Jos kierros on käynnissä niin panoksensäätönappulat piiloitetaan
     * muulloin piiloitetaan hit,stay ja doubledown-painikkeet.
     */
    
    private void piilotaNapit(){
        if(!peli.getStatus().equals("")){
            lyoKortti.setEnabled(false);
            jaaTahan.setEnabled(false);
            doubleDown.setEnabled(false);
            lisaaPanoksenMaaraa.setEnabled(true);
            vahennaPanoksenMaaraa.setEnabled(true);
        } else{
            lyoKortti.setEnabled(true);
            jaaTahan.setEnabled(true);
            doubleDown.setEnabled(true);
            lisaaPanoksenMaaraa.setEnabled(false);
            vahennaPanoksenMaaraa.setEnabled(false);
        } 
    }

    public JFrame getFrame() {
        return frame;
    }
    
    /**
     * Luo käyttöliittymän infopalkin.
     * Sisältää pelaajan rahamäärän, nykyisen panoksen ja panoksensäätämisnäppäimet.
     * @return 
     */

    private JPanel luoInfopalkki() {
        JPanel palkki = new JPanel();
        //palkki.setLayout(new BorderLayout());
        
        rahaaJaljella = new JLabel("Rahaa jaljella");
        palkki.add(rahaaJaljella);
        nykyinenPanos = new JLabel("Panoksesi");
        palkki.add(nykyinenPanos);
        
        lisaaPanoksenMaaraa = new JButton("Lisää");
        lisaaPanoksenMaaraa.addActionListener(nappainKuuntelija);
        palkki.add(lisaaPanoksenMaaraa);
        
        vahennaPanoksenMaaraa = new JButton("Vähennä");
        vahennaPanoksenMaaraa.addActionListener(nappainKuuntelija);
        palkki.add(vahennaPanoksenMaaraa);
        
        voittoStatus = new JLabel("");
        palkki.add(voittoStatus);
        
        return palkki;
    }
    
    /**
     * Luo pelivalikon, joka sisältää hit,stay,UusiJako ja doubledown-painikkeet.
     * @return 
     */
    
    private JPanel luoPeliValikko(){
        JPanel peliValikko = new JPanel();
        peliValikko.setLayout(new GridLayout(1,4));
        
        jaaTahan = new JButton("Stay");
        jaaTahan.addActionListener(nappainKuuntelija);
        peliValikko.add(jaaTahan);
        
        lyoKortti = new JButton("Hit");
        lyoKortti.addActionListener(nappainKuuntelija);
        peliValikko.add(lyoKortti);
        
        uusiJako = new JButton("Uusi jako");
        uusiJako.addActionListener(nappainKuuntelija);
        peliValikko.add(uusiJako);
        
        doubleDown = new JButton("DoubleDown");
        doubleDown.addActionListener(nappainKuuntelija);
        peliValikko.add(doubleDown);
        
        return peliValikko;
    }


}
