# Dagpenger kalkulator
Kalkulator for å beregne hvor mye dagpenger en person har rett på i Norge basert på dagens grunnbeløp (1G).

For at en person skal ha rett på dagpenger, må en av de to følgene kravene være møtt:
- De siste 3 årene må gjennomsnitslønnen være høyere enn 3G.
- Tjent mer det siste året enn 1.5G.

Hvis en person har rett på dagpenger, må følgende ting vurderes for å kalkulere dagsatsen:
- Hva er størst av gjennomsnittlig årslønn de 3 siste årene og siste årslønn.
- Hvis siste årslønn er størst, er årslønnen høyere enn 6G.

Hvis vi tar en person sine siste tre årslønner:
- 2024 - 550 000 kr
- 2023 - 24 000 kr
- 2022 - 110 000 kr

Så skal personen ha rett på en dagsats på 2116 kr

Antall årlige arbeidsdager i Norge er satt til å være 260, så ved beregning av dagsats må 260 dager
brukes og ikke 365.

Dagens grunnbeløp, som brukes i kalkulasjonene, blir hentet fra REST API'et https://g.nav.no/api/v1/grunnbeløp

## Forutsetninger
- Java v21+
- Gradle v8.9+

## Kjør tester
Tester kjøres gjennom Gradle, og kan kjøres i terminalen med følgende kommando:
````bash
./gradlew test
````