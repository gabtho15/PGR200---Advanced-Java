package mappeoppgave;

import mappeoppgave.domene.Innsjoe;
import mappeoppgave.domene.Innsjoer;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Innlevering java8. Har valgt å ikke skrive noe særlig kommentarer i dette prosjektet utover
 * de stedene jeg tenker det er meget relevant. Dette fordi det var veldig klare oppgaver
 * og jeg mener/håper at navnene er selvforklarende på hva som blir gjort.
 *
 * En kommentar til oppgaveløsningen: Jeg har i stor grad valgt s -> s.noeHer, selv om "normen" er s for string,
 * og kanskje ' i -> i.noeHer' for Innsjø, men for enkelthetensskyld ble det s stort sett over hele fjøla.
 *
 * En annen kommentar. som du ser av de andre innleveringene er jeg fan av
 *
 *          Klasse/metode
 *          {
 *              something
 *          }
 *
 * Men her hvor det var så mange små metoder, har jeg faktisk valgt å bryte mitt eget oppsett ved å kutte en linje
 * og flytte krøllparantesen opp et hakk! Dette er altså gjort bevisst.
 */
public class Streamingoppgaver {

    //region Oppgave 1
    public static List<Innsjoe> begynnerPaaC() {
        List<Innsjoe> begynnerPaaC = Innsjoer.innsjoer.stream()
                .filter(s -> s.navn().startsWith("C"))
                .collect(Collectors.toList());

        return begynnerPaaC;
    }

    public static List<Innsjoe> merEnnEttOrd() {
        List<Innsjoe> merEnnEttOrd = Innsjoer.innsjoer.stream()
                .filter(s -> s.navn().contains(" "))
                .collect(Collectors.toList());

        return merEnnEttOrd;
    }

    public static List<Innsjoe> grenserTilFlereEnnToLand() {
        List<Innsjoe> grenserTilFlereEnnToLand = Innsjoer.innsjoer.stream()
                .filter(s -> s.land().size() > 2)
                .collect(Collectors.toList());

        return grenserTilFlereEnnToLand;
    }

    public static List<Innsjoe> iEuropaOver10000KmKunEttLand() {
        List<Innsjoe> iEuropaOver10000KmKunEttLand = Innsjoer.innsjoer.stream()
                .filter(s -> s.kontinent().equals("Europe"))
                .filter(s -> s.areal() > 10000)
                .filter(s -> s.land().size() == 1)
                .collect(Collectors.toList());

        return iEuropaOver10000KmKunEttLand;
    }

    public static Innsjoe førsteInnsjoeMedArealOver5000Km() {
        Innsjoe førsteInnsjoeMedArealOver5000Km = Innsjoer.innsjoer.stream()
                .filter(s -> s.areal() > 5000)
                .findFirst()
                .get();

        return førsteInnsjoeMedArealOver5000Km;
    }
    //endregion

    //region Oppgave 2
    public static List<String> navnPaaAlleInnsjoer() {
        List<String> navnPaaAlleInnsjoer = Innsjoer.innsjoer.stream()
                .map(s -> s.navn())
                .collect(Collectors.toList());

        return navnPaaAlleInnsjoer;
    }

    public static List<String> navnPaaAlleInnsjoerMedStoreBokstaver() {
        List<String> navnPaaAlleInnsjoerMedStoreBokstaver = Innsjoer.innsjoer.stream()
                .map(s -> s.navn().toUpperCase())
                .collect(Collectors.toList());

        return navnPaaAlleInnsjoerMedStoreBokstaver;
    }

    // se http://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
    public static List<Innsjoe> alleInnsjoerMedAntarktiskSattSomKontinent() {
        List<Innsjoe> alleInnsjoerMedAntarktiskSattSomKontinent = Innsjoer.innsjoer.stream()
                .map(s -> new Innsjoe(
                        s.navn(),
                        s.land(),
                        "Antarktisk",
                        s.areal(),
                        s.lengde(),
                        s.maksDybde()))
                .collect(Collectors.toList());

        return alleInnsjoerMedAntarktiskSattSomKontinent;
    }

    public static String forsteInnsjoeMedArealOver500000Km() {
        String forsteInnsjoeMedArealOver500000Km = Innsjoer.innsjoer.stream()
                .filter(s -> s.areal() > 500000.0)
                .map(s -> s.navn())
                .findFirst()
                .orElse("Ingen");

        return forsteInnsjoeMedArealOver500000Km;
    }
    //endregion

    //region Oppgave 3
    public static double gjennomsnittligArealPaaInnsjoer() {
        Double gjennomsnittligArealPaaInnsjoer = Innsjoer.innsjoer.stream()
                .mapToDouble(s -> s.areal())
                .average()
                .getAsDouble();

        return gjennomsnittligArealPaaInnsjoer;
    }

