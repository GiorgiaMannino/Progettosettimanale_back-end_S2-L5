package it.epicode.classi;

public class Rivista extends Catalogo {
    private Periodicita periodicita;

    public Rivista(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "CodiceISBN='" + getCodiceISBN() + '\'' +
                ", Titolo='" + getTitolo() + '\'' +
                ", AnnoPubblicazione=" + getAnnoPubblicazione() +
                ", NumeroPagine=" + getNumeroPagine() +
                ", Periodicità=" + periodicita + '\'' +
                '}';
    }

}
