[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/teLsEufN)


# Oppgave 1
Skiftet om variabelnavnene for bedre lesbarhet, slik at koden ble mer selvforklarende. Metoden tar inn en verdi og bruker en while-løkke for å traversere treet og finne en ledig plass å sette inn noden. Ved hjelp av en compare-metode sammenlignes verdien med nodens verdi for å avgjøre om den nye verdien skal til venstre eller høyre. Deretter opprettes en ny node, og pekere fra foreldrenoden oppdateres for å inkludere den nye noden i trestrukturen.

# Oppgave 2
Koden teller antall forekomster av en verdi i et binært søketre. Den traverserer treet ved å sammenligne verdien med hver node. Når en match finnes, sjekker den begge sider for duplikater før den returnerer totalantallet. På denne måten fanger den opp alle forekomster av verdien, uansett plassering i treet.

# Oppgave 3
Første postorden traverserer mot venstre så langt som mulig. 
den stopper på den første bladnoden. Noden returnerer seg selv om den er den eneste noden.

Neste postorden stopper opp hvis forelder til noden er null, bare rotnoden har forelder null, så da vet den at den har kommet rundt. 
Andre noder returnerer neste.
- Hvis bladnode returner forelder
- Hvis den har høyresøsken starter den treversjeringen der.
- Hvis den ikke har høyresøsken eller er ferdig med deltre går den opp til forelder.
- 
# Oppgave 4
Lager en tempnode som utfører oppgave på og oppdaterer til å peke på neste node i postorden.

Kaster postorden rekursivt mot venstre og mot høyre. utfører oppgaven for hvert kall.

# Oppgave 5

Tok inn koden fra kompendiet. endret noen navn variablene. 
Oppdaterte hvordan pekerne ble satt til null. oppdaterte igjen når jeg manglet noen. Koden tar for seg sletting på ulike plasser.

Jeg laget en while loop som fjernet alle, fjern(verdi) skal returnere false når det ikke er verdi så det skal fungere fint.

Nullstill
Her kunne jeg laget rekursivt kall, men valgte her en metode som brukte fjern på rot. Jeg kunne effektivisert også denne funksjonen. Det får jeg se på, på egenhånd.