    public static Innsjoe innsjoeMedStoerstLengde() {
        Innsjoe innsjoeMedStoerstLengde = Innsjoer.innsjoer.stream()
                .max((a, b) -> a.lengde().compareTo(b.lengde()))
                .get();

        return innsjoeMedStoerstLengde;
    }

    public static Innsjoe innsjoeMedMinstLengde() {
        Innsjoe innsjoeMedMinstLengde = Innsjoer.innsjoer.stream()
                .min(Comparator.comparing(s -> s.lengde()))     // Denne kunne bli løst likt som oppgaven over, men en alternativ løsning! Takk til http://stackoverflow.com/questions/24378646/finding-max-with-lambda-expression-in-java
                .get();

        return innsjoeMedMinstLengde;
    }

    public static Innsjoe innsjoeMedDybdePåStørreEnnEnTiendelAvLengden() {
        Innsjoe innsjoeMedDybdePåStørreEnnEnTiendelAvLengden = Innsjoer.innsjoer.stream()
                .filter(s -> s.maksDybde() > s.lengde() / 10)
                .findAny()
                .get();

        return innsjoeMedDybdePåStørreEnnEnTiendelAvLengden;
    }

    public static Double gjennomsnittligLandPerInnsjoe() {
        Double gjennomsnittligLandPerInnsjoe = Innsjoer.innsjoer.stream()
        .mapToInt(s -> s.land().size())
                .average()
                .getAsDouble();

        return gjennomsnittligLandPerInnsjoe;
    }

    public static Double produktetAvAlleInnsjoersMaksDybde() {
        Double produktetAvAlleInnsjoersMaksDybde = Innsjoer.innsjoer.stream()
                .map(s -> s.maksDybde())
                .reduce((sum, s) -> sum * s)
                .orElse(-1.0);      // Takk til http://stackoverflow.com/questions/28139398/how-to-convert-optional-double-to-double-in-javafx

        return produktetAvAlleInnsjoersMaksDybde;
    }
    //endregion

    //region Oppgave 4
    public static Map<String, List<Innsjoe>> innsjoerPerKontinent()
    {
        Map<String, List<Innsjoe>> innsjoerPerKontinent = Innsjoer.innsjoer.stream()
                .collect(Collectors.groupingBy(s -> s.kontinent()));

        return innsjoerPerKontinent;
    }

    public static Map<String, Long> antallInnsjoerPerKontinent()
    {
        Map<String, Long> antallInnsjoerPerKontinent = Innsjoer.innsjoer.stream()       // http://stackoverflow.com/questions/25441088/group-by-counting-in-java8-stream-api
                .map(s -> s.kontinent())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return antallInnsjoerPerKontinent;
    }

    public static String navnPaaAlleInnsjoerIEnString(){
        String navnPaaAlleInnsjoerIEnString = Innsjoer.innsjoer.stream()
                .map(s -> s.navn())
                .collect(Collectors.joining("|"));

        return navnPaaAlleInnsjoerIEnString;
    }

    public static Double gjennomsnittligArealPaaInnsjoerMedAveragingDouble(){
        Double gjennomsnittligArealPaaInnsjoerMedAveragingDouble = Innsjoer.innsjoer.stream()
                .collect(Collectors.averagingDouble(s -> s.areal()));

        return gjennomsnittligArealPaaInnsjoerMedAveragingDouble;
    }

    public static Map<Boolean, List<Innsjoe>> mapMedInnsjoerDeltIToDybdeKategorier(){
        Map<Boolean, List<Innsjoe>> mapMedInnsjoerDeltIToDybdeKategorier = Innsjoer.innsjoer.stream()
                .collect(Collectors.partitioningBy(s -> s.maksDybde() > 500));     // En liste med 500+ og en med 500- (inkludert 500)

        return mapMedInnsjoerDeltIToDybdeKategorier;
    }
    //endregion

    //region Oppgave 5
    public static List<String> alleLandSomErRepresentert()
    {
        List<String> alleLandSomErRepresentert = Innsjoer.innsjoer.stream()
                .map(s -> s.land())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return alleLandSomErRepresentert;
    }

    public static List<String> alleLandSomErRepresentertUtenDuplikater()
    {
        List<String> alleLandSomErRepresentertUtenDuplikater = Innsjoer.innsjoer.stream()
                .map(s -> s.land())
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        return alleLandSomErRepresentertUtenDuplikater;
    }

    public static Map<String, Long> alleLandSomErRepresentertMedAntallForekomster()
    {
        Map<String,Long> alleLandSomErRepresentertMedAntallForekomster = Innsjoer.innsjoer.stream()
                .map(s -> s.land())
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return alleLandSomErRepresentertMedAntallForekomster;
    }
    //endregion
}