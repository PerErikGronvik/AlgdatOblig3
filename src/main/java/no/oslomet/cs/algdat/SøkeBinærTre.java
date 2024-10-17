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

    public boolean tom() { return antall == 0;  }

    // Oppgave 1
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> barn = rot, forelder = null;     // starter over roten på null.
        int cmp = 0;                             // hjelpevariabel

        while (barn != null)       // fortsetter til barn er utenfor treet
        {
            forelder = barn;                                 // flytter forelder nedover
            cmp = comp.compare(verdi,barn.verdi);
            barn = cmp < 0 ? barn.venstre : barn.høyre;     // flytter barn nedover
                // Syntaks: test ? hvis.sant : hvis:usant
        }
        // barn er nå null

        barn = new Node<>(verdi,forelder);                   // oppretter en ny node

        if (forelder == null) rot = barn;                  // p blir rotnode
        else if (cmp < 0) forelder.venstre = barn;
        else forelder.høyre = barn;


        antall++;
        return true;
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
    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {
        while (p.venstre != null || p.høyre != null) {
            if (p.venstre != null) p = p.venstre;
            else p = p.høyre;
        }
        return p;
    }
        private Node<T> nestePostorden(Node<T> p) {
            //p er aldri null
            if (p.forelder==null) return null;// Siste element i postorden
            // er høyre element eller, er venstre element og høyre ikke finns.
            if (p.forelder.høyre == p || p.forelder.høyre == null) return p.forelder;
            // venstrebarn med høyre søsken, returnerer høyre som neste postorden.
            return førstePostorden(p.forelder.høyre);
        }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {

        Node<T> tempNode = førstePostorden(rot);
        while (tempNode != null) {
            oppgave.utførOppgave(tempNode.verdi);
            tempNode = nestePostorden(tempNode);
        }
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return;
        postOrdenRekursiv(p.venstre, oppgave);
        postOrdenRekursiv(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> barn = rot, forelder = null;

        while (barn != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,barn.verdi);      // sammenlignefunksjon
            if (cmp < 0) { forelder = barn; barn = barn.venstre; }      // går enten til venstre
            else if (cmp > 0) { forelder = barn; barn = barn.høyre; }   // går eller til høyre
            else break;    // verdien er funnet.  //helt til venstre og ingen høyre
        }
        if (barn == null) return false;   // fant ikke verdi

        if (barn.venstre == null || barn.høyre == null)  // har ikke to barn, enten er den bladnode eller har en node.
        {
            Node<T> valgtBarn = barn.venstre != null ? barn.venstre : barn.høyre;  // Velger hvilket barn
            if (barn == rot) rot = valgtBarn; //Roten skal slettes hvis det er bare roten.
            else if (barn == forelder.venstre) {
                forelder.venstre = valgtBarn; //peker forbi noden barn som skal slettes
                barn.forelder=null;
            } else {
                forelder.høyre = valgtBarn; //peker forbi noden barn som skal slettes
                barn.forelder=null;
            }
            if (valgtBarn != null) valgtBarn.forelder = forelder;

        }
        else {// Noden har to barn
            Node<T> forelderNeste = barn, barnNeste = barn.høyre;   // finner neste i inorden
            while (barnNeste.venstre != null)
            { //navigerer til venstre
                forelderNeste = barnNeste;
                barnNeste = barnNeste.venstre;
            }
            barn.verdi = barnNeste.verdi;

            if (forelderNeste != barn) forelderNeste.venstre = barnNeste.høyre;
            else forelderNeste.høyre = barnNeste.høyre;
            if (barnNeste.høyre != null) barnNeste.høyre.forelder = forelderNeste;
        }

        antall--;   // det er nå én node mindre i treet
        return true;

    }

    public int fjernAlle(T verdi) {
        int antallFjernet=0;
        while (fjern(verdi)) {
            antallFjernet++;
        }
        return antallFjernet;

    }
    public void nullstill() {
        while (rot != null){
            Node<T> verdiBeholder = førstePostorden(rot);
            if (verdiBeholder == null) break;
            fjernAlle(verdiBeholder.verdi);
        }

        }
}