package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T>  implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v; høyre = h; forelder = f;
        }
        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {return verdi.toString();}
    } // class Node

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;
        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null; antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() { return antall; }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() { return antall == 0; }

    // Oppgave 1
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> barn = rot, forelder = null;     // starter over roten på null. kompendie har byttet om forelder og barn.
        int cmp = 0;                             // hjelpevariabel

        while (barn != null)       // fortsetter til barn er utenfor treet
        {
            forelder = barn;                                 // flytter forelder nedover
            cmp = comp.compare(verdi,barn.verdi);
            barn = cmp < 0 ? barn.venstre : barn.høyre;     // flytter barn nedover
            // Syntaks: test ? hvis.sant : hvis:usant
        }

        // barn er nå null

        barn = new Node<>(verdi,null);                   // oppretter en ny node vet ikke hvorfor jeg må ha null her.

        if (forelder == null) rot = barn;                  // p blir rotnode
        else if (cmp < 0) forelder.venstre = barn;
        else forelder.høyre = barn;
        barn.forelder = forelder;

        antall++;
        return true;

        // er mulig å gjøre med en node. Og sannsynligvis mer oversktil

        // En Node har referanser til sine barn og sin forelder. Forelder må få riktig verdi ved
        //hver innlegging, men rotnoden skal ha null som sin forelder.
        //Lag metoden public boolean leggInn(T verdi), som legger inn en
        //verdi riktig sted i treet. En null-verdi er ikke lov, og skal kaste en
        //NullPointerException. Du kan se på koden i kapittel 5.2 men må gjøre endrin-
        //gene som trengs for at forelder-pekeren får korrekt verdi for hver node.
    }


    // Oppgave 2
    public int antall(T verdi){

        //if (tom()||null) return 0; Ikke nødvendig
        if (verdi == null) return 0; //null vil gi feilkode, fordi ikke alt et comparable med null.

        int antallVerdi = 0;
        Node<T> forelder = rot;
        while (forelder != null) {
            int cmp = comp.compare(verdi, forelder.verdi);
            if (cmp < 0) forelder = forelder.venstre;
            else if (cmp > 0){ forelder = forelder.høyre;}
            else{
                antallVerdi++;
                forelder = forelder.høyre;
            }
        }
        return antallVerdi;
        //Metodene inneholder(), antall(), og tom() er allerede kodet. Treet tillater
        //duplikater, så en verdi kan forekomme flere ganger. Lag kode for den nye metoden
        //antall(T verdi), som teller hvor mange ganger verdi dukker opp i treet. Om
        //en verdi ikke er i treet (inkludert om verdien er null) skal metoden returnere 0.

    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {
        //p er aldri null

        Node<T> rot = p;
        while (true) {
            if (rot.venstre != null) rot = rot.venstre;
            else if (rot.høyre != null) rot = rot.høyre;
            else break;
        }
        return p==rot ? null: rot;

            //Metoden førstePostorden skal returnere første node i postorden som har
        //p som rot, og. Hvis p er den siste noden i postorden, skal metoden returnere null.
    }
    private Node<T> nestePostorden(Node<T> p) {
        //p er aldri null

        if (p.forelder==null) return null;// Siste element

        if (p.forelder.høyre == p || p.forelder.høyre == null) {// er høyre element eller er venstre element og høyre ikke finns.
            return p.forelder;
        }

        return førstePostorden(p.forelder.høyre); // venstrebarn med høyre søsken

        // nestePostorden skal returnere noden som kommer etter p i
        //postorden. Hvis p er den siste noden i postorden, skal metoden returnere null.
         //hvis p er den siste postorden.
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException();

        //Lag hjelpemetodene
        //public void postorden(Oppgave <? super T> oppgave)
        //private void postordenRekursiv(Node p, Oppgave<? super T> oppgave)
        //som brukes til å utføre en Oppgave. Oppgaven kan for eksempel være å skrive noe
        //til skjerm, og da vil denne metoden skrive ut treet i postorden.
        //Den første av disse metodene skal implementeres uten bruk av rekursjon, og
        //uten bruk av hjelpestrukturer som en stack/stabel eller queue/kø. Du skal i stedet
        //bruke funksjonen nestePostorden fra forrige oppgave. For den rekursive metoden
        //skal du lage et rekursivt kall som traverserer treet i postorden-rekkefølge.
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException();
    }

    // Oppgave 5
    public boolean fjern(T verdi) { throw new UnsupportedOperationException();
    //Lat metoden public boolean fjern(T verdi). Du kan se på koden i kapittel
        //5.2.8, men må gjøre endringene som trengs for at forelder-pekeren får rett verdi.
        //Lag så metoden public int fjernAlle(T verdi). Denne skal fjerne alle
        //forekomster av en verdi i treet, og returnere antallet som ble fjernet. Om treet ikke
        //inneholder noen forekomster (inkludert om treet er tomt) skal metoden returnere
        //0.
        //Lag til slutt metoden public void nullstill(). Den skal gå gjennom treet
        //og passe på at alle nodepekere og nodeverdier i treet blir nullet ut. Det er ikke
        //tilstrekkelig å kun sette rot til null og antall til 0.
        //3

    }

    public int fjernAlle(T verdi) { throw new UnsupportedOperationException(); }
    public void nullstill() { throw new UnsupportedOperationException(); }
}